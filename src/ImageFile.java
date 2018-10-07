import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.TreeMap;

/**
 * Class for image files.
 */
public class ImageFile extends File implements Serializable {

    private String name;
    private String path;
    private String directory;
    private TagManager tagManager;
    private ArrayList<String> nameLog;
    private ArrayList<String> allNames;
    private String originalName;

    /**
     * Constructor for ImageFile.
     *
     * @param path path directory for the image
     */

    public ImageFile(String path) {
        super(path);
        this.tagManager = new TagManager();
        this.path = path;
        int indexOfLastSlash = this.getPath().lastIndexOf(File.separatorChar);
        int indexOfPeriod = this.getPath().lastIndexOf(".");
        this.directory = this.getPath().substring(0, indexOfLastSlash);
        this.originalName = this.getPath().substring(indexOfLastSlash + 1, indexOfPeriod);
        this.name = this.getPath().substring(indexOfLastSlash + 1);
        nameLog = new ArrayList<String>();
        allNames = new ArrayList<String>();
        allNames.add(this.getName());
    }

    /**
     * Returns the name including image type of this ImageFile.
     *
     * @return name of this ImageFile
     */

    @Override
    public String getName() {

        return this.name;
    }


    /**
     * Returns the original name without the image type of this ImageFile.
     *
     * @return the original name of this ImageFile
     */
    public String getOriginalName() {
        return this.originalName;
    }

    /**
     * Returns the current directory of the ImageFile.
     *
     * @return
     */
    public String getDirectory() {
        int indexOfLastSlash = this.getPath().lastIndexOf(File.separatorChar);
        return this.getPath().substring(0, indexOfLastSlash);

    }

    /**
     * Returns the path of this ImageFile.
     */
    @Override
    public String getPath() {
        return this.path;
    }


    /**
     * Updates the name and path of this ImageFile to reflect current tags. Moves the ImageFile to
     * correct path location. Updates the log of names nameLog with the new name and time stamp.
     * Updates the ArrayList allNames with the new name.
     */
    public void update() {

        String oldName = this.getName();
        String oldPath = this.getPath();

        String newName = this.originalName;

        for (Tag tag : this.getTagManager()) {

            newName = newName + " " + tag.getName();
        }

        String newPath = this.getDirectory() + File.separatorChar + newName + "." + this.getImageType();
        newName = newName + "." + this.getImageType();
        this.path = newPath;
        this.name = newName;

        try {
            Files.move(Paths.get(oldPath), Paths.get(newPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!(allNames.contains(this.getName()))) {
            allNames.add(this.getName());
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        nameLog.add(
                "Old Name: " + oldName + ", " + "New Name: " + newName + ", " + "Time Stamp:" + timestamp
                        .toString());

    }

    /**
     * Moves the ImageFile to new directory. Updates the path name of the ImageFile.
     *
     * @param directory new directory of ImageFile
     */
    public void moveToNewDirectory(String directory) {

        String oldPath = this.getPath();
        String newPath = directory + File.separatorChar + this.getName();
        this.path = newPath;

        try {
            Files.move(Paths.get(oldPath), Paths.get(newPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Returns the TagManager for this ImageFile.
     *
     * @return the TagManager for this ImageFile
     */
    public TagManager getTagManager(){
        return this.tagManager;
    }

    /**
     * Returns the image type (jpg, gif, png etc) of this ImageFile.
     *
     * @return the image type of this ImageFile
     */

    public String getImageType() {
        int indexOfPeriod = this.getPath().lastIndexOf(".");
        return this.getPath().substring(indexOfPeriod + 1, this.getPath().length());
    }


    /**
     * Returns an ArrayList of all the names the ImageFile has ever had.
     *
     * @return an ArrayList of all the names for the ImageFile
     */
    public ArrayList<String> getAllNames() {
        return allNames;
    }


    /**
     * Returns an ArrayList that includes all the names the ImageFile had and time stamps for every
     * time the name of the ImageFile changed.
     *
     * @return ArrayList of names and time stamps
     */
    public ArrayList<String> getNameLog() {
        return nameLog;
    }


    public void revert(String oldName) {
        if (this.getAllNames().contains(oldName)) {
            int indexOfPeriod = oldName.lastIndexOf(".");
            oldName = oldName.substring(0, indexOfPeriod);
            String[] tagNames = oldName.split("@");

            ArrayList<String> oldTagNames = new ArrayList<>();

            for (int i = 1; i < tagNames.length; i = i + 1) {
                tagNames[i] = tagNames[i].trim();
                oldTagNames.add("@" + tagNames[i]);
            }

            TreeMap<Integer, ArrayList<Tag>> tagHistory = tagManager.getTagsVersions();
            ArrayList<Tag> previousTags = new ArrayList<Tag>();
            boolean flag = false;

            for (Integer i : tagHistory.keySet()) {
                if (tagHistory.get(i).size() == oldTagNames.size() && !flag) {
                    flag = true;

                    for (int j = 0; j < oldTagNames.size(); j++) {
                        if (!(tagHistory.get(i).get(j).getName()).equals(oldTagNames.get(j))) {
                            flag = false;
                        }
                    }

                    if (flag) {
                        previousTags = tagHistory.get(i);
                    }
                }
            }

            if (flag || oldTagNames.size() == 0) {
                this.getTagManager().setTags(previousTags);
                this.getTagManager().addTagsVersion();
                this.update();
            }
        }
    }


}