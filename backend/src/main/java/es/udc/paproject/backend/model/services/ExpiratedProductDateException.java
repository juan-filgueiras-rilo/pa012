package es.udc.paproject.backend.model.services;

@SuppressWarnings("serial")
public class ExpiratedProductDateException extends Exception {
	
	private Long id;

	public ExpiratedProductDateException(Long id) {
		super("Bid isn't possible because bid with ID " + id + " has already expired");
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
