package szbd.projekt.projektbazy.rentalElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import szbd.projekt.projektbazy.moviesWarehouse.MoviesWarehouse;
import szbd.projekt.projektbazy.rentals.Rentals;

import java.util.List;
import java.util.Optional;

@RestController
public class RentalElementController {

    @Autowired
    RentalElementService rentalElementService;
    @Autowired
    RentalElementRepository rentalElementRepository;

    @RequestMapping(method = RequestMethod.GET, value = "rentalElement/all")
    public List<RentalElement> getAllRentalElements() {

        return rentalElementService.getAllRentalElements();
    }
    @RequestMapping(method = RequestMethod.GET, value = "client/{idClient}/rentalElement")
    public List<RentalElement> getAllRentalElementsByClient(@PathVariable Integer idClient) {

        return rentalElementRepository.getAllRentalElementsByClient(idClient);
    }


    @RequestMapping(method= RequestMethod.GET, value = "/rentalElement/{idMoviesWarehouse}/{idRental}")
    public Optional<RentalElement> getRentalElement(@PathVariable Integer idMoviesWarehouse,
                                                    @PathVariable Integer idRental) {

        return rentalElementService.getRentalElement(new RentalElementId(idRental, idMoviesWarehouse));
    }

    //WYPOZYCZENIE
    //TODO
    //sprawdzic zeby bylo > 0 //chyba ze na froncie
    @RequestMapping(method = RequestMethod.POST, value = "rentalElement/{idMoviesWarehouse}/{idRental}/{amount}")
    public void addRentalElement(@RequestBody RentalElement rentalElement, @PathVariable Integer idMoviesWarehouse,
                                 @PathVariable Integer idRental, @PathVariable Integer amount) {

        rentalElement.setMoviesWarehouse(new MoviesWarehouse(idMoviesWarehouse, 0, 0, 0, 0));
        rentalElement.setRental(new Rentals(idRental, null, null, 0, 0, false));
        rentalElementService.addRentalElement(rentalElement);
        rentalElementService.changeQuantity(idMoviesWarehouse, amount);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "rentalElement/{idMoviesWarehouse}/{idRental}")
    public void updateRentalElement(@RequestBody RentalElement rentalElement, @PathVariable Integer idMoviesWarehouse,
                                    @PathVariable Integer idRental) {

        rentalElementService.deleteRentalElement(new RentalElementId(idRental, idMoviesWarehouse));
        rentalElement.setMoviesWarehouse(new MoviesWarehouse(idMoviesWarehouse, 0, 0, 0, 0));
        rentalElement.setRental(new Rentals(idRental, null, null, 0, 0, false));
        rentalElementService.updateRentalElement(new RentalElementId(idRental, idMoviesWarehouse), rentalElement);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "rentalElement/{idMoviesWarehouse}/{idRental}")
    public void deleteRentalElement(@PathVariable Integer idMoviesWarehouse, @PathVariable Integer idRental) {

        rentalElementService.deleteRentalElement(new RentalElementId(idRental, idMoviesWarehouse));
    }




}
