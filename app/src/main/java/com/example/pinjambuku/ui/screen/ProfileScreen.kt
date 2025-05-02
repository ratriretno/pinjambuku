package com.example.pinjambuku.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pinjambuku.R
import com.example.pinjambuku.di.ViewModelFactory
import com.example.pinjambuku.network.Constant.dataStore
import com.example.pinjambuku.network.ResultNetwork
import com.example.pinjambuku.model.ProfileItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit,
    idUser: String,
    viewModel: ProfileViewModel = viewModel(factory = LocalContext.current.let {
        ViewModelFactory.getInstance(
            LocalContext.current,
            it.dataStore
        )
    }),

    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val context = LocalContext.current // <-- G
    val isLoading by viewModel.isLoading.collectAsState()//to check valid email format

    Log.i("id profile", idUser)
    if (idUser.isNotEmpty()){
        viewModel.profile(idUser)
    }

    var profile = ProfileItem("", "", "", "", "")

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
//                    Toast.makeText(
//                        context,
//                        result.data.message,
//                        Toast.LENGTH_SHORT
//                    ).show()

                    profile = result.data.profile

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
                        Text(text = "Profile")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
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
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {},
//                shape = CircleShape, containerColor = colorResource(id = R.color.hijau_muda)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Edit,      //Icon
//                    contentDescription = "Edit Button"
//                )
//            }
//        }

    ) { innerPadding ->
        if (isLoading) {
            BigCircularLoadingLogin()
        } else {

            //var text by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Box(
                    modifier = Modifier                 //box ke-1
                        .fillMaxWidth()
                        .height(230.dp)
                        .padding(10.dp)
                        .background(
                            colorResource(id = R.color.abu),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    //.padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier.size(170.dp),
                        shape = CircleShape,
                        border = BorderStroke(width = 2.dp, color = Color.Gray),
                        color = Color.White
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_user),
                            contentDescription = "user picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                }


                Box(
                    modifier = Modifier         //box ke-2
                        .fillMaxWidth()
                        .fillMaxHeight()
                        //.height(300.dp)
                        .padding(10.dp)
                        .background(
                            colorResource(id = R.color.krem),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    //.padding(innerPadding),

                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            //.verticalScroll(scrollState)
                            .padding(16.dp)
                            .imePadding(),
                        //.windowInsetsPadding(WindowInsets.ime)
                        //.navigationBarsWithImePadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp),

                        ) {
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = profile.fullname)
                        }
//                    item {
//                        Spacer(modifier = Modifier.height(20.dp))
//                        Text(text = "username : ratri")
//                    }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = profile.email)
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = profile.city)
                        }

                        item {
                            Spacer(modifier = Modifier.height(40.dp))
                            Button(
                                modifier = Modifier
                                    .width(250.dp)
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(
                                        id = R.color.merah
                                    )
                                ),
                                onClick = {viewModel.logout()
                                    navigateBack()}
                            ) {
                                Text(stringResource(R.string.logout_button), fontSize = 18.sp)

                            }
                        }


                    }
                }


            }

        }


    }
}

/*
@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    PinjamBukuTheme {

        Profile()

    }
}

 */