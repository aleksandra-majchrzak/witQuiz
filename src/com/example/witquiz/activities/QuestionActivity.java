package com.example.witquiz.activities;

import com.example.witquiz.R;
import com.example.witquiz.fragments.QuestionFragment;
import com.example.witquiz.fragments.QuestionSecondFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class QuestionActivity extends Activity implements QuestionFragment.SendSummaryInterface {

	MainSectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		
		mSectionsPagerAdapter = new MainSectionsPagerAdapter(getFragmentManager(), this.getIntent().getExtras());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
	}

	@Override
	public void sendSummary(int allQuestions, int currentQuestion) {

		QuestionSecondFragment fragment = (QuestionSecondFragment) getFragmentManager().findFragmentById(R.id.pager);
		
		if(fragment != null)
			fragment.setSummary(allQuestions, currentQuestion);		
	}

}

class MainSectionsPagerAdapter extends FragmentPagerAdapter {
	
	Bundle bundle;
	
	public final int POSITION_QUESTION = 0;
	public final int POSITION_SUMMARY  = 1;

	public MainSectionsPagerAdapter(FragmentManager fm, Bundle bundle) {
		super(fm);
		
		this.bundle = bundle;
	}
	
	@Override
	public Fragment getItem(int position) {

		if(position == POSITION_QUESTION){
			QuestionFragment fragment = new QuestionFragment();
			
			fragment.setArguments(bundle);
			
			return fragment;
		}else{ //if (position == POSITION_SUMMARY)
			QuestionSecondFragment fragment = new QuestionSecondFragment();
			
			fragment.setArguments(bundle);
			
			return fragment;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return null;
	}
}