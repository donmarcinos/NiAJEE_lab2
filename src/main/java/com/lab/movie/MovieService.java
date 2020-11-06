package com.lab.movie;

import com.lab.screeningroom.ScreeningRoom;
import com.lab.screeningroom.ScreeningRoomRepository;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
@NoArgsConstructor
@Named
public class MovieService {
	
	private MovieRepository movieRepository;
	
	private ScreeningRoomRepository roomRepository;
	
	@Inject
	public MovieService(ScreeningRoomRepository roomRepository, MovieRepository movieRepository) {
		this.roomRepository = roomRepository;
		this.movieRepository = movieRepository;
	}
	
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}
	
	public Optional<Movie> getMovie(Long id) {
		return movieRepository.find(id);
	}
	
	public void deleteMovie(Long id) {
		Optional<Movie> movie = movieRepository.find(id);
		if (movie.isPresent()) {
			List<ScreeningRoom> rooms = roomRepository.findAll().stream().filter(room -> room.getMovieID().equals(id)).collect(Collectors.toList());
			for (ScreeningRoom entity : rooms) {
				roomRepository.delete(entity);
			}
		}
		movieRepository.delete(movieRepository.find(id).orElseThrow());
	}
}
