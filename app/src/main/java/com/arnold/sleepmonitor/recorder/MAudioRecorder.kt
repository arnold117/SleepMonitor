package com.arnold.sleepmonitor.recorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.util.Log
import androidx.core.app.ActivityCompat
import com.arnold.sleepmonitor.MApplication
import com.arnold.sleepmonitor.MainActivity
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.math.log10

class MAudioRecorder {
    val TAG = "AudioRecorder"

    val SAMPLE_RATE = 8000
    val BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT)

    var audioRecord: AudioRecord? = null
    var isRecording = false

    private val mLock = ReentrantLock()
    private val condition = mLock.newCondition()

    var thread: Thread? = null

    fun printNoiseLevel() {
        if (isRecording) {
            Log.i(TAG, "Recording is already running.")
            return
        }

        if (ActivityCompat.checkSelfPermission(
                MApplication.context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(TAG, "No permission to record audio.")
            ActivityCompat.requestPermissions(
                MainActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1
            )
            return
        }
        Log.i(TAG, "Permission to record audio have already granted.")

        audioRecord = AudioRecord(
            android.media.MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_DEFAULT,
            AudioFormat.ENCODING_PCM_16BIT,
            BUFFER_SIZE
        )
        if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
            Log.i(TAG, "AudioRecord can't initialize!")
            return
        }
        isRecording = true
        Log.i(TAG, "Start recording...")

        thread = Thread(Runnable{
            audioRecord?.startRecording()
            val buffer = ShortArray(BUFFER_SIZE)
            while (isRecording) {
                val bufferReadResult = audioRecord?.read(buffer, 0, BUFFER_SIZE)
                if (bufferReadResult != null) {
                    var sum = 0.0
                    for (i in buffer.indices) {
                        sum += buffer[i] * buffer[i]
                    }
                    if (bufferReadResult > 0) {
                        var amplitude = sum / bufferReadResult
                        Log.i(TAG, "Current amplitude: $amplitude")
                        var volume = 10 * log10(amplitude)
                        Log.i(TAG, "Current volume: $volume")
                    }
                }

                mLock.withLock {
                    condition.await(100, java.util.concurrent.TimeUnit.MILLISECONDS)
                }
            }
            audioRecord?.stop()
            audioRecord?.release()
            audioRecord = null
        })
        thread?.start()
    }

    fun stop() {
        isRecording = false
        Log.i(TAG, "Stop recording...")

        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null

        thread?.interrupt()
    }
}