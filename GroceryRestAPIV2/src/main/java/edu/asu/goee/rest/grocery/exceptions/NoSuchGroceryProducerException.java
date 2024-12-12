package edu.asu.goee.rest.grocery.exceptions;

@SuppressWarnings("serial")
public class NoSuchGroceryProducerException extends NoSuchEntityException {
	public NoSuchGroceryProducerException(String gid, String pid) {
		super("{ \"msg\": \"No Such Grocery-Producer relationship between grocery item " + gid + " and producer " + pid + " }");
	}
}
