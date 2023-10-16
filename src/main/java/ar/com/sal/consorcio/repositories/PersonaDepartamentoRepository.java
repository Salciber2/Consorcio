package ar.com.sal.consorcio.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.sal.consorcio.entities.PersonaDepartamento;

@Repository
public interface PersonaDepartamentoRepository extends CrudRepository<PersonaDepartamento, Integer>{
    List<PersonaDepartamento> findByActivoAndIdDepartamento(boolean activo, int idDepartamento);
    PersonaDepartamento findByIdDepartamentoAndIdPersona(int idDepartamento, int idPersona);
}