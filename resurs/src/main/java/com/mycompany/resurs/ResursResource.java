/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resurs;

import entities.Raspored;
import entities.Satnica;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * @author PC
 */
public interface ResursResource {
    @Headers({ "Accept: text/html" })
    @GET("resources/test")
    Call<String> test();
    
    @Headers({ "Accept: application/json" })
    @PUT("resources/preuzmi_raspored/{idRes}")
    Call<Raspored> preuzmiRaspored(@Path("idRes") Integer idRes);
    
    @Headers({ "Accept: application/json" })
    @GET("resources/preuzmi_satnice/{idRaspored}")
    Call<List<Satnica>> preuzmiSatnice(@Path("idRaspored") Integer idRaspored);
    
    @Headers({ "Accept: text/html" })
    @PUT("resources/azuriraj_resurs")
    Call<String> azurirajResurs(
            @Query("idRes") Integer idRes,
            @Query("stanje") Boolean stanje,
            @Query("snaga") Integer snaga,
            @Query("proizveo") Integer proizveo
    );
    
    @Headers({ "Accept: text/html" })
    @POST("resources/kreiranje_izvestaj")
    Call<String> kreirajIzvestaj(
            @Query("idRes") Integer idRes,
            @Query("idRaspored") Integer idRaspored,
            @Query("vremePocetka") String vremePocetka,
            @Query("vremeKraja") String vremeKraja,
            @Query("snaga") Integer snaga,
            @Query("jeNasilnoPrekinut") Boolean jeNasilnoPrekinut
    );
    
    @Headers({ "Accept: application/json" })
    @PUT("resources/obradi_prekide/{idRes}")
    Call<Boolean> obradiZahteveZaPrekidom(@Path("idRes") Integer idRes);
}
