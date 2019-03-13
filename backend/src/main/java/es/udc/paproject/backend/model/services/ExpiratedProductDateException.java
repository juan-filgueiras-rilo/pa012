package es.udc.paproject.backend.model.services;

@SuppressWarnings("serial")
public class ExpiratedProductDateException extends Exception {
	
	private long id;

	public ExpiratedProductDateException(long id) {
		super("Bid isn't possible because bid with ID " + id + " has already expired");
		this.id = id;
	}

	public long getShowId() {
		return id;
	}

	public void setShowId(long id) {
		this.id = id;
	}

}
