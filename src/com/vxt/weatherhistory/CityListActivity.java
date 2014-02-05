package com.vxt.weatherhistory;

import java.util.ArrayList;
import java.util.List;
import com.vxt.weatherhistory.R;
import com.vxt.customadapter.CityListArrayAdapter;
import com.vxt.model.Model;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CityListActivity extends Activity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.city_list_activity);

		listView = (ListView) findViewById(R.id.listView1);
		
		TextView textView = new TextView(this);
		textView.setText("City List");
		textView.setTextSize(20);
		textView.setGravity(Gravity.CENTER);

		listView.addHeaderView(textView);

		ArrayAdapter<Model> adapter = new CityListArrayAdapter(
				CityListActivity.this, getModel());
		
		

		listView.setAdapter(adapter);
		
		

	}

	
	private List<Model> getModel() {
		List<Model> list = new ArrayList<Model>();
		list.add(get("Delhi"));
		list.add(get("Mumbai"));
		list.add(get("Pune"));
		list.add(get("Indore"));
		list.add(get("Nagpur"));
		list.add(get("Ahmedabad"));
		list.add(get("Udaipur"));
		list.add(get("Jaipur"));
		list.add(get("Bhopal"));
		list.add(get("Kolkata"));
		list.add(get("Jodhpur"));

		// Initially select one of the items
		// list.get(0).setSelected(true);
		return list;
	}

	private Model get(String s) {
		return new Model(s);
	}

}
