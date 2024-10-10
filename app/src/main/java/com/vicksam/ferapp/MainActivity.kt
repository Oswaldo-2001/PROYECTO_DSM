package com.vicksam.ferapp

import android.graphics.Bitmap
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

    private val viewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(FerViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al botón de resultados y al panel celeste
        val buttonResultados = findViewById<Button>(R.id.buttonResultados)
        val panelCeleste = findViewById<RelativeLayout>(R.id.panelCeleste)
        val buttonRetroceder = findViewById<Button>(R.id.buttonRetroceder)

        // Mostrar los porcentajes de emociones detectadas al presionar el botón "RESULTADOS"
        buttonResultados.setOnClickListener {
            showEmotionPercentages()
            panelCeleste.visibility = View.VISIBLE
        }

        // Volver al panel principal y reiniciar el contador de emociones cuando se presiona "Retroceder"
        buttonRetroceder.setOnClickListener {
            panelCeleste.visibility = View.GONE
            viewModel.resetEmotionCount()
        }

        // Llamar a la función para mostrar la fecha
        showCurrentDate()

        val lensFacing =
            savedInstanceState?.getSerializable(KEY_LENS_FACING) as Facing? ?: Facing.BACK
        setupCamera(lensFacing)

        // Load model
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
        viewfinder.destroy()
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

        toggleCameraButton.setOnClickListener {
            viewfinder.toggleFacing()
        }
    }

    private fun showCurrentDate() {
        // Obtener el TextView para la fecha
        val textViewDate = findViewById<TextView>(R.id.textViewDate)

        // Obtener la fecha actual
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())

        // Establecer la fecha en el TextView
        textViewDate.text = currentDate
    }

    private fun showEmotionPercentages() {
        val percentages = viewModel.getEmotionPercentages()
        val resultsText = percentages.entries.joinToString("\n") { (emotion, percentage) ->
            "$emotion: ${"%.2f".format(percentage)}%"
        }

        // Mostrar los resultados en el TextView en lugar de un Toast
        val textViewResults = findViewById<TextView>(R.id.textViewResults)
        textViewResults.text = resultsText
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


