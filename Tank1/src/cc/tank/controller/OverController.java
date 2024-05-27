package cc.tank.controller;

import cc.tank.Director;
import cc.tank.util.SoundEffect;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
public class OverController {


        @FXML
        private ImageView flag;

        @FXML
        private ImageView toIndex;

        @FXML
        void mouseClickedToIndex(MouseEvent event) {
                Director.getInstance().toIndex();
        }

        @FXML
        void mouseEnterToIndex(MouseEvent event) {
                toIndex.setOpacity(0.8);
        }

        @FXML
        void mouseExitToIndex(MouseEvent event) {
                toIndex.setOpacity(1);
        }

        public void flagSuccess(){
                flag.setImage(new Image("image/success.png"));
        }
}

