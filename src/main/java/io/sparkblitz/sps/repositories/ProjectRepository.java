package io.sparkblitz.sps.repositories;

import io.sparkblitz.sps.models.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
}