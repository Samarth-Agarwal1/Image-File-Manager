import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Class responsible for managing Tag objects.
 */
public class TagManager implements Iterable<Tag>, Serializable {

    /*
    Stores the current Tag objects in this TagManager.
     */
    private ArrayList<Tag> tags;

    /*
    Stores the sets of Tag objects in this TagManager's tag history where
    the key is an integer representing the order each set of Tag objects
    was added in and the value is an arraylist of the set of Tag
    objects at that number.
     */
    private TreeMap<Integer, ArrayList<Tag>> tagsVersions;

    /*
    Stores the value of the key in tagsVersions.
     */
    private int keyValue;

    /**
     * Constructs a new TagManager object.
     */
    public TagManager() {
        tags = new ArrayList<>();
        tagsVersions = new TreeMap<>();
        keyValue = 0;
    }

    /**
     * Returns true iff the Tag with the given tagName is in this
     * TagManager's current version of Tag objects.
     *
     * @param tagName the name of the Tag
     * @return whether the Tag with the given tagName exists in this
     * TagManager's current version of Tag objects
     */
    private boolean checkIfTagExists(String tagName) {
        boolean flag = false;

        if (tagName.length() > 0 && tagName.charAt(0) != '@') {
            tagName = "@" + tagName;
        }

        for (Tag tag : tags) {
            if (tag.getName().equals(tagName)) {
                flag = true;
            }
        }

        return flag;
    }

    /**
     * Adds the Tag with the specified tagName to this TagManager and to the
     * database of all Tag objects.
     *
     * @param tagName the name of the Tag
     */
    public void addTag(String tagName) {
        if (tagName.length() > 0) {
            if (tagName.charAt(0) != '@') {
                tagName = "@" + tagName;
            }

            if (!checkIfTagExists(tagName)) {
                tags.add(new Tag(tagName));
            }

            Tag.addTagToAllTags(tagName);
        }
    }

    /**
     * Deletes the Tag with the specified tagName from this TagManager.
     *
     * @param tagName the name of the Tag
     */
    public void deleteTag(String tagName) {
        if (checkIfTagExists(tagName)) {
            ArrayList<Tag> tagsCopy = new ArrayList<>();

            for (Tag tag : tags) {
                if (!tag.getName().equals(tagName)) {
                    tagsCopy.add(tag.clone());
                }
            }

            tags = tagsCopy;
        }
    }

    /**
     * Saves the current set of Tag objects into this TagManager's tag history.
     */
    public void addTagsVersion() {
        boolean same = true;

        if (keyValue > 0 && tagsVersions.get(keyValue - 1).size() == tags.size()) {
            for (int i = 0; i < tags.size(); i++) {
                if (!tagsVersions.get(keyValue - 1).get(i).getName().equals(tags.get(i).getName())) {
                    same = false;
                }
            }
        } else {
            same = false;
        }

        if ((tagsVersions.size() == 0 && tags.size() != 0) || (keyValue > 0 && !same)) {
            ArrayList<Tag> temp = new ArrayList<>();

            for (Tag tag : tags) {
                temp.add(tag.clone());
            }

            tagsVersions.put(keyValue, temp);
            keyValue += 1;
        }
    }

    /**
     * Sets this TagManager's current Tag objects to the Tag objects in tagArrayList.
     *
     * @param tagArrayList the Tag objects to set this TagManager to
     */
    public void setTags(ArrayList<Tag> tagArrayList) {
        ArrayList<Tag> newTags = new ArrayList<Tag>();

        for (Tag tag : tagArrayList) {
            newTags.add(tag.clone());
        }

        this.tags = newTags;
    }

    /**
     * Returns the sets of Tag objects in this TagManager's tag history.
     *
     * @return the sets of Tag objects in this TagManager's tag history
     */
    public TreeMap<Integer, ArrayList<Tag>> getTagsVersions() {
        TreeMap<Integer, ArrayList<Tag>> returnTreeMap = new TreeMap<>();

        for (Integer i : tagsVersions.keySet()) {
            ArrayList<Tag> tagsVersionsArrayList = tagsVersions.get(i);
            ArrayList<Tag> temp = new ArrayList<>();

            for (Tag tag : tagsVersionsArrayList) {
                temp.add(tag.clone());
            }

            returnTreeMap.put(i, temp);
        }

        return returnTreeMap;
    }

    /**
     * Returns an iterator of this TagManager.
     *
     * @return an iterator of this TagManager
     */
    @Override
    public Iterator<Tag> iterator() {
        return new TagManagerIterator();
    }

    /**
     * Class responsible for iterating over Tag objects stored in
     * this TagManager.
     */
    private class TagManagerIterator implements Iterator<Tag> {

        /*
        Stores the current index of this TagManagerIterator.
         */
        private int index = 0;

        /**
         * Returns true iff this TagManagerIterator has an object
         * at its current index.
         *
         * @return whether or not this TagManager has an object at
         * its current index
         */
        @Override
        public boolean hasNext() {
            return index < tags.size();
        }

        /**
         * Returns the next Tag in this TagManagerIterator.
         *
         * @return the next Tag in this TagManagerIterator
         */
        @Override
        public Tag next() {
            Tag nextTag = tags.get(index).clone();
            index++;

            return nextTag;
        }
    }
}
