/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author PC
 */
public class Resurs {
    private Integer id;
    private Boolean jeAktivan; 
    private Integer trenutnaSnaga;
    private Integer ukupnoProizvedeno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getJeAktivan() {
        return jeAktivan;
    }

    public void setJeAktivan(Boolean jeAktivan) {
        this.jeAktivan = jeAktivan;
    }

    public Integer getTrenutnaSnaga() {
        return trenutnaSnaga;
    }

    public void setTrenutnaSnaga(Integer trenutnaSnaga) {
        this.trenutnaSnaga = trenutnaSnaga;
    }

    public Integer getUkupnoProizvedeno() {
        return ukupnoProizvedeno;
    }

    public void setUkupnoProizvedeno(Integer ukupnoProizvedeno) {
        this.ukupnoProizvedeno = ukupnoProizvedeno;
    }

    @Override
    public String toString() {
        String res = "ID\tSTANJE\tTRENUTNA_SNAGA[KW]\tUKUPNO_PROIZVEO[KWh]\n";
        return res + id + "\t" + (jeAktivan ? "radi" : "ne radi") + "\t\t" + trenutnaSnaga + "\t\t\t" + ukupnoProizvedeno;
    }
}
