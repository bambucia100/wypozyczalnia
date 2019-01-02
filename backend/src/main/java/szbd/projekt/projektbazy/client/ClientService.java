package szbd.projekt.projektbazy.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public List<Client> getAllClients() {
		List<Client> clients = new ArrayList<>();
		clientRepository.findAll()
		.forEach(clients::add);
		return clients;
	}
	
	public Optional<Client> getClient(Integer idClient) {
		return clientRepository.findById(idClient);
	}
	
	public void addClient(Client client) {
		clientRepository.save(client);
	}
	
	public void updateClient(Integer idClient, Client client ) {
		clientRepository.save(client);
	}
	
	public void deleteClient(Integer idClient) {
		clientRepository.deleteById(idClient);
	}
	
}
