package car.AcceptTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import car.Installer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InstallerTest {
	Installer installer;
	int id;
	String day;
	String name;
	String email;
	String iddd;
	@Given("installer was log in")
	public void installerWasLogIn() {
		installer=new Installer();  
	}

	@When("installer with email {string} want to view his installation request")
	public void installerWithEmailWantToViewHisInstallationRequest(String emaill) {
		email=emaill;
	   id=installer.getInstallerId(email);
	   name=installer.getInstallerName(id);
	   installer.setTest(true);
	}

	@Then("installer can view his installation request")
	public void installerCanViewHisInstallationRequest() {
		assertNotNull(installer.getInstallerId(email));
		assertNotNull(installer.getInstallerName(id));
		assertTrue(installer.viewInstallationReq(name));		
	}
    

@Given("admin was log in")
public void adminWasLogIn() {
	installer=new Installer();  

}

@When("admin want to view installer")
public void adminWantToViewInstaller() {
	installer.setTest(true); 
}

@Then("all installer will print")
public void allInstallerWillPrint() {
	
	assertTrue(installer.viewInstallerAdmin());
}

@When("installer want to make installation request with id {string} done")
public void installerWantToMakeInstallationRequestWithIdDone(String idd) {
	iddd=idd;
  day=installer.getInstallationDay(Integer.parseInt(idd));
  installer.editDay(day, Integer.parseInt(iddd), false);
  this.id=Integer.parseInt(iddd);
} 

@Then("installation request done")
public void installationRequestDone() {
	installer.setTest(true);
	assertNotNull(installer.getInstallationDay(Integer.parseInt(iddd)));
	assertFalse(installer.editDay(day, Integer.parseInt(iddd), false));
	assertFalse(installer.removeInstallation(id));  
}	
}
