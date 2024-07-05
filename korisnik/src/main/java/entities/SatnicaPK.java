/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author PC
 */
public class SatnicaPK {
    private int idRaspored;
    private String vremePocetka;
    private String vremeKraja;

    public int getIdRaspored() {
        return idRaspored;
    }

    public void setIdRaspored(int idRaspored) {
        this.idRaspored = idRaspored;
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

    @Override
    public String toString() {
        return vremePocetka + "-" + vremeKraja;
    }
}
