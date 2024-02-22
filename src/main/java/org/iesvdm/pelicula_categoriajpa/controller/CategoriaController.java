package org.iesvdm.pelicula_categoriajpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.pelicula_categoriajpa.domain.Categoria;
import org.iesvdm.pelicula_categoriajpa.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }


    // Este método NO HACE FALTA ya que en el siguiente ya hemos incluido
    // que si no lleva ningún parámetro se listen todas las categorias
    // lo pongo para ver que podemos restringir los parámetros directamente en el endpoint
    @GetMapping(value = {"","/"}, params = {"!buscar", "!ordenar"})
    public List<Categoria> all(){
        log.info("Accediendo a todas las categorías");
        return this.categoriaService.all();
    }

    @GetMapping(value = {"","/"})
    public List<Categoria> all(@RequestParam("buscar") Optional<String> buscarOptional, @RequestParam("ordenar") Optional<String> ordenarOptional){
        log.info("Accediendo a categorías con filtro buscar y ordenar");

        return this.categoriaService.all(buscarOptional, ordenarOptional);
    }

    @PostMapping({"","/"})
    public Categoria newCategoria(@RequestBody Categoria categoria){
        return this.categoriaService.save(categoria);
    }

    @GetMapping({"/{id}"})
    public Categoria one(@PathVariable("id") Long id){
        return this.categoriaService.one(id);
    }

    @PostMapping({"/{id}"})
    public Categoria one(@PathVariable("id") Long id, @RequestBody Categoria categoria){
        return this.categoriaService.replace(id, categoria);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategoria(@PathVariable("id") Long id){
        this.categoriaService.delete(id);
    }
}