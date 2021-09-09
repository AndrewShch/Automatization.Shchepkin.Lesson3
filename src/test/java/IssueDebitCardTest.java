import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.openqa.selenium.By.*;

public class IssueDebitCardTest {
    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");


    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSuccessTest() {


        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='order-success']")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedText, actualText.strip());

    }

    @Test
    void shouldSuccessTestWithHyphen() {


        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("Андрей Щепкин-Иванов");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='order-success']")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }



    //Тестирования поля "ФИО"


    @Test
    void englishName() {

        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("Andrey Ivanov");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void emptyFieldName() {

        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void hyphenInFieldName() {

        //Данный тест должен проходить. При вводе дефиса в поле ФИО, должна появляться ошибка.

        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("-");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void spaceInFieldName() {

        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys(" ");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void numbersInFieldName() {

        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("1234567");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("+79001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }


    //Тестирования поля "номер телефона"


    @Test
    void emptyFieldNumber() {

        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("Андрей Щепкин");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void fieldTelOnlyPlus() {

        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("+");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void fieldTelOneSymbol() {

        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("7");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void fieldTelThirteenSymbols() {

        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("+790012345678");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void fieldTelWithoutPlus() {

        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("89001234567");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

    @Test
    void lettersInFieldTel() {

        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("Андрей Щепкин");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.strip());
    }

//    Тестирование чекбокса

    @Test
    void dontClickCheckbox(){
        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("Андрей Щепкин");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("+79001234567");
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=agreement].input_invalid")).getText();
        String expectedText = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedText, actualText.strip());
    }
}

