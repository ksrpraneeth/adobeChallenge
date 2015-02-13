package com.ytkoff.api;

import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class ApiHandler {
	public static Context context;
	public static String TAG = "Web_service_error";

	public static ServerResponse responseActivity;

	public ApiHandler(Context context, Activity activity) {
		this.context = context;
		try {
			responseActivity = (ServerResponse) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement ServerResponseCallbacks.");
		}

	}

	public void requestServer(String url) {
		final ProgressDialog pDialog = new ProgressDialog(context);
		pDialog.setMessage("Loading...");
		pDialog.show();

		JsonArrayRequest req = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						responseActivity.onServerResponse(response.toString(),
								context);
						pDialog.hide();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						pDialog.hide();
					}
				});

		// Adding request to request queue
		ApiController.getInstance().addToRequestQueue(req, "JsonArray");
	}

	public static interface ServerResponse {
		void onServerResponse(String json, Context context);
	}
}
