package com.kevin.suishouji;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AboutActivity extends Activity {

	private RelativeLayout help, aboutUs, recommend;
	private Button back, btnRight;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		help = (RelativeLayout) findViewById(R.id.help);
		aboutUs = (RelativeLayout) findViewById(R.id.aboutUs);
		recommend = (RelativeLayout) findViewById(R.id.recommend);
		btnRight = (Button) findViewById(R.id.btnRight);
		back = (Button) findViewById(R.id.back);
		btnRight.setVisibility(View.GONE);
		back.setVisibility(View.GONE);

		help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutActivity.this,
						HelpActivity.class);
				startActivity(intent);
			}
		});
		recommend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(AboutActivity.this,
						RecommendActivity.class);
				startActivity(intent);
			}
		});
		aboutUs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutActivity.this,
						AboutUsActivity.class);
				startActivity(intent);
			}
		});
	}

	private static Boolean isExit = false;
	private static Boolean hasTask = false;
	Timer tExit = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			isExit = false;
			hasTask = true;
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				if (!hasTask) {
					tExit.schedule(task, 2000);
				}
			} else {
				finish();
				System.exit(0);
			}
		}
		return false;
	}
}
