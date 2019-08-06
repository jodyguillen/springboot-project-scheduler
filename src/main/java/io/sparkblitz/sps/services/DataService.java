package io.sparkblitz.sps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DataService<T, ID, R extends CrudRepository<T, ID>> {

    @Autowired
    private CrudRepository<T, ID> repository;

    protected final R getMyRepository() {
        return (R) repository;
    }

    public T save(T e) {
        return repository.save(e);
    }

    public Optional<T> findById(ID id) {
        if(null == id) {
            return Optional.empty();
        }
        return repository.findById(id);
    }

    public void deleteById(ID id) {
        if(null == id) {
            return;
        }
        repository.deleteById(id);
    }

    public List<T> findAll() {
        List<T> records = new ArrayList<>();
        repository.findAll().forEach(records::add);
        return records;
    }
}
