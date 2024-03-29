package org.iesvdm.pelicula_categoriajpa.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.iesvdm.pelicula_categoriajpa.domain.Categoria;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CategoriaDTO extends Categoria {

    // Se podría haber añadido el atributo al modelo
    // de Categoria con la anotación @Transient
    // pero ensucia el modelo y empeora si necesitamos más campos (no escalable)
    private int conteoPeliculas;

    public CategoriaDTO(Categoria categoria){
        super(categoria.getId(), categoria.getNombre(), categoria.getPeliculas());
        // Tiene que estar en EAGER la colección para hacer esto:
        this.conteoPeliculas = categoria.getPeliculas().size();
    }
}