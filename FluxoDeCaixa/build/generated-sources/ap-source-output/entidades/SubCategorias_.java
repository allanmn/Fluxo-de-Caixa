package entidades;

import entidades.CategoriasContas;
import entidades.FluxoCaixa;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-22T21:08:24", comments="EclipseLink-2.7.9.v20210604-rNA")
@StaticMetamodel(SubCategorias.class)
public class SubCategorias_ { 

    public static volatile SingularAttribute<SubCategorias, Integer> codSub;
    public static volatile SingularAttribute<SubCategorias, CategoriasContas> codCat;
    public static volatile CollectionAttribute<SubCategorias, FluxoCaixa> fluxoCaixaCollection;
    public static volatile SingularAttribute<SubCategorias, String> descricao;

}