package io.sparkblitz.sps.repositories;

import io.sparkblitz.sps.models.Activity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Activity, Integer> {
    List<Activity> findByProjectId(Integer projectId);
}
