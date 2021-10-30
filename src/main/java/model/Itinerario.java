package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ItinerarioDAO;

public class Itinerario {

	private double horasNecesarias;
	private double costoMonedas;
	private List<Facturable> ListaDeVisitas = new ArrayList<Facturable>();


	public Itinerario(List<Facturable> listaDeItinerario) {
		for (Facturable facturable : listaDeItinerario) {
			agregarAtraccion(facturable);
		}
	}
	

	public Itinerario() {
		super();
	}


	public double getHorasNecesarias() {
		return horasNecesarias;
	}

	public double getCostoMonedas() {
		return costoMonedas;
	}
	
	public void update(int id) throws SQLException{
		
		for(Facturable facturable : ListaDeVisitas) {
			facturable.update();
			
			int atraccionId = 0;
			int promocionId = 0;
			
			if (!facturable.esPromocion())
			{
				atraccionId = facturable.getID();
			}
			else
			{
				promocionId = facturable.getID();
			}
			// solo agregamos al itinerario si no existe ya en la base de datos.
			if(!ItinerarioDAO.existe(id, atraccionId, promocionId)) {
				ItinerarioDAO.insertar(id, atraccionId, promocionId);
			}
		}
	}

	public List<Facturable> getListaDeVisitas() {

		return this.ListaDeVisitas;
	}

	public void agregarAtraccion(Facturable atraccion) {
		
		this.ListaDeVisitas.add(atraccion);
		horasNecesarias += atraccion.obtenerTiempoTotal();
		costoMonedas += atraccion.obtenerCostoTotal();
	}
	 
	public boolean poseeAtraccion(Facturable atraccion) {
		for(Facturable visitas: this.ListaDeVisitas){
			if(visitas.seEncuentraEnElFacturable(atraccion)){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String salida = "Itinerario: " +
				", horasNecesarias = " + horasNecesarias +
				", costoMonedas = " + costoMonedas +
				"\nListaDeVisitas = \n";
		for (Facturable facturable : ListaDeVisitas) {
			salida += facturable.toString();
		}
		return salida;
	}
}
