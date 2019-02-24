import javafx.scene.layout.BorderWidths;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * The main panel representing the game environemnt itself, it also takes care
 * of the animation of the balls and their destroying.
 */
class BallsPanel extends JPanel implements BPInterface {

    private int refreshInterval = 1000; //how often will new balls be created in ms.
    private BallHandler handler; //handler, which creates new balls
    public static final int DIMENSION = 400; //dimension of the square environenment
    private boolean paused = false; //is the game paused right now?

    /**
     * Constructor creates the ball handler and draws the game environment. It
     * also becames a mouse listener.
     */
    public BallsPanel() {
        handler = new BallHandler(refreshInterval, DIMENSION);
        this.setPreferredSize(new Dimension(DIMENSION, DIMENSION));
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5, true));
        BallsData.getInstance().register(this);
        this.addMouseListener(this);
    }

    /**
     * The functions starts or stops the animation.
     */
    public void animate(boolean an) {
        if (an) {
            handler.start();
            paused = false;
        } else {
            handler.stop();
            paused = true;
        }
    }

    /**
     * The functions restarts the animation and clears data.
     */
    public void restart() {
        handler.restart();
        BallsData.getInstance().clear();
    }

    /**
     * The functions ends game and clears data.
     */
    public void endGame() {
        handler.endGame();
        BallsData.getInstance().clear();
    }

    /**
     * Main painting function, it draws the borders and all balls in the given graphics environment.
     * graphics The given graphics environment.
     */
    public void paintComponent(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        //paints the borders
        super.paintComponent(g2d);
        //and all balls
        for (Ball ball : BallsData.getInstance().getData()) {
            g2d.setColor(ball.getColor());
            ball.draw(g2d);
        }
    }

    /**
     * This method is called, when BallsData experiences a data change. It
     * means, that the View should repaint its graphics based on the data
     * change.
     */
    @Override
    public void dataChange() {
        //repaint indirectly calls the paintComponent()
        repaint();
    }

    /**
     * Main function which represents "hitting" the ball.
     */
    @Override
    synchronized public void mousePressed(MouseEvent e) {
        //hitting the balls is allowed only when the game is not paused
        if (!paused) {
            //look to all balls, if the coordinates of the click are inside one
            for (Ball ball : BallsData.getInstance().getData()) {
                Ellipse2D oval = ball.getOval();
                if (oval.contains(e.getX(), e.getY())) {
                    //if it is so, destroy the ball
                    ball.setAlive(false);
                    BallsData.getInstance().removeBall(ball, false);
                }
            }
        }
    }

    /**
     * Only mouse action, which I need to listen to is pressing, so other event
     * handlers do nothing.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Only mouse action, which I need to listen to is pressing, so other event
     * handlers do nothing.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Only mouse action, which I need to listen to is pressing, so other event
     * handlers do nothing.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Only mouse action, which I need to listen to is pressing, so other event
     * handlers do nothing.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}