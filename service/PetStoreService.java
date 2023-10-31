package pet.store.service;

import java.util.LinkedList;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreCustomer.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.dao.PetStoreRepository;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;
import pet.store.entity.PetStore.PetStoreConverter;

@Service
public class PetStoreService {
	

@Autowired
	    private PetStoreDao  petStoreDao;


public PetStoreData savePetStore(PetStoreData petStoreData) {
	PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId());
	copyPetStoreFields(petStore,petStoreData);
	PetStore  savedPetStore = petStoreDao.save(petStore);
	return new PetStoreData(savedPetStore);
	
}



private PetStore findOrCreatePetStore(Long petStoreId) {
	if(petStoreId == null) {
		return new PetStore();
	}else {
		
		return petStoreDao.findById(petStoreId).orElseThrow(() ->  new NoSuchElementException(
		"No PetStore found for ID:"  + petStoreId));
		
	}
}
	
	
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreName(petStoreData.getPetStoreName());
	    petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
	    petStore.setPetStoreCity(petStoreData.getPetStoreCity());
	    petStore.setPetStoreState(petStoreData.getPetStoreState());
	    petStore.setPetStoreZip(petStoreData.getPetStoreZip());
	    petStore.setPetStorePhone(petStoreData.getPetStorePhone());
		
		
	}


	@Autowired
    private EmployeeDao employeeDao;



  @Transactional(readOnly = false)
  
     
	public PetStoreEmployee saveEmployee( Long petStoreId, PetStoreEmployee employee) {
    	 PetStore petStore = findPetStoreById(petStoreId);
    	 Long employeeId = employee.getId();
       Employee  emp = findOrCreateEmployee(petStoreId, employeeId);
		
       copyEmployeeFields(emp, employee);
         
         emp.setPetStore(petStore);
         petStore.getEmployees().add(emp);
        Employee  savedEmployee = employeeDao.save(emp);
         
         return new PetStoreEmployee(savedEmployee);
     }

     private PetStore findPetStoreById(Long petStoreId) {
    	   if (petStoreId == null) {
    	        throw new IllegalArgumentException("PetStore ID cannot be null");
    	    }

    	    return petStoreDao.findById(petStoreId)
    	                      .orElseThrow(() -> new NoSuchElementException("No PetStore found for ID: " + petStoreId));
    

	}



	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
         if (employeeId == null) {
             return new Employee();
         } else {
             return findEmployeeById(petStoreId, employeeId);
             
         }
         
         }
	
	    
         public List<PetStoreData> retrieveAllPetStores() {
        	 List<PetStoreData> result = new LinkedList<>();
        	 List<PetStore> petStores = petStoreDao.findAll();
        	 
        	 for(PetStore petStore : petStores) {
        		 PetStoreData psd = new PetStoreData(petStore);
        		 psd.getCustomers().clear();
        		 psd.getEmployees().clear();
        		 
        		 result.add(psd);
        		 
        	 }
        	 
        	 return result;
         }
         
     
	
	

	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
	    Employee employee = employeeDao.findById(employeeId).orElseThrow(() -> new NoSuchElementException("No Employee found for ID:" 
	+ employeeId + "was not found"));
	    
	    if (employee.getPetStore() == null) {
	        throw new IllegalArgumentException("Employee not associated with any pet store.");
	    }

	    
	    if (employee.getPetStore().getPetStoreId().equals(petStoreId)) {
	        return employee;
	    } else {
	        throw new IllegalArgumentException("Employee does not belong to the given Pet Store with ID: " + petStoreId);
	    }
	}


     private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
         employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
         employee.setId(petStoreEmployee.getId());
         employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
         employee.setEmployeeEmail(petStoreEmployee.getEmployeeEmail());
         employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
         employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		
		
     }
     
     @Autowired
     private CustomerDao customerDao; 

     @Transactional(readOnly = false)
     public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer customerDto) {
         PetStore petStore = findPetStoreById(petStoreId);
         Customer  customer = findOrCreateCustomer(customerDto.getCustomerId());
         copyCustomerFields(customer, customerDto);

         customer.getPetStores().add(petStore);
         petStore.getCustomers().add(customer);

         Customer savedCustomer = customerDao.save(customer);

         return convertToPetStoreCustomer(savedCustomer);  // Assuming you have a conversion method
     }


     private PetStoreCustomer convertToPetStoreCustomer(Customer savedCustomer) {
    	    if (savedCustomer == null) {
    	        return null;
    	    }

    	    PetStoreCustomer petStoreCustomer = new PetStoreCustomer();
    	    
    	    petStoreCustomer.setCustomerId(savedCustomer.getId());
    	    petStoreCustomer.setCustomerFirstName(savedCustomer.getCustomerFirstName());
    	    petStoreCustomer.setCustomerLastName(savedCustomer.getCustomerLastName());
    	    petStoreCustomer.setCustomerEmail(savedCustomer.getCustomerEmail());
    	  

    	    return petStoreCustomer;
    	}

		



	private Customer findOrCreateCustomer(Long customerId) {
         if (customerId == null) {
             return new Customer();
         } else {
             return customerDao.findById(customerId).orElse(new Customer());
         }
     }

     private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
    	 customer.setId(petStoreCustomer.getCustomerId());
         customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
         customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
         customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
    
         
     }

   
     public  Customer findCustomerById(Long customerId, Long petStoreId) {
    	    Customer customer = customerDao.findById(customerId).orElseThrow(() -> new NoSuchElementException(
    	            " Customer with ID=" + customerId + " was not found"));

    	    boolean found = false;
    	    for (PetStore petStore : customer.getPetStores()) {
    	        if (petStore.getPetStoreId().equals(petStoreId)) {
    	            found = true;
    	            break;  
    	        }
    	    }

    	    if (!found) {
    	        throw new IllegalArgumentException(" Customer for pet store with ID =" + petStoreId + " was not found");
    	    } else {
    	        return customer;
    	    }
     }
     

     @Autowired
     private PetStoreRepository petStoreRepository;
     

     @Transactional
     public PetStoreData getPetStoreById(Long petStoreId) {
    	    return petStoreRepository.findById(petStoreId)
    	        .map(PetStoreConverter::convertToPetStoreData)
    	        .orElse(null);
    	}



	public void deletePetStoreById(Long petStoreId) {
		
	      
	        Optional<PetStore> optionalPetStore = petStoreRepository.findById(petStoreId);
	        
	        if (optionalPetStore.isPresent()) {
	            PetStore petStore = optionalPetStore.get();
	            
	            // Delete the pet store
	            petStoreRepository.delete(petStore);
	        } else {
	           
	            throw new EntityNotFoundException("Pet Store with ID " + petStoreId + " was not found.");
	        }
	    }
	}
		
	
     



    	






	




	
	



