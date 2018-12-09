package szbd.projekt.projektbazy.rental_element;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class RentalElementId implements Serializable {

    private static final long serialVersionUID    = 1L;

    @ManyToOne
    @JoinColumn(name = "id_rental", , nullable = false, insertable = false, updatable = false)
    private Movie

}
