package com.lab.movie;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.ToString;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class Movie {
	
	private long movieID;
	private String title;
	private LocalDate releaseDate;
	private String description;
	private int runtimeMins;
	
}
