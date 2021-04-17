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

    @FindBy(xpath="//*[@id=\"note-title\"]")
    private WebElement inputNoteTitle;

    @FindBy(xpath="//*[@id=\"note-description\"]")
    private WebElement inputNoteDescription;

    @FindBy(xpath="//*[@id=\"buttonAddNewNote\"]")
    private WebElement buttonAddNewNote;

    @FindBy(xpath="//*[@id=\"userTable\"]/tbody/tr/td[1]/button")
    private WebElement buttonNoteEdit;

    @FindBy(xpath="//*[@id=\"userTable\"]/tbody/tr/td[1]/a")
    private WebElement buttonNoteDelete;

    @FindBy(xpath="//*[@id='noteModal']/div/div/div[3]/button[2]")
    private WebElement buttonSaveChanges;

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

    public String getNoNotesTabText() {

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

        wait.until(ExpectedConditions.elementToBeClickable(buttonAddNewNote)).click();
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteTitle)).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteDescription)).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(buttonSaveChanges)).click();
    }

    public void editNote(String noteTitle, String noteDescription) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(buttonNoteEdit)).click();
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteTitle)).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteDescription)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputNoteDescription)).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(buttonSaveChanges)).click();
    }

    public void deleteNote() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(buttonNoteDelete)).click();

        //Handle the confirmation
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
}
