package szbd.projekt.projektbazy.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import szbd.projekt.projektbazy.adress.Adress;
import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@RestController
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientRepository clientRepository;
	
	@RequestMapping(method=RequestMethod.GET,value="rentalOffice/clients")
	public List<Client> getAllClients() {
		
		return clientService.getAllClients();
	}

	@RequestMapping(method=RequestMethod.GET,value="rentalOffice/{idRentalOffice}/clients")
	public List<Client> getAllClientsByWarehouse(@PathVariable Integer idRentalOffice) {

		return clientRepository.getAllClientsByOffice(idRentalOffice);
	}


	@RequestMapping(method=RequestMethod.GET,value="rentalOffice/{idRentalOffice}/clients/{idClient}")
	public Optional<Client> getClient(@PathVariable Integer idClient) {
		
		return clientService.getClient(idClient);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="rentalOffice/{idRentalOffice}/clients/{idAdress}")
	public void getAllClients(@RequestBody Client client, @PathVariable Integer idRentalOffice,
			@PathVariable Integer idAdress) {
		
		client.setAdress(new Adress(idAdress, "", "", "", "", ""));
		client.setRentalOffice(new RentalOffice(idRentalOffice, "", 0));
		clientService.addClient(client);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/rentalOffice/{idRentalOffice}/clients/{idClient}/{idAdress}")
	public void updateClient(@RequestBody Client client, @PathVariable Integer idClient,
			@PathVariable Integer idRentalOffice, @PathVariable Integer idAdress) {

		client.setAdress(new Adress(idAdress, "", "", "", "", ""));
		client.setRentalOffice(new RentalOffice(idRentalOffice, "", 0));
		clientService.updateClient(idClient, client);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="rentalOffice/{idRentalOffice}/clients/{idClient}")
	public void deleteClient(@PathVariable Integer idClient) {
		
		clientService.deleteClient(idClient);
	}
	
}
