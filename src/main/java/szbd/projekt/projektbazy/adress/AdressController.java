package szbd.projekt.projektbazy.adress;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import szbd.projekt.projektbazy.client.Client;
import szbd.projekt.projektbazy.employee.Employee;
import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@RestController
public class AdressController {

	@Autowired
	AdressService adressService;
	
	@RequestMapping(method=RequestMethod.GET, value = "/adress")
	public List<Adress> getAllAdress() {
		
		return adressService.getAllAdress();
	}
	
	@RequestMapping(method=RequestMethod.GET,value= {"rentalOffice/{idRentalOffice}/adress/{idAdress}", 
			"/rentalOffice/{idRentalOffice}/emmployee/{idEmployee}/adres"})
	public Optional<Adress> getAdress(@PathVariable Integer idAdress) {
		
		return adressService.getAdress(idAdress);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/rentalOffice/{idRentalOffice}/adress")
	public void addAdress(@RequestBody Adress adress, @PathVariable Integer idRentalOffice) {
		
		adress.setRentalOffice(new RentalOffice(idRentalOffice, ""));
		adressService.addAdress(adress);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/rentalOffice/{idRentalOffice}/employee/{idEmployee}/adress")
	public void addAdressEmployee(@RequestBody Adress adress, @PathVariable Integer idEmployee) {
		
		adress.setEmployee(new Employee(idEmployee, "", "", "", "", "", 0));
		adressService.addAdress(adress);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="rentalOffice/{idRentalOffice}/client/{idClient}/adress")
	public void addAdressClient(@RequestBody Adress adress, @PathVariable Integer idClient) {
		
		adress.setClient(new Client(idClient, "", "", null, "", 0, 0));
		adressService.addAdress(adress);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="rentalOffice/{idRentalOffice}/adress/{idAdress}")
	public void deleteAdress(@PathVariable Integer idAdress) {
		
		adressService.deleteAdress(idAdress);
	}
}
