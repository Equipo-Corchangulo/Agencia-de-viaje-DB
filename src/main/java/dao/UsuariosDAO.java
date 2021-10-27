package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConnectionProvider;
import model.PerfilUsuario;
import model.TipoDeAtraccion;



public class UsuariosDAO {
	
	private static PerfilUsuario toPerfilUsuario(ResultSet result) throws SQLException {
	    return new PerfilUsuario(result.getString("nombre"), result.getInt("presupuesto"), 
	    		result.getInt("tiempo_disponible"), 
	    		TipoDeAtraccion.valueOf(result.getString("nombre_tipo")), result.getInt("id"));
	}
	
	public static List<PerfilUsuario> findAll() throws SQLException {
		String query = "SELECT * FROM usuarios LEFT JOIN tipo_de_atracciones ON usuarios.atraccion_preferida = tipo_de_atracciones.id";
		
		Connection conn = ConnectionProvider.getConnection();
		
		PreparedStatement statement = conn.prepareStatement(query);
		ResultSet results = statement.executeQuery();
		
		List<PerfilUsuario> listaUsuarios = new ArrayList<PerfilUsuario>();
		while(results.next()) {
			listaUsuarios.add(toPerfilUsuario(results));
		}
		
		return listaUsuarios;
	}
	
	public static PerfilUsuario findByID(int id) throws SQLException {
    	String query = "SELECT * FROM usuarios LEFT JOIN itinerarios ON itinerarios.usuario = usuarios.id where usuarios.id = " + id;
    	
    	Connection conn = ConnectionProvider.getConnection();
		
		PreparedStatement statement = conn.prepareStatement(query);
		ResultSet results = statement.executeQuery();
		
		return toPerfilUsuario(results);
    }
    
	
	public static void updateUsuarios(int id, double nuevoPresupuesto, double nuevoTiempoDisponible) throws SQLException {
		String query = "UPDATE usuarios SET presupuesto = " + nuevoPresupuesto
				+ ", tiempo_disponible = " + nuevoTiempoDisponible 
				+ " WHERE id = " + id;
    	ConnectionProvider.executeUpdate(query);
	}

}
