package pet.store.controller.model;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.controller.model.PetStoreData.PetStoreCustomer.PetStoreEmployee;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;


@Data
@NoArgsConstructor

public class PetStoreData  {
	

		 private String  petStoreName;
		 private String petStoreAddress;
		 private String  petStoreCity;
		 private String   petStoreState;
		 private String  petStoreZip;
		 private String  petStorePhone;
		 private Long PetStoreId;
		 private Set<PetStoreCustomer> customers;
		 private Set<PetStoreEmployee> employees;
		 



  public PetStoreData(PetStore petStore) {
    this.PetStoreId = petStore.getPetStoreId();
    this.petStoreName = petStore.getPetStoreName();
    this.petStoreAddress = petStore.getPetStoreAddress();
    this.petStoreCity = petStore.getPetStoreCity();
    this.petStoreState = petStore.getPetStoreState();
    this.petStoreZip = petStore.getPetStoreZip();
    this.petStorePhone = petStore.getPetStorePhone();
  
    
    this.customers = petStore.getCustomers().stream().map(PetStoreCustomer::new).collect(Collectors.toSet());
    this.employees = petStore.getEmployees().stream().map(pet.store.controller.model.PetStoreData.PetStoreCustomer.PetStoreEmployee::new).collect(Collectors.toSet());
    

}



  @Data
  @NoArgsConstructor
 
  public static class PetStoreCustomer {
      private Long customerId;
      private String customerFirstName;
      private String customerLastName;
     private String customerEmail;
  

      public PetStoreCustomer(Customer customer) {
          this.customerId = customer.getId();
          this.customerFirstName = customer.getCustomerFirstName();
          this.customerLastName = customer.getCustomerLastName();
          this.customerEmail = customer.getCustomerEmail();
       
      }
  
      
      @Data
      @NoArgsConstructor
     
      public static class PetStoreEmployee {
          private Long id;
          private String employeeFirstName;
          private String employeeEmail;
          private String employeeJobTitle;
		private String employeeLastName;
		private String employeePhone;
		
		

          public PetStoreEmployee(Employee employee) {
              this.id = employee.getId();
              this.employeeFirstName = employee.getEmployeeFirstName();
              this.employeeLastName = employee.getEmployeeLastName();
              this.employeeEmail = employee.getEmployeeEmail();
              this.employeeJobTitle = employee.getEmployeeJobTitle();
              this.employeePhone = employee.getEmployeePhone();
          }
      }
  }
}
  


  
  
  