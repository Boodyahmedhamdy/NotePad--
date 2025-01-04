package notepad;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NotepadFXMLBase extends BorderPane {

    protected final MenuBar menuBar;
    protected final Menu fileMenu;
    protected final MenuItem newMenuItem;
    protected final MenuItem openMenuItem;
    protected final MenuItem saveMenuItem;
    protected final MenuItem exitMenuItem;
    protected final Menu editMenu;
    protected final MenuItem cutMenuItem;
    protected final MenuItem copyMenuItem;
    protected final MenuItem pasteMenuItem;
    protected final MenuItem deleteMenuItem;
    protected final MenuItem selectAllMenuItem;
    protected final Menu menu;
    protected final MenuItem menuItem;
    protected final TextArea mainTextArea;
    
    boolean isSaved = false;

    public NotepadFXMLBase() {

        menuBar = new MenuBar();
        fileMenu = new Menu();
        newMenuItem = new MenuItem();
        openMenuItem = new MenuItem();
        saveMenuItem = new MenuItem();
        exitMenuItem = new MenuItem();
        editMenu = new Menu();
        cutMenuItem = new MenuItem();
        copyMenuItem = new MenuItem();
        pasteMenuItem = new MenuItem();
        deleteMenuItem = new MenuItem();
        selectAllMenuItem = new MenuItem();
        menu = new Menu();
        menuItem = new MenuItem();
        mainTextArea = new TextArea();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(744.0);
        setPrefWidth(1050.0);

        BorderPane.setAlignment(menuBar, javafx.geometry.Pos.CENTER);
        menuBar.setPrefHeight(7.0);
        menuBar.setPrefWidth(589.0);

        fileMenu.setMnemonicParsing(false);
        fileMenu.setText("File");

        newMenuItem.setMnemonicParsing(false);
        newMenuItem.setText("New");
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(isSaved) {
                    mainTextArea.clear();
                    isSaved = false;
                } else {
                    // handle not saved
                    System.out.println("current file isn't saved, save to create new file");
                }
            }
        });

        openMenuItem.setMnemonicParsing(false);
        openMenuItem.setText("Open");
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        
        // open -- lowLevel
        /*
        openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(null);
                FileInputStream fileInputStream = null;
                if(file != null) {
                    try {
                        fileInputStream = new FileInputStream(file);
                        int size = fileInputStream.available();
                        byte[] bytes = new byte[size];
                        fileInputStream.read(bytes);
                        mainTextArea.setText(new String(bytes));
                        
                    } catch(FileNotFoundException ex) {
                        System.out.println("File Not found");
                    } catch(IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            fileInputStream.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                   
                    
                }
            }
        });
        */
        // open highLevel
        openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(null);
                FileInputStream fileInputStream = null;
                DataInputStream dataInputStream = null;
                if(file != null) {
                    try {
                        fileInputStream = new FileInputStream(file);
                        dataInputStream = new DataInputStream(fileInputStream);
                        mainTextArea.setText(dataInputStream.readUTF());

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(NotepadFXMLBase.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(NotepadFXMLBase.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            dataInputStream.close();
                        } catch (IOException ex) {
                            Logger.getLogger(NotepadFXMLBase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });

        saveMenuItem.setMnemonicParsing(false);
        saveMenuItem.setText("Save");
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        
        // Save using low level stream
        /*
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showSaveDialog(null);
                
                if(file != null) {
                    FileOutputStream fileOutputStream = null;
                    try {
                        System.out.println(file.getName());
                        fileOutputStream = new FileOutputStream(file);
                        byte[] bytes = mainTextArea.getText().getBytes();
                        fileOutputStream.write(bytes);
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(NotepadFXMLBase.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(NotepadFXMLBase.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fileOutputStream.close();
                        } catch (IOException ex) {
                            Logger.getLogger(NotepadFXMLBase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                }
            }
        });
        */
        
        // save -- high level
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                if(!isSaved) {
                    FileChooser fileChooser = new FileChooser();
                    File file = fileChooser.showSaveDialog(null);
                    FileOutputStream fileOutputStream = null;
                    DataOutputStream dataOutputStream = null;

                    if(file != null) {
                        try {
                            fileOutputStream = new FileOutputStream(file);
                            dataOutputStream = new DataOutputStream(fileOutputStream);

                            dataOutputStream.writeUTF(mainTextArea.getText());
                            isSaved = true;
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(NotepadFXMLBase.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(NotepadFXMLBase.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {
                                dataOutputStream.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                
           
            }
        });
        exitMenuItem.setMnemonicParsing(false);
        exitMenuItem.setText("Exit");
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(isSaved)
                    Platform.exit();
                else 
                    System.out.println("You need to save before closing");
            }
        });
        
        editMenu.setMnemonicParsing(false);
        editMenu.setText("Edit");

        cutMenuItem.setMnemonicParsing(false);
        cutMenuItem.setText("Cut");
        cutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        cutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainTextArea.cut();
            }
        });

        copyMenuItem.setMnemonicParsing(false);
        copyMenuItem.setText("Copy");
        copyMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        copyMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainTextArea.copy();
            }
        });

        pasteMenuItem.setMnemonicParsing(false);
        pasteMenuItem.setText("Paste");
        pasteMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        pasteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainTextArea.paste();
            }
        });

        deleteMenuItem.setMnemonicParsing(false);
        deleteMenuItem.setText("Delete");
        deleteMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainTextArea.deleteText(mainTextArea.getSelection());
                
            }
        });

        selectAllMenuItem.setMnemonicParsing(false);
        selectAllMenuItem.setText("Select All");
        selectAllMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        selectAllMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainTextArea.selectAll();
            }
        });
        

        menu.setMnemonicParsing(false);
        menu.setText("Help");

        menuItem.setMnemonicParsing(false);
        menuItem.setText("About");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("./AboutScreen.fxml"));
                Parent root = new AboutScreenBase();
                Stage stage = new Stage();
                stage.setTitle("About");
                stage.setScene(new Scene(root));
                stage.show();
            }
        });
        setTop(menuBar);
       
        
        

        BorderPane.setAlignment(mainTextArea, javafx.geometry.Pos.CENTER);
        mainTextArea.setPrefHeight(200.0);
        mainTextArea.setPrefWidth(200.0);
        mainTextArea.setPromptText("Start Typing.....");
        setCenter(mainTextArea);

        fileMenu.getItems().add(newMenuItem);
        fileMenu.getItems().add(openMenuItem);
        fileMenu.getItems().add(saveMenuItem);
        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);
        editMenu.getItems().add(cutMenuItem);
        editMenu.getItems().add(copyMenuItem);
        editMenu.getItems().add(pasteMenuItem);
        editMenu.getItems().add(deleteMenuItem);
        editMenu.getItems().add(selectAllMenuItem);
        menuBar.getMenus().add(editMenu);
        menu.getItems().add(menuItem);
        menuBar.getMenus().add(menu);


    }
}
