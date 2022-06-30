package com.example.kexampleviewstatelivedatamvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    // (1) создаем mViewModel
    lateinit var mViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    // (2) наполнение mViewModel
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // важно понимать, что mViewModel создана не во View (не нами), а во ViewModelProvider
        // им самим, у которого, например:
        //  - нет доступа к контексту, поэтому и у VM доступа нет (решается через : AndroidViewModel(application)
        //  - в него невозможно простым способом передать данные (решается через class Factory)
    }



    // тут в примере какой-то странный метод onStart - нах он нужен не знаю, оставил
    // хотя и без него все работает, можно всю шнягу в onCreate оставить
    override fun onStart() {
        super.onStart()

//                <<<<<<<<<<<<<<<<[[[  2. Подписка на liveData
//    (-(-(-(-(-( <<<<<<<<<<<<<<<<[[[  4. И сразу при Подписке описание реакции подписчика
        mViewModel.liveData.observe(this, Observer {
            textView.text = it
        })
    }
}