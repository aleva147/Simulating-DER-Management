/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.korisnik;

import entities.Izvestaj;
import entities.Raspored;
import entities.Resurs;
import entities.Satnica;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * @author PC
 */
public interface KorisnikResource {
    @Headers({ "Accept: text/html" })
    @GET("resources/test")
    Call<String> test();
    
    
    
    @Headers({ "Accept: text/html" })
    @POST("resources/kreiranje_raspored")
    Call<String> kreirajRaspored(
            @Query("idResurs") Integer idRes, 
            @Query("idRadnik") Integer idRad, 
            @Query("raspored") String raspored
    );
    
    @Headers({ "Accept: text/html" })
    @POST("resources/resetujResurs/{idRes}/{idKor}")
    Call<String> resetujResurs(@Path("idRes") Integer idRes, @Path("idKor") Integer idKor);
    
    @Headers({ "Accept: application/json" })
    @GET("resources/dohvatanje_akitvnog_rasporeda/{idRes}")
    Call<Raspored> dohvatiAktivanRasporedResursa(@Path("idRes") Integer idRes);
    
    @Headers({ "Accept: application/json" })
    @GET("resources/preuzmi_satnice/{idRaspored}")
    Call<List<Satnica>> dohvatiSatnice(@Path("idRaspored") Integer idRaspored);
    
    @Headers({ "Accept: application/json" })
    @GET("resources/dohvatanje_resursi")
    Call<List<Resurs>> dohvatiResurse();
    
    @Headers({ "Accept: application/json" })
    @GET("resources/dohvatanje_izvestaja_resursa/{idRes}")
    Call<List<Izvestaj>> dohvatiIzvestajeResursa(@Path("idRes") Integer idRes);
}
