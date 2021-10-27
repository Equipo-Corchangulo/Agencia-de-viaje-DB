package app;

import dao.AtraccionesDAO;
import dao.PromocionDAO;
import model.Atraccion;
import model.Facturable;
import model.Promocion;
import dao.UsuariosDAO;
import model.Atraccion;
import model.PerfilUsuario;

import java.sql.SQLException;
import java.util.List;

public class App {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		//dbc:sqlite:C:\Users\josel\eclipse-workspace\conexion_db_agenciadeturismo\tierramedia.db
		System.out.println("ahora buscar");
		System.out.println(AtraccionesDAO.findByID(1));
		//AtraccionesDAO.findByID(1).restarCupo();
		
		
		System.out.println("Ahora todas las atracciones");
		System.out.println("ATRACCIONES:");
		
		List<Atraccion> lista_atracciones = AtraccionesDAO.findAll();
		for(Atraccion a : lista_atracciones){
			System.out.println(a);
		}

		System.out.println("--------------------");
		System.out.println("USUARIOS:");
		
		List<PerfilUsuario> lista_usuarios = UsuariosDAO.findAll();
		for(PerfilUsuario u : lista_usuarios){
			System.out.println(u);
		}
		

	}
}
