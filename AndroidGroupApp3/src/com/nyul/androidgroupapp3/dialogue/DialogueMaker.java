package com.nyul.androidgroupapp3.dialogue;

import static android.content.DialogInterface.BUTTON_POSITIVE;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogueMaker {
	private final Context context;

	public DialogueMaker(Context context){
		this.context = context;
	}

	public void message(String title, String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder.setMessage(message)
			   .setTitle(title)
			   .setPositiveButton("Ok", new OkListener());					   
		
		AlertDialog dialog = builder.create();
		dialog.show();		
	}
	
	public SelectionListener options(String title, String ... items){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		MultiChoiceListener multiChoiceListener = new MultiChoiceListener(items);
		OkListener okListener = new OkListener();
		builder.setTitle(title)
			   .setPositiveButton("Ok", okListener)
			   .setMultiChoiceItems(items, null, multiChoiceListener);					   
		
		AlertDialog dialog = builder.create();
		dialog.show();
		return new SelectionListener(okListener, multiChoiceListener);
	}
	
	class OkListener implements DialogInterface.OnClickListener {
		private boolean wasOkPressed = false;
		
   		public void onClick(DialogInterface dialog, int id) {
   			if(id == BUTTON_POSITIVE){
   				wasOkPressed = true;
   			}
   		}
   		
   		public boolean wasOkPressed(){
   			return wasOkPressed;
   		}
    }
	
	class MultiChoiceListener implements DialogInterface.OnMultiChoiceClickListener{
		private final String[] items;
		private final boolean[] isChecked;
		private final List<String> checkedOptionsList; 
		
		public MultiChoiceListener(String ... items) {
			this.items = items;
			this.isChecked = new boolean[items.length];
			this.checkedOptionsList = new ArrayList<String>(items.length); 
		}
		
		@Override
		public void onClick(DialogInterface dialog, int which, boolean isChecked) {
			this.isChecked[which] = isChecked;
			this.checkedOptionsList.add(items[which]);
		}
		
		public String[] getCheckedOptionsList(){
			return checkedOptionsList.toArray(new String[0]);
		}
	}
	
}