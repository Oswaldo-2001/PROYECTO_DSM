package com.vicksam.ferapp

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.size.SizeSelectors
import com.vicksam.ferapp.fer.FerModel
import com.vicksam.ferapp.fer.FerViewModel
import husaynhakeem.io.facedetector.FaceBounds
import husaynhakeem.io.facedetector.FaceDetector
import husaynhakeem.io.facedetector.Frame
import husaynhakeem.io.facedetector.LensFacing
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.widget.TextView
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import com.vicksam.ferapp.data.sqlLiteHelper

class MainActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var dbHelper: sqlLiteHelper


    private val viewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(FerViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Inicializar el handler
        handler = Handler(Looper.getMainLooper())

        // Inicializar la base de datos
        dbHelper = sqlLiteHelper(this)

        // Definir el Runnable para mostrar el Toast cada segundo
        runnable = object : Runnable {
            override fun run() {
                // Obtener la fecha y las emociones
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                val currentDateTime = sdf.format(Date())
                val percentages = viewModel.getEmotionPercentages()
                val predominantEmotion = percentages.maxByOrNull { it.value }
                if (predominantEmotion != null) {
                    val emotion = predominantEmotion.key
                    val percentage = predominantEmotion.value
                    val precision = percentage
                    val error = percentages.filter { it.key != emotion }.values.sum()
                    dbHelper.addData(
                        paciente = "1",  // Identificador del paciente, puedes cambiarlo por una variable si es necesario
                        fecha = currentDateTime,  // Fecha actual
                        emocion = emotion,  // Emoción predominante
                        precision = precision,  // Precisión (porcentaje de la emoción predominante)
                        error = error  // Error (suma de los porcentajes de las otras emociones)
                    )
                }
                handler.postDelayed(this, 5000)  // Ejecutar cada 5 segundos
            }
        }

        // Iniciar la ejecución repetida
        handler.post(runnable)
        // Referencia al botón de resultados y al panel celeste
        val buttonResultados = findViewById<Button>(R.id.buttonResultados)
        val panelCeleste = findViewById<RelativeLayout>(R.id.panelCeleste)
        val buttonRetroceder = findViewById<Button>(R.id.buttonRetroceder)

        // Mostrar los porcentajes de emociones detectadas al presionar el botón "RESULTADOS"
        buttonResultados.setOnClickListener {
            showStoredEmotions()
            panelCeleste.visibility = View.VISIBLE
        }

        // Volver al panel principal y reiniciar el contador de emociones cuando se presiona "Retroceder"
        buttonRetroceder.setOnClickListener {
            panelCeleste.visibility = View.GONE
            viewModel.resetEmotionCount()
        }

        val lensFacing = Facing.FRONT
        setupCamera(lensFacing)

        // Cargar modelo
        FerModel.load(this)

        setupObservers()
    }

    override fun onStart() {
        super.onStart()
        viewfinder.open()
    }

    override fun onStop() {
        super.onStop()
        viewfinder.close()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(KEY_LENS_FACING, viewfinder.facing)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        viewfinder.destroy()
    }

    private fun showStoredEmotions() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM registroEmocional", null)
        val storedResults = StringBuilder()
        if (cursor.moveToFirst()) {
            do {
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("Fecha"))
                val emociones = cursor.getString(cursor.getColumnIndexOrThrow("Emociones"))
                val precision = cursor.getFloat(cursor.getColumnIndexOrThrow("Precision"))
                val error = cursor.getFloat(cursor.getColumnIndexOrThrow("Error"))

                // Añadir los resultados al StringBuilder, sin mostrar el paciente
                storedResults.append("Fecha: $fecha\n")
                storedResults.append("Emociones: $emociones\n")
                storedResults.append("Precisión: ${"%.2f".format(precision)}%\n")
                storedResults.append("Error: ${"%.2f".format(error)}%\n\n")
            } while (cursor.moveToNext())
        }
        cursor.close()
        val textViewResults = findViewById<TextView>(R.id.textViewResults)
        textViewResults.text = storedResults.toString()
    }


    private fun setupObservers() {
        viewModel.emotionLabels().observe(this, {
            it?.let { faceBoundsOverlay.updateEmotionLabels(it) }
        })
    }

    private fun setupCamera(lensFacing: Facing) {
        val faceDetector = FaceDetector(faceBoundsOverlay).also { it.setup() }

        viewfinder.facing = lensFacing
        // Lower the frame resolution for better computation performance when working with face images
        viewfinder.setPreviewStreamSize(SizeSelectors.maxWidth(MAX_PREVIEW_WIDTH))
        viewfinder.audio = Audio.OFF

        viewfinder.addFrameProcessor {
            faceDetector.process(
                Frame(
                    data = it.getData(),
                    rotation = it.rotation,
                    size = Size(it.size.width, it.size.height),
                    format = it.format,
                    lensFacing = if (viewfinder.facing == Facing.BACK) LensFacing.BACK else LensFacing.FRONT
                )
            )
        }
    }

    private fun FaceDetector.setup() = run {
        setOnFaceDetectionListener(object : FaceDetector.OnFaceDetectionResultListener {
            override fun onSuccess(faceBounds: List<FaceBounds>, faceBitmaps: List<Bitmap>) {
                viewModel.onFacesDetected(faceBounds, faceBitmaps)
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val KEY_LENS_FACING = "key-lens-facing"
        private const val MAX_PREVIEW_WIDTH = 480
    }

}


