package com.tanvirchoudhury.robotichoover.repository;

import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment;
import org.springframework.data.repository.CrudRepository;

public interface UncleanEnvironmentRepository extends CrudRepository<UncleanEnvironment, Long> {
}
