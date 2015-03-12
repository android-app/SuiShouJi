package com.kevin.suishouji;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

@SuppressLint("HandlerLeak")
public class WebViewActivity extends Activity {
	/*private Button back, btnRight;
	private WebView webview;
	private String flat;
	private TextView title;o
	private ProgressBar mProgress;
	private RelativeLayout mAdContainer;
	private DomobAdView mAdview320x50;
	public static final String PUBLISHER_ID = "56OJzWr4uNam66cFWS";
	private AdView adView;
	private boolean isBaiduAD = true;*/

	@SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview);
/*
		adView = (AdView) findViewById(R.id.adView);
		mAdContainer = (RelativeLayout) findViewById(R.id.adcontainer);
		title = (TextView) findViewById(R.id.title);
		back = (Button) findViewById(R.id.back);
		btnRight = (Button) findViewById(R.id.btnRight);
		back = (Button) findViewById(R.id.back);
		btnRight.setVisibility(View.GONE);
		mProgress = (ProgressBar) this.findViewById(R.id.mProgress);
		webview = (WebView) findViewById(R.id.webview);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (webview.canGoBack()) {
					webview.goBack(); // goBack()表示返回webView的上一页面

				} else {
					finish();
				}
			}
		});
		flat = this.getIntent().getStringExtra("flat");
		webview.getSettings().setJavaScriptEnabled(true);
		if (flat.equals("sinaWeibo")) {
			title.setText("新浪微博");
			webview.loadUrl("http://www.strikingly.com/xiaomolongstudio");
			// webview.loadUrl("http://vdisk.weibo.com/s/wIcQL");

		} else {
			title.setText("快乐麻花");
			webview.loadUrl("http://m.kl688.com/");
		}
		webview.setWebViewClient(new MyWebViewClient());
		// 创建一个320x50的广告View
		mAdview320x50 = new DomobAdView(this, PUBLISHER_ID,
				DomobAdView.INLINE_SIZE_320X50);
		mAdview320x50.setKeyword("game");
		mAdview320x50.setUserGender("male");
		mAdview320x50.setUserBirthdayStr("2000-08-08");
		mAdview320x50.setUserPostcode("123456");

		// 设置广告view的监听器。
		mAdview320x50.setAdEventListener(new DomobAdEventListener() {

			@Override
			// 成功接收到广告返回回调
			public void onDomobAdReturned(DomobAdView adView) {
				Log.i("DomobSDKDemo", "onDomobAdReturned");
			}

			@Override
			// Landing Page成功打开回调
			public void onDomobAdOverlayPresented(DomobAdView adView) {
				Log.i("DomobSDKDemo", "overlayPresented");
			}

			@Override
			// Landing Page关闭回调
			public void onDomobAdOverlayDismissed(DomobAdView adView) {
				Log.i("DomobSDKDemo", "Overrided be dismissed");
			}

			@Override
			// 广告点击回调
			public void onDomobAdClicked(DomobAdView arg0) {
				Log.i("DomobSDKDemo", "onDomobAdClicked");
			}

			@Override
			// 广告请求失败回调
			public void onDomobAdFailed(DomobAdView arg0, ErrorCode arg1) {
				// TODO Auto-generated method stub
				Log.i("DomobSDKDemo", "onDomobAdFailed");
			}

			@Override
			// 离开应用回调
			public void onDomobLeaveApplication(DomobAdView arg0) {
				Log.i("DomobSDKDemo", "onDomobLeaveApplication");
			}

			@Override
			// 返回当前的Context
			public Context onDomobAdRequiresCurrentContext() {
				return WebViewActivity.this;
			}
		});
		// 将广告View增加到视图中。
		mAdContainer.addView(mAdview320x50);

		autoTimer.schedule(timerTask, 3000, 10000);*/
	}

	/*Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 100:
				if (isBaiduAD) {
					adView.setVisibility(View.VISIBLE);
					mAdContainer.setVisibility(View.GONE);

				} else {
					mAdContainer.setVisibility(View.VISIBLE);
					adView.setVisibility(View.GONE);
				}
				break;
			}

		}

	};
	Timer autoTimer = new Timer();
	TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = 100;
			isBaiduAD = !isBaiduAD;
			handler.sendMessage(msg);
		}
	};

	class MyWebViewClient extends WebViewClient {

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// mProgress.setVisibility(View.VISIBLE);
			super.onPageStarted(view, url, favicon);
		}

		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);

			return true;
		}

		public void onPageFinished(WebView view, String url) {
			if (mProgress.getVisibility() == 0) {
				mProgress.setVisibility(View.GONE);
			}
		}

		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// Toast.makeText(WebViewActivity.this, "网页加载出错！",
			// Toast.LENGTH_LONG)
			// .show();

		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if (flat.equals("sinaWeibo")) {
			return false;
		} else {
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId())// 得到被点击的item的itemId
		{
		case R.id.menu_new:// 这里的Id就是布局文件中定义的Id，在用R.id.XXX的方法获取出来
			webview.loadUrl("http://m.kl688.com/");
			webview.setWebViewClient(new MyWebViewClient());
			break;
		case R.id.menu_hot:
			webview.loadUrl("http://m.kl688.com/diggjokes/");
			webview.setWebViewClient(new MyWebViewClient());
			break;
		case R.id.menu_xiaodian:
			webview.loadUrl("http://m.kl688.com/tags/");
			webview.setWebViewClient(new MyWebViewClient());
			break;
		}
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (webview.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
			webview.goBack(); // goBack()表示返回webView的上一页面

			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
*/
}