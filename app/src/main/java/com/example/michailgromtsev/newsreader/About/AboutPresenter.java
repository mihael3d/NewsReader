package com.example.michailgromtsev.newsreader.About;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.michailgromtsev.newsreader.R;

@InjectViewState
public class  AboutPresenter extends MvpPresenter<AboutView> {

    public void onClickSendEmail(String massege) {

        if ( massege.isEmpty() ) {
            getViewState().showErrorWhenEmailMessageIncorrect();
        } else {
            getViewState().sendEmailMessage(massege);
        }
    }
}
