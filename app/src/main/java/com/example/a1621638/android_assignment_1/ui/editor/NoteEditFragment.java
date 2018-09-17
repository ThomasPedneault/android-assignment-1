package com.example.a1621638.android_assignment_1.ui.editor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private Stack<Note> notes;

    private LinearLayout optionsLinearLayout;
    private Switch showOptionsSwitch;
    private TextView reminderTextView;
    private TextView titleEditText;
    private TextView bodyEditText;

    public NoteEditFragment() {
        notes = new Stack<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        setOptionOnCheckedListener(root);
        setReminderOnClickListener(root);

        titleEditText = root.findViewById(R.id.title_EditText);
        bodyEditText = root.findViewById(R.id.body_EditText);

        // Set the OnClick listeners for the CircleView objects.
        setCircleViewOnClickListener(root, R.id.red_circleView, Category.RED);
        setCircleViewOnClickListener(root, R.id.orange_circleView, Category.ORANGE);
        setCircleViewOnClickListener(root, R.id.yellow_circleView, Category.YELLOW);
        setCircleViewOnClickListener(root, R.id.green_circleView, Category.GREEN);
        setCircleViewOnClickListener(root, R.id.lightBlue_circleView, Category.LIGHT_BLUE);
        setCircleViewOnClickListener(root, R.id.darkBlue_circleView, Category.DARK_BLUE);
        setCircleViewOnClickListener(root, R.id.purple_circleView, Category.PURPLE);
        setCircleViewOnClickListener(root, R.id.brown_circleView, Category.BROWN);

        // Add the note object to the stack.
        notes.push(createNote());

        return root;
    }

    private Note createNote() {
        Note note = new Note();

        note.setTitle(titleEditText.getText().toString());
        note.setBody(bodyEditText.getText().toString());
        note.setHasReminder(false);
        note.setCreated(Calendar.getInstance().getTime());
        note.setModified(Calendar.getInstance().getTime());

        return note;
    }

    private void setOptionOnCheckedListener(View root) {
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

    private void setReminderOnClickListener(View root) {
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

                                DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
                                Date date = calendar.getTime();
                                reminderTextView.setText("Reminder: " + dateFormat.format(date));
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

    private void setCircleViewOnClickListener(View root, int id, Category category) {
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
