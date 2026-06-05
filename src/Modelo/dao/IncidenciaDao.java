/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.dao;

import Modelo.dao.ConexionBD;
import Modelo.entidades.Incidencia;
import Modelo.entidades.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author MULTI
 */
public class IncidenciaDao {
    public void crear(Incidencia incidencia) throws SQLException {
        String sql = "INSERT INTO incidencias (titulo, descripcion, fecha_alta, estado, prioridad, " +
                    "id_autor, id_tecnico_asignado, id_incidencia_anterior, activa) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            //Muy importante, preparamos las consultas y hacemos bind de las ? para evitar la inyección de código
            stmt.setString(1, incidencia.getTitulo());
            stmt.setString(2, incidencia.getDescripcion());
            stmt.setTimestamp(3, Timestamp.valueOf(incidencia.getFechaAlta()));
            stmt.setString(4, incidencia.getEstado().name());
            stmt.setString(5, incidencia.getPrioridad().name());
            stmt.setInt(6, incidencia.getAutor().getIdUsuario());
            
            if (incidencia.getTecnicoAsignado() != null) {
                stmt.setInt(7, incidencia.getTecnicoAsignado().getIdUsuario());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            
            if (incidencia.getIncidenciaAnterior() != null) {
                stmt.setInt(8, incidencia.getIncidenciaAnterior().getIdIncidencia());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }
            
            stmt.setBoolean(9, incidencia.isActiva());
            
            stmt.executeUpdate();
            
            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    incidencia.setIdIncidencia(generatedKeys.getInt(1));
                }
            }
        }
    }
    
    public void actualizar(Incidencia incidencia) throws SQLException {
        String sql = "UPDATE incidencias SET titulo = ?, descripcion = ?, fecha_inicio = ?, " +
                    "fecha_fin = ?, estado = ?, prioridad = ?, id_tecnico_asignado = ?, activa = ? " +
                    "WHERE id_incidencia = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, incidencia.getTitulo());
            stmt.setString(2, incidencia.getDescripcion());
            
            if (incidencia.getFechaInicio() != null) {
                stmt.setTimestamp(3, Timestamp.valueOf(incidencia.getFechaInicio()));
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }
            
            if (incidencia.getFechaFin() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(incidencia.getFechaFin()));
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }
            
            stmt.setString(5, incidencia.getEstado().name());
            stmt.setString(6, incidencia.getPrioridad().name());
            
            if (incidencia.getTecnicoAsignado() != null) {
                stmt.setInt(7, incidencia.getTecnicoAsignado().getIdUsuario());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            
            stmt.setBoolean(8, incidencia.isActiva());
            stmt.setInt(9, incidencia.getIdIncidencia());
            
            stmt.executeUpdate();
        }
    }
    
    public List<Incidencia> obtenerIncidenciasAbiertas() throws SQLException {
        String sql = "SELECT i.*, ua.nombre as autor_nombre, ua.apellidos as autor_apellidos, " +
                    "ut.nombre as tecnico_nombre, ut.apellidos as tecnico_apellidos " +
                    "FROM incidencias i " +
                    "LEFT JOIN usuarios ua ON i.id_autor = ua.id_usuario " +
                    "LEFT JOIN usuarios ut ON i.id_tecnico_asignado = ut.id_usuario " +
                    "WHERE i.activa = TRUE AND i.estado != 'CERRADA' " +
                    "ORDER BY i.fecha_alta DESC";
        
        return obtenerIncidenciasConQuery(sql);
    }
    
    public List<Incidencia> obtenerIncidenciasPorUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT i.*, ua.nombre as autor_nombre, ua.apellidos as autor_apellidos, " +
                    "ut.nombre as tecnico_nombre, ut.apellidos as tecnico_apellidos " +
                    "FROM incidencias i " +
                    "LEFT JOIN usuarios ua ON i.id_autor = ua.id_usuario " +
                    "LEFT JOIN usuarios ut ON i.id_tecnico_asignado = ut.id_usuario " +
                    "WHERE i.id_autor = ? AND i.activa = TRUE " +
                    "ORDER BY i.fecha_alta DESC";
        
        List<Incidencia> incidencias = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    incidencias.add(mapearIncidencia(rs));
                }
            }
        }
        return incidencias;
    }
    
    public Incidencia obtenerPorId(int id) throws SQLException {
        String sql = "SELECT i.*, ua.nombre as autor_nombre, ua.apellidos as autor_apellidos, " +
                    "ut.nombre as tecnico_nombre, ut.apellidos as tecnico_apellidos " +
                    "FROM incidencias i " +
                    "LEFT JOIN usuarios ua ON i.id_autor = ua.id_usuario " +
                    "LEFT JOIN usuarios ut ON i.id_tecnico_asignado = ut.id_usuario " +
                    "WHERE i.id_incidencia = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearIncidencia(rs);
                }
            }
        }
        return null;
    }
    
    private List<Incidencia> obtenerIncidenciasConQuery(String sql) throws SQLException {
        List<Incidencia> incidencias = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                incidencias.add(mapearIncidencia(rs));
            }
        }
        return incidencias;
    }
    
    private Incidencia mapearIncidencia(ResultSet rs) throws SQLException {
        Incidencia incidencia = new Incidencia();
        incidencia.setIdIncidencia(rs.getInt("id_incidencia"));
        incidencia.setTitulo(rs.getString("titulo"));
        incidencia.setDescripcion(rs.getString("descripcion"));
        incidencia.setFechaAlta(rs.getTimestamp("fecha_alta").toLocalDateTime());
        
        int idIncidenciaAnterior = rs.getInt("id_incidencia_anterior");
        if (idIncidenciaAnterior != 0) {
            Incidencia anterior = new Incidencia();
            anterior.setIdIncidencia(idIncidenciaAnterior);
            incidencia.setIncidenciaAnterior(anterior);
        } else {
              incidencia.setIncidenciaAnterior(null);      
        }
        
        Timestamp fechaInicio = rs.getTimestamp("fecha_inicio");
        if (fechaInicio != null) {
            incidencia.setFechaInicio(fechaInicio.toLocalDateTime());
        }
        
        Timestamp fechaFin = rs.getTimestamp("fecha_fin");
        if (fechaFin != null) {
            incidencia.setFechaFin(fechaFin.toLocalDateTime());
        }
        
        incidencia.setEstado(Incidencia.Estado.valueOf(rs.getString("estado")));
        incidencia.setPrioridad(Incidencia.Prioridad.valueOf(rs.getString("prioridad")));
        incidencia.setActiva(rs.getBoolean("activa"));
        
        // Mapear autor
        Usuario autor = new Usuario();
        autor.setIdUsuario(rs.getInt("id_autor"));
        autor.setNombre(rs.getString("autor_nombre"));
        autor.setApellidos(rs.getString("autor_apellidos"));
        incidencia.setAutor(autor);
  
        
        // Mapear técnico si existe
        if (rs.getInt("id_tecnico_asignado") != 0) {
            Usuario tecnico = new Usuario();
            tecnico.setIdUsuario(rs.getInt("id_tecnico_asignado"));
            tecnico.setNombre(rs.getString("tecnico_nombre"));
            tecnico.setApellidos(rs.getString("tecnico_apellidos"));
            incidencia.setTecnicoAsignado(tecnico);
        }
        
        return incidencia;
    }
    //he añadido este método para poder tener las incidencias cerradas en otra pestaña
    public List<Incidencia> obtenerIncidenciasCerradas() throws SQLException {
        String sql = "SELECT i.*, ua.nombre as autor_nombre, ua.apellidos as autor_apellidos, " +
                     "ut.nombre as tecnico_nombre, ut.apellidos as tecnico_apellidos " +
                     "FROM incidencias i " +
                     "LEFT JOIN usuarios ua ON i.id_autor = ua.id_usuario " +
                     "LEFT JOIN usuarios ut ON i.id_tecnico_asignado = ut.id_usuario " +
                     "WHERE i.estado = 'CERRADA' " +
                     "ORDER BY i.fecha_fin DESC";

        return obtenerIncidenciasConQuery(sql);
    }
    //he añadido estos 2 últimos métodos para conseguir hacer el reporte de las incidencias
    public int contarPorEstado(String estado, Usuario usuario) throws SQLException {
        String sql;
        if (usuario.getPerfil() == Usuario.Perfil.USUARIO) {
            sql = "SELECT COUNT(*) FROM incidencias WHERE estado = ? AND id_autor = ?";
        } else {
            sql = "SELECT COUNT(*) FROM incidencias WHERE estado = ?";
        }

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estado);

            if (usuario.getPerfil() == Usuario.Perfil.USUARIO) {
                ps.setInt(2, usuario.getIdUsuario());
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int contarTotal(Usuario usuario) throws SQLException {
        String sql;
        if (usuario.getPerfil() == Usuario.Perfil.USUARIO) {
            sql = "SELECT COUNT(*) FROM incidencias WHERE id_autor = ?";
        } else {
            sql = "SELECT COUNT(*) FROM incidencias";
        }

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (usuario.getPerfil() == Usuario.Perfil.USUARIO) {
                ps.setInt(1, usuario.getIdUsuario());
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

}
