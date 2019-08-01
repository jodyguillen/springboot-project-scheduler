package io.sparkblitz.sps.repositories;

import io.sparkblitz.sps.models.ActivityDependency;
import io.sparkblitz.sps.models.ActivityDependencyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDependencyRepository extends JpaRepository<ActivityDependency, ActivityDependencyId> {
}
