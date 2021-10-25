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
    private static String tableName = "atracciones";
    private static List<Atraccion> atraccionList = new ArrayList<Atraccion>();

    private static Atraccion toAtraccion(ResultSet result) throws SQLException {
        return new Atraccion(result.getString("nombre"), result.getDouble("costo_visita"),
                result.getDouble("costo_visita"), result.getInt("cupo_diario"),
                TipoDeAtraccion.values()[result.getInt("tipo")-1], result.getInt("id"));
    }

    public static List<Atraccion> findAll() throws SQLException {
        String query = "SELECT * FROM atracciones";
        List<Atraccion> listaAtracciones = new ArrayList<Atraccion>();
        ResultSet result = ConnectionProvider.executeQuery(query);
        while (result.next()) {
            listaAtracciones.add(toAtraccion(result));
        }
        atraccionList = listaAtracciones;
        return listaAtracciones;
    }
    
    public static Atraccion findByID(int id) throws SQLException {
    	String query = "SELECT * FROM atracciones where id = " + id;
    	Optional atraccionEncontrada =  atraccionList.stream().filter(atraccion -> atraccion.getID() == id).findFirst();
    	if (atraccionEncontrada.isPresent()) {
    		return (Atraccion) atraccionEncontrada.get();
    	}
    	else {
    		ResultSet result = ConnectionProvider.executeQuery(query);
    		return toAtraccion(result);
    	}
    }
}
