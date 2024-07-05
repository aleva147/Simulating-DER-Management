/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "izvestaj")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Izvestaj.findAll", query = "SELECT i FROM Izvestaj i"),
    @NamedQuery(name = "Izvestaj.findById", query = "SELECT i FROM Izvestaj i WHERE i.id = :id"),
    @NamedQuery(name = "Izvestaj.findByDatum", query = "SELECT i FROM Izvestaj i WHERE i.datum = :datum"),
    @NamedQuery(name = "Izvestaj.findByVremePocetka", query = "SELECT i FROM Izvestaj i WHERE i.vremePocetka = :vremePocetka"),
    @NamedQuery(name = "Izvestaj.findByVremeKraja", query = "SELECT i FROM Izvestaj i WHERE i.vremeKraja = :vremeKraja"),
    @NamedQuery(name = "Izvestaj.findBySnaga", query = "SELECT i FROM Izvestaj i WHERE i.snaga = :snaga"),
    @NamedQuery(name = "Izvestaj.findByJeNasilnoPrekinut", query = "SELECT i FROM Izvestaj i WHERE i.jeNasilnoPrekinut = :jeNasilnoPrekinut")})
public class Izvestaj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "snaga")
    private int snaga;
    @Column(name = "jeNasilnoPrekinut")
    private Boolean jeNasilnoPrekinut;
    @JoinColumn(name = "idRaspored", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Raspored idRaspored;
    @JoinColumn(name = "idResurs", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Resurs idResurs;

    public Izvestaj() {
    }

    public Izvestaj(Integer id) {
        this.id = id;
    }

    public Izvestaj(Integer id, Date datum, String vremePocetka, String vremeKraja, int snaga) {
        this.id = id;
        this.datum = datum;
        this.vremePocetka = vremePocetka;
        this.vremeKraja = vremeKraja;
        this.snaga = snaga;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getSnaga() {
        return snaga;
    }

    public void setSnaga(int snaga) {
        this.snaga = snaga;
    }

    public Boolean getJeNasilnoPrekinut() {
        return jeNasilnoPrekinut;
    }

    public void setJeNasilnoPrekinut(Boolean jeNasilnoPrekinut) {
        this.jeNasilnoPrekinut = jeNasilnoPrekinut;
    }

    public Raspored getIdRaspored() {
        return idRaspored;
    }

    public void setIdRaspored(Raspored idRaspored) {
        this.idRaspored = idRaspored;
    }

    public Resurs getIdResurs() {
        return idResurs;
    }

    public void setIdResurs(Resurs idResurs) {
        this.idResurs = idResurs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Izvestaj)) {
            return false;
        }
        Izvestaj other = (Izvestaj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Izvestaj[ id=" + id + " ]";
    }
    
}
