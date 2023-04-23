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
El siguiente viewmodel será una única instancia para la actividad y sus fragmentos. <b>Esta el la forma recomendada de instanciar viewmodels en fragmentos</b>
```
private val viewModel: CustomViewModel by activityViewModels()
```
