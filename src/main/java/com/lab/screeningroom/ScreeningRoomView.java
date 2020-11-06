package com.lab.screeningroom;

import java.io.IOException;


import java.io.Serializable;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RequestScoped
@Named
public class ScreeningRoomView implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ScreeningRoomService roomService;

	@Setter
    @Getter
    private String movieTitle;
	
	@Getter
    private ScreeningRoom room;
	
	@Getter
	@Setter
	private int number;
	
	@Inject
	public ScreeningRoomView(ScreeningRoomService roomService) {
		this.roomService = roomService;
	}
	
	public void init(int number) throws IOException {
        Optional<ScreeningRoom> room = roomService.getRoom(number);
        if (room.isPresent()) {
            this.room = room.get();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Room not found");
        }
    }
    
	public String execute(int id) throws IOException {
        Optional<ScreeningRoom> room = roomService.getRoom(id);
        if (room.isPresent()) {
            this.room = room.get();
            return "/singleRoom.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Room not found");
        }
        return "";
    }
	
	public String goToUpdate(int number) throws IOException {
		Optional<ScreeningRoom> room = roomService.getRoom(number);
        if (room.isPresent()) {
            this.room = room.get();
            return "/updateRoom.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Room not found");
        }
        return "";
	}
	
	public String goToAdd(String title) throws IOException {
        this.movieTitle = title;
        return "/addRoom.xhtml?faces-redirect=true";
	}

}
