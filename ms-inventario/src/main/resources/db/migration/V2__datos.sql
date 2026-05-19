INSERT INTO inventario (producto_id, ubicacion_bodega, cantidad_disponible, stock_minimo_alerta, activo, fecha_ultima_revision) VALUES
(1, 'Pasillo 4, Estante A', 150, 20, true, '2026-05-10'),
(2, 'Pasillo 2, Estante B', 5, 10, true, '2026-05-12'),
 (3, 'Zona Fría, Rack 1', 0, 50, false, '2026-05-01');

INSERT INTO movimiento_stock (inventario_id, tipo_movimiento, motivo_razon, cantidad_moviendo, aprobado, fecha_movimiento) VALUES
(1, 'ENTRADA', 'Compra a proveedor', 50, true, '2026-05-11'),
(1, 'SALIDA', 'Venta Pedido 100', 2, true, '2026-05-13'),
(2, 'MERMA', 'Producto dañado en manipulacion', 1, false, '2026-05-13');