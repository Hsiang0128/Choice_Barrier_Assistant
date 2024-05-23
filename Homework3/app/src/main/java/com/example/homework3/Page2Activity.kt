package com.example.homework3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homework3.R

class Page2Activity : AppCompatActivity() {
    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2)
        title = "Midterm-Project"
        val gridView = findViewById<GridView>(R.id.GRID_VIEW)
        val item = ArrayList<Item>() //store fruits info.
        val array = resources.obtainTypedArray(R.array.image_list) //from R class to read images
        var player1Img: Int = 0
        var player1Name: String = ""
        var player2Img: Int = 0
        var player2Name: String = ""
        for (i in 0 until array.length()) {
            val photo = array.getResourceId(i, 0)
            val name = resources.getResourceEntryName(array.getResourceId(i, 0))
            item.add(Item(photo, name)) //add fruit info.
        }

        gridView.numColumns = 3
        gridView.adapter = MyAdapter(this, item, R.layout.adapter_vertical)

        var playerFlag: Boolean = false
        gridView.setOnItemClickListener { parent, view, position, id ->
            playerFlag =!playerFlag
            if(playerFlag){
                player1Img = item[position].photo
                player1Name = item[position].name
                findViewById<ImageView>(R.id.IMG_PLAYER1).setImageResource(player1Img)
                findViewById<TextView>(R.id.LABEL_PLAYER_NAME_1).text = player1Name
            }
            else{
                player2Img = item[position].photo
                player2Name = item[position].name
                findViewById<ImageView>(R.id.IMG_PLAYER2).setImageResource(player2Img)
                findViewById<TextView>(R.id.LABEL_PLAYER_NAME_2).text = player2Name
            }
        }

        findViewById<Button>(R.id.BUTTON_NEXT).setOnClickListener {
            if(findViewById<TextView>(R.id.LABEL_PLAYER_NAME_2).text == "" || findViewById<TextView>(R.id.LABEL_PLAYER_NAME_1).text == ""){
                Toast.makeText(this, "Need to pick two players", Toast.LENGTH_SHORT).show()
            }
            else if(findViewById<TextView>(R.id.LABEL_PLAYER_NAME_2).text ==  findViewById<TextView>(R.id.LABEL_PLAYER_NAME_1).text ){
                Toast.makeText(this, "Please pick two different players", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, Page3Activity::class.java)
                intent.putExtra("PLAYER1_IMG",player1Img)
                intent.putExtra("PLAYER1_NAME",player1Name)
                intent.putExtra("PLAYER2_IMG",player2Img)
                intent.putExtra("PLAYER2_NAME",player2Name)
                startActivity(intent)
            }
        }
    }
}
data class Item(
    val photo: Int,
    val name: String
)