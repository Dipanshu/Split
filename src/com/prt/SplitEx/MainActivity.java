package com.prt.SplitEx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    private Button mBtnSignIn;
    private Button mBtnSignUp;
    private Button mBtnExit;
    private EditText mTxtID;
    private EditText mTxtPass;

    private DBHelper mDBHelper;

    @Override
    public void onCreate(Bundle savedInstant) {
        super.onCreate(savedInstant);
        setContentView(R.layout.main);
        mDBHelper = DBHelper.getInstance(this);
        initComps();
        addListener();
    }

    private void initComps() {
        mBtnSignIn = (Button) findViewById(R.id.btnSignIn);
        mBtnSignUp = (Button) findViewById(R.id.btnSignUp);
        mBtnExit = (Button) findViewById(R.id.btnExit);
        mTxtID = (EditText) findViewById(R.id.etId);
        mTxtPass = (EditText) findViewById(R.id.etPass);
    }

    private void addListener() {
        mBtnSignIn.setOnClickListener(this);
        mBtnSignUp.setOnClickListener(this);
        mBtnExit.setOnClickListener(this);
        callToast("SplitEx Is Ready", 1000);
    }

    private void callToast(String sMessage, int iDuration) {
        Toast.makeText(this, sMessage, iDuration).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                finish();
                break;
            case R.id.btnSignUp:
                Intent inSignup = new Intent(this, CreateId.class);
                startActivity(inSignup);
                break;
            case R.id.btnSignIn:
                String sId = mTxtID.getText().toString();
                String sPass = mTxtPass.getText().toString();
                if (sId.length() == 0) {
                    callToast("Please Type User Id", 2000);
                    mTxtID.requestFocus();
                    break;
                } else if (sPass.length() == 0) {
                    callToast("Please Type Password", 2000);
                    mTxtPass.requestFocus();
                    break;
                }
                try {
                    if (mDBHelper.doesLogExist(sId)) {
                        mTxtID.setText("");
                        mTxtPass.setText("");
                        Intent inSignIn = new Intent(this, SignIn.class);
                        startActivity(inSignIn);
                    } else {
                        callToast("Invalid ID or Password", 2000);
                        mTxtID.requestFocus();
                        break;
                    }
                } catch (Exception e) {
                    callToast("An error occured while signing you in", 2000);
                    Log.e("DBError", e.getMessage(), e);
                }
                break;
        }
    }
}