package com.example.campusboxoutlet

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.campusboxoutlet.ui.theme.CampusBoxOutletTheme

data class BoxItem(
    val id: String,
    val name: String,
    val category: String,
    val tag: String,
    val description: String,
    val price: Double,
    val oldPrice: Double,
    val storeName: String,
    val distance: String,
    val pickupHours: String,
    val stockLeft: Int,
    val accentColor: Color
)

data class OrderItem(
    val orderId: String,
    val boxName: String,
    val storeName: String,
    val pickupWindow: String,
    val totalPaid: Double,
    val qrCodeNumber: String
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

@Composable
fun CampusBoxApp(modifier: Modifier = Modifier) {
    var currentTab by remember { mutableStateOf("Browse") }
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedBox by remember { mutableStateOf<BoxItem?>(null) }
    var isCheckingOut by remember { mutableStateOf(false) }
    var selectedPickupSlot by remember { mutableStateOf("17:30 - 18:30") }
    val context = LocalContext.current

    val ordersList = remember {
        mutableStateListOf(
            OrderItem("CBO-704", "Survival Pantry Pack", "Metro Campus Express", "Today, 18:00 - 19:30", 10.00, "PICKUP-704-GBC")
        )
    }

    val boxes = listOf(
        BoxItem(
            id = "1",
            name = "Survival Pantry Pack",
            category = "Budget",
            tag = "ESSENTIALS",
            description = "Ramen bundles, rolled oats, canned tuna & electrolyte drink powders.",
            price = 10.00,
            oldPrice = 24.50,
            storeName = "George St. Bodega",
            distance = "400m",
            pickupHours = "Today 17:00 - 20:30",
            stockLeft = 3,
            accentColor = Color(0xFFF97316)
        ),
        BoxItem(
            id = "2",
            name = "Student Staples Bundle",
            category = "Pantry",
            tag = "WEEKLY MEALS",
            description = "Pasta spirals 900g, tomato basil sauce, black beans & olive oil tin.",
            price = 15.00,
            oldPrice = 34.00,
            storeName = "Jarvis Mini Market",
            distance = "550m",
            pickupHours = "Today 16:30 - 19:00",
            stockLeft = 2,
            accentColor = Color(0xFF0284C7)
        ),
        BoxItem(
            id = "3",
            name = "Study Fuel Treat Box",
            category = "Snacks",
            tag = "SNACK PACK",
            description = "Cold brew canned lattes, salted biscuits, roasted nuts & dark chocolate.",
            price = 8.50,
            oldPrice = 19.00,
            storeName = "Campus Snack Corner",
            distance = "750m",
            pickupHours = "Today 18:00 - 22:00",
            stockLeft = 6,
            accentColor = Color(0xFF8B5CF6)
        ),
        BoxItem(
            id = "4",
            name = "Quick Breakfast Crate",
            category = "Pantry",
            tag = "MORNING CRUNCH",
            description = "Almond milk carton, honey granola cluster box & protein flapjack bars.",
            price = 12.00,
            oldPrice = 27.50,
            storeName = "George St. Bodega",
            distance = "400m",
            pickupHours = "Tomorrow 08:30 - 11:30",
            stockLeft = 4,
            accentColor = Color(0xFF059669)
        )
    )

    val categories = listOf("All", "Budget", "Pantry", "Snacks")

    val filteredBoxes = if (selectedCategory == "All") {
        boxes
    } else {
        boxes.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBF7))
    ) {
        // Üst Bar (Warm Studio Header)
        Surface(
            color = Color(0xFF18181B),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                if (isCheckingOut) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF27272A),
                            modifier = Modifier
                                .size(36.dp)
                                .clickable { isCheckingOut = false }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("←", color = Color(0xFFF4F4F5), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Confirm Reservation",
                                color = Color.White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = (-0.3).sp
                            )
                            Text(
                                text = "Pickup Hold • CampusBox",
                                color = Color(0xFFA1A1AA),
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "CAMPUS",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "BOX",
                                    color = Color(0xFFF97316),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp
                                )
                            }
                            Text(
                                text = "Daily surplus rescue • Local bodegas",
                                color = Color(0xFFA1A1AA),
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF27272A),
                            border = BorderStroke(1.dp, Color(0xFF3F3F46))
                        ) {
                            Text(
                                text = "TORONTO DT",
                                color = Color(0xFFE4E4E7),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }

        // Ana İçerik Alanı
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (isCheckingOut && selectedBox != null) {
                val box = selectedBox!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp)
                ) {
                    // Seçili Ürün Kartı
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE4E4E7)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = box.accentColor.copy(alpha = 0.12f)
                                ) {
                                    Text(
                                        text = box.tag,
                                        color = box.accentColor,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                    )
                                }
                                Text(
                                    text = box.distance,
                                    fontSize = 11.sp,
                                    color = Color(0xFF71717A),
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = box.name,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF18181B),
                                letterSpacing = (-0.4).sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = box.description,
                                fontSize = 13.sp,
                                color = Color(0xFF71717A),
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))
                            HorizontalDivider(color = Color(0xFFF4F4F5))
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("COLLECTION POINT", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFA1A1AA))
                                    Text(box.storeName, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF18181B))
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("COLLECTION WINDOW", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFA1A1AA))
                                    Text(box.pickupHours, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFF97316))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "SELECT PICKUP TIME",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF71717A),
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("16:30 - 17:30", "17:30 - 18:30", "18:30 - 20:00").forEach { slot ->
                            val isSelected = selectedPickupSlot == slot
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) Color(0xFF18181B) else Color.White,
                                border = BorderStroke(1.dp, if (isSelected) Color(0xFF18181B) else Color(0xFFE4E4E7)),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedPickupSlot = slot }
                            ) {
                                Text(
                                    text = slot,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontFamily = FontFamily.Monospace,
                                    color = if (isSelected) Color.White else Color(0xFF52525B),
                                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 2.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Fiş / Makbuz Görünümlü Kart
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE4E4E7)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Retail Value", color = Color(0xFF71717A), fontSize = 12.sp)
                                Text("$${"%.2f".format(box.oldPrice)}", textDecoration = TextDecoration.LineThrough, color = Color(0xFFA1A1AA), fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Campus Outlet Savings", color = Color(0xFF15803D), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Text("-$${"%.2f".format(box.oldPrice - box.price)}", color = Color(0xFF15803D), fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Platform Handling", color = Color(0xFF71717A), fontSize = 12.sp)
                                Text("$0.00 (Self Pickup)", color = Color(0xFF18181B), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(color = Color(0xFFF4F4F5))
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("DUE AT STORE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF18181B), letterSpacing = 0.5.sp)
                                Text(
                                    text = "$${"%.2f".format(box.price)}",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color(0xFF18181B)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            val nextOrderId = "CBO-${(100..999).random()}"
                            ordersList.add(
                                0,
                                OrderItem(
                                    orderId = nextOrderId,
                                    boxName = box.name,
                                    storeName = box.storeName,
                                    pickupWindow = selectedPickupSlot,
                                    totalPaid = box.price,
                                    qrCodeNumber = "PICKUP-${(1000..9999).random()}-BOX"
                                )
                            )
                            Toast.makeText(context, "Box Reserved! Check Pickup Pass.", Toast.LENGTH_SHORT).show()
                            isCheckingOut = false
                            currentTab = "Orders"
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF97316)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Lock In Surprise Box • $${"%.2f".format(box.price)}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            } else if (currentTab == "Browse") {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Minimalist Bilgi Bandı
                    item {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF18181B),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "STOP SHELF WASTE • SAVE 60%+",
                                    color = Color(0xFFF97316),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Rescue shelf-stable pantry items before daily store inventory resets.",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 19.sp
                                )
                            }
                        }
                    }

                    // Kategori Butonları (Outlined Pill Formatı)
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(categories) { category ->
                                val active = selectedCategory == category
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (active) Color(0xFF18181B) else Color.White,
                                    border = BorderStroke(1.dp, if (active) Color(0xFF18181B) else Color(0xFFE4E4E7)),
                                    modifier = Modifier.clickable { selectedCategory = category }
                                ) {
                                    Text(
                                        text = category,
                                        fontSize = 12.sp,
                                        fontWeight = if (active) FontWeight.Bold else FontWeight.Medium,
                                        fontFamily = FontFamily.Monospace,
                                        color = if (active) Color.White else Color(0xFF52525B),
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (selectedCategory == "All") "TODAY'S AVAILABLE BOXES" else "$selectedCategory BOXES".uppercase(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF71717A),
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "${filteredBoxes.size} options",
                                fontSize = 11.sp,
                                color = Color(0xFFA1A1AA),
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    // Kutu Listesi (Sol Renkli Kenarlı Modern Kartlar)
                    items(filteredBoxes) { box ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE4E4E7)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedBox = box
                                    isCheckingOut = true
                                }
                        ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                // Sol Kenar Vurgu Şeridi
                                Box(
                                    modifier = Modifier
                                        .width(5.dp)
                                        .height(140.dp)
                                        .background(box.accentColor)
                                )

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(14.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = box.accentColor.copy(alpha = 0.12f)
                                        ) {
                                            Text(
                                                text = box.tag,
                                                color = box.accentColor,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }

                                        Text(
                                            text = "${box.stockLeft} LEFT",
                                            color = Color(0xFFDC2626),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = box.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF18181B),
                                        letterSpacing = (-0.3).sp
                                    )

                                    Spacer(modifier = Modifier.height(3.dp))

                                    Text(
                                        text = box.description,
                                        fontSize = 12.sp,
                                        color = Color(0xFF71717A),
                                        lineHeight = 16.sp
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    HorizontalDivider(color = Color(0xFFF4F4F5))

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = "${box.storeName} • ${box.distance}",
                                                fontSize = 11.sp,
                                                color = Color(0xFF52525B),
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = box.pickupHours,
                                                fontSize = 10.sp,
                                                color = Color(0xFFF97316),
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = FontFamily.Monospace
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.Bottom) {
                                            Text(
                                                text = "$${"%.2f".format(box.oldPrice)} ",
                                                fontSize = 12.sp,
                                                textDecoration = TextDecoration.LineThrough,
                                                color = Color(0xFFA1A1AA),
                                                fontFamily = FontFamily.Monospace
                                            )
                                            Text(
                                                text = "$${"%.2f".format(box.price)}",
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.Black,
                                                fontFamily = FontFamily.Monospace,
                                                color = Color(0xFF18181B)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Aktif Biletler / Siparişler Sekmesi (Boarding Pass Formatı)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "ACTIVE PICKUP PASSES",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF71717A),
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Flash this pass to cashier upon arrival",
                        fontSize = 12.sp,
                        color = Color(0xFFA1A1AA)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(ordersList) { order ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, Color(0xFFE4E4E7)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = order.orderId,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = Color(0xFF71717A),
                                            fontFamily = FontFamily.Monospace
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = Color(0xFFDCFCE7)
                                        ) {
                                            Text(
                                                text = "VERIFIED READY",
                                                color = Color(0xFF15803D),
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = order.boxName,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF18181B)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = "Location: ${order.storeName}", color = Color(0xFF71717A), fontSize = 12.sp)
                                    Text(text = "Slot: ${order.pickupWindow}", color = Color(0xFF71717A), fontSize = 12.sp, fontFamily = FontFamily.Monospace)

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Barkod / Pass Kutusu
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color(0xFF18181B),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(text = "CLAIM PASSCODE", fontSize = 9.sp, color = Color(0xFFA1A1AA), fontFamily = FontFamily.Monospace)
                                                Text(text = order.qrCodeNumber, fontWeight = FontWeight.Black, fontSize = 13.sp, color = Color.White, fontFamily = FontFamily.Monospace)
                                            }
                                            Surface(
                                                shape = RoundedCornerShape(4.dp),
                                                color = Color(0xFF27272A)
                                            ) {
                                                Text(
                                                    text = "SHOW CASHIER",
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFFF97316),
                                                    fontFamily = FontFamily.Monospace,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Alt Tab Bar
        if (!isCheckingOut) {
            Surface(
                color = Color.White,
                border = BorderStroke(1.dp, Color(0xFFE4E4E7)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { currentTab = "Browse" }
                            .padding(6.dp)
                    ) {
                        Text(
                            text = "Browse Feed",
                            fontSize = 12.sp,
                            fontWeight = if (currentTab == "Browse") FontWeight.Bold else FontWeight.Medium,
                            color = if (currentTab == "Browse") Color(0xFF18181B) else Color(0xFFA1A1AA)
                        )
                        if (currentTab == "Browse") {
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .size(width = 14.dp, height = 3.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color(0xFFF97316))
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { currentTab = "Orders" }
                            .padding(6.dp)
                    ) {
                        Text(
                            text = "Pickup Passes (${ordersList.size})",
                            fontSize = 12.sp,
                            fontWeight = if (currentTab == "Orders") FontWeight.Bold else FontWeight.Medium,
                            color = if (currentTab == "Orders") Color(0xFF18181B) else Color(0xFFA1A1AA)
                        )
                        if (currentTab == "Orders") {
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .size(width = 14.dp, height = 3.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color(0xFFF97316))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CampusBoxPreview() {
    CampusBoxOutletTheme {
        CampusBoxApp()
    }
}