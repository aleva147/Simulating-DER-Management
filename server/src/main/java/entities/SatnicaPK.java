/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author PC
 */
@Embeddable
public class SatnicaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idRaspored")
    private int idRaspored;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "vremePocetka")
    private String vremePocetka;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "vremeKraja")
    private String vremeKraja;

    public SatnicaPK() {
    }

    public SatnicaPK(int idRaspored, String vremePocetka, String vremeKraja) {
        this.idRaspored = idRaspored;
        this.vremePocetka = vremePocetka;
        this.vremeKraja = vremeKraja;
    }

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
    public int hashCode() {
        int hash = 0;
        hash += (int) idRaspored;
        hash += (vremePocetka != null ? vremePocetka.hashCode() : 0);
        hash += (vremeKraja != null ? vremeKraja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SatnicaPK)) {
            return false;
        }
        SatnicaPK other = (SatnicaPK) object;
        if (this.idRaspored != other.idRaspored) {
            return false;
        }
        if ((this.vremePocetka == null && other.vremePocetka != null) || (this.vremePocetka != null && !this.vremePocetka.equals(other.vremePocetka))) {
            return false;
        }
        if ((this.vremeKraja == null && other.vremeKraja != null) || (this.vremeKraja != null && !this.vremeKraja.equals(other.vremeKraja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SatnicaPK[ idRaspored=" + idRaspored + ", vremePocetka=" + vremePocetka + ", vremeKraja=" + vremeKraja + " ]";
    }
    
}
