package com.example.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.dao.DDIPairDao;
import com.example.dao.ObjectClassDao;
import com.example.dao.ObjectItemDao;
import com.example.dao.PercipitantClassDao;
import com.example.dao.PercipitantItemDao;
import com.example.model.DDIPair;
import com.example.model.ObjectClass;
import com.example.model.ObjectItem;
import com.example.model.PercipitantClass;
import com.example.model.PercipitantItem;
import com.example.model.Status;

public class DisplayDDI extends Activity {
	
	private DDIPairDao dao;
	private ObjectItemDao objectItemDao;
	private PercipitantItemDao percipitantItemDao;
	private PercipitantClassDao percipitantClassDao;
	private ObjectClassDao objectClassDao;
	EditText name;
	EditText info;
	EditText objectItems;
	EditText percipitantItems;
	EditText objectClass;
	EditText percipitantClass;
	long id_To_Update = 0;

	ObjectClass oc;
	PercipitantClass pc;
	String initName;
	String initInfo;
	String initObjectClass;
	String initPercipitantClass;
	String initObjectItems;
	String initPercipitantItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ddi_display);

		Button b = (Button) findViewById(R.id.button2);

		name = (EditText) findViewById(R.id.editTextDDIName);
		info = (EditText) findViewById(R.id.editTextInfo);
		objectItems = (EditText) findViewById(R.id.editTextObjectItem);
		percipitantItems = (EditText) findViewById(R.id.editTextPercipitantItem);
		objectClass = (EditText) findViewById(R.id.editTextObjectClass);
		percipitantClass = (EditText) findViewById(R.id.editTextPercipitantClass);

		dao = new DDIPairDao(this);
		objectItemDao = new ObjectItemDao(this);
		percipitantItemDao = new PercipitantItemDao(this);
		percipitantClassDao = new PercipitantClassDao(this);
		objectClassDao = new ObjectClassDao(this);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Long Value = extras.getLong("id");
			if (Value > 0) {
				DDIPair ddi = dao.getDDIPairById(Value);
				id_To_Update = Value;
				String nam = ddi.getName();
				String inf = ddi.getStatus().toString();

				initName = nam;
				initInfo = inf;

				if (!ddi.getMyDdi()) {
					b.setVisibility(View.INVISIBLE);

					name.setEnabled(false);
					name.setFocusableInTouchMode(false);
					name.setClickable(false);

					info.setEnabled(false);
					info.setFocusableInTouchMode(false);
					info.setClickable(false);

					objectItems.setEnabled(false);
					objectItems.setFocusableInTouchMode(false);
					objectItems.setClickable(false);

					percipitantItems.setEnabled(false);
					percipitantItems.setFocusableInTouchMode(false);
					percipitantItems.setClickable(false);

					objectClass.setEnabled(false);
					objectClass.setFocusableInTouchMode(false);
					objectClass.setClickable(false);

					percipitantClass.setEnabled(false);
					percipitantClass.setFocusableInTouchMode(false);
					percipitantClass.setClickable(false);
				}

				name.setText(nam);

				info.setText(inf);
				info.setEnabled(false);
				info.setFocusableInTouchMode(false);
				info.setClickable(false);
				
				
				oc = objectClassDao.getObjectClassByDDIPairId(String
						.valueOf(Value));

				if (oc != null) {
					objectClass.setText(initObjectClass = oc.getName());
				}

				pc = percipitantClassDao.getPercipitantClassByDDIPairId(String
						.valueOf(Value));
				if (pc != null) {
					percipitantClass.setText(initPercipitantClass = pc
							.getName());
				}

				objectItems.setText(initObjectItems = objectItemDao
						.getObjectItemsByDDIIdAsString(String.valueOf(Value)));
				percipitantItems
						.setText(initPercipitantItems = percipitantItemDao
								.getPercipitantItemByDDIPairIdAsString(String
										.valueOf(Value)));

			}

		}
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	public void run(View view) {
		Date date = new Date();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Long Value = extras.getLong("id");
			if (Value != null) {
				boolean updated = false;
				if (!initName.equals(name.getText().toString())
						|| !initInfo.equals(info.getText())) {
					updated = dao.update(Value, name.getText().toString(), info
							.getText().toString());
				}
				if (!initObjectClass.equals(objectClass.getText().toString())) {
					updated = objectClassDao.update(oc.getId(), objectClass
							.getText().toString());
				}
				if (!initPercipitantClass.equals(percipitantClass.getText()
						.toString())) {
					updated = percipitantClassDao.update(pc.getId(),
							percipitantClass.getText().toString());
				}

				if (!initObjectItems.equals(objectItems.getText().toString())) {
					String[] initArray = initObjectItems.split(";");
					String[] array = objectItems.getText().toString()
							.split(";");

					for (int i = 0; i < array.length; i++) {
						boolean found = false;
						for (int j = 0; j < initArray.length; j++) {
							if (array[i].trim().equalsIgnoreCase(
									initArray[j].trim())) {
								found = true;
							}
						}
						if (!found) {
							ObjectItem objectItem = new ObjectItem();
							objectItem.setId(String.valueOf(date.getTime()));
							objectItem.setName(array[i].trim());
							objectItemDao.insert(objectItem);
							objectClassDao.addObjectItem(oc, objectItem);
						}
					}
					for (int i = 0; i < initArray.length; i++) {
						boolean found = false;
						for (int j = 0; j < array.length; j++) {
							if (array[j].trim().equalsIgnoreCase(
									initArray[i].trim())) {
								found = true;
							}
						}
						if (!found) {
							objectClassDao.removeObjectItem(oc, objectItemDao
									.getObjectItemByName(initArray[i].trim()));
						}
					}
				}

				if (!initPercipitantItems.equals(percipitantItems.getText()
						.toString())) {
					String[] initArray = initPercipitantItems.split(";");
					String[] array = percipitantItems.getText().toString()
							.split(";");

					for (int i = 0; i < array.length; i++) {
						boolean found = false;
						for (int j = 0; j < initArray.length; j++) {
							if (array[i].trim().equalsIgnoreCase(
									initArray[j].trim())) {
								found = true;
							}
						}
						if (!found) {
							PercipitantItem percipitantItem = new PercipitantItem();
							percipitantItem
									.setId(String.valueOf(date.getTime()));
							percipitantItem.setName(array[i].trim());
							percipitantItemDao.insert(percipitantItem);
							percipitantClassDao.addPercipitantItem(pc,
									percipitantItem);
						}
					}
					for (int i = 0; i < initArray.length; i++) {
						boolean found = false;
						for (int j = 0; j < array.length; j++) {
							if (array[j].trim().equalsIgnoreCase(
									initArray[i].trim())) {
								found = true;
							}
						}
						if (!found) {
							percipitantClassDao
									.removePercipitantItem(
											pc,
											percipitantItemDao
													.getPercipitantItemByName(initArray[i]
															.trim()));
						}
					}
				}

				if (updated) {
					Toast.makeText(getApplicationContext(), "aktualisiert",
							Toast.LENGTH_SHORT).show();
				}
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
			}
		} else {
			DDIPair ddiPair = new DDIPair();
			ddiPair.setId(String.valueOf(date.getTime()));
			ddiPair.setName(name.getText().toString());
			Status status = null;
			if ("KLINISCH_SIGNIFIKANTE_INTERAKTION".equals(info.getText().toString())) {
				status = Status.KLINISCH_SIGNIFIKANTE_INTERAKTION;
			} else if ("DELETED".equals(info.getText().toString())) {
				status = Status.DELETED;
			} else {
				status = Status.EIGENE_INTERAKTION;
			}
			ddiPair.setStatus(status);
			ddiPair.setMyDdi(true);
			ObjectClass objectClass = new ObjectClass();
			date = new Date();
			objectClass.setId(String.valueOf(date.getTime()));
			objectClass.setName(this.objectClass.getText().toString());
			PercipitantClass percipitantClass = new PercipitantClass();
			date = new Date();
			percipitantClass.setId(String.valueOf(date.getTime()));
			percipitantClass
					.setName(this.percipitantClass.getText().toString());

			String[] objectItemsArray = objectItems.getText().toString()
					.split(";");
			String[] percipitantItemsArray = percipitantItems.getText()
					.toString().split(";");
			List<ObjectItem> objectItemList = new ArrayList<ObjectItem>();
			for (int i = 0; i < objectItemsArray.length; i++) {
				date = new Date();
				ObjectItem objectItem = new ObjectItem();
				objectItem.setId(String.valueOf(date.getTime()));
				objectItem.setName(objectItemsArray[i]);
				objectItemDao.insert(objectItem);
				objectItemList.add(objectItem);
			}
			objectClass.setObjectItems(objectItemList);

			List<PercipitantItem> percipitantItemList = new ArrayList<PercipitantItem>();
			for (int i = 0; i < percipitantItemsArray.length; i++) {
				date = new Date();
				PercipitantItem percipitantItem = new PercipitantItem();
				percipitantItem.setId(String.valueOf(date.getTime()));
				percipitantItem.setName(percipitantItemsArray[i]);
				percipitantItemDao.insert(percipitantItem);
				percipitantItemList.add(percipitantItem);
			}
			percipitantClass.setPercipitantItems(percipitantItemList);

			ddiPair.setObjectClass(objectClass);
			ddiPair.setPercipitantClass(percipitantClass);

			if (dao.insert(ddiPair)) {
				Toast.makeText(getApplicationContext(),"Interaktion wurde angelegt",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "Fehler aufgetreten",
						Toast.LENGTH_SHORT).show();
			}
			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(intent);
		}
	}
}
