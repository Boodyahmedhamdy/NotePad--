package notepad;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Notepad extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new NotepadFXMLBase();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("NotePad--");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
