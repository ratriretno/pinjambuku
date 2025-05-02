package com.example.pinjambuku.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.pinjambuku.R
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pinjambuku.di.ViewModelFactory
import com.example.pinjambuku.network.Constant.dataStore
import com.example.pinjambuku.model.LoginItem
import com.example.pinjambuku.network.ResultNetwork


@OptIn(ExperimentalMaterial3Api::class)
@Composable
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

    val context = LocalContext.current // <-- Get context for Toast
    var newTextValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isEmailValid = isValidEmail(newTextValue)

    val isLoading by viewModel.isLoading.collectAsState()//to check valid email format

    Log.i("screen", "login")

    viewModel.result.observe(lifecycleOwner) { result ->
        if (result != null) {
            when (result) {
                is ResultNetwork.Loading -> {
                    viewModel.setLoading(true)
//                    Log.i("login update?", isLoading.toString())

                }

                is ResultNetwork.Success -> {
                    viewModel.setLoading(false)

                    Log.i("login update id?", result.data.idUser)

                    viewModel.setLoginSetting(result.data)
                    if (result.data.login){
                        goToProfile(result.data.idUser)
                    } else{
                        Toast.makeText(
                            context,
                            result.data.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

                is ResultNetwork.Error -> {
                    viewModel.setLoading(false)
//                    binding.progressBar2.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Terjadi kesalahan" + result.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                        //.padding(5.dp)
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        //Text(text = "Sign Up",)
                    }
                },
                navigationIcon = {
                    IconButton(onClick =  navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.hijau),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )

            )

        },

        ) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp)
                    .background(
                        colorResource(id = R.color.orange),
                        shape = RoundedCornerShape(20.dp)
                    ),
                //.padding(innerPadding),

                contentAlignment = Alignment.Center
            ) {
                //Banner()

                Text(text = "Login", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            }

            if (isLoading){
                BigCircularLoadingLogin()
            } else{


                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    //.padding(innerPadding),
                    contentAlignment = Alignment.Center

                ) {


                    Column(
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        OutlinedTextField(value = newTextValue,
                            onValueChange = { newTextValue = it },
                            label = {
                                Text(
                                    stringResource(R.string.user_name),
                                    fontSize = 15.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            },
                            isError = newTextValue.isNotBlank() && !isEmailValid,
                            singleLine = true,
                            placeholder = { Text(text = stringResource(R.string.email_example)) },
                            modifier = Modifier.width(250.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = colorResource(id = R.color.white),    // background when focused
                                unfocusedContainerColor = colorResource(id = R.color.white)     // background when not focused
                            )
                        )

                        if (newTextValue.isNotBlank() && !isEmailValid) {       //warning for invalid email address
                            Text(
                                text = "Invalid email format",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .align(Alignment.Start)
                            )
                        }

                        OutlinedTextField(value = passwordValue,
                            onValueChange = { passwordValue = it },
                            label = {
                                Text(
                                    stringResource(R.string.user_password),
                                    fontSize = 15.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            },
                            singleLine = true,
                            //visualTransformation = PasswordVisualTransformation() ,
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val icon = if (isPasswordVisible)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff

                                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                                    )
                                }
                            },
                            modifier = Modifier.width(250.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = colorResource(id = R.color.white),    // background when focused
                                unfocusedContainerColor = colorResource(id = R.color.white)     // background when not focused))
                            )
                        )

                        Button(
                            onClick = {
                                when {
                                    newTextValue.isBlank() || passwordValue.isBlank() -> {
                                        Toast.makeText(
                                            context,
                                            "Email and Password must not be empty",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    !isValidEmail(newTextValue) -> {
                                        Toast.makeText(
                                            context,
                                            "Please enter a valid email address",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    else -> {

                                            viewModel.login(LoginItem(email = newTextValue, password = passwordValue))
                                        // Proceed with login logic
                                    }
                                }
                            },


                            modifier = Modifier
                                .width(250.dp),
                            //.height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.hijau_muda))
                        )
                        { Text(stringResource(R.string.login_button), fontSize = 18.sp) }

                        Row(modifier =
                        Modifier.width(250.dp)
                        ) {
                            Text(
                                text = "Belum punya akun?",
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.labelMedium,
                            )

                            Text(
                                text = "Register",
                                modifier = Modifier.clickable(
                                    onClick = signUp
                                ),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold, color = Color.Blue,

                            )
                        }


                    }
                }
            }


        }

    }

}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun BigCircularLoadingLogin() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview(){
//    PinjamBukuTheme{
//        LoginScreen()
//    }
//}