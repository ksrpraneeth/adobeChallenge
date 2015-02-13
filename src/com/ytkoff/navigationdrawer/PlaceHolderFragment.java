package com.ytkoff.navigationdrawer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.ytkoff.api.ApiController;
import com.ytkoff.parsers.AdobeProducts;
import com.ytkoff.pricecompare.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceHolderFragment extends Fragment {
	ListView productlistview;
	ArrayList<AdobeProducts> adobeProductList;
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlaceHolderFragment newInstance(
			ArrayList<AdobeProducts> productlist) {
		PlaceHolderFragment fragment = new PlaceHolderFragment();

		Bundle args = new Bundle();
		args.putSerializable("product", productlist);
		fragment.setArguments(args);
		return fragment;
	}

	public PlaceHolderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		productlistview = (ListView) rootView.findViewById(R.id.productlist);
		productlistview.setAdapter(new CustomAdapter(adobeProductList));
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		adobeProductList = (ArrayList<AdobeProducts>) getArguments()
				.getSerializable("product");
	}

	class CustomAdapter extends BaseAdapter {
		ImageLoader imageLoader = ApiController.getInstance().getImageLoader();
		private LayoutInflater inflater;
		ArrayList<AdobeProducts> adobeProductsList;

		public CustomAdapter(ArrayList<AdobeProducts> adobeProductList) {
			// TODO Auto-generated constructor stub
			this.adobeProductsList = adobeProductList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return adobeProductsList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (inflater == null)
				inflater = (LayoutInflater) getActivity().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null)
				convertView = inflater
						.inflate(R.layout.product_list_item, null);

			if (imageLoader == null)
				imageLoader = ApiController.getInstance().getImageLoader();
			final ImageView thumbNail = (ImageView) convertView
					.findViewById(R.id.thumbnail);
			TextView name = (TextView) convertView.findViewById(R.id.name);
			RatingBar rating = (RatingBar) convertView
					.findViewById(R.id.rating);

			TextView inapppurchase = (TextView) convertView
					.findViewById(R.id.inapppurchase);
			name.setText(adobeProductsList.get(position).getName());
			if (adobeProductsList.get(position).getInAppPurchases().toString()
					.equals("NO")) {
				inapppurchase.setText("Free");
			} else {
				inapppurchase.setText("In-app purchases");
			}
			rating.setRating(Float.valueOf(adobeProductsList.get(position)
					.getRating()));

			// As the image is in .webp format we are getting inputstream from
			// server and converting it into drawable.

			new AsyncTask<Integer, String, Drawable>() {

				@Override
				protected Drawable doInBackground(Integer... params) {
					// TODO Auto-generated method stub
					Drawable d = null;
					try {
						System.out.println("params 0" + params[0] );
						InputStream is = (InputStream) new URL(
								adobeProductsList.get(params[0]).getImage())
								.getContent();
						d = Drawable.createFromStream(is, "src name");

					} catch (IOException e) {
						e.printStackTrace();

					}
					return d;
				}

				@Override
				protected void onPostExecute(Drawable result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					thumbNail.setImageDrawable(result);
				}

			}.execute(Integer.valueOf(position));

			System.out.println(adobeProductsList.get(position).getImage());
			return convertView;
		}
	}
}
