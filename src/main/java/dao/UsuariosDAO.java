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
	    		TipoDeAtraccion.values()[result.getInt("atraccion_preferida")-1]);
	}
	
	public static List<PerfilUsuario> findAll() throws SQLException {
		String query = "SELECT * FROM usuarios";
		
		Connection conn = ConnectionProvider.getConnection();
		
		PreparedStatement statement = conn.prepareStatement(query);
		ResultSet results = statement.executeQuery();
		
		List<PerfilUsuario> listaUsuarios = new ArrayList<PerfilUsuario>();
		while(results.next()) {
			listaUsuarios.add(toPerfilUsuario(results));
		}
		
		return listaUsuarios;
	}

}
