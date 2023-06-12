package com.ispc.gestorstock.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ispc.gestorstock.R;
import com.ispc.gestorstock.activities.HomeActivity;

public class SignOutDialog extends DialogFragment {
    public static SignOutDialog newInstance(int title) {
        SignOutDialog frag = new SignOutDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.icon_close_sesion)
                .setTitle(R.string.dialog_signout_title)
                .setPositiveButton(R.string.dialog_signout_positive_button,
                        (dialogInterface, i) -> ((HomeActivity)getActivity()).doPositiveClick())
                .setNegativeButton(R.string.dialog_signout_negative_button,
                        (dialogInterface, i) -> ((HomeActivity)getActivity()).doNegativeClick())
                .create();
    }
}
