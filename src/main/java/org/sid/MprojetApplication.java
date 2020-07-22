package org.sid;

import org.sid.Entities.Film;
import org.sid.Entities.Salle;
import org.sid.Entities.Ticket;
import org.sid.services.ICinemaInitialisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class MprojetApplication implements CommandLineRunner{
	@Autowired
	private ICinemaInitialisation cinemaInitialisation;
	@Autowired
	private RepositoryRestConfiguration restConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(MprojetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		restConfiguration.exposeIdsFor(Film.class,Salle.class,Ticket.class);
		cinemaInitialisation.initCategories();
		cinemaInitialisation.initFilms();
		cinemaInitialisation.initVilles();
		cinemaInitialisation.initCinema();
		cinemaInitialisation.initSalles();
		cinemaInitialisation.initPlaces();
		cinemaInitialisation.initSeances();
		cinemaInitialisation.initProjections();
		cinemaInitialisation.initTickets();
		
	}

}
