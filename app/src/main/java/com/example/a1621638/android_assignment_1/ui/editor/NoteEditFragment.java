package com.example.a1621638.android_assignment_1.ui.editor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.a1621638.android_assignment_1.model.Note;
import com.example.a1621638.android_assignment_1.ui.util.CircleView;
import com.example.a1621638.android_assignment_1.ui.util.DatePickerDialogFragment;
import com.example.a1621638.android_assignment_1.R;
import com.example.a1621638.android_assignment_1.ui.util.NoteManager;
import com.example.a1621638.android_assignment_1.ui.util.TimePickerDialogFragment;
import com.example.a1621638.android_assignment_1.model.Category;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

/**
 * A placeholder fragment containing a simple view.
 */
public class NoteEditFragment extends Fragment {
    private static final String DATE_FORMAT_PATTERN = "EEEE, MMMM d 'at' h:mm a";
    private static final String HAS_REMINDER = "Reminder: ";
    private static final String NO_REMINDER = "Add a reminder";

    private NoteManager noteManager;
    private DateFormat dateFormat;

    private LinearLayout optionsLinearLayout;
    private Switch showOptionsSwitch;
    private TextView reminderTextView;
    private TextView titleEditText;
    private TextView bodyEditText;
    private Button undoButton;
    private View root;

    public NoteEditFragment() {
        noteManager = new NoteManager();
        dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notes, container, false);

        // Set the event listeners for all views.
        setOptionOnCheckedListener();
        setReminderOnClickListener();
        setTitleTextChangedListener();
        setBodyTextChangedListener();
        setUndoButtonOnClickListener();

        // Set the OnClick listeners for the CircleView objects.
        setCircleViewOnClickListener(R.id.red_circleView, Category.RED);
        setCircleViewOnClickListener(R.id.orange_circleView, Category.ORANGE);
        setCircleViewOnClickListener(R.id.yellow_circleView, Category.YELLOW);
        setCircleViewOnClickListener(R.id.green_circleView, Category.GREEN);
        setCircleViewOnClickListener(R.id.lightBlue_circleView, Category.LIGHT_BLUE);
        setCircleViewOnClickListener(R.id.darkBlue_circleView, Category.DARK_BLUE);
        setCircleViewOnClickListener(R.id.purple_circleView, Category.PURPLE);
        setCircleViewOnClickListener(R.id.white_circleView, Category.WHITE);

        return root;
    }

    public void shareNote() {
        String data = noteManager.toString();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, data);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void loadNote(ConstraintLayout layout) {
        titleEditText.clearFocus();
        titleEditText.setText(noteManager.getTitle());

        bodyEditText.clearFocus();
        bodyEditText.setText(noteManager.getBody());

        setBackgroundColor(noteManager.getCategory(), layout);

        if(noteManager.isHasReminder()) {
            reminderTextView.setText(HAS_REMINDER + dateFormat.format(noteManager.getReminder()));
        } else {
            reminderTextView.setText(NO_REMINDER);
        }
    }

    private void setUndoButtonOnClickListener() {
        undoButton = root.findViewById(R.id.undo_Button);
        final ConstraintLayout mainConstraintLayout = root.findViewById(R.id.main_ConstraintLayout);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteManager.undo();
                loadNote(mainConstraintLayout);
            }
        });
    }

    private void setTitleTextChangedListener() {
        titleEditText = root.findViewById(R.id.title_EditText);
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                // Check that the EditText is focused by the user before saving the change.
                // Otherwise, everytime setTitle() is called, a new copy of the Note is created.
                if(getActivity().getCurrentFocus() == titleEditText) {
                    noteManager.setTitle(titleEditText.getText().toString());
                }
            }
        });
    }

    private void setBodyTextChangedListener() {
        bodyEditText = root.findViewById(R.id.body_EditText);
        bodyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(getActivity().getCurrentFocus() == bodyEditText) {
                    noteManager.setBody(bodyEditText.getText().toString());
                }
            }
        });
    }

    private void setOptionOnCheckedListener() {
        optionsLinearLayout = root.findViewById(R.id.options_LinearLayout);
        showOptionsSwitch = root.findViewById(R.id.showOptions_Switch);
        showOptionsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    optionsLinearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    optionsLinearLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setReminderOnClickListener() {
        reminderTextView = root.findViewById(R.id.reminder_TextView);
        reminderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                final TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.create(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                Date currentReminder = calendar.getTime();
                                reminderTextView.setText("Reminder: " + dateFormat.format(currentReminder));
                                noteManager.setReminder(currentReminder);
                            }
                        }
                );

                DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.create(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                timePickerDialogFragment.show(getFragmentManager(), "timePicker");
                            }
                        }
                );

                datePickerDialogFragment.show(getFragmentManager(), "datePicker");
            }
        });
    }

    private void setCircleViewOnClickListener(int id, final Category category) {
        final ConstraintLayout mainConstraintLayout = root.findViewById(R.id.main_ConstraintLayout);
        final CircleView circleView = root.findViewById(id);
        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundColor(category, mainConstraintLayout);
                noteManager.setCategory(category);
            }
        });
    }

    private void setBackgroundColor(Category category, ConstraintLayout layout) {
        int colorId;

        switch(category) {
            case RED:
                colorId = R.color.redCircleColor;
                break;
            case ORANGE:
                colorId = R.color.orangeCircleColor;
                break;
            case YELLOW:
                colorId = R.color.yellowCircleColor;
                break;
            case GREEN:
                colorId = R.color.greenCircleColor;
                break;
            case LIGHT_BLUE:
                colorId = R.color.lightBlueCircleColor;
                break;
            case DARK_BLUE:
                colorId = R.color.darkBlueCircleColor;
                break;
            case PURPLE:
                colorId = R.color.purpleCircleColor;
                break;
            default:
                colorId = R.color.whiteCircleColor;
                break;
        }

        layout.setBackgroundColor(getContext().getColor(colorId));
    }
}
