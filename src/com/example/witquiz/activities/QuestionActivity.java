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
		
		QuestionFragment fragment = new QuestionFragment();
		
		Bundle bundle = this.getIntent().getExtras();
		fragment.setArguments(bundle);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, fragment).commit();
		}
		
	}

}
