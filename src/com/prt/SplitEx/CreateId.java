package com.prt.SplitEx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateId extends Activity implements OnClickListener {
    Button btnCreate;
    Context conActive;
    EditText etId, etPass, etConfirm;
    ProgressDialog pdProgress;
    private DBHelper mDBHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mDBHelper = DBHelper.getInstance(this);
        initComps();
    }

    private void initComps() {
        conActive = this;
        etId = (EditText) findViewById(R.id.etId);
        etPass = (EditText) findViewById(R.id.etPass);
        etConfirm = (EditText) findViewById(R.id.etConfirm);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        pdProgress = new ProgressDialog(this);
        pdProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdProgress.setIndeterminate(true);
        pdProgress.setCancelable(true);
        addListener();
    }

    private void addListener() {
        btnCreate.setOnClickListener(this);
        callToast("User Registration Interface Is Ready To Register", 2000);
    }

    private void callToast(String sMessage, int iDuration) {
        Toast.makeText(conActive, sMessage, iDuration).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnCreate:

                String sId = etId.getText().toString();
                String sPass = etPass.getText().toString();
                String sConfirm = etConfirm.getText().toString();

                if (sId.contains(" ") || sPass.contains(" ")) {
                    callToast("ID & Password Can't Contain Spaces", 2000);
                } else if (sId.length() < 6 || sPass.length() < 6) {
                    callToast("ID & Password Should Be Of Minimum 6 Characters", 2000);
                } else if (sConfirm.length() == 0) {
                    callToast("Please Confirm Your Password", 2000);
                    etConfirm.requestFocus();
                } else if (!sPass.equals(sConfirm)) {
                    callToast("Passwords Mismatch", 2000);
                    etPass.setText("");
                    etConfirm.setText("");
                    etPass.requestFocus();
                } else {
                    pdProgress.setMessage("Registering ID, Please Wait....");
                    pdProgress.show();
                    try {
                        if (!mDBHelper.doesLogExist(sId)) {
                            mDBHelper.addLog(sId, sPass);
                            callToast("ID Created", 2000);
                            finish();
                        } else {
                            callToast("The ID already exists. Please Try Again", 2000);
                        }
                    } catch (Exception ex) {
                        pdProgress.cancel();
                        callToast("Error In Registration", 2000);
                    } finally {
                      pdProgress.cancel();
                    }
                }
                break;
        }
    }
}
