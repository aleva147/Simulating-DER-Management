/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.resurs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Raspored;
import entities.Satnica;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * @author PC
 */
public class Resurs {
    private final int ResursID = 2;
    
    
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
        ResursResource resursResource = retrofit.create(ResursResource.class);
        
        // Koriscenje retrofit objekta za slanje zahteva ka serverskoj aplikaciji:
        Resurs resurs = new Resurs();
        
        // Test:
        System.out.println("TEST:");
        String test = resurs.test(resursResource);
        System.out.println(test);
        
        
        boolean nasilnoPrekinut = false;
        
        while (true) {
            // Resurs naglasava serveru da ne obavlja posao trenutno (aktivan = false, snaga = 0, ukupnoProizvedeno += 0) 
            // (Ovo imamo zbog situacije kad je prethodno rucno zaustavljana aplikacije resursa dok je jos uvek obavljao satnicu)
            if (!nasilnoPrekinut) resurs.azurirajResurs(resursResource, false, 0, 0);
            nasilnoPrekinut = false;
            
            // Preuzmi svoj raspored sa servera:
            Raspored raspored = resurs.preuzmiRaspored(resursResource);
            if (raspored != null) {
                // Dohvati satnice rasporeda:
                List<Satnica> satnice = resurs.preuzmiSatnice(resursResource, raspored.getId());

                while(true) {
                    Satnica aktuelnaSatnica = imaAktuelneSatnice(satnice);
                    if (aktuelnaSatnica != null) {
                        System.out.println("AKTUELNA SATNICA: " + aktuelnaSatnica);
                        String vremePocetka, vremeKraja;
                        LocalTime lokalnoVreme = LocalTime.now();
                        vremePocetka = dateTimeFormatter.format(lokalnoVreme);
                        Instant msStart = Instant.now();
                        
                        // Resurs javlja serveru da zapocinje rad (aktivan = true, snaga = snagaSatnice, ukupnoProizvedeno += 0)
                        resurs.azurirajResurs(resursResource, true, aktuelnaSatnica.getSnaga(), 0);

                        while (!isteklaSatnica(aktuelnaSatnica) && !nasilnoPrekinut) {
                            // (Resurs obavlja posao)
                            // ...
                            // ...
                            Thread.sleep(7000);

                            // (Resurs vrsi proveru i obradu prekida)
                            nasilnoPrekinut = resurs.obradiZahteveZaPrekidom(resursResource);
                            if (nasilnoPrekinut) System.out.println("PRIMLJEN ZAHTEV ZA NASILNIM RESETOVANJEM");
                        }

                        lokalnoVreme = LocalTime.now();
                        vremeKraja = dateTimeFormatter.format(lokalnoVreme);
                        Instant msKraj = Instant.now();
                        long msProtekloVreme = ChronoUnit.MILLIS.between(msStart, msKraj);
                            
                        System.out.println("KRAJ RADA NA AKTUELNOJ SATNICI:");
                        int proizvedenaEnergija = izracunajProizvedenuEnergiju(vremePocetka, vremeKraja, msProtekloVreme, aktuelnaSatnica.getSnaga());
                        
                        // Resurs javlja serveru da zavrsava rad (aktivan = false, snaga = 0, ukupnoProizvedeno += snaga*(krajnjeVrem-pocetnoVrem))
                        resurs.azurirajResurs(resursResource, false, 0, proizvedenaEnergija);
                        // Resurs salje serveru izvestaj o radu
                        if (!nasilnoPrekinut) {
                            resurs.posaljiIzvestaj(resursResource, raspored.getId(), vremePocetka, vremeKraja, aktuelnaSatnica.getSnaga(), false);
                        }
                        else {
                            resurs.posaljiIzvestaj(resursResource, raspored.getId(), vremePocetka, vremeKraja, aktuelnaSatnica.getSnaga(), true);
                            break;
                        }
                    }
                    
                    if (nasilnoPrekinut) break;
                }
            }
            
            else return;
        }
    }
    
    
    private static final String URI_SERVER = "http://localhost:8080/praksa/";
    
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    
    // POMOCNE F-JE:
    private static Satnica imaAktuelneSatnice(List<Satnica> satnice) {
        LocalTime lokalnoVreme = LocalTime.now();
        String trenutnoVreme = dateTimeFormatter.format(lokalnoVreme);
        
        for (Satnica satnica : satnice) {
            if (satnica.getSatnicaPK().getVremePocetka().compareTo(trenutnoVreme) < 0 
                && satnica.getSatnicaPK().getVremeKraja().compareTo(trenutnoVreme) > 0) {
                return satnica;
            }
        }
        
        // Nema trenutno aktuelne satnice
        return null;
    }
    
    private static Boolean isteklaSatnica(Satnica aktuelnaSatnica) {
        LocalTime lokalnoVreme = LocalTime.now();
        String trenutnoVreme = dateTimeFormatter.format(lokalnoVreme);
        return aktuelnaSatnica.getSatnicaPK().getVremeKraja().compareTo(trenutnoVreme) < 0;
    }
    
    private static int izracunajProizvedenuEnergiju(String vremePocetka, String vremeKraja, long msProtekloVreme, int snagaPoSatu) {
        int brSec = (int)(msProtekloVreme / 1000);
        int brSati = brSec / 3600;
        brSec = brSec % 3600;
        int brMin = brSec / 60;
        brSec = brSec % 60;
        
        System.out.println("Pocetno vreme: " + vremePocetka + ", krajnje vreme: " + vremeKraja);
        System.out.println("brSati: " + brSati + " brMin: " + brMin + " brSec: " + brSec + "  snaga[KW]: " + snagaPoSatu);
        
        float protekloSati = brSati + brMin / 60.0f + brSec / 3600.0f;
        int proizvedeno = (int)(protekloSati * snagaPoSatu);
        
        System.out.println("Proizvedeno[KWh]: " + proizvedeno);
        
        return proizvedeno; 
    }
    
    
    
    // KOMUNIKACIJA KA SERVERU:
    private String test(ResursResource klijentResource) throws Exception {
        Call<String> zahtev = klijentResource.test();
        return zahtev.execute().body();
    }
    
    private Raspored preuzmiRaspored(ResursResource resursResource) throws Exception {
        Call<Raspored> zahtev = resursResource.preuzmiRaspored(ResursID);
        Raspored raspored = zahtev.execute().body();
        
        System.out.println("PREUZET RASPORED:");
        if (raspored != null) System.out.println(raspored);
        else System.out.println("Nema rasporeda za ovaj resurs.\nKraj izvrsavanja resursa.");
        return raspored;
    }
    
    private List<Satnica> preuzmiSatnice(ResursResource resursResource, int idRaspored) throws Exception {
        Call<List<Satnica>> zahtev = resursResource.preuzmiSatnice(idRaspored);
        List<Satnica> satnice = zahtev.execute().body();
        
        System.out.println("SATNICE:");
        if (satnice != null) {
            for (Satnica s : satnice) System.out.println("\t" + s);
        }
        else System.out.println("Neuspesno preuzete satnice rasporeda.");
        return satnice;
    }
    
    private void azurirajResurs(ResursResource klijentResource, boolean stanje, int snaga, int proizveo) throws Exception {
        Call<String> zahtev = klijentResource.azurirajResurs(ResursID, stanje, snaga, proizveo);
        String ishod = zahtev.execute().body();
        System.out.println(ishod + "\n");
    }
    
    private void posaljiIzvestaj(ResursResource klijentResource, int idRaspored, String vremePocetka, String vremeKraja, int snaga, boolean nasilnoPrekinut) throws Exception {
        Call<String> zahtev = klijentResource.kreirajIzvestaj(ResursID, idRaspored, vremePocetka, vremeKraja, snaga, nasilnoPrekinut);
        String ishod = zahtev.execute().body();
        System.out.println(ishod + "\n");
    }
    
    private boolean obradiZahteveZaPrekidom(ResursResource klijentResource) throws Exception {
        Call<Boolean> zahtev = klijentResource.obradiZahteveZaPrekidom(ResursID);
        Boolean imaZahtevaZaPrekidom = zahtev.execute().body();
        return imaZahtevaZaPrekidom;
    }
}
