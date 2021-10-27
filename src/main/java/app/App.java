package app;

import dao.AtraccionesDAO;
import dao.PromocionDAO;
import model.Atraccion;
import model.Facturable;
import model.Promocion;
import dao.UsuariosDAO;
import model.PerfilUsuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		//dbc:sqlite:C:\Users\josel\eclipse-workspace\conexion_db_agenciadeturismo\tierramedia.db

		
		List<Facturable> listaDeFacturable = new ArrayList<>();
		List<PerfilUsuario> listaDeUsuario;
		List<Atraccion> listaAtracciones;
		List<Promocion> listaPromociones;
		
		listaDeUsuario = UsuariosDAO.findAll();
		listaAtracciones = AtraccionesDAO.findAll();
		listaPromociones = PromocionDAO.findAll();
		
		for (Promocion promocion : listaPromociones) {
			listaDeFacturable.add(promocion);
		}

		
		for (Atraccion atraccion : listaAtracciones) {
			listaDeFacturable.add(atraccion);
		}
		
		Recomendador recomendador = new Recomendador(listaDeUsuario, listaDeFacturable);
		recomendador.ofrecerSugerencias();

	}
}
