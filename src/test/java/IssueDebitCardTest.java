import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;

import static org.openqa.selenium.By.*;

public class IssueDebitCardTest {
    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
//        driver = new ChromeDriver();

    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
//Позитивные тесты

    @Test
    void shouldSuccessTest() {
        driver.get("http://localhost:9999");
        List<WebElement> webElement = driver.findElements(By.className("input__control"));
        driver.findElement(cssSelector("[type='text']")).sendKeys("Андрей Щепкин");
        webElement.get(1).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(className("paragraph")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedText, actualText.strip());

    }

    @Test
    void shouldSuccessTestWithHyphen() {
        driver.get("http://localhost:9999");
        List<WebElement> webElement = driver.findElements(By.className("input__control"));
        driver.findElement(cssSelector("[type='text']")).sendKeys("Андрей Щепкин-Иванов");
        webElement.get(1).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(className("paragraph")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    //Негативные тесты.


    //Тестирования поля "ФИО"


    @Test
    void englishName() {
        driver.get("http://localhost:9999");
        List<WebElement> webElement = driver.findElements(By.className("input__control"));
        driver.findElement(cssSelector("[type='text']")).sendKeys("Andrey Ivanov");
        webElement.get(1).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(className("input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void emptyFieldName() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[type='tel']")).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(className("input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void hyphenInFieldName() {
        //Данный тест не должен проходить, но он проходит!
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[type='text']")).sendKeys("-");
        driver.findElement(cssSelector("[type='tel']")).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(className("paragraph")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void spaceInFieldName() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[type='text']")).sendKeys(" ");
        driver.findElement(cssSelector("[type='tel']")).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(className("input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void numbersInFieldName() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[type='text']")).sendKeys("1234567");
        driver.findElement(cssSelector("[type='tel']")).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(className("input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }


    //Тестирования в поля "номер телефона"


    @Test
    void emptyFieldNumber() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[type='text']")).sendKeys("Андрей Щепкин");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        List<WebElement> webElement = driver.findElements(By.className("input__sub"));
        String actualText = webElement.get(1).getText();
        String expectedText = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void fieldTelOnlyPlus() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[type='text']")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[type='tel']")).sendKeys("+");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        List<WebElement> webElement = driver.findElements(By.className("input__sub"));
        String actualText = webElement.get(1).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void fieldTelOneSymbol() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[type='text']")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[type='tel']")).sendKeys("7");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        List<WebElement> webElement = driver.findElements(By.className("input__sub"));
        String actualText = webElement.get(1).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void fieldTelThirteenSymbols() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[type='text']")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[type='tel']")).sendKeys("+790012345678");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        List<WebElement> webElement = driver.findElements(By.className("input__sub"));
        String actualText = webElement.get(1).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void fieldTelWithoutPlus() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[type='text']")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[type='tel']")).sendKeys("89001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        List<WebElement> webElement = driver.findElements(By.className("input__sub"));
        String actualText = webElement.get(1).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void lettersInFieldTel() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[type='text']")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[type='tel']")).sendKeys("Андрей Щепкин");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        List<WebElement> webElement = driver.findElements(By.className("input__sub"));
        String actualText = webElement.get(1).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

}

