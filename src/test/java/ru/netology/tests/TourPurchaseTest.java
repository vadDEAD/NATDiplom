package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ru.netology.data.SQLHelper;
import ru.netology.page.PurchasePage;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;
import static ru.netology.data.SQLHelper.*;


public class TourPurchaseTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    public void cleanUpDB() {
        SQLHelper.databaseCleanUp();
    }


    @Nested
    //Проверка базы данных "Купить"
    public class CardPayment {
        @Test
        @SneakyThrows
        @DisplayName("Successful buy a tour approved card")
        public void shouldSuccessfullyBuyATourApprovedCard() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getApprovedCard();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "APPROVED";
            var actual = getPaymentStatus();
            purchasePage.bankApproved();
            assertEquals(expected, actual);
        }

        @Test
        @SneakyThrows
        @DisplayName("Error buy a tour declined card")
        void shouldErrorBuyATourDeclinedCard(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getDeclinedCard();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "DECLINED";
            var actual = getPaymentStatus();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @SneakyThrows
        @DisplayName("Error nonexistent card number")
        void shouldErrorNonexistentCardNumber(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getNonExistentCard();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error incomplete card number")
        void shouldErrorIncompleteCardNumber(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithIncompleteCardNumber();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.cardNumberFormatErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error empty card number")
        void shouldErrorEmptyCardNumber(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithEmptyCardNumber();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.cardNumberFormatErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error empty month")
        void shouldErrorEmptyMonth(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithEmptyMonth();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongFormatMonthErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error nonexistent (00) low month")
        void shouldErrorNonexistentLowMonth(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithLowNonexistentMonthValue();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongExpirationMonthErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error nonexistent (14) great month")
        void shouldErrorNonexistentGreatMonth(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithGreatNonexistentMonthValue();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongExpirationMonthErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error overdue month")
        void shouldErrorWithOverdueMonth(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithOverdueMonth();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongExpirationMonthErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with empty year")
        void shouldErrorWithEmptyYear(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithEmptyYear();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongExpirationYearErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with overdue year")
        void shouldErrorWithOverdueYear(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithOverdueYear();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.overdueYearErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with plus 8 years")
        void shouldErrorWithPlus8Years(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithYearFromFuture();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongExpirationYearErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with not complete year")
        void shouldErrorWithNotCompleteYears(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithNotCompleteYear();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongFormatYearErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with space in owner")
        void shouldErrorWithSpaceInOwner(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithSpaceInOwner();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.emptyOwnerErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with special symbols in owner")
        void shouldErrorWithSpecialSymbolsInOwner(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithSpecialSymbolsInOwner();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            purchasePage.ownerFormatErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with numbers in owner")
        void shouldErrorWithNumbersInOwner(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithNumbersInOwner();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.ownerFormatErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with incomplete CVC")
        void shouldErrorWithWithIncompleteCVC(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithIncompleteCVC();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.cvcFormatErrorVisible();
            assertEquals(expected, actual);
        }
        @Test
        @DisplayName("Error with empty CVC")
        void shouldErrorWithWithEmptyCVC(){
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithEmptyCVC();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.cvcEmptyErrorVisible();
            assertEquals(expected, actual);
        }
    }
    @Nested
    //Проверка базы данных "Купить в кредит"
    public class CardCredit {
        @Test
        @SneakyThrows
        @DisplayName("Successful buy a tour approved card")
        public void shouldSuccessfullyBuyATourApprovedCard() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getApprovedCard();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "APPROVED";
            var actual = getPaymentStatus();
            purchasePage.bankApproved();
            assertEquals(expected, actual);
        }

        @Test
        @SneakyThrows
        @DisplayName("Error buy a tour declined card")
        void shouldErrorBuyATourDeclinedCard(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getDeclinedCard();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "DECLINED";
            var actual = getPaymentStatus();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @SneakyThrows
        @DisplayName("Error nonexistent card number")
        void shouldErrorNonexistentCardNumber(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getNonExistentCard();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error incomplete card number")
        void shouldErrorIncompleteCardNumber(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithIncompleteCardNumber();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.cardNumberFormatErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error empty card number")
        void shouldErrorEmptyCardNumber(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithEmptyCardNumber();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.cardNumberFormatErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error empty month")
        void shouldErrorEmptyMonth(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithEmptyMonth();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongFormatMonthErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error nonexistent (00) low month")
        void shouldErrorNonexistentLowMonth(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithLowNonexistentMonthValue();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongExpirationMonthErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error nonexistent (14) great month")
        void shouldErrorNonexistentGreatMonth(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithGreatNonexistentMonthValue();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongExpirationMonthErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error overdue month")
        void shouldErrorWithOverdueMonth(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithOverdueMonth();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongExpirationMonthErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with empty year")
        void shouldErrorWithEmptyYear(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithEmptyYear();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongExpirationYearErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with overdue year")
        void shouldErrorWithOverdueYear(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithOverdueYear();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.overdueYearErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with plus 8 years")
        void shouldErrorWithPlus8Years(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithYearFromFuture();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongExpirationYearErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with not complete year")
        void shouldErrorWithNotCompleteYears(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithNotCompleteYear();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.wrongFormatYearErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with space in owner")
        void shouldErrorWithSpaceInOwner(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithSpaceInOwner();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.emptyOwnerErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with special symbols in owner")
        void shouldErrorWithSpecialSymbolsInOwner(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithSpecialSymbolsInOwner();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            purchasePage.ownerFormatErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with numbers in owner")
        void shouldErrorWithNumbersInOwner(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithNumbersInOwner();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.ownerFormatErrorVisible();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with incomplete CVC")
        void shouldErrorWithWithIncompleteCVC(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithIncompleteCVC();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.cvcFormatErrorVisible();
            assertEquals(expected, actual);
        }
        @Test
        @DisplayName("Error with empty CVC")
        void shouldErrorWithWithEmptyCVC(){
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithEmptyCVC();
            purchasePage.sendingData(info);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.cvcEmptyErrorVisible();
            assertEquals(expected, actual);
        }
    }
}

