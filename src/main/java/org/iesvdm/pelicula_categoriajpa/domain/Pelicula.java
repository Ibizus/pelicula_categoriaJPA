package org.iesvdm.pelicula_categoriajpa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    private String titulo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference(value = "categorias")
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany
    @JsonManagedReference(value = "actores")
    private Set<Actor> actores = new HashSet<>();

    @ManyToOne
    private Idioma idioma_original;

}
