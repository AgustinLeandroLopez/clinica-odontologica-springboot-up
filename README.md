## 🦷 Clínica Odontológica – Trabajo Práctico (UP)

> Proyecto académico desarrollado en Java como parte de la materia **Microservicios y APIs (UP)**.
> Implementa una arquitectura **DAO** con persistencia en base de datos **H2**, **test unitarios** y una arquitectura monolítica moderna en **Spring Boot 3.5.2** y **Java 21**. 
> Transitando conscientemente por decisiones iniciales (como el patrón **DAO** acoplado) para luego refactorizar a **MVC**, incorporar **ORM** con **Hibernate** y **Spring Data**, diseñar **DTOs**, manejar errores de forma global y, finalmente, presentar y “lanzar” el producto en un entorno simulado de cliente. 
> En resumen; se implementó una aplicación web para la gestión integral de una clínica odontológica, incluyendo:
- **Pacientes**
- **Odontólogos**
- **Turnos**
- **Login y roles con autenticación de usuarios**
---

### 🧩 Tecnologías utilizadas

| Tecnología              | Descripción                                             |
|-------------------------|---------------------------------------------------------|
| ☕ **Java 21**           | Lenguaje principal del proyecto                        |
| 🌱 **Spring Boot**      | Framework backend                                       |
| 🗃️ **Spring Data JPA** | Persistencia de datos                                  |
| 🧠 **Spring Security**  | Autenticación y autorización (form login)               |
| 🧱 **DAO Pattern**      | Capa de acceso a datos separada de la lógica de negocio |
| 💾 **H2 Database**      | Base de datos en memoria y persistente para pruebas     |
| ⚙️ **Maven**            | Gestión de dependencias y build del proyecto            |
| 🎨 **Bootstrap 5**      | Estilos del frontend |
| 🌐 **HTML**             | Interfaz de usuario |
| 🧠 **IntelliJ IDEA**    | IDE utilizado para desarrollo y testing                 |

---

### 📂 Estructura del proyecto

```
src/
├─ main/
│ ├─ java/com/ClinicaOdontologica/UP/
│ │ ├─ controller/ → Controladores REST
│ │ ├─ entity/ → Entidades JPA (Paciente, Odontologo, Turno, Usuario)
│ │ ├─ repository/ → Interfaces JPA Repository
│ │ ├─ service/ → Lógica de negocio
│ │ ├─ security/ → Configuración Spring Security y datos iniciales
│ │ └─ UpApplication.java → Clase principal
│ └─ resources/
│ ├─ static/ → Archivos HTML y JS (front)
│ ├─ application.properties
│ └─ data/ → Archivo H2 persistente
└─ test/
├─ service/ → Tests unitarios de servicios
```

---

### ⚗️ Funcionalidades principales

✅ **CRUD completo de Pacientes y Odontólogos**

* Crear, buscar, listar, actualizar y eliminar registros
* Relaciones entre tablas (`PACIENTES`, `DOMICILIOS`, `ODONTOLOGOS`)

✅ **Base de datos H2**

* Configuración en memoria y persistente (`~/clinicaFeliz`)
* Scripts automáticos de creación de tablas y datos iniciales

✅ **Test Unitarios JUnit**

* Casos de prueba para cada método del DAO y capa de servicio
* Validación de persistencia y comportamiento de negocio

✅ **Arquitectura DAO y Service Layer**

* Separación clara entre lógica de negocio y acceso a datos
* Reutilización de conexiones mediante `BD.getConnection()`

---

### 🚀 Cómo ejecutar el proyecto

1️⃣ Clonar el repositorio:

```bash
git clone https://github.com/TU_USUARIO/clinica-odontologica-springboot.git
```

2️⃣ Abrir el proyecto en IntelliJ IDEA

3️⃣ Ejecutar los test desde la clase:

```
test/java/PacienteTestService.java
```

o directamente con:

```bash
mvn test
```

4️⃣ Acceder a la consola H2 para ver los datos:

```
http://localhost:8082/
JDBC URL: jdbc:h2:~/clinicaFeliz
User: sa
Password: sa
```

---

### 📸 Ejemplo de salida del test

```
Datos Persistidos Ingreso Inicial
Base H2 creada para pruebas en TEST
Paciente creado
Paciente guardado correctamente: Paciente{nombre='Agustin', apellido='Prueba', id=3}
```

---

### ✍️ Autor

👨‍💻 **Agustín López**
💼 Estudiante de Licenciatura en Informática – Universidad de Palermo

📫 [GitHub](https://github.com/AgustinLeandroLopez)

---