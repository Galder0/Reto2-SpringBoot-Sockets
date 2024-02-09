# ElorChat Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Socket.IO](https://img.shields.io/badge/socket.io-%230E83CD.svg?style=for-the-badge&logo=socket.io&logoColor=white)
[![License](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](https://opensource.org/licenses/MIT)

## Descripción del Proyecto
ElorChat Backend es el componente de servidor de la aplicación de mensajería ElorChat, construido en Java con el framework Spring. Este backend proporciona las funcionalidades de manejo de mensajes en tiempo real utilizando Socket.IO.

### Características Principales
- :speech_balloon: Chat en Tiempo Real: Facilita la comunicación instantánea entre usuarios.
- :lock: Autenticación Segura: Implementa métodos robustos de autenticación para proteger la privacidad del usuario.
- :electric_plug: Conexiones Socket.IO: Utiliza el protocolo Socket.IO para conexiones bidireccionales en tiempo real.
- :card_file_box: Almacenamiento de Mensajes: Gestiona la persistencia de mensajes para un acceso rápido y fiable.

## Tecnologías Utilizadas
ElorChat Backend se basa en las siguientes tecnologías:
- **Java**: Versión 11
- **Spring Framework**: Versión 5.3.2
- **Socket.IO**: Versión 3.1.3

## Instalación
Siga estos pasos para configurar el backend localmente:

### Prerrequisitos
Asegúrese de tener instalado lo siguiente:
- :coffee: [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- :computer: [Maven](https://maven.apache.org/download.cgi)

### Pasos de Instalación

1. **Clonar el Repositorio:**
   ```bash
   git clone https://github.com/tu_usuario/tu_repositorio.git
2. **Importar Proyecto:**
- Importe el proyecto en su IDE preferido como un proyecto de Maven.
3. **Configurar el archivo de configuración:**
- Ajuste el archivo de configuración de Spring ("application.properties") y Socket según sea necesario, incluidas las credenciales de la base de datos u otros ajustes específicos del entorno.
3. **Compilar y Ejecutar:**
   ```bash
   mvn spring-boot:run

## Uso
- :computer: Utilice una aplicación de cliente compatible para conectarse al backend.
- :key: Implemente la autenticación de usuario para garantizar la seguridad de la comunicación.
- :speech_balloon: Envíe y reciba mensajes a través de las conexiones Socket.IO.

## Documentación
- ### API
La documentación detallada de la API se encuentra disponible en Swagger. Acceda a Swagger Docs para explorar los puntos finales, modelos y autenticación de la API.
- ### Socket.IO
Consulte la documentación de Socket.IO para comprender la implementación y el uso de las conexiones en tiempo real.

## Contacto
Para preguntas o problemas, puede ponerse en contacto con los mantenedores del proyecto:
- :computer: [Ager](mailto:ager.algortape@elorrieta-errekamari.com)
- :computer: [Galder](mailto:galder.gonzalez-balsiz@elorrieta-errekamari.com)
- :computer: [Ander](mailto:ander.lopezdevallejohi@elorrieta-errekamari.com)

## Licencia
Distribuido bajo la [MIT license](https://opensource.org/licenses/MIT).

