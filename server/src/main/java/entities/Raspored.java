/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "raspored")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Raspored.findAll", query = "SELECT r FROM Raspored r"),
    @NamedQuery(name = "Raspored.findById", query = "SELECT r FROM Raspored r WHERE r.id = :id"),
    @NamedQuery(name = "Raspored.findByIdRadnik", query = "SELECT r FROM Raspored r WHERE r.idRadnik = :idRadnik"),
    @NamedQuery(name = "Raspored.findByDatumvremeKreiranja", query = "SELECT r FROM Raspored r WHERE r.datumvremeKreiranja = :datumvremeKreiranja"),
    @NamedQuery(name = "Raspored.findByJeAktuelan", query = "SELECT r FROM Raspored r WHERE r.jeAktuelan = :jeAktuelan")})
public class Raspored implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idRadnik")
    private int idRadnik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumvreme_kreiranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumvremeKreiranja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "jeAktuelan")
    private boolean jeAktuelan;
    @JoinColumn(name = "idResurs", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Resurs idResurs;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "raspored")
    private List<Satnica> satnicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRaspored")
    private List<Izvestaj> izvestajList;

    public Raspored() {
    }

    public Raspored(Integer id) {
        this.id = id;
    }

    public Raspored(Integer id, int idRadnik, Date datumvremeKreiranja, boolean jeAktuelan) {
        this.id = id;
        this.idRadnik = idRadnik;
        this.datumvremeKreiranja = datumvremeKreiranja;
        this.jeAktuelan = jeAktuelan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdRadnik() {
        return idRadnik;
    }

    public void setIdRadnik(int idRadnik) {
        this.idRadnik = idRadnik;
    }

    public Date getDatumvremeKreiranja() {
        return datumvremeKreiranja;
    }

    public void setDatumvremeKreiranja(Date datumvremeKreiranja) {
        this.datumvremeKreiranja = datumvremeKreiranja;
    }

    public boolean getJeAktuelan() {
        return jeAktuelan;
    }

    public void setJeAktuelan(boolean jeAktuelan) {
        this.jeAktuelan = jeAktuelan;
    }

    public Resurs getIdResurs() {
        return idResurs;
    }

    public void setIdResurs(Resurs idResurs) {
        this.idResurs = idResurs;
    }

    @XmlTransient
    @JsonbTransient
    public List<Satnica> getSatnicaList() {
        return satnicaList;
    }

    public void setSatnicaList(List<Satnica> satnicaList) {
        this.satnicaList = satnicaList;
    }

    @XmlTransient
    @JsonbTransient
    public List<Izvestaj> getIzvestajList() {
        return izvestajList;
    }

    public void setIzvestajList(List<Izvestaj> izvestajList) {
        this.izvestajList = izvestajList;
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
        if (!(object instanceof Raspored)) {
            return false;
        }
        Raspored other = (Raspored) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Raspored[ id=" + id + " ]";
    }
    
}
