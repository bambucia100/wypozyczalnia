package szbd.projekt.projektbazy.cast;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import szbd.projekt.projektbazy.actor.Actor;
import szbd.projekt.projektbazy.movie.Movie;


@Entity
public class Cast {

	@Id
	@GeneratedValue(
		    strategy= GenerationType.AUTO, 
		    generator="native"
		)
		@GenericGenerator(
		    name = "native", 
		    strategy = "native"
		)
	@Column(name = "id_cast", nullable = false)
	private Integer idCast;
	@OneToOne
	@JoinColumn(name = "id_movie", nullable = false)
	private Movie movie;
	@ManyToOne
	@JoinColumn(name = "id_actor", nullable = false)
	private Actor actor;
	
	public Cast() {
		
	}
	public Cast(Integer idMovie, Integer idActor) {
		super();
		this.movie = new Movie(idMovie, "", 0, "", 0, "", 0);
		this.actor = new Actor(idActor, "", "", null, 0);
	}
	public Integer getIdCast() {
		return idCast;
	}
	public void setIdCast(Integer idCast) {
		this.idCast = idCast;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
	
}
