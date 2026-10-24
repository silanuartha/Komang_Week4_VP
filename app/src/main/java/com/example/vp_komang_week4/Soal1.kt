package com.example.vp_komang_week4

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Car(
    val id: Int,
    val name: String,
    val fuelType: String,
    val fuelTypeColor: Color,
    val transmission: String,
    val year: String,
    val mileage: String,
    val imageResId: Int,
    val price: String,
    val location: String,
    val description: String,
    var isFavorite: Boolean = false
)

@Composable
fun MainApp() {
    val carList = remember {
        mutableStateListOf(
            Car(1, "Tiggo 9 CSH", "PHEV", Color(0xFF8A2BE2), "Auto", "2024", "5,000 km", R.drawable.tiggo, "Rp 550.000.000", "Jakarta", "Premium PHEV SUV dengan fitur keselamatan canggih.", true),
            Car(2, "Honda CRV", "PETROL", Color(0xFFFFA500), "Auto", "2013", "15,200 km", R.drawable.crv, "Rp 210.000.000", "Bandung", "SUV tangguh dengan ruang kabin luas.", false),
            Car(3, "Toyota Yaris Cross", "HYBRID", Color(0xFF008080), "Auto", "2024", "30,500 km", R.drawable.yariscross, "Rp 398.000.000", "Surabaya", "Toyota's stylish crossover pairs a 1.5L hybrid system with a bold design.", true),
            Car(4, "Daihatsu Xenia", "PETROL", Color(0xFFFFA500), "Manual", "2022", "28,000 km", R.drawable.xenia, "Rp 185.000.000", "Malang", "MPV 7-penumpang andalan keluarga.", false),
            Car(5, "Toyota Yaris", "PETROL", Color(0xFFFFA500), "Auto", "2021", "52,400 km", R.drawable.yaris, "Rp 245.000.000", "Semarang", "The Toyota Yaris G AT is a sporty compact hatchback.", true),
            Car(6, "Honda HRV", "PETROL", Color(0xFFFFA500), "Auto", "2022", "18,900 km", R.drawable.hrv, "Rp 340.000.000", "Yogyakarta", "Crossover stylish dengan panoramic sunroof.", false),
            Car(7, "Daihatsu Ayla", "PETROL", Color(0xFFFFA500), "Manual", "2020", "45,000 km", R.drawable.ayla, "Rp 115.000.000", "Solo", "City car kompak yang sangat gesit.", false),
            Car(8, "Jaecoo J5", "ELECTRIC", Color(0xFF4CAF50), "Auto", "2025", "1,200 km", R.drawable.j5, "Rp 420.000.000", "Surabaya", "Mobil listrik dengan jangkauan baterai panjang.", true),
            Car(9, "MG 4 EV", "ELECTRIC", Color(0xFF4CAF50), "Auto", "2023", "9,800 km", R.drawable.mg4, "Rp 395.000.000", "Jakarta", "Hatchback listrik bertenaga buas RWD.", false),
            Car(10, "Toyota Innova Reborn", "DIESEL", Color(0xFFD2691E), "Auto", "2021", "68,000 km", R.drawable.reborn, "Rp 365.000.000", "Bali", "MPV diesel legendaris dengan torsi melimpah.", true)
        )
    }

    var selectedCarId by remember { mutableStateOf<Int?>(null) }

    val onToggleFavorite = { carId: Int ->
        val index = carList.indexOfFirst { it.id == carId }
        if (index != -1) {
            carList[index] = carList[index].copy(isFavorite = !carList[index].isFavorite)
        }
    }

    if (selectedCarId == null) {
        CarListScreen(
            carList = carList,
            onCarClick = { car -> selectedCarId = car.id },
            onFavoriteToggle = { car -> onToggleFavorite(car.id) }
        )
    } else {
        val selectedCar = carList.find { it.id == selectedCarId }
        if (selectedCar != null) {
            CarDetailScreen(
                car = selectedCar,
                onBackClick = { selectedCarId = null },
                onFavoriteToggle = { onToggleFavorite(selectedCar.id) }
            )
        }
    }
}

@Composable
fun CarListScreen(carList: List<Car>, onCarClick: (Car) -> Unit, onFavoriteToggle: (Car) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = "On Sale", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = "Find your perfect ride", fontSize = 14.sp, color = Color.Gray)
            }
            val savedCount = carList.count { it.isFavorite }
            Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFFFFF0F0), modifier = Modifier.padding(4.dp)) {
                Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Saved", tint = Color(0xFFFF5252), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "$savedCount saved", color = Color(0xFFFF5252), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(contentPadding = PaddingValues(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(carList) { car ->
                CarCardItem(
                    car = car,
                    onCarClick = { onCarClick(car) },
                    onFavoriteToggle = { onFavoriteToggle(car) }
                )
            }
        }
    }
}

@Composable
fun CarCardItem(car: Car, onCarClick: () -> Unit, onFavoriteToggle: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onCarClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(width = 100.dp, height = 75.dp).clip(RoundedCornerShape(12.dp)).background(Color.LightGray)) {
                Image(painter = painterResource(id = car.imageResId), contentDescription = car.name, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                Surface(shape = RoundedCornerShape(topEnd = 8.dp, bottomStart = 12.dp), color = Color.White.copy(alpha = 0.9f), modifier = Modifier.align(Alignment.BottomStart)) {
                    Text(text = car.fuelType, color = car.fuelTypeColor, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                    Text(text = car.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                    Icon(
                        imageVector = if (car.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (car.isFavorite) Color(0xFFFF5252) else Color.LightGray,
                        modifier = Modifier.size(20.dp).clickable { onFavoriteToggle() }
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.border(1.dp, Color.LightGray, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text(text = car.transmission, fontSize = 10.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(4.dp).clip(RoundedCornerShape(2.dp)).background(Color.Red))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = car.year, fontSize = 12.sp, color = Color.DarkGray, fontWeight = FontWeight.Medium)
                    }
                    Text(text = car.mileage, fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun CarDetailScreen(car: Car, onBackClick: () -> Unit, onFavoriteToggle: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F7F7))) {
        Box(modifier = Modifier.fillMaxWidth().height(250.dp).background(Color.LightGray)) {
            Image(painter = painterResource(id = car.imageResId), contentDescription = car.name, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())

            IconButton(onClick = onBackClick, modifier = Modifier.padding(16.dp).align(Alignment.TopStart).clip(CircleShape).background(Color.White).size(40.dp)) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }

            Surface(shape = RoundedCornerShape(topEnd = 8.dp), color = Color.White, modifier = Modifier.align(Alignment.BottomStart)) {
                Text(text = car.fuelType, color = car.fuelTypeColor, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
            }
        }

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = car.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Icon(
                    imageVector = if (car.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (car.isFavorite) Color(0xFFFF5252) else Color.LightGray,
                    modifier = Modifier.size(24.dp).clickable { onFavoriteToggle() }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color.Red))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = car.location, fontSize = 14.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFFFF0F0)) {
                Text(text = car.price, color = Color(0xFFFF5252), fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "SPECIFICATIONS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SpecCard("YEAR", car.year, modifier = Modifier.weight(1f))
                    SpecCard("MILEAGE", car.mileage, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SpecCard("TRANSMISSION", car.transmission, modifier = Modifier.weight(1f))
                    SpecCard("FUEL TYPE", car.fuelType, modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "ABOUT THIS CAR", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = car.description, fontSize = 14.sp, color = Color.DarkGray, lineHeight = 20.sp)

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    color = Color.White,
                    modifier = Modifier.size(50.dp).clickable { onFavoriteToggle() }
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(imageVector = if (car.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder, contentDescription = "Favorite", tint = if (car.isFavorite) Color(0xFFFF5252) else Color.LightGray)
                    }
                }
                Button(onClick = { }, modifier = Modifier.weight(1f).height(50.dp), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043))) {
                    Text(text = "Contact Seller", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun SpecCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Text(text = title, fontSize = 10.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainAppPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF7F7F7)) {
            MainApp()
        }
    }
}