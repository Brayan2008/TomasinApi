# Tomasin API

API REST para la gestión de un taller mecánico. Permite administrar clientes, vehículos, órdenes de servicio, checklist, daños, fotos y servicios asignados.

## Tecnologías

| Tecnología | Versión |
|-----------|---------|
| Java | 21 |
| Spring Boot | 4.0.6 |
| Spring Data JPA | - |
| Spring Security | - |
| Spring Validation | - |
| Spring WebMVC | - |
| MySQL Connector | - |
| JWT (jjwt) | 0.12.6 |
| Lombok | - |
| Maven | - |

## Estructura del proyecto

```
src/main/java/com/tomasin/api/
├── ApiApplication.java
├── config/
│   └── GlobalExceptionHandler.java
├── controllers/
│   ├── AuthController.java
│   ├── ClienteController.java
│   ├── FotoOrdenController.java
│   ├── MarcaController.java
│   ├── ModeloController.java
│   ├── OrdenChecklistController.java
│   ├── OrdenDanioController.java
│   ├── OrdenServicioController.java
│   ├── RolController.java
│   ├── ServicioAsignadoController.java
│   ├── ServicioController.java
│   ├── UsuarioAdminController.java
│   └── VehiculoController.java
├── dto/
│   ├── ClienteDTO.java
│   ├── FotoOrdenDTO.java
│   ├── MarcaDTO.java
│   ├── ModeloDTO.java
│   ├── OrdenChecklistDTO.java
│   ├── OrdenDTO.java
│   ├── OrdenDanioDTO.java
│   ├── RolDTO.java
│   ├── ServicioAsignadoDTO.java
│   ├── ServicioDTO.java
│   ├── UsuarioDTO.java
│   └── VehiculoDTO.java
├── enums/
│   ├── EstadoOrden.java
│   ├── EstadoServicio.java
│   ├── NivelCombustible.java
│   ├── OrigenDanio.java
│   ├── RespuestaChecklist.java
│   ├── RolEnum.java
│   ├── TipoDanio.java
│   └── TipoDocumento.java
├── model/
│   ├── Cliente.java
│   ├── FotoOrden.java
│   ├── Marca.java
│   ├── Modelo.java
│   ├── OrdenChecklist.java
│   ├── OrdenDanio.java
│   ├── OrdenServicio.java
│   ├── Rol.java
│   ├── ServicioAsignado.java
│   ├── ServicioCatalogo.java
│   ├── Usuario.java
│   └── Vehiculo.java
├── repository/
│   ├── ClienteRepository.java
│   ├── FotoOrdenRepository.java
│   ├── MarcaRepository.java
│   ├── ModeloRepository.java
│   ├── OrdenChecklistRepository.java
│   ├── OrdenDanioRepository.java
│   ├── OrdenServicioRepository.java
│   ├── RolRepository.java
│   ├── ServicioAsignadoRepository.java
│   ├── ServicioCatalogoRepository.java
│   ├── UsuarioRepository.java
│   └── VehiculoRepository.java
├── security/
│   ├── JwtFilter.java
│   ├── JwtUtil.java
│   ├── SecurityConfig.java
│   └── UsuarioService.java
└── service/
    ├── CrudService.java
    ├── IClienteService.java
    ├── IFotoOrdenService.java
    ├── IMarcaService.java
    ├── IModeloService.java
    ├── IOrdenChecklistService.java
    ├── IOrdenDanioService.java
    ├── IOrdenService.java
    ├── IRolService.java
    ├── IServicioAsignadoService.java
    ├── IServicioService.java
    ├── IUsuarioAdminService.java
    ├── IVehiculoService.java
    └── jpa/
        ├── ClienteService.java
        ├── FotoOrdenService.java
        ├── MarcaService.java
        ├── ModeloService.java
        ├── OrdenChecklistService.java
        ├── OrdenDanioService.java
        ├── OrdenService.java
        ├── RolService.java
        ├── ServicioAsignadoService.java
        ├── ServicioService.java
        ├── UsuarioAdminService.java
        └── VehiculoService.java
```

## Configuración de entorno

### Variables de entorno

Crear un archivo `db.env` en la raíz del proyecto:

```env
DB_URL="jdbc:mysql://localhost:3306/tomasinApi"
DB_USERNAME="bmaster"
DB_PASSWORD="123"
DDL_AUTO="update"
```

Variables disponibles y sus valores por defecto:

| Variable | Descripción | Default |
|----------|-------------|---------|
| `DB_URL` | URL de conexión a MySQL | `jdbc:mysql://localhost:3306/tomasinApi` |
| `DB_USERNAME` | Usuario de BD | `root` |
| `DB_PASSWORD` | Contraseña de BD | _(vacío)_ |
| `DDL_AUTO` | Estrategia DDL de Hibernate | `update` |
| `SHOW_SQL` | Mostrar SQL en consola | `false` |
| `JWT_SECRET` | Clave secreta para firmar JWT | `mySecretKey` |
| `JWT_EXPIRATION` | Tiempo de expiración del token en ms | `1800000` (30 min) |

## Ejecución

### Con Maven wrapper

```bash
./mvnw spring-boot:run
```

### Con VS Code

El proyecto incluye configuración de lanzamiento en `.vscode/launch.json` que carga las variables desde `db.env` automáticamente. Presionar `F5` con el archivo `ApiApplication.java` abierto.

### Requisitos previos

- Java 21
- MySQL corriendo en `localhost:3306`
- Base de datos `tomasinApi` creada

## Endpoints

### Autenticación

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/auth/login` | Iniciar sesión |

### Maestros

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET/POST | `/api/marcas` | Listar / Crear marcas |
| GET/PUT/DELETE | `/api/marcas/{id}` | Obtener / Actualizar / Eliminar marca |
| GET/POST | `/api/roles` | Listar / Crear roles |
| GET/PUT/DELETE | `/api/roles/{id}` | Obtener / Actualizar / Eliminar rol |
| GET/POST | `/api/servicios` | Listar / Crear servicios |
| GET/PUT/DELETE | `/api/servicios/{id}` | Obtener / Actualizar / Eliminar servicio |
| GET/POST | `/api/clientes` | Listar / Crear clientes |
| GET/PUT/DELETE | `/api/clientes/{id}` | Obtener / Actualizar / Eliminar cliente |
| GET/POST | `/api/modelos` | Listar / Crear modelos |
| GET/PUT/DELETE | `/api/modelos/{id}` | Obtener / Actualizar / Eliminar modelo |
| GET/POST | `/api/vehiculos` | Listar / Crear vehículos |
| GET/PUT/DELETE | `/api/vehiculos/{id}` | Obtener / Actualizar / Eliminar vehículo |
| GET/POST | `/api/usuarios` | Listar / Crear usuarios |
| GET/PUT/DELETE | `/api/usuarios/{id}` | Obtener / Actualizar / Eliminar usuario |

### Transaccionales

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET/POST | `/api/ordenes` | Listar / Crear órdenes |
| GET/PUT/DELETE | `/api/ordenes/{id}` | Obtener / Actualizar / Cancelar orden |
| GET/POST | `/api/servicios-asignados` | Listar / Crear servicios asignados |
| GET/PUT/DELETE | `/api/servicios-asignados/{id}` | Obtener / Actualizar / Cancelar servicio |
| GET/POST | `/api/checklist` | Listar / Crear ítems de checklist |
| GET/PUT/DELETE | `/api/checklist/{id}` | Obtener / Actualizar / Eliminar ítem |
| GET/POST | `/api/danios` | Listar / Crear daños |
| GET/PUT/DELETE | `/api/danios/{id}` | Obtener / Actualizar / Eliminar daño |
| GET/POST | `/api/fotos` | Listar / Crear fotos |
| GET/PUT/DELETE | `/api/fotos/{id}` | Obtener / Actualizar / Eliminar foto |

### Borrado lógico

Las entidades maestras (`marca`, `rol`, `servicio_catalogo`, `cliente`, `modelo`, `vehiculo`) usan **borrado lógico**: el registro se marca como `estado = false` en lugar de eliminarse físicamente. Las consultas estándar solo devuelven registros activos.

Las entidades transaccionales (`ordenes`, `servicios_asignados`) se **cancelan** mediante `DELETE` + query param `?motivo=`, cambiando su estado a `ANULADA` / `CANCELADO`.

## Respuestas HTTP

| Código | Significado |
|--------|-------------|
| `200` | OK (GET, POST) |
| `201` | Created + Location header (POST) |
| `204` | No Content (PUT, DELETE exitoso) |
| `404` | Not Found (recurso no existe) |
| `401` | Unauthorized (token inválido o ausente) |
| `400` | Bad Request (validación fallida) |
