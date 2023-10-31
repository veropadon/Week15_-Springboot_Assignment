package pet.store.entity;

import java.util.HashSet;


import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pet.store.controller.model.PetStoreData;



@Data
@Entity



public class PetStore {
	
    
	 private String  petStoreName;
	 private String petStoreAddress;
	 private String  petStoreCity;
	 private String   petStoreState;
	 private String  petStoreZip;
	 private String  petStorePhone;
	
	
	
	 
	

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	    private Long PetStoreId;
       
       
       
       
       
       @EqualsAndHashCode.Exclude
       @ToString.Exclude
       @ManyToMany(cascade = CascadeType.PERSIST)
	    @JoinTable(name = "pet_store_customer", 
	               joinColumns =  @JoinColumn(name = "pet_store_id"), 
	               inverseJoinColumns = @JoinColumn(name = "customer_id"))
	    private Set<Customer> customers = new HashSet<>();
       
       @EqualsAndHashCode.Exclude
       @ToString.Exclude
	    @OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
	    private Set<Employee> employees = new HashSet<>();
       
       



public class PetStoreConverter {
    public static PetStoreData convertToPetStoreData(PetStore petStore) {
        return new PetStoreData(petStore);
    }
}
}

	


