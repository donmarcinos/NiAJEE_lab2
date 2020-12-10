package com.lab.movie;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;

@RequestScoped
@Named
public class MovieList {
	
	private final MovieService movieService;
	
	@Getter
    private Movie movie;
	
	@Inject 
	public MovieList(MovieService movieService) {
		this.movieService = movieService;
	}
	
	public List<Movie> getMovies() {
		return movieService.getAllMovies();
	}
	
	public void deleteAction(Long id) {
		movieService.deleteMovie(id);
	}
}
