/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.entidades;

import java.time.LocalDateTime;
/**
 *
 * @author MULTI
 */
public class Incidencia {
    private int idIncidencia;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Estado estado;
    private Prioridad prioridad;
    private Usuario autor;
    private Usuario tecnicoAsignado;
    private Incidencia incidenciaAnterior;
    private boolean activa;
    
    public enum Estado {
        PENDIENTE, EN_PROCESO, ESPERA, CERRADA
    }
    
    public enum Prioridad {
        BAJA, MEDIA, ALTA
    }
    
    // Constructores
    public Incidencia() {}
    
    public Incidencia(String titulo, String descripcion, Usuario autor, Prioridad prioridad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor = autor;
        this.prioridad = prioridad;
        this.estado = Estado.PENDIENTE;
        this.activa = true;
        this.fechaAlta = LocalDateTime.now();
    }
    
    // Getters y Setters
    public int getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(int idIncidencia) { this.idIncidencia = idIncidencia; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public LocalDateTime getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDateTime fechaAlta) { this.fechaAlta = fechaAlta; }
    
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }
    
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    
    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }
    
    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }
    
    public Usuario getTecnicoAsignado() { return tecnicoAsignado; }
    public void setTecnicoAsignado(Usuario tecnicoAsignado) { this.tecnicoAsignado = tecnicoAsignado; }
    
    public Incidencia getIncidenciaAnterior() { return incidenciaAnterior; }
    public void setIncidenciaAnterior(Incidencia incidenciaAnterior) { this.incidenciaAnterior = incidenciaAnterior; }
    
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    
    public String getResumen() {
        return String.format("ID: %d - %s - %s - %s", 
            idIncidencia, titulo, estado, prioridad);
    }

}
