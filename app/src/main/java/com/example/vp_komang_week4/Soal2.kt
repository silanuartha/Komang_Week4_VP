package com.example.vp_komang_week4

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
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

val DarkBg = Color(0xFF0D0F1C)
val CardBg = Color(0xFF1A1D2D)
val YellowAccent = Color(0xFFFFD700)
val MythicColor = Color(0xFFE91E63)
val LegendaryColor = Color(0xFFFF9800)
val HpColor = Color(0xFFEF5350)
val MpColor = Color(0xFF42A5F5)

data class Hero(
    val id: Int,
    val name: String,
    val rarity: String,
    val rarityColor: Color,
    val heroClass: String,
    val classColor: Color,
    val cp: Int,
    val level: Int,
    val hp: Int,
    val maxHp: Int,
    val mp: Int,
    val maxMp: Int,
    val avatarRes: Int,
    val inSquad: Boolean = false,
    var isExpanded: Boolean = false
)

@Composable
fun PartyRosterScreen() {
    val heroList = remember {
        mutableStateListOf(
            Hero(1, "Alistair", "MYTHIC", MythicColor, "High Templar", YellowAccent, 5600, 50, 1650, 1800, 650, 800, R.drawable.paladin),
            Hero(2, "Theresa", "MYTHIC", MythicColor, "Divine Oracle", Color(0xFF4CAF50), 5480, 49, 1100, 1200, 2100, 2400, R.drawable.cleric),
            Hero(3, "Vespera", "MYTHIC", MythicColor, "Shadowblade", Color(0xFF9C27B0), 5350, 48, 890, 950, 410, 600, R.drawable.rogue),
            Hero(4, "Elaria", "MYTHIC", MythicColor, "Archmage", Color(0xFF42A5F5), 5120, 42, 450, 600, 1850, 2000, R.drawable.archmage, inSquad = true),
            Hero(5, "Seraphina", "LEGENDARY", LegendaryColor, "Luminary Priest", Color(0xFF4CAF50), 4950, 46, 920, 1053, 1600, 1800, R.drawable.berserker)
        )
    }

    var selectedFilter by remember { mutableStateOf("All (14)") }
    val filters = listOf("All (14)", "Berserker (2)", "Archmage (3)", "Paladin (3)", "Rogue (3)", "Cleric (3)", "Sort: Power")

    Column(modifier = Modifier.fillMaxSize().background(DarkBg).padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Party Roster", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Guild Vanguard • 14 Heroes Available", fontSize = 12.sp, color = Color.Gray)
            }
            Surface(shape = RoundedCornerShape(16.dp), color = Color.Transparent, border = BorderStroke(1.dp, YellowAccent)) {
                Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Add, contentDescription = null, tint = YellowAccent, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Expedition", color = YellowAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(shape = RoundedCornerShape(12.dp), color = CardBg, border = BorderStroke(1.dp, Color(0xFF333344))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color.Green))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("EXPEDITION SQUAD (3/4)", color = Color.LightGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Text("⚔ 14750 CP", color = YellowAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        repeat(3) {
                            Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(Color.White)) {
                                Image(painter = painterResource(id = R.drawable.cleric), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                            }
                        }
                        Box(modifier = Modifier.size(40.dp).border(1.dp, Color.Gray, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Filled.Add, contentDescription = null, tint = Color.Gray)
                        }
                    }
                    Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = YellowAccent), shape = RoundedCornerShape(8.dp), modifier = Modifier.height(36.dp)) {
                        Text("Deploy", color = DarkBg, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search by hero name, class, or rarity...", color = Color.Gray, fontSize = 12.sp) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = Color.Gray) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = CardBg, unfocusedBorderColor = Color.Transparent, focusedBorderColor = YellowAccent)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            filters.forEach { filter ->
                val isSelected = filter == selectedFilter
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) Color.Transparent else CardBg,
                    border = BorderStroke(1.dp, if (isSelected) YellowAccent else Color.Transparent),
                    modifier = Modifier.clickable { selectedFilter = filter }
                ) {
                    Text(text = filter, color = if (isSelected) YellowAccent else Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(bottom = 16.dp)) {
            items(heroList) { hero ->
                HeroCard(hero = hero, onClick = {
                    val index = heroList.indexOf(hero)
                    heroList[index] = hero.copy(isExpanded = !hero.isExpanded)
                })
            }
        }
    }
}

@Composable
fun HeroCard(hero: Hero, onClick: () -> Unit) {
    val borderColor = if (hero.inSquad) YellowAccent else Color.Transparent

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = hero.rarityColor.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp)) {
                        Text(hero.rarity, color = hero.rarityColor, fontSize = 8.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(hero.heroClass, color = hero.classColor, fontSize = 10.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⚔ ${hero.cp} CP", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    if (hero.inSquad) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(color = YellowAccent, shape = RoundedCornerShape(4.dp)) {
                            Text("IN SQUAD", color = DarkBg, fontSize = 8.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box {
                    Image(painter = painterResource(id = hero.avatarRes), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp)).background(Color.White))
                    Surface(
                        color = Color.Black.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.align(Alignment.BottomCenter).offset(y = 6.dp)
                    ) {
                        Text("Lvl ${hero.level}", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(hero.name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Icon(imageVector = if (hero.isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = null, tint = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("HP", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.width(20.dp))
                        Box(modifier = Modifier.weight(1f).height(4.dp).clip(CircleShape).background(Color.DarkGray)) {
                            Box(modifier = Modifier.fillMaxWidth(hero.hp.toFloat() / hero.maxHp).height(4.dp).background(HpColor))
                        }
                        Text("${hero.hp}/${hero.maxHp}", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.padding(start = 8.dp))
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("MP", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.width(20.dp))
                        Box(modifier = Modifier.weight(1f).height(4.dp).clip(CircleShape).background(Color.DarkGray)) {
                            Box(modifier = Modifier.fillMaxWidth(hero.mp.toFloat() / hero.maxMp).height(4.dp).background(MpColor))
                        }
                        Text("${hero.mp}/${hero.maxMp}", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            AnimatedVisibility(visible = hero.isExpanded) {
                Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().background(Color(0xFF22253A), RoundedCornerShape(8.dp)).padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        StatItem("ATK", "380")
                        StatItem("DEF", "540")
                        StatItem("CRIT", "22%")
                        StatItem("SPD", "105")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Add, contentDescription = null, tint = YellowAccent, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Wrath of the Heavens", color = YellowAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Text("Calls down a celestial hammer, stunning all enemies for 3 seconds and taunting survivors.", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.padding(top = 4.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.weight(1f).height(36.dp),
                            border = BorderStroke(1.dp, Color.Green),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Green)
                        ) {
                            Text("Heal (+250 HP)", fontSize = 12.sp)
                        }
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.weight(1f).height(36.dp),
                            border = BorderStroke(1.dp, YellowAccent),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = YellowAccent)
                        ) {
                            Text("Add to Squad", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Color.Gray, fontSize = 10.sp)
        Text(value, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun PartyRosterPreview() {
    MaterialTheme {
        PartyRosterScreen()
    }
}