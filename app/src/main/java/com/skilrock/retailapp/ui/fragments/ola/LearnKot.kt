package com.skilrock.retailapp.ui.fragments.ola

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class LearnKot:AppCompatActivity {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    constructor (emp_id : Int, emp_name: String)  {
        var id: Int = emp_id
        var name: String = emp_name
        for (i in 10 downTo 1 step 3) {
            print("$i ")
        }
        var planets = arrayOf("Earth", "Mars", "Venus", "Jupiter", "Saturn")

        for (i in planets.indices) {
            println(planets[i])
        }
        doNothing("Amit", 5)
        doNothing("Amit")
        doNothing("Amit")
    }

    var id: String = ""
    var name: String = ""

    fun doNothing(str: String) {
    }
    fun doNothing(str: String, valr : Int = 1){

        when(str) {
            "Sun" -> println("Sun is a Star")
            "Moon" -> println("Moon is a Satellite")
            "Earth" -> println("Earth is a planet")
        }

        var monthOfYear = valr
        var month = when(monthOfYear){
           in 1..3->"January"
            2->"Febuary"
            3->"March"
            4->"April"
            5->"May"
            6->"June"
            7->"July"
            8->"August"
            9->"September"
            10->"October"
            11->"November"
            12->"December"
            else -> {
                println("Not a month of year")
            }
        }

}}