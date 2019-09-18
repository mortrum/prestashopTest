package qatestlab.prestashopTest;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import qatestlab.WebDriverSettings;

public class MainTest extends WebDriverSettings {
    @Test
    public void mainTest() {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        SearchPage searchPage = PageFactory.initElements(driver, SearchPage.class);

        homePage.open();

        homePage.checkCurrency();

        homePage.changeCurrencyToUSD();

        homePage.search("dress");

        searchPage.checkFoundedCount();

        searchPage.checkCurrencyForSearchedElements();

        searchPage.setSortingFromHighToLow();

        searchPage.checkPrices();

        searchPage.checkDiscount();

    }
}















