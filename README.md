# TUTO!

**TUTO!** es una aplicación desarrollada en **Android Studio** que conecta estudiantes con tutores, permitiendo gestionar tutorías individuales y grupales. Esta aplicación facilita la comunicación, organización y seguimiento de tutorías, optimizando la experiencia tanto para estudiantes como para tutores.

---

## 📝 **Descripción del Proyecto**

**TUTO!** es un proyecto diseñado para el curso de **Programación de Plataformas Móviles**. Esta aplicación permite:
- Los estudiantes pueden agendar y gestionar tutorías con tutores registrados.
- Los tutores pueden ofrecer tutorías grupales y gestionar la disponibilidad de sus tutorías.
- La UI se adapta al modo claro y oscuro automáticamente, brindando una experiencia moderna y accesible.
- Todos los usarios pueden modificar sus datos personales. 

---

## 🌟 **Características Principales**

1. **Gestión de Usuarios:**
   - Registro e inicio de sesión con autenticación segura mediante Firebase Authentication.

2. **Tutorías Individuales:**
   - Los estudiantes pueden buscar tutores por nombre de tutor, agendar tutorías y gestionar las existentes.

3. **Tutorías Grupales:**
   - Inscripción a tutorías grupales con límite de cupos.
   - Cancelación de tutorías por parte de los tutores.

4. **Soporte para Modo Oscuro y Claro:**
   - Colores y temas adaptados dinámicamente según la configuración del sistema.

5. **Persistencia de Datos:**
   - Datos almacenados y gestionados con Firebase Firestore.

---

## 🖥️ **Requisitos del Sistema**

### **Hardware**
- **Teléfono Android:** Android 8.0 (Oreo) o superior.
- **Emulador Android:** Compatible con Android Virtual Device (AVD).

### **Software**
- **Android Studio:** Versión 2022.2.1 o superior.
- **Java Development Kit (JDK):** Versión 11 o superior.

---

## ⚙️ **Instalación y Configuración**

1. **Clonar el Repositorio**
   ```bash
   git clone https://github.com/tu-usuario/tutormatch.git
   cd tutormatch
2. **Abrir en Android Studio**
   - Abre Android Studio.
   - Haz clic en "Open Project" y selecciona la carpeta del proyecto clonado.
3. **Ejecutar la Aplicación**
   - Conecta un dispositivo físico o configura un emulador en AVD.
   - Haz clic en el botón de "Run" en Android Studio.

## 👥 **Integrantes del Proyecto**
- Vianka Vanessa Castro Ordonez - 23201
- Ricardo Arturo Godínez Sánchez - 23247
- Diego Javier Lopez Reinoso - 23747


## 🛠 **Tecnologías Utilizadas**
- Lenguaje Principal: Kotlin
- IDE: Android Studio
- Base de Datos: Firebase Firestore
- Autenticación: Firebase Authentication
- UI: Jetpack Compose
- Gestión de Estado: StateFlow
- Diseño Adaptativo: Soporte para modo oscuro y claro

  
## 📂 **Estructura del Proyecto**
```bash
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/tutormatch/
│   │   │   │   ├── ui/                        # Interfaces de Usuario
│   │   │   │   │   ├── estudiante/            # Funcionalidades para estudiantes
│   │   │   │   │   │   ├── Main/              # Pantalla principal del estudiante
│   │   │   │   │   │   │   ├── Repository/    # Acceso a datos de la pantalla principal
│   │   │   │   │   │   │   ├── View/          # Interfaz de usuario (Compose)
│   │   │   │   │   │   │   ├── ViewModel/     # Lógica de presentación
│   │   │   │   │   │   ├── MyTutors/          # Sección "Mis Tutores"
│   │   │   │   │   │   ├── Perfil/            # Pantalla de perfil del estudiante
│   │   │   │   │   │   ├── SolicitudTutoria/  # Solicitud de tutoría individual
│   │   │   │   │   │   ├── Tutor_Es/          # Información de los tutores
│   │   │   │   │   │   ├── TutoriaDetalle/    # Detalles de las tutorías individuales
│   │   │   │   │   │   ├── TutoriaGrupal/     # Detalles y manejo de tutorías grupales
│   │   │   │   │   ├── tutor/                 # Funcionalidades para tutores
│   │   │   │   │   │   ├── perfil/        # Pantalla de perfil del tutor
│   │   │   │   │   │   ├── solicitudes/
│   │   │   │   │   │   ├── MisTutorias/
│   │   │   │   │   │   ├── Estudiante_Tu/
│   │   │   │   │   │   ├── DetallesTutoriaGrupal/
│   │   │   │   │   │   ├── crearTutoria/
│   │   │   │   │   ├── general/               # Componentes generales de inicio
│   │   │   │   │   │   ├── bienvenida/
│   │   │   │   │   │   ├── Login/
│   │   │   │   │   │   ├── SignUp/       
│   │   │   │   │   ├── theme/                 # Configuración del tema de la app
│   │   │   │   ├── navigation/                # Configuración de navegación
│   │   │   │   │   ├── AppBar.kt
│   │   │   │   │   ├── NavigateUtil.kt
│   │   │   │   │   ├── Navigation.kt
│   │   │   │   │   ├── NavigationState.kt
│   │   │   │   ├── estructuras/               # Modelos y clases relacionadas
│   │   │   │   │   ├── firebaseImplementation/
│   │   │   │   │   │   ├── Estudiante1.kt
│   │   │   │   │   │   ├── Materia.kt
│   │   │   │   │   │   ├── Tutor1.kt
│   │   │   │   │   │   ├── Tutoria1.kt
│   │   │   │   │   │   ├── TutoriaConDetalles.kt
│   │   │   │   │   │   ├── TutoriaConDetallesEstudiante.kt
│   │   │   │   │   │   ├── TutoriaGrupal.kt
│   │   │   │   │   │   ├── Usuario1.kt
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── MyApp.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/                    # Archivos XML para layouts específicos
│   │   │   │   ├── drawable/                  # Recursos gráficos
│   │   │   │   ├── values/                    # Strings, colores, estilos
├── build.gradle                               # Configuración de Gradle
├── google-services.json                       # Configuración de Firebase



