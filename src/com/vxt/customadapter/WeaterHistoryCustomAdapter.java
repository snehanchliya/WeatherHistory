package com.vxt.customadapter;

import java.util.List;

import com.vxt.weatherhistory.R;
import com.vxt.model.Main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeaterHistoryCustomAdapter extends BaseAdapter {
	
	LayoutInflater layoutInflater;
	List<Main> mainDataList;
	
	public WeaterHistoryCustomAdapter(Activity activity,List<Main> mainDataList){
		layoutInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.mainDataList=mainDataList;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mainDataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mainDataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		View vi = arg1;
		if (arg1 == null){
			vi = layoutInflater.inflate(R.layout.custom_weather_detail_list, null);
		}
		
		TextView date=(TextView)vi.findViewById(R.id.textView1);
		TextView temp=(TextView)vi.findViewById(R.id.textView3);
		TextView temp_min=(TextView)vi.findViewById(R.id.textView5);
		TextView temp_max=(TextView)vi.findViewById(R.id.textView7);
		
		//Log.e("date", mainDataList.get(arg0).getDate());
		//Log.e("temp", mainDataList.get(arg0).getTemp());
		date.setText(mainDataList.get(arg0).getDate());
		temp.setText(mainDataList.get(arg0).getTemp());
		temp_min.setText(mainDataList.get(arg0).getTemp_min());
		temp_max.setText(mainDataList.get(arg0).getTemp_max());
			
		return vi;
	}


}
