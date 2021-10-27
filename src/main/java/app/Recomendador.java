package app;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import model.Facturable;
import model.PerfilUsuario;

public class Recomendador {
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

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

			System.out.println(ANSI_CYAN+"Presione 1 para agregar esta atraccion a su itinerario y 0 para pasar a la siguiente recomendacion.");
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
			//ManejadorDeArchivo.guardarItinerarioEnArchivo(usuario);
			//TODO guardar itinerario en base de datos
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
