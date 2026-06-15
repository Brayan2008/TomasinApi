# Respuestas Esperadas — API Taller Tomasin v2

## Índice

- [Convenciones](#convenciones)
- [1. Auth — `/auth`](#1-auth--auth)
- [2. Usuarios — `/api/usuarios`](#2-usuarios--apiusuarios)
- [3. Roles — `/api/roles`](#3-roles--apiroles)
- [4. Clientes — `/api/clientes`](#4-clientes--apiclientes)
- [5. Marcas — `/api/marcas`](#5-marcas--apimarcas)
- [6. Modelos — `/api/modelos`](#6-modelos--apimodelos)
- [7. Vehículos — `/api/vehiculos`](#7-vehículos--apivehiculos)
- [8. Órdenes de Servicio — `/api/ordenes`](#8-órdenes-de-servicio--apiordenes)
- [9. Servicios Asignados — `/api/servicios-asignados`](#9-servicios-asignados--apiservicios-asignados)
- [10. Servicios (Catálogo) — `/api/servicios`](#10-servicios-catálogo--apiservicios)
- [11. Checklist de Orden — `/api/checklist`](#11-checklist-de-orden--apichecklist)
- [12. Daños de Orden — `/api/danios`](#12-daños-de-orden--apidanios)
- [13. Fotos de Orden — `/api/fotos`](#13-fotos-de-orden--apifotos)
- [Resumen de Enums](#resumen-de-enums)

---

## Convenciones

| Código | Significado |
|--------|------------|
| `200 OK` | Operación exitosa. Retorna el recurso en el body. |
| `201 Created` | Recurso creado exitosamente. Retorna el recurso en el body. |
| `204 No Content` | Operación exitosa sin contenido de retorno (actualización, eliminación). |
| `400 Bad Request` | Error de validación o de negocio. |
| `404 Not Found` | Recurso no encontrado. |

**Formato de error (400 / 404):**
```json
{
  "error": "mensaje descriptivo del error"
}
```

**Errores de validación (400):**
```json
{
  "nombreCampo": "mensaje de validación",
  "otroCampo": "otro mensaje"
}
```

---

## 1. Auth — `/auth`

### `POST /auth/login`

**ResponseBody `200 OK`:**
```json
{
  "token": "eyJhbGciOi...",
  "type": "Bearer",
  "expiresMs": 3600000
}
```

**Errores:**
| Código | Body |
|--------|------|
| `400` | `{"error": "Credenciales invalidas"}` |
| `400` | `{"error": "Usuario bloqueado. Contacte al administrador."}` |

---

## 2. Usuarios — `/api/usuarios`

### `GET /api/usuarios`

**ResponseBody `200 OK`** — `List<UsuarioResponseDTO>`:
```json
[
  {
    "id": 1,
    "nombre": "Carlos Sanchez",
    "email": "carlos@taller.com",
    "password": "$2a$10$...",
    "bloqueado": false,
    "intentosFallidos": 0,
    "fechaBloqueo": null,
    "rol": "MECANICO"
  }
]
```

**`204 No Content`** — Lista vacía.

---

### `GET /api/usuarios/{id}`

**ResponseBody `200 OK`** — `UsuarioResponseDTO`:
```json
{
  "id": 1,
  "nombre": "Carlos Sanchez",
  "email": "carlos@taller.com",
  "password": "$2a$10$...",
  "bloqueado": false,
  "intentosFallidos": 0,
  "fechaBloqueo": null,
  "rol": "ADMINISTRADOR"
}
```

| Código | Body |
|--------|------|
| `404` | `{"error": "Usuario no encontrado"}` |

---

### `POST /api/usuarios`

**RequestBody** `UsuarioRequestDTO`:
```json
{
  "nombre": "Carlos Sanchez",
  "email": "carlos@taller.com",
  "password": "password123",
  "rol": "MECANICO"
}
```
> `rol`: `ADMINISTRADOR` | `MECANICO`

**ResponseBody `201 Created`** — `UsuarioResponseDTO`:
```json
{
  "id": 2,
  "nombre": "Carlos Sanchez",
  "email": "carlos@taller.com",
  "password": "$2a$10$...",
  "bloqueado": false,
  "intentosFallidos": 0,
  "fechaBloqueo": null,
  "rol": "MECANICO"
}
```

| Código | Body |
|--------|------|
| `400` | `{"error": "El email ya esta registrado"}` |
| `400` | Errores de validación por campo |

---

### `PUT /api/usuarios/{id}`

**RequestBody** `UsuarioRequestDTO` (mismo que POST).

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Usuario no encontrado"}` |
| `400` | Errores de validación por campo |

---

### `DELETE /api/usuarios/{id}`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Usuario no encontrado"}` |

---

## 3. Roles — `/api/roles`

### `GET /api/roles`

**ResponseBody `200 OK`** — `List<RolResponseDTO>`:
```json
[
  {
    "id": 1,
    "nombre": "ADMINISTRADOR"
  },
  {
    "id": 2,
    "nombre": "MECANICO"
  }
]
```

**`204 No Content`** — Lista vacía.

---

### `GET /api/roles/{id}`

**ResponseBody `200 OK`** — `RolResponseDTO`:
```json
{
  "id": 1,
  "nombre": "ADMINISTRADOR"
}
```

| Código | Body |
|--------|------|
| `404` | `{"error": "Rol no encontrado"}` |

---

### `POST /api/roles`

**RequestBody** `RolRequestDTO`:
```json
{
  "nombre": "ADMINISTRADOR"
}
```
> `nombre`: `ADMINISTRADOR` | `MECANICO`

**ResponseBody `201 Created`** — `RolResponseDTO`:
```json
{
  "id": 3,
  "nombre": "ADMINISTRADOR"
}
```

---

### `PUT /api/roles/{id}`

**RequestBody** `RolRequestDTO` (mismo que POST).

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Rol no encontrado"}` |

---

### `DELETE /api/roles/{id}`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Rol no encontrado"}` |

---

## 4. Clientes — `/api/clientes`

### `GET /api/clientes`

**ResponseBody `200 OK`** — `List<ClienteResponseDTO>`:
```json
[
  {
    "id": 1,
    "tipoDocumento": "DNI",
    "numeroDocumento": "12345678",
    "nombres": "Juan Carlos",
    "apellidos": "Perez Lopez",
    "telefono": "987654321",
    "direccion": "Av. Siempre Viva 123"
  }
]
```

**`204 No Content`** — Lista vacía.

---

### `GET /api/clientes/{id}`

**ResponseBody `200 OK`** — `ClienteResponseDTO`:
```json
{
  "id": 1,
  "tipoDocumento": "DNI",
  "numeroDocumento": "12345678",
  "nombres": "Juan Carlos",
  "apellidos": "Perez Lopez",
  "telefono": "987654321",
  "direccion": "Av. Siempre Viva 123"
}
```

| Código | Body |
|--------|------|
| `404` | `{"error": "Cliente no encontrado"}` |

---

### `POST /api/clientes`

**RequestBody** `ClienteRequestDTO`:
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
> `tipoDocumento`: `DNI` | `RUC`

**ResponseBody `201 Created`** — `ClienteResponseDTO`:
```json
{
  "id": 2,
  "tipoDocumento": "DNI",
  "numeroDocumento": "12345678",
  "nombres": "Juan Carlos",
  "apellidos": "Perez Lopez",
  "telefono": "987654321",
  "direccion": "Av. Siempre Viva 123"
}
```

---

### `PUT /api/clientes/{id}`

**RequestBody** `ClienteRequestDTO` (mismo que POST).

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Cliente no encontrado"}` |

---

### `DELETE /api/clientes/{id}`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Cliente no encontrado"}` |

---

## 5. Marcas — `/api/marcas`

### `GET /api/marcas`

**ResponseBody `200 OK`** — `List<MarcaResponseDTO>`:
```json
[
  {
    "id": 1,
    "nombre": "Toyota"
  }
]
```

**`204 No Content`** — Lista vacía.

---

### `GET /api/marcas/{id}`

**ResponseBody `200 OK`** — `MarcaResponseDTO`:
```json
{
  "id": 1,
  "nombre": "Toyota"
}
```

| Código | Body |
|--------|------|
| `404` | `{"error": "Marca no encontrada"}` |

---

### `POST /api/marcas`

**RequestBody** `MarcaRequestDTO`:
```json
{
  "nombre": "Toyota"
}
```

**ResponseBody `201 Created`** — `MarcaResponseDTO`:
```json
{
  "id": 2,
  "nombre": "Toyota"
}
```

---

### `PUT /api/marcas/{id}`

**RequestBody** `MarcaRequestDTO` (mismo que POST).

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Marca no encontrada"}` |

---

### `DELETE /api/marcas/{id}`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Marca no encontrada"}` |

---

## 6. Modelos — `/api/modelos`

### `GET /api/modelos`

**ResponseBody `200 OK`** — `List<ModeloResponseDTO>`:
```json
[
  {
    "id": 1,
    "nombre": "Corolla",
    "marca": {
      "id": 1,
      "nombre": "Toyota"
    }
  }
]
```

**`204 No Content`** — Lista vacía.

---

### `GET /api/modelos/{id}`

**ResponseBody `200 OK`** — `ModeloResponseDTO`:
```json
{
  "id": 1,
  "nombre": "Corolla",
  "marca": {
    "id": 1,
    "nombre": "Toyota"
  }
}
```

| Código | Body |
|--------|------|
| `404` | `{"error": "Modelo no encontrado"}` |

---

### `POST /api/modelos`

**RequestBody** `ModeloRequestDTO`:
```json
{
  "nombre": "Corolla",
  "idMarca": 1
}
```

**ResponseBody `201 Created`** — `ModeloResponseDTO`:
```json
{
  "id": 2,
  "nombre": "Corolla",
  "marca": {
    "id": 1,
    "nombre": "Toyota"
  }
}
```

---

### `PUT /api/modelos/{id}`

**RequestBody** `ModeloRequestDTO` (mismo que POST).

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Modelo no encontrado"}` |

---

### `DELETE /api/modelos/{id}`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Modelo no encontrado"}` |

---

## 7. Vehículos — `/api/vehiculos`

### `GET /api/vehiculos`

**ResponseBody `200 OK`** — `List<VehiculoResponseDTO>`:
```json
[
  {
    "id": 1,
    "placa": "ABC-123",
    "chasis": "1HGBH41JXMN109186",
    "motor": "2ZR-FE",
    "color": "Rojo",
    "anio": 2022,
    "ultimoKm": 50000,
    "modelo": {
      "id": 1,
      "nombre": "Corolla",
      "marca": {
        "id": 1,
        "nombre": "Toyota"
      }
    },
    "cliente": {
      "id": 1,
      "tipoDocumento": "DNI",
      "numeroDocumento": "12345678",
      "nombres": "Juan Carlos",
      "apellidos": "Perez Lopez",
      "telefono": "987654321",
      "direccion": "Av. Siempre Viva 123"
    }
  }
]
```

**`204 No Content`** — Lista vacía.

---

### `GET /api/vehiculos/{id}`

**ResponseBody `200 OK`** — `VehiculoResponseDTO`:
```json
{
  "id": 1,
  "placa": "ABC-123",
  "chasis": "1HGBH41JXMN109186",
  "motor": "2ZR-FE",
  "color": "Rojo",
  "anio": 2022,
  "ultimoKm": 50000,
  "modelo": {
    "id": 1,
    "nombre": "Corolla",
    "marca": {
      "id": 1,
      "nombre": "Toyota"
    }
  },
  "cliente": {
    "id": 1,
    "tipoDocumento": "DNI",
    "numeroDocumento": "12345678",
    "nombres": "Juan Carlos",
    "apellidos": "Perez Lopez",
    "telefono": "987654321",
    "direccion": "Av. Siempre Viva 123"
  }
}
```

| Código | Body |
|--------|------|
| `404` | `{"error": "Vehículo no encontrado"}` |

---

### `POST /api/vehiculos`

**RequestBody** `VehiculoRequestDTO`:
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

**ResponseBody `201 Created`** — `VehiculoResponseDTO`:
```json
{
  "id": 2,
  "placa": "ABC-123",
  "chasis": "1HGBH41JXMN109186",
  "motor": "2ZR-FE",
  "color": "Rojo",
  "anio": 2022,
  "ultimoKm": null,
  "modelo": {
    "id": 1,
    "nombre": "Corolla",
    "marca": {
      "id": 1,
      "nombre": "Toyota"
    }
  },
  "cliente": {
    "id": 1,
    "tipoDocumento": "DNI",
    "numeroDocumento": "12345678",
    "nombres": "Juan Carlos",
    "apellidos": "Perez Lopez",
    "telefono": "987654321",
    "direccion": "Av. Siempre Viva 123"
  }
}
```

---

### `PUT /api/vehiculos/{id}`

**RequestBody** `VehiculoRequestDTO` (mismo que POST).

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Vehículo no encontrado"}` |

---

### `DELETE /api/vehiculos/{id}`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Vehículo no encontrado"}` |

---

## 8. Órdenes de Servicio — `/api/ordenes`

### `GET /api/ordenes`

**ResponseBody `200 OK`** — `List<OrdenResponseDTO>`:
```json
[
  {
    "id": 1,
    "numeroCorrelativo": "OS-2026-0001",
    "fechaIngreso": "2026-06-11T09:30:00",
    "fechaEstimadaEntrega": "2026-06-15",
    "fechaEntregaReal": null,
    "estado": "REGISTRADA",
    "idCliente": 1,
    "nombreCliente": "Juan Carlos Perez Lopez",
    "vehiculo": {
      "id": 1,
      "placa": "ABC-123",
      "chasis": "1HGBH41JXMN109186",
      "motor": "2ZR-FE",
      "color": "Rojo",
      "anio": 2022,
      "ultimoKm": 50000,
      "modelo": {
        "id": 1,
        "nombre": "Corolla",
        "marca": {
          "id": 1,
          "nombre": "Toyota"
        }
      },
      "cliente": {
        "id": 1,
        "tipoDocumento": "DNI",
        "numeroDocumento": "12345678",
        "nombres": "Juan Carlos",
        "apellidos": "Perez Lopez",
        "telefono": "987654321",
        "direccion": "Av. Siempre Viva 123"
      }
    },
    "kilometraje": 50000,
    "nivelCombustible": "F",
    "costoTotal": 400.00,
    "observaciones": "Revisión general y cambio de frenos",
    "motivoAnulacion": null,
    "fechaAnulacion": null
  }
]
```
> `estado`: `REGISTRADA` | `EN_REPARACION` | `FINALIZADA` | `ENTREGADA` | `ANULADA`
> `nivelCombustible`: `E` | `_1_4` | `_1_2` | `_3_4` | `F`

**`204 No Content`** — Lista vacía.

---

### `GET /api/ordenes/{id}`

**ResponseBody `200 OK`** — `OrdenResponseDTO` (mismo formato que arriba).

| Código | Body |
|--------|------|
| `404` | `{"error": "Orden no encontrada"}` |

---

### `POST /api/ordenes`

**RequestBody** `OrdenRequestDTO`:
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

**ResponseBody `201 Created`** — `OrdenResponseDTO`:
```json
{
  "id": 2,
  "numeroCorrelativo": "OS-2026-0002",
  "fechaIngreso": "2026-06-11T10:00:00",
  "fechaEstimadaEntrega": "2026-06-15",
  "fechaEntregaReal": null,
  "estado": "REGISTRADA",
  "idCliente": 1,
  "nombreCliente": "Juan Carlos Perez Lopez",
  "vehiculo": {
    "id": 1,
    "placa": "ABC-123",
    "chasis": "1HGBH41JXMN109186",
    "motor": "2ZR-FE",
    "color": "Rojo",
    "anio": 2022,
    "ultimoKm": 50000,
    "modelo": {
      "id": 1,
      "nombre": "Corolla",
      "marca": {
        "id": 1,
        "nombre": "Toyota"
      }
    },
    "cliente": {
      "id": 1,
      "tipoDocumento": "DNI",
      "numeroDocumento": "12345678",
      "nombres": "Juan Carlos",
      "apellidos": "Perez Lopez",
      "telefono": "987654321",
      "direccion": "Av. Siempre Viva 123"
    }
  },
  "kilometraje": 50000,
  "nivelCombustible": "F",
  "costoTotal": 0.00,
  "observaciones": "Revisión general y cambio de frenos",
  "motivoAnulacion": null,
  "fechaAnulacion": null
}
```

---

### `PUT /api/ordenes/{id}`

**RequestBody** `OrdenRequestDTO` (mismo que POST).

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Orden no encontrada"}` |

---

### `DELETE /api/ordenes/{id}?motivo=Falta de repuestos`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Orden no encontrada"}` |

---

## 9. Servicios Asignados — `/api/servicios-asignados`

### `GET /api/servicios-asignados`

**ResponseBody `200 OK`** — `List<ServicioAsignadoResponseDTO>`:
```json
[
  {
    "id": 1,
    "precioAcordado": 250.00,
    "estadoServicio": "PENDIENTE",
    "fechaInicio": null,
    "fechaFin": null,
    "motivoCancelacion": null,
    "idOrden": 1,
    "idServicioCatalogo": 1,
    "nombreServicio": "Cambio de aceite",
    "idMecanico": 2,
    "nombreMecanico": "Carlos Sanchez"
  }
]
```
> `estadoServicio`: `PENDIENTE` | `EN_PROGRESO` | `COMPLETADO` | `CANCELADO`

**`204 No Content`** — Lista vacía.

---

### `GET /api/servicios-asignados/{id}`

**ResponseBody `200 OK`** — `ServicioAsignadoResponseDTO` (mismo formato que arriba).

| Código | Body |
|--------|------|
| `404` | `{"error": "Servicio asignado no encontrado"}` |

---

### `POST /api/servicios-asignados`

**RequestBody** `ServicioAsignadoRequestDTO`:
```json
{
  "precioAcordado": 250.00,
  "idOrden": 1,
  "idServicioCatalogo": 1,
  "idMecanico": 2
}
```

**ResponseBody `201 Created`** — `ServicioAsignadoResponseDTO`:
```json
{
  "id": 2,
  "precioAcordado": 250.00,
  "estadoServicio": "PENDIENTE",
  "fechaInicio": null,
  "fechaFin": null,
  "motivoCancelacion": null,
  "idOrden": 1,
  "idServicioCatalogo": 1,
  "nombreServicio": "Cambio de aceite",
  "idMecanico": 2,
  "nombreMecanico": "Carlos Sanchez"
}
```

---

### `PUT /api/servicios-asignados/{id}`

**RequestBody** `ServicioAsignadoRequestDTO` (mismo que POST).

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Servicio asignado no encontrado"}` |

---

### `DELETE /api/servicios-asignados/{id}?motivo=Cliente canceló`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Servicio asignado no encontrado"}` |

---

## 10. Servicios (Catálogo) — `/api/servicios`

### `GET /api/servicios`

**ResponseBody `200 OK`** — `List<ServicioResponseDTO>`:
```json
[
  {
    "id": 1,
    "nombre": "Cambio de aceite",
    "descripcion": "Cambio de aceite y filtro de motor",
    "precioBase": 150.00
  }
]
```

**`204 No Content`** — Lista vacía.

---

### `GET /api/servicios/{id}`

**ResponseBody `200 OK`** — `ServicioResponseDTO`:
```json
{
  "id": 1,
  "nombre": "Cambio de aceite",
  "descripcion": "Cambio de aceite y filtro de motor",
  "precioBase": 150.00
}
```

| Código | Body |
|--------|------|
| `404` | `{"error": "Servicio no encontrado"}` |

---

### `POST /api/servicios`

**RequestBody** `ServicioRequestDTO`:
```json
{
  "nombre": "Cambio de aceite",
  "descripcion": "Cambio de aceite y filtro de motor",
  "precioBase": 150.00
}
```

**ResponseBody `201 Created`** — `ServicioResponseDTO`:
```json
{
  "id": 2,
  "nombre": "Cambio de aceite",
  "descripcion": "Cambio de aceite y filtro de motor",
  "precioBase": 150.00
}
```

---

### `PUT /api/servicios/{id}`

**RequestBody** `ServicioRequestDTO` (mismo que POST).

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Servicio no encontrado"}` |

---

### `DELETE /api/servicios/{id}`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Servicio no encontrado"}` |

---

## 11. Checklist de Orden — `/api/checklist`

### `GET /api/checklist`

**ResponseBody `200 OK`** — `List<OrdenChecklistResponseDTO>`:
```json
[
  {
    "id": 1,
    "itemIndex": 1,
    "respuesta": "SI",
    "cantidad": null,
    "observacion": "Sin observaciones",
    "idOrden": 1
  }
]
```
> `respuesta`: `SI` | `NO` | `CANT`

**`204 No Content`** — Lista vacía.

---

### `GET /api/checklist/{id}`

**ResponseBody `200 OK`** — `OrdenChecklistResponseDTO` (mismo formato de arriba).

| Código | Body |
|--------|------|
| `404` | `{"error": "Checklist no encontrado"}` |

---

### `POST /api/checklist`

**RequestBody** `OrdenChecklistRequestDTO`:
```json
{
  "itemIndex": 1,
  "respuesta": "SI",
  "cantidad": null,
  "observacion": "Sin observaciones",
  "idOrden": 1
}
```

**ResponseBody `201 Created`** — `OrdenChecklistResponseDTO`:
```json
{
  "id": 2,
  "itemIndex": 1,
  "respuesta": "SI",
  "cantidad": null,
  "observacion": "Sin observaciones",
  "idOrden": 1
}
```

---

### `PUT /api/checklist/{id}`

**RequestBody** `OrdenChecklistRequestDTO` (mismo que POST).

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Checklist no encontrado"}` |

---

### `DELETE /api/checklist/{id}`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Checklist no encontrado"}` |

---

## 12. Daños de Orden — `/api/danios`

### `GET /api/danios`

**ResponseBody `200 OK`** — `List<OrdenDanioResponseDTO>`:
```json
[
  {
    "id": 1,
    "tipoDanio": "RAYADO",
    "posX": 45.50,
    "posY": 32.10,
    "observacion": "Raya en puerta trasera derecha",
    "origen": "MANUAL",
    "idFoto": null,
    "idOrden": 1
  }
]
```
> `tipoDanio`: `QUINADO` | `ABOLLADO` | `RAYADO`
> `origen`: `MANUAL` | `IA`

**`204 No Content`** — Lista vacía.

---

### `GET /api/danios/{id}`

**ResponseBody `200 OK`** — `OrdenDanioResponseDTO` (mismo formato de arriba).

| Código | Body |
|--------|------|
| `404` | `{"error": "Daño no encontrado"}` |

---

### `POST /api/danios`

**RequestBody** `OrdenDanioRequestDTO`:
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

**ResponseBody `201 Created`** — `OrdenDanioResponseDTO`:
```json
{
  "id": 2,
  "tipoDanio": "RAYADO",
  "posX": 45.50,
  "posY": 32.10,
  "observacion": "Raya en puerta trasera derecha",
  "origen": "MANUAL",
  "idFoto": null,
  "idOrden": 1
}
```

---

### `PUT /api/danios/{id}`

**RequestBody** `OrdenDanioRequestDTO` (mismo que POST).

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Daño no encontrado"}` |

---

### `DELETE /api/danios/{id}`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Daño no encontrado"}` |

---

## 13. Fotos de Orden — `/api/fotos`

### `GET /api/fotos`

**ResponseBody `200 OK`** — `List<FotoOrdenResponseDTO>`:
```json
[
  {
    "id": 1,
    "nombreArchivo": "foto_danio_001.jpg",
    "ruta": "a1b2c3d4-e5f6-7890-abcd-ef1234567890.jpg",
    "fechaSubida": "2026-06-11T09:35:00",
    "hashArchivo": "d41d8cd98f00b204e9800998ecf8427e",
    "procesadoIa": true,
    "idOrden": 1
  }
]
```

**`204 No Content`** — Lista vacía.

---

### `GET /api/fotos/{id}`

**ResponseBody `200 OK`** — `FotoOrdenResponseDTO` (mismo formato de arriba).

| Código | Body |
|--------|------|
| `404` | `{"error": "Foto no encontrada"}` |

---

### `GET /api/fotos/archivo/{filename}`

**Response `200 OK`** — Archivo binario (`image/jpeg`, `image/png`, etc.).

| Código | Body |
|--------|------|
| `404` | `{"error": "Archivo no encontrado"}` |

---

### `POST /api/fotos`

**Content-Type:** `multipart/form-data`

**Campos:**
| Campo | Tipo | Requerido | Default |
|-------|------|-----------|---------|
| `archivo` | File | Sí | — |
| `idOrden` | Long | Sí | — |
| `procesadoIa` | Boolean | No | `false` |

**ResponseBody `201 Created`** — `FotoOrdenResponseDTO`:
```json
{
  "id": 2,
  "nombreArchivo": "foto_danio_002.jpg",
  "ruta": "b2c3d4e5-f6a7-8901-bcde-f12345678901.jpg",
  "fechaSubida": "2026-06-11T10:05:00",
  "hashArchivo": "e99a18c428cb38d5f260853678922e03",
  "procesadoIa": false,
  "idOrden": 1
}
```

---

### `PUT /api/fotos/{id}`

**Content-Type:** `multipart/form-data`

**Campos:** (mismos que POST, pero `archivo` es opcional)

**ResponseBody `200 OK`** — `FotoOrdenResponseDTO` (mismo formato de arriba).

| Código | Body |
|--------|------|
| `404` | `{"error": "Foto no encontrada"}` |

---

### `DELETE /api/fotos/{id}`

**`204 No Content`**

| Código | Body |
|--------|------|
| `404` | `{"error": "Foto no encontrada"}` |

---

## Resumen de Enums

| Enum | Valores |
|------|---------|
| `EstadoOrden` | `REGISTRADA`, `EN_REPARACION`, `FINALIZADA`, `ENTREGADA`, `ANULADA` |
| `EstadoServicio` | `PENDIENTE`, `EN_PROGRESO`, `COMPLETADO`, `CANCELADO` |
| `NivelCombustible` | `E`, `_1_4`, `_1_2`, `_3_4`, `F` |
| `OrigenDanio` | `MANUAL`, `IA` |
| `RespuestaChecklist` | `SI`, `NO`, `CANT` |
| `RolEnum` | `ADMINISTRADOR`, `MECANICO` |
| `TipoDanio` | `QUINADO`, `ABOLLADO`, `RAYADO` |
| `TipoDocumento` | `DNI`, `RUC` |
