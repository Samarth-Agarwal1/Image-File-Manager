import javafx.scene.control.Button;



/**
 * Class responsible for communication between View and Model.
 */
public class Controller {

    private View view;

    /**
     * Constructor for Controller class
     *
     * @param view View class
     */
    public Controller(View view) {
        this.view = view;
    }

    /**
     * Calls on the View to open a new window showing the path of the image,
     * all the tags of the image, the universal database of all tags and the
     * list of images in the directory etc.
     *
     */

    public void enterStartAction() {
        view.setFirstWindow();


    }

    /**
     * If tagName is not empty and if the tagName has not been entered,
     * adds the tag to the image present and the universal tag database.
     *
     * @param tagName tag name entered after clicking the addTag menu
     */

    public void addTagAction(String tagName){
        if(!tagName.isEmpty()) {
            String oldName = view.getCurrentImage().getPath();
            view.getCurrentImage().getTagManager().addTag(tagName);
            addTagToTagDatabaseAction(tagName);
            view.getCurrentImage().getTagManager().addTagsVersion();
            view.getCurrentImage().update();
            view.setPathPanelBox(view.getCurrentImage().getPath());
            view.setNewImage(oldName, view.getCurrentImage().getPath());
            if (tagName.length() != 0) {
                view.setNewTag(tagName);
            }
        }

    }

    /**
     * Calls the View class and uploads the image as the current image the user is working on.
     * imageFileClicked  is set as the current image, and the boolean value isNewImageBeingEntered
     * is entered to make sure that the View class knows if there is an image present currently on the pane.
     *
     * @param imageFileClicked the buttons containing the path of the imageFileClicked
     * @param isNewImageBeingEntered gives a true or false value stating if there is an image
     */
    public void imageButtonAction(Button imageFileClicked, Boolean isNewImageBeingEntered) {
        view.getCurrentImageFile(imageFileClicked);
        view.setImagePanel(view.getCurrentImage(), isNewImageBeingEntered);


    }

    /**
     * Calls the view and updates the right panel to show the new images of the directory.
     * Also Moves the file to a new directory.
     *
     * @param newDirectory the new directory entered in the textfield under moveFile
     * */
    public void moveFileAction(String newDirectory) {
        view.getCurrentImage().moveToNewDirectory(newDirectory);
        view.setAllImages(ImageFileManager.findImages(newDirectory));
        view.listOfImagesMethod(true);
    }

    /**
     * Calls view and changes the right panel to all the image names relating to the newImage.
     *
     * @param newImage a String showing what the user would like to see
     */
    public void searchImageAction(String newImage) {
        view.setDirectoryChangeMode(true);
        view.setAllImages(ImageFileManager.search(newImage));
        view.listOfImagesMethod(false);
    }

    /**
     * Calls View and changes the right hand panel to all the images in the new directory
     *
     * @param newDirectory the new directory that the user wants to be able to shift the image to.
     */
    public void changeDirectoryAction(String newDirectory) {
        view.setAllImages(ImageFileManager.findImages(newDirectory));
        view.listOfImagesMethod(false);
    }


    /**
     * Calls the view to change the path, list of tags of the image, and the right hand side panel to show images.
     * Also changes the tags of the image.
     *
     * @param newName the new name of the image that the user wants to revert to.
     */
    public void revertFileAction (String newName){
        String oldName = view.getCurrentImage().getPath();
        view.getCurrentImage().revert(newName);
        view.setPathPanelBox(view.getCurrentImage().getPath());
        view.setRevertBackFileTags();
        view.setNewImage(oldName, view.getCurrentImage().getPath());
    }

    /**
     * Calls the view when the delete tag is entered and removed  the tag entered if it is present.
     * Also changes the path of the image.
     *
     * @param tagToBeDeleted tag name selected by the user to be deleted
     */
    public void deleteTagAction (String tagToBeDeleted){
        String oldName = view.getCurrentImage().getPath();
        view.getCurrentImage().getTagManager().deleteTag(tagToBeDeleted);
        view.getCurrentImage().getTagManager().addTagsVersion();
        view.getCurrentImage().update();
        view.setPathPanelBox(view.getCurrentImage().getPath());
        view.setRemoveTag(tagToBeDeleted);
        view.setNewImage(oldName, view.getCurrentImage().getPath());
    }

    /**
     * Calls the View and adds a tags to the universal list of all tags ever created
     * if a similar tag is not already present.
     *
     * @param tagName the name of the new tag to be attached to the database of all tags.
     */

    public void addTagToTagDatabaseAction(String tagName) {
        if (tagName.length() != 0) {
            Tag.addTagToAllTags(tagName);
            view.setAddFromSelectTag(tagName);

        }

    }

    /**
     * Calls the View and deletes a tags from the universal list of all tags ever created.
     *
     * @param tagName the name of the tag to be deleted, selected by the user
     */
    public void deleteTagFromTagDatabaseAction(String tagName) {
        view.getCurrentImage().getTagManager().deleteTag(tagName);
        view.setRemoveFromSelectTag(tagName);
        Tag.deleteTagFromAllTags(tagName);
        view.getCurrentImage().getTagManager().addTagsVersion();

    }

    /**
     * Calls the View and adds a tag from the universal database of tags to tags of the image
     * that is in focus on the screen.
     *
     * @param tagName the name of added from the universal database to the tags of the image
     */
    public void retrieveTagFromTagDatabaseAction(String tagName) {
        for (Tag tag : Tag.getAllTags()) {
            if (tag.getName().equals(tagName)) {
                String oldName = view.getCurrentImage().getPath();
                view.getCurrentImage().getTagManager().addTag(tagName);
                view.getCurrentImage().getTagManager().addTagsVersion();
                view.getCurrentImage().update();
                view.setPathPanelBox(view.getCurrentImage().getPath());
                view.setNewImage(oldName, view.getCurrentImage().getPath());

            }
        }
        view.setRetrieveFromSelectTag(tagName);
    }

    /**
     * Updates View when the add button is pressed in ActionZone or the first interface of editing buttons seen.
     */
    public void addTagToHZone() {
        view.setAddZoneButton();
    }

    /**
     * Updates View when the MoveFile button is pressed in ActionZone or the first interface of editing buttons seen.
     */
    public void moveFileToHZone() {
        view.setMoveFileButton();
    }

    /**
     * Updates View when the searchImage button is pressed in ActionZone or the first interface of editing buttons seen.
     */
    public void searchImageToHZone() {
        view.setActionTime("search image");
    }

    /**
     * Updates View when the ChangeDirectory button is pressed in ActionZone
     * or the first interface of editing buttons seen.
     */
    public void enterDirectoryToHZone() {
        view.setActionTime("enter directory");
    }

    /**
     * Updates View when the revert/Logs button is pressed in ActionZone or the first interface of editing buttons seen.
     *
     * @param newMode the mode of button selection so that that user can perform multiple actions with the same buttons.
     */
    public void revertFileToHZone(String newMode) {
        view.setMode(newMode);
        view.setRevertToPreviousAction();
    }

    /**
     * Method called when the delete button is pressed in ActionZone or the first interface of editing buttons seen.
     *
     * @param newMode the mode of button selection so that that user can perform multiple actions with the same buttons.
     */
    public void deleteTagToHZone(String newMode) {
        view.setMode(newMode);
        view.setDeleteZoneButton();
    }

    /**
     * Method called when the retrieve button is pressed in ActionZone or the first interface of editing buttons seen.
     *
     * @param newMode the mode of button selection so that that user can perform multiple actions with the same buttons.
     */
    public void retrieveTagToHZone(String newMode) {
        view.setMode(newMode);
        view.setRetrieveZoneButton();
    }

    /**
     * Updates View to add a Tag to the universal button database when a new button  is clicked and
     * changes mode to newMode.
     *
     * @param newMode the mode of button selection so that that user can perform multiple actions with the same buttons.
     */
    public void selectAddTagToHZone(String newMode) {
        view.setMode(newMode);
        view.setAddSelectZoneButton();
    }

    /**
     * Updates View to delete a Tag from the universal database of all tags present, also updates the mode of the
     * folder.
     *
     * @param newMode the mode of button selection so that that user can perform multiple actions with the same buttons.
     */
    public void selectDeleteTagToHZone(String newMode) {
        view.setMode(newMode);
        view.setDeleteSelectZoneButton();
    }

    /**
     * Updates View to change the HZone to the action Zone, which is the first submenu that the user sees.
     */
    public void goBackButton() {
        if(view.isThereAnImageViewed()) {
            view.getCurrentImage().getTagManager().addTagsVersion();
        }
        view.setBackToAction();
    }

    /**
     * Updates View return the bottom left part of the panel from the add submenu
     * to the previous 'Add Universal Tag' and'Delete Universal Tag' seen by the user while first opening the window.
     */
    public void goBackAddSelectButton() {
        view.goBackSelectUniButton();
    }

    /**
     * Updates View return the bottom left part of the panel from the delete submenu
     * to the previous 'Add Universal Tag' and'Delete Universal Tag' seen by the user while first opening the window.
     */
    public void goBackDeleteSelectButton() {
        view.goBackDeleteSelectUniButton();

    }

    /**
     * Updates View to change the right panel to all the images found by the user before the search term was entered,
     * also changes the bottom HZone, which is used to enter a term to the previous submenu, actionZone.
     */
    public void goBackSearch() {

        view.setDirectoryChangeMode(false);
        if(view.isThereAnImageViewed()) {
            view.setAllImages(ImageFileManager.findImages(view.getCurrentImage().getDirectory()));
            view.listOfImagesMethod(false);
        }
        else{
            view.setAllImages(ImageFileManager.findImages(view.getPreviousDirectoryEntered()));
            view.listOfImagesMethod(false);
        }
        view.setBackToAction();
    }

    /**
     * Method called when the back button is pressed in HZone for the Revert/Logs button
     */
    public void goBackRevertButton() {
        view.setSelectRevertBackToAction();

    }

    /**
     * Updates View when either the universal tag buttons, the tag buttons for the image or the revert
     * log buttons are pressed. This method reroutes button pressed depending upon the mode.
     *
     * @param mode the mode of the program at the point of pressing a button
     * @param button the string of the button pressed
     */

    public void reRoute(String mode, String button) {
        if (mode.equals("select add")) {
            addTagToTagDatabaseAction(button);
        } else if (mode.equals("select delete")) {
            deleteTagFromTagDatabaseAction(button);
        } else if (mode.equals("delete")) {
            deleteTagAction(button);
        } else if (mode.equals("retrieve")) {
            retrieveTagFromTagDatabaseAction(button);
        } else if (mode.equals("revert logs")) {
            int comma = button.indexOf(",");
            button = button.substring(10, comma);
            revertFileAction(button);
        }

    }


}




