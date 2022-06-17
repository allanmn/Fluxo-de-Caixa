/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author allanneves
 */
@Entity
@Table(name = "SubCategorias")
@NamedQueries({
    @NamedQuery(name = "SubCategorias.findAll", query = "SELECT s FROM SubCategorias s"),
    @NamedQuery(name = "SubCategorias.findByCodSub", query = "SELECT s FROM SubCategorias s WHERE s.codSub = :codSub"),
    @NamedQuery(name = "SubCategorias.findByDescricao", query = "SELECT s FROM SubCategorias s WHERE s.descricao = :descricao")})
public class SubCategorias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codSub")
    private Integer codSub;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "codCat", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private CategoriasContas codCat;
    @OneToMany(mappedBy = "codSubCat")
    private Collection<FluxoCaixa> fluxoCaixaCollection;

    public SubCategorias() {
    }

    public SubCategorias(Integer codSub) {
        this.codSub = codSub;
    }

    public SubCategorias(Integer codSub, String descricao) {
        this.codSub = codSub;
        this.descricao = descricao;
    }

    public Integer getCodSub() {
        return codSub;
    }

    public void setCodSub(Integer codSub) {
        this.codSub = codSub;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CategoriasContas getCodCat() {
        return codCat;
    }

    public void setCodCat(CategoriasContas codCat) {
        this.codCat = codCat;
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
        hash += (codSub != null ? codSub.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubCategorias)) {
            return false;
        }
        SubCategorias other = (SubCategorias) object;
        if ((this.codSub == null && other.codSub != null) || (this.codSub != null && !this.codSub.equals(other.codSub))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SubCategorias[ codSub=" + codSub + " ]";
    }
    
}
