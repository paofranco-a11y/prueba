
CREATE TABLE IF NOT EXISTS inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT NOT NULL,
    ubicacion_bodega VARCHAR(100) NOT NULL,
    cantidad_disponible INT NOT NULL,
    stock_minimo_alerta INT NOT NULL,
    activo BOOLEAN NOT NULL,
    fecha_ultima_revision DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS movimiento_stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    inventario_id INT NOT NULL,
    Tipo_movimiento VARCHAR(50) NOT NULL,
    motivo_razon VARCHAR(200),
    cantidad_moviendo INT NOT NULL,
    aprobado BOOLEAN NOT NULL,
    fecha_movimiento DATETIME NOT NULL,
    FOREIGN KEY (inventario_id) REFERENCES inventario(id)
);