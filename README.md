# Segundo Proyecto Programado #

## Documentación técnica ##

### Variables de ambiente ###

Variables de ambiente a definir, con valores de ejemplo. Se recomienda utilizar la herramienta *direnv* y definir la variables en un archivo `.envrc`.

```bash
export DOCKER_COMPOSE_RUN_AS_USER="$(id -u):$(id -g)"
export APP_DB_PWD=root
export APP_AUTH_JWT_SECRET=b898c01c67ba512c627db45a439b15c0c7b81411b5757e4fb0246fd6e24fa74710f002430ae5cdabba38a7caad3db854b99695e76dffbfc8239fe2baf52016d7
export MYSQL_ROOT_PASSWORD=root
```

### Construir y ejecutar ###

Para construir la aplicación utilice el comando:

```bash
docker-compose run build ./gradlew clean build
```

Para ejecutar la aplicación utilice el comando:

```bash
docker-compose run --service-ports app
```

## Especificación del proyecto ##

El proyecto consiste en aplicar una arquitectura de software en el desarrollo de un backend para una aplicación basada en uno de los siguientes tres conjuntos de requerimientos:

* [Aplicación Pendientes](https://drive.google.com/open?id=1RRxx-D7SJg2VhgSCHiP3klb1Y6aPGdkb)
* [Aplicación Notas](https://drive.google.com/open?id=1XZ_F2dh4Ubtoz78rtAZIyEqwAsqMHV2K)
* [Aplicación Calendario](https://drive.google.com/open?id=11eQWw1Zvkso6oFDKv0R2HaxOCedf4sGw)

### Objetivos formativos ###

* Desarrollar un API REST. 

* Aplicar mejores prácticas actuales para el desarrollo con stack de Java + Spring.

* Poner en práctica una metodología de desarrollo ágil. 

### Objetivos de curso ###

* Aplicar técnicas y herramientas orientadas a objetos para la modelación del diseño de software.

* Documentar la toma de decisiones durante la etapa de diseño del software.

* Comprender los diferentes niveles de abstracción en que deben expresarse las soluciones de problemas de diseño.

### Contenidos del curso ###

* El diseño de la arquitectura del software

* Tendencias en el diseño de software.

### Metodología ###

* El proyecto se desarrollará en grupos de entre 1 y 5 personas.

* El proyecto seguirá un proceso de desarrollo ágil; consistirá de dos iteraciones (*sprints*) de una semana cada uno. Cada semana, el grupo debe realizar una sesión de planificación (*sprint planning*) donde se establecerá cuál será el trabajo que cada miembro realizará en el sprint, mientras que el resto del tiempo se utilizará para ejecutar el diseño planificado. El producto de la sesión de planificación será una serie de tareas documentadas en el gestor de tareas asociado con el repositorio de código y asignadas a los miembros del equipo.

* Cada miembro del equipo trabajará cada una de sus tareas en una rama (*feature branch*) aparte; se recomienda usar el formato `feature/<# tarea>` para nombrar las tareas; por ejemplo `feature/1` o `feature/25` donde 1 y 25 son números de tarea. 

* Para que una tarea se considere completa debe realizarse la programación así como las pruebas automatizadas correspondientes. Cuando la tarea es finalizada, se debe crear un *pull request* que deberá ser revisado y aprobado por el profesor antes de fusionarla (*merge*) con la rama *develop*.  La revisión del *pull request* debe incluir asegurarse de que el código en esa rama compile correctamente y pase todas las pruebas automatizadas.

## Rúbrica ##

### Planificación 1 (5 pts) ###

* (5) Documenta y se asigna una o más tareas a desarrollar durante la iteración. Las tareas incluyen una descripción del trabajo a realizar así como los correspondientes artefactos de diseño.

* (3) Documenta y se asigna una o más tareas a desarrollar durante la iteración. Algunas de las tareas no tienen una descripción adecuada o no incluyen artefactos de diseño.

* (1) Documenta y se asigna una o más tareas a desarrollar durante la iteración. La mayoría de las tareas no tienen una descripción adecuada o no incluyen artefactos de diseño.

### Desarrollo de tareas 1 (5 pts) ###

* (5) Completa todas las tareas que le son asignadas.

* (3) Completa la mayoría de las tareas que le son asignadas.

* (1) No completa la mayoría de las tareas que le son asignadas.

### Planificación 2 (5 pts) ###

* (5) Documenta y se asigna una o más tareas a desarrollar durante la iteración. Las tareas incluyen una descripción del trabajo a realizar así como los correspondientes artefactos de diseño.

* (3) Documenta y se asigna una o más tareas a desarrollar durante la iteración. Algunas de las tareas no tienen una descripción adecuada o no incluyen artefactos de diseño.

* (1) Documenta y se asigna una o más tareas a desarrollar durante la iteración. La mayoría de las tareas no tienen una descripción adecuada o no incluyen artefactos de diseño.

### Desarrollo de tareas 2 (5 pts) ###

* (5) Completa todas las tareas que le son asignadas.

* (3) Completa la mayoría de las tareas que le son asignadas.

* (1) No completa la mayoría de las tareas que le son asignadas.

Total: **20 pts**
