drop table if EXISTS "itinerarios";
drop table if EXISTS "usuarios";
drop table if EXISTS "atracciones_promocion";
Drop TABLE if EXISTS "promociones";
Drop TABLE IF EXISTS "atracciones";
DROP TABLE IF EXISTS "tipo_de_atracciones";

CREATE TABLE "tipo_de_atracciones" (
	"id"	INTEGER,
	"nombre_tipo"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "atracciones" (
	"id"	INTEGER,
	"tipo"	INTEGER NOT NULL,
	"nombre"	TEXT,
    "costo_visita" INTEGER,
    "tiempo_promedio" INTEGER,
    "cupo_diario" INTEGER,
	FOREIGN KEY("tipo") REFERENCES "tipo_de_atracciones"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "promociones" (
	"id"	INTEGER,
	"nombre"	TEXT NOT NULL,
    "tipo_atraccion" INTEGER Not Null,
    "costo_fijo" Integer ,
    "atraccion_extra" INTEGER ,
    "porcentaje" Integer, 
    FOREIGN KEY("tipo_atraccion") REFERENCES "tipo_de_atracciones"("id"),
    FOREIGN KEY("atraccion_extra") REFERENCES "atracciones"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "atracciones_promocion" (
    "id" INTEGER,
    "promocion_id" Integer Not Null,
    "atraccion_id" Integer Not Null,
    FOREIGN KEY("promocion_id") REFERENCES "promociones"("id"),
    FOREIGN KEY("atraccion_id") REFERENCES "atracciones"("id"),
    PRIMARY KEY("id" AUTOINCREMENT)
);



CREATE TABLE "usuarios" (
	"id"	INTEGER,
	"nombre"	TEXT NOT NULL,
    "presupuesto" INTEGER NOT NULL,
    "tiempo_disponible" INTEGER NOT NULL,
	"atraccion_preferida" INTEGER NOT NULL,
	FOREIGN KEY("atraccion_preferida") REFERENCES "tipo_de_atracciones"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE "itinerarios" (
	"id"	INTEGER,
	"usuario"	INTEGER NOT NULL,
	"atraccion" INTEGER,
	"promocion" INTEGER,
	FOREIGN KEY("usuario") REFERENCES "usuarios"("id"),
	FOREIGN KEY("atraccion") REFERENCES "atracciones"("id"),
	FOREIGN KEY ("promocion") REFERENCES "promociones"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);

Insert into tipo_de_atracciones(nombre_tipo) values("AVENTURA");
Insert into tipo_de_atracciones(nombre_tipo) values("DEGUSTACION");
Insert into tipo_de_atracciones(nombre_tipo) values("PAISAJE");


Insert into atracciones(nombre, costo_visita,tiempo_promedio,cupo_diario,tipo) values('Moria',10.0,2,6,1);
Insert into atracciones(nombre, costo_visita,tiempo_promedio,cupo_diario,tipo) values('Minas Tirith',5.0,2.5,25,3);
Insert into atracciones(nombre, costo_visita,tiempo_promedio,cupo_diario,tipo) values('La Comarca',3.0,6.5,15,2);
Insert into atracciones(nombre, costo_visita,tiempo_promedio,cupo_diario,tipo) values('Mordor',25.0,3,4,1);
Insert into atracciones(nombre, costo_visita,tiempo_promedio,cupo_diario,tipo) values('Abismo de Helm',5.0,2,15,3);
Insert into atracciones(nombre, costo_visita,tiempo_promedio,cupo_diario,tipo) values('Lothlorien',35,1,30,2);
Insert into atracciones(nombre, costo_visita,tiempo_promedio,cupo_diario,tipo) values('Erebor',12,3,32,3);
Insert into atracciones(nombre, costo_visita,tiempo_promedio,cupo_diario,tipo) values('Bosque Negro',3,4,12,1);


Insert into promociones(nombre, tipo_atraccion, atraccion_extra) values('Pack paisajes',3,7);

Insert into atracciones_promocion(promocion_id,atraccion_id) values (1,2);
Insert into atracciones_promocion(promocion_id,atraccion_id) values (1,5);

Insert into promociones(nombre, tipo_atraccion, costo_fijo) values('Pack Degustacion',2,36);

Insert into atracciones_promocion(promocion_id,atraccion_id) values( 2,6);
Insert into atracciones_promocion(promocion_id,atraccion_id) values( 2,3);

Insert into promociones(nombre, tipo_atraccion, porcentaje) values('Pack Aventura',1,20);
Insert into atracciones_promocion(promocion_id,atraccion_id) values( 3,8);
Insert into atracciones_promocion(promocion_id,atraccion_id) values( 3,4);

Insert into usuarios(nombre,presupuesto,tiempo_disponible,atraccion_preferida) values("Irina",100.0,50,1);
Insert into usuarios(nombre,presupuesto,tiempo_disponible,atraccion_preferida) values("Micaela",5.0,5,2);
Insert into usuarios(nombre,presupuesto,tiempo_disponible,atraccion_preferida) values("Josy",12.0,7,3);
Insert into usuarios(nombre,presupuesto,tiempo_disponible,atraccion_preferida) values("Mariano",4.0,4,1);
Insert into usuarios(nombre,presupuesto,tiempo_disponible,atraccion_preferida) values("Eowyn",10.0,8,1);
Insert into usuarios(nombre,presupuesto,tiempo_disponible,atraccion_preferida) values("Gandalf",100.0,5,3);
Insert into usuarios(nombre,presupuesto,tiempo_disponible,atraccion_preferida) values("Sam",36.0,8,2);
Insert into usuarios(nombre,presupuesto,tiempo_disponible,atraccion_preferida) values("Galadriel",120.0,2,3);

