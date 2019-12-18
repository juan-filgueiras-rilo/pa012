package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.common.exceptions.InstanceException;

@SuppressWarnings("serial")
public class UnauthorizedBidException extends InstanceException {
	
	private Long id;
	
	public UnauthorizedBidException(Long id) {
		super("Bid rejected because product with id: " + id +" is yours.");
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}


}
