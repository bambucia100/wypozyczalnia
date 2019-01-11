package szbd.projekt.projektbazy.rentalElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    @RequestMapping(method = RequestMethod.POST, value = "rentalElement/{idMoviesWarehouse}/{idRental}")
    public void addRentalElement(@RequestBody RentalElement rentalElement, @PathVariable Integer idMoviesWarehouse,
                                 @PathVariable Integer idRental) {

        Integer stockQuantity = rentalElementRepository.getQuantityByIdWarehouse(idMoviesWarehouse);

        if(rentalElement.getAmountOfRentals() > stockQuantity) {
                if(stockQuantity == 0)
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                            "Amount of rentals is bigger than quantity in warehouse. " +
                                    "This movie is not in the stock");
                if(stockQuantity == 1)
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                            "Amount of rentals is bigger than quantity in warehouse. " +
                                    "There is one movie in the stock");
                if(stockQuantity > 1)
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                            "Amount of rentals is bigger than quantity in warehouse. " +
                                    "There are " + stockQuantity + " movies in the stock");
        } else {
            rentalElement.setMoviesWarehouse(new MoviesWarehouse(idMoviesWarehouse, 0, 0, 0, 0));
            rentalElement.setRental(new Rentals(idRental, null, null, 0, 0));
            rentalElementService.addRentalElement(rentalElement);
            rentalElementService.changeQuantity(idMoviesWarehouse, rentalElement.getAmountOfRentals());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "rentalElement/{idMoviesWarehouse}/{idRental}")
    public void updateRentalElement(@RequestBody RentalElement rentalElement, @PathVariable Integer idMoviesWarehouse,
                                    @PathVariable Integer idRental) {

        int newQuantity = rentalElement.getAmountOfRentals();
        System.out.println(rentalElementRepository.getAmountOfRentals(idMoviesWarehouse, idRental));
        System.out.println(rentalElementRepository.getQuantityByIdWarehouse(idMoviesWarehouse));

        Integer dbQuantity = rentalElementRepository.getAmountOfRentals(idMoviesWarehouse, idRental);
        Integer stockQuantity = rentalElementRepository.getQuantityByIdWarehouse(idMoviesWarehouse);
        int quantity = newQuantity - dbQuantity;

        if(rentalElementRepository.getReturnDateByIdRental(idRental) != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rental Element is already returned");
        } else if(newQuantity < 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "New amount has to be bigger than 0");
        } else if(quantity > rentalElementRepository.getQuantityByIdWarehouse(idMoviesWarehouse)) {
            if(stockQuantity == 0)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Amount of rentals is bigger than quantity in warehouse. " +
                        "This movie is not in the stock");
            if(stockQuantity == 1)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Amount of rentals is bigger than quantity in warehouse. " +
                        "There is one movie in the stock");
            if(stockQuantity > 1)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Amount of rentals is bigger than quantity in warehouse. " +
                        "There are " + stockQuantity + " movies in the stock");
        } else {
            RentalElementId idRentalElement = new RentalElementId(idRental, idMoviesWarehouse);
            rentalElement.setId(idRentalElement);
            rentalElement.setMoviesWarehouse(new MoviesWarehouse(idMoviesWarehouse, 0, 0, 0, 0));
            rentalElement.setRental(new Rentals(idRental, null, null, 0, 0));
            rentalElementService.updateRentalElement(new RentalElementId(idRental, idMoviesWarehouse), rentalElement);
            rentalElementService.changeQuantity(idMoviesWarehouse, quantity);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "rentalElement/{idMoviesWarehouse}/{idRental}")
    public void deleteRentalElement(@PathVariable Integer idMoviesWarehouse, @PathVariable Integer idRental) {

        rentalElementService.deleteRentalElement(new RentalElementId(idRental, idMoviesWarehouse));
    }




}
