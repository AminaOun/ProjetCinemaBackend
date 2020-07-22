package org.sid.Entities;

import java.util.Date;
import java.util.Collection;

import org.springframework.data.rest.core.config.Projection;

@Projection(name ="p1",types = {org.sid.Entities.Projection.class} )
public interface ProjectionsSpring {
	
	public Long getId();
	public Date getDateProjection();
	public double getPrix();
	public Salle getSalle();
	public Film getFilm();
	public Collection<Ticket> getTickets();
	public Seance getSeance();

	

}
