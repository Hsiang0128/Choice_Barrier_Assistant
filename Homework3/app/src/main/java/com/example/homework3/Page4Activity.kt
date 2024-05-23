package com.example.homework3

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
import android.media.MediaPlayer

class Page4Activity : AppCompatActivity() {
    private lateinit var music: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page4)
        title = "Midterm-Project"
        music = MediaPlayer.create(this, R.raw.music)
        val player1Img = intent.getIntExtra("PLAYER1_IMG",0)
        val player1Name = intent.getStringExtra("PLAYER1_NAME")
        val player2Img =intent.getIntExtra("PLAYER2_IMG",0)
        val player2Name =intent.getStringExtra("PLAYER2_NAME")
        findViewById<ImageView>(R.id.IMG_PLAYER1).setImageResource(player1Img)
        findViewById<ImageView>(R.id.IMG_PLAYER2).setImageResource(player2Img)
        findViewById<TextView>(R.id.LABEL_PLAYER_NAME_1).text = player1Name
        findViewById<TextView>(R.id.LABEL_PLAYER_NAME_2).text = player2Name
        findViewById<Button>(R.id.BUTTON_BACK).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        findViewById<Button>(R.id.BUTTON_START).setOnClickListener {
            findViewById<Button>(R.id.BUTTON_START).isEnabled = false
            findViewById<ImageView>(R.id.IMG_ANIMATION).setImageResource(R.drawable.animation)
            findViewById<TextView>(R.id.LABEL_REQUEST).text = "Player Contesting..."
            findViewById<SeekBar>(R.id.BAR_PLAYER1).progress = 0
            findViewById<SeekBar>(R.id.BAR_PLAYER2).progress = 0
            startPlayer1()
            startPlayer2()
            animationStart()
            music.start()
        }
    }

    private fun startPlayer1() {
        val delay = IntArray(81) { it }
        val step = IntArray(3) { it }
        val player1Bar = findViewById<SeekBar>(R.id.BAR_PLAYER1)
        val player2Bar = findViewById<SeekBar>(R.id.BAR_PLAYER2)
        Thread {
            while (player1Bar.progress < 100 && player2Bar.progress < 100) {
                try {
                    Thread.sleep(20)
                    Thread.sleep(delay.random().toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                player1Bar.progress += step.random()
            }
            if(player1Bar.progress > player2Bar.progress) {
                runOnUiThread {
                    val imageView = findViewById<ImageView>(R.id.IMG_ANIMATION)
                    imageView.clearAnimation()
                    findViewById<TextView>(R.id.LABEL_REQUEST).text = "This Winner is ${findViewById<TextView>(R.id.LABEL_PLAYER_NAME_1).text}"
                    Toast.makeText(this, "This Winner is ${findViewById<TextView>(R.id.LABEL_PLAYER_NAME_1).text}", Toast.LENGTH_SHORT).show()
                    music.stop()
                    findViewById<Button>(R.id.BUTTON_START).isEnabled = true
                }
            }
        }.start()
    }
    private fun startPlayer2() {
        val player1Bar = findViewById<SeekBar>(R.id.BAR_PLAYER1)
        val player2Bar = findViewById<SeekBar>(R.id.BAR_PLAYER2)
        val delay = IntArray(81) { it }
        val step = IntArray(3) { it }
        Thread {
            while (player1Bar.progress < 100 && player2Bar.progress < 100) {
                try {
                    Thread.sleep(20)
                    Thread.sleep(delay.random().toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                player2Bar.progress += step.random()
            }
            if(player2Bar.progress > player1Bar.progress) {
                runOnUiThread {
                    val imageView = findViewById<ImageView>(R.id.IMG_ANIMATION)
                    imageView.clearAnimation()
                    findViewById<TextView>(R.id.LABEL_REQUEST).text = "This Winner is ${findViewById<TextView>(R.id.LABEL_PLAYER_NAME_2).text}"
                    Toast.makeText(this, "This Winner is ${findViewById<TextView>(R.id.LABEL_PLAYER_NAME_2).text}", Toast.LENGTH_SHORT).show()
                    music.stop()
                    findViewById<Button>(R.id.BUTTON_START).isEnabled = true
                }
            }
        }.start()

    }
    private fun animationStart(){
        val rotateAnimation = RotateAnimation(
            0f, 360f, // 开始和结束的角度
            Animation.RELATIVE_TO_SELF, 0.4f, // 旋转中心点的X坐标（相对于自身）
            Animation.RELATIVE_TO_SELF, 0.6f // 旋转中心点的Y坐标（相对于自身）
        )

        rotateAnimation.duration = 750 // 动画持续时间（毫秒）
        rotateAnimation.repeatCount = Animation.INFINITE // 重复次数（INFINITE表示无限循环）

// 应用旋转动画到视图
        val imageView = findViewById<ImageView>(R.id.IMG_ANIMATION)
        imageView.startAnimation(rotateAnimation)
    }
}

