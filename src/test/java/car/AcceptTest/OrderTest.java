package car.AcceptTest;
import static org.junit.Assert.assertTrue;

import car.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OrderTest {
     Admin admin;
	 Order order;
	 Customer customer;
	 Product product=new Product();
	 int Orderid;
	 int quantity;
	 String email;
	 
	 @Given("admin login")
	 public void adminLogin() {
		 order=new Order();  
	
	 }

	 @When("admin want to view all order")
	 public void adminWantToViewAllOrder() {
			order.setTest(true); 
	 }

	 @Then("display all order successfully")
	 public void displayAllOrderSuccessfully() {
	     assertTrue(order.adminViewOrder());
	 }

	 
	 @Given("customer was loged in")
	 public void customerWasLogedIn() {
		order=new Order(); 
		customer=new Customer();
	}
	 
	 @Given("customer logedin")
	 public void customerLogedin() {
			order=new Order();   
	 }
	 @When("customer with name {string} want to view shoping cart")
	 public void customerWithNameWantToViewOrde(String user) {
		 order.setTest1(true);;
	     email=user;
	     
	 }

	 @Then("customer order will display")
	 public void customerOrderWillDisplay() {
	    assertTrue(order.viewOrder(email));
	 }

	 
	@When("customer with name {string} and customer id {string}")
	public void customerWithNameAndCustomerId(String Cname, String Cid) {
		order.setcustomername(Cname);
		order.setcustomerId(customer.getCustomerIdd(Cname));
	}
	@When("make order with product name {string} and product id '{int}'and product price '{int}'")
	public void makeOrderWithProductNameAndProductIdAndProductPrice(String Pname, Integer Pid, Integer Pprice) {
		order.setproductname(product.getProductName(Pid));
		order.setproductId(Pid);
		order.setproductprice(product.getProductPrice(Pid));
	}
	@When("make order with quantity {string}")
	public void makeOrderWithQuantity(String string) {
	   order.setproductquntity(Integer.parseInt(string));
	}
	@Then("make order successfully")
	public void makeOrderSuccessfully() {
		customer.productAvailable(order.getproductquntity(),order.getproductId());
		order.insertOrder(order);
		assertTrue(Customer.getFinsertOrder());
	}
	@When("customer name {string} and customer id {string}")
	public void customerNameAndCustomerId(String Cname, String Cid) {
		order.setcustomername(Cname);
		order.setcustomerId(Integer.parseInt(Cid));
	}
	@When("order with product name {string} and product id '{int}'and product price '{int}'")
	public void orderWithProductNameAndProductIdAndProductPrice(String pname, Integer PId, Integer Pprice) {
		int idd=PId;
		int pid=Pprice;
		order.setproductname(pname);
		order.setproductId(idd);
		order.setproductprice(pid);
	}
	@When("set order with quantity {string}")
	public void setOrderWithQuantity(String string) {
		quantity=Integer.parseInt(string);
   
	}
	@Then("update order successfully")
	public void updateOrderSuccessfully() {
		order.setproductquntity(2);
		Orderid=order.getOrderId(order) ;
		assertTrue(	order.updateOrder(Orderid, quantity));
	}
	
	@When("order with quantity {string}")
	public void orderWithQuantity(String quantity) {
		order.setproductquntity(Integer.parseInt(quantity));  
	}

	@Then("delete order successfully")
	public void deleteOrderSuccessfully() {
	   int id=order.getOrderId(order);
	   order.deleteOrder(id);
	   assertTrue(Customer.getFlagDeleteO());
	}

}
