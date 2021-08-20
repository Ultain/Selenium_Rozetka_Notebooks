package tests;

import dao.NotebooksDAO;
import entity.Notebooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.CompsAndNotesPage;
import pages.HomePage;
import pages.NotePage;
import pages.NoteParametersPage;
import pages.plp.PlpNotesPage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static setup.ConnectionInfoDB.*;
import static setup.WaitUtil.timeout;

public class RozetkaNotesTest {

    @Test
    public void test001() throws InterruptedException {

        // Инициализируем Вэб-драйвер
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver_92.exe");
        WebDriver driver = new ChromeDriver();

        // Открываем "Розетку"
        driver.get("https://rozetka.com.ua/");

        // Макс окна и неявное ожидание
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Жмякаем по "Компьютеры и ноутбуки"
        WebElement notesAndComps = driver.findElement(new HomePage().linkNotesAndComps);
        notesAndComps.click();

        // Жмякаем по "Ноутбуки"
        WebElement notes = driver.findElement(new CompsAndNotesPage().linkNotes);
        notes.click();

        // Выбираем метод сортировки ноутбуков в dropdown-фильтре
        WebElement notesFilter = driver.findElement(new PlpNotesPage().linkNotesFilter);
        Select selectNotesFilter = new Select(notesFilter);
        selectNotesFilter.selectByVisibleText("По рейтингу");

        List<WebElement> pageList = driver.findElements(new PlpNotesPage().linkPageList);

        int lastPageInList = pageList.size();

        int lastPageNumber = Integer.valueOf(pageList.get(lastPageInList-1).getText());

        // Генерируем рандомный номер страницы исходя из количества наших страниц
        int randomPage = new Random().nextInt(lastPageNumber) + 1;
        System.out.println("Random page: " + randomPage);

        String newURL = driver.getCurrentUrl() + "page=" + randomPage;
        driver.get(newURL);

        // Выбор минимальной и максимальной цен ноутбуков на выбранной странице
        List<WebElement> priceListElement = driver.findElements(new PlpNotesPage().linkPriceListElement);

        // Перевод цен из листа вэб элементов в стринговый лист
        List<String> priceList = new ArrayList<>();
        for (WebElement element : priceListElement) {
            priceList.add(element.getText().replace(" ", ""));
        }

        // Нахождение Максимальной и минимальной цен
        List<Integer> priceListInt = new ArrayList<>();
        for (String element : priceList) {
            priceListInt.add(Integer.valueOf(element));
        }

        int maxPrice = Integer.MIN_VALUE;
        for(int i=0; i<priceListElement.size(); i++){
            if(priceListInt.get(i) > maxPrice){
                maxPrice = priceListInt.get(i);
            }
        }

        int minPrice = Integer.MAX_VALUE;
        for(int i=0; i<priceListElement.size(); i++){
            if(priceListInt.get(i) < minPrice){
                minPrice = priceListInt.get(i);
            }
        }

        System.out.println("Max price: " + maxPrice);

        System.out.println("Min price: " + minPrice);

        // Лист кликабельных ноутбуков на странице
        List<WebElement> notesOnPage = driver.findElements(new PlpNotesPage().linkNotesOnPage);

        // Нажимаем на ноут с максимальной ценой
        for(int i=0; i<priceListElement.size(); i++){
            if(priceListInt.get(i).equals(maxPrice)){
                notesOnPage.get(i).click();
            }
        }

        // Переходим в "Характеристики" ноутбука с максимальной ценой
        WebElement noteProperties = driver.findElement(new NotePage().linkNoteParameters);
        noteProperties.click();

        try(Connection connection = DriverManager.getConnection(DB, USER, PASSWORD);
            Statement statement = connection.createStatement()){

            NotebooksDAO notebooksDAOobj = new NotebooksDAO();

            // Создание таблицы NOTEBOOKS
            notebooksDAOobj.createTable(statement);

            // Добавление характеристик ноутбука №1 в таблицу NOTEBOOKS
            Notebooks notebook1 = new Notebooks(driver.findElement(new NoteParametersPage().linkModelName).getText(),
                    driver.findElement(new NoteParametersPage().linkScreen).getText(),
                    driver.findElement(new NoteParametersPage().linkProcessor).getText(),
                    driver.findElement(new NoteParametersPage().linkRAM).getText(),
                    driver.findElement(new NoteParametersPage().linkStorage).getText(),
                    driver.findElement(new NoteParametersPage().linkVideoCard).getText(),
                    driver.findElement(new NoteParametersPage().linkWeight).getText());

            notebooksDAOobj.addNotebook(statement, notebook1);

            // Печатаем характеристики первого ноута в консоль
            System.out.println(notebooksDAOobj.getNotebookById(statement, 1));

            driver.navigate().back();
            driver.navigate().back();

            // Задаем ожидание для списка 60 ноутбуков
            List<WebElement> notesOnPageWithWait = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(new PlpNotesPage().linkNotesOnPage));

            // Нажимаем на ноут с минимальной ценой
            for(int i=0; i<priceListElement.size(); i++){
                if(priceListInt.get(i).equals(minPrice)){
                    notesOnPageWithWait.get(i).click();
                }
            }

            // Задаем ожидание для элемента "Характеристики" ноута с минимальной ценой
            WebElement notePropertiesWithWait = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(new NotePage().linkNoteParameters));

            // Переходим в "Характеристики" ноутбука с минимальной ценой
            notePropertiesWithWait.click();

            // Добавление характеристик ноутбука №2 в таблицу NOTEBOOKS
            Notebooks notebook2 = new Notebooks(driver.findElement(new NoteParametersPage().linkModelName).getText(),
                    driver.findElement(new NoteParametersPage().linkScreen).getText(),
                    driver.findElement(new NoteParametersPage().linkProcessor).getText(),
                    driver.findElement(new NoteParametersPage().linkRAM).getText(),
                    driver.findElement(new NoteParametersPage().linkStorage).getText(),
                    driver.findElement(new NoteParametersPage().linkVideoCard).getText(),
                    driver.findElement(new NoteParametersPage().linkWeight).getText());

            notebooksDAOobj.addNotebook(statement, notebook2);

            // Печатаем характеристики второго ноута в консоль
            System.out.println(notebooksDAOobj.getNotebookById(statement, 2));

            // Удаление таблицы NOTEBOOKS
            notebooksDAOobj.deleteTable(statement);

        }catch (SQLException ex){
            ex.printStackTrace();
        }

        timeout(5);
        driver.quit();

    }
}
