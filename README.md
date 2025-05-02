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

  Cara memanggil viewmodel pada screen


               fun LoginScreen(
                   navigateBack: () -> Unit,
                   signUp:()-> Unit,
                   viewModel: LoginViewModel = viewModel(factory = LocalContext.current.let {
                       ViewModelFactory.getInstance(
                           LocalContext.current,
                           it.dataStore
                       )
                   }),
                   goToProfile: (String) -> Unit,
                   lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
               ) {
  - Networking dengan retrofit
               - com/example/pinjambuku/network/
                   - ApiConfig.kt
                   - ApiService.kt

   - File Api ada pada https://github.com/ratriretno/api-pinjam-buku
 
   - Pertukaran data melalui REST API dengan PHP dan MySQL melibatkan penggunaan protokol HTTP untuk mengakses dan memanipulasi data yang disimpan dalam database MySQL. PHP digunakan untuk membuat skrip yang bertindak sebagai backend untuk API, menangani koneksi database, query, dan menghasilkan respons dalam format JSON.
 
   - Base url : https://pinjambuku.solfagaming.com
   - Contoh penggunaan api : https://ratriretnowati.postman.co/workspace/Ratri-Retnowati's-Workspace~c9967c4f-5f0d-45fe-88aa-8ec67284e69f/collection/44025365-0a99fa39-af65-470f-a4bd-963825f9d746?action=share&creator=44025365
    
