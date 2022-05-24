package com.cuongngo.cinemax.ext
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

inline fun <reified T, reified LD: LiveData<T>> Fragment.observeLiveDataChanged(liveData: LD,  crossinline onChanged: (T) -> Unit){
    liveData.observe(viewLifecycleOwner, onChanged)
}

inline fun <reified T, reified LD: LiveData<T>> AppCompatActivity.observeLiveDataChanged(liveData: LD, observer: Observer<in T>){
    liveData.observe(this, observer)
}

inline fun <reified T, reified LD: LiveData<T>> Application.observeLiveDataChangedForever(liveData: LD, observer: Observer<in T>){
    liveData.observeForever(observer)
}

fun String.toRequestBody(): RequestBody {
    return this.toRequestBody("multipart/from-data".toMediaTypeOrNull())
}

fun String.toMultipartBody(key: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(key, this)
}

fun View.hideKeyboard() {
    val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyBoard() {
    val imm =
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun WTF(msg: String?, tag: String = "WTF"): Int{
    return Log.e(tag, "$msg")
}