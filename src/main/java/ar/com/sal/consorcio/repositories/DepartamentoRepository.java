package ar.com.sal.consorcio.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.sal.consorcio.entities.Departamento;

@Repository
public interface DepartamentoRepository extends CrudRepository<Departamento, Integer> {
    List<Departamento> findByActivo(boolean activo);
    List<Departamento> findByIdEdificioAndActivo(int idEdificio, boolean activo);
}
