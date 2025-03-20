package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Category
{
    private String name;

    private Category parent_cat;
    private ArrayList<Category> child_cats;

    private ArrayList<Note> note_list; // All the notes belonging to a category
    private HashMap<Integer, Note> note_map; // To be used for fast lookup of notes

    public Category(String name)
    {
        this.name = name;

        this.parent_cat = null;
        this.child_cats = new ArrayList<>();

        this.note_list = new ArrayList<>();
        this.note_map = new HashMap<>();
    }

    public void add_note(Note note)
    {
        note_list.add(note);
        note_map.put(note.get_ID(), note);
    }

    public Note get_note(int ID)
    {
        return note_map.get(ID);
    }

    public void add_sub_category(String name)
    {
        Category child = new Category(name);
        child_cats.add(child);
    }

    public void change_parent(Category new_parent)
    {
        if (this.name.equals("main")) { return; }
        if (child_cats.contains(new_parent)) { return; }

        parent_cat = new_parent;
    }

    public String get_name()
    {
        return name;
    }
}
