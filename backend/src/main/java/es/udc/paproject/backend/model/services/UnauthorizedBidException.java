package es.udc.paproject.backend.model.services;

@SuppressWarnings("serial")
public class UnauthorizedBidException extends Exception {
	
	private long id;
	
	public UnauthorizedBidException(long id) {
		super("Bid rejected because product with id: " + id +" is yours.");
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

}
