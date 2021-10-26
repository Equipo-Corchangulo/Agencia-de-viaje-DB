package dao;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.TipoDeAtraccion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtraccionesDAO {
    private static List<Atraccion> atraccionList = new ArrayList<Atraccion>();

    private static Atraccion toAtraccion(ResultSet result) throws SQLException {
        return new Atraccion(result.getString("nombre"), result.getDouble("costo_visita"),
                result.getDouble("tiempo_promedio"), result.getInt("cupo_diario"),
                TipoDeAtraccion.valueOf((result.getString("nombre_tipo"))), result.getInt("id"));
    }

    public static List<Atraccion> findAll() throws SQLException {
        String query = "SELECT * FROM atracciones LEFT JOIN tipo_de_atracciones ON tipo_de_atracciones.id = atracciones.tipo";
        List<Atraccion> listaAtracciones = new ArrayList<Atraccion>();
        ResultSet result = ConnectionProvider.executeQuery(query);
        while (result.next()) {
            listaAtracciones.add(toAtraccion(result));
        }
        atraccionList = listaAtracciones;
        return listaAtracciones;
    }
    
    public static Atraccion findByID(int id) throws SQLException {
    	String query = "SELECT * FROM atracciones LEFT JOIN tipo_de_atracciones ON tipo_de_atracciones.id = atracciones.tipo where atracciones.id = " + id;
    	Optional<Atraccion> atraccionEncontrada =  atraccionList.stream().filter(atraccion -> atraccion.getID() == id).findFirst();
    	if (atraccionEncontrada.isPresent()) {
    		return (Atraccion) atraccionEncontrada.get();
    	}
    	else {
    		ResultSet result = ConnectionProvider.executeQuery(query);
    		return toAtraccion(result);
    	}
    }
    
    public static void editarCupoDeAtraccion(int id, int nuevoCupo) throws SQLException {
    	String query = "update atracciones SET cupo_diario = " + nuevoCupo + " WHERE id = " + id;
    	ConnectionProvider.executeUpdate(query);
    }
    
}
