package com.vicksam.ferapp.fer

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import husaynhakeem.io.facedetector.FaceBounds

/**
 * Once it receives face pictures, it runs the model and returns prediction emotion labels
 */
class FerViewModel : ViewModel() {

    private val emotionLabels = MutableLiveData<Map<Int, String>>()
    fun emotionLabels(): LiveData<Map<Int, String>> = emotionLabels

    // Variables para el conteo de emociones
    private var emotionCount = mutableMapOf<String, Int>()
    private var totalEmotionsDetected = 0
    private var processing: Boolean = false

    fun onFacesDetected(faceBounds: List<FaceBounds>, faceBitmaps: List<Bitmap>) {
        if (faceBitmaps.isEmpty()) return

        synchronized(FerViewModel::class.java) {
            if (!processing) {
                processing = true
                Handler(Looper.getMainLooper()).post {
                    val detectedEmotions = faceBounds.mapNotNull { it.id }
                        .zip(faceBitmaps)
                        .toMap()
                        .run { getEmotionsMap(this) }

                    // Actualizamos el contador de emociones
                    updateEmotionCount(detectedEmotions)
                    emotionLabels.value = detectedEmotions
                    processing = false
                }
            }
        }
    }

    // Función para actualizar el conteo de emociones
    private fun updateEmotionCount(emotionMap: Map<Int, String>) {
        for (emotion in emotionMap.values) {
            totalEmotionsDetected++
            emotionCount[emotion] = (emotionCount[emotion] ?: 0) + 1
        }
    }

    // Función que devuelve el porcentaje de cada emoción
    fun getEmotionPercentages(): Map<String, Float> {
        return emotionCount.mapValues { (emotion, count) ->
            (count.toFloat() / totalEmotionsDetected) * 100
        }
    }

    // Reiniciar el conteo de emociones
    fun resetEmotionCount() {
        emotionCount.clear()
        totalEmotionsDetected = 0
    }

    /**
     * Given map of (faceId, faceBitmap), runs prediction on the model and
     * returns a map of (faceId, emotionLabel)
     */
    private fun getEmotionsMap(faceImages: Map<Int, Bitmap>): Map<Int, String> {
        val emotionLabels = faceImages.map { FerModel.classify(it.value) }
        return faceImages.keys.zip(emotionLabels).toMap()
    }
}

