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
	private int id;
	
	@Inject
	public ScreeningRoomView(ScreeningRoomService roomService) {
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

}
