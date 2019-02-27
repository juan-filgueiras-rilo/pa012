package es.udc.paproject.backend.model.services;

@SuppressWarnings("serial")
public class ExpiratedBidDateException extends Exception {
	
	private long id;

	public ExpiratedBidDateException(long id) {
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
