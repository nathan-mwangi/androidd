package net.ezra.ui.location

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import net.ezra.R
import net.ezra.navigation.ROUTE_ADD_PRODUCT

class branch2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           rbscreen(navHostController = rememberNavController())
        }
    }
}

@Composable
fun rbscreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E2F))
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.b2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Radison Blu Hotel",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFD700),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = "Ksh 7,500",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFE91E63),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Popular amenities",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF00BFFF),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Base2Icon(iconRes = R.drawable.wifi, label = "Free WiFi")
            Base2Icon(iconRes = R.drawable.restaurant, label = "Restaurant")
            Base2Icon(iconRes = R.drawable.pool, label = "Pool")
            Base2Icon(iconRes = R.drawable.parking, label = "Parking")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Along with a restaurant, this smoke-free hotel has a fitness center and a bar/lounge. WiFi in public areas is free. Other amenities include 2 coffee shops/cafés, valet parking, and 24-hour room service.",
            fontSize = 16.sp,
            color = Color(0xFFB0C4DE),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navHostController.navigate(ROUTE_ADD_PRODUCT)},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50))
        ) {
            Text(text = "Book Now", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun Base2Icon(iconRes: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color(0xFF00FA9A),
            modifier = Modifier.size(32.dp)
        )
        Text(text = label, fontSize = 14.sp, color = Color(0xFF7FFFD4))
    }
}




