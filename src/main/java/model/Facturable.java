package model;

import java.sql.SQLException;

public interface Facturable {

	 double obtenerCostoTotal();

	 double obtenerTiempoTotal();

	 boolean hayCupo();

	 void restarCupo() throws SQLException;

	 boolean esPromocion();

	 TipoDeAtraccion getTipo();

	 boolean seEncuentraEnElFacturable(Facturable facturable);

}
