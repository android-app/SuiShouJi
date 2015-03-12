package com.kevin.suishouji;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class RecommendActivity extends Activity implements OnClickListener {
	private Button back, btnRight;
	RelativeLayout weiyan, suishouji, wifidebug;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recommend);
		back = (Button) findViewById(R.id.back);
		btnRight = (Button) findViewById(R.id.btnRight);
		weiyan = (RelativeLayout) findViewById(R.id.weiyan);
		btnRight.setVisibility(View.GONE);
		suishouji = (RelativeLayout) findViewById(R.id.suishouji);
		wifidebug = (RelativeLayout) findViewById(R.id.wifidebug);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		weiyan.setOnClickListener(this);
		suishouji.setOnClickListener(this);
		wifidebug.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weiyan:
			Uri uri1 = Uri
					.parse("http://app.mi.com/detail/25323");
			Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
			startActivity(intent1);
			break;
		case R.id.suishouji:

			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
			intent.putExtra(Intent.EXTRA_TEXT,
					"亲，我发现一款生活类应用【随手记】很实用；源自生活、方便生活，头脑风暴、随时记录、随处分享……百度移动应用、安卓市场等可下载安装，推荐你试玩！");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(Intent.createChooser(intent, getTitle()));
			break;
		case R.id.wifidebug:

			Uri uri0 = Uri
					.parse("http://as.baidu.com/a/item?docid=2477970&pre=web_am_se");
			Intent intent0 = new Intent(Intent.ACTION_VIEW, uri0);
			startActivity(intent0);
			break;

		default:
			break;
		}

	}

}
