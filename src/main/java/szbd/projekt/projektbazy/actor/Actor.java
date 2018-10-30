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
	
	
}
