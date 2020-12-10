package com.lab.screeningroom;

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
public class ScreeningRoomUpdate {

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
	ScreeningRoom room;
	
	ScreeningRoomService roomService;
	
	@Inject
	public ScreeningRoomUpdate(ScreeningRoomService roomService) {
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
	
	public String updateAction() {
		Optional<ScreeningRoom> room = roomService.getRoom(this.id);
        if (room.isPresent()) {
    		room.get().setDescription(this.description);
    		room.get().setType(RoomType.valueOf(this.type));
    		roomService.updateRoom(room.get());
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
