package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PurchasePage {
    private final SelenideElement buyButton = $(byText("Купить"));
    private final SelenideElement buyHeading = $(byText("Оплата по карте"));
    private final SelenideElement creditButton = $(byText("Купить в кредит"));
    private final SelenideElement creditHeading = $(byText("Кредит по данным карты"));
    private final SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("input[placeholder='08']");
    private final SelenideElement yearField = $("input[placeholder='22']");
    private final SelenideElement ownerField = $(byText("Владелец")).parent().$("input");
    private final SelenideElement cvcField = $("input[placeholder='999']");
    private final SelenideElement continueButton = $("form button");

    private SelenideElement cardNumberFieldError = $(byText("Неверный формат"));
    private SelenideElement wrongExpirationMonthError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement monthFieldError = $(byText("Неверный формат"));
    private SelenideElement previousYearError = $(byText("Истёк срок действия карты"));
    private SelenideElement yearFieldError = $(byText("Неверный формат"));
    private SelenideElement wrongExpirationYearError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement emptyOwnerError = $(byText("Поле обязательно для заполнения"));
    private SelenideElement ownerFieldError = $(byText("Неверный формат"));
    private SelenideElement cvcFieldError = $(byText("Неверный формат"));
    private SelenideElement cvcEmptyError = $(byText("Неверный формат"));

    private final SelenideElement notificationSuccessfully = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");

    public void cardPayment() {
        buyButton.click();
        buyHeading.shouldBe(Condition.visible);
    }

    public void cardCredit() {
        creditButton.click();
        creditHeading.shouldBe(Condition.visible);
    }
    public void sendingData(DataGenerator.CardInfo info) {
        cardNumberField.setValue(info.getNumberCard());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        cvcField.setValue(info.getCvc());
        continueButton.click();
    }

    public void emptyForm() {
        continueButton.click();
        cardNumberFieldError.shouldBe(Condition.visible);
        monthFieldError.shouldBe(Condition.visible);
        yearFieldError.shouldBe(Condition.visible);
        ownerFieldError.shouldBe(Condition.visible);
        cvcFieldError.shouldBe(Condition.visible);
    }

    public void bankApproved() {
        notificationSuccessfully.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void bankDeclined() {
        notificationError.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void cardNumberFormatErrorVisible() {
        cardNumberFieldError.shouldBe(visible);
    }


    public void wrongFormatMonthErrorVisible() {
        monthFieldError.shouldBe(visible);
    }

    public void wrongExpirationMonthErrorVisible() {
        wrongExpirationMonthError.shouldBe(visible);
    }

    public void overdueYearErrorVisible() {
        previousYearError.shouldBe(visible);
    }

    public void wrongFormatYearErrorVisible() {
        yearFieldError.shouldBe(visible);
    }

    public void wrongExpirationYearErrorVisible() {
        wrongExpirationYearError.shouldBe(visible);
    }


    public void emptyOwnerErrorVisible() {
        emptyOwnerError.shouldBe(visible);
    }

    public void ownerFormatErrorVisible() {
        ownerFieldError.shouldBe(visible);
    }

    public void cvcFormatErrorVisible() {
        cvcFieldError.shouldBe(visible);
    }

    public void cvcEmptyErrorVisible() {
        cvcEmptyError.shouldBe(visible);
    }

}

