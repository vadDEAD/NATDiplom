package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ru.netology.page.PurchasePage;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;
import static ru.netology.data.SQLHelper.*;

public class TourPurchaseTest {

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUP() {
        String sutUrl = System.getProperty("sut.url");
        open(sutUrl);
    }


    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Nested
    //"Валидная карта"

    public class ValidCard {

        @Test
        @SneakyThrows
        @DisplayName("Покупка валидной картой")
        public void shouldPaymentValidCard() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getApprovedCard();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "APPROVED";
            var payStatus = getPayStatus();
            assertEquals("APPROVED", payStatus);
        }

    }
}