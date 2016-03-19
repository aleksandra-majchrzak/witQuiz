package com.example.witquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public static final String SHARED_PREFERENCES = "WIT_PREFERENCES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        loadControls();
    }
    
    private void loadControls() {
    	
    	Button startButton = (Button) findViewById(R.id.start_button);
    	
    	startButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				EditText usernameExitText = (EditText) findViewById(R.id.username_editText);
				String userName = usernameExitText.getText().toString();
				
				SharedPreferences shared = getSharedPreferences(SHARED_PREFERENCES, 0);
				shared.edit()
						.putString("CurrentUser", userName)
						.commit();
				
				Intent intent = new Intent(MainActivity.this, MenuActivity.class);
				startActivity(intent);
			}
    		
    	});
    }
}
