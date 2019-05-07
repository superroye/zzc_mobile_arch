package com.supylc.mobilearch.uilibs.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class ProgressDialogFragment extends AppCompatDialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            Bundle args = getArguments();
            if (args != null) {
                String message = args.getString("message");
                progressDialog.setMessage(message);
            }
            progressDialog.setCanceledOnTouchOutside(false);
            return progressDialog;
        }
    }