package com.sample.test;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sample.util.ReportUtil;
import com.sample.xls.read.Xls_Reader;

public class DriverScript{

	public static Logger APP_LOGS;
	//suite.xlsx
	public Xls_Reader suiteXLS;
	public int currentSuiteID;
	public String currentTestSuite;
	public static int datarowid;
	public int startIndex=0;
	public String result=Constants.KEYWORD_FAIL;
	// current test suite
	public static Xls_Reader currentTestSuiteXLS;
	public static int currentTestCaseID;
	public static String currentTestCaseName;
	public static int currentTestStepID;
	public static String currentKeyword;
	public static int currentTestDataSetID=2;
	public static Method method[];
	public static Method capturescreenShot_method;
	public  FileInputStream fis;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	public static Keywords keywords;
	public static String keyword_execution_result;
	public static String keyword_execution_screenshot;
	public static ArrayList<String> resultSet;
	public static ArrayList<String> screenshotSet;
	public static String data;
	public static String columnName;
	public static String refexcelfilename;
	public static String refexcelsheetname;
	public static String object;
	public static String project;
	private ProgressBarDemo progressbar;
	public Boolean exectuteTestCase=true;
	// properties
	public static Properties CONFIG;
	
	public static Properties OR;
	public static FileInputStream fs;
	public DriverScript() throws NoSuchMethodException, SecurityException, IOException{
		keywords = new Keywords();
		progressbar=new ProgressBarDemo(); 
		method = keywords.getClass().getMethods();
		capturescreenShot_method =keywords.getClass().getMethod("captureScreenshot",String.class,String.class);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("user.dir")+"//src//com//sample//config//config.properties");
		fs = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//config.properties");
		CONFIG= new Properties();
		CONFIG.load(fs);
 		project = CONFIG.getProperty("project").toString();
		DriverScript test = new DriverScript();
		 
		
		test.start();
		ReportUtil report=new ReportUtil();
		report.generateReport();
	}
	
	
	public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException{
		// initialize the app logs
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug("Properties loaded. Starting testing");
		// 1) check the runmode of test Suite
		// 2) Runmode of the test case in test suite
	    // 3) Execute keywords of the test case serially
		// 4) Execute Keywords as many times as
		// number of data sets - set to Y
		APP_LOGS.debug("Intialize Suite xlsx");
		
		System.out.println("Logs : " + APP_LOGS);
		suiteXLS = new Xls_Reader(System.getProperty("user.dir")+"//src//com//sample//xls//Automation_Suite.xlsx");
		
		for(currentSuiteID=2;currentSuiteID<=suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET);currentSuiteID++){
		
			
			
			
			APP_LOGS.debug(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID)+" -- "+  suiteXLS.getCellData("Test Suite", "Runmode", currentSuiteID));
			// test suite name = test suite xls file having test cases
			currentTestSuite=suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID);
			
			
			
			if(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentSuiteID).equals(Constants.RUNMODE_YES)){
				
				APP_LOGS.debug("******Loading the OR file for ******"+currentTestSuite);
				
				FileInputStream orfs = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//"+currentTestSuite+".properties");
				OR= new Properties();
				OR.load(orfs);	
				
				// execute the test cases in the suite
				APP_LOGS.debug("******Executing the Suite******"+suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID));
				currentTestSuiteXLS=new Xls_Reader(System.getProperty("user.dir")+"//src//com//sample//xls//"+currentTestSuite+".xlsx");
				//System.out.println("TestCase file: "+(System.getProperty("user.dir")+"//src//com//sample//xls//"+currentTestSuite+".xlsx"));
				// iterate through all the test cases in the suite
				refexcelfilename=System.getProperty("user.dir")+"//src//com//sample//xls//"+currentTestSuite+".xlsx";
				for(int rescol=1;rescol<=6;rescol++)
		        {
		        currentTestSuiteXLS.clearCellData(Constants.TEST_STEPS_SHEET,"Result"+rescol," ");
		        }
				progressbar.ProgressBarinit(0,100);
				int tcnumber=0;
				int totaltc=0;
				
				for(currentTestCaseID=2;currentTestCaseID<=currentTestSuiteXLS.getRowCount("Test Cases");currentTestCaseID++)
				{				
					currentTestCaseName=currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID);
				
					
					if(currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID).equals(Constants.RUNMODE_YES)){
						totaltc=totaltc+1;
				
					}
					else
					{
						totaltc=totaltc;
					}
					
				}
				
				
				
				
				for(currentTestCaseID=2;currentTestCaseID<=currentTestSuiteXLS.getRowCount("Test Cases");currentTestCaseID++){				
					APP_LOGS.debug(currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID)+" -- "+currentTestSuiteXLS.getCellData("Test Cases", "Runmode", currentTestCaseID));
					currentTestCaseName=currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID);
				
					
					if(currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID).equals(Constants.RUNMODE_YES)){
						APP_LOGS.debug("Executing the test case -> "+currentTestCaseName);
						tcnumber=tcnumber+1;
						progressbar.pack();
						progressbar.setVisible(true);
						progressbar.setBackground(Color.LIGHT_GRAY);
						progressbar.setFocusable(false);
						progressbar.setSize(500,50);
						progressbar.setTitle("Runnig Test Case:"+" "+tcnumber +" "+"out of"+" "+ totaltc+" "+"on"+" "+CONFIG.getProperty("environment"));
						progressbar.current.setValue(1);
						
					 if(currentTestSuiteXLS.isSheetExist(currentTestCaseName)){
						 refexcelsheetname=currentTestCaseName.toString();	
						 // RUN as many times as number of test data sets with runmode Y
						 //currentTestSuiteXLS=new Xls_Reader(System.getProperty("user.dir")+"//src//com//sample//xls//"+currentTestSuite+".xlsx");
					  for(currentTestDataSetID=2;currentTestDataSetID<=currentTestSuiteXLS.getRowCount(currentTestCaseName);currentTestDataSetID++)	
					  {
						resultSet = new ArrayList<String>();
						screenshotSet = new ArrayList<String>();
						APP_LOGS.debug("Iteration number "+(currentTestDataSetID-1));
						// checking the runmode for the current data set
					   if(currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.RUNMODE, currentTestDataSetID).equals(Constants.RUNMODE_YES)){
						
					    // iterating through all keywords	
						   System.out.println("***************Executing Test Casee***************"+currentTestCaseName);
						   System.out.println("Loading config.properties file for TC "+currentTestCaseName);
						   fs = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//config.properties");
						   System.out.println("Loading config.properties "+fs.toString());
						   CONFIG.load(fs);
						   System.out.println("Loaded "+fs.toString());
						   progressbar.current.setValue(10);
						   
						   executeKeywords(); // multiple sets of data
						  // keywords.closeBrowser("","");
						   progressbar.current.setValue(80);
					   }
					   createXLSReport();
					   progressbar.current.setValue(95);
					   //Logging for Test Case level Status
					   currentTestSuiteXLS.setCellData(Constants.TEST_CASES_SHEET, Constants.TC_STATUS, currentTestCaseID, LogTestCaseResult());
					   progressbar.current.setValue(100);
					   progressbar.setTitle("Finished Test Case"+" "+ tcnumber +" "+"out of"+" "+ totaltc+" "+"on"+CONFIG.getProperty("environment"));
					   
					  }
					 }else{
						// iterating through all keywords	
						 resultSet= new ArrayList<String>();
						 executeKeywords();// no data with the test
						 createXLSReport();
					 }
					}
				}
				
				// create backup file for result set
				String timeStamp = getTimeStamp();
				String source_path = (System.getProperty("user.dir")+"//src//com//sample//xls//"+currentTestSuite+".xlsx").toString();
				System.out.println("Source: "+source_path);
				String dest_path = (System.getProperty("user.dir")+"//src//com//sample//xls//"+currentTestSuite+"_"+ timeStamp +".xlsx").toString();
				System.out.println("Dest: "+dest_path);
				Backup(source_path, dest_path);
				progressbar.dispose();
				//orfs.close();
				
			}
			
		}	
	}
	
	
	public void executeKeywords() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		
		Boolean blnFlag = false;
		
		// iterating through all keywords	
		for(currentTestStepID=2;currentTestStepID<=currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET);currentTestStepID++)
		{
			// checking TCID
		  if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, currentTestStepID))){
						
			columnName=currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.DATA,currentTestStepID  );
			if(columnName.startsWith(Constants.DATA_START_COL)){
				// read actual data value from the corresponding column				
				data=currentTestSuiteXLS.getCellData(currentTestCaseName, columnName.split(Constants.DATA_SPLIT)[1] ,currentTestDataSetID );
			}else if(columnName.startsWith(Constants.CONFIG)){
				//read actual data value from config.properties		
				data=CONFIG.getProperty(columnName.split(Constants.DATA_SPLIT)[1]);
			}
			
			else if(columnName.isEmpty())
			{
				System.out.println("data is empty");
				datarowid=currentTestDataSetID;
				System.out.println("datarow id before function is"+datarowid);
			}
			else{
				//by default read actual data value from or.properties
				data=OR.getProperty(columnName);
			}
			
			
			object=currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.OBJECT,currentTestStepID  );

			//Assign Object as DataSheet for getting Reference of Test Data Sheet
			if (object.equalsIgnoreCase("DATASHEET")){
				object = currentTestCaseName;
				//data = columnName;
				data = columnName.split(Constants.DATA_SPLIT)[1];
			}
			else if(object.isEmpty())
			{
				System.out.println("Object is empty");
				datarowid=currentTestDataSetID;
			}
				currentKeyword=currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.KEYWORD, currentTestStepID);
				APP_LOGS.debug(currentKeyword);
				
				
				if(exectuteTestCase==true){
					progressbar.current.setValue(30);
					FileInputStream orfs;
					try {
						orfs = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//"+currentTestSuite+".properties");
						OR= new Properties();
						OR.load(orfs);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// code to execute the keywords as well
				    // reflection API
					blnFlag = false;
					for(int i=0;i<method.length;i++){
											
						if(method[i].getName().equals(currentKeyword)){
							blnFlag = true;						
							System.out.println("i = "+i);
							System.out.println("method name " + method[i].getName());
							System.out.println("keywords " + keywords);
							System.out.println("object " + object);
							System.out.println("data " + data);
							keyword_execution_result=(String)method[i].invoke(keywords,object,data);
							progressbar.current.setValue(70);
							if(method[i].getName().startsWith("Pre") ||method[i].getName().startsWith("pre") 
									&& !keyword_execution_result.equalsIgnoreCase(Constants.KEYWORD_PASS))
							{
								exectuteTestCase=false;
								String colname="";
								updateStatusAsBlocked(data);
								exectuteTestCase=true;
								//break;
							}
							data="";
							
							//keywords.setSpeed();
							progressbar.current.setValue(80);
							System.out.println(">>>>>>>>"+method[i].getName()+"="+keyword_execution_result);
							APP_LOGS.debug(keyword_execution_result);
							resultSet.add(keyword_execution_result);
							// capture screenshot
							capturescreenShot_method.invoke(keywords,
									currentTestSuite+"_"+currentTestCaseName+"_TS"+currentTestStepID+"_"+(currentTestDataSetID-1),
									keyword_execution_result);
							// how do we call
							// what will be the file name				
						}
						
						else if (i==(method.length-1) && (!blnFlag)){						
							APP_LOGS.debug("Keyword not found :" + currentKeyword);
							resultSet.add("Keyword not found :" + currentKeyword);
						}
						//System.out.println(i + ":" + (method.length-1) + ":" + blnFlag + ":" + currentKeyword);
										
					}
				}else{
				
				//Handle Exception
				/*if(!isColExist)
					currentTestSuiteXLS.addColumn(Constants.TEST_STEPS_SHEET, colName);
				currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, Constants.KEYWORD_SKIP);*/
			}
				
				
			}	
		 /* try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Not able to wait"+e);
		}*/
		}
		
	}
	

	public void createXLSReport(){
		try{
		String colName=Constants.RESULT +(currentTestDataSetID-1);
		boolean isColExist=false;
		String strTemp = null;
		
		for (int c = 0; c < currentTestSuiteXLS
				.getColumnCount(Constants.TEST_STEPS_SHEET); c++) {
			if (currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, c,1).equals(colName)) {
				isColExist = true;
				break;
			}

		}
		
		
		if(!isColExist)
			currentTestSuiteXLS.addColumn(Constants.TEST_STEPS_SHEET, colName);

		
		int index=0;
		System.out.println("Before Loop: " + currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET));
		System.out.println(resultSet.size() + ":" + resultSet);
				
		for(int i=2;i<=currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET);i++){
			
			if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, i))){
				if(resultSet.size()==0){
					currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, Constants.KEYWORD_SKIP);	
				}
				else{	
					currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, resultSet.get(index));
					index++;
				}			
			}
		}
		
		if(resultSet.size()==0){
			// skip
			currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_SKIP);
			return;
		}else{
			for(int i=0;i<resultSet.size();i++){
				if(!resultSet.get(i).equals(Constants.KEYWORD_PASS)){
					currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, resultSet.get(i));
					return;
				}
				
				
			}
		}
		currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_PASS);
	//	if(!currentTestSuiteXLS.getCellData(currentTestCaseName, "Runmode",currentTestDataSetID).equals("Y")){}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error in CreateXLSReport Method " +e);
			
		}

	}
	

	public static void Backup(String source_path, String target_path)
    {
       
    	String timeStamp = getTimeStamp();

    	File source = new File(source_path);
     	File target = new File(target_path);

        File targetDir = new File(System.getProperty("java.io.tmpdir"));
         
        try
        {
            System.out.println("Copying " + source + " file to " + target);
            FileUtils.copyFile(source, target);
            System.out.println("Copying " + source + " file to " + targetDir);
            FileUtils.copyFileToDirectory(source, targetDir);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    static String getTimeStamp() {
    	  DateFormat format = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
    	  String timeStamp = format.format(new Date());
    	  return timeStamp;
    }
    
    public String LogTestCaseResult(){
		
		String strTCResult = Constants.KEYWORD_PASS;
		
		for (int xCount=0; xCount<resultSet.size(); xCount++){
			if(resultSet.get(xCount).startsWith(Constants.KEYWORD_FAIL)){
				strTCResult = Constants.KEYWORD_FAIL;
				break;
				
			}
		}
		
		return strTCResult;
	}
	
    public String updateStatusAsBlocked(String colName) throws IOException
    {
    	APP_LOGS.debug("Executes line of codes to update staus=Blocked and RunMode=N for blocked test cases");
    	try
    	{
    	currentTestSuiteXLS  =  new Xls_Reader(refexcelfilename);
    	String tcSheetName=refexcelsheetname;
    	String refCol=null;
    	String refTcs=null;
    	fis = new FileInputStream(currentTestSuiteXLS.path);
    	workbook = new XSSFWorkbook(fis);
    	APP_LOGS.debug("Gets and captrued name of current working test data sheet");
    	sheet = workbook.getSheet(tcSheetName);
    	//To find out index of refFlag column
    	System.out.println("currentTestSuiteXLS"+currentTestSuiteXLS);
    	System.out.println("cols"+currentTestSuiteXLS.getColumnCount(tcSheetName));
    	int colindex=0,newcolindex=0;
    	startIndex=0;
    	APP_LOGS.debug("Strats to move loop from fist col index of given test data sheet");
    	for(colindex=startIndex;colindex<=currentTestSuiteXLS.getColumnCount(tcSheetName);colindex++)
    	{
    		System.out.println("sheetname is"+sheet.getSheetName());
    		APP_LOGS.debug("gets and returns name of  every columns one by one on first row index");
    		refCol=currentTestSuiteXLS.getCellData(tcSheetName,colindex,1);

    		System.out.println("refCol"+refCol);
    		APP_LOGS.debug("compares and verify column name in data sheet is same as column name provided for keyword");
    		if(refCol.equalsIgnoreCase(colName)==true && refCol.isEmpty()==false)
    		{
    			newcolindex=colindex;
    			colindex=colindex+1;
    			APP_LOGS.debug("gets list of reference test case ids from the RefTCID column");
    			for(int rowindex=2;rowindex<=currentTestSuiteXLS.getRowCount(tcSheetName);rowindex++)
    			{

    				refTcs=currentTestSuiteXLS.getCellData(tcSheetName, "RefTCID", rowindex);
    				System.out.println("refTcs===="+refTcs);
    				sheet = workbook.getSheet(Constants.TEST_CASES_SHEET);
    				APP_LOGS.debug("gets list of reference test case ids and splits it from comma");
    				String [] items = refTcs.split(",");     
    				List<String> container = Arrays.asList(items);
    				APP_LOGS.debug("start to move loop in test case sheet in current xls suite");
    				for(int startindex=1;startindex<=currentTestSuiteXLS.getRowCount(Constants.TEST_CASES_SHEET);startindex++)
    				{
    					APP_LOGS.debug("start to move loop of list values");
    					for(int index=0;index<container.size();index++)
    					{
    						refTcs=currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, "TCID", startindex);
    						APP_LOGS.debug("compares both tc ids from test case sheet (TCID) and arraylist");
    						if(refTcs.equalsIgnoreCase(container.get(index).trim())==true)
    						{
    							try
    							{
    							APP_LOGS.debug("Mark status N");
    							currentTestSuiteXLS.setCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, startindex, "N");
    							APP_LOGS.debug("Mark status Blocked");
    							currentTestSuiteXLS.setCellData(Constants.TEST_CASES_SHEET,"Status", startindex, "Blocked");
    							result="PASS";
    							}
    							catch(Exception e)
    							{
    							result="FAIL";	
    							}
    						}
    					}	
    				}
    			}

    		}

    	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return result;
    } 
}
