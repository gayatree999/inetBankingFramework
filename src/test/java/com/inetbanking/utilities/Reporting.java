package com.inetbanking.utilities;

//Listener class used to generate Extent Reports
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporting<ExtentHtmlReporter> extends TestListenerAdapter {

	public ExtentSparkReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;

	public void onStart(ITestContext testContext) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp
		String repName = "Test-Report-" + timeStamp + ".html";
		htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/" + repName); // Specify
																											// location
		try {
			htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("user", "gayatri");

		htmlReporter.config().setDocumentTitle("Internet Test Project"); // Title of Report
		htmlReporter.config().setReportName("Functional Test Report");// name of the report
		// htmlReporter.config().setTestViewChartLocation(chartLocation.TOP);//location
		// of the chart
		htmlReporter.config().setTheme(Theme.DARK);
	}

	public void onTestSuccess(ITestResult tr) {
		logger = extent.createTest(tr.getName());// create new entry in the report
		logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
	}

	public void onTestFailure(ITestResult tr) {
		logger = extent.createTest(tr.getName());
		logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));

		String screenShotPath = System.getProperty("user.dir") + "\\Screenshots\\" + tr.getName() + ".png";
		System.out.println(screenShotPath);
		File f = new File(screenShotPath);

		if (f.exists()) {
			logger.fail("screenshot is below:" + logger.addScreenCaptureFromPath(screenShotPath));

		}

	}

	public void onTestSkipped(ITestResult tr) {
		logger = extent.createTest(tr.getName()); // create new entry in the report
		logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
	}

	public void onFinish(ITestContext testContext) {
		extent.flush();
	}

}
