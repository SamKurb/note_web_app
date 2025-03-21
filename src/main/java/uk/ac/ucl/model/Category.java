package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Category
{
    private String name;

    private Category parent_cat;
    private ArrayList<Category> child_cats;

    private ArrayList<Note> note_list; // All the notes belonging to a category
    private Map<Integer, Note> note_map; // To be used for fast lookup of notes

    public Category(String name)
    {
        this.name = name;

        this.parent_cat = null;
        this.child_cats = new ArrayList<>();

        this.note_list = new ArrayList<>();
        this.note_map = new HashMap<>();
    }

    public Category get_parent()
    {
        return parent_cat;
    }

    private void add_sub_category(Category child)
    {
        child.set_parent(this);
        child_cats.add(child);
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

    public void remove_note(int ID)
    {
        Note note = note_map.get(ID);
        note_map.remove(ID);
        note_list.remove(ID);
    }

    private void remove_sub_category(Category child)
    {
        child_cats.remove(child);
    }

    private void add_sub_category(String name)
    {
        Category child = new Category(name);
        child_cats.add(child);
    }

    public boolean has_note_by_ID(int ID)
    {
        return note_map.containsKey(ID);
    }

    public void set_parent(Category new_parent)
    {
        if (name.equals("main")) { return; }
        if (child_cats.contains(new_parent)) { return; }

        if (parent_cat != null)
        {
            new_parent.remove_sub_category(this);
        }
        else
        {
            parent_cat = new_parent;
        }
    }

    public String get_name()
    {
        return name;
    }
}
