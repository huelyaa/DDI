package com.example.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import com.example.db.DBInit;

public class MainActivity extends FragmentActivity implements OnClickListener {

	public static final String PREFS_NAME = "MyPrefsFile1";
	public CheckBox dontShowAgain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DBInit dbInit = new DBInit(this);
		setContentView(R.layout.activity_main);

		Button arzneimittellistButton = (Button) findViewById(R.id.Mein_eigene_Arzneimittelliste);
		arzneimittellistButton.setOnClickListener(this);

		Button liste = (Button) findViewById(R.id.Arzneimittelliste);
		liste.setOnClickListener(this);

		Button btn = (Button) findViewById(R.id.btn_search);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callIntent();
			}
		});
	}

	public void callIntent() {
		Intent intent = new Intent(this, AndroidListViewFilterActivity.class);
		intent.setData(getIntent().getData());
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.item1) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.Mein_eigene_Arzneimittelliste) {
			Intent intent = new Intent(this, DisplayDDI.class);
			startActivity(intent);
		} else if (id == R.id.Arzneimittelliste) {
			Intent intent = new Intent(this, Liste.class);
			startActivity(intent);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		LayoutInflater adbInflater = LayoutInflater.from(this);
		View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
		dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
		adb.setView(eulaLayout);
		adb.setTitle("Information Über App");
		adb.setMessage(Html
				.fromHtml("Es wird für die inhaltliche Richtigkeit, Vollständigkeit oder Aktualität der Inhalte keine Gewähr übernommen."
						+ " Eine Haftung für eventuelle Schäden, gleich welcher Art, wird ausgeschlossen. "
						+ "Wenn Sie OK drücken, sind Sie damit einverstanden."));
		adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String checkBoxResult = "NOT checked";
				if (dontShowAgain.isChecked())
					checkBoxResult = "checked";
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("skipMessage", checkBoxResult);
				editor.commit();
				return;
			}
		});

		adb.setNegativeButton("App verlassen", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String checkBoxResult = "NOT checked";
				if (dontShowAgain.isChecked())
					checkBoxResult = "checked";
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("skipMessage", checkBoxResult);
				editor.commit();

				System.exit(0);
				return;
			}
		});
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		String skipMessage = settings.getString("skipMessage", "NOT checked");
		if (!skipMessage.equals("checked"))
			adb.show();

		super.onResume();
	}

}