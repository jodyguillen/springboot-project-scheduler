package io.sparkblitz.sps.repositories;

import io.sparkblitz.sps.domain.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
