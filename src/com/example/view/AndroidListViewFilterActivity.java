package com.example.view;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dao.DDIPairDao;
import com.example.dao.ObjectItemDao;
import com.example.dao.PercipitantItemDao;
import com.example.model.Item;

public class AndroidListViewFilterActivity extends Activity {

	ArrayAdapter<String> dataAdapter = null;
	DDIPairDao ddiPairDao;
	PercipitantItemDao percipitantDao;
	List<String> itemNames;
	List<Item> selectedItems = new ArrayList<Item>();
	ObjectItemDao objectItemDao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_window);
		ddiPairDao = new DDIPairDao(this);
		percipitantDao = new PercipitantItemDao(this);
		objectItemDao = new ObjectItemDao(this);
		displayListView();

	}

	private void displayListView() {
		LayoutInflater inflater = this.getLayoutInflater();

		itemNames = new ArrayList<String>();

		itemNames.addAll(objectItemDao.getObjectItemNames(null));
		for (String name : percipitantDao.getPercipitantItemNames(null)) {
			if (!itemNames.contains(name)) {
				itemNames.add(name);
			}
		}

		dataAdapter = new ArrayAdapter<String>(this, R.layout.ddi_list,
				itemNames);
		ListView listView = (ListView) findViewById(R.id.listView1);

		ViewGroup listFooterView = (ViewGroup) inflater.inflate(
				R.layout.footer, listView, false);

		ViewGroup listHeaderView = (ViewGroup) inflater.inflate(
				R.layout.header, listView, false);

		listView.addFooterView(listHeaderView);
		listView.addHeaderView(listFooterView);

		Button btn = (Button) findViewById(R.id.btn_interaction);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callDDIActivity();
			}
		});

		
		listView.setAdapter(dataAdapter);
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String itemName = (String) ((TextView) view).getText();
				Toast.makeText(getApplicationContext(),
						itemName + " wurde in die Liste eingefügt.", Toast.LENGTH_SHORT)
						.show();
				Item item = null;
				if (objectItemDao.getObjectItemsByName(itemName).size() == 1) {
					item = objectItemDao.getObjectItemsByName(itemName).get(0);
				} else {
					item = percipitantDao.getPercipitantItemsByName(itemName)
							.get(0);
				}
				selectedItems.add(item);
				String footerText = "Wechselwirkungen zwischen: ";
				int j = 0;
				for (Item i : selectedItems) {
					footerText += i.getName();
					if (j++ != selectedItems.size() - 1) {
						footerText += ", ";
					}
				}
				TextView t = (TextView) findViewById(R.id.footer_text);
				t.setText(footerText);
			}
		});

		EditText myFilter = (EditText) findViewById(R.id.myFilter);
		myFilter.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				dataAdapter.clear();
				List<String> items = new ArrayList<String>();
				items.addAll(objectItemDao.getObjectItemNames(s.toString()));
				for (String name : percipitantDao.getPercipitantItemNames(s
						.toString())) {
					if (!items.contains(name)) {
						items.add(name);
					}
				}
				dataAdapter.addAll(items);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void callDDIActivity() {
		Bundle dataBundle = new Bundle();
		Intent intent = new Intent(this, DDIActivity.class);
		String[] itemsAsString = new String[selectedItems.size()];
		int i = 0;
		for (Item item : selectedItems) {
			itemsAsString[i++] = item.getName();
		}
		intent.putExtra("items", itemsAsString);
		intent.setData(getIntent().getData());
		startActivity(intent);
		finish();
	}
}