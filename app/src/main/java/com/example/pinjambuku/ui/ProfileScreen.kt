package com.example.pinjambuku.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pinjambuku.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navigateBack: () -> Unit, idUser: String){

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    //.padding(5.dp)
                    ,
                    contentAlignment = Alignment.Center){
                    //Text(text = "Profile",)
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
        floatingActionButton = {
            FloatingActionButton(onClick = {},
                shape = CircleShape, containerColor = colorResource(id = R.color.hijau_muda)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,      //Icon
                    contentDescription = "Edit Button"
                )
            }
        }

        ){ innerPadding ->

        //var text by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ){
                Box(modifier = Modifier                 //box ke-1
                    .fillMaxWidth()
                    .height(230.dp)
                    .padding(10.dp)
                    .background(colorResource(id = R.color.abu),shape = RoundedCornerShape(20.dp)),
                    //.padding(innerPadding),
                    contentAlignment = Alignment.Center
                ){
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


                Box(modifier = Modifier         //box ke-2
                    .fillMaxWidth()
                    .fillMaxHeight()
                    //.height(300.dp)
                    .padding(10.dp)
                    .background(colorResource(id = R.color.krem),shape = RoundedCornerShape(20.dp)),
                    //.padding(innerPadding),

                    contentAlignment = Alignment.Center
                ){
                    LazyColumn (
                        modifier = Modifier
                            .fillMaxSize()
                            //.verticalScroll(scrollState)
                            .padding(16.dp)
                            .imePadding(),
                        //.windowInsetsPadding(WindowInsets.ime)
                        //.navigationBarsWithImePadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp),

                        ){
                        item{
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = "Name : Ratri Retno")
                        }
                        item{
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = "username : ratri")
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = "Email : ratri@gmail.com")
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = "City : Depok")
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