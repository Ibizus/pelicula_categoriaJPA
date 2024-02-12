package org.iesvdm.pelicula_categoriajpa;

import org.iesvdm.pelicula_categoriajpa.domain.Categoria;
import org.iesvdm.pelicula_categoriajpa.domain.Idioma;
import org.iesvdm.pelicula_categoriajpa.domain.Pelicula;
import org.iesvdm.pelicula_categoriajpa.repository.CategoriaRepository;
import org.iesvdm.pelicula_categoriajpa.repository.IdiomaRepository;
import org.iesvdm.pelicula_categoriajpa.repository.PeliculaRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PeliculaCategoriaJpaApplicationTests {

    @Autowired
    PeliculaRepository peliculaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    IdiomaRepository idiomaRepository;

    Idioma ingles;
    Pelicula pelicula1;

    @Test
    @Order(1)
    void contextLoads() {
    }


    @Test
    @Order(2)
    void guardarOneToMany(){

        ingles = new Idioma(0, "Ingles", new HashSet<>());
        idiomaRepository.save(ingles);

        pelicula1 = new Pelicula(0, "pelicula1", new HashSet<>(), new HashSet<>(), ingles);
        peliculaRepository.save(pelicula1);
    }

    @Test
    @Order(3)
    void guardarManyToMany(){

        pelicula1 = peliculaRepository.findById(1L).orElse(null);
        Categoria categoria1 = new Categoria(0, "categoria1", new HashSet<>());
        categoriaRepository.save(categoria1);

        Categoria categoria2 = new Categoria(0, "categoria2", new HashSet<>());
        categoriaRepository.save(categoria2);

        pelicula1.getCategorias().add(categoria1);
        categoria1.getPeliculas().add(pelicula1);
        pelicula1.getCategorias().add(categoria2);
        categoria2.getPeliculas().add(pelicula1);

        peliculaRepository.save(pelicula1);
        // GUARDO POR CATEGORIA PORQUE ES EL PROPIERTARIO (quien tiene mapped by)
        categoriaRepository.save(categoria1);
        categoriaRepository.save(categoria2);
    }




}
