package com.example.a1621638.android_assignment_1;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class NotesActivityFragment extends Fragment {

    private LinearLayout optionsLinearLayout;
    private Switch showOptionsSwitch;
    private TextView reminderTextView;

    public NotesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        optionsLinearLayout = root.findViewById(R.id.options_LinearLayout);

        showOptionsSwitch = root.findViewById(R.id.showOptions_Switch);
        showOptionsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // optionsToolbar.setVisibility(View.VISIBLE);
                    optionsLinearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    // optionsToolbar.setVisibility(View.GONE);
                    optionsLinearLayout.setVisibility(View.GONE);
                }
            }
        });

        reminderTextView = root.findViewById(R.id.reminder_TextView);
        reminderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement reminders here
            }
        });

        // Set the OnClick listeners for the CircleView objects.
        setCircleViewOnClickListener(root, R.id.red_circleView);
        setCircleViewOnClickListener(root, R.id.orange_circleView);
        setCircleViewOnClickListener(root, R.id.peach_circleView);
        setCircleViewOnClickListener(root, R.id.green_circleView);
        setCircleViewOnClickListener(root, R.id.turquoise_circleView);
        setCircleViewOnClickListener(root, R.id.cyan_circleView);
        setCircleViewOnClickListener(root, R.id.purple_circleView);
        setCircleViewOnClickListener(root, R.id.brown_circleView);

        return root;
    }

    private void setCircleViewOnClickListener(View root, int id) {
        final ConstraintLayout mainConstraintLayout = root.findViewById(R.id.main_ConstraintLayout);
        final CircleView circleView = root.findViewById(id);
        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bkgColor = circleView.getColor();
                mainConstraintLayout.setBackgroundColor(bkgColor);
            }
        });
    }
}
