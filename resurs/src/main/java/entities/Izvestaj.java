/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.util.Date;

/**
 *
 * @author PC
 */
public class Izvestaj {
    private Integer id;
    private Date datum;
    private String vremePocetka;
    private String vremeKraja;
    private Integer snaga;
    private Boolean jeNasilnoPrekinut; 
    private Raspored idRaspored;
    private Resurs idResurs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Resurs getIdResurs() {
        return idResurs;
    }

    public void setIdResurs(Resurs idResurs) {
        this.idResurs = idResurs;
    }

    public Raspored getIdRaspored() {
        return idRaspored;
    }

    public void setIdRaspored(Raspored idRaspored) {
        this.idRaspored = idRaspored;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(String vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public String getVremeKraja() {
        return vremeKraja;
    }

    public void setVremeKraja(String vremeKraja) {
        this.vremeKraja = vremeKraja;
    }

    public Integer getSnaga() {
        return snaga;
    }

    public void setSnaga(Integer snaga) {
        this.snaga = snaga;
    }

    public Boolean getJeNasilnoPrekinut() {
        return jeNasilnoPrekinut;
    }

    public void setJeNasilnoPrekinut(Boolean jeNasilnoPrekinut) {
        this.jeNasilnoPrekinut = jeNasilnoPrekinut;
    }

    
    @Override
    public String toString() {
        String res = "ID\tRESURS\tRASPORED\tDATUM\t\tVREME_POCETKA\tVREME_KRAJA\tSNAGA\tBIO_NASILNO_PREKINUT\n";
        return res + id + "\t" + idResurs.getId() + "\t" + idRaspored.getId() + "\t\t" + datum.toString().substring(0,10) + "\t" + vremePocetka + "\t" + vremeKraja + "\t" + snaga + "\t\t" + (jeNasilnoPrekinut ? "da" : "ne");
    }
}
