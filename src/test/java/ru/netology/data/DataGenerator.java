package ru.netology.data;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;


public class DataGenerator {
    private static final String approveCard = "4444 4444 4444 4441";
    private static final String declineCard = "4444 4444 4444 4442";
    private static final String[] numbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private DataGenerator() {
    }

    private static String getIncompleteCardNumber() {
        return approveCard.substring(0, 18);
    }

    private static String getEmptyCardNumber() {
        return getSpace();
    }

    private static String getValidMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getEmptyMonth() {
        return getSpace();
    }

    private static String getOverdueMonth() {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getLowNonexistentMonthValue() {
        return "00";
    }

    private static String getGreatNonexistentMonthValue() {
        return "14";
    }

    private static String getValidYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("YY"));
    }

    private static String getLastYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("YY"));
    }

    private static String getNextYear() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("YY"));
    }

    private static String getFutureYear() {
        return LocalDate.now().plusYears(8).format(DateTimeFormatter.ofPattern("YY"));
    }

    private static String getNotCompleteYear() {
        Random random = new Random();
        var number = numbers[random.nextInt(10)];
        return number;
        }

    private static String getSpace() {
        return " ";
    }

    private static String getSpecialSymbols() {
        Random random = new Random();
        final String[] specialSymbols = new String[]{"!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+",
                ",", ".", "/", ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^", "_", "`", "{", "|", "}", "~", "-"};
        var fistSymbol = specialSymbols[random.nextInt(32)];
        var secondSymbol = specialSymbols[random.nextInt(32)];
        return fistSymbol + secondSymbol;
    }

    private static String getNumbers() {
        Random random = new Random();
        var fistNumber = numbers[random.nextInt(10)];
        var secondNumber = numbers[random.nextInt(9) + 1];
        return fistNumber + secondNumber;
    }

    private static String getCVC() {
        Random random = new Random();
        var firstNumber = numbers[random.nextInt(10)];
        var secondNumber = getNumbers();
        return firstNumber + secondNumber;
    }

    private static String getOwner() {
        Faker faker = new Faker();
        return faker.name().fullName();
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo(approveCard, getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(declineCard, getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getNonExistentCard() {
        return new CardInfo("7777 7777 7777 7777", getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getCardWithEmptyCardNumber() {
        return new CardInfo(getEmptyCardNumber(), getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getCardWithIncompleteCardNumber() {
        return new CardInfo(getIncompleteCardNumber(), getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getCardWithOverdueMonth() {
        if (getValidMonth().equals("01")) {
            return new CardInfo(approveCard, getOverdueMonth(), getValidYear(), getOwner(), getCVC());
        } else {
            return new CardInfo(approveCard, getOverdueMonth(), getValidYear(), getOwner(), getCVC());
        }
    }

    public static CardInfo getCardWithEmptyMonth() {
        return new CardInfo(approveCard, getEmptyMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getCardWithLowNonexistentMonthValue() {
        return new CardInfo(approveCard, getLowNonexistentMonthValue(), getNextYear(), getOwner(), getCVC());
    }

    public static CardInfo getCardWithGreatNonexistentMonthValue() {
        return new CardInfo(approveCard, getGreatNonexistentMonthValue(), getNextYear(), getOwner(), getCVC());
    }

    public static CardInfo getCardWithEmptyYear() {
        return new CardInfo(approveCard, getValidMonth(), getSpace(), getOwner(), getCVC());
    }
    public static CardInfo getCardWithNotCompleteYear() {
        return new CardInfo(approveCard, getValidMonth(), getNotCompleteYear() , getOwner(), getCVC());
    }

    public static CardInfo getCardWithOverdueYear() {
        return new CardInfo(approveCard, getValidMonth(), getLastYear(), getOwner(), getCVC());
    }

    public static CardInfo getCardWithYearFromFuture() {
        return new CardInfo(approveCard, getValidMonth(), getFutureYear(), getOwner(), getCVC());
    }

    public static CardInfo getCardWithSpaceInOwner() {
        return new CardInfo(approveCard, getValidMonth(), getValidYear(), getSpace(), getCVC());
    }

    public static CardInfo getCardWithSpecialSymbolsInOwner() {
        return new CardInfo(approveCard, getValidMonth(), getValidYear(), getSpecialSymbols(), getCVC());
    }

    public static CardInfo getCardWithNumbersInOwner() {
        return new CardInfo(approveCard, getValidMonth(), getValidYear(), getNumbers(), getCVC());
    }

    public static CardInfo getCardWithIncompleteCVC() {
        return new CardInfo(approveCard, getValidMonth(), getValidYear(), getOwner(), getNumbers());
    }
    public static CardInfo getCardWithEmptyCVC() {
        return new CardInfo(approveCard, getValidMonth(), getValidYear(), getOwner(), getSpace());
    }
    @Value
    public static class CardInfo {
        private String numberCard;
        private String month;
        private String year;
        private String owner;
        private String cvc;
    }
}
