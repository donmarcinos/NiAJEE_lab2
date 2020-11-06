package com.lab.screeningroom;

import com.lab.repository.Repository;
import com.lab.store.DataStore;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependent
public class ScreeningRoomRepository implements Repository<ScreeningRoom, Integer> {

    private DataStore store;

    @Inject
    public ScreeningRoomRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<ScreeningRoom> find(Integer number) {
        return store.findScreeningRoom(number);
    }

    @Override
    public List<ScreeningRoom> findAll() {
        return store.findAllScreeningRooms();
    }

    @Override
    public void create(ScreeningRoom entity) {
        store.createScreeningRoom(entity);
    }

    @Override
    public void delete(ScreeningRoom entity) {
        store.deleteScreeningRoom(entity.getNumber());
    }

    @Override
    public void update(ScreeningRoom entity) {
        store.updateScreeningRoom(entity);
    }

}
