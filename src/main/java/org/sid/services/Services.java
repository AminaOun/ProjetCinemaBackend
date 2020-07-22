package org.sid.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.sid.Entities.Categorie;
import org.sid.Entities.Cinema;
import org.sid.Entities.Film;
import org.sid.Entities.Place;
import org.sid.Entities.Projection;
import org.sid.Entities.Salle;
import org.sid.Entities.Seance;
import org.sid.Entities.Ticket;
import org.sid.Entities.Ville;

import org.sid.dao.CategorieRepository;
import org.sid.dao.CinemaRepository;
import org.sid.dao.FilmRepository;
import org.sid.dao.PlaceRepository;
import org.sid.dao.ProjectionRepository;
import org.sid.dao.SalleRepository;
import org.sid.dao.SeanceRepository;
import org.sid.dao.TicketRepository;
import org.sid.dao.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class Services implements ICinemaInitialisation{
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private TicketRepository ticketRepository;
   
	
	@Override
	public void initVilles() {
		Stream.of("meknes","rabat","tanger","casa","mohammedia","fes","taroudante","agadir","casablanca","rabat","kinetra","houseima","tanger","oujda","dakhla","tinghir").forEach(name->{
			Ville ville=new Ville();
			ville.setName(name);
			villeRepository.save(ville);
		});
		
	}
	
	@Override
	public void initCinema() {
		
		villeRepository.findAll().forEach(ville->{
			Stream.of("CinemaRrif","imax","megarama","Andalous").forEach(name->{
				Cinema cinema=new Cinema();
				cinema.setName(name);
				cinema.setNombreSalles(7);
				cinema.setVille(ville);
				
				cinemaRepository.save(cinema);
			});
		});
			
	
		
		
	}

	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(cinema->{
			for(int i=1;i<cinema.getNombreSalles();i++) {
				Salle salle=new Salle();
				salle.setName("Salle "+i);
				salle.setCinema(cinema);
				salle.setNombrePlace(50);
				salleRepository.save(salle);
				
			}
		});
		
		
	}

	@Override
	public void initSeances() {
		DateFormat dateFormat=new SimpleDateFormat("HH:mm");
		Stream.of("12:00","13:00","17:00","18:00").forEach(d->{
			Seance seance=new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(d));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		});
		
		
	}

	@Override
	public void initFilms() {
		double[] durees= new double[] {1,2.5,2,3,4,1};
		List<Categorie>  categories=categorieRepository.findAll();
			Stream.of("COMMENT-JE-SUIS-DEVENU-SUPER-HÉROS-1","arton35617","les-films-du-24","media","1552547178_captain marvel 3").forEach(titre->{
				Film film=new Film();
				film.setTitre(titre);
				film.setDuree(durees[new Random().nextInt(durees.length)]);
				film.setPhoto(titre+".jpg");
				film.setCategorie(categories.get(new Random().nextInt(categories.size())));
				filmRepository.save(film);
				
				
			});
		
	}

	@Override
	public void initProjections() {
		double[] prix=new double[] {10,20,30,40};
		List<Film> films=filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					int index=new Random().nextInt(films.size());
					Film film=films.get(index);
						seanceRepository.findAll().forEach(seance->{
							Projection proj=new Projection();
							proj.setDateProjection(new Date());
							proj.setPrix(prix[new Random().nextInt(prix.length)]);
							proj.setSalle(salle);
							proj.setFilm(film);
							proj.setSeance(seance);
							projectionRepository.save(proj);
						});
					
				});
			});
			
		});
		
	}

	@Override
	public void initTickets() {
		// TODO Auto-generated method stub
		projectionRepository.findAll().forEach(p->{
			p.getSalle().getPlaces().forEach(place->{
				Ticket tc= new Ticket();
				tc.setPlace(place);
				tc.setPrix(p.getPrix());
				tc.setProjection(p);
				tc.setReserve(false);
				ticketRepository.save(tc);
			});
		});
		
	}

	@Override
	public void initCategories() {
		Stream.of("Action","Drama","Romance","Aventure","Horror").forEach(name->{
			Categorie categorie=new Categorie();
			categorie.setName(name);
			categorieRepository.save(categorie);
		});
		
	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle->{
			for(int i=1;i<salle.getNombrePlace();i++) {
				Place place=new Place();
				place.setSalle(salle);
				place.setNumero(i);
				placeRepository.save(place);
			}
		});
		
	}
	

}
