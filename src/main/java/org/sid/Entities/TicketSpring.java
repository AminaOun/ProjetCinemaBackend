package org.sid.Entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "t1",types = Ticket.class)
public interface TicketSpring {
	
	public Long getId();
	public String getNomClient();
	public double getPrix();
	public Integer getCodePayement();
	public boolean getReserve();
	public Place getPlace();
	
}
