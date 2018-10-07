import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class containing unit tests for class TagManager.
 */
public class TagManagerTest {

    /**
     * Runs before each test to create the initial files.
     */
    @BeforeEach
    public void setUp() {
        ReadWrite.createInitialFiles();
    }

    /**
     * Runs after each test to delete the files created in the set-up.
     */
    @AfterEach
    public void tearDown() {
        File tagDatabase = new File("ProjectFiles/tagDatabase.txt");
        File imageFilesSer = new File("ProjectFiles/imageFiles.ser");
        File dir = new File("ProjectFiles");

        tagDatabase.delete();
        imageFilesSer.delete();
        dir.delete();
    }

    /**
     * Tests to make sure the initial values in the TagManager instance
     * constructor are set properly.
     */
    @Test
    public void testConstructor() {
        TagManager tm = new TagManager();

        ArrayList<Tag> tags = new ArrayList<>();

        for (Tag tag: tm) {
            tags.add(tag);
        }
        assertEquals(tm.getTagsVersions().size(), 0);
        assertEquals(tags.size(), 0);
    }

    /**
     * Tests instance method addTag when there are no Tag objects in the
     * TagManager instance.
     */
    @Test
    public void testAddTagEmptyTagManager() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("friends");


        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 1);
        assertEquals(tags.get(0).getName(), "@friends");
    }

    /**
     * Tests instance method addTag when the Tag name passed in to
     * add is prefixed with the '@' character.
     */
    @Test
    public void testAddTagTagNamePrefixedAtChar() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("@friends");


        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 1);
        assertEquals(tags.get(0).getName(), "@friends");
    }

    /**
     * Tests instance method addTag when the Tag name passed in to
     * add is not prefixed with the '@' character.
     */
    @Test
    public void testAddTagTagNameNotPrefixedAtChar() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("friends");


        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 1);
        assertEquals(tags.get(0).getName(), "@friends");
    }

    /**
     * Tests instance method addTag when there are Tag objects in the
     * TagManager instance.
     */
    @Test
    public void testAddTagNonEmptyTagManager() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("cow");
        tm.addTag("food");

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 2);
        assertEquals(tags.get(0).getName(), "@cow");
        assertEquals(tags.get(1).getName(), "@food");

        tm.addTag("animal");
        tags.clear();

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 3);
        assertEquals(tags.get(0).getName(), "@cow");
        assertEquals(tags.get(1).getName(), "@food");
        assertEquals(tags.get(2).getName(), "@animal");
    }

    /**
     * Tests instance method addTag when the Tag to be added to the TagManager
     * instance has an empty string as its name.
     */
    @Test
    public void testAddTagEmptyString() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("yellow");
        tm.addTag("");

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 1);
        assertEquals(tags.get(0).getName(), "@yellow");
    }

    /**
     * Tests instance method addTag when the Tag to be added already exists in the
     * TagManager instance.
     */
    @Test
    public void testAddTagTagInTagManager() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("yellow");
        tm.addTag("blue");
        tm.addTag("yellow");

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 2);
        assertEquals(tags.get(0).getName(), "@yellow");
        assertEquals(tags.get(1).getName(), "@blue");
    }

    /**
     * Tests instance method deleteTag when there are no Tag objects in the
     * TagManager instance.
     */
    @Test
    public void testDeleteTagEmptyTagManager() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.deleteTag("@dog");

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 0);
    }

    /**
     * Tests instance method deleteTag when the Tag to be deleted is not in
     * the TagManager instance.
     */
    @Test
    public void testDeleteTagTagNotInTagManager() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("animal");
        tm.addTag("pet");
        tm.deleteTag("@dog");

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 2);
        assertEquals(tags.get(0).getName(), "@animal");
        assertEquals(tags.get(1).getName(), "@pet");
    }

    /**
     * Tests instance method deleteTag when there is exactly one Tag object
     * in the TagManager instance.
     */
    @Test
    public void testDeleteTagNonEmptyTagManagerLengthOne() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("animal");
        tm.deleteTag("@animal");

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 0);
    }

    /**
     * Tests instance method deleteTag when there is more than one Tag object
     * in the TagManager instance.
     */
    @Test
    public void testDeleteTagNonEmptyTagManagerLengthGreaterThanOne() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("animal");
        tm.addTag("pet");
        tm.addTag("dog");
        tm.deleteTag("@dog");

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 2);
        assertEquals(tags.get(0).getName(), "@animal");
        assertEquals(tags.get(1).getName(), "@pet");
    }

    /**
     * Tests instance method addTagsVersion when the TagManager instance
     * initially has no Tag objects.
     */
    @Test
    public void testAddTagsVersionInitiallyEmptyTagManager() {
        TagManager tm = new TagManager();

        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 0);
    }

    /**
     * Tests instance method addTagsVersion when the TagManager instance
     * initially has exactly one Tag object.
     */
    @Test
    public void testAddTagsVersionInitiallyOneTagTagManager() {
        TagManager tm = new TagManager();

        tm.addTag("dog");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 1);
        assertEquals(tm.getTagsVersions().get(0).size(), 1);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@dog");
    }

    /**
     * Tests instance method addTagsVersion when the TagManager instance initially
     * has multiple Tag objects.
     */
    @Test
    public void testAddTagsVersionInitiallyMultipleTagsTagManager() {
        TagManager tm = new TagManager();

        tm.addTag("cat");
        tm.addTag("fluffy");
        tm.addTag("pet");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 1);
        assertEquals(tm.getTagsVersions().get(0).size(), 3);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@cat");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@fluffy");
        assertEquals(tm.getTagsVersions().get(0).get(2).getName(), "@pet");
    }

    /**
     * Tests instance method addTagsVersion when Tag objects are added to the
     * TagManager instance after a previous version of Tag objects has been stored.
     */
    @Test
    public void testAddTagsVersionAddingTagsAfterVersion() {

        TagManager tm = new TagManager();

        tm.addTag("cat");
        tm.addTag("fluffy");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 1);
        assertEquals(tm.getTagsVersions().get(0).size(), 2);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@cat");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@fluffy");

        tm.addTag("loyal animal");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 2);
        assertEquals(tm.getTagsVersions().get(0).size(), 2);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@cat");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@fluffy");
        assertEquals(tm.getTagsVersions().get(1).size(), 3);
        assertEquals(tm.getTagsVersions().get(1).get(0).getName(), "@cat");
        assertEquals(tm.getTagsVersions().get(1).get(1).getName(), "@fluffy");
        assertEquals(tm.getTagsVersions().get(1).get(2).getName(), "@loyal animal");
    }

    /**
     * Tests instance method addTagsVersion when no Tag objects are added
     * or deleted from the TagManager instance after the previous version of Tag objects
     * has been stored.
     */
    @Test
    public void testAddTagsVersionNoChangeAfterVersion() {

        TagManager tm = new TagManager();

        tm.addTag("house");
        tm.addTag("large");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 1);
        assertEquals(tm.getTagsVersions().get(0).size(), 2);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@house");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@large");

        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 1);
        assertEquals(tm.getTagsVersions().get(0).size(), 2);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@house");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@large");
    }

    /**
     * Tests instance method addTagsVersion when some Tag objects are deleted
     * from the TagManager instance after the previous version of Tag objects has
     * been stored.
     */
    @Test
    public void testAddTagsVersionDeletingSomeTagsAfterVersion() {
        TagManager tm = new TagManager();

        tm.addTag("farm");
        tm.addTag("pig");
        tm.addTag("cow");
        tm.addTag("sheep");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 1);
        assertEquals(tm.getTagsVersions().get(0).size(), 4);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@farm");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@pig");
        assertEquals(tm.getTagsVersions().get(0).get(2).getName(), "@cow");
        assertEquals(tm.getTagsVersions().get(0).get(3).getName(), "@sheep");

        tm.deleteTag("@farm");
        tm.deleteTag("@cow");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 2);
        assertEquals(tm.getTagsVersions().get(0).size(), 4);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@farm");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@pig");
        assertEquals(tm.getTagsVersions().get(0).get(2).getName(), "@cow");
        assertEquals(tm.getTagsVersions().get(0).get(3).getName(), "@sheep");
        assertEquals(tm.getTagsVersions().get(1).size(), 2);
        assertEquals(tm.getTagsVersions().get(1).get(0).getName(), "@pig");
        assertEquals(tm.getTagsVersions().get(1).get(1).getName(), "@sheep");
    }

    /**
     * Tests instance method addTagsVersion when all Tag objects are deleted
     * from the TagManager instance after the previous version of Tag objects has
     * been stored.
     */
    @Test
    public void testAddTagsVersionDeletingAllTagsAfterVersion() {
        TagManager tm = new TagManager();

        tm.addTag("farm");
        tm.addTag("pig");
        tm.addTag("cow");
        tm.addTag("sheep");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 1);
        assertEquals(tm.getTagsVersions().get(0).size(), 4);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@farm");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@pig");
        assertEquals(tm.getTagsVersions().get(0).get(2).getName(), "@cow");
        assertEquals(tm.getTagsVersions().get(0).get(3).getName(), "@sheep");

        tm.deleteTag("@farm");
        tm.deleteTag("@cow");
        tm.deleteTag("@sheep");
        tm.deleteTag("@pig");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 2);
        assertEquals(tm.getTagsVersions().get(0).size(), 4);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@farm");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@pig");
        assertEquals(tm.getTagsVersions().get(0).get(2).getName(), "@cow");
        assertEquals(tm.getTagsVersions().get(0).get(3).getName(), "@sheep");
        assertEquals(tm.getTagsVersions().get(1).size(), 0);
    }

    /**
     * Tests instance method addTagsVersion when some Tag objects are deleted
     * from the TagManager instance and then the same number of Tag objects are
     * added but with different names from the ones that were deleted after the
     * previous version of Tag objects has been stored.
     */
    @Test
    public void testAddTagsVersionDeletingSomeTagsThenAddingSameNumberOfTagsDifferentNamesAfterVersion() {
        TagManager tm = new TagManager();

        tm.addTag("farm");
        tm.addTag("pig");
        tm.addTag("cow");
        tm.addTag("sheep");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 1);
        assertEquals(tm.getTagsVersions().get(0).size(), 4);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@farm");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@pig");
        assertEquals(tm.getTagsVersions().get(0).get(2).getName(), "@cow");
        assertEquals(tm.getTagsVersions().get(0).get(3).getName(), "@sheep");

        tm.deleteTag("@farm");
        tm.deleteTag("@cow");
        tm.addTag("duck");
        tm.addTag("farm dog");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 2);
        assertEquals(tm.getTagsVersions().get(0).size(), 4);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@farm");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@pig");
        assertEquals(tm.getTagsVersions().get(0).get(2).getName(), "@cow");
        assertEquals(tm.getTagsVersions().get(0).get(3).getName(), "@sheep");
        assertEquals(tm.getTagsVersions().get(1).size(), 4);
        assertEquals(tm.getTagsVersions().get(1).get(0).getName(), "@pig");
        assertEquals(tm.getTagsVersions().get(1).get(1).getName(), "@sheep");
        assertEquals(tm.getTagsVersions().get(1).get(2).getName(), "@duck");
        assertEquals(tm.getTagsVersions().get(1).get(3).getName(), "@farm dog");
    }

    /**
     * Tests instance method addTagsVersion when some Tag objects are deleted
     * from the TagManager instance and then the same number of Tag objects are added
     * with the same names as the ones that were deleted after the previous version
     * of Tag objects has been stored.
     */
    @Test
    public void testAddTagsVersionDeletingSomeTagsThenAddingSameNumberOfTagsSameNamesAfterVersion() {
        TagManager tm = new TagManager();

        tm.addTag("farm");
        tm.addTag("pig");
        tm.addTag("cow");
        tm.addTag("sheep");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 1);
        assertEquals(tm.getTagsVersions().get(0).size(), 4);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@farm");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@pig");
        assertEquals(tm.getTagsVersions().get(0).get(2).getName(), "@cow");
        assertEquals(tm.getTagsVersions().get(0).get(3).getName(), "@sheep");

        tm.deleteTag("@sheep");
        tm.deleteTag("@cow");
        tm.addTag("cow");
        tm.addTag("sheep");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 1);
        assertEquals(tm.getTagsVersions().get(0).size(), 4);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@farm");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@pig");
        assertEquals(tm.getTagsVersions().get(0).get(2).getName(), "@cow");
        assertEquals(tm.getTagsVersions().get(0).get(3).getName(), "@sheep");
    }

    /**
     * Tests instance method setTags when there are Tag objects
     * in the TagManager instance.
     */
    @Test
    public void testSetTagsNonEmptyTagManager() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tagsToSetTo = new ArrayList<>();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("cat");
        tm.addTag("angry");

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 2);
        assertEquals(tags.get(0).getName(), "@cat");
        assertEquals(tags.get(1).getName(), "@angry");

        tagsToSetTo.add(new Tag("@happy"));
        tagsToSetTo.add(new Tag("@meow"));
        tagsToSetTo.add(new Tag("@paws"));

        tm.setTags(tagsToSetTo);
        tags.clear();

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 3);
        assertEquals(tags.get(0).getName(), "@happy");
        assertEquals(tags.get(1).getName(), "@meow");
        assertEquals(tags.get(2).getName(), "@paws");
    }

    /**
     * Tests instance method setTags when the Tag objects to set the TagManager
     * instance to are empty.
     */
    @Test
    public void testSetTagsNoTags() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tagsToSetTo = new ArrayList<>();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.setTags(tagsToSetTo);

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 0);
    }

    /**
     * Tests instance method setTags when there is exactly one Tag object to set
     * the TagManager instance to.
     */
    @Test
    public void testSetTagsOneTag() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tagsToSetTo = new ArrayList<>();
        ArrayList<Tag> tags = new ArrayList<>();

        tagsToSetTo.add(new Tag("@dog"));

        tm.setTags(tagsToSetTo);

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 1);
        assertEquals(tags.get(0).getName(), "@dog");
    }

    /**
     * Tests instance method setTags when there are multiple Tag objects to set the
     * TagManager instance to.
     */
    @Test
    public void testSetTagsMultipleTags() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tagsToSetTo = new ArrayList<>();
        ArrayList<Tag> tags = new ArrayList<>();

        tagsToSetTo.add(new Tag("@dog"));
        tagsToSetTo.add(new Tag("@fluffy"));
        tagsToSetTo.add(new Tag("@pet"));

        tm.setTags(tagsToSetTo);

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 3);
        assertEquals(tags.get(0).getName(), "@dog");
        assertEquals(tags.get(1).getName(), "@fluffy");
        assertEquals(tags.get(2).getName(), "@pet");
    }

    /**
     * Tests instance method getTagsVersions when no versions of Tag objects
     * have been added to the TagManager instance.
     */
    @Test
    public void testGetTagsVersionsNoVersions() {

        TagManager tm = new TagManager();

        assertEquals(tm.getTagsVersions().size(), 0);
    }

    /**
     * Tests instance method getTagsVersions when exactly one version of Tag
     * objects has been added to the TagManager instance.
     */
    @Test
    public void testGetTagsVersionsOneVersion() {

        TagManager tm = new TagManager();

        tm.addTag("dog");
        tm.addTag("fluffy");
        tm.addTag("pet");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 1);
        assertEquals(tm.getTagsVersions().get(0).size(), 3);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@dog");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@fluffy");
        assertEquals(tm.getTagsVersions().get(0).get(2).getName(), "@pet");
    }

    /**
     * Tests instance method getTagsVersions when multiple versions of Tag
     * objects have been added to the TagManager instance.
     */
    @Test
    public void testGetTagsVersionsMultipleVersions() {

        TagManager tm = new TagManager();

        tm.addTag("dog");
        tm.addTag("fluffy");
        tm.addTag("pet");
        tm.addTagsVersion();

        tm.deleteTag("@dog");
        tm.addTagsVersion();

        tm.deleteTag("@fluffy");
        tm.deleteTag("@pet");
        tm.addTagsVersion();

        tm.addTag("friend");
        tm.addTag("fluffy");
        tm.addTag("pet");
        tm.addTagsVersion();

        assertEquals(tm.getTagsVersions().size(), 4);
        assertEquals(tm.getTagsVersions().get(0).size(), 3);
        assertEquals(tm.getTagsVersions().get(0).get(0).getName(), "@dog");
        assertEquals(tm.getTagsVersions().get(0).get(1).getName(), "@fluffy");
        assertEquals(tm.getTagsVersions().get(0).get(2).getName(), "@pet");
        assertEquals(tm.getTagsVersions().get(1).size(), 2);
        assertEquals(tm.getTagsVersions().get(1).get(0).getName(), "@fluffy");
        assertEquals(tm.getTagsVersions().get(1).get(1).getName(), "@pet");
        assertEquals(tm.getTagsVersions().get(2).size(), 0);
        assertEquals(tm.getTagsVersions().get(3).size(), 3);
        assertEquals(tm.getTagsVersions().get(3).get(0).getName(), "@friend");
        assertEquals(tm.getTagsVersions().get(3).get(1).getName(), "@fluffy");
        assertEquals(tm.getTagsVersions().get(3).get(2).getName(), "@pet");
    }

    /**
     * Tests the iterator design when the TagManager instance has no
     * Tag objects.
     */
    @Test
    public void testIteratorEmptyTagManager() {

        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 0);
    }

    /**
     * Tests the iterator design when the TagManager instance has
     * exactly one Tag object.
     */
    @Test
    public void testIteratorOneTagTagManager() {

        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("snake");

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 1);
        assertEquals(tags.get(0).getName(), "@snake");
    }

    /**
     * Tests the iterator design when the TagManager instance has
     * multiple Tag objects.
     */
    @Test
    public void testIteratorMultipleTagsTagManager() {

        TagManager tm = new TagManager();
        ArrayList<Tag> tags = new ArrayList<>();

        tm.addTag("snake");
        tm.addTag("jungle");
        tm.addTag("venomous");
        tm.deleteTag("@jungle");

        for (Tag tag: tm) {
            tags.add(tag);
        }

        assertEquals(tags.size(), 2);
        assertEquals(tags.get(0).getName(), "@snake");
        assertEquals(tags.get(1).getName(), "@venomous");
    }
}
