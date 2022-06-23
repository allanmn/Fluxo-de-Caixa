package entidades;

import entidades.FluxoCaixa;
import entidades.SubCategorias;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-22T21:08:24", comments="EclipseLink-2.7.9.v20210604-rNA")
@StaticMetamodel(CategoriasContas.class)
public class CategoriasContas_ { 

    public static volatile SingularAttribute<CategoriasContas, Integer> codigo;
    public static volatile SingularAttribute<CategoriasContas, Boolean> positiva;
    public static volatile CollectionAttribute<CategoriasContas, SubCategorias> subCategoriasCollection;
    public static volatile CollectionAttribute<CategoriasContas, FluxoCaixa> fluxoCaixaCollection;
    public static volatile SingularAttribute<CategoriasContas, String> descricao;

}