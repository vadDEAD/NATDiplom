package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class PurchasePage {
    //кнопки
    private final SelenideElement buyButton = $(byText("Купить"));
    private final SelenideElement creditButton = $(byText("Купить в кредит"));
    private final SelenideElement continueButton = $(byText("Продолжить"));

    //надписи под кнопками
    private  final SelenideElement buyHeading = $(byText("Оплата по карте"));
    private  final SelenideElement creditHeading = $(byText("Кредит по данным карты"));

    // поля для ввода данных карт
    private  final SelenideElement cardNumber = $("input[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("input[placeholder='08']");
    private final SelenideElement yearField = $("input[placeholder='08']");
    private final SelenideElement ownerField = $(byText("Владелец")).parent().$("input");
    private final SelenideElement cvcField = $("input[placeholder='999']");
    private final SelenideElement notificationSuccessfully = $(".notification_status_ok");


    //ошибки
    private final SelenideElement cardNumberFieldError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");
    private final SelenideElement monthFieldError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");
    private final SelenideElement yearFieldError = $x("//*[text()='Год']/..//*[@class='input__sub']");
    private final SelenideElement ownerFieldError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");
    private final SelenideElement cvcFieldError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");
    private final SelenideElement notificationError = $(".notification_status_error");

    public void cardPayment(){
        buyButton.click();
        buyHeading.shouldBe(Condition.visible);
    }
    public void cardCredit(){
        creditButton.click();
        creditHeading.shouldBe(Condition.visible);
    }
    public void emptyForm() {
        continueButton.click();
        cardNumberFieldError.shouldBe(Condition.visible);
        monthFieldError.shouldBe(Condition.visible);
        yearFieldError.shouldBe(Condition.visible);
        ownerFieldError.shouldBe(Condition.visible);
        cvcFieldError.shouldBe(Condition.visible);
    }

}
