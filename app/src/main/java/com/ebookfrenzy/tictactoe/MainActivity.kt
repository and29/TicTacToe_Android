package com.ebookfrenzy.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.ebookfrenzy.touchevent.R
import com.ebookfrenzy.touchevent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class turnDef{
        x,
        o,
        empty
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var turn:turnDef
    private lateinit var elementList :ArrayList<ImageView>
    private lateinit var status : Array<Array<turnDef>>
    private var gameStop : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        turn = turnDef.x
        elementList = arrayListOf(findViewById(R.id.position11))
        elementList.add(findViewById(R.id.position12))
        elementList.add(findViewById(R.id.position13))
        elementList.add(findViewById(R.id.position21))
        elementList.add(findViewById(R.id.position22))
        elementList.add(findViewById(R.id.position23))
        elementList.add(findViewById(R.id.position31))
        elementList.add(findViewById(R.id.position32))
        elementList.add(findViewById(R.id.position33))
        status = Array(3){
            Array(3){
                turnDef.empty
                turnDef.empty
                turnDef.empty
            }
            Array(3){
                turnDef.empty
                turnDef.empty
                turnDef.empty
            }
            Array(3){
                turnDef.empty
                turnDef.empty
                turnDef.empty
            }
        }

        ContextCompat.getDrawable(this, R.drawable.x_icon)
            ?.let { DrawableCompat.setTint(it, Color.rgb(242,242,23)) };
        ContextCompat.getDrawable(this, R.drawable.o_icon)
            ?.let { DrawableCompat.setTint(it, Color.rgb(23,242,242)) };

    }

    fun onClickCell(view: View){
        if(!gameStop) {
            var index = elementList.indexOf(view)
            if (view.background == null) {
                if (turn == turnDef.x) {
                    turn = turnDef.o

                    view.background = ContextCompat.getDrawable(this, R.drawable.x_icon)
                    ContextCompat.getDrawable(this, R.drawable.x_icon)
                        ?.let { DrawableCompat.setTint(it, Color.RED) };
                    status[index / 3][index % 3] = turnDef.x
                } else {
                    turn = turnDef.x
                    view.background = ContextCompat.getDrawable(this, R.drawable.o_icon)
                    status[index / 3][index % 3] = turnDef.o
                }
            }

            var win = checkWin()
            var text :TextView
            text = findViewById(R.id.winnerText)
            var symbol : ImageView
            symbol = findViewById(R.id.winnerSymbol)
            if(win!=turnDef.empty){
                gameStop=true
                text.visibility=View.VISIBLE
                text.text = "WINS"

                if(win==turnDef.x)
                    symbol.background = ContextCompat.getDrawable(this, R.drawable.x_icon)
                else
                    symbol.background = ContextCompat.getDrawable(this, R.drawable.o_icon)
            }else{
                if(checkGameEnd()){
                    text.visibility=View.VISIBLE
                    text.text = "TIE"
                    symbol.background = ContextCompat.getDrawable(this, R.drawable.tie_game)
                    gameStop=true
                }
            }
            Log.d("Game status", win.toString())

        }
    }

    fun checkGameEnd():Boolean{
        var count = 0
        for(i in 0..2) {


            for(j in 0..2){
                if(status[i][j]!=turnDef.empty){
                    count++
                }
            }
        }
        return count==9
    }

    fun checkWin():turnDef{
        //check rows
        for(i in 0..2) {
            var countX = 0
            var countO = 0
            for(j in 0..2){
                if(status[i][j]==turnDef.x){
                    countX++
                }else if(status[i][j]==turnDef.o){
                    countO++
                }
            }
            if(countX==3) return turnDef.x
            if(countO==3) return turnDef.o
        }
        //check columns
        for(i in 0..2) {
            var countX = 0
            var countO = 0
            for(j in 0..2){
                if(status[j][i]==turnDef.x){
                    countX++
                }else if(status[j][i]==turnDef.o){
                    countO++
                }
            }
            if(countX==3) return turnDef.x
            if(countO==3) return turnDef.o
        }
        //check diagonals
        var countX = 0
        var countO = 0
        for(j in 0..2){
            if(status[j][j]==turnDef.x){
                countX++
            }else if(status[j][j]==turnDef.o){
                countO++
            }
        }
        if(countX==3) return turnDef.x
        if(countO==3) return turnDef.o
        countX = 0
        countO = 0
        for(j in 0..2){
            if(status[2-j][j]==turnDef.x){
                countX++
            }else if(status[2-j][j]==turnDef.o){
                countO++
            }
        }
        if(countX==3) return turnDef.x
        if(countO==3) return turnDef.o

        return turnDef.empty
    }

    fun onClickRestartGame(view: View){
        for(i in 0..2) {
            for(j in 0..2){
                status[i][j]= turnDef.empty
            }
        }
        gameStop=false
        var text :TextView
        text = findViewById(R.id.winnerText)
        text.visibility=View.INVISIBLE
        var symbol : ImageView
        symbol = findViewById(R.id.winnerSymbol)
        symbol.background=null
        for(v :ImageView in elementList){
            v.background = null
        }
    }
}