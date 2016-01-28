package com.umbrella.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by paul on 26.01.16.
 */
public class UtilityStore {
    public static boolean login(WebDriver drv) {

        drv.get("http://148.251.99.194:8083/login-page/");
        drv.findElement(By.id("login_email_input")).sendKeys("admin@admin.com");
        drv.findElement(By.id("login_password_input")).sendKeys("123123");
        drv.findElement(By.xpath(".//*[@id='login_form']/div[2]/div/div/button")).click();

        //waitForElementsAbsence(drv, 5, "loader_block");

        if (checkHttpResponseCode(drv.getCurrentUrl())) {
            System.out.println("LOGIN IS OK!");
            return true;
        } else {
            System.out.println("LOGIN IS FAILED!");
            return false;
        }
    }

    public static void logout(WebDriver drv) {
        drv.findElement(By.className("account-dropdown")).click();
        waitForElementsPresence(drv, 2, "dropdown-menu-right", 'c');
        drv.findElement(By.linkText("Выйти")).click();
    }

    public static boolean checkHttpResponseCode(String url) {
        com.jayway.restassured.response.Response response =
                given().get(url)
                        .then().extract().response();

        int returnedResponse = response.getStatusCode();

        if (returnedResponse >= 200 && returnedResponse < 400) {
            System.out.println("Test on \"" + url + "\" is OK! And the code is: " + returnedResponse);
            return true;
        } else {
            System.out.println("Test on \"" + url + "\" is FAILED! And the code is: " + returnedResponse);
            return false;
        }
    }


    public static boolean gotToPage(WebDriver drv, String URLtitle, String URL, String errorMsg) throws InterruptedException {
        drv.get(URL);
        if (drv.getTitle().equals(URLtitle)) {
            System.out.println(drv.getTitle() + " is ok!");
            return true;
        } else {
            System.out.println("There is a test here: " + drv.getTitle() + ", and it is failed!");
            return false;
        }

    }

    public static String addDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //get current date time with Date()
        Date date = new Date();
        String dateAndTimeAttachment = dateFormat.format(date);
        return dateAndTimeAttachment;
    }


    public static void uploadFile(WebDriver inputDriver, String nameOfFileForm, String pathToFile) {

        //File input for Lubuntu /home/paul/Desktop/2015-12-30-150350_1920x1080_scrot.pdf
        //File input for Windows C:\Users\NEXUS\Pictures\wow.pdf

        inputDriver.findElement(By.name(nameOfFileForm)).sendKeys(pathToFile);
    }


    public static void waitForElementsAbsence(WebDriver drv, int howLong, String classOfelementToWaitFor) {
        WebDriverWait wait = new WebDriverWait(drv, howLong);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(classOfelementToWaitFor)));
        System.out.println("\n " + classOfelementToWaitFor + " on the page " + drv.getCurrentUrl() + " has begun his work!");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(classOfelementToWaitFor)));
        System.out.println("\n " + classOfelementToWaitFor + " on the page " + drv.getCurrentUrl() + " has ended his work!");
    }

    public static void waitForElementsPresence(WebDriver drv, int howLong, String elementToWaitFor, char typeOfIdentifier) {

        WebDriverWait wait = new WebDriverWait(drv, howLong);

        switch (typeOfIdentifier) {
            case 'i':
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementToWaitFor)));
                break;
            case 'c':
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className(elementToWaitFor)));
                break;
            default:
                throw new IllegalArgumentException("Invalid identifier: " + typeOfIdentifier);
        }

        System.out.println("\n" + elementToWaitFor + " on the page " + drv.getCurrentUrl() + " is present!");

    }
}
