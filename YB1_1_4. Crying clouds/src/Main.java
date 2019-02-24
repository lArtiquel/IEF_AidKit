import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main extends JFrame{

    private BufferedImage buffImg1, buffImg2;

    private int cloudWidth = 1000, cloudHeight = 0, snowBankWidth = 1000, snowBankHeight = 0, snowBankY = 400;
    private int[] snowflakeX_Y = {100, 150, 250, 210, 440, 140, 520, 180, 600, 320, 700, 400, 800, 385};

    private static Image background;
    private static Image snowflake;
    private static Image snowbank;
    private static Image cloud;
    JButton bt;

    public Main() {

        setTitle("Crying clouds");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        // Background() - панель, для которой переопределен paintComponent с фоном
        setContentPane(new Background()); // панель устанавливается как contentPane в наследнике JFrame

        Container content = getContentPane(); //теперь все элементы интерфейса будут на этой панели.

        bt = new JButton("Start");

        bt.setPreferredSize(new Dimension(80,30));
        bt.setBackground(Color.white);
        bt.setForeground(Color.BLACK);

        bt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cloudHeight = 150;      // return params
                snowBankHeight = 200;
                snowBankY = 400;

                bt.setVisible(false);

                Thread snowMove = new Thread(new SnowThread());
                snowMove.start();
                Thread snowMove1 = new Thread(new SnowFlakeThread());
                snowMove1.start();
            }
        });

        content.add(bt, BorderLayout.CENTER);
        content.add(new CloudSnowbank());
    }

    private static class Background extends JPanel{ // отрисовка нового фона

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            try {
                background = ImageIO.read(new File("images/background.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(background,0,0,null);
        }
    }

    private class CloudSnowbank extends JPanel{

        public CloudSnowbank() {
            setOpaque(false);
            setPreferredSize(new Dimension(1000, 600));
            try {
                cloud = ImageIO.read(new File("images/cloud.png"));
                snowbank= ImageIO.read(new File("images/snowbank.png"));
                snowflake = ImageIO.read(new File("images/snowflake.png"));
            }
            catch (IOException exc) {
                System.out.println("IO exception!");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D)g;
            graphics2D.drawImage(cloud, 0, 0, cloudWidth, cloudHeight, this);
            graphics2D.drawImage(snowbank, 0,snowBankY, snowBankWidth, snowBankHeight, this);
            graphics2D.drawImage(snowflake, snowflakeX_Y[0], snowflakeX_Y[1], 25, 25, this);
            graphics2D.drawImage(snowflake, snowflakeX_Y[2], snowflakeX_Y[3], 55, 55, this);
            graphics2D.drawImage(snowflake, snowflakeX_Y[4],snowflakeX_Y[5], 35, 35, this);
            graphics2D.drawImage(snowflake, snowflakeX_Y[6],snowflakeX_Y[7], 45, 45, this);
            graphics2D.drawImage(snowflake, snowflakeX_Y[8],snowflakeX_Y[9], 35, 35, this);
            graphics2D.drawImage(snowflake, snowflakeX_Y[10],snowflakeX_Y[11], 45, 45, this);
            graphics2D.drawImage(snowflake, snowflakeX_Y[12],snowflakeX_Y[13], 25, 25, this);
        }

    }

    public class SnowThread implements Runnable{

        @Override
        public void run() {

            while (cloudHeight > 0) {
                cloudHeight -= 2;
                snowBankHeight += 4;
                snowBankY -= 4;
                repaint();
                try {
                    Thread.sleep(300);
                }
                catch (Exception exc) {
                    System.out.println("Thread interrupted!");
                }
            }
            bt.setVisible(true);    //return params
        }
    }

    public class SnowFlakeThread implements Runnable{

        @Override
        public void run() {
            while (cloudHeight > 0) {
                for(int i=0; i<snowflakeX_Y.length; i++){
                    if(i%2==0){
                        snowflakeX_Y[i] = ThreadLocalRandom.current().nextInt(0, cloudWidth + 1);       // snowflake x coordinate in array
                    } else{
                        snowflakeX_Y[i] = ThreadLocalRandom.current().nextInt(cloudHeight, snowBankY + 1);    // snowflake y coordinate in array
                    }
                }
                try {
                    Thread.sleep(300);
                }
                catch (Exception exc) {
                    System.out.println("Thread interrupted!");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Main().setVisible(true);
    }
}


//import javafx.application.Application;
//import javafx.stage.Stage;
//import javafx.scene.control.*;
//import javafx.scene.layout.BorderPane;
//import javafx.geometry.Insets;
//import javafx.scene.layout.Priority;
//import javafx.stage.Stage;
//import javafx.scene.Scene;
//import javafx.stage.Modality;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.image.ImageView;
//import javafx.scene.image.Image;
//
//public class Main extends Application {
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage stage) {
//
//        BorderPane root = new BorderPane();
//        ImageView iv = new ImageView();
//        Image cloud = new Image("file:images/cloud.png");
//        Image snowflake = new Image("file:images/snowflake.png");
//        Image snowdraft = new Image("file:images/snowdraft.png");
//        //root.getChildren().addAll();
//        root
//        Button start = new Button("Start");
//        start.setOnAction(new EventHandler<ActionEvent>(){
//
//            public void handle(ActionEvent e){
//                start.setDisable(true);
//
//
//            }
//        });
//
//        Scene scene = new Scene(root, 700, 700);
//        stage.setScene(scene);
//
//        stage.setTitle("YB1_1_4. Crying clouds.");
//
//        stage.show();
//    }
//}
