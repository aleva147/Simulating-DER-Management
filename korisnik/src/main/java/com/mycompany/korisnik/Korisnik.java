/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.korisnik;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Izvestaj;
import entities.Raspored;
import entities.Resurs;
import entities.Satnica;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * @author PC
 */
public class Korisnik {
    private final int KorisnikID = 1;
    
    
    public static void main(String[] args) throws Exception {
        // Zbog nekog errora bilo neophodno:
        Gson gson = new GsonBuilder()
            .setLenient()
            .create();
        
        // Pravljenje retrofit objekta:
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URI_SERVER)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
        KorisnikResource korisnikResource = retrofit.create(KorisnikResource.class);
        
        // Koriscenje retrofit objekta za slanje zahteva ka serverskoj aplikaciji:
        Korisnik klijent = new Korisnik();
        
        // Test:
        System.out.println("TEST:");
        String test = klijent.test(korisnikResource);
        System.out.println(test);
        
        
        boolean krajAplikacije = false;
        
        Scanner in = new Scanner(System.in);
        while (krajAplikacije == false) {
            System.out.println("\nUnesite broj zahteva koji zelite da se izvrsi: ");
            System.out.println("\t1: Zadavanje novog rasporeda za resurs kroz konzolu.");
            System.out.println("\t2: Zadavanje novog rasporeda za resurs putem fajla.");
            System.out.println("\t3: Resetovanje resursa.");
            System.out.println("\t4: Dohvatanje aktivnog rasporeda resursa.");
            System.out.println("\t5: Ocitavanje situacije svih resursa.");
            System.out.println("\t6: Dohvatanje svih izvestaja jednog resursa.");
            System.out.println("\t7: Kraj rada.");
            int idZahteva = Integer.parseInt(in.nextLine());
            
            switch (idZahteva) {
                case 1:
                    klijent.kreirajRasporedKonzola(korisnikResource, in);
                    break;
                case 2:                
                    klijent.kreirajRasporedeFajl(korisnikResource, in);
                    break;
                case 3:
                    klijent.resetujResurs(korisnikResource, in);
                    break;
                case 4:
                    klijent.dohvatiAktivanRasporedResursa(korisnikResource, in);
                    break;
                case 5:
                    klijent.ocitajResurse(korisnikResource, in);
                    break;
                case 6:
                    klijent.dohvatiIzvestajeResursa(korisnikResource, in);
                    break;
                case 7:
                    System.out.println("ZAHTEV 7: kraj izvrsavanja korisnicke aplikacije.");
                    krajAplikacije = true;
                    break;
                default:
                    System.out.println("Uneti zahtev ne postoji.");
            }
        }
    }
    
    
    
    private static final String URI_SERVER = "http://localhost:8080/praksa/";
    
    
    private String test(KorisnikResource klijentResource) throws Exception {
        Call<String> zahtev = klijentResource.test();
        return zahtev.execute().body();
    }
    
    private void kreirajRasporedKonzola(KorisnikResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 1: Unesite id resursa, a zatim satnice u pojedinacnim redovima u formatu: 'hh:mm:ss-hh:mm:ss snaga'");
        System.out.println("\tUnesite 'x' u zasebnom redu da oznacite kraj unosa.");
        
        int idRes = Integer.parseInt(in.nextLine());
        
        StringBuilder raspored = new StringBuilder();
        String line = in.nextLine();
        while (!line.equals("x")) {
            raspored.append(line);
            raspored.append("\n");
            line = in.nextLine();
        }
    
        Call<String> zahtev = klijentResource.kreirajRaspored(idRes, KorisnikID, raspored.toString());
        String odgovorKreirajRaspored = zahtev.execute().body();
        System.out.println(odgovorKreirajRaspored);
    }
        
    // Primer korektno zadate putanje: "C:\\Users\\PC\\Desktop\\fajl_proba.txt".
    private void kreirajRasporedeFajl(KorisnikResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 2: Unesite apsolutnu putanju do fajla sa rasporedima.");
        System.out.println("\t(U fajlu zadati raspored kao idResursa, a potom u zasebnim redovima sve satnice i snage rada tokom njih)");
        System.out.println("\t(Praznim redom u fajlu odvajati rasporede ukoliko ih ima vise od jednog)");
        
        String putanja = in.nextLine();  
        
        // Procitaj i kreiraj raspored po raspored iz fajla:
        File fajl = new File(putanja);
        BufferedReader br = new BufferedReader(new FileReader(fajl));
        StringBuilder raspored = new StringBuilder();
        int idResursa;
        String linija = "";
        
        while (linija != null) {
            linija = br.readLine();
            idResursa = Integer.parseInt(linija);
            
            while (linija != null) {
                linija = br.readLine();
                
                if (linija != null && !linija.equals("")) {
                    raspored.append(linija);
                    raspored.append("\n");
                }
                // Kraj jednog od rasporeda:
                else {
                    Call<String> zahtev = klijentResource.kreirajRaspored(idResursa, KorisnikID, raspored.toString());
                    String odgovorKreirajRaspored = zahtev.execute().body();
                    System.out.println(odgovorKreirajRaspored);
                    
                    raspored.setLength(0);
                    break;
                }
            }
        }
    }
        
    private void resetujResurs(KorisnikResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 3: Unesite id resursa koji zelite da resetujete.");
        int idRes = Integer.parseInt(in.nextLine());
                    
        Call<String> zahtev = klijentResource.resetujResurs(idRes, KorisnikID);
        String odgovorResetujResurs = zahtev.execute().body();
        System.out.println(odgovorResetujResurs);
    }
    
    private void dohvatiAktivanRasporedResursa(KorisnikResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 4: Unesite id resursa ciji raspored zelite da vidite.");
        int idRes = Integer.parseInt(in.nextLine());
                    
        Call<Raspored> zahtev = klijentResource.dohvatiAktivanRasporedResursa(idRes);
        Raspored raspored = zahtev.execute().body();
        
        if (raspored != null) {
            System.out.println("RASPORED:\n" + raspored);
            System.out.println("------------------------------------------------------------------");
            
            Call<List<Satnica>> zahtev2 = klijentResource.dohvatiSatnice(raspored.getId());
            List<Satnica> satnice = zahtev2.execute().body();
            if (satnice == null) return;
            for (Satnica s : satnice) System.out.println("\t" + s);
        }
    }
    
    private void ocitajResurse(KorisnikResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 5: Dohvata se trenutna situacija svih resursa...\n");
        
        Call<List<Resurs>> zahtev = klijentResource.dohvatiResurse();
        List<Resurs> resursi = zahtev.execute().body();
        long ukupnaTrenutnaSnaga = 0, ukupnaProizvedenaEnergija = 0;
        
        if (resursi != null) {
            for (Resurs resurs : resursi) { 
                System.out.println(resurs);
                ukupnaTrenutnaSnaga += resurs.getTrenutnaSnaga();
                ukupnaProizvedenaEnergija += resurs.getUkupnoProizvedeno();
            }
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("UKUPNA SNAGA TRENUTNO AKTIVNIH RESURSA: " + ukupnaTrenutnaSnaga);
        System.out.println("UKUPNA KOLICINA PROIZVEDENE ENERGIJE OD STARTA SERVERA: " + ukupnaProizvedenaEnergija);
    }
    
    private void dohvatiIzvestajeResursa(KorisnikResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 6: Unesite id resursa cije izvestaje zelite da vidite.");
        int idRes = Integer.parseInt(in.nextLine());
                    
        Call<List<Izvestaj>> zahtev = klijentResource.dohvatiIzvestajeResursa(idRes);
        List<Izvestaj> izvestaji = zahtev.execute().body();
        
        if (izvestaji != null) {
            for (Izvestaj izvestaj : izvestaji) System.out.println(izvestaj);
        }
        System.out.println("-------------------------------------------------------------------------");
    }
}