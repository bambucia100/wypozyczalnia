package szbd.projekt.projektbazy.adress;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class AdressController {

	@Autowired
	AdressService adressService;
	
	@RequestMapping(method=RequestMethod.GET, value = "/adress")
	public List<Adress> getAllAdress() {
		
		return adressService.getAllAdress();
	}
	
	@RequestMapping(method=RequestMethod.GET,value= {"rentalOffice/{idRentalOffice}/adress/{idAdress}"})
	public Optional<Adress> getAdress(@PathVariable Integer idAdress) {
		
		return adressService.getAdress(idAdress);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/adress")
	public void addAdress(@RequestBody Adress adress) {
		
		adressService.addAdress(adress);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="adress/{idAdress}")
	public void deleteAdress(@PathVariable Integer idAdress) {

		try {
			adressService.deleteAdress(idAdress);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element does not exist", ex);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element is a foreign key", ex);
		}

	}
	@RequestMapping(method=RequestMethod.PUT, value="adress/{idAdress}")
	public void updateAdress(@RequestBody Adress adress, @PathVariable Integer idAdress) {

		adressService.updateAdress(idAdress, adress);
	}
}
