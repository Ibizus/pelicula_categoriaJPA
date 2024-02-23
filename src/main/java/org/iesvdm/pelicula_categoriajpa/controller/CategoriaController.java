package org.iesvdm.pelicula_categoriajpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.pelicula_categoriajpa.domain.Categoria;
import org.iesvdm.pelicula_categoriajpa.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    // lo dejo para ver que podemos restringir los parámetros directamente en el endpoint

//    @GetMapping(value = {"","/"}, params = {"!buscar", "!ordenar"})
//    public List<Categoria> all(){
//        log.info("Accediendo a todas las categorías");
//        return this.categoriaService.all();
//    }

    // METODO DE LISTADO CON FILTRO BUSCAR Y ORDENAR:
    @GetMapping(value = {"","/"}, params = {"!pagina", "!tamanio"})
    public List<Categoria> all(@RequestParam("buscar") Optional<String> buscarOptional, @RequestParam("ordenar") Optional<String> ordenarOptional){
        log.info("Accediendo a categorías con filtro buscar y ordenar");

        return this.categoriaService.all(buscarOptional, ordenarOptional);
    }

    // METODO DE LISTADO CON PAGINACION:
    @GetMapping(value = {"", "/"})
    public ResponseEntity<Map<String, Object>> all(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina
            , @RequestParam(value = "tamanio", defaultValue = "3") int tamanio){

        log.info("Accediendo a todas las categorías con paginación");

        Map<String, Object> responseAll = this.categoriaService.all(pagina, tamanio);

        return ResponseEntity.ok(responseAll);
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