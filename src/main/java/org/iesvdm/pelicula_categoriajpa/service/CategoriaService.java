package org.iesvdm.pelicula_categoriajpa.service;

import org.iesvdm.pelicula_categoriajpa.exception.EntityNotFoundException;
import org.iesvdm.pelicula_categoriajpa.domain.Categoria;
import org.iesvdm.pelicula_categoriajpa.dto.CategoriaDTO;
import org.iesvdm.pelicula_categoriajpa.repository.CategoriaCustomRepositoryJPQLImpl;
import org.iesvdm.pelicula_categoriajpa.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaCustomRepositoryJPQLImpl categoriaCustomRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }


    // Opción con conteo de películas por categoría:
    public List<CategoriaDTO> all(){

        return this.categoriaRepository.findAll().stream().map(CategoriaDTO::new).toList();
    }

    public List<Categoria> all(Optional<String> buscar, Optional<String> ordenar){
        return this.categoriaCustomRepository.queryCustomCategoria(buscar, ordenar);
    }

    public Map<String, Object> all(int pagina, int tamanio){

        Pageable paginado = PageRequest.of(pagina, tamanio, Sort.by("id").ascending());
        Page<Categoria> pageAll = this.categoriaRepository.findAll(paginado);

        Map<String, Object> response = new HashMap<>();

        response.put("categorias", pageAll.getContent());
        response.put("currentpage", pageAll.getNumber());
        response.put("totalItems", pageAll.getTotalElements());
        response.put("totalPages", pageAll.getTotalPages());

        return response;
    }

    public Categoria save(Categoria categoria){
        return this.categoriaRepository.save(categoria);
    }

    public Categoria one(Long id){
        return this.categoriaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, Categoria.class));
    }

    public Categoria replace(Long id, Categoria categoria){
        return this.categoriaRepository.findById(id).map( c -> (id.equals(categoria.getId()) ? // Compruebo si la el id coincide
                                                        this.categoriaRepository.save(categoria) : null)) // Guardo en BBDD o devuelvo Null (podría ser una nueva excepcion)
                                        .orElseThrow(() -> new EntityNotFoundException(id, Categoria.class)); // En caso contrario devuelvo Excepcion
    }

    public void delete(Long id){
        this.categoriaRepository.findById(id).map( c -> {
                                        c.getPeliculas().forEach(p-> p.getCategorias().remove(c));
                                        this.categoriaRepository.delete(c);
                                        return c;})
                .orElseThrow(() -> new EntityNotFoundException(id, Categoria.class));
    }

}