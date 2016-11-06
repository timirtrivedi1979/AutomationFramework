
package com.sample.test;

import static com.sample.test.DriverScript.APP_LOGS;
import static com.sample.test.DriverScript.CONFIG;
import static com.sample.test.DriverScript.OR;
import static com.sample.test.DriverScript.currentTestDataSetID;
import static com.sample.test.DriverScript.currentTestSuiteXLS;
import java.text.DecimalFormat;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.UnhandledAlertException;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import com.eviware.soapui.model.testsuite.TestRunner;
//import com.eviware.soapui.tools.SoapUITestCaseRunner;

//import com.thoughtworks.selenium.Selenium;
import com.sample.util.DownloadManager;
import com.sample.util.ExtractExcelToCSV;
import com.sample.xls.read.Xls_Reader;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action; 
//import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

//Timir 18thApril2013

public class Keywords {

	public String windowHandle;
	//public Selenium selenium ;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row   =null;
	private XSSFCell cell = null;
	public int startIndex=0;
	public static FileInputStream fs;
	private static final String TASKLIST = "tasklist";
	private static final String KILL = "taskkill /IM ";
	/*String USERNAME = CONFIG.getProperty("gmailUser").toString();
	String PASSWORD = CONFIG.getProperty("gmailPassword").toString();*/
	public static Properties MSG;
	public WebDriver driver;
	public String result=Constants.KEYWORD_FAIL;
	public static boolean screenshotfoldercreate=true;
	public  static String filePath="";

	
	
	public static String globalValue;
	//public static String g_employeecost;
	public static String g_dependentcost;
	public static ArrayList<String> plansFromDB=new ArrayList<String>();
	public static Properties msgCONFIG;
	/*public Keywords() throws IOException
	{

		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//config.properties");

		CONFIG= new Properties();
		CONFIG.load(fs);


	}*/
	int waitforelement=Integer.parseInt(CONFIG.getProperty("waitforelement"));
	int waitfordocUpload=Integer.parseInt(CONFIG.getProperty("waitfordocUpload"));

	public String openBrowser(String object,String data){		
		APP_LOGS.debug("Opening browser");

		try
		{
			System.out.println("Updating Excel References");
			getRefFlag(object, data);
		}
		catch(Exception e)
		{
			System.out.println("Unable to update data in ref sheet");
			System.out.println(e.getMessage());
			result= Constants.KEYWORD_FAIL;
		}
		System.out.println("FINISHED: Updating Excel References");

		try
		{
			if(data.equals("Mozilla"))
			{
				System.out.println("Opening browser");
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//geckodriver.exe");
				driver=new FirefoxDriver();
				driver.manage().deleteAllCookies();

				result=Constants.KEYWORD_PASS;
			}
			else if(data.equals("IE"))
			{
				//isProcessRunging("IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//IEDriverServer.exe");
				//DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
				//caps.setCapability("javascriptEnabled", true);
				//caps.setCapability("nativeEvents", false);
				//caps.setCapability("requireWindowFocus",true);
				//driver=new InternetExplorerDriver(caps);
				//driver.manage().deleteAllCookies();
				
			
				/*DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);      
			driver = new InternetExplorerDriver(capabilities);*/

				result=Constants.KEYWORD_PASS;
			}
			else if(data.equals("Chrome")){

				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//chromedriver.exe");
							
				//DesiredCapabilities caps = DesiredCapabilities.chrome();
				//caps.setCapability("javascriptEnabled", true);
				//caps.setCapability("nativeEvents", true);
				//caps.setCapability("requireWindowFocus",true);
				//driver.manage().deleteAllCookies();

				driver=new ChromeDriver();
				
				result=Constants.KEYWORD_PASS;
			
				
			
			}	

			else if(data.equals("safari"))
			{
				System.out.println("Opening browser");
				driver=new SafariDriver();
				driver.manage().deleteAllCookies();

				result=Constants.KEYWORD_PASS;
			}
			//long implicitWaitTime=Long.parseLong(CONFIG.getProperty("implicitwait"));
			//driver.manage()).timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);


		}
		catch(Exception e)
		{
			e.printStackTrace();
			result= Constants.KEYWORD_FAIL;
			//throw new WebDriverException("Could not open browser instance");

		}
		return result;

	}

	/** This function is used to navigate to a given URL.
	 * 
	 * @param object - This var is used to identify object properties 
	 * @param data - This var is used to as a test data (URL).
	 * @return This function is return the status of current URL to load.
	 */
	public String navigate(String object,String data){		
		APP_LOGS.debug("Navigating to URL");
		try{
			driver.manage().window().maximize();	
			driver.navigate().to(data);
			sleep(10);

			//driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		}catch(Exception e){
			//return Constants.KEYWORD_FAIL+" -- Not able to navigate";
		}
		return Constants.KEYWORD_PASS;
	}

	/** This function is used to perform click event on specified link
	 * 
	 * @param object - This var is used to identify object properties 
	 * @param data - This var is used to as a test data
	 * @return This function is return the status of click event perform on link object.
	 */
	public String clickLink(String object,String data){
		APP_LOGS.debug("Clicking on link ");
		try{
			
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
			{
				
				String windowHandle=driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.xpath(OR.getProperty(object)));
				try
				{
				driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				sleep(3);
				List<WebElement> objElements = driver.findElements(By.xpath(OR.getProperty(object)));
				if (objElements.size()>0)
				{
					driver.findElement(By.xpath(OR.getProperty(object))).click();
				}
				else
				{
					result=Constants.KEYWORD_PASS;
				}
				
				}
				catch(Exception e)
				{
					driver.findElement(By.xpath(OR.getProperty(object))).click();	
				}
				
				result=Constants.KEYWORD_PASS;
			}
			else
			{
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			result=Constants.KEYWORD_PASS;
			}
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
			//throw  new NoSuchElementException("No such element found");
		}

		return result;
	}

	public String clickLink_linkText(String object,String data){
		APP_LOGS.debug("Clicking on link ");
		try{
			
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
			{
				
				String windowHandle=driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.linkText(OR.getProperty(object)));
				try
				{
				driver.findElement(By.linkText(OR.getProperty(object))).sendKeys("\n");
				sleep(3);
				List<WebElement> objElements = driver.findElements(By.linkText(OR.getProperty(object)));
				if (objElements.size()>0)
				{
					driver.findElement(By.linkText(OR.getProperty(object))).click();
				}
				else
				{
					result=Constants.KEYWORD_PASS;
				}
				
				}
				catch(Exception e)
				{
					driver.findElement(By.linkText(OR.getProperty(object))).click();
				}
				
				result=Constants.KEYWORD_PASS;
			}
			else
			{
			driver.findElement(By.linkText(OR.getProperty(object))).click();
			result=Constants.KEYWORD_PASS;
			}
		}
		catch(Exception e){
			result= Constants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
			//	throw  new NoSuchElementException("No such element found");
		}
		return result;
	}

	public  String verifyLinkText(String object,String data){
		APP_LOGS.debug("Verifying link Text");
		try{
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			String expected=data;

			if(actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- Link text not verified";

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Link text not verified"+e.getMessage();

		}

	}

	public  String clickButton(String object,String data){
		APP_LOGS.debug("Clicking on Button");
		try{
			
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
			{
				
				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				//driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle=driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				/*driver.findElement(By.cssSelector(OR.getProperty(object))).click();	
				driver.findElement(By.cssSelector(OR.getProperty(object))).sendKeys("\n");*/
				driver.findElement(By.xpath(OR.getProperty(object)));
				
				try
				{
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					sleep(3);
					List<WebElement> objElements = driver.findElements(By.xpath(OR.getProperty(object)));		
					if (objElements.size()>0)
					{			
						driver.findElement(By.xpath(OR.getProperty(object))).click();
					
					}
					else
					{
						result=Constants.KEYWORD_PASS;
					
					}
					
				}
				catch(Exception e)
				{
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					System.out.println("object is clicked");
				}
				sleep(3);
				result=Constants.KEYWORD_PASS;
			}
			else
			{
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			sleep(8);
			
			result=Constants.KEYWORD_PASS;
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL +" -- Not able to click on Button"+e.getMessage();;
			//	throw  new NoSuchElementException("No such element found");

		}


		return result;
	}

	public  String clickButtonByCss(String object,String data){
		APP_LOGS.debug("Clicking on Button");
		try{
			
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
			{
				
				
				String windowHandle=driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.cssSelector(OR.getProperty(object)));
				
				try
				{
					driver.findElement(By.cssSelector(OR.getProperty(object))).sendKeys("\n");
					sleep(3);
					List<WebElement> objElements = driver.findElements(By.cssSelector(OR.getProperty(object)));		
					if (objElements.size()>0)
					{			
						driver.findElement(By.cssSelector(OR.getProperty(object))).click();
					
					}
					else
					{
						result=Constants.KEYWORD_PASS;
					
					}
				
				}
				catch(Exception e)
				{
					driver.findElement(By.cssSelector(OR.getProperty(object))).click();	
				}
				
				
				result=Constants.KEYWORD_PASS;
			}
			else
			{
			driver.findElement(By.cssSelector(OR.getProperty(object))).click();
			//pause(3000);
			result=Constants.KEYWORD_PASS;
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL +" -- Not able to click on Button"+e.getMessage();;
			//	throw  new NoSuchElementException("No such element found");
		}
		return result;
	}

	public  String clickButtonByID(String object,String data){
		APP_LOGS.debug("Clicking on Button");
		try{
			boolean vres=true;
			//driver.findElement(By.id(OR.getProperty(object))).click();
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
			{
				
				String windowHandle=driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.id(OR.getProperty(object)));
				try
				{
					//driver.findElement(By.id(OR.getProperty(object))).click();
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
					sleep(5);
					List<WebElement> objElements = driver.findElements(By.id(OR.getProperty(object)));		
					if (objElements.size()>0)
					{			
						driver.findElement(By.id(OR.getProperty(object))).click();
					
					}
					else
					{
						result=Constants.KEYWORD_PASS;
					
					}
				
				}
				catch(Exception e)
				{
					
					
					driver.findElement(By.id(OR.getProperty(object))).click();
					System.out.println("clicked");
				}
				//sleep(4);
				result=Constants.KEYWORD_PASS;
			}
			
			else
			{
			List<WebElement> objButton = driver.findElements(By.id(OR.getProperty(object)));
				if (objButton.size()>0)
				{
				ClickWebElement(objButton.get(0));
				}
			}
			
			sleep(6);
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL +" -- Not able to click on Button"+e.getMessage();;


		}


		return result;
	}

	/*public  String clickButtonByID(String object,String data){
		APP_LOGS.debug("Clicking on Button");
		try{
			driver.findElement(By.id(OR.getProperty(object))).click();

			sleep(6);
			result=Constants.KEYWORD_PASS;	
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL +" -- Not able to click on Button"+e.getMessage();;


		}


		return result;
	}*/
	public  String clickButtonByName(String object,String data){
		APP_LOGS.debug("Clicking on Button");
		try{
			//driver.findElement(By.id(OR.getProperty(object))).click();
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
			{
				
				String windowHandle=driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.name(OR.getProperty(object)));
				try
				{
					driver.findElement(By.name(OR.getProperty(object))).sendKeys("\n");
					sleep(3);
					List<WebElement> objElements = driver.findElements(By.name(OR.getProperty(object)));		
					if (objElements.size()>0)
					{
						driver.findElement(By.name(OR.getProperty(object))).click();
					}
					else
					{
						result=Constants.KEYWORD_PASS;
					}
				}
				catch(Exception e)
				{
					
					driver.findElement(By.name(OR.getProperty(object))).click();
				}
				
				result=Constants.KEYWORD_PASS;
			}
			else
			{
			List<WebElement> objButton = driver.findElements(By.name(OR.getProperty(object)));
			if (objButton.size()>0){
				ClickWebElement(objButton.get(0));
			}
			
			sleep(6);
			result=Constants.KEYWORD_PASS;
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL +" -- Not able to click on Button"+e.getMessage();;


		}


		return result;
	}

	public  String verifyButtonText(String object,String data){
		APP_LOGS.debug("Verifying the button text");
		try{
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			String expected=data;

			if(actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- Button text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}

	}

	public  String selectRadio(String object, String data){
		APP_LOGS.debug("Selecting a radio button");
		try{
			String temp[]=object.split(Constants.DATA_SPLIT);
			driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		return Constants.KEYWORD_PASS;	
	}

	public  String selectRadioButton(String object, String data){
		APP_LOGS.debug("Selecting a radio button");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked==null){
				driver.findElement(By.xpath(OR.getProperty(object))).click();	
				return Constants.KEYWORD_PASS;	
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}

		return Constants.KEYWORD_PASS;	

	}

	public  String selectRadioButtonByID(String object, String data){
		APP_LOGS.debug("Selecting a radio button");
		try{
			String checked=driver.findElement(By.id(OR.getProperty(object))).getAttribute("value");
			if(checked.equals("NO")){
				driver.findElement(By.id(OR.getProperty(object))).click();	
				return Constants.KEYWORD_PASS;	
			}
			else if(checked=="YES")
			{
				return Constants.KEYWORD_PASS;
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	
		}
		return Constants.KEYWORD_PASS;	
	}

	public  String verifyRadioSelected(String object, String data){
		APP_LOGS.debug("Verify Radio Selected");
		try{
			String temp[]=object.split(Constants.DATA_SPLIT);
			String checked=driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).getAttribute("checked");
			if(checked==null)
				return Constants.KEYWORD_FAIL+"- Radio not selected";	
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	
		}
		return Constants.KEYWORD_PASS;	
	}

	public  String checkCheckBox(String object,String data){
		APP_LOGS.debug("Checking checkbox");
		try{
			// true or null
			sleep(4);
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked==null)// checkbox is unchecked
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;

	}

	public  String checkCheckBoxByID(String object,String data){
		APP_LOGS.debug("Checking checkbox");
		try{
			// true or null
			String checked=driver.findElement(By.id(OR.getProperty(object))).getAttribute("checked");
			if(checked==null)// checkbox is unchecked
				driver.findElement(By.id(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;

	}

	public String unCheckCheckBox(String object,String data){
		APP_LOGS.debug("Unchecking checkBox");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked!=null)
				driver.findElement(By.xpath(OR.getProperty(object))).click();

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;

	}

	public  String verifyCheckBoxSelected(String object,String data){
		APP_LOGS.debug("Verifying checkbox selected");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked!=null)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " - Not selected";

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";

		}


	}

	public String verifyText(String object, String data){
		APP_LOGS.debug("Verifying the text");
		try{
			sleep(3);
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText().trim();
			String expected=data;

			if(actual.equalsIgnoreCase(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	public String verifyTextByCSS(String object, String data){
		APP_LOGS.debug("Verifying the text");
		try{
			sleep(3);
			String actual=driver.findElement(By.cssSelector(OR.getProperty(object))).getText().trim();
			String expected=data;

			if(actual.equalsIgnoreCase(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	public  String verifyTextById(String object, String data){
		APP_LOGS.debug("Verifying the text");
		try{
			sleep(3);
			String actual=driver.findElement(By.id(OR.getProperty(object))).getAttribute("value").trim();
			String expected=data.trim();

			if(actual.equalsIgnoreCase(expected))
				return  Constants.KEYWORD_PASS;
			else
				return  Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return  Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	public String verifyTextContent(String object, String data){
		APP_LOGS.debug("Verifying the text content");
		try{
			sleep(3);

			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText().trim();
			String expected=data.trim();

			if(actual.toUpperCase() .contains(expected.toUpperCase()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- text content not verified "+actual+"--"+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}

	}

	public String verifyNumericValue(String object, String data){
		APP_LOGS.debug("Verifying the text");
		try{
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			if (actual != null){
				actual=actual.substring(0, actual.indexOf("."));
				String expected=data;

				if(actual.equals(expected))
					return Constants.KEYWORD_PASS;
				else
					return Constants.KEYWORD_FAIL+" -- Numeric value not verified "+actual+" -- "+expected;
			}
			else{
				return Constants.KEYWORD_FAIL+" -- No Value present ";
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	public  String writeInInput(String object,String data){
		APP_LOGS.debug("Writing in text box");

		//String newdata = String.valueOf(data);
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).clear(); 

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data,Keys.ENTER);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}
	
	public  String writeInInputByCSS(String object,String data){
		APP_LOGS.debug("Writing in text box");

		//String newdata = String.valueOf(data);
		try{
			driver.findElement(By.cssSelector(OR.getProperty(object))).clear(); 

			driver.findElement(By.cssSelector(OR.getProperty(object))).sendKeys(data,Keys.ENTER);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public  String writeInInputByName(String object,String data){
		APP_LOGS.debug("Writing in text box");

		//String newdata = String.valueOf(data);
		try{
			driver.findElement(By.name(OR.getProperty(object))).clear(); 

			driver.findElement(By.name(OR.getProperty(object))).sendKeys(data,Keys.ENTER);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public  String writeInInputByID(String object,String data){
		APP_LOGS.debug("Writing in text box");

		//String newdata = String.valueOf(data);
		try{
			driver.findElement(By.id(OR.getProperty(object))).clear(); 

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data,Keys.TAB);
			result=Constants.KEYWORD_PASS;
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return result;
	}
	
	
	
	public  String writeInInputByIDEnter(String object,String data){
		APP_LOGS.debug("Writing in text box");

		//String newdata = String.valueOf(data);
		try{
			driver.findElement(By.id(OR.getProperty(object))).clear(); 

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(5000);   
			   Robot robot = new Robot();
			   robot.keyPress(KeyEvent.VK_DOWN);
				//Thread.sleep(2000);   
			   robot.keyPress(KeyEvent.VK_ENTER);
			  // Thread.sleep(2000); 
			//.sendKeys(Keys.ENTER);
			//driver.findElement(By.id(OR.getProperty(object))).sendKeys(data,Keys.ENTER);
			//action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.DELETE).perform(); 
			
			result=Constants.KEYWORD_PASS;
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return result;
	}
	
	

	public  String verifyTextinInput(String object,String data){
		APP_LOGS.debug("Verifying the text in input box");
		try{
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			String expected=data;

			if(actual.equals(expected)){
				return Constants.KEYWORD_PASS;
			}else{
				return Constants.KEYWORD_FAIL+" Not matching ";
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to find input box "+e.getMessage();

		}
	}

	public  String verifyTextinInputByID(String object,String data){
		APP_LOGS.debug("Verifying the text in input box");
		try{
			String actual = driver.findElement(By.id(OR.getProperty(object))).getAttribute("value");
			String expected=data;

			if(actual.equals(expected)){
				return Constants.KEYWORD_PASS;
			}else{
				return Constants.KEYWORD_FAIL+" Not matching ";
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to find input box "+e.getMessage();

		}
	}

	 
	/** This function is used to verify Textbox value By CSS.
	 * 
	 * @param object - This var is used to identify object properties 
	 * @param data - This var is used to as a test data.
	 * @return This function is return the status-
	 * 			Pass- IF Textbox value and Data are matching
	 * 			Fail- IF Textbox value and Data are not matching
	 */

	public  String verifyTextinInputByCSS(String object,String data){
		APP_LOGS.debug("Verifying the text in input box");
		try{
			String actual = driver.findElement(By.cssSelector(OR.getProperty(object))).getAttribute("value");
			String expected=data;

			if(actual.equals(expected)){
				return Constants.KEYWORD_PASS;
			}else{
				return Constants.KEYWORD_FAIL+" Not matching ";
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to find input box "+e.getMessage();

		}
	}

	public  String clickImage(){
		APP_LOGS.debug("Clicking the image");

		return Constants.KEYWORD_PASS;
	}

	public  String verifyFileName(){
		APP_LOGS.debug("Verifying inage filename");

		return Constants.KEYWORD_PASS;
	}

	public  String verifyTitle(String object, String data){
		APP_LOGS.debug("Verifying title");
		try{
			String actualTitle= driver.getTitle();
			String expectedTitle=data;
			if(actualTitle.equals(expectedTitle))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- Title not verified "+expectedTitle+" -- "+actualTitle;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Error in retrieving title";
		}		
	}

	public String exist(String object,String data){
		APP_LOGS.debug("Checking existance of element");
		try{
			driver.findElement(By.xpath(OR.getProperty(object)));
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object doest not exist";
		}


		return Constants.KEYWORD_PASS;
	}

	public String existByID(String object,String data){
		APP_LOGS.debug("Checking existance of element");
		try{
			sleep(3);
			driver.findElement(By.id(OR.getProperty(object)));
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object doest not exist";
		}


		return Constants.KEYWORD_PASS;
	}

	public  String click(String object,String data){
		APP_LOGS.debug("Clicking on any element");
		try{
			
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
			{
				
				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				//driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle=driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				/*driver.findElement(By.cssSelector(OR.getProperty(object))).click();	
				driver.findElement(By.cssSelector(OR.getProperty(object))).sendKeys("\n");*/
				driver.findElement(By.xpath(OR.getProperty(object)));
				try
				{
					
					driver.findElement(By.xpath(OR.getProperty(object))).click();	
				
				}
				catch(Exception e)
				{
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				//driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result= Constants.KEYWORD_PASS;
			}
			else
			{
			WebElement objElement =driver.findElement(By.xpath(OR.getProperty(object)));
			driver.getWindowHandle();
			Actions builder = new Actions(driver);   
			builder.moveToElement(objElement).build().perform();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("var evt = document.createEvent('MouseEvents');" + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" + "arguments[0].dispatchEvent(evt);", objElement);
			result= Constants.KEYWORD_PASS;
			}
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL+" Not able to click";
		}
		return result;
	}

	public  String clearText(String object,String data){
		APP_LOGS.debug("Clearing the text from input field");
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to clear";
		}
		return Constants.KEYWORD_PASS;
	}

	public  String synchronize(String object,String data){
		try{
			APP_LOGS.debug("Waiting for page to load");
			((JavascriptExecutor) driver).executeScript(
					"function pageloadingtime()"+
							"{"+
							"return 'Page has completely loaded'"+
							"}"+
					"return (window.onload=pageloadingtime());");
		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL;
		}

		return Constants.KEYWORD_PASS;
	}

	public  String datePicker(String object,String data){
		try{
			APP_LOGS.debug("Waiting for date selection");

			((JavascriptExecutor)driver).executeScript(
					"arguments[0].value=arguments[1]", 
				driver.findElement(By.xpath(OR.getProperty("Birth.date.click"))), "25-02-1982");
		   
				
			
		}catch(Exception e){
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public  String waitForElementVisibility(String object,String data){
		APP_LOGS.debug("Waiting for an element to be visible");
		int start=0;
		//int time=(int)Double.parseDouble(data);
		int time=Integer.parseInt(data);
		try{
			sleep(3);
			while(time == start){
				if(driver.findElements(By.xpath(OR.getProperty(object))).size() == 0){
					sleep(1);
					start++;
				}else{
					break;
				}
			}
		}catch(Exception e){
			System.out.println("Unable to find the object"+e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public  String closeBrowser(String object, String data){
		APP_LOGS.debug("Closing the browser");
		try{

			try
			{
				System.out.println("Updating Excel References");
				getRefFlag(object, data);
			}
			catch(Exception e)
			{
				System.out.println("Unable to update data in ref sheet");
				System.out.println(e.getMessage());
				result= Constants.KEYWORD_FAIL;
			}
			System.out.println("FINISHED: Updating Excel References");



			if(CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")==true)
			{
				driver.quit();
				result=Constants.KEYWORD_PASS;
			}
			else
			{
				driver.close();
				driver.quit();
				result=Constants.KEYWORD_PASS;

			}
		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}
		return result;

	}

	public String sleep(int object) throws NumberFormatException, InterruptedException
	{
		try{
			result=pause(String.valueOf(object) , "MILLISECONDS");
		}catch(Exception e){}
		return result;
	}

	public String pause(String object, String data) throws NumberFormatException, InterruptedException{
		//long time = (long)Double.parseDouble(object);
		try{
			if(object.equals(""))
			{
				object=CONFIG.getProperty("pauseSeconds");
			}
			int time=Integer.parseInt(object);
			if(data==null){
				data="";
			}
			if(data.equalsIgnoreCase("seconds")){
				driver.manage().timeouts().implicitlyWait(time,TimeUnit.SECONDS);
			}
			else if(data.equalsIgnoreCase("MILLISECONDS")){
				Thread.sleep(time*1000);
			}
			else{
				Thread.sleep(time*1000);
				//driver.manage().timeouts().implicitlyWait(time,TimeUnit.SECONDS);	
			}
		}
		catch(Exception e)	{
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String wait(String object, String data) throws NumberFormatException, InterruptedException{
		//long time = (long)Double.parseDouble(object);
		try{
			data="";
			long time=Long.parseLong(object);

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);	
		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public  String broswerClose(String object, String data){
		APP_LOGS.debug("Closing the browser");
		try{

			if(CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")==true)
			{
				driver.quit();
				result=Constants.KEYWORD_PASS;
			}
			else if(CONFIG.getProperty("browserType").equalsIgnoreCase("safari")==true)
			{
				driver.close();
				result=Constants.KEYWORD_PASS;
			}
			else
			{
				driver.close();
				driver.quit();
				result=Constants.KEYWORD_PASS;

			}
		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}
		return result;

	}

	public  String selectList(String object, String data){
		APP_LOGS.debug("Selecting from list");
		try{
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			//dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);			  
			droplist.selectByVisibleText(data);


			//driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		}catch(Exception e){
			System.out.println(" - Could not select from list. "+ e.getMessage());
			return Constants.KEYWORD_FAIL +" - Could not select from list. ";	
		}

		return Constants.KEYWORD_PASS;	
	}

	public  String selectListByID(String object, String data){
		APP_LOGS.debug("Selecting from list");
		try{
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.id(OR.getProperty(object)));
			//dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);			  
			droplist.selectByVisibleText(data);
			result= Constants.KEYWORD_PASS;

			//driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		}catch(Exception e){
			System.out.println(" - Could not select from list. "+ e.getMessage());
			result= Constants.KEYWORD_FAIL +" - Could not select from list. ";	
		}

		return result;	
	}

	
	
	public  String selectListByCSS(String object, String data){
		APP_LOGS.debug("Selecting from list");
		try{
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.cssSelector(OR.getProperty(object)));
			//dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);			  
			droplist.selectByVisibleText(data);
			result= Constants.KEYWORD_PASS;

			//driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		}catch(Exception e){
			System.out.println(" - Could not select from list. "+ e.getMessage());
			result= Constants.KEYWORD_FAIL +" - Could not select from list. ";	
		}

		return result;	
	}
	
		
	public  String selectListByName(String object, String data){
		APP_LOGS.debug("Selecting from list");
		try{
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.name(OR.getProperty(object)));
			//dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);			  
			droplist.selectByVisibleText(data);
			result= Constants.KEYWORD_PASS;

			//driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		}catch(Exception e){
			System.out.println(" - Could not select from list. "+ e.getMessage());
			result= Constants.KEYWORD_FAIL +" - Could not select from list. ";	
		}

		return result;	
	}
	
	public  String selectMatchingDropDown(String object, String data){
		APP_LOGS.debug("Selecting from list");


		try{
			data = data.toLowerCase();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			dropDownListBox.click();

			Select objDropDown = new Select(dropDownListBox);                                      
			System.out.println(("No of Values in Dropdown:" + objDropDown.getOptions().size()));

			for (int i=0; i<objDropDown.getOptions().size(); i++){
				String strOption = objDropDown.getOptions().get(i).getText().toString().trim().toLowerCase();
				System.out.println(strOption);
				if (strOption.contains(data)){
					//if (strOption.startsWith(data.toLowerCase())){
					System.out.println(strOption + ":" + data);
					objDropDown.selectByIndex(i);
					break;
				}
			}                                      
		}catch(Exception e){
			System.out.println(" - Could not select from list." +e.getMessage());
			return Constants.KEYWORD_FAIL +" - Could not select from list. ";

		}

		return Constants.KEYWORD_PASS;   
	}

	public  String uploadPhoto(String object, String data){
		APP_LOGS.debug("uploading photo...");
		try{
			data=System.getProperty("user.dir")+data;
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			//driver.findElement(By.linkText(OR.getProperty(object))).sendKeys(data);
			result= Constants.KEYWORD_PASS;

		}catch(Exception e){
			System.out.println(" - Getting error while document uploading" +e.getMessage());
			result= Constants.KEYWORD_FAIL +" - Getting error while photo uploading";	
		}

		return result;	
	}

	public  String uploadDoc(String object, String data){
		String strPath = "";
		APP_LOGS.debug("uploading Document...");
		try{
			strPath = System.getProperty("user.dir") + data;
			System.out.println("path:"+strPath);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(strPath);	
			sleep(3);

		}catch(Exception e){
			System.out.println(" - Getting error while document uploading" +e.getMessage());
			return Constants.KEYWORD_FAIL +" - Getting error while document uploading";	
		}

		return Constants.KEYWORD_PASS;	
	}

	public  String uploadDocByCSS(String object, String data){
		String strPath = "";
		APP_LOGS.debug("uploading Document...");
		try{
			strPath = System.getProperty("user.dir") + data;
			System.out.println("path:"+strPath);
			driver.findElement(By.cssSelector(OR.getProperty(object))).sendKeys(strPath);
			sleep(3);

		}catch(Exception e){
			System.out.println(" - Getting error while document uploading" +e.getMessage());
			return Constants.KEYWORD_FAIL +" - Getting error while document uploading";	
		}

		return Constants.KEYWORD_PASS;	
	}

	public  String uploadDocByID(String object, String data){
		String strPath = "";
		APP_LOGS.debug("uploading Document...");
		try{
			strPath = System.getProperty("user.dir") + data;
			System.out.println("path:"+strPath);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(strPath);
			sleep(3);

		}catch(Exception e){
			System.out.println(" - Getting error while document uploading" +e.getMessage());
			return Constants.KEYWORD_FAIL +" - Getting error while document uploading";	

		}

		return Constants.KEYWORD_PASS;	
	}

	public String verifyAllListElements(String object, String data){
		APP_LOGS.debug("Verifying the selection of the list");
		try{	
			WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));

			// extract the expected values from OR. properties
			String temp=data;
			String allElements[]=temp.split(",");
			// check if size of array == size if list
			if(allElements.length != droplist_cotents.size())
				return Constants.KEYWORD_FAIL +"- size of lists do not match";	

			for(int i=0;i<droplist_cotents.size();i++){
				if(!allElements[i].equals(droplist_cotents.get(i).getText())){
					return Constants.KEYWORD_FAIL +"- Element not found - "+allElements[i];
				}
			}
		}catch(Exception e){
			System.out.println(" - Could not select from list. "+ e.getMessage());
			return Constants.KEYWORD_FAIL ;	

		}


		return Constants.KEYWORD_PASS;	
	}

	public  String verifyListSelection(String object,String data){
		APP_LOGS.debug("Verifying all the list elements");
		try{
			String expectedVal=data;
			//System.out.println(driver.findElement(By.xpath(OR.getProperty(object))).getText());
			WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			String actualVal=null;
			for(int i=0;i<droplist_cotents.size();i++){
				String selected_status=droplist_cotents.get(i).getAttribute("selected");
				if(selected_status!=null)
					actualVal = droplist_cotents.get(i).getText();			
			}

			if(!actualVal.equals(expectedVal))
				return Constants.KEYWORD_FAIL + "Value not in list - "+expectedVal;

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not find list. "+ e.getMessage();	

		}
		return Constants.KEYWORD_PASS;	

	}




	public static String getURLFromEmail(String USERNAME, String PASSWORD){
		APP_LOGS.debug("get URL from email");
		String urlStr = null;
		try{
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", USERNAME, PASSWORD);
			//Store store = session.getStore("imaps");
			//store.connect("imap.mail.yahoo.com", "satyendramca", "prakash222");
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			Message message[] = folder.getMessages();

			System.out.println(message[message.length-1].getContent());
			String emailContent = message[message.length-1].getContent().toString();
			urlStr = getLinks(emailContent);
			System.out.println("URL from:"  +urlStr);

		}catch(Exception e){
			//return Constants.KEYWORD_FAIL+"not found URL from email";
			//APP_LOGS.debug("URL not found from email");
		}
		return urlStr;
	}


	public static String getLinks(String text) {
		APP_LOGS.debug("get URL from string");
		String urlStr = null;
		try{
			String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(text);
			while(m.find()) {
				urlStr = m.group();
				if (urlStr.startsWith("(") && urlStr.endsWith(")"))
				{
					urlStr = urlStr.substring(1, urlStr.length() - 1);
					System.out.println(urlStr);

				}
			}
		}catch(Exception e){
			//return Constants.KEYWORD_FAIL+"NOT found URL from string";
		}
		return urlStr;	
	}


	/*public String getPasswordSetupURL(String object,String data){
	       APP_LOGS.debug("get password setup URL from email");
	       String strPasswordURL = null;
	       try{
	    	   strPasswordURL = getURLFromEmail(USERNAME, PASSWORD);
	    	   System.out.println("Password setup URL:"  +strPasswordURL);
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" URL not found";
			  }
			return Constants.KEYWORD_PASS;		
	}*/

	/*	public String verifySearchResult(String object,String data){
		APP_LOGS.debug("Verifying the Search Results");
		try{
			data=data.toLowerCase();
			for(int i=3;i<=5;i++){
				String text=driver.findElement(By.xpath(OR.getProperty("search_result_heading_start")+i+OR.getProperty("search_result_heading_end"))).getText().toLowerCase();
				if(text.indexOf(data) == -1){
					return Constants.KEYWORD_FAIL+" Got the text - "+text;
				}
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
		}

		return Constants.KEYWORD_PASS;

	}
	 */

	// not a keyword

	public String verifyErrorMsg (String object, String data) throws IOException {

		try {

			sleep(3);
			APP_LOGS.debug("verify error message");
			FileInputStream fs1 = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//message.properties");
			MSG= new Properties();
			MSG.load(fs1);

			for(int index=0;index<=MSG.size();index++)
			{
				String expectedMsg=MSG.getProperty("msg"+index);
				APP_LOGS.debug("Expected Error Message :" + expectedMsg);
				String actgualMsg=driver.findElement(By.xpath(OR.getProperty(object))).getText();
				APP_LOGS.debug("Actual Error Message :" + actgualMsg);
				if(actgualMsg.equals(expectedMsg) )
				{
					result=Constants.KEYWORD_PASS;
					break;

				}
				else
				{
					result=Constants.KEYWORD_FAIL;
				}
			}
			fs1.close();
		}
		catch (Exception e) {
			result=Constants.KEYWORD_FAIL + "Error -->" + e.getMessage();
		}
		return result;

	}

	public String validateAllErrorMessage(String object, String data) throws IOException {

		try{

			ArrayList<String> lstActualError = new ArrayList<String>();
			ArrayList<String> lstErrorMessages = new ArrayList<String>();
			FileInputStream fs1 = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//message.properties");
			msgCONFIG= new Properties();
			msgCONFIG.load(fs1);


			for(int index=1;index<=msgCONFIG.size();index++)
			{
				lstErrorMessages.add(msgCONFIG.getProperty("msg"+index));

			}
			List<WebElement> objErrors = driver.findElements(By.xpath(OR.getProperty(object)));

			if (objErrors.size() > 0 ){
				//System.out.println(objErrors.size());
				for (int i=0; i<objErrors.size(); i++){
					lstActualError.add(objErrors.get(i).getText().toString());
					System.out.println (objErrors.get(i).getText().toString());
				}
			}

			for(String errorMsg : lstActualError)
			{
				for(int i=0; i<lstErrorMessages.size(); i++)
				{
					if(lstErrorMessages.get(i).toString().equalsIgnoreCase(errorMsg))
					{
						APP_LOGS.debug("Field validation message found :" + errorMsg);
						System.out.println("Field validation message found :" + errorMsg);
						result=Constants.KEYWORD_PASS;
						break;
					}
					else if(i==lstErrorMessages.size())
					{
						APP_LOGS.debug("Field validation message not found :" + errorMsg);
						System.out.println("Field validation message not found :" + errorMsg);
						result=Constants.KEYWORD_FAIL + " Error validation not matched";
					}
				}
			}

			fs1.close();
			//Comment by Timir
			/*if (lstErrorMessages.containsAll(lstActualError)){
				return Constants.KEYWORD_PASS;
			}
			else{
				return Constants.KEYWORD_FAIL + " Error validation not matched";
			}*/
		}catch(Exception e){
			e.printStackTrace();
			result=Constants.KEYWORD_FAIL;
		}

		return result;
	}

	public String checkString(String object,String data){
		APP_LOGS.debug("Verifying the string displayed");

		try{

			List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + data + "')]"));        	

			if (list.size()>0)
			{
				return Constants.KEYWORD_PASS +"- String displayed";        			
			}
			else{
				return Constants.KEYWORD_FAIL + "- String is not displayed";	
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
		}       

	}	

	public String getUniqueIntegerValue(String object,String data){
		try{
			String strData = "";

			Calendar cal = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("mmddhhss");

			strData = "1" + df.format(cal.getTime());
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, strData)){
				result=Constants.KEYWORD_PASS;	
			}
			else{
				result=Constants.KEYWORD_FAIL;	
			}
		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}

		return result;

	}

	public String getIntegerValue(){
		try{

			Calendar cal = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("mmddhhss");
			result=df.format(cal.getTime());

		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}

		return result;
	}  

	public String getUniqueEmail(String object,String data){
		try{
			String email = "";
			email = "email" + getIntegerValue() + "@test.com";

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, email)){
				result= Constants.KEYWORD_PASS;	
			}
			else{
				result= Constants.KEYWORD_FAIL;	
			}

		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

	
	
	public String getUniqueEntity(String object,String data){
		try{
			String entity = "";
			entity = "entity" + getIntegerValue();

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, entity)){
				result= Constants.KEYWORD_PASS;	
			}
			else{
				result= Constants.KEYWORD_FAIL;	
			}

		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}
	
	public String getUniqueField(String object,String data){
		try{
			String field = "";
			field = "field" + getIntegerValue();

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, field)){
				result= Constants.KEYWORD_PASS;	
			}
			else{
				result= Constants.KEYWORD_FAIL;	
			}

		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}
	
	
	public String getUniqueStringValue(String object,String data){
		try{
			String strData = "";
			Calendar cal = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("mmddhh");

			strData = "TEST" + df.format(cal.getTime());
			//System.out.println(strData + ":" + object + ":" + data + ":" + currentTestDataSetID);   

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, strData)){
				result=Constants.KEYWORD_PASS;	
			}
			else{
				result=Constants.KEYWORD_FAIL;	
			}
		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}
		return result;

		//return "test" + df.format(cal.getTime());
	}
	public String popupClickOk(String object,String data){

		String strResult = Constants.KEYWORD_FAIL;

		try{
			Alert objPopup = driver.switchTo().alert();
			if (objPopup != null){
				System.out.println("POPUP MESSAGE:" + objPopup.getText());
				//Perform Event - Click OK
				objPopup.accept();
				strResult = Constants.KEYWORD_PASS;
				return strResult;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return strResult;
		}

		return strResult;

	}

	public String assertIfObjectNotFound(String object, String data){

		//driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);		
		try {
			Thread.sleep(5000);

			List<WebElement> objElements = driver.findElements(By.xpath(OR.getProperty(object)));		
			if (objElements.size()>0){			
				return Constants.KEYWORD_FAIL;
			}else{			
				System.out.println(Constants.KEYWORD_PASS + " - Object not found");
				return Constants.KEYWORD_PASS + " - Object not found";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.KEYWORD_FAIL;
	}

	public String assertIfObjectNotFoundById(String object, String data){
		try{
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);		
			List<WebElement> objElements = driver.findElements(By.id(OR.getProperty(object)));		
			if (objElements.size()>0){			
				result= Constants.KEYWORD_FAIL;
			}else{			
				System.out.println(Constants.KEYWORD_PASS + " - Object not found");
				result= Constants.KEYWORD_PASS + " - Object not found";
			}

		}catch(Exception e){
			result= Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String assertIfObjectNotFoundByLink(String object, String data){
		try{
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);		
			List<WebElement> objElements = driver.findElements(By.linkText(OR.getProperty(object)));		
			if (objElements.size()>0){			
				result= Constants.KEYWORD_FAIL;
			}else{			
				System.out.println(Constants.KEYWORD_PASS + " - Object not found");
				result= Constants.KEYWORD_PASS + " - Object not found";
			}

		}catch(Exception e){
			result= Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String assertIfObjectFound(String object, String data){
		try{
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);		
			List<WebElement> objElements = driver.findElements(By.xpath(OR.getProperty(object)));		
			if (objElements.size()>0){			
				result= Constants.KEYWORD_PASS;
			}else{			
				System.out.println(Constants.KEYWORD_FAIL + " - Object not found");
				result= Constants.KEYWORD_FAIL + " - Object not found";
			}
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String assertIfObjectFoundByID(String object, String data){
		try{
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);		
			List<WebElement> objElements = driver.findElements(By.id(OR.getProperty(object)));		
			if (objElements.size()>0){			
				result= Constants.KEYWORD_PASS;
			}else{			
				System.out.println(Constants.KEYWORD_FAIL + " - Object not found");
				result= Constants.KEYWORD_FAIL + " - Object not found";
			}
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String assertIfObjectFoundByLink(String object, String data){
		try{
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);		
			List<WebElement> objElements = driver.findElements(By.linkText(OR.getProperty(object)));		
			if (objElements.size()>0){			
				result= Constants.KEYWORD_PASS;
			}else{			
				System.out.println(Constants.KEYWORD_FAIL + " - Object not found");
				result= Constants.KEYWORD_FAIL + " - Object not found";
			}
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public  String loginAdmin(String object,String data){
		APP_LOGS.debug("Logging by user");


		try{

			driver.findElement(By.xpath(OR.getProperty("txt_userID"))).clear(); 			
			driver.findElement(By.xpath(OR.getProperty("txt_userID"))).sendKeys(CONFIG.getProperty("admin_userName").toString());
			driver.findElement(By.xpath(OR.getProperty("txt_password"))).clear(); 			
			driver.findElement(By.xpath(OR.getProperty("txt_password"))).sendKeys(CONFIG.getProperty("admin_password").toString());
			driver.findElement(By.xpath(OR.getProperty("btn_Go"))).click();
			//driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to login "+e.getMessage();

		}


		return Constants.KEYWORD_PASS;
	}

	public String closeSuccessBox(String object, String data){

		String status = "";
		try{
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE")){
				sleep(10);
				waitforElementToLoadByCSS("broker.signup.success.popup", data);
				
				List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#success"));
				if(objFrameContainer.size()>0){
					System.out.println("Iframe found");
				}
				else
				{
					sleep(5);
					waitforElementToDisplayByCSS("broker.signup.success.popup", data);
				}
				sleep(10);
			}
			waitforElementToDisplayByCss("broker.signup.success.popup", data);
			List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#success"));
			if(objFrameContainer.size()>0){
				System.out.println("IFRAME FOUND");
				driver.switchTo().frame(objFrameContainer.get(0));

				List<WebElement> objClose = driver.findElements(By.cssSelector("input#submitRequest"));
				if(objClose.size()>0){
					System.out.println("CLOSE BUTTON FOUND");					
					//ClickWebElement(objClose.get(0));
					objClose.get(0).click();
					status = Constants.KEYWORD_PASS;
				}
			}
			else{
				status = Constants.KEYWORD_FAIL;
			}			
		}
		catch(Exception e){
			status = Constants.KEYWORD_FAIL + "-Object Not found";
		}

		System.out.println("STATUS:" + status);
		return status;

	}

	public String closePopUpBox(String object, String data){

		String status = "";
		try{
			sleep(3);
			//List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#success"));
			List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#"+OR.getProperty(object)+""));
			if(objFrameContainer.size()>0){
				System.out.println("IFRAME FOUND");

				driver.switchTo().frame(objFrameContainer.get(0));

				List<WebElement> objClose = driver.findElements(By.cssSelector(".btn.offset2"));

				try
				{
					if(objClose.size()>0){
						System.out.println("CLOSE BUTTON FOUND");					
						//ClickWebElement(objClose.get(0));
						objClose.get(0).click();
						status = Constants.KEYWORD_PASS;
					}
				}
				catch(Exception e)
				{

					System.out.println(e+"Frame not found");
				}
			}
			else{
				status = Constants.KEYWORD_FAIL;
			}			
		}
		catch(Exception e){
			status = Constants.KEYWORD_FAIL + "-Object Not found";
		}

		System.out.println("STATUS:" + status);
		return status;

	}

	public String switchToFrame(String object,String data)
	{


		try
		{
			windowHandle=driver.getWindowHandle();
			List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#"+OR.getProperty(object)+""));
			if(objFrameContainer.size()>0){
				System.out.println("IFRAME FOUND");

				driver.switchTo().frame(objFrameContainer.get(0));
				result=Constants.KEYWORD_PASS;
			}
			else
			{
				result=Constants.KEYWORD_FAIL;
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;

		}
		return result;
	}


	public String switchToWidnow(String object,String data)
	{
		object="";

		try
		{
			driver.switchTo().window(windowHandle);
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_PASS;

		}
		return result;
	}
	public String ClickWebElement(WebElement objElement){
		try{
			
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE")){
				objElement.sendKeys("\n");
					
				if (objElement.isDisplayed()==true)
				{			
					objElement.click();
				
				}
				
			}
			else
			{
					String windowHandle=driver.getWindowHandle();
					Actions builder = new Actions(driver);   
					builder.moveToElement(objElement).build().perform();
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("var evt = document.createEvent('MouseEvents');" + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" + "arguments[0].dispatchEvent(evt);", objElement);
					driver.switchTo().window(windowHandle);
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL;
		}

		return Constants.KEYWORD_PASS;
	}

	public String verifySearchResults(String object,String data) throws InterruptedException
	{

		String actualData="";
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			//driver.findElement(By.xpath("//input[@class='input-medium']")).sendKeys(data);
			//	driver.findElement(By.xpath(OR.getProperty("admin.manageissuer.go.btn"))).click();
			try
			{
				driver.findElement(By.xpath("//input[@value='Go']")).click();
			}
			catch(Exception e)
			{
				driver.findElement(By.xpath("//input[@value=' Go ']")).click();
			}
			sleep(3);
			int rowSize = driver.findElements(By.xpath("//table//tbody/tr")).size();

			System.out.println("total row size is"+rowSize);
			for(int index=1;index<=rowSize;index++)
			{
				int colSize = driver.findElements(By.xpath("//table//tbody/tr["+index+"]//td")).size();//Timir.n
				for(int ind=1;ind<=colSize;ind++)
				{
					//String actualData=driver.findElement(By.xpath("//table//tbody//tr["+index+"]//td")).getText();//Timir.o

					actualData=driver.findElement(By.xpath("//table//tbody//tr["+index+"]//td["+ind+"]")).getText();//Timir.n					
					System.out.println("Actual data is"+actualData);
					if(actualData.equals(data))
					{
						result= Constants.KEYWORD_PASS;
						sleep(3);
						break;
					}
					else
					{
						result= Constants.KEYWORD_FAIL+" -- text not verified "+actualData+" -- "+data;
					}
					//driver.findElement(By.xpath("//table//tr["+index+"]//td//.[contains(text(),'"+data+"')]")) ;
				}

				if(result.equals(Constants.KEYWORD_PASS))
				{
					break;
				}
			}
		}
		catch(Exception e)
		{
			result=  Constants.KEYWORD_FAIL+" -- text not verified ";
		}
		return result;
	}

	public String verifySearchResultsByID(String object,String data) throws InterruptedException
	{

		try{
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			//driver.findElement(By.xpath("//input[@class='input-medium']")).sendKeys(data);
			try
			{
				//driver.findElement(By.xpath("//form[@name='frmfindplan']//div[@class='center']//input[@class='btn']")).click();
				driver.findElement(By.id(OR.getProperty("common.next.btn"))).click();
			}
			catch(Exception e)
			{
				sleep(2);
				driver.findElement(By.xpath("//input[contains(@value,'Go')]")).click();
			}
			sleep(3);
			int rowSize = driver.findElements(By.xpath("//table//tbody//tr")).size();
			for(int index=1;index<=rowSize;index++)
			{
				int tdSize = driver.findElements(By.xpath("//table//tbody//tr//td")).size();
				for(int tdindex=1;tdindex<=tdSize;tdindex++)
				{
					String actualData=driver.findElement(By.xpath("//table//tbody//tr["+index+"]//td["+tdindex+"]")).getText();
					System.out.println("Actual data is"+actualData);
					System.out.println("expected data is"+data);
					if(actualData.equalsIgnoreCase(data))
					{
						result= Constants.KEYWORD_PASS;
						sleep(3);
						break;
					}
					else
					{
						result= Constants.KEYWORD_FAIL+" -- text not verified "+actualData+" -- "+data;
					}
					//driver.findElement(By.xpath("//table//tr["+index+"]//td//.[contains(text(),'"+data+"')]")) ;
				}

				if(result.equalsIgnoreCase(Constants.KEYWORD_PASS))
				{
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result=  Constants.KEYWORD_FAIL+" -- text not verified ";
		}
		return result;
	}
	
	public  String pressEnterinInputByID(String object,String data){
        APP_LOGS.debug("Pressing Tab in text box");

        //String newdata = String.valueOf(data);
        try{
        		if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
        		{
        			driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
        		}
        		else
        		{
               driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
        		}
               result=Constants.KEYWORD_PASS;
        }catch(Exception e){
               result=Constants.KEYWORD_FAIL;

        }
        return result;
	}

	public String clickModuleMenuLink(String object,String data) throws InterruptedException
    {
           object="";
           try
           {
                  /*if(driver.findElement(By.linkText(data)).isDisplayed()==true || driver.findElement(By.linkText(data)).isEnabled()  ==true ) 
                  {*/
                  
                  if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
                  {
                        
                        String windowHandle=driver.getWindowHandle();
                        driver.switchTo().window(windowHandle);
                        driver.findElement(By.linkText(data));
                        try
                        {
                        
                               driver.findElement(By.linkText(data)).sendKeys("\n");
                               sleep(3);
                               
                               List<WebElement> objElements = driver.findElements(By.linkText(data));
               				if (objElements.size()>0)
               				{
               					driver.findElement(By.linkText(data)).click();
               				}
               				else
               				{
               					result=Constants.KEYWORD_PASS;
               				}
                               
                        }
                        catch(Exception e1 )
                        {
                               driver.findElement(By.linkText(data)).click();
                        }
                        
                        //driver.findElement(By.linkText(data)).sendKeys("\n");
                        result=Constants.KEYWORD_PASS;
                  }
                  else
                  {
                  driver.findElement(By.linkText(data)).click();
                  }
                  sleep(5);

                  result=  Constants.KEYWORD_PASS;
                  //}
           }
           catch(Exception e)
           {
                  int mnuCount=driver.findElements(By.xpath("//div[@id='menu']/div/ul/li")).size();
                  for(int mnuIndex=1;mnuIndex<=mnuCount;mnuIndex++)
                  {
                        String mnuText=driver.findElement(By.xpath("//div[@id='menu']/div/ul/li["+mnuIndex+"]")).getText();
                        mnuText=mnuText.toUpperCase();
                        data=data.toUpperCase();
                        if(mnuText.equals(data))
                        {
                               if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
                               {
                                      
                                      String windowHandle=driver.getWindowHandle();
                                      driver.switchTo().window(windowHandle);
                                      try
                                      {
                                      
                                      driver.findElement(By.xpath("//div[@id='menu']/div/ul/li["+mnuIndex+"]//a")).sendKeys("\n");
                                      sleep(3);
                                      if(driver.findElement(By.xpath("//div[@id='menu']/div/ul/li["+mnuIndex+"]//a")).isDisplayed()==true)
                                      {
                                    	  driver.findElement(By.xpath("//div[@id='menu']/div/ul/li["+mnuIndex+"]//a")).click();
                                      }
                                      }
                                      catch(Exception e1 )
                                      {
                                             driver.findElement(By.xpath("//div[@id='menu']/div/ul/li["+mnuIndex+"]//a")).click();
                                      }
                                      result=Constants.KEYWORD_PASS;
                               }
                               else{
                               driver.findElement(By.xpath("//div[@id='menu']/div/ul/li["+mnuIndex+"]//a")).click();
                               result=  Constants.KEYWORD_PASS;
                               }
                               break;
                        }
                        else
                        {
                               result= Constants.KEYWORD_FAIL+"link not found";
                        }

                  }


                  //throw new AssertionError();
           }
           return result;
    }


	/*
	 * @author=Timir
	 * @date:29th Jan 2013
	 * @Purpose: Function verifies the header text by css
	 * @Return:Result=Pass/Fail 
	 */
	public String verifyTextContentByCSS(String object, String data){
		APP_LOGS.debug("Verifying the text content");
		try{
			sleep(3);
			String actual=driver.findElement(By.cssSelector(OR.getProperty(object))).getText();
			String expected=data;

			if(actual.trim().contains(expected.trim()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- text content not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}

	}


	/*
	 * @Author=Timir
	 * @Purpose: Function to wait for the object by xpath to load and visible
	 * @Return:Result=Pass/Fail 
	 */
	public String waitforElementToLoadByXpath(String object,String data)
	{

		data=CONFIG.getProperty("implicitwait");
		try
		{
			sleep(3);
			WebDriverWait waiting = new WebDriverWait(driver,Long.parseLong(data));

			waiting.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(object))));
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to wait for the object by ID to load and visible
	 * @Return:Result=Pass/Fail 
	 */
	public String waitforElementToLoadByID(String object,String data)
	{

		data=CONFIG.getProperty("implicitwait");
		try
		{
			sleep(3);
			WebDriverWait waiting = new WebDriverWait(driver,Long.parseLong(data));
			System.out.println("object name is"+object);
			String waitObject=OR.getProperty(object);
			System.out.println("waitObject name is"+waitObject);
			waiting.until(ExpectedConditions.presenceOfElementLocated(By.id(waitObject)));

			return Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to wait for the object by css to load and visible
	 * @Return:Result=Pass/Fail 
	 */
	public String waitforElementToLoadByCSS(String object,String data)
	{

		data=CONFIG.getProperty("implicitwait");
		try
		{
			sleep(3);
			WebDriverWait waiting = new WebDriverWait(driver,Long.parseLong(data));

			waiting.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(OR.getProperty(object))));
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
		}
		return result;

	}

	/*
	 * @author=Timir
	 * @Purpose: Function to Get current Date
	 * @Return:Current Date DD/MM/YYYY format
	 */
	public String getCurrentDate()
	{
		String date="";
		try{
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");


			String year = yearFormatter.format(currentDate.getTime());
			String month = monthFormatter.format(currentDate.getTime());
			String day = dayFormatter.format(currentDate.getTime());


			date=day+"/"+month+"/"+year;

		}catch(Exception e){}
		return date;

	}

	public String getCurrentDateandTime()
	{
		String date="";
		try{
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");


			String year = yearFormatter.format(currentDate.getTime());
			String month = monthFormatter.format(currentDate.getTime());
			String day = dayFormatter.format(currentDate.getTime());


			date=day+"/"+month+"/"+year;

		}catch(Exception e){}
		return date;

	}

	/*
	 * @author=Timir
	 * @Purpose: Function to Get current Date
	 * @Return:Current Date DD/MM/YYYY format
	 */
	public String getCurrentDateMMDDYYYY(String object,String data)
	{
		object="";
		data="";
		String date="";
		try{
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");


			String year = yearFormatter.format(currentDate.getTime());
			String month = monthFormatter.format(currentDate.getTime());
			String day = dayFormatter.format(currentDate.getTime());

			date=month+"/"+day+"/"+year;
		}catch(Exception e){}
		return date;

	}

	public String verifySelectedListBoxValueByXpath(String object,String data)
	{
		boolean vres=driver.findElement(By.xpath("//select[@id='"+OR.getProperty(object)+"']//option[contains(.,'"+data+"') and @selected='']")).isDisplayed();
		try{
			if(vres)
			{

				result=Constants.KEYWORD_PASS;
			}
			else
			{
				result=Constants.KEYWORD_FAIL;
			}
		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}
		return result;


	}

	public static String getDefaultPageWaitTime() {
		return CONFIG.getProperty("implicitwait").toString();
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to click on link which contains data 
	 * @Return:Result=Pass/Fail 
	 */
	public String clickByXpath_Containstext(String object,String data)throws InterruptedException
	{
		APP_LOGS.debug("Clicking on link ");
		try{
			//driver.findElement(By.linkText(OR.getProperty(object))).click();
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
			{
				
				String windowHandle=driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.xpath("//a[contains(text(),'"+data+"')]"));
				try
				{
				driver.findElement(By.xpath("//a[contains(text(),'"+data+"')]")).sendKeys("\n");
				
				sleep(3);
				List<WebElement> objElements = driver.findElements(By.xpath("//a[contains(text(),'"+data+"')]"));
				if (objElements.size()>0)
				{
					driver.findElement(By.xpath("//a[contains(text(),'"+data+"')]")).click();
				}
				else
				{
					result=Constants.KEYWORD_PASS;
				}
				
				}
				catch(Exception e)
				{
					driver.findElement(By.xpath("//a[contains(text(),'"+data+"')]")).click();
				}
				
				result=Constants.KEYWORD_PASS;
			}
			else
			{
			driver.findElement(By.xpath("//a[contains(text(),'"+data+"')]")).click();
			result=Constants.KEYWORD_PASS;
			}
		}
		catch(Exception e){
			result= Constants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
			//	throw  new NoSuchElementException("No such element found");
		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to wait for text to be present in element
	 * @Return:Result=Pass/Fail 
	 * @object: Element ID
	 * @data : Text to be present in element
	 */
	public String waitForTextInElementByID(String object,String data)
	{

		String time=CONFIG.getProperty("implicitwait");
		try
		{
			WebDriverWait waiting = new WebDriverWait(driver,Long.parseLong(time));
			System.out.println("object name is"+object);
			String waitObject=OR.getProperty(object);
			System.out.println("waitObject name is"+waitObject);
			waiting.until(ExpectedConditions.textToBePresentInElement(By.id(OR.getProperty(object)), data));

			return Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String doLogout(String object,String data)
	{

		try
		{

			driver.findElement(By.linkText(OR.getProperty(object))).click();
			sleep(6);
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			result=result=Constants.KEYWORD_FAIL;
		}
		return result;

	}

	/*
	 * @author=Timir
	 * @Purpose: Function to exact mathc the expected and actual text value
	 * @Return:Result=Pass/Fail 
	 */
	public  String verifyexactTextMatch(String object, String data){
		APP_LOGS.debug("Verifying the text");
		try{
			sleep(3);
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText().trim();
			String expected=data;

			if(actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to swithc to frame and choose address for broker business address
	 * @Return:Result=Pass/Fail 
	 */
	public String switchToFramewithID(String object, String data){

		String status = "";
		try{
			sleep(3);
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_TAB);

			//List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#success"));
			List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#modalData"));
			if(objFrameContainer.size()>0){
				sleep(3);
				System.out.println("IFRAME FOUND");

				driver.switchTo().frame(objFrameContainer.get(0));
				//pause(6000);
				driver.findElement(By.id(OR.getProperty("broker.certificationinformation.checkyouraddress.likelymatchs.opt"))).click();
				driver.findElement(By.id(OR.getProperty("broker.certificationinformation.checkyouraddress.submitAddr.btn"))).click();
			}
			else{
				status = Constants.KEYWORD_FAIL;
			}			
		}
		catch(Exception e){
			status = Constants.KEYWORD_FAIL + "-Object Not found";
		}

		System.out.println("STATUS:" + status);
		return status;

	}

	
	
	public String closePopUpBoxForSelectAddress(String object, String data){

		String status = "";

		try{
			sleep(3);
			//List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#success"));
			List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#modalData"));
			if(objFrameContainer.size()>0){
				System.out.println("IFRAME FOUND");

				driver.switchTo().frame(objFrameContainer.get(0));

				List<WebElement> objClose = driver.findElements(By.id("userdefault"));

				try
				{
					if(objClose.size()>0){
						System.out.println("OPTION BUTTON FOUND FOR YOU ENTERED ADDRESS");					
						//ClickWebElement(objClose.get(0));
						String windowHandle=driver.getWindowHandle();
						WebElement objElement = objClose.get(0);
						Actions builder = new Actions(driver);   
						builder.moveToElement(objElement).build().perform();
						JavascriptExecutor js = (JavascriptExecutor) driver;
						js.executeScript("var evt = document.createEvent('MouseEvents');" + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" + "arguments[0].dispatchEvent(evt);", objElement);

						//driver.switchTo().frame(objFrameContainer.get(0));
						driver.findElement(By.id("submitAddr")).click();

						driver.findElement(By.id("iFrameClose")).click();
						driver.switchTo().window(windowHandle);
						//	pause(3000);

						status = Constants.KEYWORD_PASS;
					}
				}
				catch(Exception e)
				{

					System.out.println(e+"Frame not found");
				}
			}
			else{
				status = Constants.KEYWORD_FAIL;
			}			
		}
		catch(Exception e){
			status = Constants.KEYWORD_FAIL + "-Object Not found";
		}

		System.out.println("STATUS:" + status);
		return status;

	}

	public String existByLinkText(String object,String data){
		APP_LOGS.debug("Checking existance of element");
		try{
			driver.findElement(By.linkText(OR.getProperty(object)));
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object doest not exist";
		}


		return Constants.KEYWORD_PASS;
	}

	

	public boolean setCellData(String path,String sheetName,String colName,int rowNum, String data){
		try{
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);

			if(rowNum<=0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;


			sheet = workbook.getSheetAt(index);


			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				//if(row.getCell(i).getStringCellValue().trim().equals(colName))
				if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum=i;
			}
			if(colNum==-1)
				return false;

			sheet.autoSizeColumn(colNum); 
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);

			cell = row.getCell(colNum);	
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to get referrence flag from test case sheet
	 * @Return:Result=Pass/Fail 
	 */
	public String getRefFlag(String object,String Data) throws IOException
	{
		String refFlag =null;
		String refFlagdata=null;
		int columnid=0;
		try{
			Xls_Reader a=DriverScript.currentTestSuiteXLS;

			//Xls_Reader currentTestSuiteXLS=new Xls_Reader(obj1.currentTestSuiteXLS) ;

			currentTestSuiteXLS  =  new Xls_Reader(DriverScript.refexcelfilename);
			String tcSheetName=DriverScript.refexcelsheetname;
			fis = new FileInputStream(currentTestSuiteXLS.path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(tcSheetName);
			//To find out index of refFlag column

			//System.out.println("currentTestSuiteXLS"+currentTestSuiteXLS);

			//System.out.println("cols"+currentTestSuiteXLS.getColumnCount(tcSheetName));
			int colindex=0;
			int newcolindex=0;
			startIndex=0;
			for(colindex=startIndex;colindex<=currentTestSuiteXLS.getColumnCount(tcSheetName);colindex++)
			{
				//System.out.println("sheetname is"+sheet.getSheetName());

				refFlag=currentTestSuiteXLS.getCellData(tcSheetName,colindex,1);
				//if refFlag column found goes to inner condition
				//System.out.println("refflag"+refFlag);
				if(refFlag.equalsIgnoreCase("RefFlag")==true && refFlag.isEmpty()==false)
				{


					newcolindex=colindex;
					colindex=colindex+1;
					result="Y";
					//break;
				}
				else
				{
					result="N";	


				}


				if(result=="Y")
				{
					//check for Y flag exist or not under refFlag column 

					//System.out.println(currentTestSuiteXLS);
					for(int rowindex=2;rowindex<=currentTestSuiteXLS.getRowCount(tcSheetName);rowindex++)
					{
						refFlagdata=currentTestSuiteXLS.getCellData(tcSheetName, newcolindex, rowindex);
						//if reflfagdata=Y goes to inner condition
						//System.out.println("RefFlagData"+refFlagdata);
						if(refFlagdata.equalsIgnoreCase("Y"))
						{

							//return tcid for that row
							String tcids=currentTestSuiteXLS.getCellData(tcSheetName, newcolindex+1, rowindex);
							//String tcids=currentTestSuiteXLS.getCellData(tcSheetName,"RefTCID",rowindex);
							//moves for the column referrence name to be reflect
							//for(int firstcolindex=0;firstcolindex<newcolindex;firstcolindex++)
							for(int firstcolindex=startIndex;firstcolindex<newcolindex;firstcolindex++)
							{

								String tcdata=currentTestSuiteXLS.getCellData(tcSheetName,firstcolindex,rowindex);

								String cols=currentTestSuiteXLS.getCellData(tcSheetName,firstcolindex,1);
								setRefData(DriverScript.refexcelfilename,tcids,cols,rowindex,tcdata);
							}
						}
					}
					//fis.close();
					startIndex=newcolindex+2;
				}	

				/*else if(result=="N" || result.equals("N"))
			{
				startIndex=startIndex+1;
			}*/

			}
			fis.close();
			result=Constants.KEYWORD_PASS;
		}catch(Exception e)
		{
			System.out.println("Error in getRefFlag" +e.getMessage());
		}
		return refFlag;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to setref data for child sheets
	 * @Return:Result=Pass/Fail 
	 */
	public void setRefData(String path,String ReftcID,String col,int row,String tcdata) throws IOException
	{
		try{
			//currentTestSuiteXLS=new Xls_Reader(System.getProperty("user.dir")+"//src//"+currentTestSuite+".xlsx");
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			//sheet = workbook.getSheet(tcSheetName);
			//System.out.println("workbook name is"+workbook.getActiveSheetIndex());
			String associatetestCaseIDS=ReftcID;
			String [] items = associatetestCaseIDS.split(",");     
			List<String> container = Arrays.asList(items);
			for(int index=0;index<container.size();index++)
			{
				sheet=workbook.getSheet(container.get(index).trim());
				String sheetname=sheet.getSheetName();
				for(int rowchild=row;rowchild<=currentTestSuiteXLS.getRowCount(sheetname);rowchild++)
				{
					//System.out.println("UPDATING: sheet"+sheetname+"Col:"+col+"Data"+tcdata);
					setCellData(path,sheetname,col,rowchild,tcdata);
					//System.out.println("UPDATION Success");
				}
				//Jump to every parent sheet and get reference datarefereence sheet and set auto generated data
				// String refData=getParentSheetData(String tcID,String col,int row);

			}
		}catch(Exception e){
			System.out.println("Error in setRefData" +e.getMessage());
		}


	}

	public static String getLinksFromNotices(String text) {
		//ArrayList links = new ArrayList();
		String urlStr = null;
		try{
			String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(text);
			while(m.find()) {
				urlStr = m.group();
				System.out.println("urlStr:"+urlStr);
			}
		}catch(Exception e){urlStr ="";}
		return urlStr;
	}

	 

	public String verifyTextNotContain(String object, String data){
		APP_LOGS.debug("Verifying the text");
		try{
			sleep(3);
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText().trim();
			String expected=data;

			if(!actual.equalsIgnoreCase(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- text verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to wait for the object by Link Text to load and visible
	 * @Return:Result=Pass/Fail 
	 */
	public String waitforElementToLoadByLink(String object,String data)
	{

		data=CONFIG.getProperty("implicitwait");
		try
		{
			sleep(3);
			WebDriverWait waiting = new WebDriverWait(driver,Long.parseLong(data));

			waiting.until(ExpectedConditions.presenceOfElementLocated(By.linkText(OR.getProperty(object))));
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
		}
		return result;

	}

	public  String verifyListSelectionByID(String object,String data){
		APP_LOGS.debug("Verifying all the list elements");
		try{
			String expectedVal=data;
			//System.out.println(driver.findElement(By.xpath(OR.getProperty(object))).getText());
			WebElement droplist= driver.findElement(By.id(OR.getProperty(object))); 
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			String actualVal=null;
			for(int i=0;i<droplist_cotents.size();i++){
				String selected_status=droplist_cotents.get(i).getAttribute("selected");
				if(selected_status!=null)
					actualVal = droplist_cotents.get(i).getText();                                      
			}

			if(!actualVal.equals(expectedVal))
				return Constants.KEYWORD_FAIL + "Value not in list - "+expectedVal;

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not find list. "+ e.getMessage();      

		}
		return Constants.KEYWORD_PASS;         

	}

	/*
	 * @author=Timir
	 * @Purpose: To Open link in New tab
	 * @Return:Result=Pass/Fail 
	 */
	public void openUrlInTab(String url) {
		try{
			String script = "var anchor=document.createElement('a');anchor.target='_blank';anchor.href='%s';anchor.innerHTML='.';document.body.appendChild(anchor);return anchor";
			Object element = ((JavascriptExecutor)driver).executeScript(String.format(script, url));

			if (element instanceof WebElement) {
				WebElement anchor = (WebElement) element; 
				anchor.click();
				((JavascriptExecutor)driver).executeScript("var a=arguments[0];a.parentNode.removeChild(a);", anchor);
			} 
		}catch(Exception  e){

		}

	}


	public void setSpeed()
	{
		try
		{
			long secTime=Long.parseLong(CONFIG.getProperty("setspeed"));
			driver.manage().timeouts().implicitlyWait(secTime, TimeUnit.SECONDS);
		}
		catch(Exception e)
		{
			System.out.println("Fail"+e);
		}
	}


	public static void killProcess(String serviceName) throws Exception {
		try{
			Runtime.getRuntime().exec(KILL + serviceName);
		}catch(Exception e){}
	}


	public static boolean isProcessRunging(String serviceName) throws Exception {
		try{
			Process p = Runtime.getRuntime().exec(TASKLIST);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {

				System.out.println(line);
				if (line.contains(serviceName)) {
					killProcess(serviceName);
					return true;
				}
			}

			return false;
		}catch(Exception e){
			return false;
		}
	}


	public String getAlertText(String object,String data)
	{
		object="";
		data="";

		try
		{
			driver.switchTo().alert();
			String alerttext=driver.switchTo().alert().getText();
			System.out.println("Alert text is"+alerttext);
			driver.switchTo().alert().accept();
			result=Constants.KEYWORD_PASS;
		}

		catch(Exception e)
		{
			System.out.println("Not able to get text from alert"+e);
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}
	public String concateValue(String object,String data)
	{
		try
		{

			currentTestSuiteXLS  =  new Xls_Reader(DriverScript.refexcelfilename);
			String allElements[]=object.split(",");
			String[] datanew=new String[2];
			String concatdata=null;
			datanew[0]=currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName,allElements[0].trim() ,DriverScript.datarowid  );
			datanew[1]=currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName,allElements[1].trim() ,DriverScript.datarowid  );
			concatdata=datanew[0]+" "+datanew[1];
			String actualval=driver.findElement(By.cssSelector(OR.getProperty("common.main.hdr"))).getText();
			if(actualval.equalsIgnoreCase(concatdata))
			{
				result=	Constants.KEYWORD_PASS;
			}
			else
			{
				result=	Constants.KEYWORD_FAIL;
			}
			return result;
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}
	public String assertIfObjectFoundByCSS(String object, String data){
		try{
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);		
			List<WebElement> objElements = driver.findElements(By.cssSelector(OR.getProperty(object)));		
			if (objElements.size()>0){			
				result= Constants.KEYWORD_PASS;
			}else{			
				System.out.println(Constants.KEYWORD_FAIL + " - Object not found");
				result= Constants.KEYWORD_FAIL + " - Object not found";
			}
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String reloadPage(String object,String data)
	{
		try
		{
			driver.navigate().refresh();
			result="PASS";
		}
		catch(Exception e)
		{
			result="FAIL";
		}
		return result;
	}

	public  String clicklinkData(String object,String data){
		APP_LOGS.debug("Clicking on Button");
		try{
			
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
			{
				
				String windowHandle=driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.xpath(OR.getProperty(object)+"//a[contains(text(),'"+data+"')]"));
				try
				{
					
					driver.findElement(By.xpath(OR.getProperty(object)+"//a[contains(text(),'"+data+"')]")).sendKeys("\n");
					sleep(3);
					List<WebElement> objElements = driver.findElements(By.xpath(OR.getProperty(object)+"//a[contains(text(),'"+data+"')]"));
					if (objElements.size()>0)
					{
						driver.findElement(By.xpath(OR.getProperty(object)+"//a[contains(text(),'"+data+"')]")).click();
					}
					else
					{
						result=Constants.KEYWORD_PASS;
					}
					
				}
				catch(Exception e)
				{
					driver.findElement(By.xpath(OR.getProperty(object)+"//a[contains(text(),'"+data+"')]")).click();	
				}
					
				result=Constants.KEYWORD_PASS;
			}
			else
			{
			driver.findElement(By.xpath(OR.getProperty(object)+"//a[contains(text(),'"+data+"')]")).click();
			//td[contains(@id, 'name')]//a[contains(text(),'ISR36011')]
			}
			sleep(8);
			result=Constants.KEYWORD_PASS;

		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL +" -- Not able to click on Button"+e.getMessage();;
			//	throw  new NoSuchElementException("No such element found");

		}


		return result;
	}

	/*@author= Timir
	 *@Purpose:Function to click menu link
	 *@Returns: Pass if Menu link found . Returns fail if menu link not found
	 */
	public String clickMenuLink(String mnuLink,String pageTitle){
		String titleResult=Constants.KEYWORD_FAIL;
		APP_LOGS.debug("Clicking on Menu link ");
		try{
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
			{
				
				String windowHandle=driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.linkText(mnuLink));
				driver.findElement(By.linkText(mnuLink)).click();	
				//sleep(2);
				result=Constants.KEYWORD_PASS;
			}
			else
			{
			driver.findElement(By.linkText(mnuLink)).click();
			}
			sleep(2);
			result=Constants.KEYWORD_PASS;
			if(result.equalsIgnoreCase(Constants.KEYWORD_PASS))
			{
				titleResult=getPageTitle(pageTitle);
			}
			if(titleResult.equalsIgnoreCase("PASS") )
			{
				result="PASS";
			}
			return result;
		}
		catch(Exception e){
			result= Constants.KEYWORD_FAIL+" -- Either not able to click on link "+mnuLink;
			//	throw  new NoSuchElementException("No such element found");
		}


		return result;
	}
	/*@author= Timir
	 *@Purpose:Function to veriy page title
	 *@Returns: Pass if match found for expected page title else fail 
	 */
	public String getPageTitle(String data)
	{
		try
		{
			String pageTitle=driver.findElement(By.cssSelector("h1")).getText();
			if(pageTitle.contains(data)||pageTitle.equalsIgnoreCase(data))
			{
				result=Constants.KEYWORD_PASS;
			}
			else
			{
				result=Constants.KEYWORD_FAIL+"Page title does not match";
			}
		}

		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"Object not found";
		}
		return result;
	}

	/*@author=Timir 
	 *@Purpose:Function to click button by className
	 */

	/*	public  String clickButtonByClassName(String object,String data){
			APP_LOGS.debug("Clicking on DeleteButton");
			try{
				driver.findElement(By.className(OR.getProperty(object))).click();
				sleep(8);
				result=Constants.KEYWORD_PASS;

			}
			catch(Exception e)
			{
				result=Constants.KEYWORD_FAIL +" -- Not able to click on Button"+e.getMessage();;
				//	throw  new NoSuchElementException("No such element found");

			}


			return result;

		}
	 */

	/*@author= Timir
	 *@Purpose:Function to veriy text not present
	 * @Returns: Pass if text not present else fail
	 */  
	public String verifyTextNotPresent(String object,String data)
	{
		try
		{
			int totalSize=driver.findElements(By.xpath(OR.getProperty(object))).size();

			for(int index=1;index<=totalSize;index++)
			{
				String actualText=driver.findElement(By.xpath(OR.getProperty(object))).getText();
				if(!actualText.contains(data)|| !actualText.equalsIgnoreCase(data))
				{
					result=Constants.KEYWORD_PASS+"unexpected Text is not exist";
				}
				else
				{
					result=Constants.KEYWORD_FAIL+"On Para/List"+index+"unexpected text is present";
					break;
				}
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"Object"+OR.getProperty(object)+"is not found or no longer exist";
		}
		return result;

	}


	/*@author= Timir
	 *@Purpose:Function to verify element is present by contains
	 *@Returns: Pass in case of element with contain is present else fail
	 */ 
	public String verifyElementPresentByContains(String object, String data){
		APP_LOGS.debug("Verifying Element Present By Text Contains");
		try{
			sleep(3);
			if(driver.findElement(By.xpath(OR.getProperty(object)+"[contains(.,'"+data+"')]")).isDisplayed() || driver.findElement(By.xpath(OR.getProperty(object)+"[contains(.,'"+data+"')]")).isEnabled())
			{
				return Constants.KEYWORD_PASS;
			}
			else
			{

				return Constants.KEYWORD_FAIL;
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}

	}

	/*@author= Timir
	 *@Purpose:Function to verify element is not present by contains
	 * @Returns: Pass in case of element with contain is not present else fail
	 */ 
	public String verifyElementNotPresentByContains(String object, String data){
		APP_LOGS.debug("Verifying Element Present By Text Contains");
		try{
			sleep(3);
			if(driver.findElement(By.xpath(OR.getProperty(object)+"[contains(.,'"+data+"')]")).isDisplayed() || driver.findElement(By.xpath(OR.getProperty(object)+"[contains(.,'"+data+"')]")).isEnabled())
			{
				return Constants.KEYWORD_FAIL;
			}
			else
			{

				return Constants.KEYWORD_PASS;
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}

	}

	/*
	 * @author=Timir
	 * @Purpose: The "object" is searched by ID 
	 * Function to match the data , if data is unequal then the function returns PASS
	 * @Return:Result=Pass/Fail 
	 */
	public String verifyTextNotContainById(String object, String data){
		APP_LOGS.debug("Verifying the text");
		try{
			sleep(3);
			String actual=driver.findElement(By.id(OR.getProperty(object))).getText().trim();
			String expected=data;

			if(!actual.equalsIgnoreCase(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- text verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	public  String getValueByXpath(String object, String data){
		APP_LOGS.debug("Gets value from text box");
		try{
			String actualtext=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			if(actualtext.equalsIgnoreCase(data)){
				System.out.print("Current value"+actualtext);
				currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, actualtext);
				result= Constants.KEYWORD_PASS;	
			}
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL +"- Not able to get text";	

		}

		return result;

	}

	public String switchToFrameusingID(String object, String data){

		//String status = "";
		
			try
			{
				Set<String> windowids = driver.getWindowHandles();

				Iterator<String> iter= windowids.iterator();
				while(iter.hasNext()){
					System.out.println("next iterator is"+iter.next());


				}
				List<WebElement> objFrameContainer = driver.findElements(By.id(OR.getProperty(object)));
				
				if(objFrameContainer.size()>0)
				{
					System.out.println("IFRAME FOUND");

					driver.switchTo().frame(objFrameContainer.get(0));
					result=Constants.KEYWORD_PASS;
				}
				else
				{
					result=Constants.KEYWORD_FAIL;
				}
			
		}
		catch(Exception e){
			result=Constants.KEYWORD_FAIL + "Frame not fount";
		}

		//System.out.println("STATUS:" + status);
		return result;

	}

	/*@author=Timir 
	 *@Purpose:Function to fill the data for broker status search
	 */

	public  String pressTabinInputByID(String object,String data){
		APP_LOGS.debug("Pressing Tab in text box");

		//String newdata = String.valueOf(data);
		try{


			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			result=Constants.KEYWORD_PASS;
		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;

		}
		return result;
	}

	public String getWindowHandles(String object,String data)
	{
		object="";

		try
		{
			windowHandle=driver.getWindowHandle();
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;

		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: clear sata of filds by id 
	 */
	public  String clearTextByID(String object,String data){
		APP_LOGS.debug("Clearing the text from input field");
		try{
			driver.findElement(By.id(OR.getProperty(object))).clear();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to clear";
		}
		return Constants.KEYWORD_PASS;
	}

	public String verifyErrorMsgById(String object, String data) throws IOException {

		try {

			sleep(3);
			APP_LOGS.debug("verify error message");
			FileInputStream fs1 = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//message.properties");
			MSG= new Properties();
			MSG.load(fs1);

			for(int index=0;index<=MSG.size();index++)
			{
				String expectedMsg=MSG.getProperty("msg"+index);
				APP_LOGS.debug("Expected Error Message :" + expectedMsg);
				String actgualMsg=driver.findElement(By.id(OR.getProperty(object))).getText();
				APP_LOGS.debug("Actual Error Message :" + actgualMsg);
				if(actgualMsg.equals(expectedMsg) )
				{
					result=Constants.KEYWORD_PASS;
					break;

				}
				else
				{
					result=Constants.KEYWORD_FAIL;
				}
			}
			fs1.close();
		}
		catch (Exception e) {
			result=Constants.KEYWORD_FAIL + "Error -->" + e.getMessage();
		}
		return result;

	}

	/*
	 * @author=Timir
	 * @Purpose: Function to verify object is not present by css
	 * @Return:Result=Pass/Fail 
	 */

	public String assertIfObjectNotFoundByCSS(String object, String data){
		try{
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);		
			List<WebElement> objElements = driver.findElements(By.cssSelector(OR.getProperty(object)));		
			if (objElements.size()>0){			
				result= Constants.KEYWORD_FAIL;
			}else{			
				System.out.println(Constants.KEYWORD_PASS + " - Object not found");
				result= Constants.KEYWORD_PASS + " - Object not found";
			}

		}catch(Exception e){
			result= Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to verify sorted data
	 * @Return:Result=Pass/Fail 
	 */

	
	public String verifyAllListElementsByID(String object, String data){
		APP_LOGS.debug("Verifying the selection of the list");
		try{	
			sleep(1);
			WebElement droplist= driver.findElement(By.id(OR.getProperty(object))); 
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));

			// extract the expected values from OR. properties
			String temp=data;
			String allElements[]=temp.split(",");
			// check if size of array == size if list
			if(allElements.length != droplist_cotents.size())
				return Constants.KEYWORD_FAIL +"- size of lists do not match";	

			for(int i=0;i<droplist_cotents.size();i++){
				if(!((allElements[i]).trim()).equals((droplist_cotents.get(i).getText()).trim())){
					return Constants.KEYWORD_FAIL +"- Element not found - "+allElements[i];
				}
			}
		}catch(Exception e){
			System.out.println(" - Could not select from list. "+ e.getMessage());
			return Constants.KEYWORD_FAIL ;	

		}


		return Constants.KEYWORD_PASS;	
	}

	/*
	 * @author=Timir
	 * @Purpose: To verify column Name of grid(Table) present on any page
	 * @object:Should be blank 
	 * @data:Column name to verify
	 * @Return: Pass if column is present else Fail
	 */
	public  String verifyTableColumnName(String object,String data){
		APP_LOGS.debug("Verify columns are present in table");
		try{
			int totalColumns =driver.findElements(By.xpath(OR.getProperty("common.table.col.hdr"))).size();

			for(int col=1;col<=totalColumns;col++)
			{
				String actualColName=driver.findElement(By.xpath(OR.getProperty("common.table.col.hdr")+"["+col+"]")).getText();

				if(actualColName.trim().equalsIgnoreCase(data.trim()))
				{
					result=Constants.KEYWORD_PASS;
					break;
				}
				else
				{
					result=Constants.KEYWORD_FAIL;
				}
			}

			return result;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to clear";
		}
	}

	public String getUrlandNavigate(String object,String data) {
		APP_LOGS.debug("get current URL");
		System.out.println("getting url");

		try{
			String url =driver.getCurrentUrl();
			doLogout("commomn.logout.link", data);
			sleep(3);

			driver.navigate().to(url);
			sleep(3);
			System.out.println("the current url is"+url);
			result = Constants.KEYWORD_PASS;
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL+" -- Not able to navigate";
		}
		return result;
	}
	/*
	 * @author=Timir
	 * @Purpose: Function to verify error messge on base of different valid/invalid input
	 * @Return:Result=Pass/Fail 
	 */
	public String verifyErrorOnDataInput(String object,String data)
	{
		try
		{
			currentTestSuiteXLS  =  new Xls_Reader(DriverScript.refexcelfilename);
			String errorflag=currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName,"errorFlag" ,DriverScript.datarowid);
			if(errorflag.equalsIgnoreCase("Y"))
			{
				result=assertIfObjectFound(object, data);

			}
			else
			{
				result=Constants.KEYWORD_PASS;
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"Error messge not found";
		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: To verify if the passed VALUE in data parameter is present on the radio button
	 * @object:It should be each radio option 
	 * @data:Option VALUE to be verified
	 * @Return: Pass if VALUE is present else Fail
	 */
	public  String verifyRadioButtonValue(String object, String data){
		APP_LOGS.debug("Selecting a radio button");
		try{

			String radiovalue=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			String expected=data;
			if(radiovalue.equalsIgnoreCase(expected))

				return Constants.KEYWORD_PASS;	
			else
				return Constants.KEYWORD_FAIL +"- Radio Button Value does not match";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}



	}

	/*
	 * @author=Timir
	 * @Purpose: Function verifies the header text by css
	 * @Return:Result=Pass/Fail 
	 */
	public String verifyTextContentByID(String object, String data){
		APP_LOGS.debug("Verifying the text content");
		try{
			sleep(3);
			String actual=driver.findElement(By.id(OR.getProperty(object))).getText();
			actual=actual.toUpperCase();
			String expected=data;
			expected=expected.toUpperCase();
			if(actual.trim().contains(expected.trim()))
			{
				result= Constants.KEYWORD_PASS;
			}
			else
			{
				result=Constants.KEYWORD_FAIL+" -- text content not verified "+actual+" -- "+expected;
			}
		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to verify error messge on base of different valid/invalid input by ID
	 * @Return:Result=Pass/Fail 
	 */
	public String verifyErrorOnDataInputByID(String object,String data)
	{
		try
		{
			currentTestSuiteXLS  =  new Xls_Reader(DriverScript.refexcelfilename);
			String errorflag=currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName,"errorFlag" ,DriverScript.datarowid);
			if(errorflag.equalsIgnoreCase("Y"))
			{
				result=assertIfObjectFoundByID(object, data);

			}
			else
			{
				result=Constants.KEYWORD_PASS;
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"Error messge not found";
		}
		return result;
	}

	//End of Keyword

	/*
	 * @author=Timir
	 * @Purpose: Function to verify if a particular object is displayed/Hidden or not.
	 * @Return:Result=Pass/Fail 
	 */
	public  String isHiddenByID(String object, String data){
		APP_LOGS.debug("Object is displayed or not");
		try{
			boolean hide;
			hide=driver.findElement(By.id(OR.getProperty(object))).isDisplayed();
			if(hide==false)

				return Constants.KEYWORD_PASS;	
			else
				return Constants.KEYWORD_FAIL +"- Object is not hidden";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Object not found";	

		}	

	}
	/*
	 * @author=Timir
	 * @Purpose: Function to close AHPX close box
	 * @Return:Result=Pass/Fail 
	 */
	public String closeAHPXSuccessbox(String object, String Data)
	{

		try
		{
			sleep(5);
			if(driver.findElement(By.cssSelector(OR.getProperty(object))).isDisplayed())
			{
				driver.findElement(By.cssSelector(OR.getProperty(object))).click();
				result=Constants.KEYWORD_PASS;
			}
			else
			{
				result=Constants.KEYWORD_PASS;
			}
		}	
		catch(Exception e)
		{
			result=Constants.KEYWORD_PASS;
		}
		return result;
	}

	/*
	/*
	 * @author=Timir
	 * @Purpose: To connect with database
	 * @object:Should be blank 
	 * @data:Should be blank 
	 * @Return: connection string with the database
	 */
	public Connection connectToDatabase(String object,String data)
	{
		APP_LOGS.debug("Establish connection to database");
		Connection conn = null;
		String Host=null;
		String Port=null;
		String 	SID=null;
		
		String environment=CONFIG.getProperty("URL_webApp");
		environment=environment.toUpperCase();
		environment=environment.substring(7,environment.lastIndexOf("."));
		System.out.println("environemnt");
		//String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";


		try{


			String userName = "", password = "" ;

			//For QANM
			if(environment.equalsIgnoreCase("synoverge"))
			{
				Host=CONFIG.getProperty("QA_DB_Host");
				Port=CONFIG.getProperty("QA_DB_Port");
				SID=CONFIG.getProperty("QA_DB_SID");
				userName = CONFIG.getProperty("QA_DB_UserName");
				password = CONFIG.getProperty("QA_DB_Password");


			}
			
			String url = "jdbc:sqlserver://SVT-SRV-55:1433;DatabaseName=iFormsQA";
			//Class.forName(driver).newInstance();// create object of Driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url,userName,password);
			// connection will be established

			System.out.println("Connected to "+environment+" Database ");

		}catch(Exception e){
			try {
				conn.close();
			} catch (SQLException e1) {
				System.out.println("Unable to close the Connection");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Unable to Connect to Database ");
			e.printStackTrace();
		}
		return conn;  
	}



	public Connection DatabaseConnection(String object,String data)
	{
		APP_LOGS.debug("Establish connection to database");
		Connection conn = null;
		//String Host=null;
		//String Port=null;
		//String 	SID=null;
		
		//String environment=CONFIG.getProperty("URL_webApp");
		//environment=environment.toUpperCase();
	//	environment=environment.substring(7,environment.lastIndexOf("."));
	//	System.out.println("environemnt");
		//String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";


		try{


			String userName = "", password = "" ;

			//For QANM
			//if(environment.equalsIgnoreCase("synoverge"))
			//{
				//Host=CONFIG.getProperty("QA_DB_Host");
				//Port=CONFIG.getProperty("QA_DB_Port");
				//SID=CONFIG.getProperty("QA_DB_SID");
				userName = CONFIG.getProperty("QA_DB_UserName");
				password = CONFIG.getProperty("QA_DB_Password");


			//}
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			  String url = "jdbc:sqlserver://SVT-SRV-55:1433;DatabaseName=iFormsQA";
			//Class.forName(driver).newInstance();// create object of Driver
			  //con = DriverManager.getConnection(url, "sa", "Synoverge@1");
			conn = DriverManager.getConnection(url,userName,password);
			// connection will be established

		//	System.out.println("Connected to "+environment+" Database ");

		}catch(Exception e){
			try {
				conn.close();
			} catch (SQLException e1) {
				System.out.println("Unable to close the Connection");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Unable to Connect to Database ");
			e.printStackTrace();
		}
		return conn;  
	}


	
	public  String verifyWithGlobalVariable(String object, String data){
		APP_LOGS.debug("Verifying with the Global Variable");
		try{
			String actual="";
			String blank="";
			if (object.equals(blank) || (object==null))
			{
				actual=data;
			}
			else
			{
				actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			}
			System.out.println("actual: " +actual);
			System.out.println("expected: " +globalValue);
			if(globalValue.equalsIgnoreCase(actual))

				return Constants.KEYWORD_PASS;	
			else
				return Constants.KEYWORD_FAIL +globalValue +"- Value does not match " +actual;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Value does not match";	

		}

	}

	//End of Keyword

	/*
	 * @author=Timir
	 * @Purpose: Function to check if the data or the globalvalue are subset of eachother. 
	 * @Return:Result=Pass/Fail 
	 */

	public  String verifyContainsGlobalVariable(String object, String data){
		APP_LOGS.debug("Verifying with the Global Variable");
		try{
			String actual="";
			String blank="";
			if (object.equals(blank) || (object==null))
			{
				actual=data;
			}
			else
			{
				actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			}
			Scanner in = new Scanner(actual).useDelimiter("[^0-9]+");
			actual=String.valueOf(in.nextInt());
			System.out.println("actaal my is "+actual);
			if((actual.contains(globalValue))||(globalValue.contains(actual)) )

				return Constants.KEYWORD_PASS;	
			else
				return Constants.KEYWORD_FAIL +globalValue + "   - Value doesn't contain in actual -   " +actual ;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Value does not match";	

		}

	}
	//End of Keyword

	/*
	 * @author=Timir
	 * @Purpose: Function to compare the data with the value in  Global Variable 
	 * @Return:Result=Pass/Fail 
	 */

	public String verifyRadioButtonSelected(String object,String data)
	{
		APP_LOGS.debug("Verifying radio button is clicked");

		try
		{

			driver.findElement(By.xpath(OR.getProperty(object))).click();

			boolean radresult=driver.findElement(By.xpath(OR.getProperty(object))).isSelected();

			if(radresult==true)
			{
				result=Constants.KEYWORD_PASS;
			}
			else
			{
				result=Constants.KEYWORD_FAIL;
			}
		}              
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to verify radio button is not clicked
	 * @Return:Result=Pass/Fail 
	 */

	public String verifyRadioButtonNotSelected(String object,String data)
	{
		APP_LOGS.debug("Verifying radio button is clicked");

		try
		{

			driver.findElement(By.xpath(OR.getProperty(object))).click();

			boolean radresult=driver.findElement(By.xpath(OR.getProperty(object))).isSelected();

			if(!radresult)
			{
				result=Constants.KEYWORD_PASS;
			}
			else
			{
				result=Constants.KEYWORD_FAIL;
			}
		}              
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

	//End of Keyword

	/*
	 * @author=Timir
	 * @Purpose: Function to verify if a particular object is not Hidden.
	 * @Return:Result=Pass/Fail 
	 */
	public  String isNotHiddenByID(String object, String data){
		APP_LOGS.debug("Object is displayed");
		try{
			boolean visible;
			visible=driver.findElement(By.id(OR.getProperty(object))).isDisplayed();
			if(visible==true)				
				return Constants.KEYWORD_PASS+"- Object is not displayed";	
			else
				return Constants.KEYWORD_FAIL +"- Object is hidden";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Object not found";	

		}	

	}//End of Keyword


	/*
	 * @author=Timir
	 * @Purpose: Function to wait for the object by xpath to load and visible
	 * @Return:Result=Pass/Fail 
	 */
	public String waitforElementToDisplayByXpath(String object,String data)
	{
		int maxWait=waitforelement;


		try
		{
			if(object.equalsIgnoreCase("individual.plandisplay.planselection.addtocart.btn"))
			{
				maxWait=60;
			}

			for(int i=1;i<=maxWait;i++){
				try
				{
					if(driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed()==true){
						result=Constants.KEYWORD_PASS;
						sleep(3);
						break;
					}
					else{
						sleep(1);
					}

					if(i==maxWait){
						result=Constants.KEYWORD_FAIL;
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"in catch";

		}
		return result;
	}

	public  String waitForElementNotVisibility(String object,String data){
		APP_LOGS.debug("Waiting for an element to be visible");
		int start=0;
		//int time=(int)Double.parseDouble(data);
//		int time=Integer.parseInt(data);
		int time=waitforelement;
		try{
			sleep(3);
			while(time != start){
				if(driver.findElements(By.xpath(OR.getProperty(object))).size() != 0){
					sleep(1);
					start++;
				}else{
					break;
				}
			}
		}catch(Exception e){
			System.out.println("Unable to find the object"+e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to create Individual basic information
	 * @Return:Result=Pass/Fail 
	 */

	public String verifyRadioButtonSelectedByID(String object,String data)
	{
		APP_LOGS.debug("Verifying radio button is clicked");

		try
		{

			driver.findElement(By.id(OR.getProperty(object))).click();

			boolean radresult=driver.findElement(By.id(OR.getProperty(object))).isSelected();

			if(radresult==true)
			{
				result=Constants.KEYWORD_PASS;
			}
			else
			{
				result=Constants.KEYWORD_FAIL;
			}
		}              
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}
	/*
	 * @author=Timir
	 * @Purpose: Function to read error message by id
	 */
	public String validateAllErrorMessageByid(String object, String data) throws IOException {

		try{

			ArrayList<String> lstActualError = new ArrayList<String>();
			ArrayList<String> lstErrorMessages = new ArrayList<String>();
			FileInputStream fs1 = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//message.properties");
			msgCONFIG= new Properties();
			msgCONFIG.load(fs1);


			for(int index=0;index<=msgCONFIG.size();index++)
			{
				lstErrorMessages.add(msgCONFIG.getProperty("msg"+index));

			}
			List<WebElement> objErrors = driver.findElements(By.id(OR.getProperty(object)));

			if (objErrors.size() > 0 ){
				//System.out.println(objErrors.size());
				for (int i=0; i<objErrors.size(); i++){
					lstActualError.add(objErrors.get(i).getText().toString());
					System.out.println (objErrors.get(i).getText().toString());
				}
			}

			for(String errorMsg : lstActualError)
			{
				for(int i=1; i<=lstErrorMessages.size(); i++)
				{
					if(lstErrorMessages.get(i).toString().equalsIgnoreCase(errorMsg))
					{
						APP_LOGS.debug("Field validation message found :" + errorMsg);
						result=Constants.KEYWORD_PASS;
						break;
					}
					else
					{
						APP_LOGS.debug("Field validation message not found :" + errorMsg);
						result=Constants.KEYWORD_FAIL + " Error validation not matched";
					}
				}
			}

			fs1.close();

		}catch(Exception e){
			e.printStackTrace();
			result=Constants.KEYWORD_FAIL;
		}

		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to wait for the object by xpath to load and visible
	 * @Return:Result=Pass/Fail 
	 */
	public String waitforElementToDisplayByCSS(String object,String data)
	{
		try
		{
			for(int i=1;i<=waitforelement;i++){
				try
				{
					if(driver.findElement(By.cssSelector(OR.getProperty(object))).isDisplayed()==true){
						result=Constants.KEYWORD_PASS;
						sleep(3);
						break;
					}
					else{
						sleep(1);
					}

					if(i==waitforelement){
						result=Constants.KEYWORD_FAIL;
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"in catch";

		}
		return result;
	}
	/*
	 * @author=Timir
	 * @Purpose: Function to wait for the object by name to load and visible
	 * @Return:Result=Pass/Fail 
	 */
	public String waitforElementToDisplayByName(String object,String data)
	{
		try
		{
			for(int i=1;i<=waitforelement;i++){
				try
				{
					if(driver.findElement(By.name(OR.getProperty(object))).isDisplayed()==true){
						result=Constants.KEYWORD_PASS;
						sleep(3);
						break;
					}
					else{
						sleep(1);
					}

					if(i==waitforelement){
						result=Constants.KEYWORD_FAIL;
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"in catch";

		}
		return result;
	}
	/*
	 * @author=Timir
	 * @Purpose: Function to wait for the object by id to load and visible
	 * @Return:Result=Pass/Fail 
	 */
	public String waitforElementToDisplayByID(String object,String data)
	{
		try
		{
			for(int i=1;i<=waitforelement;i++){
				try
				{
					if(driver.findElement(By.id(OR.getProperty(object))).isDisplayed()==true){
						result=Constants.KEYWORD_PASS;
						sleep(3);
						break;
					}
					else{
						sleep(1);
					}

					if(i==waitforelement){
						result=Constants.KEYWORD_FAIL;
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"in catch";

		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to verify if table exist in database 
	 * @Object=Table name to be checked
	 * @Return:Result=Pass/Fail 
	 */
	public String verifyTableExistInDatabase(String object,String data)
	{
		Connection conn;
		String tableName=OR.getProperty(object);
		try{
			conn=connectToDatabase("", "");
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +  "Unable to connet to database";
		}

		try{
			Statement stmt = conn.createStatement();
			String Query="select * from "+tableName+" where rownum='1'";
			System.out.println("Query: "+Query);
			ResultSet rs = stmt.executeQuery(Query);
			rs.next();

			result=Constants.KEYWORD_PASS;
		}catch(Exception e){
			if(e.getMessage().contains("table or view does not exist"))
			{
				result=Constants.KEYWORD_FAIL + " Table does not Exist";
			}
			else
			{
				System.out.println("Unable to execute Query");
				result=Constants.KEYWORD_FAIL + "Wrong Query";
			}
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	
//Dhvani	
	public String verifyTableContentInDatabase(String object,String data) throws FileNotFoundException
	{	
		Connection conn;
		String tableName=CONFIG.getProperty("Table3_iForms_Country");
	
		
		try{
			conn=connectToDatabase("", "");
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +  "Unable to connet to database";
		}

		try{
			

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
			
			
			
			
			Statement stmt = conn.createStatement();
			String Query="select * from "+tableName+" where rownum='1'";
			System.out.println("Query: "+Query);
			ResultSet rs = stmt.executeQuery(Query);
			
			 int count=0;
		      while(rs.next()){
		      //if (result.next()) {
		    	   String CountryName = rs.getString("CountryName");
		    	   System.out.println("Country Name : "+ CountryName);
		    	  // fw.write(System.getProperty("line.separator"));
		    	  // System.getProperty("line.separator");
		    	   //System.out.println( "<br>");
		    	   System.out.println( "&nbsp;");
		    	   String CountryId = rs.getString("CountryId");
		    	   //System.out.println( "<br>");
		    	   System.out.println( "&nbsp;");
		    	   System.out.println("CountryId : " + CountryId);
		    	   System.out.println( "<br>");
		    	   count = count+1;
		    	}
			
			
			
			
			//rs.next();

			result=Constants.KEYWORD_PASS;
		}catch(Exception e){
			if(e.getMessage().contains("table or view does not exist"))
			{
				result=Constants.KEYWORD_FAIL + " Table does not Exist";
			}
			else
			{
				System.out.println("Unable to execute Query");
				result=Constants.KEYWORD_FAIL + "Wrong Query";
			}
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	
	
	
	
	
	
	
	
	
	

	/*
	 * @author=Timir
	 * @Purpose: Function to verify  table does NOT exist in database 
	 * @Object=Table name to be checked
	 * @Return:Result=Pass if table not present else Fail 
	 */
	public String verifyTableNotExistInDatabase(String object,String data)
	{
		Connection conn;
		String tableName=OR.getProperty(object);
		try{
			conn=connectToDatabase("", "");
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +  "Unable to connet to database";
		}

		try{
			Statement stmt = conn.createStatement();
			String Query="select * from "+tableName+" where rownum='1'";
			System.out.println("Query: "+Query);
			ResultSet rs = stmt.executeQuery(Query);
			rs.next();

			result=Constants.KEYWORD_FAIL;
		}catch(Exception e){
			if(e.getMessage().contains("table or view does not exist"))
			{
				result=Constants.KEYWORD_PASS + " Table does not Exist";
			}
			else
			{
				System.out.println("Unable to execute Query");
				result=Constants.KEYWORD_FAIL + "Wrong Query";
			}
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to verify column exist in database 
	 * @Object=Table name
	 * @Data=Column Name
	 * @Return:Result=Pass if table not present else Fail 
	 */
	public String verifyColumnExistInDatabase(String object,String data) throws FileNotFoundException
	{
		
		 PrintStream outStream   = null;
	      PrintStream errStream = null;
	      PrintStream fileStream  = null;
	      outStream = System.out;   
	      errStream = System.err;
	      System.out.println("hi");
	      OutputStream os = new FileOutputStream("D:/Dhvani/automation/iFormFactor/src/result.html", false); // only the file output stream
	      os = new TeeOutputStream(outStream, os); // create a TeeOutputStream that duplicates data to outStream and os
	      fileStream = new PrintStream(os);
	      
	      System.setErr(fileStream);   
	      System.setOut(fileStream);
		
		
		
		Connection conn;
		String tableName=OR.getProperty(object);
		String columnName=data;
		try{
			conn=connectToDatabase("", "");
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +  "Unable to connet to database";
		}

		try{
			Statement stmt = conn.createStatement();
			String Query="select "+columnName+" from "+tableName+" where rownum='1'";
			System.out.println("Query: "+Query);
			ResultSet rs = stmt.executeQuery(Query);
			rs.next();

			result=Constants.KEYWORD_PASS;
		}catch(Exception e){
			if(e.getMessage().contains("table or view does not exist"))
			{
				result=Constants.KEYWORD_FAIL + " Table or view does not Exist";
			}
			else if(e.getMessage().contains("ORA-00904"))
			{
				result=Constants.KEYWORD_FAIL + " Column does not Exist";
			}
			else
			{
				System.out.println("Unable to execute Query");
				result=Constants.KEYWORD_FAIL + "Wrong Query";
			}
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to verify column exist in database 
	 * @Object=Table name
	 * @Data=Column Name
	 * @Return:Result=Pass if table not present else Fail 
	 */
	public String verifyColumnNotExistInDatabase(String object,String data)
	{
		Connection conn;
		String tableName=OR.getProperty(object);
		String columnName=data;
		try{
			conn=connectToDatabase("", "");
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +  "Unable to connet to database";
		}

		try{
			Statement stmt = conn.createStatement();
			String Query="select "+columnName+" from "+tableName+" where rownum='1'";
			System.out.println("Query: "+Query);
			ResultSet rs = stmt.executeQuery(Query);
			rs.next();

			result=Constants.KEYWORD_FAIL;
		}catch(Exception e){
			if(e.getMessage().contains("table or view does not exist"))
			{
				result=Constants.KEYWORD_FAIL + " Table or view does not Exist";
			}
			else if(e.getMessage().contains("ORA-00904"))
			{
				result=Constants.KEYWORD_PASS + " Column does not Exist";
			}
			else
			{
				System.out.println("Unable to execute Query");
				result=Constants.KEYWORD_FAIL + "Wrong Query";
			}
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to verify that file is downloaded
	 * @Return:Result=Pass/Fail 
	 * @Object: Element whose href attribute contains file download link
	 */
	public String verifyDownloadFile(String object,String data)
	{
		try{

			String downloadLink=driver.findElement(By.linkText(OR.getProperty(object))).getAttribute("href");
			Boolean dwFileresult=DownloadManager.downloadFile(downloadLink);

			if(dwFileresult){
				result=Constants.KEYWORD_PASS;
			}

		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}


	/*
	 * @author=Timir
	 * @Purpose: Function to verify that file is downloaded
	 * @Return:Result=Pass/Fail 
	 * @Object: Element whose href attribute contains file download link
	 */
	public String verifyDownloadFileByXpath(String object,String data)
	{
		try{

			String downloadLink=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("href");
			Boolean dwFileresult=DownloadManager.downloadFile(downloadLink);

			if(dwFileresult){
				result=Constants.KEYWORD_PASS;
			}

		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}
	/*
	 * @author=Timir
	 * @Purpose: Function to verify that Monthly cost of the employer
	 * * @Return:Result=Pass/Fail 
	 */


	public ResultSet executeQuerryDB(String querry)
	{
		ResultSet rs = null;
		Connection conn = null;

		try{
			conn=connectToDatabase("", "");
			Statement stmt = conn.createStatement();
			String Query=querry;
			System.out.println("Query: "+Query);
			rs = stmt.executeQuery(Query);


			result=Constants.KEYWORD_PASS;
		}catch(Exception e){
			if(e.getMessage().contains("table or view does not exist"))
			{
				result=Constants.KEYWORD_PASS + " Table does not Exist";
			}
			else
			{
				System.out.println("Unable to execute Query");
				result=Constants.KEYWORD_FAIL + "Wrong Query";
			}
		}


		return rs;
	}



	/*@author= Timir
	 * @Purpose:Function to Verify pop up message , if verified click on OK ie. accept
	 */
	public String popupClickOkVerifyMessage(String object,String data){

		String strResult = Constants.KEYWORD_FAIL;

		try{
		if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE")==false)
		{
		Alert objPopup = driver.switchTo().alert();
		if (objPopup != null)
		{
		String msgstr = objPopup.getText().trim();
		System.out.println("POPUP MESSAGE:" + msgstr);
		String actstr = data.trim();
		//Perform Event - Click OK
		if(msgstr.contains(actstr))
		{
		objPopup.accept();
		strResult = Constants.KEYWORD_PASS;
		}
		else
		{
		result=Constants.KEYWORD_FAIL+" -- text content not verified "+actstr+" -- "+msgstr;
		}

		return strResult;
		}
		}
		else
		{
		String windowHandle=driver.getWindowHandle();
		driver.switchTo().window(windowHandle);
		Robot robot=new Robot();

		robot.keyPress(KeyEvent.VK_ENTER);
		strResult="PASS";
		return strResult;
		}
		}
		catch(Exception e){
		e.printStackTrace();
		return strResult;
		}

		return strResult;

		}




	/*
	 * @author=Timir
	 * @Purpose: Function to wait for the object by xpath to load and visible
	 * @Return:Result=Pass/Fail 
	 */
	public String waitforElementToDisplayBylinkText(String object,String data)
	{
		try
		{
			for(int i=1;i<=waitforelement;i++){
				try
				{
					if(driver.findElement(By.linkText(OR.getProperty(object))).isDisplayed()==true){
						result=Constants.KEYWORD_PASS;
						sleep(3);
						break;
					}
					else{
						sleep(1);
					}

					if(i==waitforelement){
						result=Constants.KEYWORD_FAIL;
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"in catch";

		}
		return result;
	}

	public String verifySortingOrder(String object,String data)
	{
		APP_LOGS.debug("Verifying the sort function");

		try{



			String sorting=currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName,"sorting" ,DriverScript.datarowid  );
			driver.findElement(By.linkText("Sort By")).click();
			data="";
			waitforElementToDisplayBylinkText(sorting, data);

			if(sorting.equalsIgnoreCase("Smart Sort")){
				driver.findElement(By.linkText(sorting)).click();

			}


			else if(sorting.equalsIgnoreCase(" Estimated Total Costs (Low to High)")){

			}
			else if(sorting.equalsIgnoreCase("Monthly Premium (Low to High)")){
				driver.findElement(By.linkText(sorting)).click();

				int divIndex=driver.findElements(By.xpath("//div[@id='mainSummary']//div")).size();

				int[] arraylist=new int[divIndex];
				for(int index=1;index<=divIndex;index++)
				{
					//int planIndex=index+1;
					String priceValue=driver.findElement(By.xpath("//div[@id='mainSummary']//div["+index+"]//h3")).getText();
					int charindex=priceValue.indexOf("/");
					String priceAfterTrim=priceValue.substring(21, charindex);
					//Timir.sn, if the Premium is in decimal, an exception occurs so to handle it we remove the decimal
					if(priceAfterTrim.contains(".00")){
						priceAfterTrim=priceAfterTrim.replace(".00","");
					}
					//Timir.en
					arraylist[index]=Integer.parseInt(priceAfterTrim);
					arraylist[index]=Integer.parseInt(priceAfterTrim);

				}
				//to sort array items in to ascending order 
				Arrays.sort(arraylist);
				int arrayinitIindex=0;

				for(int index=1;index<=divIndex;index++)
				{

					String priceValue=driver.findElement(By.xpath("//div[@id='mainSummary']//div["+index+"]//h3")).getText();
					if(priceValue.contains(String.valueOf(arraylist[arrayinitIindex])))
					{      
						result=Constants.KEYWORD_PASS;
					}
					else
					{
						result=Constants.KEYWORD_FAIL+"sorting fail";
						break;
					}
					arrayinitIindex++;

				}
			}

			else if(sorting.equalsIgnoreCase("Monthly Premium (High to Low)")){
				driver.findElement(By.linkText("Monthly Premium (Low to High)")).click();
				driver.findElement(By.linkText("Monthly Premium (Low to High)")).click();

				int divIndex=driver.findElements(By.xpath("//div[@id='mainSummary']//div")).size();

				int[] arraylist=new int[divIndex];
				for(int index=1;index<=divIndex;index++)
				{
					//int planIndex=index+1;
					String priceValue=driver.findElement(By.xpath("//div[@id='mainSummary']//div["+index+"]//h3")).getText();
					int charindex=priceValue.indexOf("/");
					String priceAfterTrim=priceValue.substring(21, charindex);
					//Timir.sn, if the Premium is in decimal, an exception occurs so to handle it we remove the decimal
					if(priceAfterTrim.contains(".00")){
						priceAfterTrim=priceAfterTrim.replace(".00","");
					}
					//Timir.en
					arraylist[index]=Integer.parseInt(priceAfterTrim);
					arraylist[index]=Integer.parseInt(priceAfterTrim);

				}
				//to sort array items in to ascending order 
				Arrays.sort(arraylist);
				int arrayinitIindex=arraylist.length;

				for(int index=1;index<=divIndex;index++)
				{

					String priceValue=driver.findElement(By.xpath("//div[@id='mainSummary']//div["+index+"]//h3")).getText();
					if(priceValue.contains(String.valueOf(arraylist[arrayinitIindex])))
					{      
						result=Constants.KEYWORD_PASS;
					}
					else
					{
						result=Constants.KEYWORD_FAIL+"sorting fail";
						break;
					}
					arrayinitIindex--;

				}
			}


			else if(sorting.equalsIgnoreCase("Overall Quality"))
			{

			}
			else if(sorting.equalsIgnoreCase("planswithmydoctor"))
			{

			}

		}

		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String validateAllErrorMessageByCSS(String object, String data) throws IOException {

		try{

			ArrayList<String> lstActualError = new ArrayList<String>();
			ArrayList<String> lstErrorMessages = new ArrayList<String>();
			FileInputStream fs1 = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//message.properties");
			msgCONFIG= new Properties();
			msgCONFIG.load(fs1);


			for(int index=0;index<=msgCONFIG.size();index++)
			{
				lstErrorMessages.add(msgCONFIG.getProperty("msg"+index));

			}
			List<WebElement> objErrors = driver.findElements(By.cssSelector(OR.getProperty(object)));

			if (objErrors.size() > 0 ){
				//System.out.println(objErrors.size());
				for (int i=0; i<objErrors.size(); i++){
					lstActualError.add(objErrors.get(i).getText().toString());
					System.out.println (objErrors.get(i).getText().toString());
				}
			}

			for(String errorMsg : lstActualError)
			{
				for(int i=1; i<=lstErrorMessages.size(); i++)
				{
					if(lstErrorMessages.get(i).toString().equalsIgnoreCase(errorMsg))
					{
						APP_LOGS.debug("Field validation message found :" + errorMsg);
						result=Constants.KEYWORD_PASS;
						break;
					}
					else
					{
						APP_LOGS.debug("Field validation message not found :" + errorMsg);
						result=Constants.KEYWORD_FAIL + " Error validation not matched";
					}
				}
			}

			fs1.close();

		}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
		}

		return result;
	}


	
	/*
	 * @author=Timir
	 * @Purpose:Function to verify confirmation pop up message and click on cancel  
	 * @Return:Result=Pass/Fail 
	 */
	public String verifyAlertMessageAndClickCancel(String object,String data){
		try{
			Alert objPopup = driver.switchTo().alert();
			if (objPopup != null){
				System.out.println("POPUP MESSAGE:" + objPopup.getText());

				if(objPopup.getText().contains(data))
				{
					//Perform Event - Click OK
					objPopup.dismiss();
				}
				result = Constants.KEYWORD_PASS;
				return result;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String verifyRoutingNumberInDb(String object,String data)
	{

		ResultSet rs;


		Connection conn=null;

		try{
			conn=connectToDatabase("", "");
			Statement stmt = conn.createStatement();
			String Query="";
			String routingnumber=currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName,"ABARoutingNo" ,DriverScript.datarowid  );
			String accountname=currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName,"AccountName" ,DriverScript.datarowid  );
			Query="select  ROUTING_NUMBER from BANK_INFO where NAME_ON_ACCOUNT='"+accountname+"'";
			System.out.println("Query: "+Query);
			rs = stmt.executeQuery(Query);
			rs.next();
			String Routing_number=rs.getString("ROUTING_NUMBER");


			if(Integer.parseInt(routingnumber)==Integer.parseInt(Routing_number))
			{
				result=Constants.KEYWORD_PASS;

			}
			else
			{
				result=Constants.KEYWORD_FAIL;
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"Routing number coloum does not exist";
		}
		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Verify with current date
	 * @object:The UI date object
	 * @Return:Fail/Pass
	 */
	public String verifyCurrentDate(String object,String data)
	{
		String todaydate;
		APP_LOGS.debug("Verify with current date");
		try{
			todaydate = getCurrentDateMMDDYYYY(object,data);
			globalValue=todaydate;

			//Verify with the global variable which has d/b count
			String verified=verifyContainsGlobalVariable(object,"");
			if(verified==Constants.KEYWORD_PASS)
			{
				return result=Constants.KEYWORD_PASS;
			}
			else
			{
				return result=Constants.KEYWORD_FAIL + " Current date is  "+globalValue+" which is differnt that the UI";
			}


		}catch(Exception e){
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
	}

	/* @author=Timir
	 * @Purpose: Open html file in browser 
	 * @Data: file path 
	 */
	public String openHtmlFileAndNavigate(String object,String data)
	{
		try{
			APP_LOGS.debug("Open html file and navigate");
			String htmlFilePath = System.getProperty("user.dir")+data; 
			File htmlFile = new File(htmlFilePath);
			driver.navigate().to(htmlFile.getAbsolutePath());

			APP_LOGS.debug("Open file and navigating file" +htmlFilePath );
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result=Constants.KEYWORD_FAIL;
		}

		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to Compare start date and current date
	 * @Return:Current Date MM/DD/YYYY format
	 */
	public String verifyCurrentDateWithStartDate(String object,String data)
	{
		String date="";
		try{
			date=getCurrentDateMMDDYYYY(object,data);
			if(date.contains("/"))
			{
				date=date.replace("/","-");
			}
			System.out.println("after replace date is"+date);
			globalValue=date;
			System.out.println("global date is"+globalValue);
			String actual=driver.findElement(By.xpath(OR.getProperty("admin.agent.certificationstatus.startdate.byxpath.txt"))).getAttribute("value");
			if(globalValue.equalsIgnoreCase(actual))
			{
				return Constants.KEYWORD_PASS;
			}
			else
			{
				result=Constants.KEYWORD_FAIL;
			}

		}catch(Exception e){

			result=Constants.KEYWORD_FAIL+"both are not same";

		}

		return result;
	}

	/*
	 * @author=Timir
	 * @Purpose: Function to verify if a particular object is not Hidden.
	 * @Return:Result=Pass/Fail 
	 */
	public  String isNotHidden(String object, String data){
		APP_LOGS.debug("Object is displayed");
		try{
			boolean visible=false;			

			visible=driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed();
			if(visible==true)	
			{
				result = Constants.KEYWORD_PASS+"- Object is not displayed";				
			}
			else
			{
				result = Constants.KEYWORD_FAIL +"- Object is hidden";				
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Object not found";	

		}	return result;	

	}//End of Keyword

	

	/* @author=Timir
	 * @Purpose: Function to verify date format of effective date under health plans
	 * @Return:Result=return status if pass else Fail 
	 */
	public String verifyEffectiveDateFormat(String object,String data)
	{

		try
		{
			data="";
			APP_LOGS.debug("gets date value from effective date label");
			String actualValue=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			String dt = actualValue;
			APP_LOGS.debug("verify . sign contains");
			if(actualValue.contains("/"))
			{
				APP_LOGS.debug("Splits with / sign");
				String dateParts[] = dt.split("/");
				int month  =Integer.parseInt(dateParts[0]);
				int day  = Integer.parseInt(dateParts[1]);
				String year =dateParts[2];
				System.out.println(month);
				System.out.println(day);
				System.out.println(year);
				APP_LOGS.debug("verifies month date and year value");
				if(month<=12 && day<=31  &&  year.length()==4)
				{
					result=Constants.KEYWORD_PASS;
				}
				else
				{
					result=Constants.KEYWORD_FAIL + "not in mm/dd/yyyy format";
				}

			}
			else
			{
				result=Constants.KEYWORD_FAIL + "date format does not contain / sign";
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"fail to get date value from object";
		}
		return result;
	}

	/*
	 * author=Timir
	 * @ Purpose: Function to Read employer enrollment status and verify the status for given ID
	 * @ Precondition : getEmployerEnrollmentID is to be executed and Excel Datasheet contains the ID
	 * @ Data: The keyword passes the column name of the status against which the status is to be checked.
	 * @Return:Result=return status if pass else Fail 
	 */

	public  String verifyContainsGlobalVariableByID(String object, String data){
		APP_LOGS.debug("Verifying with the Global Variable");
		try{
			String actual="";
			String blank="";
			if (object.equals(blank) || (object==null))
			{
				actual=data;
			}
			else
			{
				actual=driver.findElement(By.id(OR.getProperty(object))).getText();
			}
			Scanner in = new Scanner(actual).useDelimiter("[^0-9]+");
			actual=String.valueOf(in.nextInt());
			System.out.println("actual  is "+actual);
			if((actual.contains(globalValue))||(globalValue.contains(actual)) )

				return Constants.KEYWORD_PASS;	
			else
				return Constants.KEYWORD_FAIL +globalValue + "   - Value doesn't contain in actual -   " +actual ;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Value does not match";	

		}

	}

	public String switchToFrameByXpath(String object, String data){

		//String status = "";
		try{
			sleep(3);
			WebElement e=driver.findElement(By.xpath(OR.getProperty(object)));
			driver.switchTo().frame(e);
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e){
			e.printStackTrace();
			result=Constants.KEYWORD_FAIL + "Frame not fount";
		}

		//System.out.println("STATUS:" + status);
		return result;

	}
	
	public String waitforElementToDisplayByCss(String object,String data)
	{
		int maxWait=waitforelement;


		try
		{
			if(object.equalsIgnoreCase("individual.plandisplay.planselection.addtocart.btn"))
			{
				maxWait=60;
			}

			for(int i=1;i<=maxWait;i++){
				try
				{
					if(driver.findElement(By.cssSelector(OR.getProperty(object))).isDisplayed()==true){
						result=Constants.KEYWORD_PASS;
						sleep(3);
						break;
					}
					else{
						sleep(1);
					}

					if(i==maxWait){
						result=Constants.KEYWORD_FAIL;
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"in catch";

		}
		return result;
	}
	
	public  String clickByCss(String object,String data){
		APP_LOGS.debug("Clicking on any element");
		try{
			WebElement objElement =driver.findElement(By.cssSelector(data));
			driver.getWindowHandle();
			Actions builder = new Actions(driver);   
			builder.moveToElement(objElement).build().perform();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("var evt = document.createEvent('MouseEvents');" + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" + "arguments[0].dispatchEvent(evt);", objElement);
			result= Constants.KEYWORD_PASS;
			
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL+" Not able to click";
		}
		return result;
	}
	
	/*
		 * @author=Timir
		 * @Purpose: Function to wait for the object by xpath to load and visible
		 * @Return:Result=Pass/Fail 
		 */
		public String waitforDocumentToUploadByXpath(String object,String data)
		{
			int maxWait=waitfordocUpload;


			try
			{
				
				for(int i=1;i<=maxWait;i++){
					try
					{
						if(driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed()==true){
							result=Constants.KEYWORD_PASS;
							sleep(3);
							break;
						}
						else{
							sleep(1);
						}

						if(i==maxWait){
							result=Constants.KEYWORD_FAIL;
						}
					}
					catch(Exception e)
					{
					}
				}
			}
			catch(Exception e)
			{
				result=Constants.KEYWORD_FAIL+"in catch";

			}
			return result;
		}
		
		
		public String captureScreenshot(String filename, String keyword_execution_result) throws IOException{
			// take screen shots

			String strPath = null;


			try{
				if(screenshotfoldercreate)
				{
					String folder="";
					Calendar cal = Calendar.getInstance();
					DateFormat df = new SimpleDateFormat("dd-MMM_HH.mm");
					folder =  CONFIG.getProperty("environment")+"_"+ df.format(cal.getTime());
					filePath=System.getProperty("user.dir") +"//screenshots//"+folder+"//";

					File f=new File(filePath);
					if(f.exists()==false)
					{
						f.mkdirs();
					}
					screenshotfoldercreate=false;
				}

			}catch(Exception e)
			{
				screenshotfoldercreate=false;
				filePath=System.getProperty("user.dir") +"//screenshots//";
			}


			try{


				if(CONFIG.getProperty("screenshot_everystep").equals("Y")){
					// capturescreen
					//File DestFile = new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg");
					File DestFile = new File(filePath+filename+".jpg");
					File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(scrFile, DestFile);

					strPath = DestFile.getAbsolutePath();
					System.out.println(strPath);

				}else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y") ){
					// capture screenshot
					//File DestFile = new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg");
					File DestFile = new File(filePath+filename+".jpg");
					File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(scrFile, DestFile);

					strPath = DestFile.getAbsolutePath();
					System.out.println("IN ERROR CONDITION:"+strPath);
				}
			}catch(Exception e){}


			return strPath;
		}
		
		
		public String waitforElementNotToDisplayByName(String object,String data)
		{
			try
			{
				for(int i=1;i<=waitforelement;i++){
					try
					{
						if(driver.findElement(By.name(OR.getProperty(object))).isDisplayed()==false){
							result=Constants.KEYWORD_PASS;
							sleep(3);
							break;
						}
						else{
							sleep(1);
						}

						if(i==waitforelement){
							result=Constants.KEYWORD_FAIL;
						}
					}
					catch(Exception e)
					{
					}
				}
			}
			catch(Exception e)
			{
				result=Constants.KEYWORD_FAIL+"in catch";

			}
			return result;
		}
		

public  String generic_clearText(String object,String data){
		By by;
		APP_LOGS.debug("Clearing the text from input field");
		try{
			by=object_type_identifier(object);
			driver.findElement(by).clear();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to clear";
		}
		return Constants.KEYWORD_PASS;
	} 







public  String generic_click(String object,String data){
		By by = null;
		APP_LOGS.debug("Clicking on any element");
		try{

			if(CONFIG.getProperty("browserType").equalsIgnoreCase("IE"))
			{
				String windowHandle=driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				by=object_type_identifier(object);
				driver.findElement(by);
				try
				{

					driver.findElement(by).click();	

				}
				catch(Exception e)
				{
					driver.findElement(by).sendKeys("\n");
				}
				sleep(4);

				result= Constants.KEYWORD_PASS;
			}
			else
			{
				WebElement objElement =driver.findElement(by);
				driver.getWindowHandle();
				Actions builder = new Actions(driver);   
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');" + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" + "arguments[0].dispatchEvent(evt);", objElement);
				result= Constants.KEYWORD_PASS;
			}
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL+" Not able to click";
		}
		return result;
	}






public String generic_exist(String object,String data){
		By by = null;
		APP_LOGS.debug("Checking existance of element");
		try{
			by=object_type_identifier(object);
			System.out.println("Value of by as : " + by);
			driver.findElement(by);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}





public  String generic_selectList(String object, String data){
		By by;
		APP_LOGS.debug("Selecting from list");
		try{
			data = data.trim();
			by=object_type_identifier(object);
			WebElement dropDownListBox = driver.findElement(by);

			Select droplist = new Select(dropDownListBox);			  
			droplist.selectByVisibleText(data);

		}catch(Exception e){
			System.out.println(" - Could not select from list. "+ e.getMessage());
			return Constants.KEYWORD_FAIL +" - Could not select from list. ";	
		}

		return Constants.KEYWORD_PASS;	
	}




public  String generic_uploadDoc(String object, String data){
		String strPath = "";
		By by;
		APP_LOGS.debug("uploading Document...");
		try{
			strPath = System.getProperty("user.dir") + data;
			System.out.println("path:"+strPath);

			by = object_type_identifier(object);
			driver.findElement(by).sendKeys(strPath);	
			sleep(3);

		}catch(Exception e){
			System.out.println(" - Getting error while document uploading" +e.getMessage());
			return Constants.KEYWORD_FAIL +" - Getting error while document uploading";	
		}

		return Constants.KEYWORD_PASS;	
	}





public String generic_verifyText(String object, String data){
		By by = null;
		APP_LOGS.debug("Verifying the text");
		try{
			sleep(3);
			by=object_type_identifier(object);
			String actual=driver.findElement(by).getText().trim();
			String expected=data;

			if(actual.equalsIgnoreCase(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}




public  String generic_verifyTextinInput(String object,String data){
		By by = null;
		APP_LOGS.debug("Verifying the text in input box");
		try{
			by=object_type_identifier(object);
			String actual = driver.findElement(by).getAttribute("value");
			String expected=data;

			if(actual.equals(expected)){
				return Constants.KEYWORD_PASS;
			}else{
				return Constants.KEYWORD_FAIL+" Not matching ";
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to find input box "+e.getMessage();

		}
	}




public String generic_waitforElementToDisplay(String object,String data)
	{
		By by;
		try
		{
			for(int i=1;i<=waitforelement;i++){
				try
				{
					by = object_type_identifier(object);
					if(driver.findElement(by).isDisplayed()==true){
						result=Constants.KEYWORD_PASS;
						sleep(3);
						break;
					}
					else{
						sleep(1);
					}

					if(i==waitforelement){
						result=Constants.KEYWORD_FAIL;
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL+"in catch";

		}
		return result;
	}





public String generic_waitforElementToLoad(String object,String data)
	{
		By by;
		data=CONFIG.getProperty("implicitwait");
		try
		{
			by = object_type_identifier(object);
			sleep(3);
			WebDriverWait waiting = new WebDriverWait(driver,Long.parseLong(data));

			waiting.until(ExpectedConditions.presenceOfElementLocated(by));
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}




public String generic_WriteInInputbox(String object,String data)
	{     By by = null;
	try{
		by=object_type_identifier(object);
		System.out.println("Value of by as : " + by);

		driver.findElement(by).sendKeys(data);
		result=Constants.KEYWORD_PASS;
	}
	catch(Exception e)
	{
		result=Constants.KEYWORD_FAIL;
		System.out.println("Not able to click");
		e.printStackTrace();
	}

	return result;
	}

public By object_type_identifier(String OR_props_key_value)
	{
		By by = null;
		try{

			String[] xpath_split;
			String object_xpath_val;

			if(OR_props_key_value.startsWith("//"))
			{
				System.out.println("props_key_value : " + OR_props_key_value);
				by=By.xpath(OR_props_key_value);
			}
			else if(OR_props_key_value.contains("linktext="))
			{
				xpath_split = OR_props_key_value.split("=");
				object_xpath_val = xpath_split[1];
				System.out.println("linktext_val : " + object_xpath_val);
				by=By.linkText(object_xpath_val);

			}
			else if(OR_props_key_value.contains("id="))
			{
				xpath_split = OR_props_key_value.split("=");
				object_xpath_val = xpath_split[1];
				System.out.println("id : " + object_xpath_val);
				by=By.id(object_xpath_val);

			}
			else if(OR_props_key_value.contains("css="))
			{
				xpath_split = OR_props_key_value.split("=");
				object_xpath_val = xpath_split[1];
				System.out.println("css : " + object_xpath_val);
				by=By.cssSelector(object_xpath_val); 
			}
			else if(OR_props_key_value.contains("name="))
			{
				xpath_split = OR_props_key_value.split("=");
				object_xpath_val = xpath_split[1];
				System.out.println("name : " + object_xpath_val);
				by=By.name(object_xpath_val); 
			}

		}catch(Exception e)
		{
			result=Constants.KEYWORD_FAIL;
			e.printStackTrace();
		}

		return by;
	}

	public String getAndSetCropname(String object,String data)
	{
		
		try
		{
			String actualCropName=driver.findElement(By.xpath(OR.getProperty("DefaultCropName"))).getText();
			
			
			String expectedCropName=actualCropName;
			//System.out.println("value");
			
			System.out.println("Crop Name is "+ expectedCropName);
					
			
			currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, expectedCropName);
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			System.out.println(e);
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}
	
	public String getAndSetdefaultYield(String object,String data)
	{
		
		try
		{
			String actualdefaultYield=driver.findElement(By.xpath(OR.getProperty("DefaultYield"))).getText();
		
			String expecteddefaultYield=actualdefaultYield;
			
			//System.out.println("value");
			
			System.out.println("Default Yield is "+ expecteddefaultYield);
		
			currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, expecteddefaultYield);

			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			System.out.println(e);
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

	
	public String getAndSetcostofproduction(String object,String data)
	{
		
		try
		{

			String actualcostofproduction=driver.findElement(By.xpath(OR.getProperty("DefaultCostofProduction"))).getText();
			
			String expectedcostofproduction=actualcostofproduction;
			//System.out.println("value");
			
			System.out.println("Cost of Production is "+ expectedcostofproduction);
					
			currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, expectedcostofproduction);
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			System.out.println(e);
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

		public String getAndElementenabled(String object,String data)
	{
		
		try
		{
			String actualattribute=driver.findElement(By.xpath(OR.getProperty("checkbox"))).getAttribute("disabled");		
			
			System.out.println("Checkbox attribute is "+ actualattribute);
			//driver.findElement(By.xpath(OR.getProperty("Inward.bar.alert.ok.btn"))).click();
			
			  JavascriptExecutor javascript = (JavascriptExecutor) driver;
			  boolean enable=driver.findElement(By.xpath("//*[@id='td_SI_NO_0']/a/input")).isEnabled();
			  System.out.print("\nAfter : chekbox status is : "+enable);
			 								
			result=Constants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			System.out.println(e);
			result=Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public  String BirthdatePicker(String object,String data){
		try{
			APP_LOGS.debug("Waiting for date selection");
	
			((JavascriptExecutor)driver).executeScript(
					"arguments[0].value=arguments[1]", 
				driver.findElement(By.xpath(OR.getProperty("AccAdmin.DOB.txt"))), "07/21/2015");
		   
				
			
		}catch(Exception e){
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}
	
	
	public  String CommencementDate(String object,String data){
		try{
			APP_LOGS.debug("Waiting for date selection");
	
			((JavascriptExecutor)driver).executeScript(
					"arguments[0].value=arguments[1]", 
				driver.findElement(By.xpath(OR.getProperty("Commencement.date.select"))), "11-06-2014");
		   
				
			
		}catch(Exception e){
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}
	
	
	// Exe for apply login id and password in to authentication window
	/*public String RunAuthenticateWindowExe(String object,String data)
	{
	try{
		
		Process proc=Runtime.getRuntime().exec(("user.dir")+"//AutoitScript//ING_AuthenticationWindow.exe");
		Thread.sleep(5000);
			
	 }	
	catch(Exception e){
		e.printStackTrace();
		return Constants.KEYWORD_FAIL;
	}
	return Constants.KEYWORD_PASS;

	}*/
	
	
	
	
	public String RunAuthenticateWindowFFExe(String object,String data)
	 {
	 try{
	  
	  Process proc=Runtime.getRuntime().exec((System.getProperty("user.dir"))+"//AutoitScript//ING_AuthenticationWindowFF.exe");
	  Thread.sleep(5000);
	  result=Constants.KEYWORD_PASS;
	   
	  } 
	 catch(Exception e){
	  e.printStackTrace();
	  result= Constants.KEYWORD_FAIL;
	 }
	 return result;

	 }
	
	public String RunAuthenticateWindowIEExe(String object,String data)
	 {
	 try{
	  
	  Process proc=Runtime.getRuntime().exec((System.getProperty("user.dir"))+"//AutoitScript//ING_AuthenticationWindowIE.exe");
	  Thread.sleep(5000);
	  result=Constants.KEYWORD_PASS;
	   
	  } 
	 catch(Exception e){
	  e.printStackTrace();
	  result= Constants.KEYWORD_FAIL;
	 }
	 return result;

	 }
	
	
	
	// Exe for upload TIF file
	
	public String RunUploadTIFDocumentExe(String object,String data)
	{
	try{
	
		//driver = new FirefoxDriver();
		 //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 Runtime.getRuntime().exec("D:\\Automation\\ING CLMS\\src\\com\\sample\\util\\ING Browse TIF File-.exe");
		 
		 //driver.get("http://svt-srv-39:1000/");
		 
		 Thread.sleep(5000);
		 
		 //driver.close();
	 }	
	catch(Exception e){
		e.printStackTrace();
		return Constants.KEYWORD_FAIL;
	}
	return Constants.KEYWORD_PASS;
}
	
	
	// Exe for upload execel document
	
	public String RunUploadExcelDocumentExe(String object,String data)
	{
	try{
	
		//driver = new FirefoxDriver();
		 //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 Runtime.getRuntime().exec("D:\\Automation\\ING CLMS\\src\\com\\sample\\util\\ING Browse XLS File-.exe");
		 
		 //driver.get("http://svt-srv-39:1000/");
		 
		 Thread.sleep(5000);
		 
		 //driver.close();
	 }	
	catch(Exception e){
		e.printStackTrace();
		return Constants.KEYWORD_FAIL;
	}
	return Constants.KEYWORD_PASS;
}

	
	
	
	
	public String RunUploadCSVDocumentExe(String object,String data)
	{
	try{
	
		//driver = new FirefoxDriver();
		 //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 Runtime.getRuntime().exec("D:\\Automation\\ING CLMS\\src\\com\\sample\\util\\ING Browse CSV File-.exe");
		 
		 //driver.get("http://svt-srv-39:1000/");
		 
		 Thread.sleep(5000);
		 
		 //driver.close();
	 }	
	catch(Exception e){
		e.printStackTrace();
		return Constants.KEYWORD_FAIL;
	}
	return Constants.KEYWORD_PASS;
}
	
	
	
	
	//Exe for save download file
	public String RunviewDownloadedFileExe(String object,String data)
		{
		try{
		
			//driver = new FirefoxDriver();
			 //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			 Runtime.getRuntime().exec("D:\\Automation\\ING CLMS\\src\\com\\sample\\util\\save file.exe");
			 
			 //driver.get("http://svt-srv-39:1000/");
			 
			 Thread.sleep(5000);
			 
			 //driver.close();
		 }	
		catch(Exception e){
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}		
	//EXE for open windows explore
	public String RunOpenDownloadedFileExe(String object,String data)
		{
		try{
		
			//driver = new FirefoxDriver();
			 //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			 Runtime.getRuntime().exec("D:\\Automation\\ING CLMS\\src\\com\\sample\\util\\ING OpenDownloadFolder.exe");
			 
			 //driver.get("http://svt-srv-39:1000/");
			 
			 Thread.sleep(5000);
			 
			 //driver.close();
		 }	
		catch(Exception e){
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}		
	
	
	public String draganddrop(String object, String data) throws InterruptedException
    {

    //public static void main(String[] args) 
    {

    Actions act = new Actions(driver);

    //WebElement dragElement=driver.findElement(By.xpath("//div[@id='dvCustomPallet']/div")); 
    //WebElement dragElement=driver.findElement(By.id("textbox"));
    WebElement dragElement1=driver.findElement(By.cssSelector("#textbox"));
    dragElement1.click();
   
    WebElement dragElement2=driver.findElement(By.cssSelector("#radiobuttonList"));
   dragElement2.click();
    
    WebElement dragElement3=driver.findElement(By.cssSelector("#textarea"));
    dragElement3.click();
    
    WebElement dragElement4=driver.findElement(By.cssSelector("#email"));
    dragElement4.click();
    //WebElement dragElement2=driver.findElement(By.xpath("//div[@id='textbox']"));
    
    
   // WebElement dropElement=driver.findElement(By.xpath(".//*[@id='drop-form-1']"));
    WebElement dropElement=driver.findElement(By.cssSelector("#drop-form-1"));
    
    
    Actions builder = new Actions(driver);
    //Action dragAndDrop = builder.clickAndHold(dragElement).moveToElement(dropElement).release(dropElement).build();
    Action dragAndDrop1 = builder.clickAndHold(dragElement1).moveToElement(dropElement).release(dropElement).build();
    dragAndDrop1.perform();
    Thread.sleep(1000);
    
    Action dragAndDrop2 = builder.clickAndHold(dragElement2).moveToElement(dropElement).release(dropElement).build();
    dragAndDrop2.perform();
    Thread.sleep(1000);
    Action dragAndDrop3 = builder.clickAndHold(dragElement3).moveToElement(dropElement).release(dropElement).build();
    dragAndDrop3.perform();
    Thread.sleep(1000);
    Action dragAndDrop4 = builder.clickAndHold(dragElement4).moveToElement(dropElement).release(dropElement).build();
    dragAndDrop4.perform();
    Thread.sleep(1000);
    //Action dragAndDrop3 = builder.clickAndHold(dragElement3).moveToElement(dropElement).release(dropElement).build();
    
    //dragAndDrop.perform();
    //dragAndDrop1.perform();
    //Thread.sleep(1000);
    //dragAndDrop2.perform();
    //Thread.sleep(1000);
    //dragAndDrop3.perform();
    //Thread.sleep(1000);
    //dragAndDrop4.perform();
    
    }
    return data;
    }

	public  String autoSuggest(String object,String data){
		APP_LOGS.debug("Writing in text box");

		//String newdata = String.valueOf(data);
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).clear(); 

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data,Keys.TAB);
			
			//driver.findElement(By.xpath( "//*[@id='GroupAdmin']")).sendKeys("Fadel Galal");
		      Thread.sleep(1000);
		      driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.DOWN);
		      Thread.sleep(1500);
		      driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.RETURN);
		      Thread.sleep(1500);
		      result=Constants.KEYWORD_PASS;
		      
		}catch(Exception e){
			result= Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return result;
	}

	public  Connection DbConnect(String object,String data) throws SQLException, FileNotFoundException, ClassNotFoundException{
		
		APP_LOGS.debug("fetching data from database");
		 driver = new FirefoxDriver();
		 driver.navigate().to("http://www.google.com");
		 //baseUrl = "https://www.google.co.in/?gfe_rd=cr&ei=6uvSVbOUPKrv8wfHtIyQBA&gws_rd=ssl";
			
		
		
		  WebDriver driver = null;
		  Connection con = null;
		  Statement stmt = null;
		  String baseUrl;
		  
		  
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

	    String pwd =CONFIG.getProperty("QA_DB_Password");
	    // Get connection to DB.
	    con = DriverManager.getConnection(url, "sa", pwd);
	    // Create statement object which would be used in writing DDL and DML
	    // SQL statement.
	    stmt = con.createStatement();
	    // Send SQL SELECT statements to the database via the
	    // Statement.executeQuery
	    // method which returns the requested information as rows of data in a
	    // ResultSet object.
	    // define query to read data
	
	       try {
	  	  //String tableName="[Lookup].[Country]";  

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
	    
	    
	    catch (SQLException ex)
	    {
	      System.out.println("Error:"+ex);
	    }
	    

		return con;

}


public String storeValue(String object,String data){
    try{
                    String Value1 = driver.findElement(By.id((OR.getProperty(object)))).getText();
                    Pattern p = Pattern.compile("The work order: (.*)and assignment were created successfully");
                    Matcher m = p.matcher(Value1);
                    if(m.find()){
                                    String Value=m.group(1);
                                    System.out.println("generated Work Order Number:"+Value);
                                    currentTestSuiteXLS.setCellData(DriverScript.currentTestCaseName, data, currentTestDataSetID,Value);
                                    //updateProperty(In_No);
                                    result=Constants.KEYWORD_PASS;
                    }
                    
    result=Constants.KEYWORD_PASS;
}
    
    catch(Exception e){
    result=Constants.KEYWORD_FAIL + e;
}
    /*try
    {
                    System.out.println("Updating Excel References");
                    getRefFlag(DriverScript.currentTestCaseName, data);
                    updatePropertyFile(data);
                    
    }
    catch(Exception e)
    {
                    System.out.println("Unable to update data in ref sheet");
                    System.out.println(e.getMessage());
                    result= Constants.KEYWORD_FAIL;
    }*/

    return result;
}


public String getLatestValue(String object,String data)
{
    String output = "";
    String finalPrice="";
    try {
    	URL url1 = new URL("https://ondemand.websol.barchart.com/getQuote.json?apikey=d8984813a5fd51b6bf7a8b6756e12b26&symbols=ZCH17&fields=month,year&mode=I");
        //URL url1 = new URL("https://ondemand.websol.barchart.com/getQuote.json?apikey=d8984813a5fd51b6bf7a8b6756e12b26&symbols=ZC^F&fields=month,year&mode=I", "ZCH17", "lastPrice");
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
            (conn.getInputStream())));

        

                    
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            final JSONObject obj = new JSONObject(output);
            final JSONArray geodata = obj.getJSONArray("results");
            final int n = geodata.length();
            for (int i = 0; i < n; ++i) {
              final JSONObject person = geodata.getJSONObject(i);
              String expected=person.getString("symbol");
              if(expected.equals("ZCH17"))
              {
              String latestPrice=String.valueOf(person.getDouble("lastPrice")/100);
              finalPrice=latestPrice.substring(0,latestPrice.length());
              
            
  		System.out.println(finalPrice);	
              
              }
            }     
        
        }
        
        conn.disconnect();

      } catch (MalformedURLException e) {

        e.printStackTrace();

      } catch (IOException e) {

        e.printStackTrace();

      }
      currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, finalPrice);
    
    return finalPrice;
    
    
}























}



