## UNIVERSIDAD DE ESPECIALIDADES ESPÍRITU SANTO

_Facultad de Ingeniería_

Ingeniería en Computación

**Docente:** Ing. Edwin Boza 

**Periodo:** Extraordinario 

**Año:** 2022

**Materia:** Sistemas Distribuidos

Parcial: Segundo

Nicolás Cevallos - Pedro Vidal

**Modelo arquitectónico:** Cliente-Servidor (EstructuraMVC)

_(Imagen en pdf)_

**URLs:**

1. HTTP GET http://localhost:8000/

Respuesta

```
{ }
```

2. HTTP POST [http://localhost:8000/new-user?id=2019240003&name=Nicolás_Cevallos&age=](http://localhost:8000/new-user?id=2019240003&name=Nicolás_Cevallos&age=)

Respuesta:

```
{
    “id”: 2019240003,
    “name”: “Nicolás Cevallos”,
    “age”: 21
}
```

3. HTTP GET [http://localhost:9000/user-info?id=](http://localhost:9000/user-info?id=)

Respuesta:

```
{
    “id”: 2019240003,
    “name”: “Nicolás Cevallos”,
    “age”: 21
}
```

4. HTTP DELETE
[http://localhost:8000/del-user/id=2019240027&name=Pedro_Vidal&age=](http://localhost:8000/del-user/id=2019240027&name=Pedro_Vidal&age=)

Respuesta:

```HTTP RESPONSE```

5. HTTP PUT [http://localhost:8000/update-user/](http://localhost:8000/update-user/)

Respuesta:

HTTP RESPONSE

**Logs:**

Consisten en un reloj lógico con el método (GET/POST/PUT/DELETE) y la operación que se realiza con la info correspondiente

Formato: (Contador) Método: (Información).

Ejemplo: 

(1) POST: (2019240003, "Nicolás Cevallos", 21)

(2) GET: (2019240003)

(3) PUT: (2019240003, "Nicolás Cevallos Bigalli", 22)

**Modo de replicación en situación normal:** Líder-Seguidor

_(Imagen en pdf)_

**Actualización de una Réplica:**

_(Imagen en pdf)_

**Agregar una Réplica:** creación de un nuevo nodo y actualización con un snapshot sacado del líder.

_(Imagen en pdf)_

**Elección de Nuevo Líder:** Decidir por un algoritmoque permita escoger el cambio, se me ocurren:

1. Escoger al último nodo que fue actualizado por el master (recomendado en alguna clase).

2. Escoger al segundo nodo que fue creado.

3. Escoger al nodo con la menor IP.

Si el antiguo líder se reactiva, debe establecer conexión con el nuevo líder y cambiar su estado a seguidor.

_(Imagen en pdf)_