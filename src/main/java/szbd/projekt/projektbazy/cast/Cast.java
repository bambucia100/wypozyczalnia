package szbd.projekt.projektbazy.cast;


import javax.persistence.*;

import szbd.projekt.projektbazy.actor.Actor;
import szbd.projekt.projektbazy.movie.Movie;


@Entity
public class Cast{

	
	@PrePersist
	   private void prePersiste() {
	       if (getId() == null) {
	           CastId pk = new CastId();
	           pk.setMovie(getMovie());
	           pk.setActor(getActor());
	           setId(pk);
	       }
	   }
	
	
	@EmbeddedId
	private CastId id;
	
	@ManyToOne
	@JoinColumn(name = "id_movie", nullable = false, insertable = false, updatable = false)
	private Movie movie;
	
	
	@ManyToOne
	@JoinColumn(name = "id_actor", nullable = false, insertable = false, updatable = false)
	private Actor actor;
	
	public Cast() {
		
	}
	public Cast(Integer idMovie, Integer idActor) {
		super();
		this.movie = new Movie(idMovie, "", 0, "", 0, "", 0);
		this.actor = new Actor(idActor, "", "", null, 0);
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
	public CastId getId() {
		return id;
	}
	public void setId(CastId id) {
		this.id = id;
	}
	
	
}
