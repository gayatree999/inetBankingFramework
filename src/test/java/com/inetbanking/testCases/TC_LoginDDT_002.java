package com.inetbanking.testCases;

import java.io.IOException;

import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inetbanking.pageObjects.LoginPage;
import com.inetbanking.utilities.XLUtils;

public class TC_LoginDDT_002 extends BaseClass {

	@Test(dataProvider = "LoginData")
	public void loginDDT(String user, String pwd) throws InterruptedException {
		LoginPage lp = new LoginPage(driver);
		logger.info("websiteloaded");
		logger.info("user name :"+user);
		lp.setUserName(user.trim());
		logger.info("user name is provided");
		lp.setPassword(pwd.trim());
		logger.info("Password is provided");
		logger.info("pwd provides is-"+pwd);
		lp.clickSubmit();
		
		Thread.sleep(3000);

		if (isAlertPresent() == true) {
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			Assert.assertTrue(false);
			logger.warning("Login Failed");
		} else {
			Assert.assertTrue(true);
			logger.info("Login Passed");
			lp.clickLogout();
			Thread.sleep(3000);
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
		}

	}

	public boolean isAlertPresent() { // user defined method to check alert is present or not
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}

	}

	@DataProvider(name = "LoginData")
	String[][] getData() throws IOException {
		String path = "C:\\Users\\gayat\\eclipse-workspace\\inetBankingV1Framework\\src\\test\\java\\com\\inetbanking\\testData\\LoginData.xlsx";

		int rownum = XLUtils.getRowCount(path, "Sheet1");
		int colcount = XLUtils.getCellCount(path, "Sheet1", 1);
		logger.info("row:"+rownum);
		logger.info("col:"+colcount);
		String logindata[][] = new String[rownum][colcount];

		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colcount; j++) {
				logindata[i-1][j] = XLUtils.getCellData(path, "Sheet1", i, j);
			}
		}

		return logindata;
	}

}
