package com.dddpeter.app.rainweather;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		setContentView(R.layout.activity_splash_screen);
	   //������������1����
			  new Handler().postDelayed(new Runnable() {
				   @Override
			  public void run() {
				  Intent intent = new Intent(SplashScreenActivity.this,IndexActivity.class);  //����������ui��ת����ui
			    startActivity(intent);
			    SplashScreenActivity.this.finish();    // ����������������
			   }
			  }, 2000);    //������������2����
			  
		 }
}

