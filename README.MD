# Proyecto Final

## Informacion del Proyecto

Este proyecto tiene como objetivo implementar un sistema de almacenamiento distribuido de tipo clave-valor, que utilice el método de replicación líder-seguidor.

El proyecto consta de:
1. Una carpeta llamada src que contiene el codigo fuente.
2. Una carpeta llamada target donde se encuentran las clases y donde se genera el ejecutable "ProyectoFinal.jar".
3. Un archivo .xml donde se encuentra las dependencias necesarias para ejecutar el proyecto.
4. Un archivo README con informacion sobre el proyecto y como ejecutarlo.
5. Un archivo "logs.txt" donde se almacena un registro de las operaciones CRUD realizadas por medio del REST API.

---

## Clases
---

### 1. main.java

Esta es la clase principal de la cual se genera el ejecutable .jar
_La clase genera un nodo a partir de la clase Node.class. Para poder generar el nodo se deben ingresar dos argumentos, el ip del nodo y el puerto de conexión_

---

### 2. Node.java

Esta es la clase que contiene el objeto Node
_La clase está implementada como un thread para que múltiples instancias de el se puedan ejecutar a la vez._

El objeto esta compuesto por:

- String ip: _El ip personal del nodo._
- Integer port: _El puerto de conexión._
- Boolean isLeader: _Estado de liderazgo._

_Contiene getters y setters para rescatar los atributos_

El objeto Node tiene la capacidad de:
1. _Elegir por cuenta propia un líder_
2. _Solo permitir un líder pero indefinidos seguidores_
3. _Nodo líder envíar un heartbeat_ <3 _a los seguidores para indicar que sigue operando.
4. _Nodos seguidores recibir un hearbeat_
5. _Nodos seguidores detectar cuando se deja de recibir heartbeat y empezar el countdown con Timer.class_
6. _Manejar los siguientes escenarios:_
    - _Desconexión temporal de un nodo seguidor._
    - _Conexión de nuevo nodo seguidor._
    - _Desconexión temporal del nodo líder._
    - _Desconexión permantene del nodo líder._
    - _Reconexión de un nodo líder mientras los seguidores permiten recuperarse._
    
---

### 3. Timer.java

Esta es la clase que contiene el objeto Timer, el cual controla el countdown y timeout de los nodos seguidores
_La clase está implementada como un thread para que se pueda ejecutar a la vez que el nodo está funcionando._

El objeto esta compuesto por:

- boolean connected: _Determina el estado de conexión._
- boolean timeout: _Determina si el tiempo de espera se acabó._
- Integer time: _El tiempo, se mantiene en 0 mientras este conectado, caso contrario avanza 1 por segundo, el timeout ocurre a los 20 segundos._

_Contiene getters y setters para rescatar los atributos_

---

### 4. Message.java

Esta es la clase que contiene el objeto Message, el cual es modificado según el CRUD.

El objeto esta compuesto por:

- Integer id: _Identificación del mensaje._
- String name: _Nombre de la persona._
- int age: _Edad de la persona._

_Contiene getters y setters para rescatar los atributos_

---

### 5. Database.java

Esta es la clase que contiene el objeto Database, el cual es responsable de mantener el HashMap.

El objeto está compuesto por:
- Map<Integer, Message> messages: _Estructura de datos tipo Clave-Valor_

_Contiene un get getMessages() que le permite a la clase MessageService.class obtener los mensajes_

---

### 6. MessageService.java

Esta es la clase que contiene el objeto MessageService, el cual contiene la funcionalidad para los recursos.

El objeto está compuesto por:

- Map<Integer, Message> messages = Database.getMessages(): _Un modelo de datos tipo Clave-Valor para los mensajes guardados por la clase Database.class._

_Contiene funciones para realizar el CRUD en MessageResource.class_

---

### 7. MessageResource.java

Esta es la clase que contiene el objeto MessageResource

_Tiene la capacidad de:_

1. Conectar al Web Server Tomcat para realizar las operaciones CRUD con los métodos HTTP:
    - GET
    - POST
    - PUT
    - DELETE
2. Manejar el tipo de archivo JSON
3. Escribir en logs.txt las operaciones CRUD realizadas 

---

### 8. Writer.java

Esta es la clase que contiene el objeto Writer

El objeto está compuesto por:
- int counter: _Contabliliza las entradas del log._
- String filename: _Recibe el nombre del archivo log._

_Contiene getters y setters para rescatar los atributos_

_Tiene la capacidad de:_
1. Escribir entradas al archivo log.

---

## Comenzando 🚀

_Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas._

```
$ sudo apt install git
$ git clone https://github.com/Pvidal-1/uees-sd-2022-proyecto_final.git
```

### Pre-requisitos 📋

_Que cosas necesitas para instalar el software y como instalarlas_

Instalar JDK, Maven y curl

```
$ sudo apt update
$ sudo apt install default-jdk 
$ sudo apt install maven
$ sudo apt install curl
```

Instalar Tomcat

```
$ sudo apt update
$ sudo groupadd tomcat
$ sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
$ cd /tmp
$ curl -O https://www-us.apache.org/dist/tomcat/tomcat-9/v9.0.17/bin/apache-tomcat-9.0.17.tar.gz
```

Actualizar permisos de Tomcat

```
$ sudo mkdir /opt/tomcat
$ cd /opt/tomcat
$ sudo tar xzvf /tmp/apache-tomcat-9.0.*tar.gz -C /opt/tomcat --strip-components=1
$ sudo chgrp -R tomcat /opt/tomcat
$ sudo chmod -R g+r conf
$ sudo chmod g+x conf
$ sudo chown -R tomcat webapps/ work/ temp/ logs/
```

Pasos adicionales Tomcat

```
$ sudo nano /etc/systemd/system/tomcat.service
$ sudo systemctl daemon-reload
$ sudo ufw allow 8080
```

## Ejecucion de servicio Tomcat ⚙️

_Refrescar cambios_

```
$ sudo systemctl daemon-reload
```

_Ejecutar el servicio_

```
$ cd /opt/tomcat/bin
$ sudo ./startup.sh run
```

## Despliegue 📦

_Desde el directorio del proyecto, para compilar los .java y crear el ejecutable .jar_

```
$ mvn clean install
```

_Para Inicializar un nodo (debe de ser en el directorio del proyecto)_

```
$ java -jar target/ProyectoFinal.jar (ip) (puerto)
```

## Pruebas del Tomcat ⚙️

Se puede utilizar la extensión Postman para visualizar los cambios de los CRUD
La ruta del proyecto es: 
```
http://localhost:8080/replicacion/webapi/messages/
```

## Construido con 🛠️

* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Tomcat](https://tomcat.apache.org/) - Contenedor de servlets

## Autores ✒️

* **Pedro Vidal** - [Pvidal-1](https://github.com/Pvidal-1/)
* **Nicolás Cevallos** - [nicolascevallosbUEES](https://github.com/nicolascevallosbuees/)
