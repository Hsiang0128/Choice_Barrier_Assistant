package com.example.homework3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import android.media.MediaPlayer

import android.widget.ImageView

class Page3Activity : AppCompatActivity() {
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page3)
        title = "Midterm-Project"
        val player1Img = intent.getIntExtra("PLAYER1_IMG",0)
        val player1Name = intent.getStringExtra("PLAYER1_NAME")
        val player2Img =intent.getIntExtra("PLAYER2_IMG",0)
        val player2Name =intent.getStringExtra("PLAYER2_NAME")

        // 請求錄音權限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
        }
        findViewById<Button>(R.id.BUTTON_NEXT).setOnClickListener {
            if(findViewById<Button>(R.id.BUTTON_STOP_RECORDING).isEnabled == true || findViewById<Button>(R.id.BUTTON_STOP_PLAYBACK).isEnabled == true){
                Toast.makeText(this, "Please stop working first", Toast.LENGTH_SHORT).show()
            }
            else if(findViewById<Button>(R.id.BUTTON_PLAYBACK).isEnabled == false){
                Toast.makeText(this, "Please start recording first", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, Page4Activity::class.java)
                intent.putExtra("PLAYER1_IMG",player1Img)
                intent.putExtra("PLAYER1_NAME",player1Name)
                intent.putExtra("PLAYER2_IMG",player2Img)
                intent.putExtra("PLAYER2_NAME",player2Name)
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.BUTTON_RECORDING).setOnClickListener {
            startRecording()
            findViewById<Button>(R.id.BUTTON_RECORDING).isEnabled = false
            findViewById<Button>(R.id.BUTTON_STOP_RECORDING).isEnabled = true
            findViewById<Button>(R.id.BUTTON_PLAYBACK).isEnabled = false
            findViewById<Button>(R.id.BUTTON_STOP_PLAYBACK).isEnabled = false
            findViewById<TextView>(R.id.LABEL_REQUEST).text = "Recording..."
        }

        findViewById<Button>(R.id.BUTTON_STOP_RECORDING).setOnClickListener {
            stopRecording()
            findViewById<Button>(R.id.BUTTON_RECORDING).isEnabled = true
            findViewById<Button>(R.id.BUTTON_STOP_RECORDING).isEnabled = false
            findViewById<Button>(R.id.BUTTON_PLAYBACK).isEnabled = true
            findViewById<Button>(R.id.BUTTON_STOP_PLAYBACK).isEnabled = false
            findViewById<TextView>(R.id.LABEL_REQUEST).text = "Recoeded to ${externalCacheDir?.absolutePath}/audiorecord.3gp"
        }

        findViewById<Button>(R.id.BUTTON_PLAYBACK).setOnClickListener {
            findViewById<Button>(R.id.BUTTON_RECORDING).isEnabled = false
            findViewById<Button>(R.id.BUTTON_STOP_RECORDING).isEnabled = false
            findViewById<Button>(R.id.BUTTON_PLAYBACK).isEnabled = false
            findViewById<Button>(R.id.BUTTON_STOP_PLAYBACK).isEnabled = true
            findViewById<TextView>(R.id.LABEL_REQUEST).text = "Playback..."
            playAudio()
        }

        findViewById<Button>(R.id.BUTTON_STOP_PLAYBACK).setOnClickListener {
            findViewById<Button>(R.id.BUTTON_RECORDING).isEnabled = true
            findViewById<Button>(R.id.BUTTON_STOP_RECORDING).isEnabled = false
            findViewById<Button>(R.id.BUTTON_PLAYBACK).isEnabled = true
            findViewById<Button>(R.id.BUTTON_STOP_PLAYBACK).isEnabled = false
            findViewById<TextView>(R.id.LABEL_REQUEST).text = "Playback Stop"
            stopAudio()
        }

    }
    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
    private fun startRecording() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile("${externalCacheDir?.absolutePath}/audiorecord.3gp")
            try {
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }
    private fun playAudio() {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource("${externalCacheDir?.absolutePath}/audiorecord.3gp")
                prepare()
                start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun stopAudio() {
        mediaPlayer?.apply {
            stop()
            release()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}