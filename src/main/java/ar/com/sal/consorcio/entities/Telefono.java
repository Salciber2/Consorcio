package ar.com.sal.consorcio.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "teleofonos")
public class Telefono implements Comparable<Telefono>{
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    private int numero;
    private int idPersona; 
    private String comentario;
    private boolean activo;
    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    // Constructores
    public Telefono() {
        inicializarParametrosPredeterminados();
    }

    public Telefono(int numero, int idPersona, String comentario) {
        this.numero = numero;
        this.idPersona = idPersona;
        this.comentario = comentario;
        inicializarParametrosPredeterminados();
    }

    public Telefono(int id, int numero, int idPersona, String comentario) {
        this.id = id;
        this.numero = numero;
        this.idPersona = idPersona;
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
        return "Telefono [id=" + id + ", numero=" + numero + ", idPersona=" + idPersona + ", comentario=" + comentario
                + ", activo=" + activo + ", fechaAlta=" + fechaAlta + ", fechaModificacion=" + fechaModificacion + "]";
    }

    @Override
    public int compareTo(Telefono telefono){
        return this.toString().compareTo(telefono.toString());
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

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
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