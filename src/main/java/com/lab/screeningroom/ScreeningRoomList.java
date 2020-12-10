package com.lab.screeningroom;

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
public class ScreeningRoomList {
	
	private final ScreeningRoomService roomService;
	
	@Getter
    private ScreeningRoom room;
	
	@Inject 
	public ScreeningRoomList(ScreeningRoomService roomService) {
		this.roomService = roomService;
	}
	
	public List<ScreeningRoom> getRooms(Long id) {
		return roomService.getScreeningRoomByMovie(id);
	}
}
