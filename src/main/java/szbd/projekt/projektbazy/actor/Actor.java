package szbd.projekt.projektbazy.actor;

import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Actor {

	@Id
	@GeneratedValue(
	    strategy= GenerationType.AUTO, 
	    generator="native"
	)
	@GenericGenerator(
	    name = "native", 
	    strategy = "native"
	)
	@Column(name = "id_actor", unique = true, nullable = false)
	private int idActor;
	@Column(name = "actor_first_name", unique = false, nullable = false)
	private String actorFirstName;
	@Column(name = "actor_last_name", unique = false, nullable = false)
	private String actorLastName;
	@Column(name = "date_of_birth", unique = false, nullable = false)
	private Date dateOfBirth;
	@Column(name = "rating", unique = true, scale = 2, nullable = true)
	private double rating;
	
	public Actor() {
		
	}
	public Actor(int idActor, String actorFirstName, String actorLastName, Date dateOfBirth, double rating) {
		this.idActor = idActor;
		this.actorFirstName = actorFirstName;
		this.actorLastName = actorLastName;
		this.dateOfBirth = dateOfBirth;
		this.rating = rating;
	}
	
	public int getIdActor() {
		return idActor;
	}
	public void setIdActor(int idActor) {
		this.idActor = idActor;
	}
	public String getActorFirstName() {
		return actorFirstName;
	}
	public void setActorFirstName(String actorFirstName) {
		this.actorFirstName = actorFirstName;
	}
	public String getActorLastName() {
		return actorLastName;
	}
	public void setActorLastName(String actorLastName) {
		this.actorLastName = actorLastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	
}
