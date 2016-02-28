package com.example.view;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.dao.DDIPairDao;

public class DDIActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		DDIPairDao dao = new DDIPairDao(this);
		LayoutInflater inflater = this.getLayoutInflater();
		setContentView(R.layout.activity_ddi);

		ListView listView = (ListView) findViewById(R.id.interactions);

		Intent intent = getIntent();

		String[] items = intent.getStringArrayExtra("items");

		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < items.length; i++) {
			for (int j = i + 1; j < items.length; j++) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("Item1", items[i]);
				map.put("Item2", items[j]);
				if (dao.checkIfInteractionExist(items[i], items[j]) != null) {
					map.put("Result", "Eine klinische signifikante Wechselwirkung ist vorhanden!");
				} else {
					map.put("Result", "Es besteht keine signifikante Wechselwirkung.");
				}
				mylist.add(map);
			}
		}
		SimpleAdapter mSchedule = new SimpleAdapter(this, mylist,
				R.layout.interaction_list, new String[] { "Item1", "Item2",
						"Result" }, new int[] { R.id.firstItem,
						R.id.secondItem, R.id.result });
		listView.setAdapter(mSchedule);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}