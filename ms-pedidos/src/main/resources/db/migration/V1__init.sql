CREATE TABLE IF NOT EXISTS pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    codigo_seguimiento VARCHAR(50) NOT NULL,
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE (10, 2) NOT NULL,
    pagado BOOLEAN DEFAULT FALSE,
    direccion_envio VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS detalles_pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DOUBLE (10, 2) NOT NULL,
    descuento_aplicado BOOLEAN DEFAULT FALSE,
    fecha_agregado DATETIME DEFAULT CURRENT_TIMESTAMP,
    notas_adicionales VARCHAR(100),
    CONSTRAINT fk_pedido_detalle FOREIGN KEY (pedido_id)
    REFERENCES pedidos(id) ON DELETE CASCADE
    );

INSERT INTO pedidos (cliente_id, codigo_seguimiento, total, pagado, direccion_envio)
VALUES (1, 'TRK-1001', 70000.00, true, 'Av. Siempre Viva 123');

INSERT INTO pedidos (cliente_id, codigo_seguimiento, total, pagado, direccion_envio)
VALUES (2, 'TRK-1002', 25000.00, false, 'Calle Falsa 456');

INSERT INTO pedidos (cliente_id, codigo_seguimiento, total, pagado, direccion_envio)
VALUES (1, 'TRK-1003', 180000.00, true, 'Av. Siempre Viva 123');

INSERT INTO detalles_pedidos (pedido_id, producto_id, cantidad, precio_unitario, notas_adicionales)
VALUES (1, 1, 1, 45000.00, 'Entregar en portería');

INSERT INTO detalles_pedidos (pedido_id, producto_id, cantidad, precio_unitario, notas_adicionales)
VALUES (1, 2, 1, 25000.00, 'Sin notas');

INSERT INTO detalles_pedidos (pedido_id, producto_id, cantidad, precio_unitario, notas_adicionales)
VALUES (2, 2, 1, 25000.00, 'Llamar antes de entregar');

