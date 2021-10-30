package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConnectionProvider;
import model.Facturable;
import model.PerfilUsuario;
import model.TipoDeAtraccion;



public class UsuariosDAO {
	
	private static PerfilUsuario toPerfilUsuario(ResultSet result) throws SQLException {
		List<Facturable> listadeItinerario = new ArrayList<Facturable>();
		
		String atraccionesStr = result.getString("atracciones");
		if(atraccionesStr != null)
		{
			String[] atraccionIdList = atraccionesStr.split("-");
			getFacturableByIdList(atraccionIdList,false, listadeItinerario);
		}
		
		String promoStr = result.getString("promociones");
		if(promoStr != null) {
			
			String[] promoIdList= promoStr.split("-");
			getFacturableByIdList(promoIdList,true, listadeItinerario);
		}
		
		
		
	    return new PerfilUsuario(result.getString("nombre"), result.getDouble("presupuesto"), 
	    		result.getInt("tiempo_disponible"), 
	    		TipoDeAtraccion.valueOf(result.getString("nombre_tipo")), 
	    		result.getInt("id"),
	    		listadeItinerario);
	}
	
	public static void getFacturableByIdList(String[] facturableList, boolean isPromo, List<Facturable> resultList) throws NumberFormatException, SQLException {
		
		for(String a : facturableList) {
			if (!isPromo)
				resultList.add(AtraccionesDAO.findByID(Integer.parseInt(a)));
			else
				resultList.add(PromocionDAO.findByID(Integer.parseInt(a)));
		}
	}
	
	public static List<PerfilUsuario> findAll() throws SQLException {
		String query = "SELECT usuarios.*, tipo.nombre_tipo, group_concat(itinerarios.atraccion,'-') AS atracciones," +
				" group_concat(itinerarios.promocion,'-') AS promociones" +
				" FROM usuarios" +
				" LEFT JOIN tipo_de_atracciones AS 'tipo' ON usuarios.atraccion_preferida = tipo.id" +
				" LEFT JOIN itinerarios ON usuarios.id = itinerarios.usuario" +
				" GROUP BY usuarios.id";
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
