# Parcial

## Despliegue:
[![Deployed to Heroku](https://www.herokucdn.com/deploy/button.png)](https://git.heroku.com/arepweather.git)

## Descripción:
Para descargar este proyecto ejecute el comando:
> git clone https://git.heroku.com/arepweather.git

Para correr el proyecto de manera local corra la clase
> Server

Luego vaya a su navegador y coloque:
> http://localhost:3500/index.html

Esto lo llevara al index de la pagina, en caso tal de no agregar el path arrojara un error 404.

## Desarrollo:

Se inicia el desarrollo del parcial creando dos sockets `client` y `server`. Una vez los sockets estuvieran desarrollados de la manera correcta se procede a 
desarrollar el manejo de la `URI` y a separarla por `PATH` y `QUERY` de esta manera podriamos encontrar los archivos html deseados y los valores de las variables 
que se utilizaria para averiguar el clima de la ciudad deseada.

Una vez se logra realizar el manejo de manera correcta de la `URI` se procede a realizar la conexión con el API, esta conexión se puede ver en la clase `Conecction`
en donde se arma la URL necesaria para el funcionamiento del API.

Después de realizada la clase se intenta desarrollar la clase client.js para mostrar los resultados en pantalla de forma amigable para el usuario. 



