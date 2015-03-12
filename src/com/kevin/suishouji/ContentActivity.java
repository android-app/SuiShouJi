package com.kevin.suishouji;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ContentActivity extends Activity {
	private TextView txttime, txtcontent;
	private Button back, btnRight;
	private String content;

	DBConnection helper;
	public int id_this;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.content);

		back = (Button) findViewById(R.id.back);
		btnRight = (Button) findViewById(R.id.btnRight);
		txttime = (TextView) findViewById(R.id.txttime);
		txtcontent = (TextView) findViewById(R.id.txtcontent);

		btnRight.setText("分享");
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		txttime.setText(null);
		txtcontent.setText(null);
		helper = new DBConnection(this);
		final SQLiteDatabase db = helper.getReadableDatabase();

		id_this = Integer.parseInt(this.getIntent().getStringExtra("id"));
		Cursor cursor = db.query("content", new String[] { "_id", "title",
				"content", "writetime" }, "_id='" + id_this + "'", null, null,
				null, null);
		cursor.moveToFirst();
		content = cursor.getString(2);
		String writetime = cursor.getString(3);
		cursor.close();

		txttime.setText(writetime);
		txtcontent.setText("        " + Html.fromHtml(content) + "");
		db.close();
		btnRight.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
				intent.putExtra(Intent.EXTRA_TEXT, content);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(Intent.createChooser(intent, getTitle()));
			}
		});
	}

}
