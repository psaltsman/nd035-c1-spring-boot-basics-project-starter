package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageobjects.HomePageObject;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.LoginPageObject;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.SignupPageObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testLogin() {

		String testLoginFirstName = "testLoginFirstName";
		String testLoginLastName = "testLoginLastName";
		String testLoginUsername = "testLoginUserName";
		String testLoginPassword = "testLoginPassword";

		//Test signing up as a new user
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		SignupPageObject signUpPage = new SignupPageObject(driver);
		signUpPage.signup(testLoginFirstName, testLoginLastName, testLoginUsername, testLoginPassword);
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signUpPage.getSuccessMsg());

		//Test signing up again with the same username
		signUpPage.signup(testLoginFirstName, testLoginLastName, testLoginUsername, testLoginPassword);
		Assertions.assertEquals("That username is not available", signUpPage.getErrorMsg());

		//Test logging in with the new username and password
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		LoginPageObject loginPage = new LoginPageObject(driver);
		loginPage.login(testLoginUsername, testLoginPassword);
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		//Test logging out
		HomePageObject homePage = new HomePageObject(driver);
		homePage.logout();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		//Try logging in with a bad username
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login("badUserName", testLoginPassword);
		Assertions.assertEquals("Invalid username or password", loginPage.getErrorMsg());

		//Try logging in with bad password
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(testLoginUsername, "badPassword");
		Assertions.assertEquals("Invalid username or password", loginPage.getErrorMsg());
	}

	@Test
	public void testNotes() {

		String testNotesFirstName = "testNotesFirstName";
		String testNotesLastName = "testNotesLastName";
		String testNotesUsername = "testNotesUserName";
		String testNotesPassword = "testNotesPassword";

		//Signup
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		SignupPageObject signUpPage = new SignupPageObject(driver);
		signUpPage.signup(testNotesFirstName, testNotesLastName, testNotesUsername, testNotesPassword);
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signUpPage.getSuccessMsg());

		//Login
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		LoginPageObject loginPage = new LoginPageObject(driver);
		loginPage.login(testNotesUsername, testNotesPassword);
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		try {
			//Test notes
			HomePageObject homePage = new HomePageObject(driver);
			Thread.sleep(5000);
			homePage.clickNotesTab();
			Thread.sleep(5000);
			Assertions.assertEquals("You have not saved any notes", homePage.getNoNotesMsg());

		} catch (Exception ex) {

		}
	}
}
