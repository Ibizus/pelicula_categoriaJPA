package org.iesvdm.pelicula_categoriajpa.service;


import org.iesvdm.pelicula_categoriajpa.domain.Pelicula;
import org.iesvdm.pelicula_categoriajpa.exception.EntityNotFoundException;
import org.iesvdm.pelicula_categoriajpa.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;


    public List<Pelicula> all(){
        return this.peliculaRepository.findAll();
    }

    public Map<String, Object> all(int pagina, int tamanio){

        Pageable paginado = PageRequest.of(pagina, tamanio, Sort.by("id").ascending());
        Page<Pelicula> pageAll = this.peliculaRepository.findAll(paginado);

        Map<String, Object> response = new HashMap<>();

        response.put("peliculas", pageAll.getContent());
        response.put("currentpage", pageAll.getNumber());
        response.put("totalItems", pageAll.getTotalElements());
        response.put("totalPages", pageAll.getTotalPages());

        return response;
    }

    public Map<String, Object> allOrdered(String campo, String sentido){

        Pageable paginado;
        if(sentido.equals("desc")){
            paginado = PageRequest.of(0, 10, Sort.by(campo).descending());
        }else{
            paginado = PageRequest.of(0, 10, Sort.by(campo).ascending());
        }

        Page<Pelicula> pageAll = this.peliculaRepository.findAll(paginado);

        Map<String, Object> response = new HashMap<>();
        response.put("peliculas", pageAll.getContent());
        response.put("currentPage", pageAll.getNumber());
        response.put("totalItems", pageAll.getTotalElements());
        response.put("totalPages", pageAll.getTotalPages());
        return response;
    }

    public Map<String, Object> allOrdered(String campo1, String sentido1, String campo2, String sentido2){

        Sort ordenacion = Sort.by(
                Sort.Order.by(campo1).with(
                        sentido1.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC
                ),
                Sort.Order.by(campo2).with(
                        sentido2.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC
                )
        );

        Pageable paginado = PageRequest.of(0, 10, ordenacion);

        Page<Pelicula> pageAll = this.peliculaRepository.findAll(paginado);

        Map<String, Object> response = new HashMap<>();
        response.put("peliculas", pageAll.getContent());
        response.put("currentPage", pageAll.getNumber());
        response.put("totalItems", pageAll.getTotalElements());
        response.put("totalPages", pageAll.getTotalPages());
        return response;
    }

    public Pelicula save(Pelicula pelicula){return this.peliculaRepository.save(pelicula);}

    public Pelicula one(Long id){
        return this.peliculaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, Pelicula.class));
    }

    public Pelicula replace(Long id, Pelicula pelicula){
        return this.peliculaRepository.findById(id).map( c -> (id.equals(pelicula.getId()) ? // Compruebo si la el id coincide
                        this.peliculaRepository.save(pelicula) : null)) // Guardo en BBDD o devuelvo Null (podría ser una nueva excepcion)
                .orElseThrow(() -> new EntityNotFoundException(id, Pelicula.class)); // En caso contrario devuelvo Excepcion
    }

    public void delete(Long id){
        this.peliculaRepository.findById(id).map( p -> {
                    p.getCategorias().forEach(c-> c.getPeliculas().remove(p));
                    this.peliculaRepository.delete(p);
                    return p;})
                .orElseThrow(() -> new EntityNotFoundException(id, Pelicula.class));
    }
}
