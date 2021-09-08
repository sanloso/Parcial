var client = (function () {
//    var url = 'https://git.heroku.com/arepweather.git';
    var url = 'http://localhost:3500';

    function getCity(city, callback) {
        axios.get(url+'/https://api.openweathermap.org/data/2.5/weather?q=/'+city+"&appid=b9a51334e335723c69229ad6037bc26b"
        ).then(function f (res){
            callback(res.data);
        }).catch(function (error) {
            alert(error.response.data);
        })

    }

    function printCity(city){
        document.getElementById("demo").innerHTML = " Sensacion de temperatura: "+city.main.feels_like+"°C Humedad: "+city.main.humidity+"%" + "Temperatura: "+city.main.temp+"°C Descripción: "+city.city[0].description + "Temperatura Maxima: "+city.main.temp_max + " Temperatura Minima: " + city.main.temp_min;
    }

    function answer(data){
        getCity(data, printCity)
    }

    return {
        getCity: getCity,
        answer: answer
    }
})();