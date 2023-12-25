package car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	private static final Scanner SCANN = new Scanner(System.in);
	private String scan; 
private Login loggin=new Login();
private Product product=new Product();
private Admin admin=new Admin();
private Installer installer=new Installer();
private Order order=new Order();
private Customer customer=new Customer();

public static final String ADMIN_ROLE = "admin";
private static final String INSTALLER = "installer";
private static final String ERROR_PREFIX = "An error occurred: ";
private static final String ENTER_CATEGORY_MESSAGE = "Enter name of category";
private static final String TAB_SPACING = "\t\t\t";

private static Connection con=null;
private static PreparedStatement stm=null;
private static ResultSet rs=null;
	public void mainMenue() {
		LOGGER.info("Welcome to Carr Accessories company");
		 int x=0;
  	   while(x!=1) {
			LOGGER.info("Please choose between the specific users");
			LOGGER.info("1-Admin");
			LOGGER.info("2-Customer");
			LOGGER.info("3-Installer");
			LOGGER.info("4-exit");
			scan=SCANN.nextLine();

			if(scan.equalsIgnoreCase("1")) {
				start(ADMIN_ROLE);

			}

			else if(scan.equalsIgnoreCase("2")){
				start("customer");
			}

			else if(scan.equalsIgnoreCase("3")){
				start(INSTALLER);
			}
			else if(scan.equalsIgnoreCase("4")){
				LOGGER.info("you log out succesfully");
				 x=1;
			}
			else {
				LOGGER.info("please make sure to enter the right user");
                 
			}

		}
	}
	public void start(String usertype) {
		if(!loggin.getFlaglogin()) {
			if(!usertype.equalsIgnoreCase(ADMIN_ROLE)&&!usertype.equalsIgnoreCase(INSTALLER)){
				LOGGER.info("1- sign up");
		 	}
			LOGGER.info("2- login");
			LOGGER.info("3- go back");
			scan=SCANN.nextLine();
		}
		if(scan.equalsIgnoreCase("1")&&!usertype.equalsIgnoreCase(ADMIN_ROLE)&&!usertype.equalsIgnoreCase(INSTALLER)) {
			signup(usertype);
		}
		else if(scan.equalsIgnoreCase("2")) {
			LOGGER.info("to login please enter your email and password");
			LOGGER.info(" email: ");
			String email=SCANN.nextLine();
			LOGGER.info("password: ");
			String pass=SCANN.nextLine();

			logIn(usertype,email,pass);

		}
		else if(scan.equalsIgnoreCase("3")) {
			mainMenue();
		}

	}
	public void signup(String usertype) {
		LOGGER.info(" Enter your email :");
		String email=SCANN.nextLine();
		
        LOGGER.info(" Enter your username :");
		String username=SCANN.nextLine();

		LOGGER.info(" Enter your password :");
		String password=SCANN.nextLine();

		LOGGER.info(" Confirm your password :");
		String confirmPassword=SCANN.nextLine();
		
		regesterUser( email,username,password,confirmPassword,usertype);
		

	}
	public void logIn(String usertype,String email,String password) {

		if(!email.contains("@")||!email.contains(".")) {
			LOGGER.info("syntex error in email");
			loggin.setFlaglogin(true);
			start(usertype);
		}
		else {
			loggin.checkEmail(email,usertype);
			if(!loggin.getFlagemail()) {
				LOGGER.info("user email doesnt exist"); 
				loggin.setFlaglogin(true);
				start(usertype);

			}
			else{ 
				loggin.checkpassword(email,password,usertype);
				
				if(loggin.getFlagPass()) {
				

					if(usertype.equalsIgnoreCase(ADMIN_ROLE)) {
                     adminDashboard();
					}
					else if(usertype.equalsIgnoreCase("customer")) {
					customerDashboard(email);
					}
					else  {
					installerDashboard(email);
					}
				}
				else {
					LOGGER.info("you enter incorrect password"); 
					loggin.setFlaglogin(true);
					start(usertype);
				}
			}

		}
	}
public void regesterUser(String email,String username,String password,String confirmPassword,String usertype) {
		
		if(!email.contains("@")||!email.contains(".")) {
			LOGGER.info("syntex error in email");
			signup(usertype);
		}
		else {
			loggin.checkEmail(email,usertype);
			if(loggin.getFlagemail()) {
				LOGGER.info("this email already exist"); 
				signup(usertype);
			}
			else{ 
		
				if(checkName(username)) {
					
					loggin.setFlagName(true);
				if(confirmPassword.equals(password)) {
					loggin.setFlagConfPass(true);
					
					insertuser(email,username,password,usertype);
				LOGGER.info("you sign up sucessfulley");
				logIn(usertype,email,password);
				}
				else {
					LOGGER.info("your password doesnt match your confirm password");
					signup(usertype);
				}
				
					
				}
				else {
					LOGGER.info("your name should contain character ");
					signup(usertype);

				}
			}

		}

		
		
	}
public static boolean checkName(String name) {
	int count=0;
	for(int i=0;i<name.length();i++) {
		if(Character.isDigit(name.charAt(i))) {
			count++;
		}
	}
	return (count != name.length() && !Character.isDigit(name.charAt(0)));
}
public void insertuser(String email,String name,String password,String usertype) {
	try {
		loggin.connection();
		String sql="INSERT INTO users (name,email,password,user_type) values(?,?,?,?)";
		stm=con.prepareStatement(sql);
  stm.setString(1,name);
  stm.setString(2,email);
stm.setString(3, password);
stm.setString(4, usertype);
stm.executeUpdate();
stm.close();
	}
	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX + e.getMessage());
	}
}


private void printProduct(ArrayList<Product> prod) {
	for(int i=0;i<prod.size();i++) {
		LOGGER.info("id="+prod.get(i).getId()+"\t"+prod.get(i).getName()+"\t"+prod.get(i).getDescription()+"\t"+prod.get(i).getPrice()+"$"+"\t"+prod.get(i).getQuientity());
			}
}
public void addProduct() {
	   LOGGER.info("Enter category:");
	   String category=SCANN.nextLine();

	   LOGGER.info("Enter product name:");
	   String pname=SCANN.nextLine();

	   LOGGER.info("Enter product description:");
	   String pdescription=SCANN.nextLine();

	   LOGGER.info("Enter product price:");
	   String pprice=SCANN.nextLine();

	   LOGGER.info("Enter product quientity:");
	   String pquientity=SCANN.nextLine();

	   admin.checkProduct(pname);
	   if(!admin.getCheckprod()) { 
		   Product p=new Product( pname, pdescription, Integer.parseInt(pprice),Integer.parseInt(pquientity),category);
		   product.insertProduct(p);
		   if(admin.getFlaginsertP())
			   LOGGER.info("product added successfuly");

	   }
	   else {
		   LOGGER.info("this product already found");


	   }

}  
public void updateProduct() {

	   product.viewCategories();
	   
	   LOGGER.info("Please enter the name of category you want to update ");
	   String category=SCANN.nextLine();
	   ArrayList<Product>prod;
    
    prod= product.viewProduct(category);
    printProduct(prod);
    LOGGER.info("enter Id of product you wnat to update ");
	   String id=SCANN.nextLine();
	   LOGGER.info("1- update name of product");
	   LOGGER.info("2- update description of product");
	   LOGGER.info("3- update price of product");
	   LOGGER.info("4- update quantity of product");
	   LOGGER.info("5- Go back");
	   LOGGER.info("Choose that you want to update ");
	   String input=SCANN.nextLine();

	   if(input.equalsIgnoreCase("1")) {
		   LOGGER.info("enter new name of product  ");
	   String name=SCANN.nextLine();
	   product.updateProduct(id,"name",name);

	   }
	   else if(input.equalsIgnoreCase("2")) {
		   LOGGER.info("enter new description of product  ");
 	   String desc=SCANN.nextLine(); 
 	   product.updateProduct(id,"description",desc);
	   }
	   else if(input.equalsIgnoreCase("3")) {
		   LOGGER.info("enter new price of product  ");
 	   String p=SCANN.nextLine();
 	   product.updateProduct(id,"price",p);
	   }
	  
	   else if(input.equalsIgnoreCase("4")) {
		   LOGGER.info("enter new quantity of product  ");
		   String quantity=SCANN.nextLine();
 	   product.updateProduct(id,"quantity",quantity);
	   }
	   else if(input.equalsIgnoreCase("5")) {
 	    adminDashboard();

	   }
	   else {
		   LOGGER.info("enter wronge input ,please try again");
		   updateProduct();
		   
	   }
	  
}

public void deleteProduct() {

	   product.viewCategories();
	   LOGGER.info("Please enter the name of category you want to delete ");
	   String category=SCANN.nextLine();

ArrayList<Product>prod;
    
    prod= product.viewProduct(category);
    printProduct(prod);

    LOGGER.info("enter Id of product you wnat to delete ");
	   scan=SCANN.nextLine();  
	   product.removeProdct(Integer.parseInt(scan),category);

	   if(admin.getFlagdeleteP())  LOGGER.info("product delete successfuly");

	   else {LOGGER.info("you enter wrong product id");
	   
	   }

}
public void addInstaller() {
	   LOGGER.info("Enter first name installer");
	String fname=SCANN.nextLine();
	
	LOGGER.info("Enter last name installer");
	String lname=SCANN.nextLine();
	
	LOGGER.info("Enter email installer");
	String email=SCANN.nextLine();
	
	LOGGER.info("Enter password installer");
	String password=SCANN.nextLine();
	
	LOGGER.info("Enter phone number installer");
	String phone=SCANN.nextLine();
	
	
	if(!email.contains("@")||!email.contains(".")) {
		LOGGER.info("syntex error in email");
		addInstaller();
	}
	else {
		if(!(checkName(fname)&&checkName(lname))) {
			LOGGER.info("Name should start with character and contains character");
			addInstaller();
			
		}
		else {
			int count=0;
			for(int i=0;i<phone.length();i++) {
				if(Character.isDigit(phone.charAt(i))) {
					count++;
				}
			}
			if(count==phone.length()) {      				
				installer=new Installer(fname,lname,email,password,phone);
				if(installer.insertInstaller(installer)) {
					installer.insertInstallerUser(installer);
					LOGGER.info("Add intaller succseefully");
				}
				
				
			}
		}
		
	}
	
}
public void viewCustomerReviews() {
	   try {
		   loggin.connection();
		   String sql="Select*from product";
		   stm=con.prepareStatement(sql);
		   rs=stm.executeQuery();
		   while (rs.next()) {

			   String name = rs.getString("name");
			   String logMessagee = (name != null) ? String.format("%s%s", name, TAB_SPACING) : "";
			   LOGGER.info(logMessagee);


	            for (int i = 0; i < rs.getInt("evaluation"); i++) {
	            	LOGGER.info("* ");
	            }

	            int userEval = rs.getInt("userEval");
	            String logMessage = (userEval != 0) ? String.format("%s%d", TAB_SPACING, userEval) : "";
	            LOGGER.info(logMessage);
	            LOGGER.info("\n");
			   
		   }
		  
		   rs.close();
		   stm.close();
	   }
	   catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
	   }
	 
}
public void adminDashboard() {
	   int x=0;
	   while(x!=1) {
		   LOGGER.info("chose the fnction you want: ");
		   LOGGER.info("1 Veiw Product Category");
		   LOGGER.info("2 Add Category");
	   LOGGER.info("3 Delete Category");
	   LOGGER.info("4 Veiw product.");
	   LOGGER.info("5 add new product.");
	   LOGGER.info("6 Update product.");
	   LOGGER.info("7 Delete Product");
	   LOGGER.info("8 View customer Account .");
	   LOGGER.info("9 View all order.");
	   LOGGER.info("10 View installation Request  .");
	   LOGGER.info("11 Add new installer.");
	   LOGGER.info("12 Remove installer.");
	   LOGGER.info("13 Sales Report");
	   LOGGER.info("14 View customer reviews");
	   LOGGER.info("15 log out .");
	   
	   scan=SCANN.nextLine();

	   
	   switch(scan) {
	   case "1": product.viewCategories();
	    	      break;
	   case "2":LOGGER.info(ENTER_CATEGORY_MESSAGE);
    String category=SCANN.nextLine();
   if( !admin.cheackCategory(category)) {
	   admin.addCategory(category); 
	   if(admin.getFlaginsertC()) {
		LOGGER.info("Insert category succssesfuly");  
	          }
	          else {
	        	 LOGGER.info("Insert category unsuccssesfuly");  
	          } 
   }
   else {
 	  LOGGER.info("this category is already exist");  
   }
    break;
	   case "3":LOGGER.info(ENTER_CATEGORY_MESSAGE);
    String categoryy=SCANN.nextLine();
    admin.deleteCategory(categoryy); 
    admin.deleteProductCategory(categoryy);
		   if(admin.getFlagdeleteC()) {
			   LOGGER.info("delete category succssesfuly");  
	          }
	          else {
	        	  LOGGER.info("delete category unsuccssesfuly");  
	          }
    break;
	   case "4":  LOGGER.info(ENTER_CATEGORY_MESSAGE);
	              String cat=SCANN.nextLine();
	              ArrayList<Product>prod;
	             
	              prod= product.viewProduct(cat);
		printProduct(prod);

	              break;

	   case "5":addProduct();
    break;
	   case "6":updateProduct();
    break;
	   case "7":deleteProduct();
    break;
	   case "8":  List<Customer>cust;
               cust= admin.veiwCustomerAccount();
               for(int i=0;i<cust.size();i++) {
             	  LOGGER.info("id= "+cust.get(i).getid()+"\t"+cust.get(i).getemail()+"\t"+cust.get(i).getname());
               }
    break;
	   case "9":order.adminViewOrder();
    break;
	   case "10":installer=new Installer();
		   installer.veiwInstallationRequestAdmin();
    break;
	   case "11":addInstaller();
    break;
	   case "12":installer.viewInstallerAdmin();
	   LOGGER.info("Enter id of installer that you want to remove");
    String idInstaller=SCANN.nextLine();
    int id=Integer.parseInt(idInstaller);
    installer.removeInstaller(id);
    break;
	   case "13":admin.reportAdmin();
    break;
	   case "14":viewCustomerReviews();
    break;
	   case "15": x=1;
        break;
	    default: LOGGER.info("please chose one of the availabe choises");
	    adminDashboard();
	   }
	   }
}

public void viewBuy(String customername){
	LOGGER.info("1.add product to cart");
	LOGGER.info("2.Rate the products");
	LOGGER.info("3.Back to  products");
	scan=SCANN.nextLine();
	if(scan.equalsIgnoreCase("1")) {
		LOGGER.info("please enter id product");
		scan=SCANN.nextLine();
		int productId=Integer.parseInt(scan);
		LOGGER.info("enter quntity");
		scan=SCANN.nextLine();
		int quantity=Integer.parseInt(scan);

		customer.productAvailable(quantity,productId);
		if(customer.getIsAvaliable()) {
			int customerId=customer.getCustomerIdd(customername);
			String productname=product.getProductName(productId);
			int price=product.getProductPrice(productId);
			Order orderr=new Order(customername,customerId, productId,  productname,quantity,price);
			order.insertOrder(orderr);
			if(customer.getFinsertOrder()) {
				LOGGER.info("insert order succsessfully");
				int pQuantity=product.getProductQuantity(productId);
				pQuantity-=quantity;
				product.updateProductQuantity(productId,pQuantity);
			}
			else {
				LOGGER.info("insert order unsuccsessfully");	
			}
		}
		else {
			LOGGER.info("This product is not avaliable or quantity avaliable not enough");	

		}

	}
	else if(scan.equalsIgnoreCase("2")) {
		LOGGER.info("please enter id product");
		scan=SCANN.nextLine();
		int productId=Integer.parseInt(scan);
		LOGGER.info("please enter your evaluation for product between 1-5");
		scan=SCANN.nextLine();
		int eval=Integer.parseInt(scan);
		
		while(eval<1||eval>5) {
			LOGGER.info("please enter your evaluation for product between 1-5");
			scan=SCANN.nextLine();
			 eval=Integer.parseInt(scan);	
		}
		int oldeval=customer.oldEvalProduct(productId);
		int numberOfUser=customer.numberOfUserEval(productId);
		customer.updateUserEval(productId,(numberOfUser+1));
		if(oldeval!=0) {
			int avgEval=(eval+oldeval)/2;
			customer.setEval(productId,avgEval);	
		}
		else {
			customer.setEval(productId,eval);	
		}
	}
}
public void makeInstallation(String email) { 
	installer.viewInstaller();
	 LOGGER.info("Enter the id of installer you want ");
	 String installerId=SCANN.nextLine();
	 int instalerIdd=Integer.parseInt(installerId);
	 LOGGER.info("Enter the day of installation you want ");
	 String day=SCANN.nextLine();
	 
		LOGGER.info("Enter your request");
		String request=SCANN.nextLine();
		
		LOGGER.info("Enter your car model");
		String carModel=SCANN.nextLine();
		 
		LOGGER.info("Enter your phone number");
String phone=SCANN.nextLine();
customer.setphone(phone);
		LOGGER.info("Enter your address");
		LOGGER.info("Enter your city");
		String city=SCANN.nextLine();
		customer.setcity(city);
		
		LOGGER.info("Enter your street");
		 String street=SCANN.nextLine();
		 customer.setstreet(street);
		 String customername=customer.getCustomerName(email);
		 customer.setName(customername);
		 String installerName=installer.getInstallerName(instalerIdd);
customer.installationReq(carModel,request,installerName,day);
	 installer.editDay(day,instalerIdd,true);
	 
	 EMAIL emaill=new EMAIL();
		String body="Dear installer , \n you are have anew installation rwquest , please check your installation request table .";
     		
		String subject="Customer installation request";
		emaill.sendEmail("nadoosh.jamal.aj@gmail.com", subject, body);
}
public void search(String user) {
	LOGGER.info("1.Search by name.");
	LOGGER.info("2.Search by price.");
	LOGGER.info("3.Search by category.");
	scan=SCANN.nextLine();
	ArrayList<Product>prod=new ArrayList<>();

	if(scan.equalsIgnoreCase("1")) {
		LOGGER.info("enter name");
		scan=SCANN.nextLine();
		prod=product.searchByName(scan);
	
	}
	else if(scan.equalsIgnoreCase("2")) {
		LOGGER.info("enter price");
		scan=SCANN.nextLine();
		int price=Integer.parseInt(scan);
		prod=product.searchByPrice(price);
	}
	else if(scan.equalsIgnoreCase("3")) {
		LOGGER.info("enter category");
		scan=SCANN.nextLine();

		prod=product.searchByCategory(scan);
	}
	if(!customer.getFlagSearch()) {
		LOGGER.info("no product to display");
	}
	else {
		for(int i=0;i<prod.size();i++) {
	LOGGER.info("id="+prod.get(i).getId()+"\t"+prod.get(i).getName()+"\t"+prod.get(i).getDescription()+"\t"+prod.get(i).getPrice()+"$"+"\t"+prod.get(i).getCategory());
		}
		viewBuy(user);
	}
}
public void shoppingCart(String user) {
	LOGGER.info("choose betwen choices");
	LOGGER.info("1.Update Order");
	LOGGER.info("2.Delete Order");
	LOGGER.info("3.Confirm Order");
	LOGGER.info("4.Go back");
	scan=SCANN.nextLine();

	if(scan.equalsIgnoreCase("1")) {
		LOGGER.info("to update quintity the product in your order please enter id order ");
		scan=SCANN.nextLine();
		int orderid=Integer.parseInt(scan);

		LOGGER.info(" please enter new quantity ");
		scan=SCANN.nextLine();
		int quuantity=Integer.parseInt(scan);

		if(order.updateOrder(orderid,quuantity)) {
			LOGGER.info("update order succsessfuly");
		}
		else {
			LOGGER.info("update order unsuccsessfuly you enter incorrect id ");
		}
	}

	else if(scan.equalsIgnoreCase("2")){
		LOGGER.info("to delete order please enter id order");
		scan=SCANN.nextLine();
		int idd=Integer.parseInt(scan);
		order.deleteOrder(idd);
		if(customer.getFlagDeleteO()) {
			LOGGER.info("Delete Order successfuly");	
		}
		else {
			LOGGER.info("Delete Order unsuccessfuly, incorrect order Id");	
		}
	}
	else if(scan.equalsIgnoreCase("3")){
		confirmOrder(user);
		
	}
	

}
public void editCustomerProfile(String user){
	LOGGER.info("1.edit your name");
	LOGGER.info("2.edit your email");
	LOGGER.info("3.edit your password");
	String edit=SCANN.nextLine();
	if(edit.equalsIgnoreCase("1")) {
		LOGGER.info("enter your new name");
		String ename=SCANN.nextLine();
		customer.editName(user,ename);
	}
	else if(edit.equalsIgnoreCase("2")) {
		LOGGER.info("enter your new email");
		String eemail=SCANN.nextLine();
		if(eemail.contains("@")||eemail.contains(".")) {
			customer.editEmail(user,eemail);
		}
	}
	else if(edit.equalsIgnoreCase("3")) {
		LOGGER.info("enter your old password");
		String oldPass=SCANN.nextLine();
		LOGGER.info("enter your new password");
		String epassword=SCANN.nextLine();
		String oldpassword=customer.getCustomerPassword(user);
		if(oldPass.equals(oldpassword)){
			customer.editPassword(user,epassword);
		}
		else {
			LOGGER.info("enter wronge old password");
		}
	}
}
public void confirmOrder(String customername) {
	LOGGER.info("Enter your address");
	LOGGER.info("Enter your city");
	scan=SCANN.nextLine();
	String cityy=scan;

	LOGGER.info("Enter your steet");
	String streett=SCANN.nextLine();

	LOGGER.info("Enter your phone number");
	String phoneNumber=SCANN.nextLine();
	int count=0;
	for(int i=0;i<phoneNumber.length();i++) {
		if(Character.isDigit(phoneNumber.charAt(i))) {
			count++;
		}
	}
	if(count==phoneNumber.length()) {
		customer.insertConfirmOrder(customername,cityy,streett,phoneNumber);
		EMAIL email=new EMAIL();
		String body="Dear user , \n your order is ready, please pick it up from the company's delivery service ."
        		+ "\n Please contact the owner of this number: 0599516693 in case the delivery is delayed or there is an error in the order."
        		+ " \n Thank you for dealing with our company for Car Accessories.";
		String subject="Customer Order";
		email.sendEmail("ibtihajsami9@gmail.com", subject, body);
		LOGGER.info("Email sent successfully!");

	}
	else {
		LOGGER.info("should all phoneNumber is digit");
	}
}
public void customerDashboard(String user) {
	int x=0; 
	while(x!=1) {
		LOGGER.info("Welcome, CUSTOMER!");
		LOGGER.info("Please choose you want need.");
		LOGGER.info("1.View category.");
		LOGGER.info("2.View product.");
		LOGGER.info("3.Make Installation request.");
		LOGGER.info("4.View Installation request.");
		LOGGER.info("5.Search.");
		LOGGER.info("6.View Shopping cart");
		LOGGER.info("7.Edit your profile");
		LOGGER.info("8.Log OUT");

		String input=SCANN.nextLine();
		if(input.equalsIgnoreCase("1")) {
			product.viewCategories();
		}

		else if(input.equalsIgnoreCase("2")){
			LOGGER.info("Enter name of category");
              String category=SCANN.nextLine();
              ArrayList<Product>prod;
	             
              prod= product.viewProduct(category);
              String vailability;
	         for(int i=0;i<prod.size();i++) {
	        	 if(prod.get(i).getQuientity()>0) {
						vailability="avaliable";
					}
					else {
						vailability="not avaliable";
					}
	        	 if (prod != null && i < prod.size()) {
	        		    int productId = prod.get(i).getId();
	        		    String productName = prod.get(i).getName();
	        		    String productDescription = prod.get(i).getDescription();
	        		    int productPrice = prod.get(i).getPrice();
	        		    String availability = ""; 
	        		    int oldEval = customer.oldEvalProduct(productId);

	        		    LOGGER.info(String.format("id=%d\t%s\t%s\t%d$\t%s\t%d star",
	        		        productId,
	        		        (productName != null) ? productName : "",
	        		        (productDescription != null) ? productDescription : "",
	        		        productPrice,
	        		        availability,
	        		        oldEval
	        		    ));
	        		} else {
	        		    LOGGER.warning("Product list is null or index is out of bounds. Unable to log information.");
	        		}

	     			}
			viewBuy(user);
		}
		
		else if(input.equalsIgnoreCase("3")){
             makeInstallation(user);
		}
		else if(input.equalsIgnoreCase("4")){
			String name=customer.getCustomerName(user);
		
			installer.customerViewInstallation(name);
		}
		else if(input.equalsIgnoreCase("5")){
			search(user);
		}
		else if(input.equalsIgnoreCase("6")){
			order.viewOrder(user);
			shoppingCart(user);
		}
		else if(input.equalsIgnoreCase("7")){
	     editCustomerProfile(user);
		}
		else if(input.equalsIgnoreCase("8")){
			
			x=1;
		} 
		else {
			LOGGER.info("Invalid choice. Please enter 1, 2, 3,4,5,6 ,7or 8.");

		}

	}

}
public void installerDashboard(String email) {
	int x=0;
	while(x!=1) {
		LOGGER.info("Welcome, INSTALLER!");
		LOGGER.info("Please choose you want need.");
		LOGGER.info("1.View Instllation request.");
		LOGGER.info("2.Done Instllation request.");
		LOGGER.info("3.Change the installation day.");
		LOGGER.info("4.Log OUT");

		String input=SCANN.nextLine();
		if(input.equalsIgnoreCase("1")) {
			int id=installer.getInstallerId(email);
			String name=installer.getInstallerName(id);
			
			installer.viewInstallationReq(name);
		}

		else if(input.equalsIgnoreCase("2")){
			LOGGER.info("Enter the id of installation to make it done");
			String id=SCANN.nextLine();
			int installaionId=Integer.parseInt(id);
			int installerId=installer.getInstallerId(email);
			String day=installer.getInstallationDay(installaionId);
			installer.editDay(day,installerId,false);
			if(installer.removeInstallation(installaionId)) {
				LOGGER.info("Done installation");
				
			}
			else {
				LOGGER.info("you enter wrong id");
			}
		}
		
		else if(input.equalsIgnoreCase("3")){
			int idinstaller=installer.getInstallerId(email);
			String name=installer.getInstallerName(idinstaller);
			installer.viewInstallationReq(name);
			
			LOGGER.info("Enter the id of installation to change the day");
			String iddinstallation=SCANN.nextLine();
			int idinst=Integer.parseInt(iddinstallation);
			LOGGER.info("Enter the new day");
			String newday=SCANN.nextLine();
			String oldDay =installer.getInstallationDay(idinst);
			installer.editDay(oldDay, idinstaller, false);
			installer.editDay(newday, idinstaller, true);
			installer.updateDayforcustomer(idinst,newday);
			EMAIL emaill=new EMAIL();
			String body="Dear customer, \n We would like to inform you that there has been a change in the installation calculations, please check your account \n"
					+ "if there is any problem  please contact us of this number: 0599516693.";
         		
			String subject="Customer installation request";
			emaill.sendEmail("ibtihajsami9@gmail.com", subject, body);
			

		 
		} 
		else if(input.equalsIgnoreCase("4")){
			
			x=1;
		} 
		else {
			LOGGER.info("Invalid choice. Please enter 1, 2 or 3");

		}

	}

}
	public static void main(String[] args) {
Main m=new Main();
m.mainMenue();
	}

}
