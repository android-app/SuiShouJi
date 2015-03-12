package com.kevin.suishouji;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomePageActivity extends Activity {

	private EditText content;
	private Button back;
	private ImageButton btnRight;
	private String str;
	private DBConnection helper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home);

		content = (EditText) findViewById(R.id.content);
		btnRight = (ImageButton) findViewById(R.id.btnRight);
		back = (Button) findViewById(R.id.back);

		helper = new DBConnection(this);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		str = formatter.format(curDate);
		// 检查网络状态
		if (!checkNetworkInfo()) {
			return;
		}
		btnRight.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isEmpty()) {
					ContentValues values = new ContentValues();
					values.put("content",
							Html.fromHtml(content.getText().toString()) + "");
					values.put("writetime", str);

					SQLiteDatabase db = helper.getWritableDatabase();
					db.insert("content", null, values);
					db.close();

					// 关闭键盘
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(btnRight.getWindowToken(), 0);

					isShare();// 分享
				}
			}
		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(HomePageActivity.this,
						WebViewActivity.class);
				intent.putExtra("flat", "baidu");
				startActivity(intent);

			}
		});
	}

	private void isShare() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.logo)
				.setTitle("发表成功，立即分享？")
				.setPositiveButton("好的", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("text/plain");
						intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
						intent.putExtra(Intent.EXTRA_TEXT, content.getText()
								.toString());
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(Intent.createChooser(intent, getTitle()));

						content.setText("");
					}
				})
				.setNegativeButton("以后", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						content.setText("");
					}
				}).show();

	}

	/**
	 * 检查网络状态
	 */
	public boolean checkNetworkInfo() {

		ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// mobile 3G Data Network
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		// wifi
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		// 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接

		if (mobile == State.CONNECTED || mobile == State.CONNECTING)
			return true;
		if (wifi == State.CONNECTED || wifi == State.CONNECTING)
			return true;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("需要网络支持！")
				.setCancelable(false)
				.setPositiveButton("进行网络配置",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								// 进入无线网络配置界面
								startActivity(new Intent(
										Settings.ACTION_WIRELESS_SETTINGS));
								HomePageActivity.this.finish();
							}
						})
				.setNegativeButton("退出", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						HomePageActivity.this.finish();
					}
				});
		builder.show();
		return false;

	}

	private boolean isEmpty() {
		if (content.getText() == null
				|| content.getText().toString().trim().equals("")) {
			Toast toast = Toast.makeText(HomePageActivity.this,
					this.getText(R.string.contentnull), Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return false;
		}

		return true;
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
