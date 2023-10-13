package ar.com.sal.consorcio.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.sal.consorcio.entities.Telefono;

@Repository
public interface TelefonoRepository extends CrudRepository<Telefono, Integer>{
    List<Telefono> findByActivo(boolean activo);
    List<Telefono> findByIdPersonaAndActivo(int idPersona, boolean activo);
}
