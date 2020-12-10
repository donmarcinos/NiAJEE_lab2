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
public class ScreeningRoomDelete {
	@Getter
	ScreeningRoom room;
	
	@Getter
	@Setter
	int id;
	
	MovieService movieService;
	
	ScreeningRoomService roomService;
	
	@Inject
	public ScreeningRoomDelete(MovieService movieService, ScreeningRoomService roomService) {
		this.movieService = movieService;
		this.roomService = roomService;
	}
	
	public void init() throws IOException {
        Optional<ScreeningRoom> room = roomService.getRoom(this.id);
        if (room.isPresent()) {
            this.room = room.get();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Room not found");
        }
    }
	
	public String deleteAction() {
		Optional<ScreeningRoom> room = roomService.getRoom(this.id);
        if (room.isPresent()) {
    		roomService.deleteRoom(room.get());
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
