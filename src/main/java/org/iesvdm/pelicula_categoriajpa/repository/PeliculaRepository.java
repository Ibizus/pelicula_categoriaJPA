package org.iesvdm.pelicula_categoriajpa.repository;

import org.iesvdm.pelicula_categoriajpa.domain.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {


}
