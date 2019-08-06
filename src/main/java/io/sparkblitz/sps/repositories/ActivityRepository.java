package io.sparkblitz.sps.repositories;

import io.sparkblitz.sps.domain.Activity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, String> {
    @Query(nativeQuery = true, value = "SELECT a.* FROM activity a INNER JOIN activity_next n on n.next = a.code WHERE n.activity_code= ?1")
    List<Activity> findNextByCode(String code);
}
