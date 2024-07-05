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
public class Raspored {
    private Integer id;
    private Resurs idResurs;
    private Integer idRadnik;
    private Date datumvremeKreiranja;
    private Boolean jeAktuelan;

            
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

    public Integer getIdRadnik() {
        return idRadnik;
    }

    public void setIdRadnik(Integer idRadnik) {
        this.idRadnik = idRadnik;
    }

    public Date getDatumvremeKreiranja() {
        return datumvremeKreiranja;
    }

    public void setDatumvremeKreiranja(Date datumvremeKreiranja) {
        this.datumvremeKreiranja = datumvremeKreiranja;
    }

    public Boolean getJeAktuelan() {
        return jeAktuelan;
    }

    public void setJeAktuelan(Boolean jeAktuelan) {
        this.jeAktuelan = jeAktuelan;
    }

    @Override
    public String toString() {
        String res = "ID\tRESURS\tKORISNIK\tVREME_KREIRANJA\t\tJE_AKTUELAN\n";
        return res + String.format(id + "\t" + idResurs.getId() + "\t" + idRadnik + "\t" + datumvremeKreiranja.toString() + "\t\t" + (jeAktuelan ? "da" : "ne"));
    }
}
