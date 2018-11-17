package szbd.projekt.projektbazy.cast;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import szbd.projekt.projektbazy.actor.Actor;
import szbd.projekt.projektbazy.movie.Movie;

@Embeddable
public class CastId  implements Serializable {
	

	private static final long   serialVersionUID    = 1L;
	
	
	@ManyToOne
	@JoinColumn(name = "id_movie", nullable = false, insertable = false, updatable = false)
	private Movie movie;
	
	
	@ManyToOne
	@JoinColumn(name = "id_actor", nullable = false, insertable = false, updatable = false)
	private Actor actor;

	public CastId() {
		
	}
	
	public CastId(Integer idMovie, Integer idActor) {
		super();
		this.movie = new Movie(idMovie, "", 0, "", 0, "", 0);
		this.actor = new Actor(idActor, "", "", null);
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
