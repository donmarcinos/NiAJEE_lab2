package com.lab.store;

import com.lab.movie.Movie;
import com.lab.screeningroom.ScreeningRoom;
import com.lab.screeningroom.RoomType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DataStore {
	
	private static ArrayList<Movie> movies =
            new ArrayList<>(Arrays.asList(
                    new Movie(1, "Forrest Gump", LocalDate.of(1994, 11, 4), "aaa", 142),
                    new Movie(2, "Halloween", LocalDate.of(1978, 10, 25), "bbb", 102),
                    new Movie(3, "La La Land", LocalDate.of(2017, 1, 6), "ccc", 128),
                    new Movie(4, "The Godfather", LocalDate.of(1972, 12, 31), "ddd", 178)));
	
	private static ArrayList<ScreeningRoom> screeningRooms =
            new ArrayList<>(Arrays.asList(
                    new ScreeningRoom(1, "", RoomType.IMAX, Long.valueOf(1)),
                    new ScreeningRoom(2, "", RoomType.REGULAR, Long.valueOf(1)),
                    new ScreeningRoom(3, "", RoomType.REGULAR, Long.valueOf(1)),
                    new ScreeningRoom(4, "", RoomType.IMAX, Long.valueOf(2)),
                    new ScreeningRoom(5, "", RoomType.IMAX, Long.valueOf(2)),
                    new ScreeningRoom(6, "", RoomType.REGULAR, Long.valueOf(2)),
                    new ScreeningRoom(7, "", RoomType.IMAX, Long.valueOf(3)),
                    new ScreeningRoom(8, "", RoomType.REGULAR, Long.valueOf(3)),
                    new ScreeningRoom(9, "", RoomType.REGULAR, Long.valueOf(4)),
                    new ScreeningRoom(10, "", RoomType.IMAX, Long.valueOf(4))));
	
	public synchronized List<Movie> findAllMovies() {
		return movies;
	}
	
	public Optional<Movie> findMovie(long id) {
		return movies.stream().
				filter(movie -> movie.getMovieID() == id)
				.findFirst();
	}
	
	public synchronized void createMovie(Movie movie) throws IllegalArgumentException {
		movie.setMovieID(findAllMovies().stream().mapToLong(Movie::getMovieID).max().orElse(0) + 1);
		movies.add(movie);
	}
	
	public synchronized void updateMovie(Movie movie) throws IllegalArgumentException {
        findMovie(movie.getMovieID()).ifPresentOrElse(
                original -> {
                    movies.remove(original);
                    movies.add(movie);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The movie with id \"%d\" does not exist", movie.getMovieID()));
                });
    }
	
	public synchronized void deleteMovie(Long id) throws IllegalArgumentException {
        findMovie(id).ifPresentOrElse(
                original -> movies.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The movie with id \"%d\" does not exist", id));
                });
    }
	
	public List<ScreeningRoom> findAllScreeningRooms() {
		return screeningRooms;
	}
	
	public Optional<ScreeningRoom> findScreeningRoom(int number) {
		return screeningRooms.stream().
				filter(screeningRoom -> screeningRoom.getNumber() == number)
				.findFirst();
	}
	
	public synchronized void createScreeningRoom(ScreeningRoom screeningRoom) throws IllegalArgumentException {
		findScreeningRoom(screeningRoom.getNumber()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The screening room number \"%d\" is not unique", screeningRoom.getNumber()));
                },
                () -> screeningRooms.add(screeningRoom));
	}
	
	public synchronized void updateScreeningRoom(ScreeningRoom screeningRoom) throws IllegalArgumentException {
        findMovie(screeningRoom.getNumber()).ifPresentOrElse(
                original -> {
                    screeningRooms.remove(original);
                    screeningRooms.add(screeningRoom);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The screening room with number \"%d\" does not exist", screeningRoom.getNumber()));
                });
    }
	
	public synchronized void deleteScreeningRoom(int number) throws IllegalArgumentException {
        findScreeningRoom(number).ifPresentOrElse(
                original -> screeningRooms.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The screening room with number \"%d\" does not exist", number));
                });
    }

}
