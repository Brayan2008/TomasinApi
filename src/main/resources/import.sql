INSERT INTO
    rol (id, estado, nombre)
VALUES (255, 1, 'Administrador');

INSERT INTO
    usuario (
        id,
        bloqueado,
        nombre,
        email,
        intentos_fallidos,
        password_hash,
        id_rol
    )
VALUES (
        1,
        0,
        'Admin',
        'admin@tomasin.api',
        0,
        '$2a$10$UUvXkp9h.1HjBasMQwwqDuc.NykqAMdKPnOZyZ5.mXcnvmJKX8aOy',
        255
    );