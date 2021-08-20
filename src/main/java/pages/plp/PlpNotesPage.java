package pages.plp;

import org.openqa.selenium.By;

public class PlpNotesPage {

    public By linkNotesFilter = new By.ByXPath("//select[contains(@class, 'select-css ng-untouched')]");
    public By linkPageList = new By.ByXPath("//ul[@class='pagination__list']/li/a");
    public By linkPriceListElement = new By.ByXPath("//span[@class='goods-tile__price-value']");
    public By linkNotesOnPage = new By.ByXPath("//div[@class='goods-tile__inner']/a[@class='goods-tile__picture ng-star-inserted']");

}
