
CREATE TABLE especialidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO especialidades (nombre, descripcion) VALUES
    ('Cardiología', 'Especialidad del corazón y sistema circulatorio'),
    ('Dermatología', 'Especialidad de la piel'),
    ('Oftalmología', 'Especialidad de los ojos'),
    ('Pediatría', 'Especialidad de niños'),
    ('Neurocirugía', 'Especialidad del sistema nervioso');


CREATE TABLE pacientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    correo_electronico VARCHAR(100),
    direccion TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO pacientes (nombre, apellido, cedula, telefono, correo_electronico, direccion) VALUES
    ('Juan', 'Pérez', '1234567', '3011234567', 'juan@email.com', 'Calle 1 # 10-20'),
    ('María', 'González', '9876543', '3029876543', 'maria@email.com', 'Calle 2 # 20-30'),
    ('Carlos', 'Rodríguez', '5555555', '3035555555', 'carlos@email.com', 'Calle 3 # 30-40');

CREATE TABLE medicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    cedula_profesional VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    email VARCHAR(100),
    especialidad_id INTEGER NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE medicos ADD CONSTRAINT fk_medicos_especialidades FOREIGN KEY (especialidad_id) REFERENCES especialidades(id);

INSERT INTO medicos (nombre, apellido, cedula_profesional, telefono, email, especialidad_id, disponible) VALUES
    ('Dr. Antonio', 'López', 'MP001', '3001111111', 'dr.lopez@hospital.com', 1, TRUE),
    ('Dra. Patricia', 'Martínez', 'MP002', '3002222222', 'dra.martinez@hospital.com', 2, TRUE),
    ('Dr. Roberto', 'García', 'MP003', '3003333333', 'dr.garcia@hospital.com', 3, TRUE);

CREATE TABLE turnos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INTEGER NOT NULL,
    medico_id INTEGER NOT NULL,
    fecha_hora TIMESTAMP NOT NULL,
    motivo_consulta TEXT,
    estado VARCHAR(50) DEFAULT 'PROGRAMADO', -- PROGRAMADO, COMPLETADO, CANCELADO
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notas TEXT
);

ALTER TABLE turnos ADD CONSTRAINT fk_turnos_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes(id) ON DELETE CASCADE;
ALTER TABLE turnos ADD CONSTRAINT fk_turnos_medico FOREIGN KEY (medico_id) REFERENCES medicos(id) ON DELETE RESTRICT;

CREATE INDEX idx_turnos_paciente ON turnos(paciente_id);
CREATE INDEX idx_turnos_medico ON turnos(medico_id);
CREATE INDEX idx_turnos_fecha ON turnos(fecha_hora);
CREATE INDEX idx_turnos_estado ON turnos(estado);

INSERT INTO turnos (paciente_id, medico_id, fecha_hora, motivo_consulta, estado) VALUES
    (1, 1, '2024-12-15 09:00:00', 'Revisión cardíaca rutinaria', 'PROGRAMADO'),
    (2, 2, '2024-12-15 10:30:00', 'Consulta dermatológica', 'PROGRAMADO'),
    (3, 3, '2024-12-16 14:00:00', 'Examen de vista', 'PROGRAMADO');
