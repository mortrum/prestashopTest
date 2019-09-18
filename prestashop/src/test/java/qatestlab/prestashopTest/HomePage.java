package qatestlab.prestashopTest;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger logger = Logger.getLogger(HomePage.class.getName());

    private static String URL = "http://prestashop-automation.qatestlab.com.ua";

    public HomePage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    /**
     * Task 1 Открыть главную страницу сайта
     */
    public void open() {
        driver.get(URL);
        Assert.assertTrue(driver.getTitle().equals("prestashop-automation"));
        System.out.println("Task 1, web page is opened!");
        logger.info("web page is opened!");
    }

    /**
     * Task 2 Выполнить проверку, что цена продуктов в секции "Популярные товары" указана в соответствии с установленной валютой в шапке сайта (USD, EUR, UAH).
     */
    public void checkCurrency() {
        String currency;
        String item1; //only 1 item tested :)

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#_desktop_currency_selector > div > span.expand-more._gray-darker.hidden-sm-down")));
        currency = driver.findElement(By.cssSelector("#_desktop_currency_selector > div > span.expand-more._gray-darker.hidden-sm-down")).getText();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#content > section > div > article:nth-child(1) > div > div.product-description > div > span")));
        item1 = driver.findElement(By.cssSelector("#content > section > div > article:nth-child(1) > div > div.product-description > div > span")).getText();

        Assert.assertTrue(currency.split(" ")[1].equals(item1.split(" ")[1]));
        System.out.println("Task 2, currency checked!");
    }

    /**
     * Task 3 Установить показ цены в USD используя выпадающий список в шапке сайта.
     */
    public void changeCurrencyToUSD() {
        driver.findElement(By.cssSelector("#_desktop_currency_selector > div > a > i")).click();
        logger.info("clicked to #_desktop_currency_selector > div > a > i");

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#_desktop_currency_selector > div > ul > li:nth-child(3) > a")));

        driver.findElement(By.cssSelector("#_desktop_currency_selector > div > ul > li:nth-child(3) > a")).click();
        logger.info("clicked to #_desktop_currency_selector > div > ul > li:nth-child(3) > a");

    }

    /**
     * Task 4 Выполнить поиск в каталоге по слову “dress.”
     * @param s search input
     */
    public void search(String s) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#search_widget > form > input.ui-autocomplete-input")));
        driver.findElement(By.cssSelector("#search_widget > form > input.ui-autocomplete-input")).sendKeys(s);
        logger.info("sendKeys to #search_widget > form > input.ui-autocomplete-input");

        driver.findElement(By.cssSelector("#search_widget > form > button")).click();
        logger.info("clicked to #search_widget > form > button");
    }
}
