package car;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;
public class Customer {
	   public static final Logger LOGGER = Logger.getLogger(Customer.class.getName());
	    public static final Scanner SCANN = new Scanner(System.in);
	private  String scanN;
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
	private static boolean test=false;

	private static boolean isavaliable=false;
	private static boolean finsertorder=false;
	private static boolean flagSearch=false;
private static boolean flagdeleteO=false;
private Product product=new Product();
private Order order=new Order();
private Installer installer=new Installer();
private String customername;
private String phone;
private String city;
private String street;
private String customeremail;
private String password;
private int customerid;
private static final String SELECT_USERS_QUERY = "Select * from users where email='";
private static final String SELECT_PRODUCT_BY_ID_QUERY = "Select * from product where id='";
private static final String QUANTITY_LITERAL = "quantity";
private static final String ERROR_PREFIX = "An error occurred: ";

	public Customer() {

	}
	public void setName( String customername) {
		this.customername=customername;
	}
	public void setEmail( String customeremail) {
		this.customeremail=customeremail;
	}

	public void setPassword( String password) {
		this.password=password;
	}
	public void setid( int customerid) {
		this.customerid=customerid;
	}
	public void setphone( String phone) {
		this.phone=phone;
	}
	public void setcity( String city) {
		this.city=city;
	}
	public void setstreet( String street) {
		this.street=street;
	}
	public String getname() {
	return customername;
	}
	public String getemail() {
		return customeremail;
		}
	public String getpassword() {
		return password;
		}
	public String getphone() {
		return phone;
		}
	public String getcity() {
		return city;
		}
	public String getstreet() {
		return street;
		}
	public int getid() {
	return customerid;
	}
	
	

	public void viewCategoryProduct(String category) {

		try { 
			LOGGER.info("please choose the number of product you want.");
			connection();
			String sql="Select * from product where category='" +category+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			String vailability=null;
			while (rs.next()) {
				int stars=0;
				String id="id = "+rs.getInt("id");
				String name= rs.getString("name");
				String description= rs.getString("description");
				int price= rs.getInt("price");

				if(rs.getInt(QUANTITY_LITERAL)>0) {
					vailability="avaliable";
				}
				else {
					vailability="not avaliable";
				}

				if (rs != null) {
		
				    if (!rs.wasNull()) {
				        name = rs.getString(2);
				    }
			      if (!rs.wasNull()) {
				        description = rs.getString(3);
				    }

				    price = rs.getInt(4);
				    stars = rs.getInt(7);

				} else {
				    LOGGER.warning("Result set is null. Unable to log information.");
				}		}
			stm.close();
			rs.close();
		}

		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
		}

	}
	
	
	
	
public String getCustomerName(String email) {
	String name=null;
	try {
		connection();
		String sql=SELECT_USERS_QUERY +email+"' and user_type='customer' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		if(rs.next()) {
			name=rs.getString("name");
		}
		rs.close();
		stm.close();
	}
	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
	}
	return name ;
}
public boolean installationReq(String carmodel,String request,String installerNamme,String day) {
	int num=0;
	try {
		connection();
		String sql="INSERT INTO installation_req (customername,customerphone,customerreq,carmodel,city,street,day,installer_name) values(?,?,?,?,?,?,?,?)";
		stm=con.prepareStatement(sql);
if(!getTest()) {
				stm.setString(1,this.getname());
		    	stm.setString(2,this.getphone());
		    	stm.setString(3,request);
		    	stm.setString(4,carmodel);
		    	stm.setString(5,this.getcity()); 
		    	stm.setString(6,this.getstreet());
		    	stm.setString(7,day);
		    	stm.setString(8,installerNamme);
		 
		     num=stm.executeUpdate();
}
		stm.close();
	}
	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
	}
	
	return num>0;
	
}
	

	
	
	public int getCustomerIdd(String customeremail) {
		int id=0;
		try {
			connection();
			String sql=SELECT_USERS_QUERY +customeremail+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if(rs.next()) {
				id=rs.getInt("id");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
		}
		return id;
	}
	
	public void productAvailable(int quantity,int idproduct) { 

		try {

			connection();
			String sql=SELECT_PRODUCT_BY_ID_QUERY +idproduct+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			while (rs.next()) {

				setIsAvaliable(rs.getInt(QUANTITY_LITERAL) > 0 && rs.getInt(QUANTITY_LITERAL) >= quantity ? true : false);

			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
		}

	}
	public void insertConfirmOrder(String customername,String city,String street,String phoneNumber) {

		try {
			connection();
			String sql="Update orders set Buy=?,city=?,street=?,phoneNumber=? where customername='"+customername+"'";
			stm=con.prepareStatement(sql);

			stm.setBoolean(1,true);
			stm.setString(2, city);
			stm.setString(3,street);
			stm.setString(4, phoneNumber);

			stm.executeUpdate();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
		}

	}
	
	
	
	
	public boolean editName(String user,String ename){
		boolean flagN=false;
		try {
			connection();
			String sql="Update users set name=? where email='"+user+"'";
			stm=con.prepareStatement(sql);
			stm.setString(1, ename);
			int num=stm.executeUpdate();
			stm.close();
			if(num>0) {
				flagN=true;
			}
		}
		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
		}
		return flagN;
	}
	
	
	
	
	public boolean editEmail(String user,String eemail){
		 boolean flagE=false;
		try {
			connection();
			String sql="Update users set email=? where email='"+user+"'";
			stm=con.prepareStatement(sql);
			stm.setString(1, eemail);
			int num=stm.executeUpdate();
			stm.close();
			if(num>0) {
				flagE=true;
			}
		}
		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
		}
		return flagE;
	}
	
	
	public boolean editPassword(String user,String epassword){
		boolean flagP=false;
		try {
			connection();
			String sql="Update users set password=? where email='"+user+"'";
			stm=con.prepareStatement(sql);
			stm.setString(1, epassword);
			int num=stm.executeUpdate();
			stm.close();
			if(num>0) {
				flagP=true;
			}
		}
		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
		}
		return flagP;
	}
	
	
	private void connection() throws ClassNotFoundException, SQLException {
		String passwordd = System.getProperty("database.password");
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root",passwordd);
	}
	
	
	public String getCustomerPassword(String user) {
		String oldpass=null;
		try {
			connection();
			String sql=SELECT_USERS_QUERY +user+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if(rs.next()) {
				oldpass=rs.getString("password");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
		}
		return oldpass;
	}

	
    
    public int oldEvalProduct(int id) {
	int eval=0;
	try {
		connection();
		String sql=SELECT_PRODUCT_BY_ID_QUERY+id+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		if(rs.next()) {
			eval=rs.getInt("evaluation");
		}
		rs.close();
		stm.close();
	}
	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
	}
	return eval;
}

public boolean setEval(int id,int neweval)	{
	int num=0;
	try {
		connection();
		String sql="Update product set evaluation=? where id='"+id+"'";
		stm=con.prepareStatement(sql);
        stm.setInt(1,neweval );
		num=stm.executeUpdate();
		stm.close();
		
	}
	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
	}
	return num!=0;
}
public void updateUserEval(int id,int user ) {

	try {
		connection();
		String sql="Update product set userEval=? where id='"+id+"'";
		stm=con.prepareStatement(sql);

	
		stm.setInt(1,user );
		
		stm.executeUpdate();
		stm.close();
	}
	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
	}
}

public int numberOfUserEval(int id) {
	int user=0;
	try {
		connection();
		String sql=SELECT_PRODUCT_BY_ID_QUERY+id+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		if(rs.next()) {
			user=rs.getInt("userEval");
		}
		rs.close();
		stm.close();
	}
	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX+ e.getMessage());
	}
	return user;
}
public static Boolean getFinsertOrder() {
    return finsertorder;
}


public static void setFinsertOrder(Boolean value) {
	finsertorder = value;
}

public static Boolean getFlagDeleteO() {
    return flagdeleteO;
}


public static void setFlagDeleteO(Boolean value) {
	flagdeleteO = value;
}


public static Boolean getFlagSearch() {
    return flagSearch;
}

public static void setFlagSearch(Boolean value) {
	flagSearch = value;
}

public static Boolean getIsAvaliable() {
    return isavaliable;
}


public static void setIsAvaliable(Boolean value) {
	isavaliable = value;
}
public static Boolean getTest() {
    return test;
}


public static void setTest(Boolean value) {
	   test = value;
}
}