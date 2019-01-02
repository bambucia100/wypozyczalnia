package szbd.projekt.projektbazy.genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class GenreService {
	
	@Autowired
	private GenreRepository genreRepository;
	
	public List<Genre> getAllGenres() {
		List<Genre> genres = new ArrayList<>();
		genreRepository.findAll()
		.forEach(genres::add);
		return genres;
	}
	
	
	public Optional<Genre> getGenre(String IdGenre) {
		return genreRepository.findById(IdGenre);
	}
	
	public void addGenre(Genre genre) {
		genreRepository.save(genre);
	}

	public void updateGenre(String idGenre, Genre genre) {
		genreRepository.save(genre);
	}
	
	public void deleteGenre(String idGenre) {
		genreRepository.deleteById(idGenre);
	}
	
}
