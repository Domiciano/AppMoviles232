## Ejercicio en clase
Para el ejercicio parta de este repositorio
```
https://github.com/Domiciano/Semana8Moviles
```

### Dependencia para instanciar facilmente los viewmodels
```
implementation 'androidx.fragment:fragment-ktx:1.4.1'
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

### Retrofit
Retrofit es una librería ampliamente usada para hacer peticiones HTTP
```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```
Retrofit cuenta con un core, pero también un conversor basado en Gson de Google

### Corutinas
Use las corutinas para crear funciones asíncronas
```
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4'
```

### Ciclo de vida
Para poder lanzar corutinas use los diferentes ambientes. Para lanzar una corutina desde Activity/Fragment debe usar
```
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
```

Para poder lazar corutinas desde un viewmodel use
```
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
```
