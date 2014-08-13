package com.nyul.androidgroupapp3.minigames;

import static java.lang.Thread.sleep;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.nyul.androidgroupapp3.dialogue.DialogueMaker;
import com.nyul.androidgroupapp3.dialogue.SelectionListener;


public class MainActivity extends ParentActivity{
	DialogueMaker dialogueMaker = new DialogueMaker(this);
	private static String answer = null;



	public static HashMap<String, String[]> questionMap  = new HashMap();
	public static HashMap<String, String[][]> answerMap = new HashMap();
	private static HashMap<String, String[]> correctAnswermap = new HashMap();
	static{
		questionMap.put("Shoes", new String[]{"What year did the Jordan 12's come out in?","When did the Reebok Kamakizi's come out?"});
		answerMap.put("Shoes", new String[][]{{"1997", "1996", "2000", "1993"}, {"2012", "2006", "2014","2009"} });
		questionMap.put("Video Games", new String[]{"What happend at the end of Batman Arkham Orgins?","When does call of duty advanced warfare come out"});
		answerMap.put("Video Games", new String[][]{{"Batman fights Joker", "Batman fights Harley", "Batman fights Blackmask", "Batman dies"}, {"November 4 2014","October 4 2014","December 4 2014","October 31st 2014" } });
		questionMap.put("Encyclopedia", new String[]{"Who made the Encyclopedia?","When was the encyclopedia released?"});
		answerMap.put("Encyclopedia", new String[][]{{"Judy McGrath", "Denis Diderot", "Rajeev Suri", "Mark Parker"},{"A.D. 77","B.C. 44","2012","1414"}});
		questionMap.put("Mark Zuckerberhg", new String[]{"How much is Facebook worth?","What was Mark's Family meassage system titled?"});
		answerMap.put("Mark Zuckerberhg", new String[][]{{"$16 Billion", "$200 Million", "104 Billion", "104 Million"},{"Zucknet","FaceChat","Zuckchat","Zuckertext"}});
		questionMap.put("Comic Books", new String[]{"What is the Omega Force?","Who cursed Deadpool?"});
		answerMap.put("Comic Books", new String[][]{{"Beams that can obiterate you from existance", "Something that makes you evil", "Darkseid", "Source of Darkseid's Power"}, {"Thanos","Death","Pools","Eternity"} });
		questionMap.put("Steve Jobs", new String[]{"Who was Steve Jobs Right hand man?","When was Steve Jobs fired"});
		answerMap.put("Steve Jobs", new String[][]{{"Steve Wozniak", "James Higa", "Larry Page", "Bill Gates"}, {"1991","1985","2010","1983"}});
		questionMap.put("Microsoft", new String[]{"Is the Surface tablet the first device to use the name?","When did Bill Gates found Microsoft"});
		answerMap.put("Microsoft", new String[][]{{"Yes", "No", "Both A & B", "None of the above"}, {"1985","2010","1975","1965"}});
		questionMap.put("Apple", new String[]{"Who is the CEO of Apple?","How many Apple stores in the world"});
		answerMap.put("Apple", new String[][]{{"Tim Cook", "Mark Zuckerbergh", "Steve Jobs", "Bruce Wayne"}, {"430","200","1050","550"}});

		correctAnswermap.put("Shoes",new String[]{"1997", "2014"});
		correctAnswermap.put("Video Games",new String[]{"Batman fights Joker","November 4 2014"});
		correctAnswermap.put("Encyclopedia",new String[]{"Denis Diderot","A.D. 77"});
		correctAnswermap.put("Mark Zuckerberhg",new String[]{"104 Billion","Zucknet"});
		correctAnswermap.put("Comic Books",new String[]{"Source of Darkseid's Power","Thanos"});
		correctAnswermap.put("Steve Jobs",new String[]{"Steve Wozniak","1985"});
		correctAnswermap.put("Microsoft",new String[]{"No","1975"});
		correctAnswermap.put("Apple",new String[]{"Tim Cook","430"});

	}


	MultiOptionsHandler categoryHandler = null;
	protected void onClickButton1(View v) {
		final String[] items = { "Shoes", "Video Games", "Steve Jobs","Comic Books", "Encyclopedia","Mark Zuckerberhg","Microsoft","Apple" };
		final SelectionListener listener = dialogueMaker.options("Pick a Category", items);
		TextView textView = (TextView) findViewById(R.id.textView1);
		categoryHandler =  new MultiOptionsHandler(listener, textView, questionMap) ;
		Thread t = new Thread(categoryHandler);		
		t.start();
	}

	MultiAnswersHandler answerHandler = null;
	protected void onClickButton2(View v) {
		if(categoryHandler != null){
			String selection = categoryHandler.getSelectedOption();
			final SelectionListener listener = dialogueMaker.options("Choose answer", answerMap.get(selection)[random]);
			answer = correctAnswermap.get(selection)[random];
			TextView textView = (TextView) findViewById(R.id.textView2);	
			answerHandler = new MultiAnswersHandler(listener, textView);
			Thread t = new Thread(answerHandler);
			t.start();
		}
	}
	@Override

	protected void onClickButton3(View v) {
		if(answerHandler != null){
			String selectedAnswer = answerHandler.getSelectedOption();

			if( selectedAnswer.equals(answer) ){
				TextView textView = (TextView) findViewById(R.id.textView3);
				textView.setText("Correct Answer");

			}else{
				TextView textView = (TextView) findViewById(R.id.textView3);
				textView.setText("Incorrect Answer");

			}

		}
	}

	static int random = 0;
	class SelectedOptionsHandler implements Runnable{
		private final SelectionListener listener;
		private final TextView textView;
		private String selection = "";
		Map<String, String[]> map;

		public SelectedOptionsHandler(SelectionListener listener, TextView textView, Map<String, String[]> map) {
			this.listener = listener;
			this.textView = textView;
			this.map = map;
		}

		@Override
		public void run() {
			String[] optionsSelected = listener.getCheckedOptionsList();
			for(String selection : optionsSelected){
				this.selection = selection;
				textView.setText(makeQuestion(selection));
			}
		}

		private String makeQuestion(String selection) {
			Random r = new Random();
			String[] questionList = map.get(selection);
			random = r.nextInt(questionList.length);
			return questionList[random];
		}

		public String getSelection(){
			return selection;
		}

	}

	class SelectedAnswerHandler implements Runnable{
		private final SelectionListener listener;
		private final TextView textView;
		private String selection = "";

		public SelectedAnswerHandler(SelectionListener listener, TextView textView) {
			this.listener = listener;
			this.textView = textView;
		}

		@Override
		public void run() {
			String[] optionsSelected = listener.getCheckedOptionsList();
			for(String selection : optionsSelected){
				this.selection = selection;
				textView.setText("You selected " + selection);
			}
		}

		public String getSelection(){
			return selection;
		}

	}

	class MultiOptionsHandler implements Runnable{
		private final SelectionListener listener;
		private final TextView textView;		
		private SelectedOptionsHandler selectedOptionsHandler;
		Map<String, String[]> map;

		public MultiOptionsHandler(SelectionListener listener, TextView textView, Map<String, String[]> map) {
			this.listener = listener; 
			this.textView = textView;
			this.map = map;
		}

		@Override
		public void run() {
			Looper.prepare();
			while( ! listener.wasOkPressed() ){
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					log(e.toString());
				}
			}

			selectedOptionsHandler = new SelectedOptionsHandler(listener, textView, map);
			textView.post(selectedOptionsHandler);	
		}

		public String getSelectedOption(){
			return selectedOptionsHandler.getSelection();
		}

	}

	class MultiAnswersHandler implements Runnable{
		private final SelectionListener listener;
		private final TextView textView;		
		private SelectedAnswerHandler selectedOptionsHandler;

		public MultiAnswersHandler(SelectionListener listener, TextView textView) {
			this.listener = listener; 
			this.textView = textView;
		}

		@Override
		public void run() {
			Looper.prepare();
			while( ! listener.wasOkPressed() ){
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					log(e.toString());
				}
			}

			selectedOptionsHandler = new SelectedAnswerHandler(listener, textView);
			textView.post(selectedOptionsHandler);	
		}

		public String getSelectedOption(){
			return selectedOptionsHandler.getSelection();
		}

	}


}