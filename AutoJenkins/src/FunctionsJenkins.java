import core.ErrorManager.ErrorManager;
import core.TestDriver.TestDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by pvalls on 10/04/2017.
 *///
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import core.CommonActions.DataGenerator;
import core.ErrorManager.ErrorManager;
import core.FileGestor.ReportFile;
import core.TestDriver.TestDriver;
import core.recursiveData.recursiveXPaths;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * For use this need a webDriver
 */
public class FunctionsJenkins {
    Data data;


    public FunctionsJenkins(Data data) {
        this.data=data;
    }

    public  boolean simpleClick(TestDriver driver, String[] path, String where) {
        try {
            driver.getDriver().findElement(By.xpath(path[1])).click();
            driver.getReport().addContent(path[0] + " clicked " + where);
            return true;
        } catch (Exception var5) {
            String ecode = "--ERROR: simpleClick(): Unable to click the element " + path[0] + " with xpath: " + path[1] + where + ".";
            var5.printStackTrace();
            ErrorManager.process(driver, ecode);
            return false;
        }
    }

    public  boolean insertInput(TestDriver driver, String[] path, String data_name, String value, String where) {
        driver.getReport().addContent("Insert Input:", "h5", "");
        WebDriverWait wdw = new WebDriverWait(driver.getDriver(), 30L, 1000L);

        try {
            wdw.until(ExpectedConditions.elementToBeClickable(By.xpath(path[1])));
            if(!simpleClick(driver, path, where)) {
                return false;
            }

            break_time(driver, 30, 500L);
            driver.getDriver().findElement(By.xpath(path[1])).clear();
            break_time(driver, 30, 500L);
            driver.getDriver().findElement(By.xpath(path[1])).sendKeys(new CharSequence[]{value});
            break_time(driver, 30, 500L);
           data.getData().put(data_name, driver.getDriver().findElement(By.xpath(path[1])).getAttribute("value"));
            if(!((String) data.getData().get(data_name)).equals("") &&  data.getData().get(data_name) != null) {
                if(data_name.contains("password")) {
                    driver.getReport().addContent(data_name + " (******) inserted" + where + ".");
                } else {
                    driver.getReport().addContent(data_name + " (" + value + ") inserted" + where + ".");
                }
            }
        } catch (Exception var8) {
            String ecode = "--ERROR: insertInput(): Unable to insert " + data_name + " with value: \"" + value + "\" into " + path[0] + " (xpath " + path[1] + ") on " + where + ".";
            var8.printStackTrace();
            ErrorManager.process(driver, ecode);
            return false;
        }

        driver.getReport().addContent("", "br", "");
        return true;
    }

    public  boolean zoomOut(TestDriver driver, int times) {
        try {
            WebElement e = driver.getDriver().findElement(By.tagName("html"));

            for(int var5 = 0; var5 < times + 1; ++var5) {
                e.sendKeys(new CharSequence[]{Keys.chord(new CharSequence[]{Keys.CONTROL, Keys.SUBTRACT})});
            }

            driver.getReport().addContent("Screen zoomed out " + times + " times.");
            return true;
        } catch (Exception var4) {
            String ecode = "--ERROR:zoomOut(): Not possible to zoom out.";
            var4.printStackTrace();
            ErrorManager.process(driver, ecode);
            return false;
        }
    }

    public  boolean displayed(TestDriver driver, String xpath) {
        try {
            return driver.getDriver().findElement(By.xpath(xpath)).isDisplayed();
        } catch (NoSuchElementException var3) {
            return false;
        } catch (StaleElementReferenceException var4) {
            return false;
        }
    }

    public  boolean checkboxValue(TestDriver driver, String Xpath, String dataname, boolean active, String where) {
        try {
            boolean e;
            for(e = driver.getDriver().findElement(By.xpath(Xpath)).isSelected(); e != active; e = driver.getDriver().findElement(By.xpath(Xpath)).isSelected()) {
                simpleClick(driver, new String[]{"xpath_checkbox", Xpath}, where);
                break_time(driver, 5, 1000L);
            }

             data.getData().put(dataname, String.valueOf(e));
            System.out.println("The checkbox is equals to " + e + " on Xpath " + Xpath);
            return true;
        } catch (Exception var7) {
            String ecode = "--ERROR: error to give value in the checkbox in " + where;
            var7.printStackTrace();
            ErrorManager.process(driver, ecode);
            return false;
        }
    }

    public  boolean break_time(TestDriver driver, int seconds, long miliseconds) {
        WebDriverWait wdw = new WebDriverWait(driver.getDriver(), (long)seconds, miliseconds);

        try {
            if(displayed(driver, "//*[contains(@id, \'si7\')]/img")) {
                long startworking = System.currentTimeMillis();
                System.out.println("working");
                wdw.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@id, \'si7\')]/img")));
                long finishworking = System.currentTimeMillis();
                System.out.println("working finished: Time " + (finishworking - startworking) / 1000L + "s");
            }

            return true;
        } catch (Exception var11) {
            String ecode = "--ERROR: Timed out after " + seconds + " seconds waiting and the system continue in working. ";
            var11.printStackTrace();
            ErrorManager.process(driver, ecode);
            return false;
        }
    }


}
