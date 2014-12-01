package PageMethods;

import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.By;

import PageVariables.GmailPageVariable;
import Selenium.SeleniumDriver;

public class GmailPage extends SeleniumDriver{
		
	GmailPageVariable pageVar=new GmailPageVariable();
	
		public void logInoGmail(){
			try{
				webDriver.get(config.getProperty("gmail.url"));
				try{
					if(webDriver.findElement(By.id(pageVar.signInButton))!=null)
						webDriver.findElement(By.id(pageVar.signInButton)).click();
				}catch(Exception e){}
				
				boolean userPage=false;
				try{
					if(webDriver.findElement(By.id(pageVar.userIdTextBox))!=null){
						userPage=true;
					}
				}catch(Exception e){}
				if(userPage){
					webDriver.findElement(By.id(pageVar.userIdTextBox)).sendKeys(config.getProperty("gmail.user"));
					webDriver.findElement(By.id(pageVar.pswdTextBox)).sendKeys(config.getProperty("gmail.password"));
					if(webDriver.findElement(By.id(pageVar.stayloggedInChkBox)).isSelected()){
						webDriver.findElement(By.id(pageVar.stayloggedInChkBox)).click();
					}
					
					webDriver.findElement(By.id(pageVar.logInButton)).click();
					
					userPage=false;
					try{
						if(webDriver.findElement(By.cssSelector(pageVar.inbox))!=null){
							userPage=true;
						}
					}catch(Exception e){}
					if(userPage){
					 out.write("Logg into gmail,success,success, Pass");
			          out.newLine();
			          Assert.assertEquals("Log into Gmail", "Log into Gmail");
					}else{
						out.write("Logg into gmail,success,could not found inbox, fail");
				          out.newLine();
				          Assert.assertEquals("Log into Gmail", "Log into Gmail fail");
					}
				}
			}catch(Exception e){
				try {
					out.write("Log into Gmail,Success,"+ e.getMessage()+ ",Fail");
					 out.newLine();
				} catch (IOException e1) {}
				 Assert.assertEquals("Log into Gmail", "Log into Gmail fail");
			}
		}
		
		public void searchEmailAndVerifyContent(){
			boolean userPage=false;
			try{
				if(webDriver.findElement(By.cssSelector(pageVar.inbox))!=null){
					userPage=true;
				}
			}catch(Exception e){}
			if(userPage){
					String expurl=AmazonPage.productUrl.split("ref=")[0];
					String expProdname=AmazonPage.productHeading;
		          int count=webDriver.findElements(By.cssSelector(pageVar.inboxMailTable)).size();
		          boolean inboxItem=false;
		          for(int i=1;i<=count;i++){
		        	  String text=webDriver.findElement(By.cssSelector(pageVar.inboxMailTable+":nth-of-type("+ i +")>td+td+td+td+td+td span")).getText();
		        	  System.out.println(text);
		        	  if(text.contains("wants you to see this item at Amazon.com")){
		        		  try {
		  					out.write("Email from amazon,wants you to see this item at Amazon.com,"+ text +", pass");
		  					 out.newLine();
		  				} catch (IOException e) {}
		        		  webDriver.findElement(By.cssSelector(pageVar.inboxMailTable+":nth-of-type("+ i +")>td+td+td+td+td+td span")).click();
		        		  int counts=webDriver.findElements(By.cssSelector("a[href*='"+ expurl + "']")).size();
		        		  String produName="";
		        		  for(int j=0;j<= counts;j++){
		        			  produName =webDriver.findElements(By.cssSelector("a[href*='"+ expurl + "']")).get(j).getText();
		        			  if(produName!="" && produName.equals(expProdname))
		        				  break;
		        		  }
		        		
		        		 Assert.assertEquals(expProdname, produName);
		        		 try {
			  					out.write("Product verified in email with name,Exp:"+ expProdname +", Act:"+ produName +", pass");
			  					out.newLine();
			  					out.write("Product verified in email with link,Exp:"+ expurl +", Act:"+ expurl +", pass");
			  					out.newLine();
			  					 Assert.assertEquals("Product verified in email", "Product verified in email");
			  				} catch (IOException e) {}
		        		
		        		 inboxItem=true;
		        		 break;
		        	  }
			          
		          }
		          if(!inboxItem){
		        	  try {
		  					out.write("Product verified in email,Exp:"+ expProdname +", Not found, fail");
		  					 out.newLine();
		  					
		  				} catch (IOException e) {}
		        	  Assert.assertEquals("Product verified in email", "Product verified in email fail");
		          }
		          
			}else{
				
				try {
					out.write("Logg into gmail,success,could not found inbox, fail");
					 out.newLine();
				} catch (IOException e) {}
				Assert.assertEquals("Logg into gmail", "Logged in fail");
			}
		}
}
