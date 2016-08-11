Proyecto DS: proyecto web (tomcat), capa RESTful para las llamadas desde Angular  

Proyecto BE: es una applicacion, tiene la conexion a la base de datos, para correrlo tenemos varios profiles (dev y prod) los 2 van a intentar conectarse a la BBDD.

Profiles: 

-Aplicaion web DS, ir a la carpeta bin del tomcat y editar el archivo setenv.sh. Reiniciar el Tomcat.

-Aplicacion BE, ir a /etc/systemd/system/ y editar proyectosBA-BE.service. Reiniciar el servicio.

Properties: application.properties las propiedades estan externalizadas y se encuentran en /etc/proyectosBA/ds o be/.

Logs: para los logs, si se quieren cambiar la configuracion se debe pasar el parametro "--logging.config=/path/al/xml" o  "-Dlogging.config=/path/al/xml"

Proyecto Utility: estan las clases de dominio, las clases para consumir y producir mensajes al BUS de servicios y las clases para la importacion/exportacion del Excel.
