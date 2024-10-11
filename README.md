# Proyecto DSM: Sisvita

**Sisvita** es una aplicación de Android diseñada para el reconocimiento de emociones a través de la cámara de un dispositivo móvil. Utiliza técnicas de procesamiento de imágenes para detectar rostros y determinar la emoción predominante de las personas capturadas. Los resultados se almacenan y gestionan mediante una base de datos SQLite.

## Tabla de Contenidos
- [Descripción del Proyecto](#descripción-del-proyecto)
- [Características](#características)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Contribuciones](#contribuciones)


## Descripción del Proyecto
Sisvita permite a los usuarios detectar y analizar emociones como alegría, tristeza y enojo a través de la cámara de un dispositivo Android. La aplicación muestra los resultados de forma interactiva, indicando la emoción detectada y el porcentaje de ocurrencia de cada emoción. Los datos son almacenados en una base de datos local utilizando `SQLiteHelper`.

## Características
- **Detección de Emociones**: Utiliza la cámara del dispositivo para identificar y clasificar emociones de las personas en tiempo real.
- **Interfaz Interactiva**: Muestra los resultados de las emociones con mensajes claros, como "Alegría detectada", "Tristeza detectada", etc.
- **Almacenamiento de Datos**: Guarda un historial de las emociones detectadas junto con la fecha y hora en una base de datos SQLite.
- **Visualización de Resultados**: Muestra el porcentaje de cada emoción detectada a través de un panel interactivo.
- **Panel de Resultados**: Permite a los usuarios ver un resumen de las emociones detectadas por fecha y sesión.

## Requisitos Previos
- Android Studio (última versión recomendada)
- Dispositivo Android o emulador con cámara

## Instalación
1. Clona el repositorio en tu máquina local:
   ```bash
   git clone https://github.com/Oswaldo-2001/PROYECTO_DSM.git
  #Uso
Sisvita es una aplicación móvil de Android que permite detectar y analizar las emociones de las personas en tiempo real utilizando la cámara del dispositivo. 

Iniciar la aplicación: Al abrir Sisvita en un dispositivo Android, la cámara frontal se activa automáticamente, configurada para detectar rostros.
Detección de emociones: La cámara captura los rostros de las personas y, mediante el modelo de reconocimiento de emociones integrado, clasifica las expresiones faciales en diferentes categorías, como alegría, tristeza y enojo.
Visualización de resultados: Los resultados de la detección de emociones se muestran directamente en la interfaz de usuario con mensajes claros, como "Alegría detectada" o "Tristeza detectada". Adicionalmente, los porcentajes de cada emoción se muestran para indicar la distribución de las emociones detectadas en tiempo real.
Historial de detección: Los datos de cada sesión, que incluyen la fecha, hora y porcentajes de emociones detectadas, se almacenan en una base de datos SQLite. Esto permite que el usuario consulte los resultados anteriores a través de la interfaz.
Panel de resultados: Al presionar el botón "Resultados", el usuario puede acceder a un panel que muestra el historial de emociones detectadas, proporcionando un resumen de las emociones por fecha y hora.

#Estructura del Proyecto
Proyecto_DSM/(con variaciones a futuro para nuevas funciones
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/vicksam/ferapp/
│   │   │   │   ├── MainActivity.kt          # Actividad principal que maneja la cámara y la interfaz
│   │   │   │   ├── FerViewModel.kt          # Gestión de la lógica de negocio y observación de datos de emociones
│   │   │   │   ├── FerModel.kt              # Carga y manejo del modelo de detección de emociones
│   │   │   │   ├── sqlLiteHelper.kt         # Clase para interactuar con la base de datos SQLite
│   │   │   │   └── ... (otras clases y archivos)
│   │   │   ├── res/
│   │   │   │   ├── layout/activity_main.xml # Layout de la interfaz principal de la aplicación
│   │   │   │   ├── values/strings.xml       # Textos y recursos de la interfaz
│   │   │   │   └── ... (otros recursos gráficos)
│   └── ... (otros archivos de configuración)
├── facedetector/
│   ├── src/main/java/husaynhakeem/io/facedetector/
│   │   ├── FaceDetector.kt                  # Lógica para la detección de rostros usando la cámara
│   │   ├── FaceBounds.kt                    # Clase para definir los límites de los rostros detectados
│   │   ├── FaceDetectorUtils.kt             # Utilidades para el procesamiento de las imágenes
│   │   └── ... (otras clases)
├── images/                                  # Imágenes y recursos gráficos para la documentación
├── gradle/                                  # Configuración de Gradle
├── build.gradle                             # Configuración del proyecto
├── settings.gradle                          # Configuración de módulos del proyecto
├── privacy_policy.md                        # Política de privacidad de la aplicación
└── README.md                                # Documentación del proyecto

# Tecnologías Utilizadas
Kotlin: Lenguaje de programación utilizado para implementar la lógica de la aplicación y la interacción con la cámara.
Android Camera: Bibliotecas utilizadas para manejar la cámara del dispositivo, permitiendo la captura de imágenes de alta calidad,detección de rostros 
y la clasificación de emociones a partir de las expresiones faciales,con el objetivo de identificar emociones como alegría, tristeza y enojo.
SQLitehelper: Base de datos embebida utilizada para almacenar el historial de detecciones de emociones, permitiendo guardar y consultar los registros de forma local en el dispositivo.
Gradle: Herramienta de construcción y gestión de dependencias, utilizada para configurar el entorno de desarrollo y las bibliotecas necesarias.
#Contribuciones
Sebastian24-stack
unmsm-jcrs
Oswaldo-2001
PieroJaraAnticona
