package qatestlab.prestashopTest;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> listOfDresses;
    private Logger logger = Logger.getLogger(SearchPage.class.getName());

    public SearchPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }


    /**
     * Task 5 Выполнить проверку, что страница "Результаты поиска" содержит надпись "Товаров: x", где x - количество действительно найденных товаров.
     */
    public void checkFoundedCount() {
        String count = driver.findElement(By.cssSelector("#js-product-list-top > div.col-md-6.hidden-sm-down.total-products > p")).getText();
        count = count.split(" |\\.")[1];

        listOfDresses = driver.findElements(By.cssSelector(".product-miniature"));

        Assert.assertEquals(Integer.parseInt(count), listOfDresses.size());
        System.out.println("Task 5, count checked!");
    }

    /**
     * Task 6, Проверить, что цена всех показанных результатов отображается в долларах.
     */
    public void checkCurrencyForSearchedElements() {
        for (WebElement e :
                listOfDresses) {
            String itemCurrency;
            String lines[] = e.getText().split("\n");
            itemCurrency = lines[1].split(" ")[1];

            Assert.assertTrue(itemCurrency.equals("$"));
        }
        System.out.println("Task 6, new currency checked!");
    }

    /**
     * Tast 7, Установить сортировку "от высокой к низкой."
     */
    public void setSortingFromHighToLow() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#js-product-list-top > div:nth-child(2) > div > div > a > i")));
        driver.findElement(By.cssSelector("#js-product-list-top > div:nth-child(2) > div > div > a > i")).click();
        logger.info("clicked to #js-product-list-top > div:nth-child(2) > div > div > a > i");

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#js-product-list-top > div:nth-child(2) > div > div > div > a:nth-child(5)")));
        driver.findElement(By.cssSelector("#js-product-list-top > div:nth-child(2) > div > div > div > a:nth-child(5)")).click();
        logger.info("clicked to #js-product-list-top > div:nth-child(2) > div > div > div > a:nth-child(5)");

    }

    /**
     * Task 8, Проверить, что товары отсортированы по цене, при этом некоторые товары могут быть со скидкой, и при сортировке используется цена без скидки.
     */
    public void checkPrices() {
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        List<WebElement> listOfDressesSorted = driver.findElements(By.cssSelector(".product-miniature.js-product-miniature"));
        List<String> itemPrices = new ArrayList<String>();
        for (WebElement e :
                listOfDressesSorted) {
            String itemPrice;
            String lines[] = e.getText().split("\n");
            itemPrice = lines[1].split(" ")[0];
            itemPrices.add(itemPrice);
        }
        List<String> tmp = new ArrayList<String>(itemPrices);
        Collections.sort(tmp, Collections.<String>reverseOrder());

        Assert.assertTrue(itemPrices.equals(tmp));
        System.out.println("Task 8, sorting checked!");
    }

    /**
     * Task 9, 10
     * 9.  Для товаров со скидкой указана скидка в процентах вместе с ценой до и после скидки.
     *
     * 10. Необходимо проверить, что цена до и после скидки совпадает с указанным размером скидки.
     */
    public void checkDiscount() {
        listOfDresses = driver.findElements(By.cssSelector(".product-miniature"));
        for (WebElement e :
                listOfDresses) {
            float itemPrice;
            float discount;
            float discountedPrice;
            String lines[] = e.getText().split("\n");
            if (lines[2].contains("%")) {
                discount = Float.parseFloat(lines[2].substring(1, lines[2].length() - 1));
                itemPrice = Float.parseFloat(lines[1].substring(0, lines[1].length() - 2).replaceAll(",", "."));
                discountedPrice = Float.parseFloat(lines[3].substring(0, lines[3].length() - 2).replaceAll(",", "."));

                float expectDiscountedPrice = Math.round((itemPrice * (1 - discount / 100)) * 100) / 100.0f;
                Assert.assertEquals(discountedPrice, expectDiscountedPrice, 0.0f);
            }
        }
    }
}
