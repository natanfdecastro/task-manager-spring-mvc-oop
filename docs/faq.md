# Instalando el ambiente de desarrollo

## Preguntas frecuentes

---
### ¿Cómo instalo docker?

Para que docker funcione correctamente debe ser instalado desde las fuentes oficiales. La guía de instalación se encuentra en el siguiente enlace: https://docs.docker.com/install/linux/docker-ce/ubuntu/

Deben seguir las instrucciones bajo las secciones **Install Docker CE / Install using the repository**. Por favor leer las instrucciones de la guía, no deberían simplemente copiar los comandos sin saber qué hacen o para qué deben ser ejecutados.

Una vez finalizado este proceso deben proceder a configurar su usuario para que pueda utilizar el comando `docker` sin necesidad de `sudo` siguiendo las instrucciones en la sección **Manage Docker as a non-root user** en la guía que se encuentra en este enlace: https://docs.docker.com/install/linux/linux-postinstall/ . Para que los cambios al usuario tomen efecto debe reiniciar su computadora. Este paso es absolutamente necesario, si ejecutan `docker` con `sudo` el ambiente de desarrollo no va a funcionar. 

---
### ¿Cómo instalo direnv?

La herramienta `direnv` (https://direnv.net/) permite manejar automáticamente la carga de variables de ambiente específicas para un proyecto sin necesidad de cargarlas en la configuración general del sistema.

Para instalar esta herramienta en ubuntu debe ejecutar el siguiente comando:

```bash
sudo apt-get install direnv
```

Y posteriormente configurar el servicio. Para configurar el servicio primero debe determinar cuál *shell* (programa de línea de comando) está corriendo en la terminal ejecutando el siguiente comando:

```bash
echo $0
```

Si está corriendo `bash` (el caso más común) entonces debe editar el archivo `~/.bashrc` agregando la siguiente línea al final del mismo:

```bash
eval "$(direnv hook bash)"
```

Después de salvar el archivo debe cerrar la terminal y abrir una nueva para que los cambios tomen efecto.

En el caso de que esté corriendo el *shell* `zsh` entonces debe editar el `~/.zshrc` agregando la línea:

```zsh
eval "$(direnv hook zsh)"
```

Y reiniciar la terminal.

Para cualquier otro shell por favor consultar la documentació de direnv en el enlace indicado anteriormente.

---
### ¿Cómo configuro las variables de ambiente del proyecto?

Para lograr esta tarea debe haber instalado y configurado correctamente la herramiente `direnv`. 

En el directorio principal del proyecto (el que inicia con `ic-6821...`) debe crear un archivo llamado `.envrc` y colocar ahí los `export` de las variables de ambiente:

```bash
export DOCKER_COMPOSE_RUN_AS_USER="$(id -u):$(id -g)"
export APP_DB_PWD=root
export APP_AUTH_JWT_SECRET=b898c01c67ba512c627db45a439b15c0c7b81411b5757e4fb0246fd6e24fa74710f002430ae5cdabba38a7caad3db854b99695e76dffbfc8239fe2baf52016d7
export MYSQL_ROOT_PASSWORD=root
```

Una vez guardado el archivo, ejecutar el siguiente comando en la terminal: 

```bash
direnv allow .
```

**Noten el punto al final del comando, ¡es importante!**

El comando debe producir la siguiente salida indicando que las variables fueron cargadas correctamente:

```
direnv: loading .envrc                                                                                  
direnv: export +APP_AUTH_JWT_SECRET +APP_DB_PWD +DOCKER_COMPOSE_RUN_AS_USER +MYSQL_ROOT_PASSWORD
```

---
### Mi build está fallando. ¿Cómo puedo ver el stack trace detallado del error?

Cuando el build falla `gradle` imprime información resumida que en ocasiones no es suficiente para identificar el problema. Con el fin de tener mayor información pueden correr `gradlew` con la opción `--stacktrace`:

```bash
docker-compose run build ./gradlew --stacktrace clean build
```

---
### Estoy recibiendo errores de permisos a la hora de correr el build.

Si el build falla con el error `Could not create service of type FileHasher...` quiere decir que tiene un problema de permisos en los directorios generados por gradle.

Esto usualmente sucede cuando se corre el build sin haber cargado correctamente la variable de ambiente `DOCKER_COMPOSE_RUN_AS_USER`. Cuando esto sucede gradle genera los directorios `build` y `.gradle` bajo el usuario `root`. Para ver los usuarios dueños de cada directorio puede ejecutar el comando

```bash
ls -alh
```

Para corregir este problema debe primero borrar los directorios generados incorrectamente

```bash
sudo rm -rf .gradle build
```

El comando anterior se debe ejecutar con `sudo` puesto que los directorios pertenecen al usuario `root`.

Luego de borrados debe asegurarse que las variables de ambiente del proyecto estén correctamente configuradas (ver pregunta **¿Cómo configuro las variables de ambiente del proyecto?**).

Con las variables configuradas y todos los directorios bajo el usuario actual ya puede volver a ejecutar el proceso de build.

---
### Los test están fallando. ¿Cómo puedo ver el stack trace detallado del error?

Cuando los test fallan se despliega en la consola un stack trace resumido, en ocasiones esto podría ser insuficiente para determinar la causa del error.

Para ver información más detallada acerca del error puede abrir el reporte generado por gradle. Para abrir este reporte puede ejecutar el comando:

```bash
xdg-open build/reports/tests/test/index.html 
```

El reporte se abre en el navegador. En la página principal se listan todos los test ejecutados. Al darle click a cualquiera de los test se puede ver información acerca del mismo, incluyendo un stack trace detallado en el caso de que haya fallado.

---
### Los tests están fallando porque no se pueden conectar a la base de datos.

Los test fallan con un error `Unknown Host Exception` o `Name does not resolve`. En este caso necesita correr el contenedor de la base de datos de manera independiente para que se inicialice el volume correctamente.

Para correr el contenedor puede utilizar el comando:

```bash
docker-compose run db
```

El contenedor ejecutará una serie de operaciones de inicialización, el proceso finaliza cuando se imprime en consola el mensaje `ready for connections`.

Una vez finalizado hay que detener el contenedor. En una terminal nueva ejecutar el comando 

```bash
docker ps
```

Y tomar nota del nombre del contenedor de la base de datos, este contenedor lo podemos identificar porque es el corre la imagen `mysql` y se asocia al puerto `3306/tcp`. El nombre debería seguir un formato parecido al siguiente ejemplo `ic-6821-p2-a_db_run_1`. Para detenerlo correr el comando `docker stop` con el nombre del contenedor, por ejemplo:

```bash
docker stop ic-6821-p2-a_db_run_1
```

Una vez detenido el contenedor ya puede volver a ejecutar el proceso de build.

---
### ¿Cómo configuro IntelliJ IDEA para usarlo con el proyecto?

Para editar el código con IDEA primero deben utilizar gradle para generar el proyecto de IDEA:

```bash
docker-compose run build ./gradlew idea
```

Esto generará tres archivos con extensiones `.ipr`, `.iml`, `.iws`. Desde IDEA abrir el proyecto seleccionando el archivo `.ipr`.

**IMPORTANTE**: No compilar el proyecto desde IDEA pues no va funcionar, el proyecto debería ser compilado únicamente desde el gradle corriendo en el contenedor de docker.
