package com.nominas.empresa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nominas.empresa.models.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByName(String name);
}