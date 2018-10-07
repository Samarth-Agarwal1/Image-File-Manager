import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Class responsible for managing ImageFile objects.
 */
public class ImageFileManager {


    private static ArrayList<ImageFile> imageFiles = ReadWrite.deserializeImageFiles();

    /**
     * For each image in the format jpg, jpeg, gif, png and bmp in the directory creates an ImageFile
     * of those images, adds each ImageFile to  an ArrayList<ImageFile> imagesInDirectory, updates the
     * static ArrayList<ImageFile> imageFiles with all the ImageFiles created, returns
     * imagesInDirectory
     *
     * @param directory directory of the imageFiles
     * @return ArrayList<ImageFile> of images in the directory
     */
    public static ArrayList<ImageFile> findImages(String directory) {

        File folder = new File(directory);
        File[] files = folder.listFiles();
        ArrayList<ImageFile> imagesInDirectory = new ArrayList<ImageFile>();

        if (files != null) {
            for (int i = 0; i < files.length; i = i + 1) {

                if (files[i].isFile()) {
                    String name = files[i].getName();
                    String path = files[i].getPath();
                    int indexOfPeriod = files[i].getName().lastIndexOf(".");
                    String type = name.substring(indexOfPeriod + 1, files[i].getName().length());
                    Boolean present = false;
                    int index = -1;
                    for (int count = 0; count < imageFiles.size(); count++) {
                        if (imageFiles.get(count).getPath().equals(path)) {
                            present = true;
                            index = count;
                            count = imageFiles.size() + 1;

                        }
                    }
                    if (type.equalsIgnoreCase("jpeg") || type.equalsIgnoreCase("jpg") ||
                            type.equalsIgnoreCase("gif") || type.equalsIgnoreCase("png") ||
                            type.equalsIgnoreCase("bmp")) {
                        ImageFile image = new ImageFile(path);
                        if (present) {
                            imagesInDirectory.add(imageFiles.get(index));
                        } else {

                            imageFiles.add(image);
                            imagesInDirectory.add(image);
                        }

                    }
                } else if (files[i].isDirectory()) {

                    ArrayList<ImageFile> imagesInSubdirectory = findImages(files[i].getPath());
                    for (ImageFile imageFile : imagesInSubdirectory) {

                        if (!imagesInDirectory.contains(imageFile)) {

                            imagesInDirectory.add(imageFile);

                            if (!imageFiles.contains(imageFile)) {
                                imageFiles.add(imageFile);
                            }

                        }
                    }
                }
            }
        }
        return imagesInDirectory;
    }


    /**
     * Returns the ArrayList<ImageFile> imageFiles containing all ImageFiles ever created.
     *
     * @return the ArrayList containing all the ImageFiles
     */
    public static ArrayList<ImageFile> getImageFiles() {
        return imageFiles;
    }


    public static ArrayList<ImageFile> search(String keyword) {


        ArrayList<ImageFile> searchResults = new ArrayList<ImageFile>();

        for (ImageFile imageFile : imageFiles) {
            if (imageFile.getName().contains(keyword)) {

                searchResults.add(imageFile);
            } else {

                for (Tag tag : imageFile.getTagManager()) {

                    if (tag.getName().equalsIgnoreCase(keyword)) {
                        searchResults.add(imageFile);
                    }
                }
            }

        }


        return searchResults;
    }


}