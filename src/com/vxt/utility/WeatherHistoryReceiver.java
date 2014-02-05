package com.vxt.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class WeatherHistoryReceiver extends AsyncTask<String, Void, JSONArray> {

	private ProgressDialog progressDialog;
	private Context context;
	
	public WeatherHistoryReceiver(ProgressDialog progressDialog,Context context) {
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.progressDialog=progressDialog;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		//progressDialog=new ProgressDialog(context);
		//progressDialog.setMessage("Loading...");
		//progressDialog.show();
	
	}
	
	@Override
	protected JSONArray doInBackground(String... weatherURL) {
		// TODO Auto-generated method stub
		
		//getJSONFromUrl(weatherURL[0]);
		return getJSONFromUrl(weatherURL[0]);
	}

	public JSONArray getJSONFromUrl(String url) {
		InputStream is = null;
		String json = null;
		JSONObject jObj;
		JSONArray data = null;
	    // Making HTTP request
	    try {
	        // defaultHttpClient
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpPost = new HttpGet(url);

	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        is = httpEntity.getContent();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    try {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                is), 8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	            
	        }
	        is.close();
	       // Log.e("data", String.valueOf(sb.toString().length()));
	        json = sb.toString();
	    } catch (Exception e) {
	        Log.e("Buffer Error", "Error converting result " + e.toString());
	    }

	    // try parse the string to a JSON object
	    try {	    	
	    	//if(json.length()<0)
	    	//Log.e("json", json);
	    	
	        jObj = new JSONObject(json);
	       		
	        //Log.e("data", jObj.toString());
	        
	        data = jObj.getJSONArray("list");
	        
	        
	        
	        //jObj=data.getJSONObject(0);
	        
	        //jObj= jObj.getJSONObject("main");
	        	        
	        //Log.e("Re", jObj.toString());
	        
	        
	    } catch (JSONException e) {
	        Log.e("JSON Parser", "Error parsing data " + e.toString());
	    }

	    // return JSON String
	    return data;
	}
	

}
