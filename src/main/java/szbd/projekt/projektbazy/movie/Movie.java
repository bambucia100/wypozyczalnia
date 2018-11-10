package szbd.projekt.projektbazy.movie;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import szbd.projekt.projektbazy.genre.Genre;
@Entity
public class Movie {

	@Id
	@GeneratedValue(
	    strategy= GenerationType.AUTO, 
	    generator="native"
	)
	@GenericGenerator(
	    name = "native", 
	    strategy = "native"
	)
	@Column(name = "id_movie", unique = true)
	private int idMovie;
	@Column(name = "title")
	private String title;
	@Column(name = "length")
	private int length;
	@Column(name = "director")
	private String director;
	@Column(name = "rating", nullable = true)
	private double rating;
	@Column(name = "description", nullable = true)
	private String description;
	@ManyToOne
	@JoinColumn(name = "id_genre", nullable = false)
	private Genre genre;
	
	public Movie() {
		
	}
	
	public Movie(int idMovie, String title, int length, String director, double rating, String description, int idGenre) {
		super();
		this.idMovie = idMovie;
		this.title = title;
		this.length = length;
		this.director = director;
		this.rating = rating;
		this.description = description;
		this.genre = new Genre(idGenre, "", "");
		
	}
	
	public int getIdMovie() {
		return idMovie;
	}
	public void setIdMovie(int idMovie) {
		this.idMovie = idMovie;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	
	
}
