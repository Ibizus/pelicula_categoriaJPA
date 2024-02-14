package org.iesvdm.pelicula_categoriajpa.service;

import org.iesvdm.pelicula_categoriajpa.domain.Categoria;
import org.iesvdm.pelicula_categoriajpa.exception.CategoriaNotFoundException;
import org.iesvdm.pelicula_categoriajpa.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }


    public List<Categoria> all(){
        return this.categoriaRepository.findAll();
    }

    public Categoria save(Categoria categoria){
        return this.categoriaRepository.save(categoria);
    }

    public Categoria one(Long id){
        return this.categoriaRepository.findById(id).orElseThrow(()-> new CategoriaNotFoundException(id));
    }

    public Categoria replace(Long id, Categoria categoria){
        return this.categoriaRepository.findById(id).map( c -> (id.equals(categoria.getId()) ? // Compruebo si la el id coincide
                                                        this.categoriaRepository.save(categoria) : null)) // Guardo en BBDD o devuelvo Null (podría ser una nueva excepcion)
                                        .orElseThrow(() -> new CategoriaNotFoundException(id)); // En caso contrario devuelvo Excepcion
    }

    public void delete(Long id){
        this.categoriaRepository.findById(id).map( c -> {
                                                        this.categoriaRepository.delete(c);
                                                        return c;})
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }
}
