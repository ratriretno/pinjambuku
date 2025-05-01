package com.example.pinjambuku.ui

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pinjambuku.R
import com.example.pinjambuku.di.ViewModelFactory
import com.example.pinjambuku.network.Constant.dataStore
import com.example.pinjambuku.network.ResultNetwork
import com.example.pinjambuku.network.SignupItem
import com.example.pinjambuku.ui.screen.LoginViewModel
import com.example.pinjambuku.ui.screen.SignupViewModel
import com.example.pinjambuku.ui.theme.PinjamBukuTheme
import kotlin.math.sign


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(
    navigateBack: () -> Unit,
    goToProfile: (String) -> Unit,
    viewModel: SignupViewModel = viewModel(factory = LocalContext.current.let {
        ViewModelFactory.getInstance(
            LocalContext.current,
            it.dataStore
        )
    }),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,

){

    val isLoading by viewModel.isLoading.collectAsState()//to check valid email format

    val focusManager = LocalFocusManager.current
    var signUpUserNameValue by remember{ mutableStateOf("") }
    var signUpNameValue by remember{ mutableStateOf("") }
    var signUpEmailValue by remember{ mutableStateOf("") }
    var signupPasswordValue by remember { mutableStateOf("") }
    var confirmPasswordValue by remember { mutableStateOf("") }
    var signupCityValue by remember { mutableStateOf("") }

    val context = LocalContext.current // <-- Get context for Toast
    var isSignUpPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    val isEmailValid = isValidEmail(signUpEmailValue)                      //to check valid email format

    viewModel.result.observe(lifecycleOwner) { result ->
        if (result != null) {
            when (result) {
                is ResultNetwork.Loading -> {
                    viewModel.setLoading(true)
                    Log.i("login update?", isLoading.toString())

                }

                is ResultNetwork.Success -> {
                    viewModel.setLoading(false)

                    Log.i("login update?", isLoading.toString())
                    Log.i("login update?", result.data.toString())
                    Toast.makeText(
                        context,
                        result.data.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.setLoginSetting(result.data)
                    goToProfile(result.data.idUser)
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

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    //.padding(5.dp)
                    ,
                    contentAlignment = Alignment.Center
                ){
                    //Text(text = "Sign Up",)
                }
            },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Button")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.hijau),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )

            )

        },

        ){ innerPadding ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .imePadding()
        ){

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(10.dp)
                .background(colorResource(id = R.color.orange),shape = RoundedCornerShape(20.dp)),
                //.padding(innerPadding),

                contentAlignment = Alignment.Center
            ){
                //Banner()

                Text(text = "Create an Account", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier
                .fillMaxWidth(),

                //.padding(innerPadding),
                contentAlignment = Alignment.Center,


                ){


                if (isLoading){
                    BigCircularLoadingLogin()
                } else{
                    LazyColumn (
                        modifier = Modifier
                            .fillMaxSize()
                            //.verticalScroll(scrollState)
                            //.padding(16.dp)
                            .imePadding(),
                        //.windowInsetsPadding(WindowInsets.ime)
                        //.navigationBarsWithImePadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ){

                        item{
                            Text(modifier = Modifier
                                //.fillMaxWidth()
                                .width(250.dp),
                                //align(Alignment.CenterEnd)
                                text = stringResource(R.string.signup_username_title),
                                fontWeight = FontWeight.Bold,
                                //modifier = Modifier.padding(bottom = 5.dp)
                            )

                            OutlinedTextField(value = signUpUserNameValue, onValueChange = {signUpUserNameValue = it}, label = { Text(
                                stringResource(R.string.signup_username), fontSize = 15.sp, fontStyle = FontStyle.Italic
                            )}, singleLine = true, placeholder = { Text(text = stringResource(R.string.signup_username))} ,modifier = Modifier.width(250.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = colorResource(id = R.color.white),    // background when focused
                                    unfocusedContainerColor = colorResource(id = R.color.white)   // background when not focused)
                                )
                            )
                        }

                        item{
                            Text(modifier = Modifier
                                //.fillMaxWidth()
                                .width(250.dp),
                                //align(Alignment.CenterEnd)
                                text = stringResource(R.string.signup_fullname_title),
                                fontWeight = FontWeight.Bold,
                                //modifier = Modifier.padding(bottom = 5.dp)
                            )

                            OutlinedTextField(value = signUpNameValue, onValueChange = {signUpNameValue = it}, label = { Text(
                                stringResource(R.string.signup_fullname), fontSize = 15.sp, fontStyle = FontStyle.Italic
                            )}, singleLine = true, placeholder = { Text(text = stringResource(R.string.signup_fullname))} ,modifier = Modifier.width(250.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = colorResource(id = R.color.white),    // background when focused
                                    unfocusedContainerColor = colorResource(id = R.color.white)   // background when not focused)
                                )
                            )
                        }

                        item{
                            Text(modifier = Modifier
                                //.fillMaxWidth()
                                .width(250.dp),
                                //align(Alignment.CenterEnd)
                                text = stringResource(R.string.signup_email_title),
                                fontWeight = FontWeight.Bold
                                //modifier = Modifier.padding(bottom = 5.dp)
                            )


                            OutlinedTextField(value = signUpEmailValue, onValueChange = {signUpEmailValue = it}, label = { Text(
                                stringResource(R.string.email_example),fontSize = 15.sp, fontStyle = FontStyle.Italic
                            )}, singleLine = true, placeholder = { Text(text = stringResource(R.string.email_example))} ,modifier = Modifier.width(250.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = colorResource(id = R.color.white),    // background when focused
                                    unfocusedContainerColor = colorResource(id = R.color.white)     // background when not focused))
                                )
                            )

                            if (signUpEmailValue.isNotBlank() && !isEmailValid) {       //warning for invalid email address
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp) // optional padding to align visually
                                ) {
                                    Text(
                                        text = "Invalid email format",
                                        color = Color.Red,
                                        fontSize = 12.sp,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }

                        }

                        item{
                            Text(modifier = Modifier
                                //.fillMaxWidth()
                                .width(250.dp),
                                //align(Alignment.CenterEnd)
                                text = stringResource(R.string.signup_password),
                                fontWeight = FontWeight.Bold
                                //modifier = Modifier.padding(bottom = 5.dp)
                            )

                            OutlinedTextField(value = signupPasswordValue, onValueChange = {signupPasswordValue = it}, label = { Text(
                                stringResource(R.string.signup_password_placeholder),fontSize = 15.sp, fontStyle = FontStyle.Italic
                            )}, singleLine = true,
                                //visualTransformation = PasswordVisualTransformation()
                                visualTransformation = if (isSignUpPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    val icon = if (isSignUpPasswordVisible)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Default.VisibilityOff

                                    IconButton(onClick = { isSignUpPasswordVisible = !isSignUpPasswordVisible }) {
                                        Icon(imageVector = icon, contentDescription = if (isSignUpPasswordVisible) "Hide password" else "Show password")
                                    }
                                },
                                modifier = Modifier.width(250.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = colorResource(id = R.color.white),    // background when focused
                                    unfocusedContainerColor = colorResource(id = R.color.white)     // background when not focused
                                )
                            )
                        }

                        item{
                            Text(modifier = Modifier
                                //.fillMaxWidth()
                                .width(250.dp),
                                //align(Alignment.CenterEnd)
                                text = stringResource(R.string.signup_confirm__password),
                                fontWeight = FontWeight.Bold
                                //modifier = Modifier.padding(bottom = 5.dp)
                            )

                            OutlinedTextField(value = confirmPasswordValue, onValueChange = {confirmPasswordValue = it}, label = { Text(
                                stringResource(R.string.signup_password_placeholder),fontSize = 15.sp, fontStyle = FontStyle.Italic
                            )}, singleLine = true,
                                //visualTransformation = PasswordVisualTransformation()
                                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    val icon = if (isConfirmPasswordVisible)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Default.VisibilityOff

                                    IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                                        Icon(imageVector = icon, contentDescription = if (isConfirmPasswordVisible) "Hide password" else "Show password")
                                    }
                                },
                                modifier = Modifier.width(250.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = colorResource(id = R.color.white),    // background when focused
                                    unfocusedContainerColor = colorResource(id = R.color.white)     // background when not focused
                                )
                            )
                        }

                        item{
                            Text(modifier = Modifier
                                //.fillMaxWidth()
                                .width(250.dp),
                                //align(Alignment.CenterEnd)
                                text = stringResource(R.string.signup_city),
                                fontWeight = FontWeight.Bold
                                //modifier = Modifier.padding(bottom = 5.dp)
                            )


                            OutlinedTextField(value = signupCityValue, onValueChange = {signupCityValue = it}, label = { Text(
                                stringResource(R.string.signup_city_placeholder),fontSize = 15.sp, fontStyle = FontStyle.Italic
                            )}, singleLine = true, modifier = Modifier.width(250.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = colorResource(id = R.color.white),    // background when focused
                                    unfocusedContainerColor = colorResource(id = R.color.white)     // background when not focused))
                                )
                            )
                        }

                        //

                        item{
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(onClick = {
                                focusManager.clearFocus()
                                if (signUpUserNameValue.isBlank() || signUpNameValue.isBlank() || signUpEmailValue.isBlank() || signupCityValue.isBlank() || confirmPasswordValue.isBlank() || signUpNameValue.isBlank()) {
                                    Toast.makeText(
                                        context,
                                        "The field must not be empty",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (!isEmailValid) {                   //check for invalid email format
                                    Toast.makeText(
                                        context,
                                        "Invalid email format",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Check if passwords match
                                } else if (signupPasswordValue != confirmPasswordValue) {
                                    Toast.makeText(
                                        context,
                                        "Passwords do not match",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    // Proceed with sign-up logic (e.g. save data, navigate, etc.)
                                    /*Toast.makeText(
                                        context,
                                        "Account created successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                     */
                                    viewModel.signup(item =
                                    SignupItem(
                                        email = signUpEmailValue,
                                        password = signupPasswordValue,
                                        fullname = signUpNameValue,
                                        username = signUpUserNameValue,
                                        city = signupCityValue
                                        ))
                                }

                            },
                                modifier = Modifier
                                    .width(250.dp)
                                    .height(48.dp),
                                colors= ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.hijau_muda) )
                            )
                            { Text(stringResource(R.string.signup_button), fontSize = 18.sp) }
                        }


                    }



                }

            }

                }


    }

}

/*
@Composable
fun Banner(modifier: Modifier = Modifier){
    Box(){
        Image(painter = painterResource(R.drawable.gambar), contentDescription = "coba",contentScale = ContentScale.Crop, modifier = Modifier.height(100.dp))
    }

}

 */

@Composable
fun BigCircularLoadingSignUp() {
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


@Preview(showBackground = true)
@Composable
fun SignUpPreview(){
    PinjamBukuTheme {

//        SignUp()

    }
}