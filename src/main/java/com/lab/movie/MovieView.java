package com.lab.movie;

import java.io.IOException;



import java.io.Serializable;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
public class MovieView implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Setter
    @Getter
    private Long id;
	
	@Getter
    private Movie movie;
	
	private MovieService movieService;
	
	@Inject
	public MovieView(MovieService movieService) {
		this.movieService = movieService;
	}
    
	public String execute(Long id) throws IOException {
        Optional<Movie> movie = movieService.getMovie(id);
        if (movie.isPresent()) {
            this.movie = movie.get();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("mv", movie.get());
            return "/singleMovie.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
        }
        return "";
    }

}
