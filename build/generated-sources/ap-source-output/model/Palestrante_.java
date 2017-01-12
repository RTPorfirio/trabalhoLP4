package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Palestra;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-12T05:08:22")
@StaticMetamodel(Palestrante.class)
public class Palestrante_ { 

    public static volatile SingularAttribute<Palestrante, String> nome;
    public static volatile SingularAttribute<Palestrante, Integer> id;
    public static volatile CollectionAttribute<Palestrante, Palestra> palestraCollection;

}