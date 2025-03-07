package com.example.holatoast

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { UIPrincipal() }
    }
}

@Composable
fun UIPrincipal() {
    var nombre by remember { mutableStateOf("") }
    val contexto = LocalContext.current
    fun btnSaludar_click() {
        Toast.makeText(contexto, "Hola $nombre!", Toast.LENGTH_SHORT).show()
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Text("¿Cómo te llamas?")
        TextField(
            value = nombre,
            onValueChange = { nombre = it }
        )
        Button(onClick = { btnSaludar_click() }) {
            Text("Saludar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Previsualizacion() {
    UIPrincipal()
}
