package com.example.seniordesign_app

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.seniordesign_app.ui.theme.SeniorDesign_AppTheme
import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeniorDesign_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    SensorDataScreen()
                }
            }
        }
    }
}

@Composable
fun SensorDataScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var accelerometerData by remember { mutableStateOf("Accelerometer Data: -") }
    var gyroscopeData by remember { mutableStateOf("Gyroscope Data: -") }
    var leftTextColor by remember { mutableStateOf(Color.Black) }
    var rightTextColor by remember { mutableStateOf(Color.Black) }
    var frontTextColor by remember { mutableStateOf(Color.Black) }
    var backTextColor by remember { mutableStateOf(Color.Black) }
    var topTextColor by remember { mutableStateOf(Color.Black) }
    var bottomTextColor by remember { mutableStateOf(Color.Black) }

    LaunchedEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        val accelerometerListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                accelerometerData =
                    "Accelerometer Data: x=${event.values[0]}, y=${event.values[1]}, z=${event.values[2]}"
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                //Dunno just ignore :)
            }
        }

        val gyroscopeListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                gyroscopeData =
                    "Gyroscope Data: x=${event.values[0]}, y=${event.values[1]}, z=${event.values[2]}"
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                //Dunno just ignore :)
            }
        }

        sensorManager.registerListener(
            accelerometerListener,
            accelerometerSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            gyroscopeListener,
            gyroscopeSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                sensorManager.unregisterListener(accelerometerListener)
                sensorManager.unregisterListener(gyroscopeListener)
            }
        })
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SensorButton(
                text = "Left",
                textColor = leftTextColor,
                onClick = { leftTextColor = if (leftTextColor == Color.Black) Color.Red else Color.Black }
            )
            SensorButton(
                text = "Right",
                textColor = rightTextColor,
                onClick = { rightTextColor = if (rightTextColor == Color.Black) Color.Red else Color.Black }
            )
            SensorButton(
                text = "Front",
                textColor = frontTextColor,
                onClick = { frontTextColor = if (frontTextColor == Color.Black) Color.Red else Color.Black }
            )
            SensorButton(
                text = "Back",
                textColor = backTextColor,
                onClick = { backTextColor = if (backTextColor == Color.Black) Color.Red else Color.Black }
            )
            SensorButton(
                text = "Top",
                textColor = topTextColor,
                onClick = { topTextColor = if (topTextColor == Color.Black) Color.Red else Color.Black }
            )
            SensorButton(
                text = "Bottom",
                textColor = bottomTextColor,
                onClick = { bottomTextColor = if (bottomTextColor == Color.Black) Color.Red else Color.Black }
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = accelerometerData,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = gyroscopeData,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SensorButton(text: String, textColor: Color, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = onClick) {
            Text(text = text)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = textColor
        )
    }
}
