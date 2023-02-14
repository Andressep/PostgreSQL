package com.trainee.PostgreSQL.repository;

import com.trainee.PostgreSQL.models.Model;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends CrudRepository<Model, Integer> {
}
