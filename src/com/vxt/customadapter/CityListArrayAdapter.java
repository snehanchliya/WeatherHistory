package com.vxt.customadapter;

import java.util.ArrayList;
import java.util.List;
import com.vxt.weatherhistory.R;
import com.vxt.model.Model;
import com.vxt.weatherhistory.WeatherHistoryActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class CityListArrayAdapter extends ArrayAdapter<Model> {

	private final List<Model> list;
	private final Activity context;
	Button checkWeather;
	ArrayList<String> cityNames;
	public CityListArrayAdapter(final Activity context, List<Model> list) {
		super(context, R.layout.rowbuttonlayout, list);
		this.context = context;
		this.list = list;
		
		cityNames=new ArrayList<String>();		
		
		checkWeather=(Button)context.findViewById(R.id.checkWeather);
		
		checkWeather.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(cityNames.size()<1)
				{					
					Toast.makeText(context,"Please select city",Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent=new Intent(context,WeatherHistoryActivity.class);
				intent.putStringArrayListExtra("CityNames", cityNames);
				//Toast.makeText(context,String.valueOf(cityNames.size()),Toast.LENGTH_SHORT).show();
				context.startActivity(intent);				
			}
		});
	}
	
	

	static class ViewHolder {
		private TextView text;
		private CheckBox checkbox;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.rowbuttonlayout, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) view.findViewById(R.id.label);
			viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
			viewHolder.checkbox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Model element = (Model) viewHolder.checkbox.getTag();
							element.setSelected(buttonView.isChecked());
							
							if(isChecked)
							{
								cityNames.add(element.getName());
								//Toast.makeText(context,element.getName()+" Add",Toast.LENGTH_SHORT).show();
							}
							else
							{
							 cityNames.remove(element.getName());
							 //Toast.makeText(context,element.getName()+" Remove",Toast.LENGTH_SHORT).show();
							}
							
							
						}
					});
			
			view.setTag(viewHolder);
			viewHolder.checkbox.setTag(list.get(position));
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.text.setText(list.get(position).getName());
		holder.checkbox.setChecked(list.get(position).isSelected());
		return view;
	}
}