package es.udc.paproject.backend.model.services;

import java.time.LocalDateTime;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import es.udc.paproject.backend.model.entities.Bid.StateType;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;

public class BidInfo {

	private StateType state;
	private LocalDateTime date;

	public BidInfo(StateType state, LocalDateTime date) {
		
		this.state = state;
		this.date = date;
	}
	

	public StateType getState() {
		return state;
	}

	public void setState(StateType state) {
		this.state = state;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}


}
