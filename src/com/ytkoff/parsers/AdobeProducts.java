package com.ytkoff.parsers;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.http.entity.SerializableEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdobeProducts implements Serializable {
	String name;
	String type;
	String url;
	String image;
	String rating;
	String lastUpdated;
	String inAppPurchases;
	String description;

	public static String C_NAME = "name";
	public static String C_TYPE = "type";
	public static String C_URL = "url";
	public static String C_IMAGE = "image";
	public static String C_RATING = "rating";
	public static String C_LASTUPDATED = "last updated";
	public static String C_INAPPPURCHASES = "inapp-purchase";
	public static String C_DESCRIPTION = "description";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getInAppPurchases() {
		return inAppPurchases;
	}

	public void setInAppPurchases(String inAppPurchases) {
		this.inAppPurchases = inAppPurchases;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static ArrayList<AdobeProducts> parse(String json) {
		ArrayList<AdobeProducts> productsList = new ArrayList<AdobeProducts>();
		try {
			JSONArray productsarray = new JSONArray(json);

			for (int i = 0; i < productsarray.length(); i++) {
				JSONObject productObject = productsarray.getJSONObject(i);
				AdobeProducts products = new AdobeProducts();
				products.setName(productObject.getString(AdobeProducts.C_NAME));
				products.setDescription(productObject
						.getString(AdobeProducts.C_DESCRIPTION));
				products.setImage(productObject
						.getString(AdobeProducts.C_IMAGE));
				products.setInAppPurchases(productObject
						.getString(AdobeProducts.C_INAPPPURCHASES));
				products.setLastUpdated(productObject
						.getString(AdobeProducts.C_LASTUPDATED));
				products.setRating(productObject
						.getString(AdobeProducts.C_RATING));
				products.setType(productObject.getString(AdobeProducts.C_TYPE));
				products.setUrl(productObject.getString(AdobeProducts.C_URL));
				productsList.add(products);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return productsList;

	}
}
