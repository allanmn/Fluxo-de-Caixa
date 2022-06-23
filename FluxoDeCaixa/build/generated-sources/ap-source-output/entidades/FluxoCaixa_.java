package entidades;

import entidades.CategoriasContas;
import entidades.SubCategorias;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-22T23:11:56", comments="EclipseLink-2.7.9.v20210604-rNA")
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