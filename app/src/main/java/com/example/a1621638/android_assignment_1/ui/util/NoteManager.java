package com.example.a1621638.android_assignment_1.ui.util;

import com.example.a1621638.android_assignment_1.model.Category;
import com.example.a1621638.android_assignment_1.model.Note;

import java.util.Date;
import java.util.Stack;

public class NoteManager {

    private Note currentNote;
    private Stack<Note> notes;

    public NoteManager() {
        notes = new Stack<>();
        currentNote = new Note();
    }

    public void undo() {
        if(notes.empty()) {
            return;
        }

        currentNote = notes.pop();
    }

    public void setTitle(String title) {
        notes.push(currentNote.clone());
        currentNote.setTitle(title);
    }

    public String getTitle() {
        return currentNote.getTitle();
    }

    public void setBody(String body) {
        notes.push(currentNote.clone());
        currentNote.setBody(body);
    }

    public String getBody() {
        return currentNote.getBody();
    }

    public void setCategory(Category category) {
        notes.push(currentNote.clone());
        currentNote.setCategory(category);
    }

    public Category getCategory() {
        return currentNote.getCategory();
    }

    public void setReminder(Date date) {
        notes.push(currentNote.clone());
        currentNote.setReminder(date);
        currentNote.setHasReminder(true);
    }

    public Date getReminder() {
        return currentNote.getReminder();
    }

    public boolean hasReminder() {
        return currentNote.isHasReminder();
    }

}
