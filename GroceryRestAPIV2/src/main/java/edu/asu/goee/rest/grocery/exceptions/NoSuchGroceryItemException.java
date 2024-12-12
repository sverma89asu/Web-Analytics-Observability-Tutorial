package edu.asu.goee.rest.grocery.exceptions;

@SuppressWarnings("serial")
public class NoSuchGroceryItemException extends NoSuchEntityException {
	public NoSuchGroceryItemException(String id) {
		super("{ \"msg\": \"No Such Grocery Item found with id " + id + " }");
	}
}
