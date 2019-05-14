package com.example.hekai.xunw.activityTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hekai.xunw.R;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ObserableActivity extends AppCompatActivity {
    private Disposable disposable;

    @Override
    protected void onStart() {
        super.onStart();
        Observable<String> buttonClick = createButtomClickObservable();
        Observable<String> textChangeStream = createTextChangeObservable();
        Observable<String> searchTextObservable = Observable.merge(textChangeStream,buttonClick);
        /*disposable = searchTextObservable
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //showProgressBar();
                    }
                })
                .observeOn(Schedulers.io())
                *//*.map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(String s) throws Exception {
                        return "";//newtWork.query(s);
                    }
                })*//*
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        //hideProgressBar();
                        //showResult();
                    }
                });*/
    }

    private Observable<String> createTextChangeObservable() {
        return null;
    }

    private Observable<String> createButtomClickObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obserable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
