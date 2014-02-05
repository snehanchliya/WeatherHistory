package com.vxt.weatherhistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.vxt.weatherhistory.R;
import com.vxt.customadapter.WeaterHistoryCustomAdapter;
import com.vxt.model.Main;
import com.vxt.utility.WeatherHistoryReceiver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherHistoryActivity extends Activity {

	ListView listView;
	Spinner spinner;
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_history_activity);

		ArrayList<String> cityNames = null;
		try {
			Intent i = getIntent();

			cityNames = i.getStringArrayListExtra("CityNames");

			spinner = (Spinner) findViewById(R.id.spinner1);

			cityNames.add("Select City Name");

			Collections.reverse(cityNames);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, cityNames);

			spinner.setAdapter(adapter);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		listView = (ListView) findViewById(R.id.listView1);
		
		TextView textView = new TextView(this);
		textView.setText("Weather Report");
		textView.setTextSize(20);
		textView.setGravity(Gravity.CENTER);

		listView.addHeaderView(textView);

		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				if(!hasConnection()){
				Toast.makeText(getApplicationContext(), "Internet Not Available!", Toast.LENGTH_LONG).show();
				return;
				}
				// Toast.makeText(getApplicationContext(),spinner.getItemAtPosition(arg2).toString(),Toast.LENGTH_LONG).show();
				String cityName = spinner.getItemAtPosition(arg2).toString();
				if (arg2 == 0)
					return;

				final WeatherHistoryReceiver whr = new WeatherHistoryReceiver(
						progressDialog, WeatherHistoryActivity.this) {

					JSONObject jsonObject;
					ArrayList<Main> mainDataList = new ArrayList<Main>(5);

					@Override
					protected void onPostExecute(final JSONArray result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);

						Thread t = new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Main mdc;

								try {
									for (int i = 0; i < 400;) {
										mdc = new Main();
										jsonObject = result.getJSONObject(i);

										long unixSeconds = Long
												.parseLong(jsonObject.get("dt")
														.toString());
										Date date = new Date(
												unixSeconds * 1000L);
										SimpleDateFormat sdf = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss z");
										sdf.setTimeZone(TimeZone
												.getTimeZone("GMT-4"));
										String formattedDate = sdf.format(date);
										Log.e("Data",
												String.valueOf(formattedDate));

										jsonObject = jsonObject
												.getJSONObject("main");

										String temp = jsonObject
												.getString("temp");

										String temp_min = jsonObject
												.getString("temp_min");

										String temp_max = jsonObject
												.getString("temp_max");

										mdc.setDate(formattedDate);
										mdc.setTemp(temp);
										mdc.setTemp_min(temp_min);
										mdc.setTemp_max(temp_max);

										Log.e("Data", String.valueOf(temp));

										mainDataList.add(mdc);
										i = i + 24;
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									Log.e("Data",
											String.valueOf(e.getMessage()));
								}

								Log.e("Data List",
										String.valueOf(mainDataList.size()));

								WeatherHistoryActivity.this
										.runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												WeaterHistoryCustomAdapter weaterHistoryCustomAdapter = new WeaterHistoryCustomAdapter(
														WeatherHistoryActivity.this,
														mainDataList);

												listView.setAdapter(weaterHistoryCustomAdapter);

												if (progressDialog.isShowing())
													progressDialog.dismiss();
											}
										});

							}
						});

						t.start();

					}
				};

				whr.execute("http://api.openweathermap.org/data/2.5/history/city?q="
						+ cityName
						+ "&type=day&start=1356998400&end=1358640000&APPID=b43361fe97ad84105ec0ce8117bf490f&ctn=30");

				progressDialog = new ProgressDialog(WeatherHistoryActivity.this);
				progressDialog.setMessage("Loading...");
				progressDialog.show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}
	
	public boolean hasConnection() {
	    ConnectivityManager cm = (ConnectivityManager)this.getSystemService(
	        Context.CONNECTIVITY_SERVICE);

	    NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    if (wifiNetwork != null && wifiNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    if (mobileNetwork != null && mobileNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    if (activeNetwork != null && activeNetwork.isConnected()) {
	      return true;
	    }

	    return false;
	  }
	
	

}
