package szbd.projekt.projektbazy.genre;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Genre {

	@Id
	@GeneratedValue(
	    strategy= GenerationType.AUTO, 
	    generator="native"
	)
	@GenericGenerator(
	    name = "native", 
	    strategy = "native"
	)
	@Column(name = "id_genres", unique = true)
	private int idGenre;
	@Column(name = "genre_name", unique = true)
	private String genreName;
	@Column(name = "genre_description", unique = true)
	private String genreDescription;
	
	private Genre() {
		
	}
	private Genre(int idGenre, String genreName, String genreDescription) {
		super();
		this.idGenre = idGenre;
		this.genreName = genreName;
		this.genreDescription = genreDescription;
	}
	public int getIdGenre() {
		return idGenre;
	}
	public void setIdGenre(int idGenre) {
		this.idGenre = idGenre;
	}
	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	public String getGenreDescription() {
		return genreDescription;
	}
	public void setGenreDescription(String genreDescription) {
		this.genreDescription = genreDescription;
	}
	
	
}
