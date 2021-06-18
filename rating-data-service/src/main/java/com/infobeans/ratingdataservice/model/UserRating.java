/**
 * 
 */
package com.infobeans.ratingdataservice.model;

import java.util.List;


/**
 * @author Nayan
 *
 */
public class UserRating {

	private List<Rating> ratings;

	public UserRating() {
		// TODO Auto-generated constructor stub
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

}
