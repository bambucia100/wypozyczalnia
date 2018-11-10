package szbd.projekt.projektbazy.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@RestController
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(method=RequestMethod.GET,value="rentalOffice/{idRentalOffice}/clients")
	public List<Client> getAllClients(@PathVariable Integer idRentalOffice) {
		
		return clientService.getAllClients();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="rentalOffice/{idRentalOffice}/clients/{idClient}")
	public Optional<Client> getClient(@PathVariable Integer idClient) {
		
		return clientService.getClient(idClient);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="rentalOffice/{idRentalOffice}/clients")
	public void getAllClients(@RequestBody Client client, @PathVariable Integer idRentalOffice) {
		
		client.setRentalOffice(new RentalOffice(idRentalOffice, ""));
		clientService.addClient(client);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/rentalOffice/{idRentalOffice}/clients/{idClient}")
	public void updateClient(@RequestBody Client client, @PathVariable Integer idClient,
			@PathVariable Integer idRentalOffice) {
		
		client.setRentalOffice(new RentalOffice(idRentalOffice, ""));
		clientService.updateClient(idClient, client);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="rentalOffice/{idRentalOffice}/clients/{idClient}")
	public void deleteClient(@PathVariable Integer idClient) {
		
		clientService.deleteClient(idClient);
	}
	
}
