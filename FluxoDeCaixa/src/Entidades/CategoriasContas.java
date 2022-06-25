/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author allanneves
 */
@Entity
@Table(name = "CategoriasContas")
@NamedQueries({
    @NamedQuery(name = "CategoriasContas.findAll", query = "SELECT c FROM CategoriasContas c"),
    @NamedQuery(name = "CategoriasContas.findByCodigo", query = "SELECT c FROM CategoriasContas c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CategoriasContas.findByDescricao", query = "SELECT c FROM CategoriasContas c WHERE c.descricao = :descricao"),
    @NamedQuery(name = "CategoriasContas.findByPositiva", query = "SELECT c FROM CategoriasContas c WHERE c.positiva = :positiva")})
public class CategoriasContas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "positiva")
    private boolean positiva;
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy = "codCat")
    private Collection<SubCategorias> subCategoriasCollection;
    @OneToMany(mappedBy = "codCat")
    private Collection<FluxoCaixa> fluxoCaixaCollection;

    public CategoriasContas() {
    }

    public CategoriasContas(Integer codigo) {
        this.codigo = codigo;
    }

    public CategoriasContas(Integer codigo, String descricao, boolean positiva) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.positiva = positiva;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean getPositiva() {
        return positiva;
    }

    public void setPositiva(boolean positiva) {
        this.positiva = positiva;
    }

    public Collection<SubCategorias> getSubCategoriasCollection() {
        return subCategoriasCollection;
    }

    public void setSubCategoriasCollection(Collection<SubCategorias> subCategoriasCollection) {
        this.subCategoriasCollection = subCategoriasCollection;
    }

    public Collection<FluxoCaixa> getFluxoCaixaCollection() {
        return fluxoCaixaCollection;
    }

    public void setFluxoCaixaCollection(Collection<FluxoCaixa> fluxoCaixaCollection) {
        this.fluxoCaixaCollection = fluxoCaixaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriasContas)) {
            return false;
        }
        CategoriasContas other = (CategoriasContas) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descricao;
    }

    
}
