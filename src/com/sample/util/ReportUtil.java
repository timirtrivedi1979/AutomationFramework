package com.sample.util;
// reads the xls files and generates corresponding html reports
// Calls sendmail - mail
import java.io.*;
import java.util.Date;
import java.util.Properties;

import com.sample.test.Constants;
import com.sample.xls.read.Xls_Reader;




public class ReportUtil {
	public static String result_FolderName=null;
	public static String report_FolderName=null;
	
	//public static void main(String[] arg) throws Exception 
	public void generateReport()throws Exception 
	{
	
		// read suite.xls
		System.out.println("executing");
		Date d = new Date();
		String date=d.toString().replaceAll(" ", "_");
		date=date.replaceAll(":", "_");
		date=date.replaceAll("\\+", "_");
		System.out.println(date);
		report_FolderName="HTML Reports";
		new File(report_FolderName).mkdirs();
		
		result_FolderName="Reports"+"_"+date;
		String reportsDirPath=System.getProperty("user.dir")+"\\"+report_FolderName+"\\"+result_FolderName;
		//String reportsDirPath=System.getProperty("user.dir")+"\\"+report_FolderName;
		new File(reportsDirPath).mkdirs();
		int rowmoduleindex=2;
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//config.properties");
		Properties CONFIG= new Properties();
		CONFIG.load(fs);
		String environment=CONFIG.getProperty("environment");
		String release=CONFIG.getProperty("release");
		Xls_Reader suiteXLS = new Xls_Reader(System.getProperty("user.dir")+"//src//com//sample//xls//Automation_Suite.xlsx");
		String finalresult=null;
		String finalresultsuite=null;
		Xls_Reader current_suite_xls=null;
		Xls_Reader current_suite_xls1=null;
		int pcount=0;
		int fcount=0;
		int row=0;
		int totalf=0;
		int totalp=0;
		int prowindex=0;
		// create index.html
		String indexHtmlPath=report_FolderName+"\\"+result_FolderName+"\\index.html";
		String testSteps_file=null;
		new File(indexHtmlPath).createNewFile();
		/*String testcaseFilePath=result_FolderName+"\\testcaselist.html";
		new File(testcaseFilePath).createNewFile();*/

		FileInputStream fs1 = new FileInputStream(System.getProperty("user.dir")+"//src//com//sample//config//config.properties");
		CONFIG= new Properties();


		try{

			FileWriter fstream = new FileWriter(indexHtmlPath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("<html><HEAD> <TITLE>Automation Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> Automation Test Results</u></b></h4><table  border=1 cellspacing=1 cellpadding=1 ><tr><h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Test Details :</u></h4><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Date</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			out.write(d.toString());
			
			out.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Environment</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			out.write(environment);
			out.write("</b></td></tr><tr><td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Release</b></td><td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>");
			out.write(release);
			out.write("</b></td></tr><tr><td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Browser</b></td><td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>");
			
			CONFIG.load(fs1);
	 		String browser= CONFIG.getProperty("browserType").toString();
			out.write(browser);
			out.write("</b></td></tr></table>");
			fs1.close();
			
			
			
			out.write("<h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Summary Report :</u></h4>");
			
			out.write("<table  border=1 cellspacing=1 cellpadding=1 width=30%><tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>SUITE NAME</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Total Pass</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Total Fail</b></td></tr>");
			int totalTestSuites1=suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET);
			
				String currentTestSuite1=null;

				String suite_result1="";
				for(int currentSuiteID1 =2;currentSuiteID1<= totalTestSuites1;currentSuiteID1++)
				{
				
					suite_result1="";
					currentTestSuite1=null;
					current_suite_xls1=null;
					currentTestSuite1 = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.SUITE_ID,currentSuiteID1);
					current_suite_xls1=new Xls_Reader(System.getProperty("user.dir")+"//src//com//sample//xls//"+currentTestSuite1+".xlsx");
					String currentTestName1=null;
					String currentTestRunmode1=null;
					String currentTestDescription1=null;
					String testcaseFilePath1=currentTestSuite1;
					out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
					out.write(currentTestSuite1);
					
					for(prowindex=2;prowindex<=current_suite_xls1.getRowCount("Test Cases");prowindex++)
					{
						if(current_suite_xls1.getCellData("Test Cases", "Status", prowindex).equalsIgnoreCase("PASS")&& current_suite_xls1.getCellData("Test Cases", "Status", prowindex).isEmpty()==false)
						{
							pcount++;
							
						}
						else if(current_suite_xls1.getCellData("Test Cases", "Status", prowindex).equalsIgnoreCase("FAIL") && current_suite_xls1.getCellData("Test Cases", "Status", prowindex).isEmpty()==false)
						{
							fcount++;
							
						}
						
					}
					
					
					out.write("</b></td><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
					String totalpass=String.valueOf(pcount);
					out.write(totalpass);
					out.write("</b/></td><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
					String totalfail=String.valueOf(fcount);
					out.write(totalfail);
					pcount=0;
					fcount=0;
				}
				out.write("</td></tr></table>");
				/*out.write("</b/><tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				out.write("TOTAL");
				out.write("</b/></td><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				String totalpc=String.valueOf(totalp);
				out.write(totalpc);
				out.write("</b/></td><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				String totalfc=String.valueOf(totalf);
				out.write(totalfc);
				out.write("</td></tr></table>");*/
			
			
				out.write("<h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Detailed Report :</u></h4>");
			
			out.write("<table  border=1 cellspacing=1 cellpadding=1 width=100%><tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>SUITE NAME</b></td><td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>DESCRIPTION</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>EXECUTION RESULT</b></td></tr>");

			int totalTestSuites=suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET);
			String currentTestSuite=null;

			String suite_result="";
			for(int currentSuiteID =2;currentSuiteID<= totalTestSuites;currentSuiteID++)
			{
				suite_result="";
				currentTestSuite=null;
				current_suite_xls=null;
				currentTestSuite = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.SUITE_ID,currentSuiteID);
				current_suite_xls=new Xls_Reader(System.getProperty("user.dir")+"//src//com//sample//xls//"+currentTestSuite+".xlsx");

				String currentTestName=null;
				String currentTestRunmode=null;
				String currentTestDescription=null;
				String testcaseFilePath=currentTestSuite;
				new File(testcaseFilePath).createNewFile();

				//Code to create test case files
				for(int currentTestCaseID=2;currentTestCaseID<=current_suite_xls.getRowCount(Constants.TEST_CASES_SHEET);currentTestCaseID++)
				{
					currentTestName=null;
					currentTestDescription=null;
					currentTestRunmode=null;

					currentTestName = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID);
					currentTestDescription = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.DESCRIPTION, currentTestCaseID);
					//currentTestRunmode = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID);
					// make the file corresponding to test Steps

					testSteps_file=report_FolderName+"\\"+result_FolderName+"\\"+currentTestSuite+"_steps.html";
					new File(testSteps_file).createNewFile();
					int rows= current_suite_xls.getRowCount(Constants.TEST_CASES_SHEET);
					int cols = current_suite_xls.getColumnCount(Constants.TEST_CASES_SHEET);
					FileWriter fstream_test_steps= new FileWriter(report_FolderName+"\\"+result_FolderName+"\\"+testcaseFilePath+".html");
					BufferedWriter out_test_steps= new BufferedWriter(fstream_test_steps);
					out_test_steps.write("<html><HEAD> <TITLE>"+currentTestSuite+" Detail Test Results</TITLE></HEAD><body><table><tr><td><a href=index.html><h4 align=left><FONT COLOR=660066 FACE=AriaL SIZE=1><b><u>Back To Home Page</u></b></h4></a></td><td><a href="+currentTestSuite+"_steps.html><h4 align=right><FONT COLOR=660066 FACE=AriaL SIZE=1><b><u>Click For Detailed Result</u></b></h4></a></td></tr></table><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> "+currentTestSuite+" Test Case Result</u></b></h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");
					out_test_steps.write("<tr>");
					for(int colNum=0;colNum<cols;colNum++)
					{
						if(colNum!=2)
							out_test_steps.write("<td align= left bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
						if (current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, colNum, 1).isEmpty()){
							out_test_steps.write("--");  
						}
						else if(colNum!=2){
							out_test_steps.write(current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, colNum, 1));  
						}							  
					}

					out_test_steps.write("</b></tr>");

					// fill the whole sheet
					boolean result_col=false;
					for(int rowNum=2;rowNum<=rows;rowNum++)
					{
						out_test_steps.write("<tr>");
						String tcIDdata=current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, 0, rowNum);
						for(int colNum=0;colNum<cols;colNum++)
						{
							String data=null;
							if(colNum==2)
							{
								data="";
							}
							else
							{
							data=current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, colNum, rowNum);
							
							result_col=current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, colNum, 1).startsWith(Constants.TC_STATUS);
							
							}
							if(data.isEmpty()){
								if(result_col)
									data="SKIP";  
								/*else
									data="--";*/
								
							}
							
							if((data.startsWith("Pass") || data.startsWith("PASS")) && result_col && colNum!=2)
								out_test_steps.write("<td align=left bgcolor=green><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							else if((data.startsWith("Fail") || data.startsWith("FAIL")) && result_col && colNum!=2){
								out_test_steps.write("<td align=center bgcolor=red><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
								if(suite_result.equals(""))
									suite_result="FAIL";
							}
							else if((data.startsWith("Skip") || data.startsWith("SKIP")) ||data.contains("-") && result_col && colNum!=2)
								out_test_steps.write("<td align=left bgcolor=yellow><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							else if(colNum!=2)
								out_test_steps.write("<td align= left bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							out_test_steps.write(data);
							// out_test_steps.write(data);

						}
						out_test_steps.write("</tr>");
					}
					out_test_steps.write("</tr>");
					out_test_steps.write("</table>");  
					out_test_steps.close();

				} 

				//Code complete for creation of test case files


				out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				out.write("<a href="+testcaseFilePath+".html>"+currentTestSuite+"</a>");
				out.write("</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				out.write(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.DESCRIPTION,currentSuiteID));
				out.write("</b></td><td width=10% align=center  bgcolor=");


				int rowm;

				System.out.println("Total rows are"+suiteXLS.getRowCount("Test Suite"));
				System.out.println("currentSuiteID="+currentSuiteID);
				for(int rowmodule=currentSuiteID;rowmodule<=suiteXLS.getRowCount("Test Suite");rowmodule++)
				{
					System.out.println("rowmoduleindex="+rowmodule);
					if(suiteXLS.getCellData("Test Suite","Runmode",rowmodule).contains("N"))
					{
						finalresultsuite="SKIP";
						finalresult="SKIP";
						out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"+finalresult+"</b></td></tr>");
						break;
					}
					else if(suiteXLS.getCellData("Test Suite","Runmode",rowmodule).equalsIgnoreCase("Y"))

					{
						System.out.println("N rowmoduleindex="+rowmodule);
						current_suite_xls=new Xls_Reader(System.getProperty("user.dir")+"//src//com//sample//xls//"+currentTestSuite+".xlsx");
						for(row=2;row<=current_suite_xls.getRowCount(Constants.TEST_CASES_SHEET);row++)
						{
							
							if(current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TC_STATUS,row).equalsIgnoreCase(Constants.KEYWORD_FAIL))
							{
								finalresult="FAIL";
								//out.write("red><FONT COLOR=153E7E FACE=Arial SIZE=2><b>FAIL</b></td></tr>");
								break;
							}
							else if(current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TC_STATUS,row).equalsIgnoreCase(Constants.KEYWORD_PASS))
							{
								
								finalresult="PASS";
								
								//out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
							}
							
							
						}
						System.out.println("after loop row="+row);
						
						if(finalresult=="PASS")
						{
							out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
							break;
						}
						else if(finalresult=="FAIL")
						{
							out.write("red><FONT COLOR=153E7E FACE=Arial SIZE=2><b>FAIL</b></td></tr>");
							break;
						}
						else if(finalresult=="SKIP")
						{
							out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"+finalresult+"</b></td></tr>");
							break;
						}
					}
						
				}
				
				
				//for  test steps 

				int  rows= current_suite_xls.getRowCount(Constants.TEST_STEPS_SHEET);
				int  cols = current_suite_xls.getColumnCount(Constants.TEST_STEPS_SHEET);
				FileWriter  fstream_test_steps= new FileWriter(testSteps_file);
				BufferedWriter out_test_steps= new BufferedWriter(fstream_test_steps);
				out_test_steps.write("<html><HEAD> <TITLE>"+currentTestSuite+"Detail Test Results</TITLE></HEAD><body><table><tr><td><a href=index.html><h4 align=left><FONT COLOR=660066 FACE=AriaL SIZE=1><b><u>Back To Home Page</u></b></h4></a></td></tr></table><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> "+currentTestSuite+" Detailed Test Case Result</u></b></h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");
				out_test_steps.write("<tr>");
				for(int colNum=0;colNum<cols;colNum++){
					out_test_steps.write("<td align= left bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
					if (current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1).isEmpty()){
						out_test_steps.write("--");  
					}
					else{
						out_test_steps.write(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1));  
					}							  
				}

				out_test_steps.write("</b></tr>");

				// fill the whole sheet
				boolean result_col=false;
				for(int rowNum=2;rowNum<=rows;rowNum++)
				{
					out_test_steps.write("<tr>");
					String tcIDdata=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, 0, rowNum);

					for(int colNum=0;colNum<cols;colNum++){
						String data=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, rowNum);

						result_col=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1).startsWith(Constants.RESULT);
						if(data.isEmpty()){
							if(result_col)
								data="SKIP";  
							else
								data="--";
						}
						if((data.startsWith("Pass") || data.startsWith("PASS")) && result_col)
							out_test_steps.write("<td align=left bgcolor=green><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
						else if((data.startsWith("Fail") || data.startsWith("FAIL")) && result_col){
							out_test_steps.write("<td align=center bgcolor=red><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							if(suite_result.equals(""))
								suite_result="FAIL";
						}
						else if((data.startsWith("Skip") || data.startsWith("SKIP")) && result_col)
							out_test_steps.write("<td align=left bgcolor=yellow><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
						else 
							out_test_steps.write("<td align= left bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
						out_test_steps.write(data);
						// out_test_steps.write(data);

					}
					out_test_steps.write("</tr>");
				}
				out_test_steps.write("</tr>");


				out_test_steps.write("</table>");  
				out_test_steps.close();

				// }
			}
			//Close the output stream
			out.write("</table>");
			out.close();

		}
		catch (Exception e){//Catch exception if any
			//  System.err.println("Error: " + e.getMessage());
			//  e.printStackTrace();
		}

		//SendMail.execute(CONFIG.getProperty("report_file_name"));


	}


}

