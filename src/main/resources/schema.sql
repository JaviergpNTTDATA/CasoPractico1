CREATE
DATABASE novabank;

CREATE TABLE clientes
(
    id             SERIAL PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    apellidos      VARCHAR(150) NOT NULL,
    dni            VARCHAR(20)  NOT NULL UNIQUE,
    email          VARCHAR(150) NOT NULL UNIQUE,
    telefono       VARCHAR(20)  NOT NULL UNIQUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_email_formato CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
    );

CREATE TABLE cuentas
(
    id             SERIAL PRIMARY KEY,
    numero_cuenta  VARCHAR(34)    NOT NULL UNIQUE,
    cliente_id     INT            NOT NULL,
    saldo          NUMERIC(15, 2) NOT NULL DEFAULT 0,
    fecha_creacion TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cuenta_cliente FOREIGN KEY (cliente_id) REFERENCES clientes (id) ON DELETE CASCADE,
    CONSTRAINT chk_saldo_no_negativo CHECK (saldo >= 0)
);

CREATE TABLE movimientos
(
    id        SERIAL PRIMARY KEY,
    cuenta_id INT            NOT NULL,
    tipo      VARCHAR(50)    NOT NULL,
    cantidad  NUMERIC(15, 2) NOT NULL,
    fecha     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (cuenta_id) REFERENCES cuentas (id) ON DELETE CASCADE,
    CONSTRAINT chk_cantidad_positiva CHECK (cantidad > 0),
    CONSTRAINT chk_tipo_valido CHECK (tipo IN ('DEPOSITO', 'RETIRO', 'TRANSFERENCIA_SALIENTE', 'TRANSFERENCIA_ENTRANTE')
        )
);