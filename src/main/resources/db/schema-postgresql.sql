-- Esquema PostgreSQL - Sistema de Gestión de Turnos Médicos

CREATE TABLE IF NOT EXISTS especialidades (
    id              BIGSERIAL PRIMARY KEY,
    nombre          VARCHAR(100) NOT NULL UNIQUE,
    descripcion     TEXT,
    fecha_creacion  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS pacientes (
    id                  BIGSERIAL PRIMARY KEY,
    nombre              VARCHAR(100) NOT NULL,
    apellido            VARCHAR(100) NOT NULL,
    cedula              VARCHAR(20) NOT NULL UNIQUE,
    telefono            VARCHAR(20),
    correo_electronico  VARCHAR(100),
    direccion           TEXT,
    fecha_registro      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS medicos (
    id                  BIGSERIAL PRIMARY KEY,
    nombre              VARCHAR(100) NOT NULL,
    apellido            VARCHAR(100) NOT NULL,
    cedula_profesional  VARCHAR(20) NOT NULL UNIQUE,
    telefono            VARCHAR(20),
    email               VARCHAR(100),
    especialidad_id     BIGINT NOT NULL REFERENCES especialidades(id),
    disponible          BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS turnos (
    id               BIGSERIAL PRIMARY KEY,
    paciente_id      BIGINT NOT NULL REFERENCES pacientes(id) ON DELETE CASCADE,
    medico_id        BIGINT NOT NULL REFERENCES medicos(id) ON DELETE RESTRICT,
    fecha_hora       TIMESTAMP NOT NULL,
    motivo_consulta  TEXT,
    estado           VARCHAR(20) NOT NULL DEFAULT 'PROGRAMADO'
                     CHECK (estado IN ('PROGRAMADO', 'COMPLETADO', 'CANCELADO')),
    fecha_creacion   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    notas            TEXT
);

CREATE INDEX IF NOT EXISTS idx_turnos_paciente ON turnos(paciente_id);
CREATE INDEX IF NOT EXISTS idx_turnos_medico ON turnos(medico_id);
CREATE INDEX IF NOT EXISTS idx_turnos_fecha ON turnos(fecha_hora);
CREATE INDEX IF NOT EXISTS idx_turnos_estado ON turnos(estado);

CREATE UNIQUE INDEX IF NOT EXISTS uk_turno_medico_hora_activo
    ON turnos (medico_id, fecha_hora)
    WHERE estado <> 'CANCELADO';

CREATE UNIQUE INDEX IF NOT EXISTS uk_turno_paciente_hora_activo
    ON turnos (paciente_id, fecha_hora)
    WHERE estado <> 'CANCELADO';
