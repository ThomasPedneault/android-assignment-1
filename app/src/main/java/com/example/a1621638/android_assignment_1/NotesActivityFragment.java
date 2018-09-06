package com.example.a1621638.android_assignment_1;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toolbar;

/**
 * A placeholder fragment containing a simple view.
 */
public class NotesActivityFragment extends Fragment {

    private LinearLayout optionsLinearLayout;
    private Toolbar optionsToolbar;
    private Switch showOptionsSwitch;

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

        return root;
    }
}
