package com.infobeans.moviecatlogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.infobeans.moviecatlogservice.model.CatalogItem;
import com.infobeans.moviecatlogservice.model.Movie;
import com.infobeans.moviecatlogservice.model.UserRating;

@RestController
@RequestMapping("/catlog")
public class MovieCatlogResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		// get all rated movie IDs
		// List<Rating> ratings = Arrays.asList(new Rating("1234", 4), new
		// Rating("5678", 3));

		UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingdata/users/" + userId,
				UserRating.class);
		;

		return ratings.getRatings().stream().map(rating -> {
			// For each movie ID, call Movie Info Service and get Details
			// Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" +
			// rating.getMovieId(), Movie.class);
			Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/" + rating.getMovieId())
					.retrieve().bodyToMono(Movie.class).block();
			// Put them all together
			return new CatalogItem(movie.getName(), "Desc", rating.getRating());

		}).collect(Collectors.toList());

//		return Collections.singletonList(new CatalogItem("Transformers", "Test",4));
	}

}