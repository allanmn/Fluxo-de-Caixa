package Entidades;

import Entidades.CategoriasContas;
import Entidades.SubCategorias;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-25T16:25:50", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(FluxoCaixa.class)
public class FluxoCaixa_ { 

    public static volatile SingularAttribute<FluxoCaixa, Date> dataOcorrencia;
    public static volatile SingularAttribute<FluxoCaixa, Integer> formaPagto;
    public static volatile SingularAttribute<FluxoCaixa, CategoriasContas> codCat;
    public static volatile SingularAttribute<FluxoCaixa, SubCategorias> codSubCat;
    public static volatile SingularAttribute<FluxoCaixa, Double> valor;
    public static volatile SingularAttribute<FluxoCaixa, Integer> id;
    public static volatile SingularAttribute<FluxoCaixa, String> descricao;

}