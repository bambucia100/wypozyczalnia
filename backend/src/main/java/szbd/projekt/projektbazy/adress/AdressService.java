package szbd.projekt.projektbazy.adress;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdressService {

	@Autowired
	private AdressRepository adressRepository;
	
	public List<Adress> getAllAdress() {
		List<Adress> adress = new ArrayList<>();
		adressRepository.findAll()
		.forEach(adress::add);
		return adress;
	}
	
	public Optional<Adress> getAdress(Integer idAdress) {
		return adressRepository.findById(idAdress);
	}
	
	public void addAdress(Adress adress) {
		adressRepository.save(adress);
	}
	
	public void updateAdress(Integer idAdress, Adress adress) {
		adressRepository.save(adress);
	}
	
	public void deleteAdress(Integer idAdress) {

		try {
			adressRepository.deleteById(idAdress);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element does not exist", ex);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element is a foreign key in EMPLOYEE OR " +
					"CLIENT OR RENTAL OFFICE tables. Delete records in EMPLOYEE OR CLIENT OR RENTAL OFFICE first", ex);
		}
	}
}
