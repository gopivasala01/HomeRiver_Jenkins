package Alabama;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import mainPackage.RunnerClass;
//import mainPackage.AppConfig;
import Alabama.AppConfig;
import mainPackage.InsertDataIntoDatabase;

public class AL_RunnerClass 
{
public static ChromeDriver AZ_driver;
public static Actions AZ_actions;
public static JavascriptExecutor AZ_js;
public static File AZ_file;
public static FileInputStream AZ_fis;
public static StringBuilder AZ_stringBuilder = new StringBuilder();
public static WebDriverWait AZ_wait;
public static FileOutputStream AZ_fos;
public static String pdfFormatType;

	public boolean runAutomation(String portfolio, String leaseName, String leaseOwnername)  throws Exception
	{
		
		//AZ_RunnerClass AZ_runnerClass = new AZ_RunnerClass();
		AL_RunnerClass.openBrowser();
		//Login to Propertyware
		AL_PropertyWare downloadLeaseAgreement =new  AL_PropertyWare();
		downloadLeaseAgreement.login();
		
		boolean selectLeaseResult = downloadLeaseAgreement.selectLease(leaseName);
		if(selectLeaseResult==false)
			return false;
		//Empty all static variable values
		AL_RunnerClass.emptyAllValues();
		
		boolean downloadLeaseAgreementResult =  downloadLeaseAgreement.validateSelectedLease(leaseOwnername);//leaseOwnername
		if(downloadLeaseAgreementResult==false)
			return false;
		//Extract data from PDF
		
		if(RunnerClass.portfolio.contains("MAN")||RunnerClass.portfolio.contains("HS")||RunnerClass.portfolio.contains("MCH"))
		{
		AL_PropertyWare.portfolioType = "MCH";
		}
		else
		AL_PropertyWare.portfolioType = "Others";
		
		// Decide the PDF Format
        pdfFormatType = AL_RunnerClass.decidePDFFormat();
        if(pdfFormatType.equalsIgnoreCase("Format1"))
        {
        	System.out.println("PDF Type = Format 1");
        	ExtractDataFromPDF getDataFromPDF = new ExtractDataFromPDF();
    		boolean getDataFromPDFResult =  getDataFromPDF.arizona();
    		if(getDataFromPDFResult == false)
    			return false;
        }
        else if(pdfFormatType.equalsIgnoreCase("Format2"))
             {
        	    System.out.println("PDF Type = Format 2");
        	    ExtractDataFromPDF_Format2 getDataFromPDF_format2 = new ExtractDataFromPDF_Format2();
	    		boolean getDataFromPDFResult =  getDataFromPDF_format2.arizona();
	    		if(getDataFromPDFResult == false)
    			return false;
             }
             else
             {
            	 System.out.println("PDF Type = Not Supported Format");
            	 return false;
             }
        String startDate="";
        try
        {
        	startDate= RunnerClass.convertDate(AL_PropertyWare.commensementDate).trim();
        }
        catch(Exception e)
        {
        	InsertDataIntoDatabase.notAutomatedFields(RunnerClass.leaseName, "Unable to get Start Date"+'\n');
        	return false;
        }
        String endDate = RunnerClass.convertDate(AL_PropertyWare.expirationDate).trim();
		//Check if the Start Date, End Date and Move In Date matches in both PW and Lease Agreement
        if(!AL_PropertyWare.leaseStartDate_PW.trim().equalsIgnoreCase(startDate))
        {
        	System.out.println("Start Date doesn't Match");
 	    	InsertDataIntoDatabase.notAutomatedFields(RunnerClass.leaseName, "Start Date is not matched"+'\n');
        }
        if(!AL_PropertyWare.leaseEndDate_PW.trim().equalsIgnoreCase(endDate))
        {
        	System.out.println("End Date doesn't Match");
 	    	InsertDataIntoDatabase.notAutomatedFields(RunnerClass.leaseName, "End Date is not matched"+'\n');
        }
        /*
        if(!AL_PropertyWare.leaseStartDate_PW.trim().equalsIgnoreCase(RunnerClass.convertDate(AL_PropertyWare.commensementDate).trim()))
        {
        	System.out.println("Start Data doesn't Match");
 	    	InsertDataIntoDatabase.notAutomatedFields(RunnerClass.leaseName, "Start Date is not matched in PropertyWare and Lease Agreement");
        }
        */
        
		//Insert data into propertyware
		//Check the Portfolio Type - MCH&HS or Other personal portfolios
		
		//if(RunnerClass.portfolio.contains("MAN")||RunnerClass.portfolio.contains("HS"))
		//{
		//AL_PropertyWare.portfolioType = "MCH";
			/*
		AL_InsertDataIntoPropertyWare insertDataInPW_MCH_HS = new AL_InsertDataIntoPropertyWare();
			boolean insertingDataResult =  insertDataInPW_MCH_HS.insertData();
			//AZ_driver.close();
			return true;*/
		//}
		//else
		//{
		//	AL_PropertyWare.portfolioType = "Others";
			/*
			AL_InsertDataIntoPropertyWare_OtherPortfolios insertDataInPW_Other = new AL_InsertDataIntoPropertyWare_OtherPortfolios();
		    boolean insertingDataResult =  insertDataInPW_Other.insertData();
		    //AZ_driver.close();
		    return true;
		    */
		//}
		
        InsertDataIntoPropertyWare_UsingConfigTable.insertData();
		return true;
	}

	public  static void openBrowser()
	{
		Map<String, Object> prefs = new HashMap<String, Object>();
        // Use File.separator as it will work on any OS
        prefs.put("download.default_directory",
                "C:\\Gopi\\Projects\\Property ware\\Lease Close Outs\\PDFS");
        // Adding cpabilities to ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        // Printing set download directory
         
        // Launching browser with desired capabilities
        WebDriverManager.chromedriver().setup();
        AZ_driver= new ChromeDriver(options);
        AZ_actions = new Actions(AZ_driver);
        AZ_js = (JavascriptExecutor)AZ_driver;
        AZ_driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        AZ_wait = new WebDriverWait(AZ_driver, Duration.ofSeconds(50));
       // AZ_driver.get(AppConfig.propertyWareURL);
        
	}
	
	public static String decidePDFFormat() throws Exception
	{
		try
		{
		File file = RunnerClass.getLastModified();
		FileInputStream fis = new FileInputStream(file);
		PDDocument document = PDDocument.load(fis);
	    String text = new PDFTextStripper().getText(document);
	    AL_PropertyWare.pdfText  = text;
	    if(text.contains(AppConfig.PDFFormatConfirmationText)) 
	    {
	    	
			return "Format1";
	    	
	    }
	    else if(text.contains(AppConfig.PDFFormat2ConfirmationText))
	         {
            return "Format2";	
	         }
	         else 
	         {
	        	System.out.println("Wrong PDF Format");
	 	    	InsertDataIntoDatabase.notAutomatedFields(RunnerClass.leaseName, "Wrong Lease Agreement PDF Format"+'\n');
	 			RunnerClass.leaseCompletedStatus = 3;
	 			return "Others";
	          }
		}
		catch(Exception e)
		{
			System.out.println("Lease Agreement was not downloaded, Bad Network");
 	    	InsertDataIntoDatabase.notAutomatedFields(RunnerClass.leaseName, "Lease Agreement was not downloaded, Bad Network"+'\n');
 			RunnerClass.leaseCompletedStatus = 3;
 			return "Others";
		}
	}
	public static void emptyAllValues()
	{
		AL_PropertyWare.commensementDate ="";
		AL_PropertyWare.expirationDate ="";
		AL_PropertyWare.proratedRent ="";
		AL_PropertyWare.proratedRentDate ="";
		AL_PropertyWare.monthlyRent="";
		AL_PropertyWare.monthlyRentDate="";
		AL_PropertyWare.adminFee="";
		AL_PropertyWare.airFilterFee="";
		AL_PropertyWare.earlyTermination="";
		AL_PropertyWare.occupants="";
		AL_PropertyWare.lateChargeDay="";
		AL_PropertyWare.lateChargeFee="";
		AL_PropertyWare.proratedPetRent="";
		AL_PropertyWare.petRentWithTax="";
		AL_PropertyWare.proratedPetRentDate="";
		AL_PropertyWare.petSecurityDeposit="";
		AL_PropertyWare.RCDetails="";
		AL_PropertyWare.petRent="";
		AL_PropertyWare.petFee="";
		AL_PropertyWare.pet1Type="";
		AL_PropertyWare.pet2Type="";
		AL_PropertyWare.serviceAnimalType="";
		AL_PropertyWare.pet1Breed="";
		AL_PropertyWare.pet2Breed="";
		AL_PropertyWare.serviceAnimalBreed="";
		AL_PropertyWare.pet1Weight="";
		AL_PropertyWare.pet2Weight="";
		AL_PropertyWare.serviceAnimalWeight="";
		AL_PropertyWare.petOneTimeNonRefundableFee="";
		AL_PropertyWare.countOfTypeWordInText=0;
		AL_PropertyWare.lateFeeChargeDay="";
		AL_PropertyWare.lateFeeAmount="";
		AL_PropertyWare.lateFeeChargePerDay="";
		AL_PropertyWare.additionalLateCharges="";
		AL_PropertyWare.additionalLateChargesLimit="";
		AL_PropertyWare.CDEType="";
		AL_PropertyWare.monthlyTenantAdminFee_Amount=0.00;
		AL_PropertyWare.calculatedPetRent=0.00;
		AL_PropertyWare.df = new DecimalFormat("0.00");
		AL_PropertyWare.pdfText="";
		AL_PropertyWare.securityDeposit="";
		AL_PropertyWare.leaseStartDate_PW="";
		AL_PropertyWare.leaseEndDate_PW="";
		AL_PropertyWare.prepaymentCharge="";
		AL_PropertyWare.petType=null;
		AL_PropertyWare.petBreed=null;
		AL_PropertyWare.petWeight=null;
		AL_PropertyWare.robot=null;
		AL_PropertyWare.concessionAddendumFlag = false;
		AL_PropertyWare.petSecurityDepositFlag = false;
		AL_PropertyWare.petFlag = false;
		AL_PropertyWare.portfolioType="";
		AL_PropertyWare.incrementRentFlag = false;
		AL_PropertyWare.proratedRentDateIsInMoveInMonthFlag=false;
		AL_PropertyWare.increasedRent_previousRentStartDate ="";
		AL_PropertyWare.increasedRent_previousRentEndDate ="";
		AL_PropertyWare.increasedRent_amount ="";
		AL_PropertyWare.increasedRent_newStartDate ="";
		AL_PropertyWare.increasedRent_newEndDate ="";
	}
	
	
	

}
