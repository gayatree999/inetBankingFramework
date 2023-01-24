package com.inetbanking.testCases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.inetbanking.utilities.ReadConfig;

public class BaseClass {

	ReadConfig readConfig = new ReadConfig();

	public String baseUrl = readConfig.getApplicationURL();
	public String username = readConfig.getUserName();
	public String password = readConfig.getPassword();
	public static WebDriver driver;
	public static Logger logger;

	@Parameters("browser")
	@BeforeClass
	public void setup(String br) {
		logger = Logger.getLogger("ebanking");
		PropertyConfigurator.configure("log4j.properties");

		if (br.equals("chrome")) {
			// System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", readConfig.getChromePath());
			driver = new ChromeDriver();
		} else if (br.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", readConfig.getFirefoxPath());
			driver = new ChromeDriver();
		} else if (br.equals("msedge")) {
			System.setProperty("webdriver.edge.driver", readConfig.getMSEdgePath());
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(baseUrl);

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot Taken");
	}

	public String randomstring() {
		String generatedstring = RandomStringUtils.randomAlphabetic(8);
		return generatedstring;
	}

	public static String randomNum() {
		String generatedString2 = RandomStringUtils.randomNumeric(4);
		return generatedString2;
	}

}
