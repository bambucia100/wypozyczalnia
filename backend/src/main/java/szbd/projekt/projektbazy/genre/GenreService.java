package szbd.projekt.projektbazy.genre;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


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

		try {
			genreRepository.deleteById(idGenre);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element does not exist.", ex);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element is a foreign key in MOVIE " +
					"table. Delete records in MOVIE first.", ex);
		}
	}
	
}
