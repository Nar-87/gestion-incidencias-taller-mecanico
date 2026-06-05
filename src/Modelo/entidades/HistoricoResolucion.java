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
public class HistoricoResolucion {
    private int idHistorico;
    private Incidencia incidencia;
    private LocalDateTime fechaCambio;
    private Incidencia.Estado estadoAnterior;
    private Incidencia.Estado estadoNuevo;
    private Incidencia.Prioridad prioridadAnterior;
    private Incidencia.Prioridad prioridadNueva;
    private String comentario;
    private Usuario tecnico;
    
    // Constructores
    public HistoricoResolucion() {}
    
    public HistoricoResolucion(Incidencia incidencia, Usuario tecnico, String comentario) {
        this.incidencia = incidencia;
        this.tecnico = tecnico;
        this.comentario = comentario;
        this.fechaCambio = LocalDateTime.now();
    }
    
    // Getters y Setters
    public int getIdHistorico() { return idHistorico; }
    public void setIdHistorico(int idHistorico) { this.idHistorico = idHistorico; }
    
    public Incidencia getIncidencia() { return incidencia; }
    public void setIncidencia(Incidencia incidencia) { this.incidencia = incidencia; }
    
    public LocalDateTime getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(LocalDateTime fechaCambio) { this.fechaCambio = fechaCambio; }
    
    public Incidencia.Estado getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(Incidencia.Estado estadoAnterior) { this.estadoAnterior = estadoAnterior; }
    
    public Incidencia.Estado getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(Incidencia.Estado estadoNuevo) { this.estadoNuevo = estadoNuevo; }
    
    public Incidencia.Prioridad getPrioridadAnterior() { return prioridadAnterior; }
    public void setPrioridadAnterior(Incidencia.Prioridad prioridadAnterior) { this.prioridadAnterior = prioridadAnterior; }
    
    public Incidencia.Prioridad getPrioridadNueva() { return prioridadNueva; }
    public void setPrioridadNueva(Incidencia.Prioridad prioridadNueva) { this.prioridadNueva = prioridadNueva; }
    
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    
    public Usuario getTecnico() { return tecnico; }
    public void setTecnico(Usuario tecnico) { this.tecnico = tecnico; }
    
    @Override
    public String toString() {
        return String.format("[%s] %s -> %s - %s", 
            fechaCambio, estadoAnterior, estadoNuevo, comentario);
    }

}
