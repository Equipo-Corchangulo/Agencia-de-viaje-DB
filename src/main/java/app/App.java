package app;

import dao.AtraccionesDAO;
import dao.PromocionDAO;
import model.Atraccion;
import model.Promocion;
import dao.UsuariosDAO;
import model.PerfilUsuario;

import java.sql.SQLException;
import java.util.List;

public class App {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		//dbc:sqlite:C:\Users\josel\eclipse-workspace\conexion_db_agenciadeturismo\tierramedia.db
		System.out.println("Buscar Atracciones por ID");
		System.out.println(AtraccionesDAO.findByID(1));
		//AtraccionesDAO.findByID(1).restarCupo();
		
		System.out.println("----------------");
		
		System.out.println("Ahora todas las atracciones");
		System.out.println("ATRACCIONES:");
		
		List<Atraccion> lista_atracciones = AtraccionesDAO.findAll();
		for(Atraccion a : lista_atracciones){
			System.out.println(a);
		}
		
		System.out.println("----------------");

		System.out.println("Ahora los usuarios");
		System.out.println("USUARIOS:");
		
		List<PerfilUsuario> lista_usuarios = UsuariosDAO.findAll();
		for(PerfilUsuario u : lista_usuarios){
			System.out.println(u);
		}
		
		System.out.println("----------------");
		
		System.out.println("Ahora los usuarios por ID");
		System.out.println(UsuariosDAO.findByID(2));
		
		System.out.println("----------------");
		
		System.out.println("Ahora las promociones");
		System.out.println("PROMOCIONES:");
		List<Promocion> promos = PromocionDAO.findAll();
		for (Promocion p : promos) {
			System.out.println(p);
		}

	}
}
