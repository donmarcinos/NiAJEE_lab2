package com.lab.screeningroom;

import com.lab.movie.Movie;
import com.lab.movie.MovieRepository;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApplicationScoped
@NoArgsConstructor
@Named

public class ScreeningRoomService {
	
	private ScreeningRoomRepository roomRepository;
	
	private MovieRepository movieRepository;
	
	@Getter
	@Setter
	private int number;
	
	@Getter
	@Setter
	private String description;
	
	@Getter
	@Setter
	private RoomType type;
	
	@Getter
	@Setter
	private Long movieID;
	
	@Inject
	public ScreeningRoomService(ScreeningRoomRepository roomRepository, MovieRepository movieRepository) {
		this.roomRepository = roomRepository;
		this.movieRepository = movieRepository;
	}
	
	
	public Optional<ScreeningRoom> getRoom(int number) {
		return roomRepository.find(number);
	}
	
	public void deleteRoom(int roomNumber) {
		roomRepository.delete(roomRepository.find(roomNumber).orElseThrow());
	}
	
	public void updateRoom(int roomNumber) {
		roomRepository.update(roomRepository.find(roomNumber).orElseThrow());
	}
	
	public void addRoom(Long movieID) throws IOException {
		
		ScreeningRoom entity = new ScreeningRoom();
		Optional<Movie> movie = movieRepository.find(movieID);
		
		if (movie.isPresent()) {
			entity.setNumber(this.number);
			entity.setDescription(this.description);
			entity.setType(this.type);
			entity.setMovieID(movieID);
			
			roomRepository.create(entity);
		} else {
            FacesContext.getCurrentInstance().getExternalContext()
            .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
		}
	}
	
	public List<ScreeningRoom> getScreeningRoomByMovie(Long id) {
		List<ScreeningRoom> list = roomRepository.findAll().stream().filter(room -> room.getMovieID().equals(id)).collect(Collectors.toList());
		return list;
	}

}
