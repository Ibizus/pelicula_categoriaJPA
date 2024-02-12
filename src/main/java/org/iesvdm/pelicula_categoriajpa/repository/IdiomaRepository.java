package org.iesvdm.pelicula_categoriajpa.repository;

import org.iesvdm.pelicula_categoriajpa.domain.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
}
