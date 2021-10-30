package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.ConnectionProvider;

public class ItinerarioDAO {
	
	public static void insertar(int idUsuario, int idAtraccion, int idPromocion) throws SQLException {
    	String campos="" ;
    	String values="";
    	
		if(idAtraccion > 0) {
    		campos = "(usuario,atraccion)";
    		values =  "("+idUsuario+","+idAtraccion+")";
    	}
    	else if(idPromocion >0) {
    		campos = "(usuario,promocion)";
    		values =  "("+idUsuario+","+idPromocion+")";
    		
    	}
		String query = "INSERT INTO itinerarios"+campos+" values " + values;
    	
    	ConnectionProvider.executeUpdate(query);
    }
	
	public static boolean existe(int idUsuario, int idAtraccion, int idPromocion) throws SQLException {
		String promoAtraccion = idAtraccion > 0 ? "atraccion = "+ idAtraccion : "promocion = "+ idPromocion;
		String query ="SELECT EXISTS(SELECT 1 FROM itinerarios WHERE usuario = " + idUsuario +
				" AND " + promoAtraccion + " Limit 1) AS existe";
		ResultSet result = ConnectionProvider.executeQuery(query);
		return result.getBoolean("existe");
	}

}
