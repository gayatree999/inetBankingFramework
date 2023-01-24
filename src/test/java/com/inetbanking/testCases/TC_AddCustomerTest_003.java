package com.inetbanking.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.inetbanking.pageObjects.AddCustomerPage;
import com.inetbanking.pageObjects.LoginPage;

public class TC_AddCustomerTest_003 extends BaseClass{
	
	@Test
	public void addNewCustomer() throws InterruptedException, IOException {
		LoginPage lp = new LoginPage(driver);
		lp.setUserName(username);
		logger.info("user name is provided");
		lp.setPassword(password);
		logger.info("user password is provided");
		lp.clickSubmit();
		
		Thread.sleep(3000);
		AddCustomerPage addcust= new AddCustomerPage(driver);
		addcust.clickAddNewCustomer();
		
		
		logger.info("providing customer details");
		
		addcust.custName("gayatri");
		addcust.custgender("female");
		addcust.custdob("10", "06", "1993");
		Thread.sleep(3000);
		addcust.custaddress("INDIA");
		addcust.cuscity("Blr");
		addcust.cusstate("Karnataka");
		addcust.custpinno(500077);
		addcust.custtelephoneno("9878000067");
		String email = randomstring()+"@gmai.com";
		addcust.custemailid(email);
		addcust.cuspassword("abcdef");
		addcust.custsubmit();
		
		Thread.sleep(3000);
		
		logger.info("validation started");
		
		boolean res = driver.getPageSource().contains("Customer Registered Successfully!!!");
		if(res==true) {
			Assert.assertTrue(true);
			logger.info("TC passed...");
		}
		else {
			captureScreen(driver, "addNewCustomer");
			Assert.assertTrue(false);
			logger.info("TC failed...");
		}
			
		
		
	}
	
	

}
