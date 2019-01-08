package szbd.projekt.projektbazy.rentalElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalElementService {

    @Autowired
    private RentalElementRepository rentalElementRepository;

    @PersistenceContext
    private EntityManager entityManager ;

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

    public void changeQuantity(Integer idMoviesWarehouse, Integer amount) {

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("change_quantity");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);

        query.setParameter(1, idMoviesWarehouse);
        query.setParameter(2, amount);

        query.execute();
    }

}
