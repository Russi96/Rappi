# Rappi TV

<p>Esta aplicación permite a los usuarios obtener una lista de peliculas y series de televisión, con sus respectivos detalles al escoger una en especifico. Ademas cuenta con
un filtro en la seccion de peliculas por popularidad o mejor calificada, adicionalmente de un buscador para que sea mas rapido encontrar tu pelicula favorita.</p>

<p>No olvides que en el detalle puedes encontrar la lista de trailers o videos, y un homepage de cada una de las peliculas.</p>

<p>La aplicación usa la siguiente documentacion.</p> 👇🏻

<a href="https://www.themoviedb.org/documentation/api">API THE MOVIE DB</a>


## Arquitectura 🚀
<p>
El proyecto tiene implementado Clean Arquitecture el cual esta conformado por las siguientes capas:

![clean_arquitecture](https://user-images.githubusercontent.com/61768939/141804064-cf90f5e5-dd51-4424-8fb1-58b1a174156f.jpg)

- Capas de Presentacion = Esta capa interactua con la interfaz de usuario. Aqui se concentraran 
tanto la vista (Activities, Fragments, etc) como los ViewModels. En esta capa se implementa los diferentes
patrones de arquitectura MVVM 

- Capas de Casos de Uso = Los casos de uso o interactors, manejan las acciones que el usuario puede
desencadenar, es aquel que detona una accion en la aplicacion, son los que interactuan con los viewModels

- Capa de Dominio = Contiene las reglas de negocio de la aplicacion, en esta se encuentran los modelos, logica
que valida las cosas como contraseñas o correos (Se creo de tipo JAVA Y KOTLIN). En la capa de dominio no se usan
librerias o dependencias.

- Capa de Datos = En esta capa se definen las abstracciones para acceder a las fuentes de datos de la aplicacion.
Se utiliza el patron de repositorio en esta capa (Se creo de tipo Android Library)

  - Repository utiliza dos fuentes una Local Data Source = Base de datos - Archivo y una Remote Data Source = un API o un Sensor

- Capa de Framework = En esta capa implementamos las bibliotecas  externas y definimos el comportamiento de las
interfaces de las fuentes de datos
</p>

## Pantalla de Inicio 📺
![screenshot](https://user-images.githubusercontent.com/61768939/141806392-54ebb960-5936-4611-851d-d33da961d923.jpg)  ![screenshot_1](https://user-images.githubusercontent.com/61768939/141806562-3003b04c-4f15-4c12-ae40-00d86c2abb4b.jpg)

## Detalles ✅

![details](https://user-images.githubusercontent.com/61768939/141807010-f68c01df-0351-4908-a01d-ec07c577db51.jpg)

## Videos y HomePage ▶️

![videos](https://user-images.githubusercontent.com/61768939/141807921-ae6408da-48ff-42cf-8563-ef96297d5555.jpg)
![web](https://user-images.githubusercontent.com/61768939/141807963-db1ddcf7-b820-4ad8-854e-db3a6707b589.jpg)

## Categorias 🔝

![filter](https://user-images.githubusercontent.com/61768939/141808392-83e3acb3-7f9d-45f4-8242-d9bda2846560.jpg)


## Compativilidad 🔧

<li><strong>Minimum Android SDK</strong>: Glide v4 requires a minimum API level of 26.</li>
<li><strong>Compile Android SDK</strong>: Glide v4 requires you to compile against API 23 or later.</li>
<li>Gradle Kotlin DSL Primer</li>


## Futuras Mejoras ⚙️

<li><strong>Pagination</strong>: Paginar cuando se consulte las listas de peliculas y series de manera local como remota por medio de Paging 3, Retrofit y kotlin Flow :
Agregando las respectivas dependencias, luego crear un servicio que permita la actualizacion, paso siguiente implementar PagingSource y PageData para su respectiva actualizacion y al finalizar integrarlo con el viewModel y su respectiva vista</li>
<li><strong>Series Offline</strong>: Leer de manera local la lista de series por medio de room creando una nueva tabla que me permita consultar cuando la aplicacion no cuente con internet</li>
<li><strong>Unit Test Capa Presentacion</strong>: Realizar pruebas unitarias de la capa de presentacion con mockito y junit validando las respectivas condiciones que se encuentran y el viewModel</li>


## Construido por 🛠️
```
Daniel Felipe Alvarado Russi
```
