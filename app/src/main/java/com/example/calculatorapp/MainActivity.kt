package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //concatenates the number entered
    //private var strNumber: String = ""

    private var strNumber = StringBuilder()

    //list the buttons and operators
    private lateinit var numberButtons: Array<Button>
    private lateinit var operatorButtons: List<Button>

    //define what operator to use, set the default optr to NONE
    private var operator: Operator = Operator.NONE

    //monitor if an operator btn is clicked
    private var isOperatorClicked: Boolean = false

    //initialize the TextView and Buttons
    private lateinit var tvDisplay: TextView

    private var operand1: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region val vs var
        //        val num1 = 5
//        val num2 = 12
//        val sum = num1 + num2
//        val tvDisplay: TextView = findViewById(R.id.tvDisplay)
//        tvDisplay.text = sum.toString()
        //endregion

        initializeComponents()


    }

    private fun initializeComponents() {
        //finds the TextView
        tvDisplay = findViewById(R.id.tvDisplay)

        //init the buttons and operators
        numberButtons = arrayOf(
            button0, button1, button2, button3, button4,
            button5, button6, button7, button8, button9
        )
        operatorButtons = listOf(buttonAdd, buttonSub, buttonMul, buttonDiv)
        for (i in numberButtons) {
            i.setOnClickListener { numberButtonClick(i) }
        }
        for (i in operatorButtons) {
            i.setOnClickListener { operatorButtonClick(i) }
        }
        buttonEquals.setOnClickListener { buttonEqualClick() }

        buttonClear.setOnClickListener { clearButtonClicked() }


        //region other method
        //        numberButtons = arrayOf(button7, button8, button9)
//        for (i in numberButtons.indices) {
//            numberButtons[i].setOnClickListener { button9Clicked() }
//        }
//        //changes TextView based on what button is clicked
//        button7.setOnClickListener { button7Clicked() }
//        button8.setOnClickListener { button8Clicked() }
//        button9.setOnClickListener { button9Clicked() }
        //endregion

    }

    private fun clearButtonClicked() {
        strNumber.clear()
        operand1 = 0
        isOperatorClicked = false
        tvDisplay.text = "0".toInt().toString()
    }

    private fun buttonEqualClick() {
        try {
            val operand2 = strNumber.toString().toInt()
            val result = when (operator) {
                Operator.ADD -> operand1 + operand2
                Operator.SUB -> operand1 - operand2
                Operator.MUL -> operand1 * operand2
                Operator.DIV -> operand1 / operand2
                else -> 0
            }

            strNumber.clear()
            strNumber.append(result.toString())
            updateDisplay()
            isOperatorClicked = true
        } catch (f: IllegalArgumentException) {
            strNumber.clear()
            tvDisplay.text = "0".toInt().toString()
        }
    }

    private fun updateDisplay() {
        try {
            val textValue = strNumber.toString().toInt()
            tvDisplay.text = textValue.toString()
        } catch (e: IllegalArgumentException) {
            strNumber.clear()
            tvDisplay.text = "ERROR"
        }

    }

    private fun operatorButtonClick(btn: Button) {
        if (btn.text == "+") operator = Operator.ADD
        else if (btn.text == "-") operator = Operator.SUB
        else if (btn.text == "*") operator = Operator.MUL
        else if (btn.text == "/") operator = Operator.DIV
        else operator = Operator.NONE
        isOperatorClicked = true
    }

    private fun numberButtonClick(btn: Button) {
        if (isOperatorClicked) {
            operand1 = strNumber.toString().toInt()
            strNumber.clear()
            isOperatorClicked = false
        }
        strNumber.append(btn.text)
        updateDisplay()
    }

    //region not needed fun
    //    private fun button8Clicked() {
//        strNumber.append(8)
//        tvDisplay.text = strNumber
//    }
//
//    private fun button7Clicked() {
//        strNumber.append(7)
//        tvDisplay.text = strNumber
//    }
    //endregion
}

enum class Operator { ADD, SUB, MUL, DIV, NONE }