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
	
	@RequestMapping(method=RequestMethod.GET,value= {"rentalOffice/adress/{idAdress}"})
	public Optional<Adress> getAdress(@PathVariable Integer idAdress) {
		
		return adressService.getAdress(idAdress);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/adress")
	public Integer addAdress(@RequestBody Adress adress) {
		
		adressService.addAdress(adress);

		return adress.getIdAdress();
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="adress/{idAdress}")
	public void deleteAdress(@PathVariable Integer idAdress) {

			adressService.deleteAdress(idAdress);
	}

	@RequestMapping(method=RequestMethod.PUT, value="adress/{idAdress}")
	public void updateAdress(@RequestBody Adress adress, @PathVariable Integer idAdress) {

		adressService.updateAdress(idAdress, adress);
	}

}
