package car.AcceptTest;
import static org.junit.Assert.assertTrue;

import car.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class add_deletecategoryTest {
	Admin admin;
	boolean checkC=false;
	
	@Given("admin was loged in")
	public void adminWasLogedIn() {
		admin=new Admin();
		
	}
	

	@When("admin set new category {string}")
	public void adminSetNewCategory(String category) {
		checkC=admin.cheackCategory(category);
		if(!checkC) {
			admin.addCategory(category);	
		}
			}
	@Then("category added successfully")
	public void categoryAddedSuccessfully() {
	   if(admin.getFlaginsertC()==true) 
		   assertTrue(true);
	   else assertTrue(false); 
	   
	}
@Then("category already exist and doesnt add")
public void categoryAlreadyExistAndDoesntAdd() {
	if(checkC==true) 
		   assertTrue(true);
	   else assertTrue(false); 
	   
}
@When("admin set category to delete {string}")
public void adminSetCategoryToDelete(String category) {
    admin.deleteCategory(category);
    admin.deleteProductCategory(category);
}
@Then("delete category successfully")
public void deleteCategorySuccessfully() {
    if(admin.getFlagdeleteC()) 
    	  assertTrue(true);

    else assertTrue(false);
}








}

