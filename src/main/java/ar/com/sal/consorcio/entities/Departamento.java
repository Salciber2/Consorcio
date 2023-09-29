package ar.com.sal.consorcio.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "departamentos")
public class Departamento implements Comparable<Departamento>{
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    private int numero;
    private int idEdificio; // TODO Cambiar a relación objeto
    private String comentario;
    private boolean activo;
    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    // Constructores
    public Departamento() {
        inicializarParametrosPredeterminados();
    }

    public Departamento(int numero, int idEdificio, String comentario) {
        this.numero = numero;
        this.idEdificio = idEdificio;
        this.comentario = comentario;
        inicializarParametrosPredeterminados();
    }

    public Departamento(int id, int numero, int idEdificio, String comentario) {
        this.id = id;
        this.numero = numero;
        this.idEdificio = idEdificio;
        this.comentario = comentario;
        inicializarParametrosPredeterminados();
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
        return "Departamento [id=" + id + ", numero=" + numero + ", idEdificio=" + idEdificio + ", comentario="
                + comentario + ", activo=" + activo + ", fechaAlta=" + fechaAlta + ", fechaModificacion="
                + fechaModificacion + "]";
    }

    @Override
    public int compareTo(Departamento departamento){
        return this.toString().compareTo(departamento.toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(int idEdificio) {
        this.idEdificio = idEdificio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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