package com.kevin.suishouji;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class HelpActivity extends Activity {
	private ViewFlipper flipper;
	private ImageView imageShow;
	private TextView txtShow;
	// 手势及相关变化动画
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private int currentIndex = 0;
	// private int maxIndex = 2;// 三个tip页需要maxIndex = 2
	Integer[] image = new Integer[] { R.drawable.tip1, R.drawable.tip2,
			R.drawable.tip3, R.drawable.tip4, R.drawable.tip5 };
	private int maxIndex = image.length;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.help);

		flipper = (ViewFlipper) findViewById(R.id.flipper);
		imageShow = (ImageView) findViewById(R.id.imageShow);
		txtShow = (TextView) findViewById(R.id.txtShow);
		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		};
		// 圈的动画效果
		slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
		slideLeftOut = AnimationUtils
				.loadAnimation(this, R.anim.slide_left_out);
		slideRightIn = AnimationUtils
				.loadAnimation(this, R.anim.slide_right_in);
		slideRightOut = AnimationUtils.loadAnimation(this,
				R.anim.slide_right_out);
		imageShow(0);
	}

	private void imageShow(int imgId) {
		imageShow.setBackgroundResource(image[imgId]);
		txtShow.setText(1 + imgId + "/" + maxIndex + "");
	}

	// 手势判断
	class MyGestureDetector extends SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (flipper.getVisibility() == View.VISIBLE) {

				try {
					if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
						return false;
					// right to left swipe
					if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
							&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
						flipper.setInAnimation(slideLeftIn);
						flipper.setOutAnimation(slideLeftOut);

						if (currentIndex == maxIndex) {
							// currentIndex = maxIndex;
							return false;
						} else {
							currentIndex = currentIndex + 1;
							// getCollectRequest();塞数据
							imageShow(currentIndex);
							System.gc();

							flipper.showNext();
							return true;
						}

					} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
							&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
						flipper.setInAnimation(slideRightIn);
						flipper.setOutAnimation(slideRightOut);

						if (currentIndex == 0) {
							// currentIndex =0;
							return false;
						} else {
							currentIndex = currentIndex - 1;
							// getCollectRequest();塞数据
							imageShow(currentIndex);
							System.gc();
							flipper.showPrevious();
							return true;
						}

					}
				} catch (Exception e) {
					// nothing
				}
			}
			return false;
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		super.dispatchTouchEvent(event);
		return gestureDetector.onTouchEvent(event);
	}

}
