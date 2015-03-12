package com.kevin.suishouji;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends Activity {
	private EditText content;
	private Button button, back;
	private DBConnection helper;
	public int id_this;
	public String strTime;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit);
		content = (EditText) findViewById(R.id.content);
		button = (Button) findViewById(R.id.btnRight);
		back = (Button) findViewById(R.id.back);
		button.setText("修改");
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});

		helper = new DBConnection(this);
		final SQLiteDatabase db = helper.getReadableDatabase();

		id_this = Integer.parseInt(this.getIntent().getStringExtra("id"));
		Cursor cursor = db.query("content", new String[] { "_id", "title",
				"content", "writetime" }, "_id='" + id_this + "'", null, null,
				null, null);
		cursor.moveToFirst();
		String content1 = cursor.getString(2);
		cursor.close();

		content.setText(content1);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		strTime = formatter.format(curDate);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isEmpty()) {
					ContentValues values = new ContentValues();
					values.put("content", content.getText().toString());
					values.put("writetime", strTime);

					String where = "_id" + "=" + id_this;
					SQLiteDatabase db = helper.getWritableDatabase();
					db.update("content", values, where, null);
					db.close();

					finish();
					Toast toast = Toast.makeText(EditActivity.this, "编辑成功",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
		});
	}

	private boolean isEmpty() {
		if (content.getText() == null
				|| content.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(),
					this.getText(R.string.contentnull), Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		return true;
	}

}
