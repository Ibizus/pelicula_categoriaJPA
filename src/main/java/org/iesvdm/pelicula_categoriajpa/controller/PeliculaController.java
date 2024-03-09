package org.iesvdm.pelicula_categoriajpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.pelicula_categoriajpa.domain.Pelicula;
import org.iesvdm.pelicula_categoriajpa.service.PeliculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService){
        this.peliculaService = peliculaService;
    }


    @GetMapping(value = {"","/"}, params = {"!buscar", "!ordenacion", "!pagina", "!tamanio"})
    public List<Pelicula> all(){
        log.info("Accediendo a todas las categorías");
        return this.peliculaService.all();
    }

    // METODO DE LISTADO CON PAGINACION:
    @GetMapping(value = {"", "/"}, params = {"pagina", "tamanio"})
    public ResponseEntity<Map<String, Object>> all(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina
            , @RequestParam(value = "tamanio", defaultValue = "3") int tamanio){

        log.info("Accediendo a todas las películas con paginación");

        Map<String, Object> responseAll = this.peliculaService.all(pagina, tamanio);

        return ResponseEntity.ok(responseAll);
    }

    // METODO DE LISTADO CON ORDENACIÓN USANDO ARRAY DE PARAM:
    @GetMapping(value = {"", "/"}, params = {"ordenacion"})
    public ResponseEntity<Map<String, Object>> all(
            @RequestParam(value = "ordenacion") ArrayList<String> ordenacion){

        log.info("Procesando array de parámetros de ordenación");
        // Partimos en dos cada posicion del array por la coma,
        // ya que no sabemos si vienen dos o 4 parámetros en el siguiente formato:
        // ["campo1", "sentido1"] ó ["campo1,sentido1", "campo2,sentido2"]
        List<String> posicion0 = Arrays.stream(ordenacion.get(0).split(",")).toList();
        List<String> posicion1 = Arrays.stream(ordenacion.get(1).split(",")).toList();

        // Controlamos el número de parámetros recibidos para hacer una ordenación u otra:
        if(posicion0.size() == 1 && posicion1.size() == 1){ // CASO 1 CAMPO Y 1 SENTIDO
            log.info("Recibido 1 orden y 1 sentido");
            return ResponseEntity.ok(this.peliculaService.allOrdered(posicion0.getFirst(), posicion1.getFirst()));

        }else if(posicion0.size() == 2 && posicion1.size() == 2){ // CASO 2 CAMPOS Y 2 SENTIDOS
            log.info("Recibidos 2 campos y 2 sentidos");
            return ResponseEntity.ok(this.peliculaService.allOrdered(posicion0.get(0), posicion0.get(1), posicion1.get(0), posicion1.get(1)));

        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping({"","/"})
    public Pelicula newPelicula(@RequestBody Pelicula pelicula){
        return this.peliculaService.save(pelicula);
    }

    @GetMapping({"/{id}"})
    public Pelicula one(@PathVariable("id") Long id){
        return this.peliculaService.one(id);
    }

    @PostMapping({"/{id}"})
    public Pelicula one(@PathVariable("id") Long id, @RequestBody Pelicula pelicula){
        return this.peliculaService.replace(id, pelicula);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePelicula(@PathVariable("id") Long id){
        this.peliculaService.delete(id);
    }
}