
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

### Dependencia para descargar imágenes
```
implementation("com.github.bumptech.glide:glide:4.16.0")
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
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val pokedexRepository: PokedexRepository = pokedexService.create(PokedexRepository::class.java)



}
```
Como se observa la variable pokedexRepository está expuesta para ser usada desde la capa de viewModel


# 3. Capa ViewModel
Esta capa es responsable de modelar los datos que va a ver nuestro usuario.
De esta forma, en el ejemplo de Pokemon, vamos a tener un viewModel similar a:
```
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import icesi.edu.co.emptytest.model.Pokemon

class PokedexViewModel :ViewModel(){

    private val _pokemon = MutableLiveData<Pokemon>()
    private val pokemon:LiveData<Pokemon> get() = _pokemon

}
```
En esta capa se usan las corutinas para poder ejecutar las tareas en segundo plano.

Un método de corutina se ve como esto:
```
fun getPokemon(query:String){
   viewModelScope.launch(Dispatchers.IO) {
      ...
   }
}
```
Donde Dispatcher.IO significa que la operación dentro de las llaves se hará en segundo plano


### Instanciar un viewmodel
Si usted quiere instanciar un viewModel desde una Activity
```
private val viewModel: CustomViewModel by viewModels()
```

### Instanciar un viewmodel compartido
Si usted quiere instanciar un ViewModel desde un fragmento, tenga en cuenta que el viewmodel tendrá alcance de Actividad, así que este objeto será igual para todos los fragmentos de la actividad de host en común
```
private val viewModel: CustomViewModel by activityViewModels()
```


### Usar Retrofit
Siempre dentro de una corutina se puede usar retrofit como se muestra en el ejemplo
```
fun getPokemon(query:String){
        viewModelScope.launch(Dispatchers.IO) {
            var response = RetrofitServices.pokedexRepository.getPokemon(query).execute()
            val pokemon = response.body()
        }
    }
```




