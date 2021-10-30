package model;


import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import dao.UsuariosDAO;

public class PerfilUsuario {

	private double presupuesto;

	private double tiempoDisponible;
	private String nombre;
	private TipoDeAtraccion tipoDeAtraccion;
	private Itinerario itinerario;
	private int id;

	public PerfilUsuario(String nombre, double presupuesto, int tiempoDisponible, TipoDeAtraccion tipoDeAtraccion, int id) {
		this.nombre = nombre;
		this.presupuesto = presupuesto;
		this.tiempoDisponible = tiempoDisponible;
		this.tipoDeAtraccion = tipoDeAtraccion;
		this.itinerario = new Itinerario();
		this.id = id;
	}
	
	

	public PerfilUsuario(String nombre, double presupuesto, int tiempoDisponible, TipoDeAtraccion tipoDeAtraccion, int id, List<Facturable> listaDeItinerario) {
		super();
		this.presupuesto = presupuesto;
		this.tiempoDisponible = tiempoDisponible;
		this.nombre = nombre;
		this.tipoDeAtraccion = tipoDeAtraccion;
		this.id = id;
		this.itinerario = new Itinerario (listaDeItinerario);
	}



	@Override
	public String toString() {
		return "Nombre: " + nombre+ "\nMonedas Disponibles: " + presupuesto + "\nTiempo Disponible: " + tiempoDisponible
				+ "\nTipo de atraccion: " + tipoDeAtraccion + " ";
	}

	public double getPresupuesto() {

		return presupuesto;
	}

	public double getTiempoDisponible() {

		return tiempoDisponible;
	}

	public TipoDeAtraccion getTipoDeAtraccion() {

		return tipoDeAtraccion;
	}

	public String getNombre() {

		return nombre;
	}

	public void reservarTiempoYdinero(Facturable atraccion){
		this.tiempoDisponible -= atraccion.obtenerTiempoTotal();
		this.presupuesto -= atraccion.obtenerCostoTotal();
	}

	public boolean tieneTiempoYdinero() {

		return this.tiempoDisponible > 0 && this.presupuesto > 0 ;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PerfilUsuario that = (PerfilUsuario) o;
		return Double.compare(that.getPresupuesto(), getPresupuesto()) == 0 && Double.compare(that.getTiempoDisponible(), getTiempoDisponible()) == 0 && getNombre().equals(that.getNombre()) && getTipoDeAtraccion() == that.getTipoDeAtraccion();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPresupuesto(), getTiempoDisponible(), getNombre(), getTipoDeAtraccion());
	}
	
	public void update() throws SQLException {
		UsuariosDAO.updateUsuarios(id, this.presupuesto, this.tiempoDisponible);
		this.itinerario.update(this.id);
	}

	public void agregarAtraccion(Facturable atraccion) throws SQLException {
		
		this.itinerario.agregarAtraccion(atraccion);
		atraccion.restarCupo();
		this.reservarTiempoYdinero(atraccion);
	}

	public boolean puedeComprar(Facturable atraccion) {
		return  !this.itinerario.poseeAtraccion(atraccion)
				&& atraccion.obtenerCostoTotal() <= this.presupuesto
				&& atraccion.obtenerTiempoTotal() <= this.tiempoDisponible
				&& atraccion.hayCupo();
	}

	public Itinerario getItinerario(){
		return  this.itinerario;
	}

}
