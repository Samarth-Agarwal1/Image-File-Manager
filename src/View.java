import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.paint.Color.DARKGREY;

import java.io.FileInputStream;

public class View extends Application {

    private Button enterButton;
    private Button addZoneButton = new Button("Add Tag");
    private Button moveFileButton = new Button("Move File");
    private Button revertLogsButton = new Button("Revert/Logs");
    private Button deleteZoneButton = new Button("Delete Tag");
    private Button retrieveZoneButton = new Button("Retrieve Tag");
    private Button searchImageButton = new Button("Search Image");
    private Button enterOtherDirectoryButton = new Button("Change Directory");
    private Button backButton = new Button("Back");
    private Button revertBackButton;
    private Button selectBackButton = new Button("Back");
    private Button searchImageBackButton = new Button("Back");
    private Button makeNewTagButton;
    private Button addSelectAddTagButton;
    private Button moveFileTagButton;
    private Button addSearchImageButton;
    private Button addEnterOtherDirectoryButton;
    private Button selectAddTagButton = new Button("Add Universal Tag");
    private Button selectDeleteTagButton = new Button("Delete Universal Tag");
    private TextField enterDirectoryText = new TextField();;
    private TextField newTagNameText;
    private TextField searchImageText;
    private TextField selectTagNameText;
    private TextField moveFileText;
    private Stage stage;
    private Scene firstScene;
    private Scene newScene;
    private Image image;
    private ImageView imageView;
    private ImageView imagePanel;
    private ArrayList<Button> buttonChoiceList;
    private ArrayList<ImageFile> allImages;
    private StackPane imagePanelBox = new StackPane();
    private VBox listOfImages = new VBox();
    private VBox previousFileNames = new VBox();
    private VBox selectButtons = new VBox();
    private HBox previousTagUnivNames = new HBox();
    private HBox tagsPanelBox = new HBox();
    private HBox pathPanelBox = new HBox();
    private HBox actionZone = new HBox();
    private HBox ZoneHBox = new HBox();
    private HBox enterSelectHBox = new HBox();
    private ScrollPane scrollableListOfImages = new ScrollPane();
    private ScrollPane scrollableListOfTags = new ScrollPane();
    private ScrollPane scrollableListOfImagePath = new ScrollPane();
    private ScrollPane scrollableListOfAllTags = new ScrollPane();
    private SplitPane imageAndActionPanel = new SplitPane();
    private Controller controller;
    private SplitPane sidePanel;
    private Canvas imageListCanvas;
    private Canvas imageSelectListCanvas;
    private static ImageFile currentImage;
    private static String mode = "none";
    private static boolean newDirectoryBeingEntered = false;


    public static void main(String[] args) {
        ReadWrite.createInitialFiles();
        launch(args);
    }

    /**
     * Calls for the user to enter a directory path that the program takes and uses to display the list of
     * images in this directory, the universal tag database, and the image path and tags.
     *
     * @param primaryStage the stage that every scene and gui component is displayed on
     */

    @Override
    public void start(Stage primaryStage) {
        controller = new Controller(this);
        stage = primaryStage;
        stage.setTitle("Photoviewer");
        HBox horizontalLayout = new HBox();
        Label file_d = new Label("Please enter your file path directory:");
        enterDirectoryText.setPromptText("Enter Path");
        enterDirectoryText.setFocusTraversable(false);
        enterDirectoryText.setOnAction(evt -> controller.enterStartAction());

        enterButton = new Button("Enter");
        enterButton.setOnAction(evt -> controller.enterStartAction());


        horizontalLayout.getChildren().addAll(file_d, enterDirectoryText, enterButton);
        horizontalLayout.setSpacing(10);
        horizontalLayout.setAlignment(Pos.CENTER);
        horizontalLayout.setPadding(new Insets(10, 10, 10, 10));
        HBox.setMargin(file_d, new Insets(10));
        HBox.setMargin(enterButton, new Insets(10));
        firstScene = new Scene(horizontalLayout, 700, 200);
        stage.setScene(firstScene);
        stage.setTitle("PhotoViewer");
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Return the pack.ImageFile that was clicked on the list of all images displayed
     *
     * @param  imagePressed: the button that was pressed.
     */

    public ImageFile getCurrentImageFile(Button imagePressed) {
        int numIndexButton = buttonChoiceList.indexOf(imagePressed);
        currentImage = allImages.get(numIndexButton);
        return allImages.get(numIndexButton);
    }

    /* Returns the imageFile that represents the images we are working with in the program.
     *
     */

    public ImageFile getCurrentImage() {
        return currentImage;
    }

    /* Returns a boolean value stating whether there is an image present in the viewing area
     *
     */
    public Boolean isThereAnImageViewed() {
        return imagePanelBox.getChildren().size() >= 1;
    }

    /* Returns the previous directory that was entered by the user so that the user can go back to the directory
     * after searching for a key work in Search Image
     */
    public String getPreviousDirectoryEntered() {
        return enterDirectoryText.getCharacters().toString();
    }

    /**
     * Change the Canvas on the top right corner of the second Scene so that the user knows what is going on in the
     * application and has a clear understanding of what is needed of them.
     *
     * @param canvasString: The text that is displayed on the Canvas
     */
    public void changeCanvas(String canvasString) {
        imageSelectListCanvas = new Canvas(newScene.getWidth() * 0.35, newScene.getHeight() * 0.15);
        GraphicsContext graphics = imageSelectListCanvas.getGraphicsContext2D();

        graphics.strokeText(canvasString, imageSelectListCanvas.getWidth() * 0.2,
                imageSelectListCanvas.getHeight() / 2);
        graphics.setStroke(DARKGREY);
        sidePanel.getItems().remove(selectButtons);
        sidePanel.getItems().remove(scrollableListOfImages);
        sidePanel.getItems().remove(imageListCanvas);
        sidePanel.getItems().addAll(imageSelectListCanvas, scrollableListOfImages, selectButtons);
        sidePanel.setDividerPosition(0, .15);
        sidePanel.setDividerPosition(1, .91);

    }


    /**
     * The GUI change when the enter button for the Add/ Select/ Delete/Move File/Revert Logs/
     * Search Image /Change Directory buttons have been selected.
     *
     * @param mode states whether a Add/ Select/ Delete/Move File/Revert Logs/
     * Search Image /Change Directory button has been pressed
     */

    public void setActionTime(String mode) {
        for (int a = ZoneHBox.getChildren().size() - 1; a >= 0; a--) {
            ZoneHBox.getChildren().remove(a);
        }
        imageAndActionPanel.getItems().remove(scrollableListOfAllTags);
        imageAndActionPanel.getItems().remove(actionZone);
        backButton.setOnAction(evt -> controller.goBackButton());

        if (mode.equals("retrieve")) {
            ZoneHBox.getChildren().addAll(backButton);
        } else if (mode.equals("add")) {
            Label addTagLabel = new Label("New tag:");
            newTagNameText = new TextField();
            makeNewTagButton = new Button("Enter");
            newTagNameText.setPromptText("Tag name");
            newTagNameText.setFocusTraversable(false);
            newTagNameText.setOnAction(evt -> controller.addTagAction(newTagNameText.getCharacters().toString()));
            makeNewTagButton.setOnAction(evt -> controller.addTagAction(newTagNameText.getCharacters().toString()));
            ZoneHBox.getChildren().addAll(addTagLabel, newTagNameText, makeNewTagButton, backButton);
        } else if (mode.equals("delete")) {
            ZoneHBox.getChildren().addAll(backButton);
        } else if (mode.equals("move")) {
            Label moveFileTagLabel = new Label("New directory:");
            moveFileText = new TextField();
            moveFileTagButton = new Button("Enter");
            moveFileText.setPromptText("New directory");
            moveFileText.setFocusTraversable(false);
            moveFileText.setOnAction(evt -> controller.moveFileAction(moveFileText.getCharacters().toString()));
            moveFileTagButton.setOnAction(evt -> controller.moveFileAction(moveFileText.getCharacters().toString()));
            ZoneHBox.getChildren().addAll(moveFileTagLabel, moveFileText, moveFileTagButton, backButton);
        }
        else if (mode.equals("enter directory")) {
            Label enterNewDirectoryTagLabel = new Label("Change to directory:");
            addEnterOtherDirectoryButton = new Button("Enter");
            enterDirectoryText.clear();
            enterDirectoryText.setOnAction(evt -> controller.changeDirectoryAction
                    (enterDirectoryText.getCharacters().toString()));
            addEnterOtherDirectoryButton.setOnAction(evt -> controller.changeDirectoryAction
                    (enterDirectoryText.getCharacters().toString()));
            ZoneHBox.getChildren().addAll(enterNewDirectoryTagLabel, enterDirectoryText,
                    addEnterOtherDirectoryButton, backButton);
        }
        else if (mode.equals("search image")) {
            Label searchImageLabel = new Label("Search Image:");
            searchImageBackButton.setOnAction(evt -> controller.goBackSearch());
            searchImageText = new TextField();
            addSearchImageButton = new Button("Enter");
            searchImageText.setPromptText("Enter Image Name");
            searchImageText.setFocusTraversable(false);
            searchImageText.setOnAction(evt -> controller.searchImageAction(searchImageText.getCharacters().toString()));
            addSearchImageButton.setOnAction(evt -> controller.searchImageAction
                    (searchImageText.getCharacters().toString()));
            ZoneHBox.getChildren().addAll(searchImageLabel, searchImageText, addSearchImageButton,
                    searchImageBackButton);
        } else {
            revertBackButton = new Button("Back");
            revertBackButton.setOnAction(evt -> controller.goBackRevertButton());
            changeCanvas("Log Of All Past Saved Files\n (Click a button to revert)");
            scrollableListOfImages.setContent(previousFileNames);
            previousFileNames.setSpacing(5);
            ZoneHBox.getChildren().addAll(revertBackButton);

        }

        ZoneHBox.setSpacing(10);
        ZoneHBox.setAlignment(Pos.CENTER);
        imageAndActionPanel.getItems().addAll(ZoneHBox, scrollableListOfAllTags);
        imageAndActionPanel.setDividerPosition(0, .65);
        imageAndActionPanel.setDividerPosition(1, .71);
        imageAndActionPanel.setDividerPosition(2, .77);
        imageAndActionPanel.setDividerPosition(3, .90);
    }

    /**
     * The UI changed when a picture is selected from the list of images in the directory.
     *
     * @param image the imageFile that has the path of the button selected
     * @param isNewImageBeingEntered a boolean value stating whether a image is present or not
     */

    public void setImagePanel(ImageFile image, Boolean isNewImageBeingEntered) {
        try {
            if (imagePanelBox.getChildren().size() >= 1) {
                imagePanelBox.getChildren().remove(imagePanelBox.getChildren().get(0));
            }
            FileInputStream input = new FileInputStream(image.getPath());
            imagePanel = new ImageView(new Image(input));
            imagePanel.setPreserveRatio(true);
            imagePanel.setFitWidth(newScene.getWidth() * 0.62);
            imagePanel.setFitHeight(newScene.getHeight() * 0.68);
            imagePanelBox.setMaxSize(newScene.getWidth() * 0.65, newScene.getHeight() * 0.70 );
            imagePanelBox.setPadding(new Insets(10, 10, 10, 20));
            imagePanelBox.getChildren().add(imagePanel);
            imagePanelBox.setAlignment(Pos.CENTER);
            imageAndActionPanel.setDividerPosition(0, .65);
            imageAndActionPanel.setDividerPosition(1, .71);
            imageAndActionPanel.setDividerPosition(2, .77);
            imageAndActionPanel.setDividerPosition(3, .90);
            for (int a = tagsPanelBox.getChildren().size() - 1; a > 0; a--) {
                tagsPanelBox.getChildren().remove(a);
            }
            for (Tag tag : currentImage.getTagManager()) {
                tagsPanelBox.getChildren().add(new Button(tag.getName()));
                ((Button) (tagsPanelBox.getChildren().get(tagsPanelBox.getChildren().size() - 1))).
                        setOnAction(evt -> controller.reRoute(mode, ((Button) (evt.getSource())).getText()));
            }
            setPathPanelBox(image.getPath());

            // if we are changing the directory that is being used.
            if(isNewImageBeingEntered){
                setAllImages(ImageFileManager.findImages(getCurrentImage().getDirectory()));
                listOfImagesMethod(false);
            }

            input.close();
        } catch (IOException fileNotFound) {
            //http://code.makery.ch/blog/javafx-dialogs-official/ used this website to uderstand and derive alert
            // code from
            fileNotFound.printStackTrace();

        }
    }

    /**
     * Updates the GUI to the previous submenu on the bottom left corner which contains two buttons vertically arranged
     */
    public void goBackSelectUniButton() {
        for (int count = enterSelectHBox.getChildren().size() - 1; count >= 0; count--) {
            enterSelectHBox.getChildren().remove(count);
        }
        setMode("none");
        sidePanel.getItems().remove(enterSelectHBox);
        sidePanel.getItems().addAll(selectButtons);
        sidePanel.setDividerPosition(1, .90);
    }

    /**
     * Updates the GUI to the previous submenu on the bottom left corner which contains two buttons vertically arranged,
     * when the back button is pressed for a universal tag delete
     */
    public void goBackDeleteSelectUniButton() {
        setMode("none");
        selectButtons.getChildren().remove(0);
        selectButtons.getChildren().addAll(selectAddTagButton, selectDeleteTagButton);

    }

    /**
     * Updates the GUI to the previous menus where the Add/ Select/ Delete/Move File/Revert Logs/
     * Search Image /Change Directory buttons are shown and the list of tags is also shown.
     */
    public void setBackToAction() {
        setMode("none");
        imageAndActionPanel.getItems().remove(scrollableListOfAllTags);
        imageAndActionPanel.getItems().remove(ZoneHBox);
        imageAndActionPanel.getItems().addAll(actionZone, scrollableListOfAllTags);
        imageAndActionPanel.setDividerPosition(0, .65);
        imageAndActionPanel.setDividerPosition(1, .71);
        imageAndActionPanel.setDividerPosition(2, .77);
        imageAndActionPanel.setDividerPosition(3, .90);

    }

    /**
     * Updates the GUI to the previous menus where the Add/ Select/ Delete/ Move File/ Revert Logs/
     * Search Image / Change Directory buttons are shown and the list of tags is also shown. Also remove all
     * three elements from the  right hand side panel to show the images present
     */
    public void setSelectRevertBackToAction() {
        scrollableListOfImages.setContent(listOfImages);
        sidePanel.getItems().remove(selectButtons);
        sidePanel.getItems().remove(scrollableListOfImages);
        sidePanel.getItems().remove(imageSelectListCanvas);
        sidePanel.getItems().addAll(imageListCanvas, scrollableListOfImages, selectButtons);
        sidePanel.setDividerPosition(0, .15);
        sidePanel.setDividerPosition(1, .91);
        setBackToAction();
    }


    /**
     * Updates the GUI to the previous menus where the Add/ Select/ Delete/ Move File/ Revert Logs/
     * Search Image / Change Directory buttons are shown and the list of tags is also shown. Also remove all
     * three elements from the  right hand side panel to show the images present.
     *
     * @param  path: the path of the current image
     */
    public void setPathPanelBox(String path) {
        if(pathPanelBox.getChildren().size() >1) {
            pathPanelBox.getChildren().remove(1);
        }
        pathPanelBox.getChildren().add(new Button(path));
    }


    /**
     * Change the mode of the program to a different mode so that the program
     * does different things when buttons for universal Tags, Tags for the image and revert logs are pressed.
     *
     * @param newMode: A different mode for the buttons that have been clicked
     */
    public void setMode(String newMode) {
        mode = newMode;
    }

    /**
     * Change the directory mode so that a new directory can be added.
     *
     * @param newMode: a true or false value stating if the user is going into a new directory from the search
     *               Image menu.
     */
    public void setDirectoryChangeMode(Boolean newMode) {
        newDirectoryBeingEntered = newMode;
    }

    /**
     * Updates the GUI when the user presses the Revert/logs button.
     */
    public void setRevertToPreviousAction() {
        if(imagePanelBox.getChildren().size() >= 1){
            for(int count = previousFileNames.getChildren().size()-1; count >= 0; count--){
                previousFileNames.getChildren().remove(count);
            }
            for(int count = 0; count < currentImage.getNameLog().size(); count++){
                previousFileNames.getChildren().add(new Button(currentImage.getNameLog().get(count)));
                ((Button) (previousFileNames.getChildren().get(previousFileNames.getChildren().size() - 1))).
                        setOnAction(evt -> controller.reRoute(mode, ((Button) (evt.getSource())).getText()));
            }
            setActionTime("revert");
        }
    }

    /**
     * Sets  the arrayList allImages to a different arraylist of ImageFiles, listOfImages
     *
     * @param listOfImages: a list of imageFiles
     */
    public void setAllImages(ArrayList<ImageFile> listOfImages) {
        allImages = listOfImages;
    }

    /**
     * Set the GUI when the Move File button is selected
     */
    public void setMoveFileButton() {
        if (imagePanelBox.getChildren().size() >= 1) {
            setActionTime("move");
        }
    }

    /**
     * Set the GUI when the Retrieve button is selected
     */
    public void setRetrieveZoneButton() {
        if (imagePanelBox.getChildren().size() >= 1) {
            setActionTime("retrieve");
        }
    }

    /**
     * Set the GUI when the Delete button is selected in the first menu
     */
    public void setDeleteZoneButton() {
        if (imagePanelBox.getChildren().size() >= 1) {
            setActionTime("delete");
        }
    }

    /**
     * Set the Gui when the Add button is selected
     */
    public void setAddZoneButton() {
        if (imagePanelBox.getChildren().size() >= 1) {
            setActionTime("add");
        }
    }

    /**
     * Updates the GUI when the Delete universal Tag button is pressed to be able to click on other buttons and delete
     * them.
     */
    public void setDeleteSelectZoneButton() {
        selectButtons.getChildren().remove(0);
        selectButtons.getChildren().remove(0);
        selectButtons.getChildren().add(selectBackButton);
        selectBackButton.setOnAction(evt -> controller.goBackDeleteSelectButton());
    }

    /**
     * Updates the GUI when the Add universal Tag button is pressed to be able to add a Tag through a Textfield and
     * add a Tag to the universal tag database.
     */
    public void setAddSelectZoneButton() {
        Label addUniversalTag = new Label("New universal tag:");
        selectTagNameText = new TextField();
        addSelectAddTagButton = new Button("Enter");
        selectTagNameText.setPromptText("Tag name");
        selectTagNameText.setFocusTraversable(false);
        addSelectAddTagButton.setOnAction(evt -> controller.addTagToTagDatabaseAction(selectTagNameText.getCharacters()
                .toString()));
        selectTagNameText.setOnAction(evt -> controller.addTagToTagDatabaseAction(selectTagNameText.getCharacters()
                .toString()));
        enterSelectHBox.getChildren().addAll(addUniversalTag, selectTagNameText, addSelectAddTagButton, selectBackButton);
        enterSelectHBox.setSpacing(5);
        enterSelectHBox.setPadding(new Insets(3, 3, 3, 3));
        enterSelectHBox.setAlignment(Pos.CENTER);
        sidePanel.getItems().remove(selectButtons);
        sidePanel.setDividerPosition(0, .15);
        sidePanel.setDividerPosition(1, .90);
        sidePanel.getItems().add(enterSelectHBox);

        selectBackButton.setOnAction(evt -> controller.goBackAddSelectButton());
    }

    /* Updates the GUI when the enter button is selected in the inner submenu of the Add Tag button to display a new
     * tag for the image.
     *
     * @param addTag: The new tag that is to be created.
     */

    public void setNewTag(String addTag) {
        Boolean alreadyThere = false;
        if (addTag.charAt(0) != '@') {
            addTag = "@" + addTag;
        }
        for (int count = 0; count < tagsPanelBox.getChildren().size(); count++) {
            if (((Button) (tagsPanelBox.getChildren().get(count))).getText().equals(addTag)) {
                alreadyThere = true;
            }
        }
        if (!alreadyThere) {
            tagsPanelBox.getChildren().add(new Button(addTag));
            ((Button)(tagsPanelBox.getChildren().get(tagsPanelBox.getChildren().size()-1))).
                    setOnAction(evt -> controller.reRoute(mode,((Button)(evt.getSource())).getText()));
        }
    }

    /**
     * Updates the GUI to remove a tag if the tag is present in the imageFile, one the tag button for deletion
     * has been clicked
     *
     * @param removeTag: the string of the tag that has to be removed
     */
    public void setRemoveTag(String removeTag) {
        if (removeTag.charAt(0) != '@') {
            removeTag = "@" + removeTag;
        }

        for (int i = 0; i < tagsPanelBox.getChildren().size(); i++) {
            if ((((Button) (tagsPanelBox.getChildren().get(i))).getText()).equals(removeTag)) {
                tagsPanelBox.getChildren().remove(tagsPanelBox.getChildren().get(i));
            }
        }

    }

    /**
     * Updates the GUI to retrieve a Tag from the Universal list of all Tags and add it to the list of Tags for
     * the image currently being used.
     *
     * @param retrieveTag the string that wil be added to the the list of tags for the image if the Tag is not already
     *                    in the list of Tags of the image
     */

    public void setRetrieveFromSelectTag(String retrieveTag) {
        setNewTag(retrieveTag);
    }

    /**
     * Updates the GUI to remove a tag button in the interface from the universal list of tags
     *
     * @param removeUniversalTag the string that wil be removed from the universal Tag list
     */
    public void setRemoveFromSelectTag(String removeUniversalTag) {
        ArrayList<Tag> allTags = Tag.getAllTags();
        for (int i = 0; i < allTags.size(); i++) {
            if (((Button) previousTagUnivNames.getChildren().get(i + 1)).getText().equals(removeUniversalTag)) {
                previousTagUnivNames.getChildren().remove(i + 1);
                i = allTags.size();
            }
        }

    }

    /**
     * Set the GUI to add a tag button in the interface once the enter button for the select button
     *
     * @param addTag the string that wil be added from the universal Tag list
     */
    public void setAddFromSelectTag(String addTag) {
        Boolean alreadyThere = false;
        if (addTag.charAt(0) != '@') {
            addTag = "@" + addTag;
        }
        for (int count = 0; count < previousTagUnivNames.getChildren().size(); count++) {
            if (((Button) previousTagUnivNames.getChildren().get(count)).getText().equals(addTag)) {
                alreadyThere = true;
            }
        }
        if (!alreadyThere) {
            previousTagUnivNames.getChildren().add(new Button(addTag));
            ((Button) (previousTagUnivNames.getChildren().get(previousTagUnivNames.getChildren().size() - 1))).
                    setOnAction(evt -> controller.reRoute(mode, ((Button) (evt.getSource())).getText()));
        }
    }

    /**
     * Updates the GUI reset the list of tags for a image after the file has been reverted back to an older state.
     *
     */
    public void setRevertBackFileTags() {
        for (int count = tagsPanelBox.getChildren().size() - 1; count > 0; count--) {
            tagsPanelBox.getChildren().remove(count);
        }
        for (Tag tag : currentImage.getTagManager()) {
            tagsPanelBox.getChildren().add(new Button(tag.getName()));
            ((Button)(tagsPanelBox.getChildren().get(tagsPanelBox.getChildren().size()-1))).setOnAction(evt ->
                    controller.reRoute(mode, ((Button) (evt.getSource())).getText()));
        }


    }

    /**
     * Updates the GUI so that after the user has reverted back to an older version of the image file, the oldName
     * on the right hand panel containing every image in a directory side changes to the newName
     *
     * @param oldName the string that wil be old name of the file
     * @param newName the string that wil be old name of the file
     */
    public void setNewImage(String oldName, String newName) {
        for (int i = 0; i < listOfImages.getChildren().size(); i++) {
            if (i % 2 == 1 && (((Button) listOfImages.getChildren().get(i)).getText().equals(oldName))) {
                ((Button)listOfImages.getChildren().get(i)).setText(newName);
            }

        }
    }


    /**
     * Updates the GUI so that after the user has entered the first directory, the program updates to list every image
     * in that directory on the right panel, list ever tag, the Add/ Select/ Delete/ Move File/ Revert Logs/
     * Search Image / Change Directory buttons and the image, image path and image tags all in one window.
     */

    public void setFirstWindow() {
        // got the idea for this code from
        // https://stackoverflow.com/questions/23160573/javafx-stage-setoncloserequest-without-function
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ReadWrite.serializeImageFiles(ImageFileManager.getImageFiles());
            }
        });
        {
        }
        SplitPane parent_layout = new SplitPane();
        parent_layout.setOrientation(Orientation.HORIZONTAL);
        newScene = new Scene(parent_layout, 1300, 800);
        addZoneButton.setOnAction(evt -> controller.addTagToHZone());
        deleteZoneButton.setOnAction(evt -> controller.deleteTagToHZone("delete"));
        retrieveZoneButton.setOnAction(evt -> controller.retrieveTagToHZone("retrieve"));
        moveFileButton.setOnAction(evt -> controller.moveFileToHZone());
        revertLogsButton.setOnAction(evt -> controller.revertFileToHZone("revert logs"));
        searchImageButton.setOnAction(evt -> controller.searchImageToHZone());
        enterOtherDirectoryButton.setOnAction(evt -> controller.enterDirectoryToHZone());

        //ended of the check here
        actionZone.getChildren().addAll(addZoneButton, deleteZoneButton, retrieveZoneButton, moveFileButton,
                revertLogsButton, searchImageButton, enterOtherDirectoryButton);
        actionZone.setSpacing(10);
        actionZone.setAlignment(Pos.CENTER);

        //this is the tagsPanel box where everyone will see the tags on the picture
        tagsPanelBox.getChildren().add(new Button("Tags:"));
        scrollableListOfTags.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollableListOfTags.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollableListOfTags.setContent(tagsPanelBox);

        //this is the tagsPanel box where everyone will see the tags on the picture
        pathPanelBox.getChildren().add(new Button("Path of Image:"));
        scrollableListOfImagePath.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollableListOfImagePath.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollableListOfImagePath.setContent(pathPanelBox);

        previousTagUnivNames.getChildren().add(new Button("All Tags Created:"));
        ArrayList<Tag> allTags = Tag.getAllTags();
        for (int i = 0; i < allTags.size(); i++) {
            previousTagUnivNames.getChildren().add(new Button(allTags.get(i).getName()));
            ((Button) (previousTagUnivNames.getChildren().get(i + 1))).
                    setOnAction(evt -> controller.reRoute(mode, ((Button) (evt.getSource())).getText()));

        }
        previousTagUnivNames.setPadding(new Insets(10, 10, 10, 10));
        previousTagUnivNames.setSpacing(2);
        scrollableListOfAllTags.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollableListOfAllTags.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollableListOfAllTags.setContent(previousTagUnivNames);

        imageAndActionPanel.setOrientation(Orientation.VERTICAL);
        imageAndActionPanel.getItems().addAll(imagePanelBox, scrollableListOfImagePath, scrollableListOfTags, actionZone
                , scrollableListOfAllTags);
        imageAndActionPanel.setDividerPosition(0, .65);
        imageAndActionPanel.setDividerPosition(1, .71);
        imageAndActionPanel.setDividerPosition(2, .77);
        imageAndActionPanel.setDividerPosition(3, .90);

        ArrayList<Object> tempArrayList = new ArrayList<Object>();
        allImages = ImageFileManager.findImages(enterDirectoryText.getCharacters().toString());
        listOfImagesMethod(false);


        //https://www.youtube.com/watch?v=IyenGPFjBqw used this to understand how to use scrollPane

        scrollableListOfImages.setContent(listOfImages);
        sidePanel = new SplitPane();
        sidePanel.setOrientation(Orientation.VERTICAL);

        // Used https://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm to understand how to mess with
        // GraphicsContext. Did not copy any code from here though.

        imageListCanvas = new Canvas(newScene.getWidth() * 0.35, newScene.getHeight() * 0.15);
        GraphicsContext graphics = imageListCanvas.getGraphicsContext2D();

        graphics.strokeText("List Of Images from Directory", imageListCanvas.getWidth() * 0.2,
                imageListCanvas.getHeight() / 2);
        graphics.setStroke(DARKGREY);
        selectButtons.getChildren().addAll(selectAddTagButton, selectDeleteTagButton);
        selectAddTagButton.setPrefWidth(newScene.getWidth() * 0.35);
        selectDeleteTagButton.setPrefWidth(newScene.getWidth() * 0.35);
        selectButtons.setSpacing(2);
        selectButtons.setPadding(new Insets(4, 4, 4, 4));
        selectButtons.setAlignment(Pos.CENTER);
        sidePanel.getItems().addAll(imageListCanvas, scrollableListOfImages, selectButtons);
        sidePanel.setDividerPosition(0, .15);
        sidePanel.setDividerPosition(1, .91);
        selectAddTagButton.setOnAction(evt -> controller.selectAddTagToHZone("select add"));
        selectDeleteTagButton.setOnAction(evt -> controller.selectDeleteTagToHZone("select delete"));
        parent_layout.getItems().addAll(imageAndActionPanel, sidePanel);
        parent_layout.setDividerPosition(0, 0.65);

        stage.setScene(newScene);
    }

    /**
     * Sets the GUI to display a list of images on the right hand panel and changing the path of the image depending
     * upon whether the  fileBeingMoved.
     *
     * @param fileBeingMoved dictates whether the file has been moved to another directory.
     */

    public void listOfImagesMethod(Boolean fileBeingMoved) {
        for (int count = listOfImages.getChildren().size() - 1; count >= 0; count--) {
            listOfImages.getChildren().remove(count);
        }
        int numOfImages = allImages.size();
        buttonChoiceList = new ArrayList<Button>(numOfImages);
        listOfImages.setSpacing(10);
        listOfImages.setPadding(new Insets(10, 10, 10, 10));

        if (fileBeingMoved) {
            pathPanelBox.getChildren().remove(1);
            pathPanelBox.getChildren().add(new Button(currentImage.getPath()));
        }

        try {
            for (int i = 0; i < numOfImages; i++) {

                FileInputStream input = new FileInputStream(allImages.get(i).getPath());
                image = new Image(input);
                imageView = new ImageView(image);
                imageView.maxWidth(newScene.getWidth() * 0.35);
                imageView.maxHeight(newScene.getWidth() * 0.35);
                buttonChoiceList.add(new Button(allImages.get(i).getPath()));
                buttonChoiceList.get(i).setOnAction(evt -> controller.imageButtonAction(((Button)(evt.getSource())),
                        newDirectoryBeingEntered));
                listOfImages.getChildren().addAll(imageView, buttonChoiceList.get(i));

                input.close();
            }

        } catch (IOException noFile) {
            //http://code.makery.ch/blog/javafx-dialogs-official/ used this website to uderstand and derive alert
            // code from
            noFile.printStackTrace();
            stage.setScene(firstScene);
        }
    }
}