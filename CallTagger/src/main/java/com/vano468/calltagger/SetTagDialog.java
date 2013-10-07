package com.vano468.calltagger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

public class SetTagDialog extends DialogFragment implements DialogInterface.OnClickListener {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText inputTag = new EditText(getActivity());
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
            .setTitle("Set #tag")
            //.setMessage(R.string.messageText)
            .setView(inputTag)
            .setPositiveButton(R.string.positive, this)
            .setNegativeButton(R.string.negative, this);
        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
