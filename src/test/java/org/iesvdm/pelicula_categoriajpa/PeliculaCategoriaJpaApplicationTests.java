package org.iesvdm.pelicula_categoriajpa;

import org.iesvdm.pelicula_categoriajpa.domain.Categoria;
import org.iesvdm.pelicula_categoriajpa.domain.Idioma;
import org.iesvdm.pelicula_categoriajpa.domain.Pelicula;
import org.iesvdm.pelicula_categoriajpa.repository.CategoriaRepository;
import org.iesvdm.pelicula_categoriajpa.repository.PeliculaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

@SpringBootTest
class PeliculaCategoriaJpaApplicationTests {

    @Autowired
    PeliculaRepository peliculaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    @Test
    void contextLoads() {
    }


    @Test
    void guardarManyToMany(){

        Pelicula pelicula1 = new Pelicula(0, "pelicula1", new HashSet<>(), new HashSet<>(), new Idioma());
        peliculaRepository.save(pelicula1);

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
