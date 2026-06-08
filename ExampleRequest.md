# Ejemplos de Request Body

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
{E
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
