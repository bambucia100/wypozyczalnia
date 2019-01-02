package szbd.projekt.projektbazy.cast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CastService {

	@Autowired
	private CastRepository castRepository;
	

	public List<Cast> getAllCast() {
		List<Cast> cast = new ArrayList<>();
		castRepository.findAll()
		.forEach(cast::add);
		return cast;
	}
	
	
	public Optional<Cast> getCast(CastId idCast) {
		return castRepository.findById(idCast);
	}
	
	public void addCast(Cast cast) {
		castRepository.save(cast);
	}

	public void updateCast(CastId idCast, Cast cast) {
		castRepository.save(cast);
	}
	
	public void deleteCast(CastId idCast) {
		castRepository.deleteById(idCast);
	}

}
