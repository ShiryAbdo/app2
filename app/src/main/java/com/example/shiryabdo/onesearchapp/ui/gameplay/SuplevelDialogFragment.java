package com.example.shiryabdo.onesearchapp.ui.gameplay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.shiryabdo.onesearchapp.R;

/**
 * Created by d on 8/23/2017.
 */

public class SuplevelDialogFragment extends DialogFragment {

//    private PauseDialogFragment.PauseDialogListener mListener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        WordSearchActivity activity = (WordSearchActivity) getActivity();
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.sup_level_dialoge, null);
        Button levelone = (Button) layout.findViewById(R.id.levelone);
        Button   leveltwo = (Button) layout.findViewById(R.id.leveltwo);
        Button levelthree = (Button) layout.findViewById(R.id. levelthree);
        levelone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();

                Intent i = new Intent(getActivity(), WordSearchActivity.class);
                startActivity(i);
            }
        });
        leveltwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
             }
        });
        levelone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
             }
        });
//        TextView scorePauseTextView = (TextView) layout.findViewById(R.id.tvScorePause);
//        scorePauseTextView.setText(Integer.toString(activity.getScore()));
//        TextView timerPauseTextView = (TextView) layout.findViewById(R.id.tvTimePause);
//        timerPauseTextView.setText(Long.toString(activity.getTimeRemaining() / 1000 + 1));
        builder.setView(layout);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
         super.onCancel(dialog);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
         } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PauseDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
     }


}
