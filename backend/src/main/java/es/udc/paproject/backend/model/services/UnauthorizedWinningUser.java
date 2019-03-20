package es.udc.paproject.backend.model.services;

@SuppressWarnings("serial")
public class UnauthorizedWinningUser extends Exception {

	private long id;
	
	public UnauthorizedWinningUser(long id) {
		super("Bid with id: " +id+ "can't be made because the user already has the winning bid");
		this.id = id;
	}
	

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
}
