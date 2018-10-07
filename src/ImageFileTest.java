import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

class ImageFileTest {

    /**
     * Tests getName method in ImageFile.
     */

    @Test
    public void testGetName() {

        String currentDir = System.getProperty("user.dir");

        ImageFile testImage = new ImageFile(currentDir + "/TestingFiles1/flat.jpg");
        assertEquals("flat.jpg", testImage.getName());
    }

    /**
     * Tests getPath method in ImageFile.
     */

    @Test
    public void testGetPath() {
        String currentDir = System.getProperty("user.dir");

        ImageFile testImage = new ImageFile(currentDir + "/TestingFiles1/flat.jpg");
        assertEquals(currentDir + "/TestingFiles1/flat.jpg", testImage.getPath());

    }

    /**
     * Tests getOriginalName method in ImageFile.
     */

    @Test
    public void testGetOriginalName() {
        String currentDir = System.getProperty("user.dir");
        ImageFile testImage = new ImageFile(currentDir + "/TestingFiles1/flat.jpg");
        assertEquals("flat", testImage.getOriginalName());


    }

    /**
     * Tests getDirectory method in ImageFile.
     */

    @Test
    public void testGetDirectory() {
        String currentDir = System.getProperty("user.dir");
        ImageFile testImage = new ImageFile(currentDir + "/TestingFiles1/flat.jpg");
        assertEquals(currentDir + "/TestingFiles1", testImage.getDirectory());

    }

    /**
     * Tests update method in ImageFile to see that the name and path are changed after tags are added.
     */

    @Test
    public void testUpdate() {
        String currentDir = System.getProperty("user.dir");
        ImageFile testImage = new ImageFile(currentDir + "/TestingFiles1/dog.jpeg");
        testImage.getTagManager().addTag("cute");
        testImage.getTagManager().addTag("happy");
        testImage.getTagManager().addTagsVersion();
        testImage.update();
        assertEquals("dog @cute @happy.jpeg", testImage.getName());
        assertEquals(currentDir + "/TestingFiles1/dog @cute @happy.jpeg", testImage.getPath());

    }

    /**
     * Tests update method in ImageFile to see that no exception is raised when it is called twice in a row.
     */

    @Test
    public void testUpdateNoChange() {
        String currentDir = System.getProperty("user.dir");
        ImageFile testImage = new ImageFile(currentDir + "/TestingFiles1/happy.jpeg");
        testImage.getTagManager().addTag("cute");
        testImage.getTagManager().addTagsVersion();
        testImage.update();
        testImage.update();
        assertEquals("happy @cute.jpeg", testImage.getName());
        assertEquals(currentDir + "/TestingFiles1/happy @cute.jpeg", testImage.getPath());

    }


    /**
     * Tests getAllNames method in ImageFile to see that it returns the correct ArrayList after one file is updated.
     */

    @Test
    public void testGetAllNames() {
        String currentDir = System.getProperty("user.dir");
        ImageFile testImage = new ImageFile(currentDir + "/TestingFiles1/white.jpeg");
        testImage.getTagManager().addTag("cute");
        testImage.getTagManager().addTag("fluffy");
        ArrayList<String> testAllNames = new ArrayList<String>();
        testAllNames.add("white.jpeg");
        testImage.update();
        testAllNames.add("white @cute @fluffy.jpeg");
        assertEquals(testAllNames, testImage.getAllNames());


    }

    /**
     * Tests getAllNames method in ImageFile to see that it returns the correct ArrayList after multiple files are updated.
     */

    @Test
    public void testGetAllNamesMultipleUpdates() {
        String currentDir = System.getProperty("user.dir");
        ImageFile testImage = new ImageFile(currentDir + "/TestingFiles1/labrador.jpeg");
        ArrayList<String> testAllNames = new ArrayList<String>();
        testImage.getTagManager().addTag("white");
        testImage.getTagManager().addTag("puppy");
        testImage.getTagManager().addTagsVersion();
        testImage.update();
        testImage.getTagManager().addTag("cute");
        testImage.getTagManager().addTagsVersion();
        testImage.update();
        testAllNames.add("labrador.jpeg");
        testAllNames.add("labrador @white @puppy.jpeg");
        testAllNames.add("labrador @white @puppy @cute.jpeg");
        assertEquals(testAllNames, testImage.getAllNames());
    }

    /**
     * Tests revert method in ImageFile to see that the name and path of the file is changed after we revert to an
     * older name.
     */

    @Test
    public void testRevert() {
        String currentDir = System.getProperty("user.dir");
        ImageFile testImage = new ImageFile(currentDir + "/TestingFiles1/run.jpeg");
        testImage.getTagManager().addTag("active");
        testImage.getTagManager().addTagsVersion();
        testImage.update();
        testImage.getTagManager().addTag("dog");
        testImage.getTagManager().addTag("cute");
        testImage.getTagManager().addTagsVersion();
        testImage.update();
        testImage.revert("run @active.jpeg");
        assertEquals("run @active.jpeg", testImage.getName());


    }

    /**
     * Tests revert method in ImageFile to see that the name and path do not get changed when reverting to a name that
     * was not a previous name.
     */
    @Test
    public void testRevertNotAnOldName() {
        String currentDir = System.getProperty("user.dir");
        ImageFile testImage = new ImageFile(currentDir + "/TestingFiles1/family.jpg");
        testImage.getTagManager().addTag("multiple");
        testImage.getTagManager().addTag("puppy");
        testImage.getTagManager().addTagsVersion();
        testImage.update();
        testImage.revert("family @multiple.jpg");
        assertEquals("family @multiple @puppy.jpg", testImage.getName());
        assertEquals(currentDir + "/TestingFiles1/family @multiple @puppy.jpg", testImage.getPath());


    }


    /**
     * Tests moveToNewDirectory method to see that after it is called the path is changed.
     */

    @Test
    public void testMove() {
        String currentDir = System.getProperty("user.dir");
        ImageFile testImage = new ImageFile(currentDir + "/TestingFiles1/TestingFiles1 Sub/bulldog.jpg");
        testImage.moveToNewDirectory(currentDir + "/TestingFiles1");
        assertEquals(currentDir + "/TestingFiles1/bulldog.jpg", testImage.getPath());

    }


}