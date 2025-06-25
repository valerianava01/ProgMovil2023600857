EstudiApp

EstudiApp es una aplicación móvil desarrollada con Kotlin y Jetpack Compose. Está diseñada para ayudar a los estudiantes a organizar sus actividades escolares, como tareas, proyectos, exámenes y materias, tanto de forma individual como colaborativa en grupos de estudio.

---

Propósito del proyecto

Facilitar la gestión académica de los estudiantes mediante una app intuitiva, funcional y accesible que permita tener un control total sobre sus entregas, exámenes, proyectos y planificación escolar diaria, con sincronización en la nube y trabajo en equipo.

---

Público objetivo

- Estudiantes de secundaria, preparatoria o universidad
- Jóvenes que requieren ayuda en la organización escolar
- Equipos de trabajo o grupos de estudio que colaboran en línea

---

Tecnologías utilizadas

- Lenguaje de programación: Kotlin
- Framework de interfaz: Jetpack Compose
- Sincronización y almacenamiento: Tecnología en la nube propia
- Plataforma: Android
- IDE de desarrollo: Android Studio

---

Requisitos del sistema

- Sistema operativo: Android 10 o superior
- Conectividad: Acceso a internet necesario para sincronización
- Servidor: Acceso a la nube propia del proyecto
- Entorno de desarrollo: Android Studio Arctic Fox o superior
- Cuenta de usuario: Registro por correo electrónico obligatorio

---

Instalación y configuración

Para comenzar a usar EstudiApp, sigue los siguientes pasos:

1. Instalación del APK
   Descarga e instala el archivo APK de EstudiApp en tu dispositivo Android. Asegúrate de tener activada la opción para permitir instalaciones desde orígenes desconocidos si es necesario.

2. Ingreso a la aplicación
   Una vez instalada, abre la app. La primera vez que ingreses, se te solicitará proporcionar la IP del servidor web al que la aplicación se conecta para la sincronización en la nube.

3. Registro de usuario
   Después de configurar la IP, accede a la pantalla de registro. Ahí deberás ingresar un **correo electrónico válido, una contraseña segura y tu nombre de usuario.

4. Inicio de sesión
   Una vez registrado, podrás iniciar sesión con tu correo y contraseña en cualquier dispositivo compatible, siempre que se haya configurado correctamente la IP del servidor web.

---

Funcionalidades principales

Autenticación

- Registro e inicio de sesión por correo y contraseña
- Sincronización por botón después de iniciar sesión en otro dispositivo

Modo Individual

- Crear materias escolares con nombre personalizado
- Añadir tareas, proyectos y exámenes dentro de cada materia
- Establecer fecha y hora de entrega para cada elemento
- Editar y eliminar cualquier tarea, proyecto, examen o materia

Modo Grupal

- Crear un grupo de estudio con código único generado automáticamente
- Unirse a grupos ingresando el código compartido
- Colaboración en tiempo real: todos los miembros pueden ver y editar tareas, proyectos, exámenes y materias compartidas

Menú de navegación desplegable

Al iniciar sesión y entrar a la pantalla principal, se despliega un menú lateral con las siguientes opciones:

- Proyectos y Tareas
- Exámenes
- Configuración
- Ayuda
- Cerrar sesión

Este menú permite cambiar de sección rápidamente para una mejor experiencia de usuario.

---

Pantallas de la aplicación

Pantalla de Login

- Ingreso de correo y contraseña
- Enlace a pantalla de registro

Pantalla de Registro

- Crear nueva cuenta con correo y contraseña

Pantalla Principal

- Muestra los modos de selección de la aplicación (Individual y grupal)

Detalle de Materia

- Muestra tareas, proyectos y exámenes de la materia seleccionada
- Posibilidad de añadir, editar o eliminar elementos

Añadir / Editar Elemento

- Selección del tipo (tarea, proyecto, examen)
- Fecha, hora, descripción y título

Pantalla de Grupos

- Crear grupo (genera código)
- Unirse a grupo con código existente

Contenido compartido del grupo

- Visualización y edición colaborativa
- Sincronización en tiempo real

---

Modelo de datos

usuarios

sql
CREATE TABLE usuarios (
  id varchar(36) NOT NULL,
  correo varchar(100) NOT NULL,
  contrasena varchar(255) NOT NULL,
  nombre varchar(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY correo (correo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


Contiene la información de cada usuario registrado. Cada id es único y se utiliza como referencia en otras tablas. Se almacena el correo electrónico, la contraseña cifrada y el nombre de usuario.

grupos

sql
CREATE TABLE grupos (
  id_grupo varchar(36) NOT NULL,
  codigo_grupo varchar(10) NOT NULL,
  nombre_grupo varchar(100) DEFAULT 'Grupo de Estudio',
  fecha_creacion datetime DEFAULT current_timestamp(),
  id_admin_usuario varchar(36) NOT NULL,
  PRIMARY KEY (id_grupo),
  UNIQUE KEY codigo_grupo (codigo_grupo),
  KEY fk_grupo_admin_usuario (id_admin_usuario),
  CONSTRAINT fk_grupo_admin_usuario FOREIGN KEY (id_admin_usuario) REFERENCES usuarios (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


Almacena los grupos de estudio creados por los usuarios. Cada grupo tiene un código único, un nombre (por defecto "Grupo de Estudio"), fecha de creación y el usuario administrador asociado mediante una clave foránea.

usuarios\_grupos

sql
CREATE TABLE usuarios_grupos (
  id_usuario varchar(36) NOT NULL,
  id_grupo varchar(36) NOT NULL,
  fecha_union datetime DEFAULT current_timestamp(),
  PRIMARY KEY (id_usuario,id_grupo),
  KEY usuarios_grupos_ibfk_2 (id_grupo),
  CONSTRAINT usuarios_grupos_ibfk_1 FOREIGN KEY (id_usuario) REFERENCES usuarios (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT usuarios_grupos_ibfk_2 FOREIGN KEY (id_grupo) REFERENCES grupos (id_grupo) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


Relaciona a los usuarios con los grupos a los que se han unido. Soporta una relación muchos-a-muchos y registra la fecha de unión. Se utiliza para permitir la colaboración dentro de un mismo grupo.

materias

sql
CREATE TABLE materias (
  id varchar(36) NOT NULL,
  nombre varchar(50) NOT NULL,
  descripcion text DEFAULT NULL,
  usuario_id varchar(36) DEFAULT NULL,
  grupo_id varchar(36) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_materia_usuario (usuario_id),
  KEY fk_materia_grupo (grupo_id),
  CONSTRAINT fk_materia_grupo FOREIGN KEY (grupo_id) REFERENCES grupos (id_grupo) ON DELETE CASCADE,
  CONSTRAINT fk_materia_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


Guarda la información de las materias creadas por usuarios o compartidas en grupos. Puede tener relación directa con un usuario o con un grupo, permitiendo flexibilidad entre uso individual y colaborativo.

proyectos

sql
CREATE TABLE proyectos (
  id varchar(36) NOT NULL,
  nombre varchar(50) NOT NULL,
  materia_id varchar(36) NOT NULL,
  usuario_id varchar(36) NOT NULL,
  grupo_id varchar(36) DEFAULT NULL,
  fechaLimite date NOT NULL,
  horaLimite time NOT NULL DEFAULT '00:00:00',
  estado varchar(30) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_proyecto_materia (materia_id),
  KEY fk_proyecto_usuario (usuario_id),
  KEY fk_proyecto_grupo (grupo_id),
  CONSTRAINT fk_proyecto_grupo FOREIGN KEY (grupo_id) REFERENCES grupos (id_grupo) ON DELETE CASCADE,
  CONSTRAINT fk_proyecto_materia FOREIGN KEY (materia_id) REFERENCES materias (id) ON DELETE CASCADE,
  CONSTRAINT fk_proyecto_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


Contiene los proyectos y tareas asignados a materias. Incluye fecha y hora límite, y un campo para el estado del proyecto. Pueden pertenecer a un usuario o a un grupo.

examenes

sql
CREATE TABLE examenes (
  id varchar(36) NOT NULL,
  nombre varchar(100) NOT NULL,
  materia_id varchar(36) NOT NULL,
  usuario_id varchar(36) NOT NULL,
  grupo_id varchar(36) DEFAULT NULL,
  fecha date NOT NULL,
  hora time NOT NULL,
  tipo varchar(50) NOT NULL,
  estado varchar(20) DEFAULT 'Próximo',
  PRIMARY KEY (id),
  KEY fk_examen_materia (materia_id),
  KEY fk_examen_usuario (usuario_id),
  KEY fk_examen_grupo (grupo_id),
  CONSTRAINT fk_examen_grupo FOREIGN KEY (grupo_id) REFERENCES grupos (id_grupo) ON DELETE CASCADE,
  CONSTRAINT fk_examen_materia FOREIGN KEY (materia_id) REFERENCES materias (id) ON DELETE CASCADE,
  CONSTRAINT fk_examen_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


Define los exámenes programados, su tipo (oral, escrito, parcial, etc.), su estado (Próximo, Realizado, etc.) y sus vínculos a usuario, grupo y materia. Es útil para organizar el calendario académico.

eventos

sql
CREATE TABLE eventos (
  id varchar(36) NOT NULL,
  nombre varchar(50) NOT NULL,
  fecha date NOT NULL,
  completado tinyint(1) DEFAULT 0,
  usuario_id varchar(36) NOT NULL,
  grupo_id varchar(36) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_evento_usuario (usuario_id),
  KEY fk_evento_grupo (grupo_id),
  CONSTRAINT fk_evento_grupo FOREIGN KEY (grupo_id) REFERENCES grupos (id_grupo) ON DELETE CASCADE,
  CONSTRAINT fk_evento_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


Permite guardar eventos personalizados del usuario, como recordatorios o actividades extracurriculares. También se pueden compartir con grupos. Tiene un campo para marcar si fue completado.

---

Pruebas realizadas

Se realizaron pruebas funcionales enfocadas en:

- Inicio de sesión y registro
- Creación de materias y navegación dentro de ellas
- Añadir, editar y eliminar proyectos y exámenes
- Sincronización en la nube entre dispositivos
- Creación de grupos y colaboración entre usuarios
- Uso correcto del menú desplegable para cambiar entre secciones

---

Autores

- Contreras Flores Alejandro
- Cuevas Fonseca Paola
- Nava Serrano Linda Valeria

---

Derechos de autor

© 2025 Contreras Flores Alejandro, Cuevas Fonseca Paola y Nava Serrano Linda Valeria. Todos los derechos reservados.\
Este software es propiedad intelectual de sus autores. Su distribución, reproducción o modificación no autorizada está prohibida por la legislación vigente.
