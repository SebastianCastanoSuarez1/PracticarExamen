@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.practicarexamen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicarexamen.ui.theme.PracticarExamenTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticarExamenTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Interfaz()
                }
            }
        }
    }
}


@Composable
fun Interfaz() {
    var posicion by remember { mutableStateOf(1) }
    var numeroDeClicks by remember { mutableStateOf(0) }
    when (posicion) {
        1 -> {
            Pantalla(
                name = R.string.app_name,
                imagen = R.drawable.banco_foto,
                clicks = {  posicion++ })
        }
        2 -> {
            SegundaPantalla(
                name = R.string.pobreza,
                imagen = R.drawable.pobreza_foto,
                clicks = {
                    numeroDeClicks = (1..10).random()
                    posicion++
                })
        }

        3 -> {
            TerceraPantalla(name = R.string.bonoloto, imagen = R.drawable.bonoloto,
                clicks = {
                    numeroDeClicks--
                    if (numeroDeClicks == 0) {
                        posicion++
                    }
                })
        }

        4 -> {
            CuartaPantalla(
                name = R.string.loteria,
                imagen = R.drawable.loteria_foto,
                clicks = { posicion = 1 })
        }
    }
}



@Composable
fun Pantalla(name: Int, imagen: Int, clicks: () -> Unit, modifier: Modifier = Modifier) {
    val dineroInical by remember { mutableStateOf(5000.0) }
    var hipotecaInicialString by remember { mutableStateOf("") }
    var ivaString by remember { mutableStateOf("") }
    val hipotecaInicial = hipotecaInicialString.toDoubleOrNull() ?: 0.0
    val iva = ivaString.toDoubleOrNull() ?: 0.0
    val dineroActual = calcularDinero(dineroInical, hipotecaInicial, iva)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.2f)
                .background(Color(88, 95, 153)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = name),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.4F),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = imagen),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clickable { clicks() })
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.4F),
        ) {
            EditarCampoDinero(
                nombre = R.string.hipoteca,
                valor = hipotecaInicialString,
                onValueChange = { hipotecaInicialString = it },
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth()
                    .weight(1f)
            )
            EditarCampoDinero(
                nombre = R.string.Iva,
                valor = ivaString,
                onValueChange = { ivaString = it },
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.4F)
        ) {
            Text(
                text = stringResource(R.string.Salario)
            )
            Text(
                text = dineroActual, modifier = Modifier.padding(start = 15.dp)
            )
        }
    }
}

fun calcularDinero(dineroInicial: Double, hipoteca: Double, porcentaje: Double): String {
    val dinero = dineroInicial - (hipoteca * (porcentaje / 100))
    return NumberFormat.getCurrencyInstance().format(dinero)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarCampoDinero(
    nombre: Int, valor: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    TextField(
        value = valor,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(stringResource(nombre)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
        ),
        modifier = modifier
    )
}
@Composable
fun RicoAPobre(){

}

@Preview(showBackground = true)
@Composable
fun Practicar() {
    PracticarExamenTheme {
        Interfaz()
    }
}

@Composable
fun SegundaPantalla(name: Int, imagen: Int, clicks: () -> Unit, modifier: Modifier = Modifier) {
    var cambioImagen by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.2f)
                .background(Color(88, 95, 153)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = name),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.4F),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = if (cambioImagen) R.drawable.rendirse_foto else imagen),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clickable { clicks() }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.4F),
        ) {
            Text(text = stringResource(id = R.string.Rendirse))
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.4F)
        ) {
            Switch(checked = cambioImagen,
                onCheckedChange = { cambioImagen = it })

        }
    }
}


@Composable
fun TerceraPantalla(name: Int, imagen: Int, clicks: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.2f)
                .background(Color(88, 95, 153)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = name),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.4F),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = imagen),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clickable { clicks() })
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.4F),
        ) {}
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.4F)
        ) {

        }
    }
}

@Composable
fun CuartaPantalla(name: Int, imagen: Int, clicks: () -> Unit, modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
    val cambioDeImagenes = when (result) {
        1 -> R.drawable.dinero_1
        2 -> R.drawable.dinero_2
        3 -> R.drawable.dinero_3
        4 -> R.drawable.loteria_foto
        else -> {
            result = 1
            imagen
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.2f)
                .background(Color(88, 95, 153)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = name),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.4F),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = cambioDeImagenes),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clickable { clicks() })
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.4F)
        ) {
            Button(
                onClick = {
                    if (result == 1) {
                        result = 4
                    } else result--
                },
            ) {
                Text(text = stringResource(R.string.DerrochadorBack), fontSize = 24.sp)
            }
            Button(
                onClick = { result++ },
            ) {
                Text(text = stringResource(R.string.DerrochadorNext), fontSize = 24.sp)
            }
        }
    }
}