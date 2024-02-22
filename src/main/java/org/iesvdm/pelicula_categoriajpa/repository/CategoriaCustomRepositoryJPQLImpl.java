package org.iesvdm.pelicula_categoriajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.iesvdm.pelicula_categoriajpa.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaCustomRepositoryJPQLImpl implements CategoriaCustomRepository{

    @Autowired
    private EntityManager em;

    @Override
    public List<Categoria> queryCustomCategoria(Optional<String> buscarOptional, Optional<String> ordenarOptional) {
        // String Builder ya no es necesario, se pueden concatenar cadenas, es igual de optimo y queda más limpio el codigo.
        StringBuilder queryBuilder = new StringBuilder("SELECT C FROM Categoria C");
        if (buscarOptional.isPresent()){
            queryBuilder.append(" ").append("WHERE C.nombre like :nombre");
        }                                   // ESTO LO PONEMOS PARA COMPROBAR QUE SOLO ENTRAN ESOS DOS CASOS DE ORDEN Y NO UNA INYECCION SQL
        if (ordenarOptional.isPresent() && (ordenarOptional.get().equalsIgnoreCase("ASC") || ordenarOptional.get().equalsIgnoreCase("DESC"))) {
            if (ordenarOptional.isPresent() && "asc".equalsIgnoreCase(ordenarOptional.get())) {
                queryBuilder.append(" ").append("ORDER BY C.nombre ASC");
            } else if (ordenarOptional.isPresent() && "desc".equalsIgnoreCase(ordenarOptional.get())) {
                queryBuilder.append(" ").append("ORDER BY C.nombre DESC");
            }
        }

        // Estamos usando una consulta JPQL (SQL con entidades de JPA, objetos) así que usamos Entitymangaer y el método creatQuery
        Query query = em.createQuery(queryBuilder.toString());
        if (buscarOptional.isPresent()){
            query.setParameter("nombre", "%"+buscarOptional.get()+"%");
        }
        return query.getResultList();
    }

    @Override
    public int countCustomCategoria(Optional<String> buscarOptional) {
        return 0;
    }
}
