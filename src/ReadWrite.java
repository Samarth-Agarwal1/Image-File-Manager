import java.io.*;
import java.util.ArrayList;

/**
 * Class responsible for reading from and writing to files for information storage purposes.
 */
public class ReadWrite {

    /*
    Stores the directory where all the files will be located in.
     */
    private static final String folderName = "ProjectFiles";

    /*
    Stores the file name of where all the Tag names will be written to.
     */
    private static final String tagDatabase = folderName + "/tagDatabase.txt";

    /*
    Stores the file name of where all the ImageFile objects will be serialized to.
     */
    private static final String serImageFiles = folderName + "/imageFiles.ser";

    /**
     * Creates the files required for the initial execution of the program.
     */
    public static void createInitialFiles() {
        File dir = new File(folderName);
        File tagDatabaseFile = new File(tagDatabase);
        File serImageFilesFile = new File(serImageFiles);

        dir.mkdir();

        try {
            tagDatabaseFile.createNewFile();
            serImageFilesFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the given tagName to the file where all Tag names are stored.
     *
     * @param tagName name to write to the file
     */
    public static void addTagToDatabase(String tagName) {
        try {
            FileWriter fileWriter = new FileWriter(tagDatabase, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(tagName + "\n");

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns Tag objects of every Tag name in the file where all Tag names
     * are stored.
     *
     * @return Tag objects of every Tag name in the file where all Tag names
     * are stored
     */
    public static ArrayList<Tag> getAllTagsFromDatabase() {
        ArrayList<Tag> allTags = new ArrayList<>();
        String textLine;

        try {
            FileReader fileReader = new FileReader(tagDatabase);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            do {
                textLine = bufferedReader.readLine();

                if (textLine != null) {
                    allTags.add(new Tag(textLine));
                }

            } while (textLine != null);

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allTags;
    }

    /**
     * Removes the given tagName from the file where all Tag names are stored.
     *
     * @param tagName name to remove from the file
     */
    public static void removeTagFromDatabase(String tagName) {
        String tempTagDatabase = folderName + "/tempTagDatabase.txt";
        File tempFile = new File(tempTagDatabase);
        String textLine;

        try {
            FileWriter fileWriter = new FileWriter(tempFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            FileReader fileReader = new FileReader(tagDatabase);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            do {
                textLine = bufferedReader.readLine();

                if (textLine != null && !textLine.equals(tagName)) {
                    bufferedWriter.write(textLine + "\n");
                }

            } while (textLine != null);

            bufferedReader.close();
            fileReader.close();

            bufferedWriter.close();
            fileWriter.close();

            fileWriter = new FileWriter(tagDatabase);
            bufferedWriter = new BufferedWriter(fileWriter);

            fileReader = new FileReader(tempFile);
            bufferedReader = new BufferedReader(fileReader);

            do {
                textLine = bufferedReader.readLine();

                if (textLine != null) {
                    bufferedWriter.write(textLine + "\n");
                }

            } while (textLine != null);

            bufferedReader.close();
            fileReader.close();

            bufferedWriter.close();
            fileWriter.close();

            tempFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes the given imageFiles to a file.
     *
     * @param imageFiles files to serialize
     */
    public static void serializeImageFiles(ArrayList<ImageFile> imageFiles) {
        try {
            // Referenced this website for serialization: https://www.tutorialspoint.com/java/java_serialization.htm
            FileOutputStream fileOutputStream = new FileOutputStream(serImageFiles);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(imageFiles);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the serialized ImageFiles after deserializing them.
     *
     * @return deserialized ImageFiles
     */
    public static ArrayList<ImageFile> deserializeImageFiles() {
        ArrayList<ImageFile> imageFiles = new ArrayList<>();

        try {
            // Referenced this website for deserialization: https://www.tutorialspoint.com/java/java_serialization.htm
            FileInputStream fileInputStream = new FileInputStream(serImageFiles);

            if (fileInputStream.getChannel().size() != 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                imageFiles = (ArrayList<ImageFile>) objectInputStream.readObject();

                objectInputStream.close();
            }

            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return imageFiles;
    }
}