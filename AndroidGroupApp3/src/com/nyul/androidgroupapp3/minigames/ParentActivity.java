package com.nyul.androidgroupapp3.minigames;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public abstract class ParentActivity extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		final int id = v.getId();

		switch (id) {
		case R.id.button1:
			onClickButton1(v);
			break;
		case R.id.button2:
			onClickButton2(v);
			break;
		case R.id.button3:
			onClickButton3(v);
			break;
		}
	}

	protected abstract void onClickButton1(View v);
	protected abstract void onClickButton2(View v);
	protected abstract void onClickButton3(View v);
	
	public void log(String message){
		Log.e(this.toString(), message);
	}

}