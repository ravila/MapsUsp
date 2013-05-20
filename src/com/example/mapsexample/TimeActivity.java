package com.example.mapsexample;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class TimeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);
		
		TextView tv = (TextView) findViewById(R.id.time);
		
		TimeCalculatorBUSP t = new TimeCalculatorBUSP(this);
		int time = t.time(2);
		tv.setText("O tempo eh: " + time);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time, menu);
		return true;
	}

}
