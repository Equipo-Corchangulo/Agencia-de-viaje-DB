package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Facturable;
import model.PromoAbsoluta;
import model.PromoAxB;
import model.PromoPorcentual;
import model.Promocion;
import model.TipoDeAtraccion;

public class PromocionDAO {
	private static List<Promocion> promocionesList = new ArrayList<Promocion>();
	private static Promocion toPromocion(ResultSet result) throws SQLException {
		Promocion promo;
		//List<Facturable> listaDeAtracciones, TipoDeAtraccion tipoDeAtraccion, String nombreDePromocion
		List<Facturable> listaDeAtracciones = new ArrayList<Facturable>();
		String[] atraccionIdList = result.getString("atraccion_list").split("-");
		for(String a : atraccionIdList) {
			listaDeAtracciones.add(AtraccionesDAO.findByID(Integer.parseInt(a)));
		}
		if(result.getDouble("porcentaje") >0 ) 
		{
			promo = new PromoPorcentual(listaDeAtracciones,
					TipoDeAtraccion.valueOf(result.getString("nombre_tipo")),
					result.getString("nombre"),result.getDouble("porcentaje"),
					result.getInt("id"));
		}
		else if (result.getInt("atraccion_extra")!=0) {
			Atraccion atraccionExtra = AtraccionesDAO.findByID(result.getInt("atraccion_extra"));
			promo = new PromoAxB(listaDeAtracciones, 
					TipoDeAtraccion.valueOf(result.getString("nombre_tipo")), 
					result.getString("nombre"),atraccionExtra,
					result.getInt("id"));
		}
		else {
			promo = new PromoAbsoluta(listaDeAtracciones, 
					TipoDeAtraccion.valueOf(result.getString("nombre_tipo")),
					result.getString("nombre"),result.getDouble("costo_fijo"),
					result.getInt("id"));
		}
		return promo;
	}
	
	public static List<Promocion>findAll() throws SQLException {
		String query = "SELECT *, GROUP_CONCAT(lista_atracciones.atraccion_id,'-') AS atraccion_list "
				+ "FROM promociones "
				+ "LEFT JOIN tipo_de_atracciones on promociones.tipo_atraccion = tipo_de_atracciones.id"
				+ " LEFT JOIN atracciones_promocion AS lista_atracciones on lista_atracciones.promocion_id = promociones.id"
				+ "  GROUP by lista_atracciones.promocion_id";
		List<Promocion> listaPromociones = new ArrayList<Promocion>();
        ResultSet result = ConnectionProvider.executeQuery(query);

		System.out.println("las promociones son");
		System.out.println(result.getFetchSize());
        while (result.next()) {
        	listaPromociones.add(toPromocion(result));
        }
        promocionesList = listaPromociones;
        return listaPromociones;
	}
	
	public static Promocion findByID(int id) throws SQLException {
    	Optional<Promocion> promoEncontrada =  promocionesList.stream().filter(promocion -> promocion.getID() == id).findFirst();
    	if (promoEncontrada.isPresent()) {
    		return (Promocion) promoEncontrada.get();
    	}
    	else {
    		String query = "SELECT *, GROUP_CONCAT(lista_atracciones.atraccion_id,'-') AS atraccion_list "
    				+ "FROM promociones "
    				+ "LEFT JOIN tipo_de_atracciones on promociones.tipo_atraccion = tipo_de_atracciones.id"
    				+ " LEFT JOIN atracciones_promocion AS lista_atracciones on lista_atracciones.promocion_id = promociones.id"
    				+ "  GROUP by lista_atracciones.promocion_id " 
    				+ " WHERE promociones.id = "+ id;
    		ResultSet result = ConnectionProvider.executeQuery(query);
    		return toPromocion(result);
    	}
	}

}
