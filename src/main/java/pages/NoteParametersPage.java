package pages;

import org.openqa.selenium.By;

public class NoteParametersPage {

    public By linkModelName = new By.ByXPath("//span[@class='product-tabs__heading_color_gray']");
    public By linkScreen = new By.ByXPath("//span[text()='Диагональ экрана']/ancestor::div[@class='characteristics-full__item ng-star-inserted']/dd/ul/li/a[@class='ng-star-inserted']");
    public By linkProcessor = new By.ByXPath("//span[text()='Процессор']/ancestor::div[@class='characteristics-full__item ng-star-inserted']/dd/ul/li/a[@class='ng-star-inserted']");
    public By linkRAM = new By.ByXPath("//span[text()='Объем оперативной памяти']/ancestor::div[@class='characteristics-full__item ng-star-inserted']/dd/ul/li/a[@class='ng-star-inserted']");
    public By linkStorage = new By.ByXPath("//span[text()='Объём накопителя']/ancestor::div[@class='characteristics-full__item ng-star-inserted']/dd/ul/li/a[@class='ng-star-inserted']");
    public By linkVideoCard = new By.ByXPath("//span[text()='Видеокарта']/ancestor::div[@class='characteristics-full__item ng-star-inserted']/dd/ul/li/span[@class='ng-star-inserted']");
    public By linkWeight = new By.ByXPath("//span[text()='Вес']/ancestor::div[@class='characteristics-full__item ng-star-inserted']/dd/ul/li/span[@class='ng-star-inserted']");


}
