package TestPackage;


import org.testng.annotations.Test;

import PageMethods.AmazonPage;
import PageMethods.GmailPage;
import Selenium.SeleniumDriver;

public class TestClass extends SeleniumDriver{
	
	static public String productUrl="";
	static public String productName="";
	AmazonPage amazone=new AmazonPage();
	GmailPage gmail=new GmailPage();
	
	@Test
	public void amazonProductShareViaEmail(){
		try{
			//call method to log in to amazon
			amazone.logInToAmazon();
			/*
			//call method to search a product in amazon and select. Get product details from UI- product name and product's amazon URL.
			amazone.searchAndSelectProduct();
			
			//Click on share product details via email option and enter email ID and click send.
			amazone.shareViaEmail();
			
			//log into gmail with user ID, the one used in share product details via email.
			gmail.logInoGmail();
			
			//search inbox for the email from amazon, select mail and verify the mail content, product name and URL, against the amazon UIs stored values.
			gmail.searchEmailAndVerifyContent();
			
			*/
		}finally {
			tearDown();
		}
		
	}
	

}
