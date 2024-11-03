# Proyecto DSM: Sisvita

**Sisvita** es una aplicación de Android diseñada para el reconocimiento de emociones a través de la cámara de un dispositivo móvil. Utiliza técnicas de procesamiento de imágenes para detectar rostros y determinar la emoción predominante de las personas capturadas. Los resultados se almacenan y gestionan mediante una base de datos SQLite.

## Tabla de Contenidos
- [Descripción del Proyecto](#descripción-del-proyecto)
- [Características](#características)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Advertencias](#advertencias)
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
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Oswaldo-2001/PROYECTO_DSM.git
   
2. Configurar el entorno en el IDE:
   - Navegar a File > Settings > Build, Execution, Deployment > Gradle
   - Configurar los siguientes valores:
     - Distribución: Wrapper
     - Gradle JDK: correto-1.8

## Advertencias
Actualmente se está utilizando la versión correto-1.8 del JDK para fines prácticos, aunque ya se ha estado trabajando para otra version de JDS más reciente. Tomar en cuenta lo siguiente:

- Compatibilidad con Android: Al instalar la aplicación en dispositivos Android, puede aparecer una advertencia indicando que la versión de JDK es antigua. Para continuar, selecciona Más información y luego elige Instalar de todas formas.

- Permisos: Después de otorgar los permisos a la aplicación, es posible que debas esperar unos momentos o cerrar y volver a abrir la aplicación para que los permisos se apliquen correctamente. Esto se debe al uso de una versión más antigua del JDK.




## Uso
Sisvita es una aplicación móvil de Android que permite detectar y analizar las emociones de las personas en tiempo real utilizando la cámara del dispositivo.

1. Iniciar la aplicación:
   - Al abrir Sisvita en un dispositivo Android, la cámara frontal se activa automáticamente, configurada para detectar rostros.
   - Nota: Si estás utilizando la versión correto-1.8, es posible que debas esperar unos momentos o reiniciar la aplicación para que los permisos se apliquen correctamente.

2. Detección de emociones:
   - La cámara captura los rostros de las personas y, mediante un modelo de reconocimiento de emociones integrado, clasifica las expresiones faciales en categorías como alegría, tristeza y enojo.

3. Visualización de resultados:
   - Los resultados de la detección de emociones se muestran directamente en la interfaz de usuario con mensajes claros, como "Alegría detectada" o "Tristeza detectada".
   - Además, se muestran los porcentajes de cada emoción para indicar la distribución de las emociones detectadas en tiempo real.

4. Historial de detección:
   - Los datos de cada sesión, que incluyen la fecha, hora y porcentajes de emociones detectadas, se almacenan en una base de datos SQLite.
   - Esto permite que el usuario consulte los resultados anteriores a través de la interfaz.

5. Panel de resultados:
   - Al presionar el botón Resultados, el usuario puede acceder a un panel que muestra el historial de emociones detectadas, proporcionando un resumen de las emociones por fecha y hora.

## Estructura del Proyecto
El proyecto Sisvita está estructurado de manera organizada para facilitar el desarrollo y la gestión de su código. La carpeta principal app contiene el código fuente de la aplicación, incluyendo el paquete com/vicksam/ferapp/, donde se encuentran archivos clave como MainActivity.kt, que maneja la lógica principal de la cámara y la interfaz de usuario, y FerViewModel.kt, encargado de la gestión de la lógica de negocio y la observación de los datos de emociones. Además, FerModel.kt se encarga de la carga y gestión del modelo de detección de emociones, mientras que sqlLiteHelper.kt maneja la interacción con la base de datos local SQLite para almacenar los registros de las emociones detectadas.

## Tecnologías Utilizadas
Kotlin: Lenguaje de programación utilizado para implementar la lógica de la aplicación y la interacción con la cámara.
Android Camera: Bibliotecas utilizadas para manejar la cámara del dispositivo, permitiendo la captura de imágenes de alta calidad,detección de rostros 
y la clasificación de emociones a partir de las expresiones faciales,con el objetivo de identificar emociones como alegría, tristeza y enojo.
SQLitehelper: Base de datos embebida utilizada para almacenar el historial de detecciones de emociones, permitiendo guardar y consultar los registros de forma local en el dispositivo.
Gradle: Herramienta de construcción y gestión de dependencias, utilizada para configurar el entorno de desarrollo y las bibliotecas necesarias.

## Contribuciones
Sebastian24-stack
unmsm-jcrs
Oswaldo-2001
PieroJaraAnticona
