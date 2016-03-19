package com.example.witquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		loadControls();
	}
	
	private void loadControls(){
		
		Button newGameButton = (Button) findViewById(R.id.new_game_button);
		Button bestScoresButton = (Button) findViewById(R.id.best_scores_button);
		Button creatorButton = (Button) findViewById(R.id.creator_button);
		Button exitButton = (Button) findViewById(R.id.exit_button);
		
		newGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent intent = new Intent(MenuActivity.this, NewGameActivity.class);
				startActivity(intent);
			}
		});
		
		bestScoresButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent intent = new Intent(MenuActivity.this, BestScoresActivity.class);
				startActivity(intent);
			}
		});
	}
}
