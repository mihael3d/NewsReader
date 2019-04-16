package com.example.michailgromtsev.newsreader.About;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AboutView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void sendEmailMessage(String message);

    @StateStrategyType(SkipStrategy.class)
    void showErrorWhenEmailMessageIncorrect();


}
