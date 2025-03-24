package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class IndexManager {
    private Map<Integer, Index> indexMap; // Map of all indices by ID for fast access
    private Index mainIndex; // Root index

    private static int highestIndexID = 0;


    public IndexManager()
    {
        this.indexMap = new HashMap<>();
        // Create the main index
        this.mainIndex = new Index("main", 0);
        indexMap.put(mainIndex.getID(), mainIndex);
    }

    public Index getMainIndex()
    {
        return mainIndex;
    }

    public Index getIndexByID(int id)
    {
        return indexMap.get(id);
    }


    public Index createIndex(String name, int parentID)
    {
        // Create the new index
        Index newIndex = new Index(name, ++highestIndexID);
        newIndex.setParentID(parentID);

        // Get the parent index (use main if parent doesn't exist)
        Index parent = getIndexByID(parentID);
        if (parent == null) {
            parent = mainIndex;
            parentID = mainIndex.getID();
        }

        // Set up the parent-child relationship
        parent.addSubIndex(newIndex.getID());

        // Store the index
        indexMap.put(newIndex.getID(), newIndex);

        return newIndex;
    }

    public int getHighestIndexID()
    {
        return highestIndexID;
    }

    public void setHighestIndexID(int ID)
    {
        highestIndexID = ID;
    }

    /**
     * Deletes an index by ID. Can't delete the main index.
     * Returns true if successful, false otherwise.
     */
    public boolean deleteIndex(int id) {
        // Can't delete the main index
        if (id == mainIndex.getID()) {
            return false;
        }

        Index index = getIndexByID(id);
        if (index == null) {
            return false; // Index doesn't exist
        }

        // Remove from parent
        int parentID = index.getParentID();
        if (parentID != -1) {
            Index parent = getIndexByID(parentID);
            if (parent != null) {
                parent.removeSubIndex(id);
            }
        }

        // Remove all child indices (recursively)
        ArrayList<Integer> childIDs = new ArrayList<>(index.getChildIndices());
        for (int childID : childIDs) {
            deleteIndex(childID);
        }

        // Remove from map
        indexMap.remove(id);

        return true;
    }

    /**
     * Moves an index to a new parent.
     * Returns true if successful, false otherwise.
     */
    public boolean moveIndex(int indexID, int newParentID) {
        // Can't move the main index
        if (indexID == mainIndex.getID()) {
            return false;
        }

        Index index = getIndexByID(indexID);
        Index newParent = getIndexByID(newParentID);

        if (index == null || newParent == null) {
            return false;
        }

        // Check for circular reference (can't make a child the parent)
        if (isDescendant(newParent.getID(), index.getID())) {
            return false;
        }

        // Remove from old parent
        int oldParentID = index.getParentID();
        if (oldParentID != -1) {
            Index oldParent = getIndexByID(oldParentID);
            if (oldParent != null) {
                oldParent.removeSubIndex(indexID);
            }
        }

        // Add to new parent
        newParent.addSubIndex(indexID);

        return true;
    }

    /**
     * Checks if potentialDescendant is a descendant of ancestor.
     */
    private boolean isDescendant(int potentialDescendant, int ancestor)
    {
        if (potentialDescendant == ancestor)
        {
            return true;
        }

        Index index = getIndexByID(potentialDescendant);
        if (index == null)
        {
            return false;
        }

        for (int childID : index.getChildIndices())
        {
            if (isDescendant(childID, ancestor))
            {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Index> getAllIndices()
    {
        return new ArrayList<>(indexMap.values());
    }

    public int getIndexCount()
    {
        return indexMap.size();
    }

    /**
     * Gets all direct child indices of a parent index.
     */
    public ArrayList<Index> getChildIndices(int parentID)
    {
        Index parent = getIndexByID(parentID);
        if (parent == null)
        {
            return new ArrayList<>();
        }

        ArrayList<Index> children = new ArrayList<>();
        for (int childID : parent.getChildIndices()) {
            Index child = getIndexByID(childID);
            if (child != null) {
                children.add(child);
            }
        }

        return children;
    }

    public void setMainIndex(Index index) {
        this.mainIndex = index;
    }

    public void addLoadedIndex(Index index)
    {
        indexMap.put(index.getID(), index);
        if (index.isMain())
        {
            mainIndex = index;
        }
    }
}