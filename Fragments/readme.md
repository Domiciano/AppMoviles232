### Dependencia para instanciar facilmente los viewmodels
```
implementation 'androidx.fragment:fragment-ktx:1.4.1'
```

### Instanciar un viewmodel
El siguiente viewmodel será una nueva instancia de viewmodel
```
private val viewModel: CustomViewModel by viewModels()
```

### Instanciar un viewmodel
El siguiente viewmodel será una única instancia para la actividad donde se esté llamando
```
private val viewModel: CustomViewModel by activityViewModels()
```
