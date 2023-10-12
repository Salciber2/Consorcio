package ar.com.sal.consorcio.entities;

import java.time.LocalDateTime;

import ar.com.sal.consorcio.enums.Relacion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas_x_departamentos")
public class PersonaDepartamento implements Comparable<PersonaDepartamento>{
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    private int idPersona;
    private int idDepartamento;
    @Enumerated(value = EnumType.STRING)
    private Relacion relacion;
    private boolean activo;
    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    // Constructores

    public PersonaDepartamento() {
        inicializarParametrosPredeterminados();
    }

    public PersonaDepartamento(int idPersona, int idDepartamento, Relacion relacion, boolean activo,
                                LocalDateTime fechaAlta, LocalDateTime fechaModificacion) {
        this.idPersona = idPersona;
        this.idDepartamento = idDepartamento;
        this.relacion = relacion;
        this.activo = activo;
        this.fechaAlta = fechaAlta;
        this.fechaModificacion = fechaModificacion;
    }

    public PersonaDepartamento(int id, int idPersona, int idDepartamento, Relacion relacion, boolean activo,
                                LocalDateTime fechaAlta, LocalDateTime fechaModificacion) {
        this.id = id;
        this.idPersona = idPersona;
        this.idDepartamento = idDepartamento;
        this.relacion = relacion;
        this.activo = activo;
        this.fechaAlta = fechaAlta;
        this.fechaModificacion = fechaModificacion;
    }

    // Métodos
    private void inicializarParametrosPredeterminados(){
        this.activo = true;
        this.fechaAlta = LocalDateTime.now();
        this.fechaModificacion = fechaAlta;
    }

    // toString - compareTo - Getters - Setters
    @Override
    public String toString() {
        return "PersonaDepartamento [id=" + id + ", idPersona=" + idPersona + ", idDepartamento=" + idDepartamento
                + ", relacion=" + relacion + ", activo=" + activo + ", fechaAlta=" + fechaAlta + ", fechaModificacion="
                + fechaModificacion + "]";
    }

    @Override
    public int compareTo(PersonaDepartamento personaDepartamento){
        return this.toString().compareTo(personaDepartamento.toString());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Relacion getRelacion() {
        return relacion;
    }

    public void setRelacion(Relacion relacion) {
        this.relacion = relacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }    
}