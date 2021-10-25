package app;

import dao.AtraccionesDAO;
import model.Atraccion;

import java.sql.SQLException;
import java.util.List;

public class App {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		//dbc:sqlite:C:\Users\josel\eclipse-workspace\conexion_db_agenciadeturismo\tierramedia.db
		System.out.println("ahora buscar");
		System.out.println(AtraccionesDAO.findByID(1));
		System.out.println("Ahota todas las atracciones");
		List<Atraccion> lista_atracciones = AtraccionesDAO.findAll();
		for(Atraccion a : lista_atracciones){
			System.out.println(a);
		}
		
		System.out.println("ahora buscar");
		System.out.println(AtraccionesDAO.findByID(5));
	}

}
