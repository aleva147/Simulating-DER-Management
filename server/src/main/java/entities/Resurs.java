/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "resurs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resurs.findAll", query = "SELECT r FROM Resurs r"),
    @NamedQuery(name = "Resurs.findById", query = "SELECT r FROM Resurs r WHERE r.id = :id"),
    @NamedQuery(name = "Resurs.findByJeAktivan", query = "SELECT r FROM Resurs r WHERE r.jeAktivan = :jeAktivan"),
    @NamedQuery(name = "Resurs.findByTrenutnaSnaga", query = "SELECT r FROM Resurs r WHERE r.trenutnaSnaga = :trenutnaSnaga"),
    @NamedQuery(name = "Resurs.findByUkupnoProizvedeno", query = "SELECT r FROM Resurs r WHERE r.ukupnoProizvedeno = :ukupnoProizvedeno")})
public class Resurs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "jeAktivan")
    private Boolean jeAktivan;
    @Column(name = "trenutnaSnaga")
    private Integer trenutnaSnaga;
    @Column(name = "ukupnoProizvedeno")
    private Integer ukupnoProizvedeno;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idResurs")
    private List<NasilnoRestartovanje> nasilnoRestartovanjeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idResurs")
    private List<Raspored> rasporedList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idResurs")
    private List<Izvestaj> izvestajList;

    public Resurs() {
    }

    public Resurs(Integer id) {
        this.id = id;
    }

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

    @XmlTransient
    @JsonbTransient
    public List<NasilnoRestartovanje> getNasilnoRestartovanjeList() {
        return nasilnoRestartovanjeList;
    }

    public void setNasilnoRestartovanjeList(List<NasilnoRestartovanje> nasilnoRestartovanjeList) {
        this.nasilnoRestartovanjeList = nasilnoRestartovanjeList;
    }

    @XmlTransient
    @JsonbTransient
    public List<Raspored> getRasporedList() {
        return rasporedList;
    }

    public void setRasporedList(List<Raspored> rasporedList) {
        this.rasporedList = rasporedList;
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
        if (!(object instanceof Resurs)) {
            return false;
        }
        Resurs other = (Resurs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Resurs[ id=" + id + " ]";
    }
    
}
