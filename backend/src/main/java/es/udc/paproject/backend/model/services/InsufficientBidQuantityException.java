package es.udc.paproject.backend.model.services;

@SuppressWarnings("serial")
public class InsufficientBidQuantityException extends Exception {
	
	private double quantity; 
	
	public InsufficientBidQuantityException(double quantity) {
		super("Bid's price must be more than " + quantity + "€");
		this.quantity = quantity;
	}
	
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

}
