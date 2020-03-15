package com.example.kidsaphonic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_a_page.*
import kotlinx.android.synthetic.main.activity_a_page.homeButton
import kotlinx.android.synthetic.main.activity_a_page.nextButton
import kotlinx.android.synthetic.main.activity_b_page.*
import java.io.File
import java.io.IOException

class bPage : AppCompatActivity() {
    var output: String? = null
    var mediaRecorder: MediaRecorder? = null
    var state: Boolean = false
    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_page)

        restartMediaRecorder()

        bRecordButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val permissions = arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                ActivityCompat.requestPermissions(this, permissions, 0)
            } else {
                startRecording()
            }
        }

        bStopRecord.setOnClickListener {
            stopRecording()
        }

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, cPage::class.java)
            startActivity(intent)
        }
        previousButton.setOnClickListener {
            val intent = Intent(this, aPage::class.java)
            startActivity(intent)
        }

        bButton.setOnClickListener {
            var bearsound = MediaPlayer.create(this, R.raw.bear)
            bearsound.start()
        }

        bPlayRecord.setOnClickListener{0
            playRecording()
        }
    }

    fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopRecording() {
        if (state) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            restartMediaRecorder()
            state = false
        } else {
            Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show()
        }
    }

    fun playRecording() {
        val filePath = Environment.getExternalStorageDirectory().absolutePath + "/bRecording.mp3"
        val fileCheck = File(filePath)
        if (fileCheck.exists()) {
            mediaPlayer = MediaPlayer.create(this, Uri.parse(filePath))
            mediaPlayer?.setVolume(100f, 100f)
            mediaPlayer?.setOnCompletionListener {
                mediaPlayer?.stop()
                mediaPlayer?.release()
            }
            mediaPlayer?.start()
        }
    }

        fun restartMediaRecorder() {
            mediaRecorder = MediaRecorder()
            output = Environment.getExternalStorageDirectory().absolutePath + "/bRecording.mp3"

            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder?.setOutputFile(output)
        }
}
