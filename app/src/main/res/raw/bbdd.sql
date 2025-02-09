-- Crear base de datos examenprom si no existe
CREATE DATABASE IF NOT EXISTS `examenprom`;

-- Usar la base de datos examenprom
USE `examenprom`;

-- Crear tabla Grupo
DROP TABLE IF EXISTS `Grupo`;

CREATE TABLE `Grupo` (
  `id_grupo` INT AUTO_INCREMENT PRIMARY KEY,  -- Correcto en MariaDB
  `nombre_grupo` TEXT NOT NULL
);

-- Crear tabla Alumno
DROP TABLE IF EXISTS `Alumno`;

CREATE TABLE `Alumno` (
  `id_alumno` INT AUTO_INCREMENT PRIMARY KEY,  -- Correcto en MariaDB
  `nombre` TEXT NOT NULL,
  `contrasenia` TEXT NOT NULL,
  `puntuacion` INT NOT NULL,
  `id_grupo` INT,
  FOREIGN KEY (`id_grupo`) REFERENCES `Grupo` (`id_grupo`)
);

-- Insertar datos de ejemplo en la tabla Grupo
INSERT INTO `Grupo` (`nombre_grupo`) VALUES ('Grupo A');
INSERT INTO `Grupo` (`nombre_grupo`) VALUES ('Grupo B');
INSERT INTO `Grupo` (`nombre_grupo`) VALUES ('Grupo C');

-- Insertar datos de ejemplo en la tabla Alumno
INSERT INTO `Alumno` (`nombre`, `contrasenia`, `puntuacion`, `id_grupo`) VALUES ('Juan Pérez', 'password123', 85, 1);
INSERT INTO `Alumno` (`nombre`, `contrasenia`, `puntuacion`, `id_grupo`) VALUES ('María López', 'password456', 90, 1);
INSERT INTO `Alumno` (`nombre`, `contrasenia`, `puntuacion`, `id_grupo`) VALUES ('Carlos Rodríguez', 'password789', 95, 2);
INSERT INTO `Alumno` (`nombre`, `contrasenia`, `puntuacion`, `id_grupo`) VALUES ('Ana García', 'password321', 80, 2);
INSERT INTO `Alumno` (`nombre`, `contrasenia`, `puntuacion`, `id_grupo`) VALUES ('Josefo Hernández', 'password654', 88, 3);
