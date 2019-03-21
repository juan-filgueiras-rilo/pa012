package es.udc.paproject.backend.model.services;

@SuppressWarnings("serial")
public class UnauthorizedBidException extends Exception {
	
	private Long id;
	
	public UnauthorizedBidException(Long id) {
		super("Bid rejected because product with id: " + id +" is yours.");
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

}
