package com.example.witquiz.activities;

import com.example.witquiz.R;
import com.example.witquiz.fragments.QuestionFragment;

import android.app.Activity;
import android.os.Bundle;

public class QuestionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new QuestionFragment()).commit();
		}
	}

}
