package com.ytkoff.navigationdrawer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ytkoff.api.ApiHandler;
import com.ytkoff.api.ApiHandler.ServerResponse;
import com.ytkoff.api.Constants;
import com.ytkoff.parsers.AdobeProducts;
import com.ytkoff.pricecompare.R;
import com.ytkoff.utils.ConnectionDetector;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks, ServerResponse {

	/*
	 * Navigaition drawer list menu
	 */
	ArrayList<String> menu = new ArrayList<String>();
	ArrayList<AdobeProducts> productlist = new ArrayList<AdobeProducts>();
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (new ConnectionDetector(this).isConnectingToInternet()) {
			new ApiHandler(this, MainActivity.this).requestServer(Constants
					.getUrl());
		}
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = "Adobe";

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();

		ArrayList<AdobeProducts> tempProductList = new ArrayList<AdobeProducts>();
		if (productlist.size() > 0) {
			for (int i = 0; i < productlist.size(); i++) {
				if (productlist.get(i).getType().equals(menu.get(position))) {
					tempProductList.add(productlist.get(i));
				}
			}
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							PlaceHolderFragment.newInstance(tempProductList))
					.commit();
		}

	}

	public void onSectionAttached(int number) {
		if (menu.size() > 0) {
			mTitle = menu.get(number - 1);

		} else {
			mTitle = "Adobe";
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onServerResponse(String json, Context context) {
		// TODO Auto-generated method stub
		ArrayList<AdobeProducts> productlist = AdobeProducts.parse(json);

		ArrayList<String> menulist = new ArrayList<String>();
		if (productlist.size() > 0) {
			this.productlist = productlist;
			for (int i = 0; i < productlist.size(); i++) {
				menulist.add(productlist.get(i).getType());
			}
			Set<String> uniqueTypes = new HashSet<String>(menulist);
			menu.addAll(uniqueTypes);
			// Set up the drawer.
			mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
					(DrawerLayout) findViewById(R.id.drawer_layout), menu);
		} else {
			// display sorry error
		}

	}
}
