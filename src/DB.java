 import static com.sample.test.DriverScript.CONFIG;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.output.TeeOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DB {

  private WebDriver driver = null;
  private Connection con = null;
  private Statement stmt = null;
  String baseUrl;
  
  

  @Before
  public void setUp() throws Exception {
    // use firefox browser
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.co.in/?gfe_rd=cr&ei=6uvSVbOUPKrv8wfHtIyQBA&gws_rd=ssl";
    //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void test() throws SQLException, ClassNotFoundException, IOException {
	  
      PrintStream outStream   = null;
      PrintStream errStream = null;
      PrintStream fileStream  = null;
      outStream = System.out;   
      errStream = System.err;
      System.out.println("hi");
      OutputStream os = new FileOutputStream("D:/Dhvani/automation/iFormFactor/src/com/sample/util/result.html", false); // only the file output stream
      os = new TeeOutputStream(outStream, os); // create a TeeOutputStream that duplicates data to outStream and os
      fileStream = new PrintStream(os);
      
      System.setErr(fileStream);   
      System.setOut(fileStream);
      
      
    // Load Microsoft SQL Server JDBC driver.
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    //Class.forName("com.mysql.jdbc.Driver");
    // Prepare connection url.
    String url = "jdbc:sqlserver://SVT-SRV-55:1433;DatabaseName=iFormsQA";
   // String url = "jdbc:mysql://SVT-SRV-55;DatabaseName=iFormsQA";
    
    //String url ="jdbc:mysql://SVT-SRV-55//SQLEXPRESS:11.0.2100/";

    
    // Get connection to DB.
    con = DriverManager.getConnection(url, "sa", "Synoverge@1");
    // Create statement object which would be used in writing DDL and DML
    // SQL statement.
    stmt = con.createStatement();
    // Send SQL SELECT statements to the database via the
    // Statement.executeQuery
    // method which returns the requested information as rows of data in a
    // ResultSet object.
    // define query to read data
    try {
  	  //String tableName=CONFIG.getProperty("Table3_iForms_Country");  

      String query = "select * from [Lookup].[Country]";
      ResultSet result = stmt.executeQuery(query);
      
      int count=0;
      while(result.next()){
      //if (result.next()) {
    	   String CountryName = result.getString("CountryName");
    	   System.out.println("Country Name : "+ CountryName);
    	  // fw.write(System.getProperty("line.separator"));
    	  // System.getProperty("line.separator");
    	   //System.out.println( "<br>");
    	   System.out.println( "&nbsp;");
    	   String CountryId = result.getString("CountryId");
    	   //System.out.println( "<br>");
    	   System.out.println( "&nbsp;");
    	   System.out.println("CountryId : " + CountryId);
    	   System.out.println( "<br>");
    	   count = count+1;
    	}

    }
  
      
      /*
      else
      {
    	  System.out.println("No rows returned");
      }*/
      
      //System.out.println(result.getString("FirstName"));
     // if (result.next()) {
       // while (result.next()) {
          // Fetch value of "username" and "password" from "result"
          // object; this will return 2 existing users in the DB.

         
         // String username = result.getString("username");
          //String password = result.getString("userpassword");
          // print them on the console
          //System.out.println("username :" + username);
          //System.out.println("password: " + password);
        //}
       // result.close();
     // }
  
   
    catch (SQLException ex)
    {
      System.out.println("Error:"+ex);
    }
    
    
  
    // Add a new user on the UI
    
   // finally {
     //   out.close();
       // in.close();
 //   }
}
}
