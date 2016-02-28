package com.example.dao;

import android.content.Context;
import com.example.db.DBHelper;
 
public abstract class AbstractDao {
	protected DBHelper dbHelper;
	protected static Context context;
	public AbstractDao(Context context){
		dbHelper = new DBHelper(context);
		AbstractDao.context = context;
		
		
	}
}
