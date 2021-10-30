package app;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import jdbc.ConnectionProvider;
import model.Facturable;
import model.PerfilUsuario;

public class Recomendador {

	private List<PerfilUsuario> listaDeUsuarios;

	private List<Facturable> listaDeFacturables;

	public Recomendador(List<PerfilUsuario> listaDeUsuarios, List<Facturable> listaDeFacturables) {
		super();
		this.listaDeUsuarios = listaDeUsuarios;
		this.listaDeFacturables = listaDeFacturables;
	}

	

	public boolean leer() {

		//si el usuario ingresa 1 es verdadero sino no.

		@SuppressWarnings("resource")
				int entradaDelUsuario = 10;
		Scanner teclado = new Scanner(System.in);
		while (entradaDelUsuario != 0 && entradaDelUsuario != 1){

			System.out.println("Presione 1 para agregar esta atraccion a su itinerario y 0 para pasar a la siguiente recomendacion.");
			if (teclado.hasNextInt())
				entradaDelUsuario = teclado.nextInt();
			else
				entradaDelUsuario = 10;
		}

		return entradaDelUsuario == 1;

	}

	public void ofrecerSugerencias() throws SQLException {

		for(PerfilUsuario usuario : listaDeUsuarios) {
			System.out.println("-------------------------------");
			System.out.println(usuario);
			System.out.println("-------------------------------");
			System.out.println();

			Collections.sort(listaDeFacturables, new ComparadorDeFacturable(usuario.getTipoDeAtraccion()));

			iterarSugerencias(usuario);
			this.guardarItinerarioEnBD(usuario);
			System.out.println("================================");
			System.out.println("Resultados");
			System.out.println(usuario);
			System.out.println(usuario.getItinerario());
			System.out.println("==============================");
			System.out.println();
			
		}
	}
	
	public void guardarItinerarioEnBD(PerfilUsuario usuario) throws SQLException {
		Connection conn = ConnectionProvider.getConnection();
		conn.setAutoCommit(false);
		try {
			usuario.update();
			
		}catch(SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.commit();
		}
	}
	
	public void iterarSugerencias(PerfilUsuario usuario) throws SQLException {
		for (Facturable atraccion :listaDeFacturables) {
			if (!usuario.tieneTiempoYdinero()){
				return;
			}
			else if(usuario.puedeComprar(atraccion)){

				System.out.println(atraccion);
	
				//si el usuario lo quiere la agregamos al itinerario
				if(leer()) {
					usuario.agregarAtraccion(atraccion);
				}
			}
		}
	}

}
