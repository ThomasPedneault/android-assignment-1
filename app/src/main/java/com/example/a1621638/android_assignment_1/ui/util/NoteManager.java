package com.example.a1621638.android_assignment_1.ui.util;

import com.example.a1621638.android_assignment_1.model.Category;
import com.example.a1621638.android_assignment_1.model.Note;

import java.util.Date;
import java.util.Stack;

/**
 * Keeps a history of the changes to the Note object and allows the user to revert
 * to previous changes.
 */
public class NoteManager {

    private Note currentNote;
    private Stack<Note> notes;

    public NoteManager() {
        notes = new Stack<>();
        currentNote = new Note();
        currentNote.setTitle("");
        currentNote.setBody("");
        currentNote.setCategory(Category.WHITE);
    }

    /**
     * Undoes the latest change to the note.
     */
    public void undo() {
        if(!notes.isEmpty()) {
            currentNote = notes.pop();
        }
    }

    /**
     * Sets the title of the currently set Note and pushes a copy of the previous Note to the stack.
     * @param title
     */
    public void setTitle(String title) {
        notes.push(currentNote.clone());
        currentNote.setTitle(title);
    }

    public String getTitle() {
        return currentNote.getTitle();
    }

    /**
     * Sets the body of the currently set Note and pushes a copy of the previous Note to the stack.
     * @param body
     */
    public void setBody(String body) {
        notes.push(currentNote.clone());
        currentNote.setBody(body);
    }

    public String getBody() {
        return currentNote.getBody();
    }

    /**
     * Sets the category of the currently set Note and pushes a copy of the previous Note to the stack.
     * @param category
     */
    public void setCategory(Category category) {
        notes.push(currentNote.clone());
        currentNote.setCategory(category);
    }

    public Category getCategory() {
        return currentNote.getCategory();
    }

    /**
     * Sets the reminder of the currently set Note and pushes a copy of the previous Note to the stack.
     * @param date
     */
    public void setReminder(Date date) {
        notes.push(currentNote.clone());
        currentNote.setReminder(date);
        currentNote.setHasReminder(true);
    }

    public Date getReminder() {
        return currentNote.getReminder();
    }

    public boolean isHasReminder() {
        return currentNote.isHasReminder();
    }

    @Override
    public String toString() {
        return currentNote.toString();
    }

}
