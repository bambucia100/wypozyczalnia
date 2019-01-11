package szbd.projekt.projektbazy.genre;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Genre {

    @Id
    @Column(name = "genre_name", unique = true)
	private String genreName;
	@Column(name = "genre_description", unique = true)
	private String genreDescription;
	
	public Genre() {
		
	}
	public Genre(String genreName, String genreDescription) {
		super();
		this.genreName = genreName;
		this.genreDescription = genreDescription;
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
