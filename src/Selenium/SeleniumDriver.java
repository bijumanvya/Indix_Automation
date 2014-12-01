package Selenium;


import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;


public class SeleniumDriver {

	public static Properties config = new Properties();
	public static WebDriver	webDriver	=	null;
	public static Actions action =null;
	
   public static BufferedWriter out =null;
	
	{
		loadConfig();
	}
	@BeforeClass
	public void testSetup() {
		tearUp();
	}	
	
	
	
	private  void loadConfig() {
		
		System.out.println(System.getProperty("user.dir"));
		File localConfig = new File(System.getProperty("user.dir")+"/resources/automation.properties");
		if (localConfig.exists()) {
			InputStream inStream = null;//getClass().getClassLoader().getResourceAsStream(System.getProperty("user.dir")+"/resources/automation.properties");
			try {
				inStream = new BufferedInputStream(new FileInputStream("./resources/automation.properties"));
				config.load(inStream);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				
				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						
					}
				}
			}
		}
	}
	
	public void tearUp() {
		
		String browser=config.getProperty("browser.type");
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");
            Date date = new Date();
            String Dater = dateFormat.format(date);
			FileWriter fstream = new FileWriter(Dater+"_test_results.csv");
			out = new BufferedWriter(fstream);
			out.write("Test Step,Actual,Expected,Status");
			 out.newLine();
			if(browser.equalsIgnoreCase("IE") || browser.equalsIgnoreCase("Internet Explorer")){
				webDriver	=	createIEDriver() ;  			
			}else if(browser.equalsIgnoreCase("FF") || browser.equalsIgnoreCase("firefox")){
				webDriver	=	createFirefox();
			}else if(browser.equalsIgnoreCase("Chrome") ){
				webDriver	=	createChromeDriver();
			}
			webDriver.get("http://www.google.com");
			
			webDriver.manage().window().maximize();
			webDriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			action = new Actions(webDriver);
		} catch(Exception e){
			e.printStackTrace();
			Assert.assertEquals("Failed to create webdriver", "Create webdriver");
		}
		
	}
	
	public WebDriver createIEDriver(){
		String systemOSBit= System.getProperty("os.arch");
				
		if(systemOSBit.contains("64")) {
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+File.separator+ "resources" + File.separator + "IEDriverServer-64.exe");	
		}
		else {
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+File.separator+ "resources" + File.separator + "IEDriverServer-32.exe");
	    }
		WebDriver driver = new InternetExplorerDriver();
		return driver;
	} 
	
	public WebDriver createChromeDriver(){
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+ "resources" + File.separator + "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		return driver;
	}
	
	public WebDriver createFirefox() {
		WebDriver driver=new FirefoxDriver();
		return driver;
	}
	
	
	public void tearDown(){
		webDriver.quit();
		
		 try {
			out.flush();
			 out.close();
		} catch (IOException e) {}
	       
	}
}
