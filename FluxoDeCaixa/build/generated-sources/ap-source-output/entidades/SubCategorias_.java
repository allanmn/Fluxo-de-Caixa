package Entidades;

import Entidades.CategoriasContas;
import Entidades.FluxoCaixa;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-25T16:25:50", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(SubCategorias.class)
public class SubCategorias_ { 

    public static volatile SingularAttribute<SubCategorias, Integer> codSub;
    public static volatile SingularAttribute<SubCategorias, CategoriasContas> codCat;
    public static volatile CollectionAttribute<SubCategorias, FluxoCaixa> fluxoCaixaCollection;
    public static volatile SingularAttribute<SubCategorias, String> descricao;

}