import javax.swing.*;

public class frame extends JFrame{
    frame() {
        //adding the panel to the window
        this.add(new panel());
        // adding a title to the window
        this.setTitle("Snake Game");
        // to make sure uniform game size
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
    }


}
