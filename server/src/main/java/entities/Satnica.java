/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "satnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Satnica.findAll", query = "SELECT s FROM Satnica s"),
    @NamedQuery(name = "Satnica.findByIdRaspored", query = "SELECT s FROM Satnica s WHERE s.satnicaPK.idRaspored = :idRaspored"),
    @NamedQuery(name = "Satnica.findByVremePocetka", query = "SELECT s FROM Satnica s WHERE s.satnicaPK.vremePocetka = :vremePocetka"),
    @NamedQuery(name = "Satnica.findByVremeKraja", query = "SELECT s FROM Satnica s WHERE s.satnicaPK.vremeKraja = :vremeKraja"),
    @NamedQuery(name = "Satnica.findBySnaga", query = "SELECT s FROM Satnica s WHERE s.snaga = :snaga")})
public class Satnica implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SatnicaPK satnicaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "snaga")
    private int snaga;
    @JoinColumn(name = "idRaspored", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Raspored raspored;

    public Satnica() {
    }

    public Satnica(SatnicaPK satnicaPK) {
        this.satnicaPK = satnicaPK;
    }

    public Satnica(SatnicaPK satnicaPK, int snaga) {
        this.satnicaPK = satnicaPK;
        this.snaga = snaga;
    }

    public Satnica(int idRaspored, String vremePocetka, String vremeKraja) {
        this.satnicaPK = new SatnicaPK(idRaspored, vremePocetka, vremeKraja);
    }

    public SatnicaPK getSatnicaPK() {
        return satnicaPK;
    }

    public void setSatnicaPK(SatnicaPK satnicaPK) {
        this.satnicaPK = satnicaPK;
    }

    public int getSnaga() {
        return snaga;
    }

    public void setSnaga(int snaga) {
        this.snaga = snaga;
    }

    public Raspored getRaspored() {
        return raspored;
    }

    public void setRaspored(Raspored raspored) {
        this.raspored = raspored;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (satnicaPK != null ? satnicaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Satnica)) {
            return false;
        }
        Satnica other = (Satnica) object;
        if ((this.satnicaPK == null && other.satnicaPK != null) || (this.satnicaPK != null && !this.satnicaPK.equals(other.satnicaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Satnica[ satnicaPK=" + satnicaPK + " ]";
    }
    
}
