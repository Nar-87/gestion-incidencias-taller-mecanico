/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.dao;

import Modelo.dao.ConexionBD;
import Modelo.entidades.HistoricoResolucion;
import Modelo.entidades.Incidencia;
import Modelo.entidades.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MULTI
 */
public class HistoricoResolucionDao {
    public void crearRegistro(HistoricoResolucion historico) throws SQLException {
        String sql = "INSERT INTO historico_resoluciones (id_incidencia, fecha_cambio, " +
                    "estado_anterior, estado_nuevo, prioridad_anterior, prioridad_nueva, " +
                    "comentario, id_tecnico) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, historico.getIncidencia().getIdIncidencia());
            stmt.setTimestamp(2, Timestamp.valueOf(historico.getFechaCambio()));
            
            if (historico.getEstadoAnterior() != null) {
                stmt.setString(3, historico.getEstadoAnterior().name());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }
            
            if (historico.getEstadoNuevo() != null) {
                stmt.setString(4, historico.getEstadoNuevo().name());
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }
            
            if (historico.getPrioridadAnterior() != null) {
                stmt.setString(5, historico.getPrioridadAnterior().name());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }
            
            if (historico.getPrioridadNueva() != null) {
                stmt.setString(6, historico.getPrioridadNueva().name());
            } else {
                stmt.setNull(6, Types.VARCHAR);
            }
            
            stmt.setString(7, historico.getComentario());
            stmt.setInt(8, historico.getTecnico().getIdUsuario());
            
            stmt.executeUpdate();
        }
    }
    
    public List<HistoricoResolucion> obtenerPorIncidencia(int idIncidencia) throws SQLException {
        String sql = "SELECT hr.*, u.nombre, u.apellidos " +
                    "FROM historico_resoluciones hr " +
                    "JOIN usuarios u ON hr.id_tecnico = u.id_usuario " +
                    "WHERE hr.id_incidencia = ? " +
                    "ORDER BY hr.fecha_cambio DESC";
        //Voy a añadir una sql alternativa para precindir del Join
        List<HistoricoResolucion> historico = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idIncidencia);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    historico.add(mapearHistorico(rs));
                }
            }
        }
        return historico;
    }
    
    private HistoricoResolucion mapearHistorico(ResultSet rs) throws SQLException {
        HistoricoResolucion historico = new HistoricoResolucion();
        historico.setIdHistorico(rs.getInt("id_historico"));
        historico.setFechaCambio(rs.getTimestamp("fecha_cambio").toLocalDateTime());
        
        String estadoAnt = rs.getString("estado_anterior");
        if (estadoAnt != null) {
            historico.setEstadoAnterior(Incidencia.Estado.valueOf(estadoAnt));
        }
        
        String estadoNuevo = rs.getString("estado_nuevo");
        if (estadoNuevo != null) {
            historico.setEstadoNuevo(Incidencia.Estado.valueOf(estadoNuevo));
        }
        
        String prioridadAnt = rs.getString("prioridad_anterior");
        if (prioridadAnt != null) {
            historico.setPrioridadAnterior(Incidencia.Prioridad.valueOf(prioridadAnt));
        }
        
        String prioridadNueva = rs.getString("prioridad_nueva");
        if (prioridadNueva != null) {
            historico.setPrioridadNueva(Incidencia.Prioridad.valueOf(prioridadNueva));
        }
        
        historico.setComentario(rs.getString("comentario"));
        
        // Mapear técnico
        Usuario tecnico = new Usuario();
        tecnico.setIdUsuario(rs.getInt("id_tecnico"));
        tecnico.setNombre(rs.getString("nombre"));
        tecnico.setApellidos(rs.getString("apellidos"));
        historico.setTecnico(tecnico);
        
        return historico;
    }
}
