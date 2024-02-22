package org.iesvdm.pelicula_categoriajpa.repository;

import org.iesvdm.pelicula_categoriajpa.domain.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaCustomRepository {

    public List<Categoria> queryCustomCategoria(Optional<String> buscarOptional, Optional<String> ordenarOptional);

    public int countCustomCategoria(Optional<String> buscarOptional);

}
