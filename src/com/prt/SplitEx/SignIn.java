package com.prt.SplitEx;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SignIn extends Activity implements View.OnClickListener{
    private Button mBtnNewExpense;
    private Button mBtnNewFriend;
    private Button mBtnNewGroup;
    private Button mBtnSignOut;
    @Override
    public void onCreate(Bundle savedInstant){
        super.onCreate(savedInstant);
        setContentView(R.layout.signin);
        initComps();
        callToast("Successfully Log In", 1000);
    }
    private void initComps(){
        this.mBtnNewExpense = (Button)findViewById(R.id.btnNewExpense);
        this.mBtnNewFriend = (Button)findViewById(R.id.btnNewFriend);
        this.mBtnNewGroup = (Button)findViewById(R.id.btnNewGroup);
        this.mBtnSignOut = (Button)findViewById(R.id.btnSignOut);
        addListener();
    }
    private void addListener(){
        this.mBtnNewExpense.setOnClickListener(this);
        this.mBtnNewFriend.setOnClickListener(this);
        this.mBtnNewGroup.setOnClickListener(this);
        this.mBtnSignOut.setOnClickListener(this);
    }
    private void callToast(String sMessage, int iDuration){
        Toast.makeText(this, sMessage, iDuration).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNewExpense:
                callToast("New Expense Added", 1000);
                break;
            case R.id.btnNewFriend:
                callToast("New Friend Added", 1000);
                break;
            case R.id.btnNewGroup:
                callToast("New Group Added", 1000);
                break;
            case R.id.btnSignOut:
                finish();
        }
    }
}
