package ar.com.sal.consorcio.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "edificios")
public class Edificio implements Comparable<Edificio>{
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    private String direccion;
    private String comentario;
    private boolean activo;
    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    // Constructores
    public Edificio() {
        inicializarParametrosPredeterminados();
    }
    
    public Edificio(String direccion, String comentario, boolean activo, LocalDateTime fechaAlta, LocalDateTime fechaModificacion) {
        this.direccion = direccion;
        this.comentario = comentario;
        this.activo = activo;
        this.fechaAlta = fechaAlta;
        this.fechaModificacion = fechaModificacion;
    }
    
    public Edificio(int id, String direccion, String comentario, boolean activo, LocalDateTime fechaAlta, LocalDateTime fechaModificacion) {
        this.id = id;
        this.direccion = direccion;
        this.comentario = comentario;
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
        return "Edificio [id=" + id + ", direccion=" + direccion + ",comentario=" + comentario + ", activo=" + activo + ", fechaAlta=" + fechaAlta
                + ", fechaModificacion=" + fechaModificacion + "]";
    }

    @Override
    public int compareTo(Edificio edificio){
        return this.toString().compareTo(edificio.toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
