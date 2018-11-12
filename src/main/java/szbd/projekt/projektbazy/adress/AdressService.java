package szbd.projekt.projektbazy.adress;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		adressRepository.deleteById(idAdress);
	}
}
