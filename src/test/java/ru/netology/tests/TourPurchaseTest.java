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
import static ru.netology.data.SQLHelper.getOrderCount;
import static ru.netology.data.SQLHelper.getPaymentStatus;


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
        @DisplayName("Error buy a tour declined card")
        void shouldErrorBuyATourDeclinedCard() throws InterruptedException {
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
        @DisplayName("Error nonexistent card number")
        void shouldErrorNonexistentCardNumber() throws InterruptedException {
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
        void shouldErrorIncompleteCardNumber() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithIncompleteCardNumber();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error empty card number")
        void shouldErrorEmptyCardNumber() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithEmptyCardNumber();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error empty month")
        void shouldErrorEmptyMonth() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithEmptyMonth();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error nonexistent (00) low month")
        void shouldErrorNonexistentLowMonth() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithLowNonexistentMonthValue();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error nonexistent (14) great month")
        void shouldErrorNonexistentGreatMonth() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithGreatNonexistentMonthValue();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error overdue month")
        void shouldErrorWithOverdueMonth() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithOverdueMonth();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with empty year")
        void shouldErrorWithEmptyYear() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithEmptyYear();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with overdue year")
        void shouldErrorWithOverdueYear() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithOverdueYear();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with plus 8 years")
        void shouldErrorWithPlus8Years() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithYearFromFuture();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with space in owner")
        void shouldErrorWithSpaceInOwner() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithSpaceInOwner();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with special symbols in owner")
        void shouldErrorWithSpecialSymbolsInOwner() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithSpecialSymbolsInOwner();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with numbers in owner")
        void shouldErrorWithNumbersInOwner() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithNumbersInOwner();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with incomplete CVC")
        void shouldErrorWithWithIncompleteCVC() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();
            var info = getCardWithIncompleteCVC();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
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
            purchasePage.cardCredit();
            var info = getApprovedCard();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "APPROVED";
            var actual = getPaymentStatus();
            purchasePage.bankApproved();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error buy a tour declined card")
        void shouldErrorBuyATourDeclinedCard() throws InterruptedException {
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
        @DisplayName("Error nonexistent card number")
        void shouldErrorNonexistentCardNumber() throws InterruptedException {
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
        void shouldErrorIncompleteCardNumber() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithIncompleteCardNumber();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error empty card number")
        void shouldErrorEmptyCardNumber() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithEmptyCardNumber();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error empty month")
        void shouldErrorEmptyMonth() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithEmptyMonth();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error nonexistent (00) low month")
        void shouldErrorNonexistentLowMonth() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithLowNonexistentMonthValue();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error nonexistent (14) great month")
        void shouldErrorNonexistentGreatMonth() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithGreatNonexistentMonthValue();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error overdue month")
        void shouldErrorWithOverdueMonth() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithOverdueMonth();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with empty year")
        void shouldErrorWithEmptyYear() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithEmptyYear();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with overdue year")
        void shouldErrorWithOverdueYear() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithOverdueYear();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with plus 8 years")
        void shouldErrorWithPlus8Years() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithYearFromFuture();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with space in owner")
        void shouldErrorWithSpaceInOwner() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithSpaceInOwner();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with special symbols in owner")
        void shouldErrorWithSpecialSymbolsInOwner() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithSpecialSymbolsInOwner();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with numbers in owner")
        void shouldErrorWithNumbersInOwner() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithNumbersInOwner();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            purchasePage.bankDeclined();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Error with incomplete CVC")
        void shouldErrorWithWithIncompleteCVC() throws InterruptedException {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
            var info = getCardWithIncompleteCVC();
            purchasePage.sendingData(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "0";
            var actual = getOrderCount();
            assertEquals(expected, actual);
        }
    }

    @Nested
    //Уведомления об ошибке на странице
    public class CreditFormFieldValidation {

        @BeforeEach
        public void setPayment() {
            var purchasePage = new PurchasePage();
            purchasePage.cardCredit();
        }

        @Test
        @DisplayName("Error empty form")
        public void shouldEmpty() {
            var purchasePage = new PurchasePage();
            purchasePage.emptyForm();
        }

        @Test
        @DisplayName("Error empty card number")
        public void shouldEmptyCardNumberField() {
            var purchasePage = new PurchasePage();
            var info = getApprovedCard();
            purchasePage.emptyCardNumberField(info);
        }

        @Test
        @DisplayName("Error incomplete card number")
        public void shouldCardWithIncompleteCardNumber() {
            var purchasePage = new PurchasePage();
            var info = getCardWithIncompleteCardNumber();
            purchasePage.invalidCardNumberField(info);
        }

        @Test
        @DisplayName("Error empty month")
        public void shouldEmptyMonthField() {
            var purchasePage = new PurchasePage();
            var info = getApprovedCard();
            purchasePage.emptyMonthField(info);
        }

        @Test
        @DisplayName("Error overdue month")
        public void shouldCardWithOverdueMonth() {
            var purchasePage = new PurchasePage();
            var info = getCardWithOverdueMonth();
            purchasePage.invalidMonthField(info);
        }

        @Test
        @DisplayName("Error low non existing (00) Month")
        public void shouldCardWithLowerMonthValue() {
            var purchasePage = new PurchasePage();
            var info = getCardWithLowNonexistentMonthValue();
            purchasePage.invalidMonthField(info);
        }

        @Test
        @DisplayName("Error max non existing (14) Month")
        public void shouldCardWithGreaterMonthValue() {
            var purchasePage = new PurchasePage();
            var info = getCardWithGreatNonexistentMonthValue();
            purchasePage.invalidMonthField(info);
        }

        @Test
        @DisplayName("Error empty year")
        public void shouldEmptyYearField() {
            var purchasePage = new PurchasePage();
            var info = getApprovedCard();
            purchasePage.emptyYearField(info);
        }

        @Test
        @DisplayName("Error overdue year")
        public void shouldCardWithOverdueYear() {
            var purchasePage = new PurchasePage();
            var info = getCardWithOverdueYear();
            purchasePage.invalidYearField(info);
        }

        @Test
        @DisplayName("Error overdue (+8) year")
        public void shouldCardWithYearFromFuture() {
            var purchasePage = new PurchasePage();
            var info = getCardWithYearFromFuture();
            purchasePage.invalidYearField(info);
        }

        @Test
        @DisplayName("Error message empty owner")
        public void shouldEmptyOwnerField() {
            var purchasePage = new PurchasePage();
            var info = getApprovedCard();
            purchasePage.emptyOwnerField(info);
        }

        @Test
        @DisplayName("Error message Space in owner")
        public void shouldCardWithSpaceInOwner() {
            var purchasePage = new PurchasePage();
            var info = getCardWithSpaceInOwner();
            purchasePage.invalidOwnerField(info);
        }

        @Test
        @DisplayName("Error message special symbols in owner")
        public void shouldCardWithSpecialSymbolsInOwner() {
            var purchasePage = new PurchasePage();
            var info = getCardWithSpecialSymbolsInOwner();
            purchasePage.invalidOwnerField(info);
        }

        @Test
        @DisplayName("Error message numbers in owner")
        public void shouldCardWithNumbersInOwner() {
            var purchasePage = new PurchasePage();
            var info = getCardWithNumbersInOwner();
            purchasePage.invalidOwnerField(info);
        }

        @Test
        @DisplayName("Error message empty CVC/CVV")
        public void shouldEmptyCVCField() {
            var purchasePage = new PurchasePage();
            var info = getApprovedCard();
            purchasePage.emptyCVCField(info);
        }

        @Test
        @DisplayName("Error message incomplete CVC/CVV")
        public void shouldCardWithIncompleteCVC() {
            var purchasePage = new PurchasePage();
            var info = getCardWithIncompleteCVC();
            purchasePage.invalidCVCField(info);
        }
    }
}
