package com.example.homework3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework3.ui.theme.Homework3Theme
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page1)
        title = "Midterm-Project"
        findViewById<Button>(R.id.BUTTON_START).setOnClickListener {
            // 创建意图对象，指定当前活动和目标活动
            val intent = Intent(this, Page2Activity::class.java)
            // 启动目标活动
            startActivity(intent)
        }
    }
}
