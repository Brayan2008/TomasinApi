# Ejemplos de Request Body

## Índice

1. [Marca](#marca)
2. [Rol](#rol)
3. [Servicio (Catálogo)](#servicio-catalogo)
4. [Cliente](#cliente)
5. [Modelo](#modelo)
6. [Vehículo](#vehiculo)
7. [Orden de Servicio](#orden-de-servicio)
8. [Servicio Asignado](#servicio-asignado)
9. [Checklist de Orden](#checklist-de-orden)
10. [Daño de Orden](#daño-de-orden)
11. [Foto de Orden](#foto-de-orden)
12. [Usuario](#usuario)
13. [Login](#login)

---

## Marca

### POST / PUT `/api/marcas`
```json
{
  "nombre": "Toyota"
}
```

---

## Rol

### POST / PUT `/api/roles`
```json
{
  "nombre": "ADMINISTRADOR"
}
```
> Valores validos para `nombre`: `ADMINISTRADOR`, `MECANICO`

---

## Servicio (Catalogo)

### POST / PUT `/api/servicios`
```json
{
  "nombre": "Cambio de aceite",
  "descripcion": "Cambio de aceite y filtro de motor",
  "precioBase": 150.00
}
```

---

## Cliente

### POST / PUT `/api/clientes`
```json
{
  "tipoDocumento": "DNI",
  "numeroDocumento": "12345678",
  "nombres": "Juan Carlos",
  "apellidos": "Perez Lopez",
  "telefono": "987654321",
  "direccion": "Av. Siempre Viva 123"
}
```
> Valores validos para `tipoDocumento`: `DNI`, `RUC`

---

## Modelo

### POST / PUT `/api/modelos`
```json
{
  "nombre": "Corolla",
  "idMarca": 1
}
```

---

## Vehiculo

### POST / PUT `/api/vehiculos`
```json
{
  "placa": "ABC-123",
  "chasis": "1HGBH41JXMN109186",
  "motor": "2ZR-FE",
  "color": "Rojo",
  "anio": 2022,
  "idModelo": 1,
  "idCliente": 1
}
```

---

## Orden de Servicio

### POST / PUT `/api/ordenes`
```json
{
  "idCliente": 1,
  "idVehiculo": 1,
  "kilometraje": 50000,
  "nivelCombustible": "F",
  "fechaEstimadaEntrega": "2026-06-15",
  "observaciones": "Revisión general y cambio de frenos"
}
```
> Valores validos para `nivelCombustible`: `E`, `_1_4`, `_1_2`, `_3_4`, `F`

### Cancelar orden `DELETE /api/ordenes/{id}?motivo=Falta de repuestos`

---

## Servicio Asignado

### POST / PUT `/api/servicios-asignados`
```json
{
  "precioAcordado": 250.00,
  "idOrden": 1,
  "idServicioCatalogo": 1,
  "idMecanico": 2
}
```

### Cancelar servicio `DELETE /api/servicios-asignados/{id}?motivo=Cliente canceló`

---

## Checklist de Orden

### POST / PUT `/api/checklist`
```json
{
  "itemIndex": 1,
  "respuesta": "SI",
  "cantidad": null,
  "observacion": "Sin observaciones",
  "idOrden": 1
}
```
> Valores validos para `respuesta`: `SI`, `NO`, `CANT`

---

## Daño de Orden

### POST / PUT `/api/danios`
```json
{
  "tipoDanio": "RAYADO",
  "posX": 45.50,
  "posY": 32.10,
  "observacion": "Raya en puerta trasera derecha",
  "origen": "MANUAL",
  "idFoto": null,
  "idOrden": 1
}
```
> Valores validos para `tipoDanio`: `QUINADO`, `ABOLLADO`, `RAYADO`
> Valores validos para `origen`: `MANUAL`, `IA`

---

## Foto de Orden

### POST `/api/fotos`
```
Content-Type: multipart/form-data

Campos:
  archivo: (imagen, obligatorio)
  idOrden: 1 (Long, obligatorio)
  procesadoIa: false (boolean, opcional, default false)
```

Ejemplo con curl:
```bash
curl -X POST http://localhost:8080/api/fotos \
  -H "Authorization: Bearer <token>" \
  -F "archivo=@foto_danio_001.jpg" \
  -F "idOrden=1" \
  -F "procesadoIa=true"
```

### PUT `/api/fotos/{id}`
```
Content-Type: multipart/form-data

Campos:
  archivo: (imagen, opcional — si se envía, reemplaza la foto anterior)
  idOrden: 1 (Long, obligatorio)
  procesadoIa: false (boolean, opcional, default false)
```

### GET `/api/fotos/archivo/{filename}`
Sirve la imagen almacenada. El `{filename}` es el nombre único (UUID) del archivo, que se obtiene del campo `ruta` en la respuesta del POST/GET.

---

## Usuario

### POST / PUT `/api/usuarios`
```json
{
  "nombre": "Carlos Sanchez",
  "email": "carlos@taller.com",
  "password": "password123",
  "rol": "MECANICO"
}
```
> Valores validos para `rol`: `ADMINISTRADOR`, `MECANICO`

---

## Login

### POST `/auth/login`
```json
{
  "email": "carlos@taller.com",
  "password": "password123"
}
```
