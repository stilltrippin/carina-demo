/*
 * Copyright 2013-2021 QAPROSOFT (http://qaprosoft.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qaprosoft.carina.demo;

import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.qaprosoft.carina.core.foundation.utils.ownership.MethodOwner;
import com.qaprosoft.carina.core.foundation.utils.tag.Priority;
import com.qaprosoft.carina.core.foundation.utils.tag.TestPriority;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.demo.gui.components.FooterMenu;
import com.qaprosoft.carina.demo.gui.components.HeaderMenu;
import com.qaprosoft.carina.demo.gui.components.LoginWindow;
import com.qaprosoft.carina.demo.gui.components.NewsItem;
import com.qaprosoft.carina.demo.gui.components.compare.ModelSpecs;
import com.qaprosoft.carina.demo.gui.components.compare.ModelSpecs.SpecType;
import com.qaprosoft.carina.demo.gui.pages.*;
import com.zebrunner.agent.core.annotation.TestLabel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

/**
 * This sample shows how create Web test.
 *
 * @author qpsdemo
 */
public class CustomWebTest implements IAbstractTest {

    @Test()
    @MethodOwner(owner = "qpsdemo")
    public void testHeaderElements() {
        // Open GSM Arena home page and verify page is opened
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");

        HeaderMenu headerMenu = homePage.getHeaderMenu();
        Assert.assertTrue(headerMenu.isUIObjectPresent(10), "Header menu wasn't found!");
        ExtendedWebElement menuButton = headerMenu.getMenuButton();
        Assert.assertTrue(menuButton.isElementPresent(),"Menu Button wasn't found");
        ExtendedWebElement logo = headerMenu.getLogo();
        Assert.assertTrue(logo.isElementPresent(), "Logo wasn't found");
        List<ExtendedWebElement> headerLinks = headerMenu.getHeaderLinks();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(headerLinks.get(0).isElementPresent(), "Tip Us Link wasn't found");
        softAssert.assertTrue(headerLinks.get(1).isElementPresent(), "Youtube Link wasn't found");
        softAssert.assertTrue(headerLinks.get(2).isElementPresent(), "Instagram Link wasn't found");
        softAssert.assertTrue(headerLinks.get(3).isElementPresent(), "RSS Link wasn't found");
        softAssert.assertTrue(headerLinks.get(4).isElementPresent(), "ArenaEV Link wasn't found");
        softAssert.assertTrue(headerLinks.get(5).isElementPresent(), "Merch Link wasn't found");
        softAssert.assertTrue(headerLinks.get(6).isElementPresent(), "Login Link wasn't found");
        softAssert.assertTrue(headerLinks.get(7).isElementPresent(), "SignUp Link wasn't found");
        softAssert.assertAll();

        menuButton.click();

        List<ExtendedWebElement> menuItems = headerMenu.getMenuItems();
        SoftAssert softAssert1 = new SoftAssert();
        softAssert1.assertTrue(menuItems.get(0).isElementPresent(),"Home wasn't found");
        softAssert1.assertTrue(menuItems.get(1).isElementPresent(),"News wasn't found");
        softAssert1.assertTrue(menuItems.get(2).isElementPresent(),"Reviews wasn't found");
        softAssert1.assertTrue(menuItems.get(3).isElementPresent(),"Videos wasn't found");
        softAssert1.assertTrue(menuItems.get(4).isElementPresent(),"Featured wasn't found");
        softAssert1.assertTrue(menuItems.get(5).isElementPresent(),"Phone Finder wasn't found");
        softAssert1.assertTrue(menuItems.get(6).isElementPresent(),"Deals wasn't found");
        softAssert1.assertTrue(menuItems.get(7).isElementPresent(),"Merch wasn't found");
        softAssert1.assertTrue(menuItems.get(8).isElementPresent(),"Coverage wasn't found");
        softAssert1.assertTrue(menuItems.get(9).isElementPresent(),"Contact wasn't found");
        softAssert1.assertAll();
    }

    @Test()
    @MethodOwner(owner = "qpsdemo")
    public void testSuccessLogin() {
        // Open GSM Arena home page and verify page is opened
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");

        String email = "s9rowa@mail.ru";
        String password = "changeme";
        HeaderMenu headerMenu = homePage.getHeaderMenu();
        Assert.assertTrue(headerMenu.isUIObjectPresent(10), "Header menu wasn't found!");

        ExtendedWebElement loginLink = headerMenu.getLoginLink();
        loginLink.click();

        LoginWindow loginWindow = homePage.getLoginWindow();
        ExtendedWebElement emailInput = loginWindow.getEmailInput();
        ExtendedWebElement passwordInput = loginWindow.getPasswordInput();
        emailInput.type(email);
        passwordInput.type(password);
        ExtendedWebElement loginButton = loginWindow.getLoginButton();
        loginButton.click();

        headerMenu = homePage.getHeaderMenu();
        Assert.assertTrue(headerMenu.getLogoutLink().isElementPresent(), "Login Failed");
    }

    @Test()
    @MethodOwner(owner = "qpsdemo")
    public void testWrongEmail() {
        // Open GSM Arena home page and verify page is opened
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");

        String email = RandomStringUtils.randomAlphabetic(10) + "@" + RandomStringUtils.randomAlphabetic(5);
        String password = "changeme";
        HeaderMenu headerMenu = homePage.getHeaderMenu();
        Assert.assertTrue(headerMenu.isUIObjectPresent(10), "Header menu wasn't found!");

        ExtendedWebElement loginLink = headerMenu.getLoginLink();
        loginLink.click();

        LoginWindow loginWindow = homePage.getLoginWindow();
        ExtendedWebElement emailInput = loginWindow.getEmailInput();
        ExtendedWebElement passwordInput = loginWindow.getPasswordInput();
        emailInput.type(email);
        passwordInput.type(password);
        ExtendedWebElement loginButton = loginWindow.getLoginButton();
        loginButton.click();

        String GSM_ARENA_LOGIN_FAILED_EMAIL = "Reason: User record not found.";
        String result = getDriver().findElement(By.xpath("//div[@class='normal-text res-error']/p")).getText();
        Assert.assertTrue(GSM_ARENA_LOGIN_FAILED_EMAIL.equals(result),"Username check failed");
    }

    @Test()
    @MethodOwner(owner = "qpsdemo")
    public void testWrongPassword() {
        // Open GSM Arena home page and verify page is opened
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");

        String email = "s9rowa@mail.ru";
        String password = RandomStringUtils.randomAlphanumeric(10);
        HeaderMenu headerMenu = homePage.getHeaderMenu();
        Assert.assertTrue(headerMenu.isUIObjectPresent(10), "Header menu wasn't found!");

        ExtendedWebElement loginLink = headerMenu.getLoginLink();
        loginLink.click();

        LoginWindow loginWindow = homePage.getLoginWindow();
        ExtendedWebElement emailInput = loginWindow.getEmailInput();
        ExtendedWebElement passwordInput = loginWindow.getPasswordInput();
        emailInput.type(email);
        passwordInput.type(password);
        ExtendedWebElement loginButton = loginWindow.getLoginButton();
        loginButton.click();

        String GSM_ARENA_LOGIN_FAILED_PASSWORD = "Reason: Wrong password.";
        String result = getDriver().findElement(By.xpath("//div[@class='normal-text res-error']/p")).getText();
        Assert.assertTrue(GSM_ARENA_LOGIN_FAILED_PASSWORD.equals(result),"Password check failed");
    }
}
