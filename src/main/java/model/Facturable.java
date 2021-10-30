package model;

import java.sql.SQLException;

public interface Facturable {

	 double obtenerCostoTotal();

	 double obtenerTiempoTotal();

	 boolean hayCupo();

	 void restarCupo();

	 boolean esPromocion();

	 TipoDeAtraccion getTipo();

	 boolean seEncuentraEnElFacturable(Facturable facturable);
	 
	 void update() throws SQLException;
	 
	 int getID();

}
