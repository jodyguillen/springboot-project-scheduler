package io.sparkblitz.sps.repositories;

import io.sparkblitz.sps.models.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByProjectId(Integer projectId);
}
