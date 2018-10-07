import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class responsible for storing tag information about an image.
 */
public class Tag implements Cloneable, Serializable {

    /*
    Stores all Tag objects.
     */
    private static ArrayList<Tag> allTags = ReadWrite.getAllTagsFromDatabase();

    /*
    Stores the name of this Tag.
     */
    private String name;

    /**
     * Constructs a new Tag object with the given name.
     *
     * @param name name of the Tag
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this Tag.
     *
     * @return name of this Tag
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns all Tag objects.
     *
     * @return all Tag objects
     */
    public static ArrayList<Tag> getAllTags() {
        ArrayList<Tag> allTagsCopy = new ArrayList<>();

        for (Tag tag : allTags) {
            allTagsCopy.add(tag.clone());
        }

        return allTagsCopy;
    }

    /**
     * Returns true iff Tag with the given tagName is in the database of all
     * Tag objects.
     *
     * @param tagName name of the Tag
     * @return whether or not the Tag with the given tagName is in the database
     * of all Tag objects
     */
    private static boolean checkIfTagExistsInAllTags(String tagName) {
        boolean flag = false;

        if (tagName.length() > 0 && tagName.charAt(0) != '@') {
            tagName = "@" + tagName;
        }

        for (Tag tag : allTags) {
            if (tag.getName().equals(tagName)) {
                flag = true;
            }
        }

        return flag;
    }

    /**
     * Adds the Tag with the given tagName to the database of all Tag objects.
     *
     * @param tagName name of the Tag
     */
    public static void addTagToAllTags(String tagName) {
        if (tagName.length() > 0) {
            if (tagName.charAt(0) != '@') {
                tagName = "@" + tagName;
            }

            if (!checkIfTagExistsInAllTags(tagName)) {
                allTags.add(new Tag(tagName));
                ReadWrite.addTagToDatabase(tagName);
            }
        }
    }

    /**
     * Deletes the Tag with the given tagName from the database of all Tag objects.
     *
     * @param tagName name of the Tag
     */
    public static void deleteTagFromAllTags(String tagName) {
        if (checkIfTagExistsInAllTags(tagName)) {
            ArrayList<Tag> allTagsCopy = new ArrayList<>();

            for (Tag tag : allTags) {
                if (!tag.getName().equals(tagName)) {
                    allTagsCopy.add(tag.clone());
                    ReadWrite.removeTagFromDatabase(tagName);
                }
            }

            allTags = allTagsCopy;

            for (ImageFile imageFile : ImageFileManager.getImageFiles()) {
                imageFile.getTagManager().deleteTag(tagName);
            }
        }
    }

    /**
     * Returns a clone of this Tag.
     *
     * @return clone of this Tag
     */
    @Override
    public Tag clone() {
        Tag clonedTag = null;

        try {
            clonedTag = (Tag) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clonedTag;
    }
}
