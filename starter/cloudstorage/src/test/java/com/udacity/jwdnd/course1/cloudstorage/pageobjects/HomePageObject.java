package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageObject {

    @FindBy(xpath="//*[@id=\"buttonLogout\"]")
    private WebElement buttonLogout;

    @FindBy(xpath="//*[@id=\"nav-notes-tab\"]")
    private WebElement navNotesTab;

    @FindBy(xpath="//*[@id=\"nav-credentials-tab\"]")
    private WebElement navCredentialsTab;

    @FindBy(xpath="//*[@id=\"buttonAddNewNote\"]")
    private WebElement buttonAddNote;

    @FindBy(xpath="//*[@id=\"userTable\"]/tbody/tr/td[1]/button")
    private WebElement buttonEditNote;

    @FindBy(xpath="//*[@id='noteModal']/div/div/div[3]/button[2]")
    private WebElement buttonSaveNote;

    @FindBy(xpath="//*[@id=\"userTable\"]/tbody/tr/td[1]/a")
    private WebElement buttonDeleteNote;

    @FindBy(xpath="//*[@id=\"note-title\"]")
    private WebElement inputNoteTitle;

    @FindBy(xpath="//*[@id=\"note-description\"]")
    private WebElement inputNoteDescription;

    @FindBy(xpath="//*[@id=\"nav-credentials\"]/button")
    private WebElement buttonAddCredential;

    @FindBy(xpath="//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button")
    private WebElement buttonEditCredential;

    @FindBy(xpath="//*[@id=\"credentialModal\"]/div/div/div[3]/button[2]")
    private WebElement buttonSaveCredential;

    @FindBy(xpath="//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a")
    private WebElement buttonDeleteCredential;

    @FindBy(xpath="//*[@id=\"credential-url\"]")
    private WebElement inputCredentialUrl;

    @FindBy(xpath="//*[@id=\"credential-username\"]")
    private WebElement inputCredentialUsername;

    @FindBy(xpath="//*[@id=\"credential-password\"]")
    private WebElement inputCredentialPassword;



    private WebDriver driver;

    public HomePageObject(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(this.driver, this);
    }

    public void logout() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(buttonLogout)).click();
    }

    public void clickNotesTab() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(navNotesTab)).click();
    }

    public String getNotesTabText() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement notesTabText = wait.until(webDriver -> webDriver.findElement(By.xpath("//*[@id=\"nav-notes-tab\"]")));

        return notesTabText.getText();
    }

    public String getNoNotesMsg() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement noNotesMsg = wait.until(webDriver -> webDriver.findElement(By.xpath("//*[@id=\"no-notes-msg\"]")));

        return noNotesMsg.getAttribute("innerHTML");
    }

    public void addNewNote(String noteTitle, String noteDescription) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(buttonAddNote)).click();
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteTitle)).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteDescription)).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(buttonSaveNote)).click();
    }

    public void editNote(String noteTitle, String noteDescription) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(buttonEditNote)).click();
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteTitle)).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteDescription)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteDescription)).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(buttonSaveNote)).click();
    }

    public void deleteNote() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(buttonDeleteNote)).click();

        //Handle the confirmation
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void clickCredentialsTab() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab)).click();
    }

    public String getCredentialsTabText() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement credentialsTabText = wait.until(webDriver -> webDriver.findElement(By.xpath("//*[@id=\"nav-credentials-tab\"]")));

        return credentialsTabText.getText();
    }

    public String getNoCredentialsMsg() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement noCredentialsMsg = wait.until(webDriver -> webDriver.findElement(By.xpath("//*[@id=\"no-credentials-msg\"]")));

        return noCredentialsMsg.getAttribute("innerHTML");
    }

    public void addNewCredential(String url, String username, String password) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(buttonAddCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(inputCredentialUrl)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(inputCredentialUsername)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(inputCredentialPassword)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(buttonSaveCredential)).click();
    }

    public void editCredential(String url, String username, String password) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(buttonEditCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(inputCredentialUrl)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputCredentialUrl)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(inputCredentialUsername)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputCredentialUsername)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(inputCredentialPassword)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputCredentialPassword)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(buttonSaveCredential)).click();
    }

    public void deleteCredential() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(buttonDeleteCredential)).click();

        //Handle the confirmation
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
}
