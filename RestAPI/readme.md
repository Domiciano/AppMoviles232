## Consumo de RestAPI desde Android


Para poder usar las corutinas use
```
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4'
```
Para poder usar el lyfeCycleScope (Scope de Activity/Fragment) use
```
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.0'
```
Para poder usar el viewModelScope (Scope de la capa de ViewModel) use
```
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'
```

Para poder usar los proveedores y facilitar la instancia de ViewModel sobre Activities/Fragments use
```
implementation 'androidx.fragment:fragment-ktx:1.5.2'
```
Use la siguiente librería para descargar Retrofit
```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
```

Use la siguiente librería para hacer transformaciones de JSON a Objetos
```
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```
