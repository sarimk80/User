package com.example.user;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.user.databinding.ActivityMainBinding;
import com.example.user.model.Usermodel;
import com.example.user.remote.APIService;
import com.example.user.remote.RetroClass;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    Disposable disposable;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);



        LoadJson();
    }

    void LoadJson() {
        APIService apiService = RetroClass.getAPIService();

        Observable<Usermodel> observable = apiService.getUserModel()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Usermodel>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onNext(Usermodel usermodel) {


                activityMainBinding.setUser(usermodel);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Retro", "" + e);

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
