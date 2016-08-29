Proyecto DS: proyecto web (tomcat), capa RESTful para las llamadas desde Angular  

Proyecto BE: es una applicacion, tiene la conexion a la base de datos, para correrlo tenemos varios profiles (DEV y PROD) los 2 van a intentar conectarse a la BBDD, esto es mas que nada para evitar ver errores en el test que valida el contexto.

ejemplo para activar los distintos profiles: 
vmArguments 			-Dspring.profiles.active=dev
program arguments 		--spring.profiles.active=dev

Properties: para ambos proyectos tenemos las properties locales ir a application.properties y ahi poner el path a donde vamos a colocar nuestras porperties locales.

Logs: para los logs, si se quieren cambiar la configuracion se debe pasar el parametro "--logging.config=/path/al/xml" o  "-Dlogging.config=/path/al/xml"

Proyecto Utility: estan las clases de dominio, las clases para consumir y producir mensajes al BUS de servicios y las clases para la importacion/exportacion del Excel.
