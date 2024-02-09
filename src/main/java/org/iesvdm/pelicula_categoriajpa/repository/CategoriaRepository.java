package org.iesvdm.pelicula_categoriajpa.repository;

import org.iesvdm.pelicula_categoriajpa.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {


}
