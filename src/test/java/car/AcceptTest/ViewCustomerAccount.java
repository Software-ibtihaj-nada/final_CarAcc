package car.AcceptTest;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

import car.Admin;
import car.Customer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ViewCustomerAccount {
	Admin admin;
	List<Customer>cust;
@Given("admin is logedin")
public void adminIsLogedin() {
	admin=new Admin();
}

@When("admin want to view customer account")
public void adminWantToViewCustomerAccount() {
	cust=admin.veiwCustomerAccount();
}

@Then("customer account will display")
public void customerAccountWillDisplay() {
   assertNotNull(cust); 
}

}
