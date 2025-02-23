package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.HomePageObject;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.LoginPageObject;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.SignupPageObject;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	private Integer userId = null;
	private String userName = "userName";
	private String userSalt = null;
	private String userFirstName = "userFirstName";
	private String userLastName = "userLastName";
	private String userPassword = "userPassword";

	private String noteTitle = "noteTitle";
	private String noteDescription = "noteDescription";

	private String credentialUrl = "https://credentialUrl";
	private String credentialUsername = "credentialUsername";
	private String credentialPassword = "credentalPassword";

	private String newCredentialUrl = "https://newcredentialUrl";
	private String newCredentialUsername = "newCredentialUsername";
	private String newCredentialPassword = "newCredentalPassword";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@Autowired
	private UserService userService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private CredentialService credentialService;

	@Autowired
	EncryptionService encryptionService;

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

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		signUpPage = new SignupPageObject(driver);
		signUpPage.signup(testLoginFirstName, testLoginLastName, testLoginUsername, testLoginPassword);

		signUpPage.signup(testLoginFirstName, testLoginLastName, testLoginUsername, testLoginPassword);
		Assertions.assertEquals("That username is not available", signUpPage.getErrorMsg());
	}

	@Test
	public void testNotes() {

		try {
			//Create user
			User user = new User(userId, userName, userSalt, userPassword, userFirstName, userLastName);
			Assertions.assertEquals(1, userService.createNewUser(user));

			user = userService.getUser(userName);
			Assertions.assertNotNull(user);
			Assertions.assertEquals("userName", user.getUsername());

			driver.get("http://localhost:" + this.port + "/login");
			Assertions.assertEquals("Login", driver.getTitle());
			LoginPageObject loginPage = new LoginPageObject(driver);
			loginPage.login(user.getUsername(), userPassword);  //Have to use decrypted value here

			driver.get("http://localhost:" + this.port + "/home");
			Assertions.assertEquals("Home", driver.getTitle());
			HomePageObject homePage = new HomePageObject(driver);
			homePage.clickNotesTab();

			Assertions.assertEquals("Notes (0)", homePage.getNotesTabText());
			Assertions.assertEquals("You have not saved any notes", homePage.getNoNotesMsg());

			homePage.addNewNote(noteTitle, noteDescription);

			//Verify that the note was created.  There should only be a single note returned.
			List<Note> notes = noteService.getAllNotes(user);
			Assertions.assertEquals(1, notes.size());

			//Verify that the note title and description match
			Note note = notes.get(0);
			Assertions.assertEquals(noteTitle, note.getNoteTitle());
			Assertions.assertEquals(noteDescription, note.getNoteDescription());

			driver.get("http://localhost:" + this.port + "/home");
			Assertions.assertEquals("Home", driver.getTitle());
			homePage = new HomePageObject(driver);
			homePage.clickNotesTab();
			Assertions.assertEquals("Notes (1)", homePage.getNotesTabText());

			//Update the note by switching the title and description
			homePage.editNote(noteDescription, noteTitle);

			notes = noteService.getAllNotes(user);
			Assertions.assertNotNull(notes);
			Assertions.assertEquals(1, notes.size());

			note = notes.get(0);

			//Verify that the title and description have been updated
			Assertions.assertEquals(noteDescription, note.getNoteTitle());
			Assertions.assertEquals(noteTitle, note.getNoteDescription());

			driver.get("http://localhost:" + this.port + "/home");
			Assertions.assertEquals("Home", driver.getTitle());
			homePage = new HomePageObject(driver);
			homePage.clickNotesTab();
			Assertions.assertEquals("Notes (1)", homePage.getNotesTabText());

			//Delete the note
			homePage.deleteNote();

			Thread.sleep(2000);

			notes = noteService.getAllNotes(user);
			Assertions.assertNotNull(notes);
			Assertions.assertEquals(0, notes.size());

			driver.get("http://localhost:" + this.port + "/home");
			Assertions.assertEquals("Home", driver.getTitle());
			homePage = new HomePageObject(driver);
			homePage.clickNotesTab();

			Assertions.assertEquals("Notes (0)", homePage.getNotesTabText());
			Assertions.assertEquals("You have not saved any notes", homePage.getNoNotesMsg());

		} catch (Exception ex) {

			System.out.println(ex.getMessage());
		}
	}

	@Test
	public void testCredentials() {

		try {
			//Create user
			User user = new User(userId, userName, userSalt, userPassword, userFirstName, userLastName);
			Assertions.assertEquals(1, userService.createNewUser(user));

			user = userService.getUser(userName);
			Assertions.assertNotNull(user);
			Assertions.assertEquals("userName", user.getUsername());

			driver.get("http://localhost:" + this.port + "/login");
			Assertions.assertEquals("Login", driver.getTitle());
			LoginPageObject loginPage = new LoginPageObject(driver);
			loginPage.login(user.getUsername(), userPassword);  //Have to use decrypted value here

			driver.get("http://localhost:" + this.port + "/home");
			Assertions.assertEquals("Home", driver.getTitle());
			HomePageObject homePage = new HomePageObject(driver);
			homePage.clickCredentialsTab();

			Assertions.assertEquals("Credentials (0)", homePage.getCredentialsTabText());
			Assertions.assertEquals("You have not saved any credentials", homePage.getNoCredentialsMsg());

			homePage.addNewCredential(credentialUrl, credentialUsername, credentialPassword);

			//Verify that the credential was created. There should only be a single note returned.
			List<Credential> credentials = credentialService.getAllCredentials(user);
			Assertions.assertEquals(1, credentials.size());

			Credential credential = credentials.get(0);
			Assertions.assertEquals(credentialUrl, credential.getUrl());
			Assertions.assertEquals(credentialUsername, credential.getUsername());
			Assertions.assertNotEquals(credentialPassword, credential.getPassword());
			Assertions.assertEquals(credentialPassword, encryptionService.decryptValue(credential.getPassword(), credential.getKey()));

			driver.get("http://localhost:" + this.port + "/home");
			Assertions.assertEquals("Home", driver.getTitle());
			homePage = new HomePageObject(driver);
			homePage.clickCredentialsTab();

			Assertions.assertEquals("Credentials (1)", homePage.getCredentialsTabText());

			homePage.editCredential(newCredentialUrl, newCredentialUsername, newCredentialPassword);

			credentials = credentialService.getAllCredentials(user);
			Assertions.assertEquals(1, credentials.size());

			credential = credentials.get(0);
			Assertions.assertEquals(newCredentialUrl, credential.getUrl());
			Assertions.assertEquals(newCredentialUsername, credential.getUsername());
			Assertions.assertNotEquals(newCredentialPassword, credential.getPassword());
			Assertions.assertEquals(newCredentialPassword, encryptionService.decryptValue(credential.getPassword(), credential.getKey()));

			driver.get("http://localhost:" + this.port + "/home");
			Assertions.assertEquals("Home", driver.getTitle());
			homePage = new HomePageObject(driver);
			homePage.clickCredentialsTab();

			Assertions.assertEquals("Credentials (1)", homePage.getCredentialsTabText());

			//Delete the credential
			homePage.deleteCredential();
			Thread.sleep(2000);

			credentials = credentialService.getAllCredentials(user);
			Assertions.assertEquals(0, credentials.size());

			driver.get("http://localhost:" + this.port + "/home");
			Assertions.assertEquals("Home", driver.getTitle());
			homePage = new HomePageObject(driver);
			homePage.clickCredentialsTab();

			Assertions.assertEquals("Credentials (0)", homePage.getCredentialsTabText());
			Assertions.assertEquals("You have not saved any credentials", homePage.getNoCredentialsMsg());

		} catch (Exception ex) {

			System.out.println(ex.getMessage());
		}
	}
}
