package com.nyul.androidgroupapp3.dialogue;

import com.nyul.androidgroupapp3.dialogue.DialogueMaker.MultiChoiceListener;
import com.nyul.androidgroupapp3.dialogue.DialogueMaker.OkListener;

public class SelectionListener{
	private final OkListener okListener;
	private final MultiChoiceListener multiChoiceListener;
	
	public SelectionListener(OkListener okListener, MultiChoiceListener multiChoiceListener) {
		this.okListener = okListener;
		this.multiChoiceListener = multiChoiceListener;
	}
	
	public boolean wasOkPressed(){
		return okListener.wasOkPressed();
	}
	
	public String[] getCheckedOptionsList(){
		return multiChoiceListener.getCheckedOptionsList();
	}
	
}