package car.AcceptTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import car.Customer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CustomerRateEval {
Customer customer;
int idd;
int intid;
int avarg;
String day;
String carmodel;
String req;
@Given("customerr was login")
public void customerrWasLogin() {
	customer=new Customer();  
}

@When("customer want to rate product with id {string} and set new evaluation for product {string}")
public void customerWantToRateProductWithIdAndSetNewEvaluationForProduct(String id, String eval) {
	idd=Integer.parseInt(id);
  int old=customer.oldEvalProduct(Integer.parseInt(id)) ;
  int num=customer.numberOfUserEval(Integer.parseInt(id));
  customer.updateUserEval(Integer.parseInt(id), num+1);
   avarg=Integer.parseInt(eval)+old/2;
  
} 

@Then("customer rate product succsessfully")
public void customerRateProductSuccsessfully() {
   assertTrue(customer.setEval(idd, avarg)) ;
}


@When("customer with email {string} choose  installer with id {string}")
public void customerWithEmailChooseInstallerWithId(String email, String id) {
    customer.setName(customer.getCustomerName(email));
    intid=Integer.parseInt(id);
    
}

@When("day is {string}")
public void dayIs(String d) {
    day=d;
	
}

@When("installation request is {string}")
public void installationRequestIs(String request) {
req=request;  
}

@When("carmodel is {string}")
public void carmodelIs(String carm) {
  carmodel=carm;  
}

@Then("customer  make installation succsessfully")
public void customerMakeInstallationSuccsessfully() {
 customer.setphone("0567571805");  
 customer.setcity("nablus");
 customer.setstreet("rafedia");
 customer.setTest(true);
 assertFalse(customer.installationReq(carmodel, req, carmodel, day));
}

}
