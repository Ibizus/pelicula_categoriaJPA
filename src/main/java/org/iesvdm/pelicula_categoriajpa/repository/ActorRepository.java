package org.iesvdm.pelicula_categoriajpa.repository;

import org.iesvdm.pelicula_categoriajpa.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
}
