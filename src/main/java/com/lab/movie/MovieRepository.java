package com.lab.movie;

import com.lab.store.DataStore;
import com.lab.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependent
public class MovieRepository implements Repository<Movie, Long> {

    private DataStore store;

    @Inject
    public MovieRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Movie> find(Long id) {
        return store.findMovie(id);
    }

    @Override
    public List<Movie> findAll() {
        return store.findAllMovies();
    }

    @Override
    public void create(Movie entity) {
        store.createMovie(entity);
    }

    @Override
    public void delete(Movie entity) {
        store.deleteMovie(entity.getMovieID());
    }

    @Override
    public void update(Movie entity) {
        store.updateMovie(entity);
    }

}
