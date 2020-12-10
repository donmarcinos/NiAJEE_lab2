package com.lab.screeningroom;

import com.lab.movie.MovieService;
import com.lab.movie.Movie;

import java.io.IOException;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
public class ScreeningRoomAdd {
	
	@Getter
	@Setter
	private String description;
	
	@Getter
	@Setter
	private String type;
	
	@Getter
	@Setter
	int id;
	
	@Getter
	@Setter
	Long movieID;
	
	@Getter
	ScreeningRoom room;
	
	MovieService movieService;
	
	ScreeningRoomService roomService;
	
	@Inject
	public ScreeningRoomAdd(MovieService movieService, ScreeningRoomService roomService) {
		this.movieService = movieService;
		this.roomService = roomService;
	}
	
	public void init() throws IOException {
        Optional<Movie> movie = movieService.getMovie(this.movieID);
        if (movie.isPresent()) {
            this.movieID = movie.get().getMovieID();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Room not found");
        }
    }
	
	public String addAction() {
		Optional<ScreeningRoom> room = roomService.getRoom(this.id);
        if (!room.isPresent()) {
        	ScreeningRoom newRoom = new ScreeningRoom();
        	newRoom.setNumber(this.id);
        	newRoom.setDescription(this.description);
        	newRoom.setMovieID(this.movieID);
        	newRoom.setType(RoomType.valueOf(this.type));
    		try {
				roomService.addRoom(newRoom);
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else {
				try {
					FacesContext.getCurrentInstance().getExternalContext()
					        .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Room not found");
				} catch (IOException e) {
					e.printStackTrace();
				}
        }
        return "movies.xhtml?faces-redirect=true";
	}
}
