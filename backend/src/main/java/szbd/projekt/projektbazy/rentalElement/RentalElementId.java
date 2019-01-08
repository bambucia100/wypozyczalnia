package szbd.projekt.projektbazy.rentalElement;

import szbd.projekt.projektbazy.moviesWarehouse.MoviesWarehouse;
import szbd.projekt.projektbazy.rentals.Rentals;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class RentalElementId implements Serializable {

    private static final long serialVersionUID    = 1L;

    @ManyToOne
    @JoinColumn(name = "id_rental", nullable = false, insertable = false, updatable = false)
    private Rentals rental;
    @ManyToOne
    @JoinColumn(name = "id_warehouse", nullable = false, insertable = false, updatable = false)
    private MoviesWarehouse moviesWarehouse;

    public RentalElementId() {

    }

    public  RentalElementId(int idRental, int idMoviesWarehouse) {
        super();
        this.rental = new Rentals(idRental, null, null, 0, 0, false);
        this.moviesWarehouse = new MoviesWarehouse(idMoviesWarehouse, 0, 0, 0, 0);
    }

    public Rentals getRental() {
        return rental;
    }

    public void setRental(Rentals rental) {
        this.rental = rental;
    }

    public MoviesWarehouse getMoviesWarehouse() {
        return moviesWarehouse;
    }

    public void setMoviesWarehouse(MoviesWarehouse moviesWarehouse) {
        this.moviesWarehouse = moviesWarehouse;
    }
}
