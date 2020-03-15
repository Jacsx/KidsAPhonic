package com.example.kidsaphonic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mp: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions,0)
        }

        playButton.setOnClickListener{
            playButton.setImageResource(R.drawable.playbuttonhighlight)
            val intent = Intent(this, aPage::class.java)
            startActivity(intent)
        }

        mp =  MediaPlayer.create(this,  R.raw.sweettreats)
        mp?.isLooping = true
        mp?.setVolume(100f, 100f)
        mp?.start()

    }

    override fun onPause() {
        super.onPause()
        mp?.pause()
    }

    override fun onResume() {
        super.onResume()
        mp?.start()
    }



}
