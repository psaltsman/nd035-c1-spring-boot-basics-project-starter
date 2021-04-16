package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageObject {

    @FindBy(id="buttonLogout")
    private WebElement buttonLogout;

    @FindBy(id="nav-notes-tab")
    private WebElement navNotesTab;

    private WebDriver driver;

    public HomePageObject(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(this.driver, this);
    }

    public void clickNotesTab() {
        navNotesTab.click();
    }

    public void logout() {

        buttonLogout.click();
    }

    public String getNoNotesMsg() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement noNotesMsg = wait.until(webDriver -> webDriver.findElement(By.id("no-notes-msg")));

        return noNotesMsg.getText();
    }
}
