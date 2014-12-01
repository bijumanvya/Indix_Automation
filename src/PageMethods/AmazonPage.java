package PageMethods;

import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.By;

import PageVariables.AmazonPageVar;
import Selenium.SeleniumDriver;

public class AmazonPage extends SeleniumDriver{
	AmazonPageVar pageVar=new AmazonPageVar();
	public static String productUrl="";
	public static String productHeading="";
	
	public void logInToAmazon() {
		try{
			webDriver.get(config.getProperty("amazone.url"));
			webDriver.findElement(By.id(pageVar.accountLink)).click();
			boolean userPage=false;
			try{
				if(webDriver.findElement(By.id(pageVar.userEmail))!=null){
					userPage=true;
				}
			}catch(Exception e){}
			if(userPage){
				webDriver.findElement(By.id(pageVar.userEmail)).sendKeys(config.getProperty("amazone.user"));
				webDriver.findElement(By.id(pageVar.userPassword)).sendKeys(config.getProperty("amazone.password"));
				webDriver.findElement(By.id(pageVar.submitButton)).click();
			}
			try{
				if(webDriver.findElement(By.id(pageVar.searchTextBox))!=null){
					 out.write("Log into Amazon,Success,Success,Pass");
			          out.newLine();
			          Assert.assertEquals("Log into Amazon", "Log into Amazon");
				}
			}catch(Exception e){
				out.write("Log into Amazon,Success,Search box not found,Fail");
		          out.newLine();
		          Assert.assertEquals("Log into Amazon", "Log into Amazon fail");
			}
		}catch(Exception e){
			try {
				out.write("Log into Amazon,Success,"+ e.getMessage()+ ",Fail");
				 out.newLine();
			} catch (IOException e1) {}
			Assert.assertEquals("Log into Amazon", "Log into Amazon fail");
	         
		}
		
	}
	
	public void searchAndSelectProduct(){
		try{
			webDriver.findElement(By.id(pageVar.searchTextBox)).sendKeys(config.getProperty("amazone.searchitem"));
			webDriver.findElement(By.cssSelector(pageVar.searchGoButton)).click();
			productHeading=webDriver.findElement(By.cssSelector(pageVar.productLink)).getText();
			System.out.println(productHeading); ;
			out.write("Selected product's title:"+productHeading+",Success,Success,Pass");
	        out.newLine();
	        webDriver.findElement(By.cssSelector(pageVar.productLink)).click();
	        productUrl=webDriver.getCurrentUrl();
	        out.write("Selected product's URL:"+productUrl+",Success,Success,Pass");
	        out.newLine();
	          System.out.println(productUrl);
	          Assert.assertEquals("Search product in Amazon", "Search product in Amazon");
		}catch(Exception e){
			try {
				out.write("Search product in Amazon,Success,"+ e.getMessage()+ ",Fail");
				 out.newLine();
			} catch (IOException e1) {}
			Assert.assertEquals("Search product in Amazon", "Search product in Amazon fail");
	         
		}
		
	}
	
	public void shareViaEmail(){
		
         try{
        	 webDriver.findElement(By.cssSelector(pageVar.shareViaEmailLink)).click();
       	  if(webDriver.findElement(By.id("ac_input"))!=null){
	        	  String itemCode=webDriver.findElement(By.cssSelector(pageVar.sendEmailListTextBox)).getText();
	        	  webDriver.findElement(By.id("ac_input")).sendKeys(config.getProperty("amazone.shareEmailId"));
	        	  webDriver.findElement(By.id(pageVar.shareEmaiilSendButton)).click();
       	  }
         }catch(Exception e){
        	 try {
				out.write("Share product via email,Success,"+ e.getMessage()+ ",Fail");
				 out.newLine();
				 Assert.assertEquals("Share product via email", "Share product via email fail");
			} catch (IOException e1) {}
			
         }
	}

}
