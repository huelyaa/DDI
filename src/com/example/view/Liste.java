package com.example.view;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.dao.DDIPairDao;
import com.example.model.DDIPair;

public class Liste extends Activity {

	private ListView obj;
	private DDIPairDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		dao = new DDIPairDao(this);
		final List<DDIPair> array_list = dao.getAll();
		List<String> ddiNames = new ArrayList<String>();
		for (DDIPair ddiPair : array_list) {
			ddiNames.add(ddiPair.getName());
		}

		ArrayAdapter arrayAdapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, ddiNames);

	
		obj = (ListView) findViewById(R.id.listView1);
		obj.setAdapter(arrayAdapter);

		
		obj.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Bundle dataBundle = new Bundle();
				dataBundle.putLong("id",
						Long.valueOf(array_list.get(arg2).getId()));

				Intent intent = new Intent(getApplicationContext(),
						DisplayDDI.class);
				intent.putExtras(dataBundle);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.item1:
			Intent intent = new Intent(getApplicationContext(),
					DisplayDDI.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}

	}

}