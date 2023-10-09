package ar.com.sal.consorcio.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.sal.consorcio.entities.Departamento;

@Repository
public interface DepartamentoRepository extends CrudRepository<Departamento, Integer> {
    List<Departamento> findByIdEdificio(int idEdificio);
    List<Departamento> findByActivo(boolean activo);
    long countByIdEdificio(int idEdificio);
    long countByIdEdificioAndActivo(int idEdificio, boolean activo);
}
