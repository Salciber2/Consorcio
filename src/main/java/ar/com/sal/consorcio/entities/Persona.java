package ar.com.sal.consorcio.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas")
public class Persona implements Comparable<Persona>{
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    private String nombre;
    private String apellido;
    private String comentario;
    private boolean activo;
    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    // Constructores
    public Persona() {
        inicializarParametrosPredeterminados();
    }

    public Persona(String nombre, String apellido, String comentario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.comentario = comentario;
        inicializarParametrosPredeterminados();
    }

    public Persona(int id, String nombre, String apellido, String comentario) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
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
        return "Persona [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", comentario=" + comentario
                + ", activo=" + activo + ", fechaAlta=" + fechaAlta + ", fechaModificacion=" + fechaModificacion + "]";
    }

    @Override
    public int compareTo(Persona persona){
        return this.toString().compareTo(persona.toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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
