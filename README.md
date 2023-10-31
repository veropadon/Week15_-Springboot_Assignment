# Week15_-Springboot_Assignment

Overview
This project is a continuation of the Spring Boot/Maven project developed in prior weeks where Spring JPA was used to create pet_store tables. Following that, we introduced a controller-layer class, a service-layer class, and a data-layer interface. Methods were also developed to enable the addition of rows in the pet_store table.

In this week's expansion, the application now supports the addition of employees and customers to the pet store and allows for more comprehensive CRUD operations on the pet_store table, including:

Retrieving summary data for all pet stores
Retrieving a pet store by its ID, complemented by associated employee and customer data
Deleting a pet store by its ID, ensuring that all linked employee and customer data is also removed
Objectives
The main objectives of this week's assignment are to:

Manage Spring JPA relationships, demonstrating the ability to add child rows in both one-to-many and many-to-many relationships.
Continue showcasing the prowess of Spring JPA by coding operations to retrieve and delete pet store records.
Output
The developed system facilitates:

Addition of an employee and a customer to a specific pet store
Retrieval of pet store data, inclusive of the related customer and employee details
Deletion of a pet store, ensuring that associated customer and employee data is also eradicated
Instructions for Week 15 Homework
Before attempting these exercises, please ensure you've reviewed and understood the content from Week 13 and Week 14 videos.

Add Store Employee
Here, you'll be coding to add an employee to an existing pet store. Follow these instructions:

Controller class:

Develop a method in the controller class to add an employee to the employee table.
Annotate with @PostMapping and @ResponseStatus.
Configure @PostMapping for HTTP POST requests to "/pet_store/{petStoreId}/employee".
Configure @ResponseStatus to return a 201 (Created) status code.
The method should be public, returning a PetStoreEmployee object.
Accept the pet store ID and the PetStoreEmployee object as parameters.
Log the request.
Call the saveEmployee method in the pet store service, returning the method call results.
DAO interface:

Create a new DAO interface named EmployeeDao.
Extend JpaRepository, with reference to an Employee object in the Generic.
Place the new DAO in the pet.store.dao package.
Service class:

Develop the saveEmployee method in the service class, analogous to the savePetStore method from the previous week.
