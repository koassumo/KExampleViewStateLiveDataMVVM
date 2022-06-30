package com.example.kexampleviewstatelivedatamvvm

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_main.*

// Вариант 1. Без возможности передачи контекста (owner, lifecycle)
// class MainViewModel : ViewModel() {
// Вариант 2. С возможностью передачи контекста
class MainViewModel (application: Application): AndroidViewModel(application) {

    //   <<<<<<<<<<<<<<<<<<<[[   1. выставляем вышку
    val liveData = MutableLiveData <String> ()


    init {
        // при создании класса запускается таймер
        // т.е в данном примере данные не продюсируются из View (как обычно при нажатии на кнопку)
        // а производятся прямо в VM (для простоты примера)
        startTimer()
    }

    private fun startTimer () {

        // счетчик обратного отсчета. Вот она - самая бизнес-логика!
        object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                // чето посчитали - получили данные, а во вью их невозможно напрямую отправить
                // т.к. из VM нет ссылки на View (это ж сука несвязная архитектура)
                // и нельзя просто указать "textView.text = " (VM о textView из View ниче не знает)
                // поэтому:
    //    (-(-(-(-(-( <<<<<<<<<<<<<<<<<<<[[   3. пихаем данные в вышку liveData - она разберется
                liveData.value = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                // Toast требует контекста, и это стало возможным, потому что MainViewModel
                // была унаследована именно от AndroidViewModel (с передачей контекста)
                // Хотя большой вопрос - для чего вывод на экран из VM ?
                Toast.makeText(getApplication(), "Boo", Toast.LENGTH_LONG).show()
            }

        }.start() // не забывай запустить счетчик

    }

}