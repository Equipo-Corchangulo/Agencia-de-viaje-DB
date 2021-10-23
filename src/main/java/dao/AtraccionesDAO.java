package dao;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.TipoDeAtraccion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtraccionesDAO {
    private static String tableName = "atracciones";

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

        return listaAtracciones;
    }
}
