package com.nyul.androidgroupapp3;

import static java.lang.Thread.sleep;

import java.util.HashMap;

import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nyul.androidgroupapp3.dialogue.DialogueMaker;
import com.nyul.androidgroupapp3.dialogue.SelectionListener;


public class MainActivity extends ParentActivity{
	DialogueMaker dialogueMaker = new DialogueMaker(this);	
	
	public static HashMap<String, String> map  = new HashMap();
	public static HashMap<String, String[]> answerMap = new HashMap();
	static{
		map.put("Shoes", "What year did the Jordan 12's come out in?");
		answerMap.put("Shoes", new String[]{"anwer1", "answer2", "answer3", "answer4"});
		map.put("Video Games", "What happend at the end of Batman Arkham Orgins?");
		answerMap.put("Video Games", new String[]{"anwer1", "answer2", "answer3", "answer4"});
		map.put("Encyclopedia", "Who made the Encyclopedia?");
		answerMap.put("Encyclopedia", new String[]{"anwer1", "answer2", "answer3", "answer4"});
		map.put("", "What year did the Jordan 12's come out in?");
		answerMap.put("Shoes", new String[]{"anwer1", "answer2", "answer3", "answer4"});
		answerMap.put("Shoes", new String[]{"anwer1", "answer2", "answer3", "answer4"});
		answerMap.put("Shoes", new String[]{"anwer1", "answer2", "answer3", "answer4"});
		answerMap.put("Shoes", new String[]{"anwer1", "answer2", "answer3", "answer4"});
		answerMap.put("Shoes", new String[]{"anwer1", "answer2", "answer3", "answer4"});
		
		
		map.put("Video Games","What happend at the end of Batman Arkham Orgins?");
		map.put("Encyclopedia","Who made the Encyclopedia?");
		map.put("Mark Zuckerbrhg","How Much is Facebook worth?");
		map.put("Comic Books","What is the Omega Force");
		map.put("Steve Jobs","Who was Steve Jobs Right hand Man?");
		map.put("Microsoft","Is The Surface tablet the first device to use that name");
		map.put("Apple","Who is the CEO of Apple?");
	}
	
	MultiOptionsHandler multiOptionsHandler = null;
	protected void onClickButton1(View v) {
		final String[] items = { "Shoes", "Video Games", "Steve Jobs","Comic Books", "Encyclopedia","Mark Zuckerberhg","Microsoft","Apple" };
		final SelectionListener listener = dialogueMaker.options("Pick a Category", items);
		
		multiOptionsHandler =  new MultiOptionsHandler(listener) ;
		Thread t = new Thread(multiOptionsHandler);		
		t.start();
	}
	
	protected void onClickButton2(View v) {
		if(multiOptionsHandler != null){
			String selection = multiOptionsHandler.getSelectedOption();
			dialogueMaker.options("Title", answerMap.get(selection));
			Thread t = new Thread(multiOptionsHandler);	
			t.start();
		}
	}
	
	class SelectedOptionsHandler implements Runnable{
		private final SelectionListener listener;
		private final TextView tV = (TextView) findViewById(R.id.textView1);
		private String selection = "";
		
		public SelectedOptionsHandler(SelectionListener listener) {
			this.listener = listener;
		}

		@Override
		public void run() {
			String[] optionsSelected = listener.getCheckedOptionsList();
			for(String selection : optionsSelected){
				this.selection = selection;
				tV.setText(makeQuestion(selection));
			}
		}

		private String makeQuestion(String selection) {
			return map.get(selection);	
		}
		
		public String getSelection(){
			return selection;
		}
		
	}
	
	class MultiOptionsHandler implements Runnable{
		private final SelectionListener listener;
		private final TextView tV = (TextView) findViewById(R.id.textView1);				
		private SelectedOptionsHandler selectedOptionsHandler;
		
		public MultiOptionsHandler(SelectionListener listener) {
			this.listener = listener; 
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
			
			selectedOptionsHandler = new SelectedOptionsHandler(listener);
			tV.post(selectedOptionsHandler);	
		}
		
		public String getSelectedOption(){
			return selectedOptionsHandler.getSelection();
		}
		
	}

}