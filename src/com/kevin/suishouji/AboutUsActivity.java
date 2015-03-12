package com.kevin.suishouji;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class AboutUsActivity extends Activity {
	private Button back, btnRight;
	private TextView txtVersion, txtName;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.aboutus);
		txtVersion = (TextView) findViewById(R.id.txtVersion);
		txtName = (TextView) findViewById(R.id.txtName);
		txtVersion.setText("版本 V" + getVersionName(this));
		back = (Button) findViewById(R.id.back);
		btnRight = (Button) findViewById(R.id.btnRight);
		back = (Button) findViewById(R.id.back);
		btnRight.setVisibility(View.GONE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		SpannableString spannableString = new SpannableString("小尛龙工作室");
		spannableString.setSpan(new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Uri uri = Uri.parse("http://www.strikingly.com/xiaomolongstudio"); 
				Intent it  = new Intent(Intent.ACTION_VIEW,uri); 
				startActivity(it);

			}
		}, 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		txtName.setText(spannableString);
		txtName.setMovementMethod(LinkMovementMethod.getInstance());

	}

	/**
	 * 获取版本名称 VersionName
	 * 
	 * @param context
	 *            应用的上下文环境（activity、service的超类）
	 * @return 版本名称VersionName
	 */
	public String getVersionName(Context context) {
		PackageManager manager = context.getPackageManager();
		String packageName = context.getPackageName();
		try {
			PackageInfo info = manager.getPackageInfo(packageName, 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			return "1.0.0";
		}
	}

}
