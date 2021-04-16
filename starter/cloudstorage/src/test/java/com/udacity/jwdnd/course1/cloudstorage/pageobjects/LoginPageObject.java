package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageObject {

    @FindBy(id="inputUsername")
    private WebElement inputUserName;

    @FindBy(id="inputPassword")
    private WebElement inputPassword;

    @FindBy(id="buttonLogin")
    private WebElement buttonLogin;

    private WebDriver driver;

    public LoginPageObject(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(this.driver, this);
    }

    public void login(String userName, String password) {

        inputUserName.sendKeys(userName);
        inputPassword.sendKeys(password);
        buttonLogin.click();
    }

    public String getLogoutMsg() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement successMsg = wait.until(webDriver -> webDriver.findElement(By.id("logout-msg")));

        return successMsg.getText();
    }

    public String getErrorMsg() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement errorMsg = wait.until(webDriver -> webDriver.findElement(By.id("error-msg")));

        return errorMsg.getText();
    }
}
