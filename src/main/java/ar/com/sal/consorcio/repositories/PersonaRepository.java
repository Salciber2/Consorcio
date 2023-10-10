package ar.com.sal.consorcio.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.sal.consorcio.entities.Persona;

@Repository
public interface PersonaRepository extends CrudRepository<Persona, Integer>{    
    List<Persona> findByActivo(boolean activo);
    long countByActivo(boolean activo);
}
