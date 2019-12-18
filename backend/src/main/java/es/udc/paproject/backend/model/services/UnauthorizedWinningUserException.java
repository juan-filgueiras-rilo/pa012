package es.udc.paproject.backend.model.services;

@SuppressWarnings("serial")
public class UnauthorizedWinningUserException extends Exception {

	private Long id;
	
	public UnauthorizedWinningUserException(Long id) {
		super("Bid with id: " + id + " can't be made because the user already has the winning bid");
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
}
