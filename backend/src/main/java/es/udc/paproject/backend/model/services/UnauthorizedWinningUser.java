package es.udc.paproject.backend.model.services;

@SuppressWarnings("serial")
public class UnauthorizedWinningUser extends Exception {

	private Long id;
	
	public UnauthorizedWinningUser(Long id) {
		super("Bid with id: " +id+ "can't be made because the user already has the winning bid");
		this.id = id;
	}
	

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
}
