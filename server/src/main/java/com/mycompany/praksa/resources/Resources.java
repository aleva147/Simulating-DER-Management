/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praksa.resources;

import entities.Izvestaj;
import entities.NasilnoRestartovanje;
import entities.Raspored;
import entities.Resurs;
import entities.Satnica;
import entities.SatnicaPK;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author PC
 */
@Path("")
@Stateless
public class Resources {
    @PersistenceContext(unitName = "pu")
    EntityManager em;
    
    
    // TESTIRANJE VEZE SA SERVEROM:
    @GET
    @Path("/test")
    public String test(){
        System.out.println("test uspesan");
        return "Uspesno uspostavljena konekcija sa serverom.";
    }
    
    
    
    // SPECIJALAN ZAHTEV:
    @PUT
    @Path("/obrisi_ukupno_proizvedeno")
    public String obrisiUkupnoProizvedeno(){
        List<Resurs> resursi = em.createNamedQuery("Resurs.findAll", Resurs.class).getResultList();
        for (Resurs r : resursi) r.setUkupnoProizvedeno(0);
        
        return "Uspesno obrisano ukupno_proizvedeno";
    }
    
    
    
    // ZAHTEVI OD KORISNIKA:
    @POST
    @Path("/kreiranje_raspored")
    public String kreirajRaspored(
            @QueryParam("idResurs") Integer idRes, 
            @QueryParam("idRadnik") Integer idRad,
            @QueryParam("raspored") String rasporedStr
    ) {
        // Kreiranje rasporeda:
        Raspored raspored = new Raspored();
        Integer poslId = em.createQuery("SELECT MAX(r.id) FROM Raspored r", Integer.class).getSingleResult();
        int rasporedId = poslId == null ? 1 : poslId + 1;
        raspored.setId(rasporedId);
        
        Resurs resurs = em.find(Resurs.class, idRes);
        raspored.setIdResurs(resurs);
        
        raspored.setIdRadnik(idRad);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Belgrade"));
        raspored.setDatumvremeKreiranja(calendar.getTime());
        raspored.setJeAktuelan(false);
        raspored.setIzvestajList(null);
        raspored.setSatnicaList(null);
        em.persist(raspored);
        em.flush();
        
        // Kreiranje satnica:
        List<Satnica> satnicaList = new ArrayList<>();
        String[] paroviSatnicaSnaga = rasporedStr.split("\n");
        for (String parSatnicaSnaga : paroviSatnicaSnaga) {
            String vremena = parSatnicaSnaga.split(" ")[0];
            int snaga = Integer.parseInt(parSatnicaSnaga.split(" ")[1]);
            String vremePocetka = vremena.split("-")[0];
            String vremeKraja = vremena.split("-")[1];
            
            Satnica satnica = new Satnica(new SatnicaPK(rasporedId, vremePocetka, vremeKraja));
            satnica.setSnaga(snaga);
            satnicaList.add(satnica);
            
            em.persist(satnica);
        }
        em.flush();

        // Da bi promene bile vidljive dalje tokom rada aplikacije korisnika.
        raspored.setSatnicaList(satnicaList);
        em.persist(raspored);
        em.flush();
        
        return "Uspesno kreiran raspored.";
    }
    
    @POST
    @Path("/resetujResurs/{idRes}/{idKor}")
    public String resetujResurs(@PathParam("idRes") Integer idRes, @PathParam("idKor") Integer idKor) {
        NasilnoRestartovanje nr = new NasilnoRestartovanje();
        Integer poslId = em.createQuery("SELECT MAX(nr.id) FROM NasilnoRestartovanje nr", Integer.class).getSingleResult();
        int nrId = poslId == null ? 1 : poslId + 1;
        nr.setId(nrId);
        
        Resurs resurs = em.find(Resurs.class, idRes);
        nr.setIdResurs(resurs);
        
        nr.setIdRadnik(idKor);
        nr.setDatumvremeKreiranja(new Date());
        nr.setJeIzvrseno(false);
        
        em.persist(nr);
        em.flush();
        
        return "Uspesno kreiran zahtev za nasilnim restartovanjem resursa " + idRes;
    }
    
    @GET
    @Path("/dohvatanje_akitvnog_rasporeda/{idRes}")
    @Produces(MediaType.APPLICATION_JSON)
    public Raspored dohvatiAktivanRasporedResursa(@PathParam("idRes") Integer idRes) {
        Raspored raspored = em.createQuery("SELECT r FROM Raspored r WHERE r.idResurs.id = :idRes AND r.jeAktuelan = :aktuelnost", Raspored.class).setParameter("idRes", idRes).setParameter("aktuelnost", true).setMaxResults(1).getSingleResult();
        return raspored;
    } 
    
    @GET
    @Path("/dohvatanje_resursi")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resurs> dohvatiResurse() {
        List<Resurs> resursi = em.createNamedQuery("Resurs.findAll", Resurs.class).getResultList();
        return resursi;
    } 
    
    @GET
    @Path("/dohvatanje_izvestaja_resursa/{idRes}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Izvestaj> dohvatiIzvestajeResursa(@PathParam("idRes") Integer idRes) {
        List<Izvestaj> izvestaji = em.createQuery("SELECT i FROM Izvestaj i WHERE i.idResurs.id = :idRes", Izvestaj.class).setParameter("idRes", idRes).getResultList();
        return izvestaji;
    }
    
    
    
    // ZAHTEVI OD RESURSA:
    @PUT
    @Path("/preuzmi_raspored/{idRes}")
    public Raspored preuzmiRaspored(@PathParam("idRes") Integer idRes){
        Integer rasporedId = em.createQuery("SELECT MAX(r.id) FROM Raspored r WHERE r.idResurs.id = :idRes", Integer.class).setParameter("idRes", idRes).setMaxResults(1).getSingleResult();
        if (rasporedId == null) return null;
        
        Raspored raspored = em.createQuery("SELECT r FROM Raspored r WHERE r.id = :rasporedId", Raspored.class).setParameter("rasporedId", rasporedId).setMaxResults(1).getSingleResult();
        
        // Azuriraj da je ovo sada aktuealan raspored za resurs:
        if (raspored.getJeAktuelan() == false) {
            List<Raspored> stariRaspored = em.createQuery("SELECT r FROM Raspored r WHERE r.idResurs.id = :idRes AND r.jeAktuelan = :aktuelan", Raspored.class).setParameter("idRes", idRes).setParameter("aktuelan", true).getResultList();
            if (stariRaspored != null && !stariRaspored.isEmpty()) stariRaspored.get(0).setJeAktuelan(false);
            raspored.setJeAktuelan(true);
        }
        
        return raspored;
    }
    
    @GET
    @Path("/preuzmi_satnice/{idRaspored}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Satnica> dohvatiSatnice(@PathParam("idRaspored") Integer idRaspored) {
        List<Satnica> satnice = em.createQuery("SELECT s FROM Satnica s WHERE s.satnicaPK.idRaspored = :idRaspored", Satnica.class).setParameter("idRaspored", idRaspored).getResultList();
        return satnice;
    }
    
    @PUT
    @Path("/azuriraj_resurs")
    public String azurirajResurs(
            @QueryParam("idRes") Integer idRes,
            @QueryParam("stanje") Boolean stanje,
            @QueryParam("snaga") Integer snaga,
            @QueryParam("proizveo") Integer proizveo
    ){
        Resurs resurs = em.find(Resurs.class, idRes);
        resurs.setJeAktivan(stanje);
        resurs.setTrenutnaSnaga(snaga);
        resurs.setUkupnoProizvedeno(resurs.getUkupnoProizvedeno() + proizveo);
        
        StringBuilder sb = new StringBuilder();
        sb.append("Uspesno azuriran resurs na serveru [stanje: ");
        sb.append(stanje ? "radi" : "ne radi");
        sb.append(", snaga: ");
        sb.append(snaga);
        sb.append("]");
        
        return sb.toString();
    }
    
    @POST
    @Path("/kreiranje_izvestaj")
    public String kreirajIzvestaj(
            @QueryParam("idRes") Integer idRes,
            @QueryParam("idRaspored") Integer idRaspored,
            @QueryParam("vremePocetka") String vremePocetka,
            @QueryParam("vremeKraja") String vremeKraja,
            @QueryParam("snaga") Integer snaga,
            @QueryParam("jeNasilnoPrekinut") Boolean jeNasilnoPrekinut
    ){
        Izvestaj izvestaj = new Izvestaj();
        Integer poslId = em.createQuery("SELECT MAX(i.id) FROM Izvestaj i", Integer.class).getSingleResult();
        int izvestajId = poslId == null ? 1 : poslId + 1;
        izvestaj.setId(izvestajId);
        
        Resurs resurs = em.find(Resurs.class, idRes);
        izvestaj.setIdResurs(resurs);
        Raspored raspored = em.find(Raspored.class, idRaspored);
        izvestaj.setIdRaspored(raspored);
        
        izvestaj.setDatum(new Date());
        
        izvestaj.setVremePocetka(vremePocetka);
        izvestaj.setVremeKraja(vremeKraja);
        izvestaj.setSnaga(snaga);
        izvestaj.setJeNasilnoPrekinut(jeNasilnoPrekinut);
        
        em.persist(izvestaj);
        em.flush();
        
        return "Uspesno kreiran izvestaj na serveru";
    }
    
    @PUT
    @Path("/obradi_prekide/{idRes}")
    public Boolean obradiZahteveZaPrekidom(@PathParam("idRes") Integer idRes){
        Integer maxNR = em.createQuery("SELECT MAX(nr.id) FROM NasilnoRestartovanje nr WHERE nr.idResurs.id = :idRes AND nr.jeIzvrseno = :izvrseno", Integer.class).setParameter("idRes", idRes).setParameter("izvrseno", false).getSingleResult();
        if (maxNR == null) return false;
        
        NasilnoRestartovanje nr = em.createQuery("SELECT nr FROM NasilnoRestartovanje nr WHERE nr.id = :maxNR", NasilnoRestartovanje.class).setParameter("maxNR", maxNR).setMaxResults(1).getSingleResult();
        nr.setJeIzvrseno(true);
        
        return true;
    }
}
