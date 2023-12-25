package car;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Installer {
    private static final Logger LOGGER = Logger.getLogger(Installer.class.getName());
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
	
	private String fname;
	private String lname;
	private String email;
	private String password;
	private String phone;
	private  static Boolean test=false;
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String CUSTOMER_NAME_COLUMN = "customername";
    private static final String CUSTOMER_REQ_COLUMN = "customerreq";
    private static final String CAR_MODEL_COLUMN = "carmodel";
    private static final String CUSTOMER_PHONE_COLUMN = "customerphone";
    private static final String STREET_COLUMN = "street";
    private static final String ERROR_PREFIX = "An error occurred: ";

    private static final Scanner SCANNER = new Scanner(System.in);
	public Installer() {
	
	}
	public Installer(String fname,String lname,String email, String password,String phone) {
		this.fname=fname;
		this.lname=lname;
		this.email=email;
		this.password=password;
		this.phone=phone;
		

	}
	public void setfname(String fname) {
		this.fname=fname;
	}
	public void setlname(String lname) {
		this.lname=lname;
	}
	public void setemail(String email) {
		this.email=email;
	}
	public void setphone(String phone) {
		this.phone=phone;

	}
	public void setpassword(String pass) {
		this.password=pass;
	}
	
	
	public String getfname() {
		return fname;
		}
	public String getlname() {
		return lname;
		}
	public String getemail() {
		return email;
		}
	public String getpassword() {
		return password;
		}
	public String getphone() {
		return phone;
		}
	

	
	
	public boolean insertInstaller(Installer installer) {
		int num=0;
		try {
			connection();
			String sql="INSERT INTO installer (first_name,last_name,email,password,phone_num,saturday,sunday,monday,tuesday,Wensday,Thuersday) values(?,?,?,?,?,?,?,?,?,?,?)";
			stm=con.prepareStatement(sql);
	
					stm.setString(1,installer.getfname());
			    	stm.setString(2,installer.getlname());
			    	stm.setString(3,installer.getemail());
			    	stm.setString(4,installer.getpassword());
			    	stm.setString(5,installer.getphone());
			    	stm.setBoolean(6,false);
			    	stm.setBoolean(7,false);
			    	stm.setBoolean(8,false);
			    	stm.setBoolean(9,false);
			    	stm.setBoolean(10,false);
			    	stm.setBoolean(11,false);
			    	
			num=stm.executeUpdate();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
		}
   return num>0;

	}
	private void connection() throws ClassNotFoundException, SQLException {
		String passworrd = System.getProperty("database.password");
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root",passworrd);
	}
	
public void viewInstaller() {
		 
		try {
			connection();
			String sql="Select * from installer";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			
			
			while (rs.next()) {
				String day="";
			        
				if(!rs.getBoolean("Saturday")) {
					day+="  Saturday";
				}
				if(!rs.getBoolean("Sunday")) {
					day+="  Sunday";
				}
				if(!rs.getBoolean("Monday")) {
					day+="  Monday";
				}
				if(!rs.getBoolean("Tuesday")) {
					day+="  Tuesday";
				}
				if(!rs.getBoolean("Wensday")) {
					day+="  Wensday";
				}
				if(!rs.getBoolean("Thuersday")) {
					day+="  Thuersday";
				}
				int id = rs.getInt("id");
				String firstName = (rs.getString(FIRST_NAME_COLUMN) != null) ? rs.getString(FIRST_NAME_COLUMN) : "";
				String lastName = (rs.getString(LAST_NAME_COLUMN) != null) ? rs.getString(LAST_NAME_COLUMN) : "";
				String phoneNumber = (rs.getString("phone_num") != null) ? rs.getString("phone_num") : "";

				LOGGER.info(String.format("id=%d %s %s Phone Number is:%s%nAvailable ON:%s",
				    id,
				    firstName,
				    lastName,
				    phoneNumber,
				    day
				));

		}
			stm.close();
			rs.close();
		}

		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
		}
	}

public int getInstallerId(String email) {
	int id=0;
	try {
		connection();
		String sql="Select * from installer where email='"+email+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		if (rs.next()) {
		id=rs.getInt("id");
			
	}
		stm.close();
		rs.close();
	}

	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX + e.getMessage());
	}
	return id;
}



public boolean insertInstallerUser(Installer installer) {
	   int num=0;
	   try {
			connection();
			String sql="INSERT INTO users (name,email,password,user_type) values(?,?,?,?)";
			stm=con.prepareStatement(sql);
	stm.setString(1,installer.getfname()+installer.getlname());
	stm.setString(2,installer.getemail());
	stm.setString(3,installer.getpassword());
	stm.setString(4,"installer");
	stm.executeUpdate();
 num=stm.executeUpdate();

	stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
		}
	   return num > 0;

}

public String getInstallerName(int id) {
	
	String name=null;
	try {
		connection();
		String sql="Select * from installer where id='"+id+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		if (rs.next()) {
		name=rs.getString(FIRST_NAME_COLUMN)+" "+rs.getString(LAST_NAME_COLUMN);
			
	}
		stm.close();
		rs.close();
	}

	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX + e.getMessage());
	}
	return name;
}
public boolean viewInstallationReq(String name) {
	boolean flag=false;
	try {
		connection();
		String sql="Select * from installation_req where installer_name='"+name+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		while (rs.next()) {
			if (!getTest()) {
			int idd = rs.getInt("id");
			String cname = rs.getString(CUSTOMER_NAME_COLUMN);
			String req = rs.getString(CUSTOMER_REQ_COLUMN);
			String carmodel = rs.getString(CAR_MODEL_COLUMN);
			String day = rs.getString("day");
			String phonee = rs.getString(CUSTOMER_PHONE_COLUMN);
			String citty = rs.getString("city");
			String strreet = rs.getString(STREET_COLUMN);

			if (cname != null && req != null && carmodel != null && day != null && phonee != null && citty != null && strreet != null) {
			    LOGGER.info(String.format("id= %d\t%s\t%s\t%s\t%s\t%s\t%s\t%s", idd, cname, req, carmodel, day, phonee, citty, strreet));
			} else {
			    LOGGER.warning("Some values are null. Unable to log information.");
			} 
			}
			else {
				flag=true;
			}
	}
		stm.close();
		rs.close();
	}

	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX + e.getMessage());
	}
	return flag;
	
}

public boolean editDay(String day,int id,boolean validDay) {
	boolean flag=false;
	try {
		connection();
		String sql=null;
		if(validDay) {
		 sql="Update installer set "+day+"=true where id='"+id+"'";
		}
		else {
			sql="Update installer set "+day+"=false where id='"+id+"'";
		}
		stm=con.prepareStatement(sql);
		int num=stm.executeUpdate();
		stm.close();
		if(num>0) {
			flag=true;
		}
	}
	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX + e.getMessage());
	}
	return flag;

}

public boolean removeInstallation(int idd) {
	int num =0;
	 try {
 			connection();
 			String sql="Delete from installation_req where ID='" +idd+"' ";
 			stm=con.prepareStatement(sql);
 			if(!getTest()) {
 				 num =stm.executeUpdate();	
 			}
 			 					
 			stm.close();
 			
 		}
 		catch(Exception e) {
 	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
 		}
	 return num != 0;
}
public String getInstallationDay(int instid) {
	String day=null;
	
	try {
		connection();
		String sql="Select * from installation_req where id='"+instid+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		if(rs.next()) {
		day=rs.getString("day");
	}
		stm.close();
		rs.close();
	}

	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX + e.getMessage());
	}	
	return day;
}
public boolean updateDayforcustomer(int instid,String day){
	boolean flagUpdate=false;
	 try {
		   connection();
	   		    String sql="Update installation_req set day=? where id='"+instid+"'";
	            stm=con.prepareStatement(sql);
	           
	            stm.setString(1, day);
	            
	            int num=stm.executeUpdate();
	            stm.close();
	            if(num>0) {
	            	flagUpdate=true;	
	            	
	            }
	            else {
	            	flagUpdate=false;	
	            }
	            
	   		}
	   		catch(Exception e) {
	   	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
	   		}
	 return flagUpdate;
}

public boolean veiwInstallationRequestAdmin() {
	boolean flag=false;
	try {
		connection();
		String sql="Select * from installation_req  ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
	
		while (rs.next()) {
			if (!getTest()) {
//			    int idd = rs.getInt("id");
//			    String cnname = rs.getString(CUSTOMER_NAME_COLUMN);
//			    String req = rs.getString(CUSTOMER_REQ_COLUMN);
//			    String carmodel = rs.getString(CAR_MODEL_COLUMN);
//			    String day = rs.getString("day");
//			    String phonee = rs.getString(CUSTOMER_PHONE_COLUMN);
//			    String city1 = rs.getString("city");
//			    String street1 = rs.getString(STREET_COLUMN);
//			    String installername = rs.getString("installer_name");
//			    if (cnname != null && req != null && carmodel != null && day != null && phonee != null && city1 != null && street1 != null && installername != null) {
//			        LOGGER.info(String.format("id= %s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", idd, installername, cnname, req, carmodel, day, phonee, city1, street1));
//			    }
//				else {
//			        LOGGER.warning("Some values are null. Unable to log information.");
//			    }
			} 

		else {
			flag=true;
		}
	}
		stm.close();
		rs.close();
	}
	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX + e.getMessage());
	}
	return flag;
	
}
public boolean viewInstallerAdmin() {
	boolean flag=false;
	   try {
		   connection();
		   String sql="Select * from installer ";
		   stm=con.prepareStatement(sql);
		   rs=stm.executeQuery();

		   while (rs.next()) {
			   if(!getTest()) {
			   Integer id=rs.getInt("id");
			   LOGGER.info(String.format("%s\t%s\t%s\t%s", id, rs.getString(FIRST_NAME_COLUMN), rs.getString(LAST_NAME_COLUMN), rs.getString("phone_num")));
		   }
			   else {
				   flag=true;
			   }
		   }
		   stm.close();
		   rs.close();
	   
	   }
	   catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
	   }
	   return flag;
}
public boolean removeInstaller(int id) {
	int num=0;
	   try {
			connection();
			String sql="Delete from installer where ID='" +id+"' ";
			stm=con.prepareStatement(sql);
			 num =stm.executeUpdate();
					
			stm.close();
			
		}
		catch(Exception e) {
	        LOGGER.severe(ERROR_PREFIX + e.getMessage());
		} 
	
		   return num>0;
	   
}

public boolean customerViewInstallation(String name) {
	boolean flag=false;
	try {
		connection();
		String sql="Select * from installation_req where customername='"+name+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		while (rs.next()) {
			if(!getTest()) {
//		int idd=rs.getInt("id");
//		String cname=rs.getString(CUSTOMER_NAME_COLUMN);
//		String req=rs.getString(CUSTOMER_REQ_COLUMN);
//		String carmodel=rs.getString(CAR_MODEL_COLUMN);
//		String day=rs.getString("day");
//		String phonee=rs.getString(CUSTOMER_PHONE_COLUMN);
//		String installername=rs.getString("installer_name");
//		LOGGER.info(String.format("id= %d\t%s\t%s\t%s\t%s\t%s\t%s", idd, cname, req, carmodel, day, installername, phonee));
	}
			else {
				flag=true;
			}
		}
		
		stm.close();
		rs.close();
	}

	catch(Exception e) {
        LOGGER.severe(ERROR_PREFIX + e.getMessage());
	}	
	return flag;
}

public static Boolean getTest() {
    return test;
}


public static void setTest(Boolean value) {
	   test = value;
}
}
