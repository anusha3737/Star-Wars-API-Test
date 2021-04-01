package dev.swapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class ListOfChar {

//Create Driver
	private WebDriver driver;

	@BeforeMethod(alwaysRun = true)
	private void openChrome() {

		System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver1.exe");
		driver = new ChromeDriver();

		// maximize browser window
		driver.manage().window().maximize();

	}
	

	// Test Case 1: Displaying list of all star wars characters
	/*
	 * //Steps Performed - 1.perform search for each page 
	 * 2. Retrieve the results from API 
	 * 3. Compare the API results to displayed result using JSON compare
	 *  4. If the results are equal, Then print the list of characters from each page.
	 */
	
	@Test(enabled = true)
	public void displayCharTest() {

		// Strings to be search for to display result
		String[] searchelement = {"people/?page=1", "people/?page=2", "people/?page=3", "people/?page=4",
				"people/?page=5", "people/?page=6", "people/?page=7", "people/?page=8", "people/?page=9"};
		
		
	//Step 1.perform search for each page 
		//for loop to search and display each search element
		for (String pages : searchelement) // passing each string for search
		{

			// Open test Url
			String url = "https://swapi.dev/";
			driver.get(url);

			// Click on Search field
			WebElement searchfield = driver.findElement(By.xpath("/html//input[@id='interactive']"));
			searchfield.click();

			searchfield.sendKeys(pages);
			
			// click on request button
			WebElement requestclick = driver.findElement(
					By.xpath("/html/body/div[@class='container-fluid']/div[3]//button[@class='btn btn-primary']"));
			requestclick.click();
			
            
			sleep();
			
	//Step 2. Retrieve the results from API 
			baseURI = "https://swapi.dev/api/";
			Response response = get(pages);
			String expectedresult = response.getBody().asString();

			//get the result text from search result
			WebElement lists = driver.findElement(By.xpath("/html//pre[@id='interactive_output']"));
			String actualresult = lists.getText();
			sleep();

	//Step 3. Compare the API results to displayed result using JSON compare
			driver.get("https://jsoncompare.org/");
			WebElement jsoncompare1 = driver.findElement(By.xpath("/html//textarea[@id='textarealeft']"));
			jsoncompare1.sendKeys(expectedresult);
			WebElement jsoncompare2 = driver.findElement(By.xpath("/html//textarea[@id='textarearight']"));
			jsoncompare2.sendKeys(actualresult);
			sleep();
			WebElement comparebutton = driver.findElement(By.id("compare"));
			comparebutton.click();
			sleep();
			String expectedMessage = "The two files were semantically identical.";
			WebElement actualMessage = driver
					.findElement(By.xpath("//div[@id='report']/span[.='The two files were semantically  identical.']"));
			String actualMessage1 = actualMessage.getText();
			Assert.assertTrue(expectedMessage.contains(actualMessage1));

			sleep();
			
	//Step 4. If the results are equal, Then print the list of characters from each page.
			System.out.println("Expected Result:\n" + expectedresult);
			System.out.println("Actual Result:\n" + actualresult);

			sleep();

		}

		driver.quit();

	}

	
	// Test Case 2: Search and Display details of specified character/planet details
	/*
	 * //Steps Performed - 1. Open URL 
	 * 2. Get input string from user and enter it in search field of test site 
	 * 3. Perform search for entered text and get Results
	 * 4. Get the results from API 
	 * 5. Compare both results using JSON Compare and display the result if it is equal 
	 * 6. Ask the user if another search is to be performed 
	 * 7. Perform another search - Go to step 1 or Quit
	 */
	
	
	@Test(enabled = true)
	public void specifiedCharTest() throws IOException {

		String type;
		
		
		do {
			
	// Step 1: Open test Url
			
			String url = "https://swapi.dev/";
			driver.get(url);

	//Step 2. Get input string from user and enter it in search field of test site
			
			// Click on Search field
			WebElement searchfield = driver.findElement(By.xpath("/html//input[@id='interactive']"));
			searchfield.click();

			// reading the text typed in console to enter it in search field
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			System.out.println(
					"Enter the string for specified character or planet search:(eg:people/?search=luke or planets/?search=IV)");
			String enterText = br.readLine();
			searchfield.sendKeys(enterText);
			sleep();
			
    //Step 3. Perform search for entered text and get Results
			
			WebElement requestclick = driver.findElement(
					By.xpath("/html/body/div[@class='container-fluid']/div[3]//button[@class='btn btn-primary']"));
			requestclick.click();
			sleep();

			WebElement lists = driver.findElement(By.id("interactive_output"));
			String actualresult2 = lists.getText();
			
	//Step 4. Get the results from API
			baseURI = "https://swapi.dev/api/";
			Response response = get(enterText);
			String expectedresult2 = response.getBody().asString();

			sleep();

    //Step 5. Compare both results using JSON Compare and display the result if it is equal 
			
			// comparing the two results
			driver.get("https://jsoncompare.org/");
			WebElement jsoncompare11 = driver.findElement(By.xpath("/html//textarea[@id='textarealeft']"));
			jsoncompare11.sendKeys(expectedresult2);
			WebElement jsoncompare12 = driver.findElement(By.xpath("/html//textarea[@id='textarearight']"));
			jsoncompare12.sendKeys(actualresult2);
			WebElement comparebutton2 = driver.findElement(By.id("compare"));
			comparebutton2.click();

			String expectedMessage2 = "The two files were semantically identical.";
			WebElement actualMessage2 = driver
					.findElement(By.xpath("//div[@id='report']/span[.='The two files were semantically  identical.']"));
			String actualMessage12 = actualMessage2.getText();
			Assert.assertTrue(expectedMessage2.contains(actualMessage12));

			sleep();
			//Displaying the results in console
			System.out.println("Expected Result:\n" + expectedresult2);
			System.out.println("Actual Result:\n" + actualresult2);

	//Step 6. Ask the user if another search is to be performed
			
			BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Do you want to perform another search:Y/N -");
			 type = br2.readLine();
		}
		while(type.equals("Y"));
		
		driver.quit();
	}

	public void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
