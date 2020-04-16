package appiumtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestCalculator {

	private static AndroidDriver<MobileElement> androidDriver;
	
    @Before
    public void clearCalculator() {
    	MobileElement clear =  androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_clear"));
    	clear.click();
    }
	
	@BeforeClass
	public static void setUp() throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("deviceName", "TabA");
		cap.setCapability("udid", "232406e6");
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformVersion", "9.0.0");
		cap.setCapability("automationName", "UiAutomator2");
		cap.setCapability("appPackage", "com.sec.android.app.popupcalculator");
		cap.setCapability("appActivity", "com.sec.android.app.popupcalculator.Calculator");
				
		URL url =  new URL("http://75.158.114.124:4723/wd/hub");
		androidDriver  = new AndroidDriver<MobileElement>(url, cap);
		
		

	}

	@Ignore
	@Test
	public void testInitialSnapshot() throws IOException, InterruptedException  {
		
			MobileElement clear =  androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_clear"));
			    	clear.click();
					File expectedFile  = new File("C:\\Users\\grewa\\work\\appium\\CalculatorPageSource.txt");
					
					
					String actualPageSource = androidDriver.getPageSource();
					
					if (expectedFile.exists() == false) { 
						BufferedWriter writer = null;
						try  {
							writer  = new BufferedWriter(new FileWriter(expectedFile));
							writer.write(actualPageSource);
							writer.flush();
							writer.close();
							fail("Expected file did not exist. Creating one");	
						} catch(IOException  ioe)  {
							writer.close();
						}
						
					}
					
					File actualFile = new File("C:\\Users\\grewa\\work\\appium\\actual.txt");
					String expected = new String ( Files.readAllBytes( Paths.get("C:\\Users\\grewa\\work\\appium\\CalculatorPageSource.txt") ) );
					BufferedWriter bw = new BufferedWriter(new  FileWriter(actualFile));
					bw.write(actualPageSource);
					bw.flush();
					bw.close();
			//		System.out.println("Expected:  " + expected + "***");
			//		System.out.println("Actual:  " + actualPageSource + "***");
					
					assertEquals("The files differ!", 
						    FileUtils.readFileToString(expectedFile, "utf-8"), 
						    FileUtils.readFileToString(actualFile, "utf-8"));
		
			
	}
	
	
	@Test
	public void testSimpleAdd() {
		MobileElement two  =  androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_02"));
		MobileElement five  =  androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_05"));
		MobileElement add  =  androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_add"));
		MobileElement equals  =  androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_equal"));
		
		two.click();
		add.click();
		five.click();
		equals.click();
		
		MobileElement result = androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_edt_formula"));
		String resultValue = result.getText();
		assertEquals("Addition of 2 and  5: ", "7", resultValue);
	}
	
	@Test
	public void testSimpleSub() {
		androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_01")).click();
		androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_02")).click();
		androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_sub")).click();
		androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_06")).click();
		androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_equal")).click();
		
		MobileElement result = androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_edt_formula"));
		String resultValue = result.getText();
		assertEquals("Subtraction of 6 from 12 is: ", "6", resultValue);
	}
	
	@Test
	public void testSimpleMultiply() {
		MobileElement three  =  androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_03"));
		MobileElement four  =  androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_04"));
		MobileElement mult  =  androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_mul"));
		MobileElement equals  =  androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_equal"));
		
		three.click();
		mult.click();
		four.click();
		equals.click();
		
		MobileElement result = androidDriver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_edt_formula"));
		String resultValue = result.getText();
		assertEquals("Multiplicaton of 3 and  4: ", "12", resultValue);
	}

	@AfterClass
	public static void tearDown()  {
		androidDriver.quit();
	}

}
