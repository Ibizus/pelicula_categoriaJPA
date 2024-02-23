package org.iesvdm.pelicula_categoriajpa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    private String nombre;

    @ManyToMany(mappedBy = "categorias", fetch = FetchType.EAGER)
    @JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id") // para poder hacer el Post sin peliculas
    @ToString.Exclude
    private Set<Pelicula> peliculas = new HashSet<>();

    // Transient se utiliza para añadir campos a la clase
    // e ignorarlos para la persistencia, Alternativa a DTO
//    @Transient
//    private int conteoPeliculas;
}
