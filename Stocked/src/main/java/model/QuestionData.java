package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionData {
	private String questionPrompt;
	private List<String> choices;
	private int buttonValue; // this represents the likelihood the user selected in the survey
	private int weighting;

	public QuestionData(String questionPrompt, String... choices) {
		this.questionPrompt = questionPrompt;
		this.choices = Arrays.asList(choices);
	}

	public static List<QuestionData> getQuestions() {
		List<QuestionData> questionDataList = new ArrayList<>();

		// personality questions (less weight)
		questionDataList.add(new QuestionData(
				"If you were to start a business, how likely would you be to see the initial challenges as an exciting part of the entrepreneurial journey, with the potential for significant rewards?"));
		questionDataList.add(new QuestionData(
				"If you were to explore a new career path that might involve some financial uncertainty initially, how likely would you be to embrace the opportunity for personal and professional growth?"));
		questionDataList.add(new QuestionData(
				"If you were to consider investing in a startup or a new technology, how likely would you be to see it as an exciting venture with the potential for innovation and substantial returns?"));

		questionDataList.add(new QuestionData(
				"If you were to invest in your education for a career change, how likely would you be to see it as a strategic move to increase your earning potential in the long run?"));
		questionDataList.add(new QuestionData(
				"If you were to prioritize experiences like travel and personal development over saving for a traditional down payment, how likely would you be to see it as an investment in your overall well-being and happiness?"));

		
		// finance questions (more weighting)
		questionDataList.add(new QuestionData(
				"What is the likelihood of losing my job or experiencing a significant reduction in income in the near future?"));
		questionDataList.add(new QuestionData(
				"If you were to take a year off to travel or pursue a passion project, how likely would you be to view it as an investment in your personal development, even with potential financial trade-offs?"));
		questionDataList.add(new QuestionData(
				"If you were to consider freelancing or gig work over traditional employment, how likely would you be to value the flexibility and potential for higher earnings, despite the inconsistent income?"));

		return questionDataList;
	}


	public String getQuestionPrompt() {
		return questionPrompt;
	}

	public void setQuestionPrompt(String questionPrompt) {
		this.questionPrompt = questionPrompt;
	}

}
