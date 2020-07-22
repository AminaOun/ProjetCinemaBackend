package org.sid.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.sid.Entities.Film;
import org.sid.Entities.Ticket;
import org.sid.dao.FilmRepository;
import org.sid.dao.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

//creer api rest
@RestController
@CrossOrigin("*")
public class ControllerCinema {
	
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private FilmRepository filmRepository;
	
	@GetMapping(path = "/images/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] images(@PathVariable (name = "id") Long id) throws Exception {
		Film f=filmRepository.findById(id).get();
		String photo=f.getPhoto();
		File file=new File(System.getProperty("user.home")+"/images/"+photo);
		Path path=Paths.get(file.toURI());
		return Files.readAllBytes(path);
	}
	
	@PostMapping(path ="/payement")
	@Transactional 
	public List<Ticket> payement(@RequestBody TicketForm ticketForm) {
		List<Ticket> tickets=new ArrayList<>();
		ticketForm.getTickets().forEach(id->{
			Ticket ticket=ticketRepository.findById(id).get();
			ticket.setNomClient(ticketForm.getNomClient());
			ticket.setReserve(true);
			ticket.setCodePayement(ticketForm.getCodePayement());
			ticketRepository.save(ticket);
			tickets.add(ticket);
		});
		return tickets;
	}
	
}
@Data
class TicketForm{
	private int codePayement;
	private String nomClient;
	private List<Long> tickets=new ArrayList<>();
}
