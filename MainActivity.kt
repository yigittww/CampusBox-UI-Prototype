MainActivity.kt

package com.example.campusboxoutlet

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.campusboxoutlet.ui.theme.CampusBoxOutletTheme

data class BoxItem(
    val id: String,
    val name: String,
    val description: String,
    val price: String,
    val oldPrice: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusBoxOutletTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CampusBoxApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusBoxApp(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf("Home") }
    var selectedBox by remember { mutableStateOf<BoxItem?>(null) }
    val context = LocalContext.current

    val boxes = listOf(
        BoxItem("1", "Survival Box", "Deep discounts on essentials.", "$10.00", "$25.00"),
        BoxItem("2", "Pantry Box", "Bulk staples and dry goods.", "$15.00", "$35.00"),
        BoxItem("3", "Treat Box", "Snacks, drinks, and study treats.", "$8.00", "$18.00")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "CampusBox Outlet",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2C3E50))
        )

        if (currentScreen == "Home") {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Available Boxes Today",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(boxes) { box ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedBox = box
                                    currentScreen = "Checkout"
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = box.name,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF27AE60)
                                    )
                                    Row {
                                        Text(
                                            text = "${box.price} ",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFE74C3C)
                                        )
                                        Text(
                                            text = box.oldPrice,
                                            fontSize = 14.sp,
                                            textDecoration = TextDecoration.LineThrough,
                                            color = Color.Gray
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = box.description, color = Color(0xFF555555), fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Tap to buy & pickup ➔",
                                    color = Color(0xFF3498DB),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                TextButton(
                    onClick = { currentScreen = "Home" },
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(text = "⬅ Back to Home", fontSize = 16.sp, color = Color(0xFF3498DB))
                }

                Text(
                    text = "Checkout Summary",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Selected Box:", fontSize = 13.sp, color = Color.Gray)
                        Text(
                            text = selectedBox?.name ?: "",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C3E50)
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Discounted Total:", fontSize = 13.sp, color = Color.Gray)
                        Text(
                            text = selectedBox?.price ?: "",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE74C3C)
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Pickup Time:", fontSize = 13.sp, color = Color.Gray)
                        Text(
                            text = "Today, 18:00 - 20:00 (In-Store)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2C3E50)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        Toast.makeText(context, "Payment Successful! Order Ready.", Toast.LENGTH_LONG).show()
                        currentScreen = "Home"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Pay & Confirm Order (via Stripe)", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CampusBoxPreview() {
    CampusBoxOutletTheme {
        CampusBoxApp()
    }
}
