///*
// * 
// */
//
//package controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import model.QuestionData;
//
//public class SurveyController {
//    public static List<QuestionData> getQuestions() {
//        List<QuestionData> questionDataList = new ArrayList<>();
//
//        questionDataList.add(new QuestionData("How old are you?", List.of("16-18", "18-20", "20-25", "25+")));
//        questionDataList.add(new QuestionData("How much money have you allocated for investing?",
//                List.of("Less than $100", "$100-$300", "$300-$500", "$500+")));
//        questionDataList.add(new QuestionData("How much investing experience do you have?",
//                List.of("This is my first time", "Less than 1 year", "1-3 years", "More than 4 years")));
//        questionDataList.add(new QuestionData("What is your primary reason for investing?",
//                List.of("Saving for retirement", "Home deposit", "Pay off student loans", "Not sure yet")));
//        questionDataList.add(new QuestionData("When do you expect to pull money from your investments?",
//                List.of("Less than 1 year", "1-4 years", "5-10 years", "10+ years")));
//        questionDataList.add(new QuestionData("Which scenario best describes you?",
//                List.of("Pursue modest increases in my investments, with low risk of loss",
//                        "Aim for investment growth, " + "accepting moderate risk of loss",
//                        "Seek above-average growth in investments, accepting above-average risk of loss",
//                        "Reach for maximum returns, accepting significant risk of loss ")));
//        questionDataList.add(new QuestionData(
//                "You make an investment, planning to hold it for 5 years. It then loses 20% in its first year. How do you react?",
//                List.of("I would sell my investment because of my concerns",
//                        "I would consider selling a part of my investment",
//                        "I would wait to see how it continues to perform",
//                        "I would buy more of the investment because of the discount")));
//        questionDataList.add(new QuestionData(
//                "Imagine that an investment you own lost 30% of its value in 3 days. What would you do?",
//                List.of("Sell all my shares", "Sell a portion of my shares", "Do nothing", "Buy more shares")));
//
//        return questionDataList;
//    }
//}
