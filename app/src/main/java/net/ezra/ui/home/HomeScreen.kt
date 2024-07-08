package net.ezra.ui.home



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import net.ezra.R
import net.ezra.navigation.ROUTE_ADD_PRODUCT
import net.ezra.navigation.ROUTE_BM
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_HR
import net.ezra.navigation.ROUTE_ME
import net.ezra.navigation.ROUTE_MH
import net.ezra.navigation.ROUTE_OS
import net.ezra.navigation.ROUTE_RB
import net.ezra.navigation.ROUTE_RC
import net.ezra.navigation.ROUTE_REGISTER
import net.ezra.navigation.ROUTE_RO
import net.ezra.navigation.ROUTE_SETTINGS
import net.ezra.navigation.ROUTE_SS
import net.ezra.navigation.ROUTE_ST
import net.ezra.navigation.ROUTE_VIEW_PROD
import net.ezra.ui.students.BottomBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(navHostController = rememberNavController())
        }
    }
}

@Composable
fun HomeScreen(navHostController: NavHostController) {
    Scaffold(

        bottomBar = { BottomBar(navController = navHostController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar()
            Spacer(modifier = Modifier.height(16.dp))
            TopBar()
            Spacer(modifier = Modifier.height(16.dp))
            TopCities(navController = navHostController)
        }
    }
}

@Composable
fun TopBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberImagePainter("https://via.placeholder.com/150"), // Replace with your image URL
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "View your favorite hotels",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f)
        )


        }
    }


@Composable
fun SearchBar() {
    var searchQuery by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_person), // Replace with your search icon
            contentDescription = "Search",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onSearch = { /* Perform search */ }
            ),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.book), // Replace with your microphone icon
                contentDescription = "null"
            )
        }
    }
}


@Composable
fun TopCities(navController: NavController) {
    val cities = listOf(
        "Venice, Italy",
        "Lisbon, Portugal",
        "Muscat, Oman",
        "Paris, France",
        "Kyoto, Japan",
        "Havana, Cuba"
    )
    val images = listOf(
        R.drawable.city1, // Replace with your image resources
        R.drawable.city3,
        R.drawable.city4,
        R.drawable.city5,
        R.drawable.city6,
        R.drawable.city5
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cities.size) { index ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(100.dp)
            ) {
                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = cities[index],
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(15.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text("Nearest Hotel")
        }
        Button(onClick = { /*TODO*/ }) {
            Text("Hot Deals")
        }
    }

    Row {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                 .clickable { navController.navigate(ROUTE_HR) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.b1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Hard Rock Hotel"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_RB) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.b2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Radison Blu Hotel"
                    )
                }
            }
        }
    }

    Row {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_ST) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.b3),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Shaky Tapers Hotel"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_OS) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.b4),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Olive Springs Hotel"
                    )
                }
            }
        }
    }

    Row {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .size(170.dp)
                .clickable { navController.navigate(ROUTE_ME) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.b5),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Masked eater Hotel"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_BM) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.b6),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Blu morning Hotel"
                    )
                }
            }
        }
    }


    Row {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_RC) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.b7),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Royal Crest Hotel"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .size(170.dp)
                .clickable { navController.navigate(ROUTE_RO) },




            elevation = 5.dp,

        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.b8),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Regal Oasis"
                    )
                }
            }
        }
    }

    Row {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_MH) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.b9),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Modern Haven"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_SS) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.b10),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Sunset Sands Hotel"
                    )
                }
            }
        }
    }


}



@Composable
fun BottomBar(navController: NavController) {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation {
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.book), // Replace with your home icon
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") },
            selected = selectedIndex.value == 0,
            onClick = {
                selectedIndex.value = 0
                navController.navigate(ROUTE_HOME)
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
            },
            label = { Text("Favorites") },
            selected = selectedIndex.value == 1,
            onClick = {
                selectedIndex.value = 1
                navController.navigate(ROUTE_VIEW_PROD) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
            },
            label = { Text("Profile") },
            selected = selectedIndex.value == 2,
            onClick = {
                selectedIndex.value = 2
                navController.navigate(ROUTE_REGISTER) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }
            }
        )
    }
}






