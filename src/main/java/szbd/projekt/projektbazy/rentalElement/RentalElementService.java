package szbd.projekt.projektbazy.rentalElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalElementService {

    @Autowired
    private RentalElementRepository rentalElementRepository;

    public List<RentalElement> getAllRentalElements() {
        List<RentalElement> rentalElement = new ArrayList<>();
        rentalElementRepository.findAll()
                .forEach(rentalElement::add);
        return rentalElement;
    }

    public Optional<RentalElement> getRentalElement(RentalElementId idRental) {
        return rentalElementRepository.findById(idRental);
    }

    public void addRentalElement(RentalElement rentalElement) {
        rentalElementRepository.save(rentalElement);
    }

    public void updateRentalElement(RentalElementId idRentalElement, RentalElement rentalElement) {
        rentalElementRepository.save(rentalElement);
    }

    public void deleteRentalElement(RentalElementId idRentalElement) {
        rentalElementRepository.deleteById(idRentalElement);
    }

}
