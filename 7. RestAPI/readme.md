
# 1. Instalar dependencias

### Dependencia para instanciar facilmente los viewmodels
```
implementation("androidx.fragment:fragment-ktx:1.4.1")
```
### Dependencia para hacer un llamado a un API HTTP
Retrofit es una librería ampliamente usada para hacer peticiones HTTP
```
implementation("com.squareup.retrofit2:retrofit:2.9.0")  
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
```
Retrofit cuenta con un core, pero también un conversor basado en Gson de Google

### Dependencias de las corutinas
Para poder lanzar corutinas use los diferentes ambientes. Para lanzar una corutina desde Activity/Fragment debe usar
```
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
```

Para poder lazar corutinas desde un viewmodel use
```
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
```


### Instanciar un viewmodel
El siguiente viewmodel será una nueva instancia de viewmodel
```
private val viewModel: CustomViewModel by viewModels()
```

### Instanciar un viewmodel compartido
El siguiente viewmodel será una única instancia para la actividad y sus fragmentos
```
private val viewModel: CustomViewModel by activityViewModels()
```

# 2. Configurar Retrofit
Use una estructura de carpetas similar a 
```
- mainpackage
   - model
      - repository
      - entity
      - services
```
En repository debe crear una interfaz con el CRUD del servicio
```
import icesi.edu.co.emptytest.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface PokedexRepository {
    @GET("pokemon/{pokemon}")
    fun getPokemon(@Path("pokemon") pokemon: String): Call<Pokemon>
}
```
En entity van los modelos de base de datos para poder hacer la serialización. Un ejemplo simple:
```
data class Pokemon(
    var name:String
)
```
En donde sólo se está deserializando la propiedad nombre.</br> </br>

Finalmente en la carpeta services puede crear un objeto que permita hacer los llamados
```
package icesi.edu.co.emptytest.util

import icesi.edu.co.emptytest.repository.PokedexRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {

    private val pokedexService = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val pokedexRepository: PokedexRepository = pokedexService.create(PokedexRepository::class.java)



}
```
Como se observa la variable pokedexRepository está expuesta para ser usada desde la capa de viewModel


