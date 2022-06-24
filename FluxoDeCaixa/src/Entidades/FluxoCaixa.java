/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

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

/**
 *
 * @author beraldo
 */
@Entity
@Table(name = "FluxoCaixa")
@NamedQueries({
    @NamedQuery(name = "FluxoCaixa.findAll", query = "SELECT f FROM FluxoCaixa f"),
    @NamedQuery(name = "FluxoCaixa.findById", query = "SELECT f FROM FluxoCaixa f WHERE f.id = :id"),
    @NamedQuery(name = "FluxoCaixa.findByDataOcorrencia", query = "SELECT f FROM FluxoCaixa f WHERE f.dataOcorrencia = :dataOcorrencia"),
    @NamedQuery(name = "FluxoCaixa.findByDataOcorrenciaAndAPagar", query = "SELECT f FROM FluxoCaixa f WHERE f.codCat = :codCat AND f.dataOcorrencia = :dataOcorrencia"),
    @NamedQuery(name = "FluxoCaixa.findByDescricao", query = "SELECT f FROM FluxoCaixa f WHERE f.descricao = :descricao"),
    @NamedQuery(name = "FluxoCaixa.findByValor", query = "SELECT f FROM FluxoCaixa f WHERE f.valor = :valor"),
    @NamedQuery(name = "FluxoCaixa.findByFormaPagto", query = "SELECT f FROM FluxoCaixa f WHERE f.formaPagto = :formaPagto")})
public class FluxoCaixa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "dataOcorrencia")
    @Temporal(TemporalType.DATE)
    private Date dataOcorrencia;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "valor")
    private double valor;
    @Basic(optional = false)
    @Column(name = "formaPagto")
    private int formaPagto;
    @JoinColumn(name = "codSubCat", referencedColumnName = "codSub")
    @ManyToOne
    private SubCategorias codSubCat;
    @JoinColumn(name = "codCat", referencedColumnName = "codigo")
    @ManyToOne
    private CategoriasContas codCat;

    public FluxoCaixa() {
    }

    public FluxoCaixa(Integer id) {
        this.id = id;
    }

    public FluxoCaixa(Integer id, Date dataOcorrencia, String descricao, double valor, int formaPagto) {
        this.id = id;
        this.dataOcorrencia = dataOcorrencia;
        this.descricao = descricao;
        this.valor = valor;
        this.formaPagto = formaPagto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(Date dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getFormaPagto() {
        return formaPagto;
    }

    public void setFormaPagto(int formaPagto) {
        this.formaPagto = formaPagto;
    }

    public SubCategorias getCodSubCat() {
        return codSubCat;
    }

    public void setCodSubCat(SubCategorias codSubCat) {
        this.codSubCat = codSubCat;
    }

    public CategoriasContas getCodCat() {
        return codCat;
    }

    public void setCodCat(CategoriasContas codCat) {
        this.codCat = codCat;
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
        if (!(object instanceof FluxoCaixa)) {
            return false;
        }
        FluxoCaixa other = (FluxoCaixa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.FluxoCaixa[ id=" + id + " ]";
    }
    
}
