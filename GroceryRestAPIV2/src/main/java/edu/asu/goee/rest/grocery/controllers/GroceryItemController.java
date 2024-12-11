package edu.asu.goee.rest.grocery.controllers;

import java.net.URI;
import java.util.List;

import edu.asu.goee.rest.grocery.exceptions.NoSuchGroceryItemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.asu.goee.rest.grocery.model.GroceryItem;
import edu.asu.goee.rest.grocery.model.GroceryItem.GroceryType;
import edu.asu.goee.rest.grocery.services.GroceryServices;

@Slf4j
@RestController
@RequestMapping("/api/groceries")
public class GroceryItemController {
	private GroceryServices __groceryService = null;
	
	public GroceryItemController() {
		// get the service implementation we will use
		__groceryService = GroceryServices.getGroceryService();  // we should check if it is null
	}

	@GetMapping("/{id}")
	public GroceryItem getGroceryItem(@PathVariable("id") String id) throws NoSuchGroceryItemException {
		log.info("Received a request to fetch grocery with id [{}]", id);
		return __groceryService.findOne(id);
	}
	@GetMapping
	public List<GroceryItem> getGroceryItemsByType(@RequestParam(name = "category", required = false) GroceryType type) throws Exception {
		if (type == null) {
			log.info("Received request to get all grocery items");
			return __groceryService.findAll();
		} else {
			log.info("Received a request to get grocery by type [{}]", type);
			return __groceryService.findByCategory(type);
		}
	}
	
	@SuppressWarnings("unused")
	@PostMapping
	public ResponseEntity<GroceryItem> createGroceryItem(@RequestBody GroceryItem gItem) throws Exception {
		log.info("Received a request to add a grocery item");
		String rval = __groceryService.create(gItem);
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(gItem.getId())
                .toUri();
		HttpHeaders headers = new HttpHeaders();   // note this is the Spring type, not the java.net type
		headers.set(HttpHeaders.LOCATION, location.toString());
		// need to add rval to the Location header
		return new ResponseEntity<GroceryItem>(gItem, headers, HttpStatus.CREATED);
	}
	// Note that this PUT example is a little different than the other example we have - why?
	@PutMapping
	public ResponseEntity<GroceryItem> updateGroceryItem(@RequestBody GroceryItem gItem) throws Exception {
		log.info("Received a request to update a grocery item");
		boolean rval = __groceryService.update(gItem);
		// need to add id to the Location header if a create was done, and change the response status
		if (rval) { // it was an update
			return new ResponseEntity<GroceryItem>(gItem, HttpStatus.OK);
		} else {  // it was created
			URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(gItem.getId())
	                .toUri();
			HttpHeaders headers = new HttpHeaders();   // note this is the Spring type, not the java.net type
			headers.set(HttpHeaders.LOCATION, location.toString());
			return new ResponseEntity<GroceryItem>(gItem, headers, HttpStatus.CREATED);
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteGroceryItem(@PathVariable("id") String id) throws NoSuchGroceryItemException {
		log.info("Received a request to delete a grocery item with id [{}]", id);
		boolean rval = __groceryService.delete(id);
		// if true we return a 204, else we return a 404
		if (!rval) {
			// no such grocery item, return a 404
			return new ResponseEntity<String>("No such Grocery Item with id = " + id, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<String>(id, HttpStatus.NO_CONTENT);
		}
	}
	
	// Exception handling
	// I always include this one - there are almost always some HTTP methods our resource does not respond to
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exc) {
		log.error("Received error with message: [{}]", exc.getMessage());
		log.debug("Received error with stacktrace: ", exc);
		return new ResponseEntity<String>("Invalid method provided to GroceryItemController", HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(value = NoSuchGroceryItemException.class)
	public ResponseEntity<String> handleNoSuchItem(NoSuchGroceryItemException ex) {
		log.error("Received error with message: [{}]", ex.getMessage());
		log.debug("Received error with stacktrace: ", ex);
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	// This is the catch-all, returns our 500-level error
	@ExceptionHandler(value = java.lang.Throwable.class)
	public ResponseEntity<?> handleThrowable(java.lang.Throwable t) {
		log.error("Received error with message: [{}]", t.getMessage());
		log.debug("Received error with stacktrace: ", t);
		return new ResponseEntity<String>(t.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
