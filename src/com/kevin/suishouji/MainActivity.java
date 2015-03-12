package com.kevin.suishouji;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends ActivityGroup {
	private ImageButton home;
	private ImageButton write, look, about;
	private RelativeLayout level2;
	private FrameLayout content;
	private boolean isLevel2Show = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		content = (FrameLayout) this.findViewById(R.id.content);

		home = (ImageButton) findViewById(R.id.home);
		write = (ImageButton) findViewById(R.id.write);
		look = (ImageButton) findViewById(R.id.look);
		about = (ImageButton) findViewById(R.id.about);

		level2 = (RelativeLayout) findViewById(R.id.level2);

		try {
			setCurrentActivity("HomePageActivity");
		} catch (Exception e) {
			e.printStackTrace();
		}
		write.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					setCurrentActivity("HomePageActivity");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		look.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					setCurrentActivity("LookActivity");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					setCurrentActivity("AboutActivity");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				level2.setVisibility(View.VISIBLE);
				if (!isLevel2Show) {
					// 显示2级导航菜单
					MyAnimation.startAnimationIN(level2, 500);
				} else {
					// 隐藏2级导航菜单
					MyAnimation.startAnimationOUT(level2, 500, 0);
				}
				isLevel2Show = !isLevel2Show;
			}
		});
	}

	/**
	 * activity跳转
	 * 
	 * @param _activityName
	 * @throws Exception
	 */
	public void setCurrentActivity(String _activityName) throws Exception {

		if (null == content) {
			throw new Exception("内容VIEW尚未创建");
		}

		if (null == _activityName || "".equals(_activityName)) {
			throw new Exception("参数为空");
		}

		if (ActivityManageHelp.getInstance().contains(_activityName)) {

			content.removeAllViews();

			final Intent mIntent = new Intent(this, ActivityManageHelp
					.getInstance().getActivityClass(_activityName))
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			content.addView(getLocalActivityManager().startActivity(
					_activityName, mIntent).getDecorView());

			return;
		}

		throw new Exception("尚未注册此Activity");

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
