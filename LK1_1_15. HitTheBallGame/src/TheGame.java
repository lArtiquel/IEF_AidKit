import javax.swing.*;

public class TheGame extends JApplet {

    public void init() {
        this.setContentPane(GamePanel.getInstance());
    }

    public static void main(String[] args) {
        TheGame applet = new TheGame();
        JFrame window = new JFrame("LK2_1_1. Hit the ball game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * window is resizable
         * but the Game window has all the time same size
         * (otherwise it would be unfair to other players)
         */
        window.setSize(450, 560);
        window.setContentPane(applet);
        applet.init();
        window.setVisible(true);
    }
}
