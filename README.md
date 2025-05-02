Berikut beberapa cara penggunaan proyek pinjambuku :

- Dependency Injection Manual
     -  com/example/pinjambuku/di/
           -  Injection.kt
           -   ViewModelFactory.kt

- Repository untuk memanggil data
      com/example/pinjambuku/repository/
            BookRepository.kt

- Viewmodel telah diinject repository
      com/example/pinjambuku/ui/screen/
            LoginViewModel.kt

                    class LoginViewModel(private val repository: BookRepository) : ViewModel() {
