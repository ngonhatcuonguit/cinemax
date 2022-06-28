package com.cuongngo.cinemax

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor
    private const val REFERENCES_NAME = "AppPreferences"
    private const val KEY_SHOWN_ON_BOARD = "key_shown_on_board"

    init{
        preferences = App.getInstance().getSharedPreferences(REFERENCES_NAME, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    /**
     * set shown onboard
     */
    fun setShowOnBoard(isShown: Boolean){
        editor.also {
            it.putBoolean(KEY_SHOWN_ON_BOARD, isShown)
            it.commit()
        }
    }

    /**
     * is user already view onboard screen or not
     */
    fun isShownOnBoard():Boolean{
        return preferences.getBoolean(KEY_SHOWN_ON_BOARD,false)
    }

}