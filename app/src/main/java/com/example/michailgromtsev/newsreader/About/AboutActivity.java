package com.example.michailgromtsev.newsreader.About;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.TaskStackBuilder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.mvp.MvpAppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class AboutActivity extends MvpAppCompatActivity implements AboutView {

    @InjectPresenter
    AboutPresenter aboutPresenter;

    private static final String MAIL_TO_URI = "mihael3d@mail.ru";
    private RelativeLayout mainLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        startInit();
        setEmailSending();
        //setLinkViews();
    }

    private void startInit() {
        mainLayout = findViewById(R.id.about_main_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.about_title);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setEmailSending() {
        EditText messageEditText = findViewById(R.id.message_edit);
        Button sendMessageButton = findViewById(R.id.message_send);
        sendMessageButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            aboutPresenter.onClickSendEmail(message);
        });
    }

    @Override
    public void sendEmailMessage(String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","m.gromtsev@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.about_send_email_title);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        if (emailIntent.resolveActivity(getPackageManager()) == null) {
            Snackbar.make(
                    mainLayout,
                    R.string.about_send_email_no_clients_error,
                    Snackbar.LENGTH_SHORT
            ).show();
            return;
        }
        startActivity(emailIntent);
    }

    @Override
    public void showErrorWhenEmailMessageIncorrect() {
        Snackbar.make(
                mainLayout,
                R.string.about_send_email_empty_error,
                Snackbar.LENGTH_SHORT
        ).show();
    }

//    private void showErrorWhenEmailMessageIncorrect() {
//
//    }
//
//    private void sendEmailMessage(String message) {
//
//    }
}
