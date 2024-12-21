package com.example.tic_tac

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tic_tac.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class Turn {
        NOUGHT,
        CROSS
    }
    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var crossesScore = 0
    private var noughtsScore = 0

    private var boardList= mutableListOf<Button>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        initBoard()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)

    }

    private fun fullBoard(): Boolean {
        for (button in boardList)
        {
            if (button.text =="")
                return false
        }
        return true
    }

    fun boardTapped(view: View) {
        if (view !is Button)
            return
        addToBoard(view)
        
        if (checkForVictory(NOUGHT)){
            noughtsScore++
            result("Noughts win wohooo!")
        }
        if (checkForVictory(CROSS)){
            crossesScore++
            result("Crosses win wohooo!")
        }

        if (fullBoard()){
            result("Draw")
        }
    }

    private fun checkForVictory(s: String): Boolean {
        //Horizontal victory
        if (match(binding.a1, s) && match(binding.a2, s) &&match(binding.a3, s) )
            return true
        if (match(binding.b1, s) && match(binding.b2, s) &&match(binding.b3, s) )
            return true
        if (match(binding.c1, s) && match(binding.c2, s) &&match(binding.c3, s) )
            return true
        //Vertical victory
        if (match(binding.a1, s) && match(binding.b1, s) &&match(binding.c1, s) )
            return true
        if (match(binding.a2, s) && match(binding.b2, s) &&match(binding.c2, s) )
            return true
        if (match(binding.a3, s) && match(binding.b3, s) &&match(binding.c3, s) )
            return true
        //Diagonal victory
        if (match(binding.a1, s) && match(binding.b2, s) &&match(binding.c3, s) )
            return true
        if (match(binding.a3, s) && match(binding.b2, s) &&match(binding.c1, s) )
            return true
        return false
    }

    private fun match(button: Button, symbol: String) = button.text == symbol

    private fun result(title: String) {
        val message = "\nNoghts: $noughtsScore\n\nCrosses: $crossesScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("reset")
            {_,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }
    private fun resetBoard(){
        for (button in boardList){
            button.text = ""
        }
        if (firstTurn == Turn.NOUGHT)
            firstTurn = Turn.CROSS
        else if (firstTurn == Turn.CROSS)
            firstTurn = Turn.NOUGHT
        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun addToBoard(button: Button) {
        if (button.text != "")
            return
        if (currentTurn == Turn.NOUGHT){
            button.text = NOUGHT
            currentTurn = Turn.CROSS
        }
        else if (currentTurn == Turn.CROSS){
            button.text = CROSS
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""
        if (currentTurn == Turn.CROSS)
            turnText = "Turn $CROSS"
        else if (currentTurn == Turn.NOUGHT)
            turnText = "Turn $NOUGHT"
        binding.turnTV.text= turnText
    }


    companion object {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }
}