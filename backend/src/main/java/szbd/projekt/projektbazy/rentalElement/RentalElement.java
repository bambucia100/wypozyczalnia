package szbd.projekt.projektbazy.rentalElement;

import szbd.projekt.projektbazy.cast.CastId;
import szbd.projekt.projektbazy.moviesWarehouse.MoviesWarehouse;
import szbd.projekt.projektbazy.rentals.Rentals;

import javax.persistence.*;

@Entity
@Table(name="rental_element")
public class RentalElement {


    @PrePersist
    private void prePersiste() {
        if (getId() == null) {
            RentalElementId pk = new RentalElementId();
            pk.setRental(getRental());
            pk.setMoviesWarehouse(getMoviesWarehouse());
            setId(pk);
        }
    }

    @EmbeddedId
    private RentalElementId id;

    @Column(name = "amount_of_rentals", nullable = false)
    private int amountOfRentals;
    @ManyToOne
    @JoinColumn(name = "id_rental", nullable = false, insertable = false, updatable = false)
    private Rentals rental;
    @ManyToOne
    @JoinColumn(name = "id_warehouse", nullable = false, insertable = false, updatable = false)
    private MoviesWarehouse moviesWarehouse;

    public RentalElement() {

    }
    public RentalElement(int amountOfRentals, Integer idRental, Integer idMoviesWarehouse) {
        super();
        this.amountOfRentals = amountOfRentals;
        this.rental = new Rentals(idRental, null, null, 0, 0);
        this.moviesWarehouse = new MoviesWarehouse(idMoviesWarehouse, 0, 0, 0, 0);
    }

    public RentalElementId getId() {
        return id;
    }

    public void setId(RentalElementId id) {
        this.id = id;
    }

    public int getAmountOfRentals() {
        return amountOfRentals;
    }

    public void setAmountOfRentals(int amountOfRentals) {
        this.amountOfRentals = amountOfRentals;
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
