import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class GamePanel extends JPanel {

    private BallsPanel bp; //main game environment
    private JLabel score;  //text label with score
    private JLabel missedScore; //missed info text info label
    private static GamePanel instance; //instance of this singleton class

    /**
     * Constructor is private,
     * so only one instance will exist - singleton class.
     */
    private GamePanel() {
        //create the components of the game
        bp = new BallsPanel();
        /* 
         * main game environment is in the GridBagLayout, so it will
         * be always the same size and always in the middle
         */
        JPanel outer = new JPanel(new GridBagLayout());
        outer.add(bp);

        //buttons creation
        JPanel buttonpanel = new JPanel(new FlowLayout());
        JButton start = new JButton("Start");
        JButton pause = new JButton("Pause");
        JButton restart = new JButton("Restart");
        JButton endGame = new JButton("End Game");

        //labels creation
        missedScore = new JLabel("");
        missedScore.setFont(new Font("Arial", Font.PLAIN, 15));
        score = new JLabel("");
        score.setFont(new Font("Arial", Font.PLAIN, 15));

        //add action listeners to the buttons
        start.addActionListener(new Start());
        pause.addActionListener(new Pause());
        restart.addActionListener(new Restart());
        endGame.addActionListener(new EndGame());

        //add buttons to the button panel
        buttonpanel.add(start);
        buttonpanel.add(pause);
        buttonpanel.add(restart);
        buttonpanel.add(endGame);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        infoPanel.add(score);
        infoPanel.add(missedScore);

        //add components to the app
        this.setLayout(new BorderLayout());
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(outer, BorderLayout.CENTER);
        this.add(buttonpanel, BorderLayout.SOUTH);
    }

    /**
     * The instance of the class is received by this static method.
     */
    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    /**
     * Returns the label with score information.
     */
    public JLabel getScoreInfo() {
        return score;
    }
    public JLabel getMissedScoreInfo() { return missedScore; }

    /**
     * Inner class, which serves as the Action listener for pause button.
     */
    class Pause implements ActionListener {

        /**
         * Pause causes the game animations to pause.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            bp.animate(false);
        }
    }

    /**
     * Inner class, which serves as the Action listener for start button.
     */
    class Start implements ActionListener {

        /**
         * Start will start the game or continue after the pause.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            bp.animate(true);
            score.setText("Balls hit: 0 || ");
            missedScore.setText("Balls missed: 0");
        }
    }

    /**
     * Inner class, which serves as the Action listener for restart button.
     */
    class Restart implements ActionListener {

        /**
         * Restart causes the game to start again.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            bp.restart();
            score.setText("Balls hit: 0 || ");
            missedScore.setText("Balls missed: 0");

        }
    }

    /**
     * Ends the game.
     */
    class EndGame implements ActionListener{
        /**
         * Clean previous data
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            bp.endGame();
            missedScore.setText("");
            score.setText("");
        }
    }
}
