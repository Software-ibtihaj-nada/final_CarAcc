package car;
import java.util.*;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
    public static final Logger LOGGER = Logger.getLogger(Admin.class.getName());
    public static final Scanner SCANN = new Scanner(System.in);
    private static final String ENTER_CATEGORY_MESSAGE = "Enter name of category";
    private static final String TAB_SPACING = "\t\t\t";
    private static final String ERROR_PREFIX = "An error occurred: ";

    private  String scan ;
    private static Boolean checkprod = false;
    private static Boolean flaginsertP=false;
	private static Boolean flagdeleteP=false;


	private static Boolean flaginsertC=false;
	private static Boolean flagdeleteC=false;

	private Product product=new Product();
	private Installer installer;
	private Order order=new Order();
       public Admin() {
           // No actions are performed.
  
       }
       
    public boolean cheackCategory(String category) {
    	boolean flag=false;
    	  try {
   		   connection();
   		   String sql="Select * from category where category='" +category+"' ";
   		   stm=con.prepareStatement(sql);
   		   rs=stm.executeQuery();
   	if(rs.next()){
   		flag=true;
   	}
   	else {
   		flag=false;
   	}

   		   stm.close();
   		   rs.close();
   	   }
   	   catch(Exception e) {
           LOGGER.severe(ERROR_PREFIX + e.getMessage());
   		 }
    	  return flag;
    }


	private void connection() throws ClassNotFoundException, SQLException {
		String password = System.getProperty("database.password");
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root",password);
	}
   
    public void  addCategory(String category){
    	   try {
    		   connection();
    		   String sql="INSERT INTO Category (category) values(?)";
    		   stm=con.prepareStatement(sql);

    		   stm.setString(1,category);

    		   int num=stm.executeUpdate();
    		   setFlaginsertC(num != 0);
    		   stm.close();
    	   }
    	   catch(Exception e) {
    	        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
    	   } 
       }
       public void deleteCategory(String categoryy){
    	   try {
    		   connection();
    		   String sql="Delete from Category where category='" +categoryy+"' ";
    		   stm=con.prepareStatement(sql);
    		   int num =stm.executeUpdate();
    		   setFlagdeleteC(num != 0);
    		   stm.close();

    	   }
    	   catch(Exception e) {
    	        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
    	   }  

       }

       public void deleteProductCategory(String categoryy) {
    	   try {
    		   connection();
    		   String sql="Delete from product where category='" +categoryy+"' ";
    		   stm=con.prepareStatement(sql);
    		   stm.executeUpdate();

    		   stm.close();

    	   }
    	   catch(Exception e) {
    	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
    	   }    
       }
     
     

       public void checkProduct(String name) {
    	   try {
    		   connection();
    		   String sql="Select name from product where name='" +name+"' ";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   if (rs.next()) {
    			   setCheckprod(true);
    		   }

    		   stm.close();
    		   rs.close();
    	   }
    	   catch(Exception e) {
    	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
    	   }

       }


       
      

     
 public List<Customer> veiwCustomerAccount() {
	 ArrayList<Customer>customer=new ArrayList<>();
    	   try {
    		   connection();
    		   String sql="Select * from users where user_type='customer'";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();

    		   while (rs.next()) {
    			   Customer costomers=new Customer(); 
    			   int id=rs.getInt("id");
    			   String name=rs.getString("name");
    			   String email=rs.getString("email");
    			   costomers.setid(id);
    			   costomers.setName(name);
    			   costomers.setEmail(email);
    			   customer.add(costomers);
    		   }

    		   stm.close();
    		   rs.close();
    	   }
    	   catch(Exception e) {
    	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
    	   }
    	   return customer;
       }
       
       
       public void reportAdmin() {
    	   int price=0;
    	  
    	   LOGGER.info("Product Name" + "\t\t" + "Product Quantity" + "\t" + "Product Price" + "\n");
    	   try {
    		   connection();
    		   String sql="Select*from orders where Buy=true";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   while (rs.next()) {
    			   LOGGER.info(String.format("%s\t%d\t%d$", rs.getString("productname"), rs.getInt("productquantity"), rs.getInt("productprice")));

    			   price+=rs.getInt("productprice");
    		   }
    		   
    		   LOGGER.info(String.format("Total income=%d$", price));
    		   rs.close();
    		   stm.close();
    	   }
    	   catch(Exception e) {
    	        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
    	   }
    	   
    	   
    	   
       }
      
       public static Boolean getCheckprod() {
           return checkprod;
       }

    
       public static void setCheckprod(Boolean value) {
           checkprod = value;
       }
       
       public static Boolean getFlaginsertP() {
           return flaginsertP;
       }

    
       public static void setFlaginsertP(Boolean value) {
    	   flaginsertP = value;
       }
       
       public static Boolean getFlagdeleteP() {
           return flagdeleteP;
       }

    
       public static void setFlagdeleteP(Boolean value) {
    	   flagdeleteP = value;
       }
       
       public static Boolean getFlaginsertC() {
           return flaginsertC;
       }

    
       public static void setFlaginsertC(Boolean value) {
    	   flaginsertC = value;
       }
       
       public static Boolean getFlagdeleteC() {
           return flagdeleteC;
       }

    
       public static void setFlagdeleteC(Boolean value) {
    	   flagdeleteC = value;
       }
}