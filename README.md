## 🦷 Clínica Odontológica – Trabajo Práctico (UP)

> Proyecto académico desarrollado en Java como parte de la materia **Microservicios y APIs (UP)**.
> Implementa una arquitectura **DAO** con persistencia en base de datos **H2**, **test unitarios** y una arquitectura monolítica moderna en **Spring Boot 3.5.2** y **Java 21**. 
> Transitando conscientemente por decisiones iniciales (como el patrón **DAO** acoplado) para luego refactorizar a **MVC**, incorporar **ORM** con **Hibernate** y **Spring Data**, diseñar **DTOs**, manejar errores de forma global y, finalmente, presentar y “lanzar” el producto en un entorno simulado de cliente. 
> En resumen; se implementó una aplicación web para la gestión integral de una clínica odontológica, incluyendo:
- **Gestión de Pacientes**
- **Gestión de Odontólogos**
- **Gestión de Turnos**
- **Autenticación con usuarios y roles mediante Spring Security**
---

### 🧩 Tecnologías utilizadas

| Tecnología              | Descripción                          |
|-------------------------|--------------------------------------|
| ☕ **Java 21**           | Lenguaje principal del proyecto     |
| 🌱 **Spring Boot**      | Framework backend                    |
| 🗃️ **Spring Data JPA** | Persistencia de datos               |
| 🧠 **Spring Security**  | Autenticación y autorización (form login) |
| 🧱 **DAO Pattern**      | Capa de acceso a datos separada de la lógica de negocio |
| 💾 **H2 Database**      | Base de datos en memoria y persistente para pruebas |
| ⚙️ **Maven**            | Gestión de dependencias y build del proyecto |
| 🎨 **Bootstrap 5**      | Estilos del frontend |
| 🌐 **HTML**             | Interfaz de usuario |
| 🧠 **IntelliJ IDEA**    | IDE utilizado                 |

---

### 📂 Estructura del proyecto

```
src
└── main
    ├── java
    │   └── com.ClinicaOdontologica.UP
    │       ├── controller
    │       │   ├── OdontologoController
    │       │   ├── PacienteController
    │       │   └── TurnoController
    │       │
    │       ├── dto
    │       │   ├── PacienteDTO
    │       │   ├── PacienteListaDTO
    │       │   ├── TurnoDTO
    │       │   └── TurnoUpdateDTO
    │       │
    │       ├── entity
    │       │   ├── Domicilio
    │       │   ├── Odontologo
    │       │   ├── Paciente
    │       │   ├── Turno
    │       │   ├── Usuario
    │       │   └── UsuarioRole
    │       │
    │       ├── exception
    │       │   ├── GlobalException
    │       │   ├── ResourceNotFoundException
    │       │   └── ResourceValidationException
    │       │
    │       ├── repository
    │       │   ├── OdontologoRepository
    │       │   ├── PacienteRepository
    │       │   ├── TurnoRepository
    │       │   └── UsuarioRepository
    │       │
    │       ├── security
    │       │   ├── DatosIniciales
    │       │   ├── PasswordEncoder
    │       │   └── WebConfigSecurity
    │       │
    │       ├── service
    │       │   ├── OdontologoService
    │       │   ├── PacienteService
    │       │   ├── TurnoService
    │       │   └── UsuarioService
    │       │
    │       └── UpApplication
    │
    └── resources
        ├── static
        │   ├── js
        │   │   ├── del_odontologo.js
        │   │   ├── del_paciente.js
        │   │   ├── del_turno.js
        │   │   ├── get_odontologo.js
        │   │   ├── get_paciente.js
        │   │   ├── get_turno.js
        │   │   ├── post_odontologo.js
        │   │   ├── post_paciente.js
        │   │   ├── post_turno.js
        │   │   ├── put_odontologo.js
        │   │   ├── put_paciente.js
        │   │   └── put_turno.js
        │   │
        │   ├── del_odontologo.html
        │   ├── del_paciente.html
        │   ├── del_turno.html
        │   ├── get_odontologos.html
        │   ├── get_pacientes.html
        │   ├── get_turnos.html
        │   ├── index.html
        │   ├── login.html
        │   ├── post_odontologo.html
        │   ├── post_paciente.html
        │   ├── post_turno.html
        │   ├── put_odontologo.html
        │   ├── put_paciente.html
        │   └── put_turno.html
        │
        ├── application.properties
        └── data (H2 file database)

```

---

### ⚗️ Funcionalidades principales

✅ **CRUD completo de Pacientes, Odontólogos y Turnos**

* Crear, actualizar, listar y eliminar Pacientes, Odontólogos y Turnos
* Relaciones entre entidades con JPA
* Manejo de errores con excepciones personalizadas

✅ **Seguridad**

* Login con Spring Security
* Roles y usuarios precargados en DatosIniciales


✅ **Base de datos H2**

* Persistente en `jdbc:h2:file:./data/clinicaFeliz`

✅ **Testing**

* Pruebas manuales vía front + Postman

✅ **Endpoints principales**

* Pacientes:
* GET /paciente
* GET /paciente/{id}
* POST /paciente
* PUT /paciente
* DELETE /paciente/{id}

* Odontólogos:
* GET /odontologo
* POST /odontologo
* PUT /odontologo
* DELETE /odontologo/{id}

* Turnos:
* GET /turno
* POST /turno
* PUT /turno
* DELETE /turno/{id}


---

### 🚀 Cómo ejecutar el proyecto

1️⃣ Clonar el repositorio:

```bash
git clone https://github.com/TU_USUARIO/clinica-odontologica-springboot.git
```

2️⃣ Abrir el proyecto en IntelliJ IDEA
3️⃣ Ejecutar la aplicación: mvn spring-boot:run
4️⃣ Acceder a la web: http://localhost:8080/
5️⃣ Acceder a la consola H2 para ver los datos:

```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:./data/clinicaFeliz
User: sa
Password: sa
```

---

### ✍️ Autor

👨‍💻 **Agustín López**
💼 Estudiante de Licenciatura en Informática – Universidad de Palermo

📫 [GitHub](https://github.com/AgustinLeandroLopez)

---