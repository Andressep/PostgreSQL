package com.trainee.PostgreSQL.security.repository;

import com.trainee.PostgreSQL.security.models.NombreRol;
import com.trainee.PostgreSQL.security.models.Rol;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolRepository extends CrudRepository<Rol, Long> {
    Optional<Rol> findByNombreRol(NombreRol nombreRol);
}
