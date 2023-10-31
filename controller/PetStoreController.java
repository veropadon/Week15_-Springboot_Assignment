package pet.store.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreCustomer.PetStoreEmployee;
import pet.store.service.PetStoreService;


@RestController
@RequestMapping("/pet_store")
@Slf4j


public class PetStoreController {
	
	
	@Autowired
	private PetStoreService petStoreService;
	
	
	
	
	
	@PostMapping("/pet_store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData savePetStore(
			@RequestBody PetStoreData petStoreData) {
		
		log.info("Created", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
			
			
	
	
	
	

	
	
	public ResponseEntity<PetStoreData> createOrUpdatePetStore(@RequestBody 
			PetStoreData petStoreData ) {
		log.info(" Request to save pet store: {} ", petStoreData);
		PetStoreData savePetStoreData = petStoreService.savePetStore(petStoreData);
		return new ResponseEntity<> (savePetStoreData, HttpStatus.CREATED);
		
	}
	
	
	

	@GetMapping("/{petStoreId}")
	public ResponseEntity<PetStoreData> getPetStoreById(@PathVariable Long petStoreId) {
	    PetStoreData petStoreData = petStoreService.getPetStoreById(petStoreId);
	    if (petStoreData == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    return new ResponseEntity<>(petStoreData, HttpStatus.OK);
	}

	
	@PutMapping("/{petStoreId}/employee")
	public PetStoreData updatePetStore (@PathVariable ("petStoreId") Long PetStoreId, @RequestBody PetStoreData petStoreData) {
		 petStoreData.setPetStoreId(PetStoreId);
	 log.info(" Updating PetStore {} :{} ", PetStoreId, petStoreData);   
	 return petStoreService.savePetStore(petStoreData);

		
	}

	 @PostMapping("/{petStoreId}/employee")
	    @ResponseStatus( code = HttpStatus.CREATED)
	 
	    public PetStoreEmployee addEmployee (
	            @PathVariable ("petStoreId") Long petStoreId,
	            @RequestBody PetStoreEmployee employee) {
		 
		 log.info("Adding employee to pet store with ID: {}", petStoreId);
		    return petStoreService.saveEmployee(petStoreId, employee);
		}

	 

	

	 @PostMapping("/{petStoreId}/customer")
	 @ResponseStatus(HttpStatus.CREATED)
	 public PetStoreCustomer addCustomer (@PathVariable("petStoreId") Long petStoreId, @RequestBody PetStoreCustomer customer) {
	     log.info("Adding customer to pet store with ID: {}", petStoreId);
	     return petStoreService.saveCustomer(petStoreId, customer);
	 }

	 
	 
	 @GetMapping
	 public List<PetStoreData> retrieveAllPetStores() {
		    return petStoreService.retrieveAllPetStores();
		}




    

    @DeleteMapping("/{id}")
    public Map<String, String> deletePetStoreById(@PathVariable("id") Long petStoreId) {
        // Logging the request
        System.out.println("Request to delete pet store with ID: " + petStoreId);

        // Service method call to delete the pet store
        petStoreService.deletePetStoreById(petStoreId);

        // Response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet Store with ID " + petStoreId + " was deleted successfully.");
        return response;
    }
}


	
	


