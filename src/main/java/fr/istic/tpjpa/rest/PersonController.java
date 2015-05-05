package fr.istic.tpjpa.rest;

import com.sun.jersey.spi.resource.Singleton;
import fr.istic.tpjpa.domain.Person;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Singleton
@Path("/persons")
public class PersonController {

	private EntityManagerFactory factory;
	private EntityManager manager;
	private EntityTransaction tx;

	public PersonController() {
		factory = Persistence.createEntityManagerFactory("example");
		manager = factory.createEntityManager();
		tx = manager.getTransaction();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Person> getAction() {
		TypedQuery<Person> q = manager.createQuery(
				"select distinct p from Person p", Person.class);
		return q.getResultList();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getAction(@PathParam("id") Long id) {
		return manager.find(Person.class, id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Person postAction(Person person) {
		tx.begin();
		manager.persist(person);
		tx.commit();
		return person;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Person putAction(Person person) {
		tx.begin();
		manager.merge(person);
		tx.commit();
		return person;
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteAction(@PathParam("id") Long id) {
		Person person = manager.find(Person.class, id);
		tx.begin();
		manager.remove(person);
		tx.commit();
		return true;
	}
}