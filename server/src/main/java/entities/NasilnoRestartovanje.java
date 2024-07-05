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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "nasilno_restartovanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NasilnoRestartovanje.findAll", query = "SELECT n FROM NasilnoRestartovanje n"),
    @NamedQuery(name = "NasilnoRestartovanje.findById", query = "SELECT n FROM NasilnoRestartovanje n WHERE n.id = :id"),
    @NamedQuery(name = "NasilnoRestartovanje.findByIdRadnik", query = "SELECT n FROM NasilnoRestartovanje n WHERE n.idRadnik = :idRadnik"),
    @NamedQuery(name = "NasilnoRestartovanje.findByDatumvremeKreiranja", query = "SELECT n FROM NasilnoRestartovanje n WHERE n.datumvremeKreiranja = :datumvremeKreiranja"),
    @NamedQuery(name = "NasilnoRestartovanje.findByJeIzvrseno", query = "SELECT n FROM NasilnoRestartovanje n WHERE n.jeIzvrseno = :jeIzvrseno")})
public class NasilnoRestartovanje implements Serializable {

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
    @Column(name = "jeIzvrseno")
    private Boolean jeIzvrseno;
    @JoinColumn(name = "idResurs", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Resurs idResurs;

    public NasilnoRestartovanje() {
    }

    public NasilnoRestartovanje(Integer id) {
        this.id = id;
    }

    public NasilnoRestartovanje(Integer id, int idRadnik, Date datumvremeKreiranja) {
        this.id = id;
        this.idRadnik = idRadnik;
        this.datumvremeKreiranja = datumvremeKreiranja;
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

    public Boolean getJeIzvrseno() {
        return jeIzvrseno;
    }

    public void setJeIzvrseno(Boolean jeIzvrseno) {
        this.jeIzvrseno = jeIzvrseno;
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
        if (!(object instanceof NasilnoRestartovanje)) {
            return false;
        }
        NasilnoRestartovanje other = (NasilnoRestartovanje) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.NasilnoRestartovanje[ id=" + id + " ]";
    }
    
}
