package com.umbrella.test;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */


public class AppTest {

    @Test

    public void t1umLogin() {

        WebDriver drv = new PhantomJSDriver();
        UtilityStore.login(drv);
        drv.close();
    }


    //    http://148.251.99.194:8081/admin/import/db/
//    Проверить импорт, статус должен быть SUCCESS

    @Test

    public void t2umDbUpload() {
        WebDriver drv = new PhantomJSDriver();
        UtilityStore.login(drv);
        drv.get("http://148.251.99.194:8081/admin/import/db/");
        UtilityStore.waitForElementsPresence(drv, 1, "id_file", 'i');
        UtilityStore.uploadFile(drv, "file", "/home/paul/Downloads/Geographic50.xlsx");
        UtilityStore.waitForElementsPresence(drv, 1, "import_to_db", 'i');
        drv.findElement(By.id("import_to_db")).click();
        UtilityStore.waitForElementsPresence(drv, 3, "label-success", 'c');

        drv.close();
    }


    //    http://148.251.99.194:8081/ranking/
//    В селекте Sector выбрать Software & Internet, проверить чтобы появилась таблица с данными.
//    В шапке таблицы есть еще один селект - повыбирать там другие варианты и проверить, чтобы данные в таблице менялись.

    @Test

    public  void t3compareTablesSoftware() {
        WebDriver drv = new PhantomJSDriver();
        UtilityStore.login(drv);
        drv.get("http://148.251.99.194:8081/ranking");
        UtilityStore.waitForElementsPresence(drv, 2, "select2-sector_select-container", 'i');
        drv.findElement(By.id("select2-sector_select-container")).click();
        UtilityStore.waitForElementsPresence(drv, 2, "select2-search__field", 'c');
        drv.findElement(By.className("select2-search__field")).sendKeys("sof", Keys.ENTER);


        List<WebElement> results;
        List<WebElement> results2;
        List<String> strResults = new ArrayList<String>();
        List<String> strResults2 = new ArrayList<String>();

        results = drv.findElements(By.className("progress_text"));
        results2 = drv.findElements(By.className("progress_text"));

        for (int i = 0; i < results.size(); i++) {
            System.out.println("Element " + results.get(i).getText());
            strResults.add(results.get(i).getText());

        }

        drv.findElement(By.id("select2-measure_select-container")).click();
        UtilityStore.waitForElementsPresence(drv, 1, "select2-search__field", 'c');
        drv.findElement(By.className("select2-search__field")).click();
        drv.findElement(By.className("select2-search__field")).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        WebDriverWait wait = new WebDriverWait(drv, 1);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader_block")));
        //UtilityStore.waitForElementsAbsence(drv, 1, "loader_block");
        results2 = drv.findElements(By.className("progress_text"));


        for (int i = 0; i < results2.size(); i++) {
            System.out.println("Element " + results2.get(i).getText());
            strResults2.add(results2.get(i).getText());
        }


        Assert.assertNotEquals(strResults.get(0), strResults2.get(0));

        drv.close();

    }

    //    http://148.251.99.194:8081/company/
//    В инпут вбить "appl" в выпадающем списке выбрать Apple
//    Должна появиться таблица с данными.
//    Так же как на предыдущей странице, в шапке таблице повыбирать в селекте другие опции и проверить, что данные меняются.

    @Test

    public void t4compareTablesApple() {

        WebDriver drv = new PhantomJSDriver();
        UtilityStore.login(drv);
      //  drv.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        drv.get("http://148.251.99.194:8081/company/");

        drv.findElement(By.id("company_select")).click();
        drv.findElement(By.id("company_select")).sendKeys("appl", Keys.ARROW_DOWN, Keys.ENTER);
        UtilityStore.waitForElementsPresence(drv, 2, "ui-id-1", 'i');
        drv.findElement(By.className("ui-menu-item")).click();


        List<WebElement> results;
        List<WebElement> results2;
        List<String> strResults = new ArrayList<String>();
        List<String> strResults2 = new ArrayList<String>();

        results = drv.findElements(By.className("data_td"));


        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getText().length() > 0) {
                System.out.println("Element " + results.get(i).getText());
                strResults.add(results.get(i).getText());
            }
        }

        drv.findElement(By.id("select2-value_select-container")).click();
        UtilityStore.waitForElementsPresence(drv, 1, "select2-search__field", 'c');
        drv.findElement(By.className("select2-search__field")).click();
        drv.findElement(By.className("select2-search__field")).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        //  UtilityStore.waitForElementsAbsence(drv, 2, "loader_block");
        results2 = drv.findElements(By.className("data_td"));


        for (int i = 0; i < results2.size(); i++) {
            if (results.get(i).getText().length() > 0) {
                System.out.println("Element " + results2.get(i).getText());
                strResults2.add(results2.get(i).getText());
            }
        }


        Assert.assertNotEquals(strResults.get(0), strResults2.get(0));

        drv.close();
    }
}
