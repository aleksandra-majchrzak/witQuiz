package com.example.witquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class CreatorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creator);
		
		loadControls();
	}
	
	private void loadControls(){
		
		RadioButton editCategoryRadio = (RadioButton) findViewById(R.id.edit_category_radioButton);
		RadioButton newCategoryRadio = (RadioButton) findViewById(R.id.new_category_radioButton);
		
		final Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner);
		final EditText newCategoryEditText = (EditText) findViewById(R.id.new_category_editText);
		Button nextButton = (Button) findViewById(R.id.creator_next_button);
		
		if(editCategoryRadio.isChecked())
			categorySpinner.setEnabled(true);
		else
			categorySpinner.setEnabled(false);
		
		if(newCategoryRadio.isChecked())
			newCategoryEditText.setEnabled(true);
		else
			newCategoryEditText.setEnabled(false);
		
		editCategoryRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if(isChecked)
					categorySpinner.setEnabled(true);
				else
					categorySpinner.setEnabled(false);
				
			}
		});
		
		newCategoryRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked)
					newCategoryEditText.setEnabled(true);
				else
					newCategoryEditText.setEnabled(false);
				
			}
		});
		
	}
}
