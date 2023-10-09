package ar.com.sal.consorcio.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.sal.consorcio.entities.Edificio;

@Repository
public interface EdificioRepository extends CrudRepository<Edificio, Integer> {
    /*
     * formas de escribir las queries en el nombre del método
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query
     * -methods.query-creation
     * 
     */
    List<Edificio> findByActivo(boolean activo);
    long countByActivo(boolean activo);
}
