package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPageObject {

    @FindBy(id="inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id="inputLastName")
    private WebElement inputLastName;

    @FindBy(id="inputUsername")
    private WebElement inputUserName;

    @FindBy(id="inputPassword")
    private WebElement inputPassword;

    @FindBy(id="buttonSignup")
    private WebElement buttonSignup;

    private WebDriver driver;

    public SignupPageObject(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void signup(String firstName, String lastName, String userName, String password) {

        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUserName.sendKeys(userName);
        inputPassword.sendKeys(password);
        buttonSignup.click();
    }

    public String getSuccessMsg() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement successMsg = wait.until(webDriver -> webDriver.findElement(By.id("success-msg")));

        return successMsg.getText();
    }

    public String getErrorMsg() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement errorMsg = wait.until(webDriver -> webDriver.findElement(By.id("error-msg")));

        return errorMsg.getText();
    }
}
