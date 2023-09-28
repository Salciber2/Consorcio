package ar.com.sal.consorcio.entities;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "edificios")
public class Edificio implements Comparable<Edificio>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    private String direccion;
    private boolean activo;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    public Edificio() {}
    
    public Edificio(String direccion) {
        this.direccion = direccion;
        this.activo = true;
        this.fechaAlta = LocalDateTime.now();
        this.fechaModificacion = fechaAlta;
    }
    
    public Edificio(int id, String direccion) {
        this.id = id;
        this.direccion = direccion;
        this.activo = true;
        this.fechaAlta = LocalDateTime.now();
        this.fechaModificacion = fechaAlta;
    }
    
    @Override
    public String toString() {
        return "Edificio [id=" + id + ", direccion=" + direccion + ", activo=" + activo + ", fechaAlta=" + fechaAlta
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
