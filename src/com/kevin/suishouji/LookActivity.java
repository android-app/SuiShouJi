package com.kevin.suishouji;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LookActivity extends Activity {

	private ListView listView;
	private ImageView no;
	private Button back, btnRight;
	private DBConnection helper;
	private ContentAdapter adapter;
	private CharSequence[] listid, listtitle, listwtime, listContent;

	private int listId;
	private static final int ITEM1 = Menu.FIRST;
	private static final int ITEM2 = Menu.FIRST + 1;
	private static final int ITEM3 = Menu.FIRST + 2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.look);

		listView = (ListView) findViewById(R.id.listView1);
		btnRight = (Button) findViewById(R.id.btnRight);
		no = (ImageView) findViewById(R.id.no);
		back = (Button) findViewById(R.id.back);
		btnRight.setVisibility(View.GONE);
		back.setVisibility(View.GONE);

		helper = new DBConnection(this);
		data();// 数据
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(LookActivity.this,
						ContentActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putString("id", listid[arg2].toString());
				intent.putExtras(mBundle);
				startActivity(intent);
			}
		});
		// 长按显示菜单
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				menu.setHeaderIcon(R.drawable.logo);
				// menu.setHeaderTitle("随心记");
				menu.add(0, ITEM1, 0, "编辑");
				menu.add(0, ITEM2, 2, "删除");
				menu.add(0, ITEM3, 2, "取消");
			}
		});
		// 长按
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				listId = Integer.parseInt(listid[arg2].toString());
				listView.showContextMenu();

				return true;
			}
		});

	}

	private void data() {
		final SQLiteDatabase db = helper.getReadableDatabase();
		// 取得所有数据的title，放置在list[i]上
		Cursor cursor = db.query("content", new String[] { "_id", "title",
				"writetime", "content" }, null, null, null, null,
				"writetime desc");
		cursor.moveToFirst();

		listid = new CharSequence[cursor.getCount()];
		listtitle = new CharSequence[cursor.getCount()];
		listwtime = new CharSequence[cursor.getCount()];
		listContent = new CharSequence[cursor.getCount()];

		for (int i = 0; i < listid.length; i++) {
			listid[i] = cursor.getString(0);
			listtitle[i] = cursor.getString(1);
			listwtime[i] = cursor.getString(2);
			listContent[i] = cursor.getString(3);
			cursor.moveToNext();
		}
		if (cursor.getCount() < 1) {
			listView.setAdapter(null);
			no.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		} else {
			no.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			adapter = new ContentAdapter(this, listid.length);
			listView.setAdapter(adapter);
		}

		cursor.close();
		db.close();
	}

	// 菜单操作
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ITEM1:
			Intent intent = new Intent(LookActivity.this, EditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("id", listId + "");
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case ITEM2:
			new AlertDialog.Builder(LookActivity.this)
					.setIcon(R.drawable.logo)
					.setTitle("确定删除？")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									String where = "_id" + "=" + listId;
									SQLiteDatabase db = helper
											.getWritableDatabase();
									db.delete("content", where, null);
									db.close();
									data();
								}
							})
					.setNegativeButton("否",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();
			break;
		case ITEM3:
			break;
		}
		return super.onContextItemSelected(item);
	}

	private class ContentAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private int listRow;
		private Context context;

		ContentAdapter(Context _context, int _listRow) {

			this.context = _context;
			this.listRow = _listRow;

			this.mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return listRow;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			view = mInflater.inflate(R.layout.item, parent, false);

			final TextView time = (TextView) view.findViewById(R.id.time);
			final TextView content = (TextView) view.findViewById(R.id.content);
			String contentStr = listContent[position].toString();
			time.setText(listwtime[position].toString());
			if (contentStr.length() < 30) {
				content.setText(Html.fromHtml(contentStr
						+ "<font color=#008ff1>[更多]</font>"));
			} else {
				content.setText(Html.fromHtml(contentStr.substring(0, 29)
						+ "<font color=#008ff1>[更多]</font>"));
			}

			return view;
		}

	}

	protected void onResume() {
		super.onResume();
		data();
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
