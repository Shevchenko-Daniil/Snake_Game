import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    private final int SIZE = 320*3; //размер игровой панели
    private final int DOT_SIZE = 16*3;  //размер одной точки на поле
    private int ALL_DOTS = SIZE*SIZE/(DOT_SIZE*DOT_SIZE);  //максимальное количесвто точек на панели
    private Image dot;  // изображение куска змейки
    private Image apple;  // изображение яблока
    private int appleX;  //координата Х яблока
    private int appleY;  //Координата Y яблока
    private int[] x = new int[ALL_DOTS];  //массив координат Х
    private int[] y = new int[ALL_DOTS];  //массив координат Y
    private int dots; // количество кусков змейки
    private Timer timer;
    private boolean left = false;  //направление влево
    private boolean right = true;  //направление вправо
    private boolean up = false;  //направление вверх
    private boolean down = false;  //направление вниз
    private boolean inGame = true;  //нахождение в игре

    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){

        dots = 3;
        for(int i =0; i < dots; i++){
            x[i] = DOT_SIZE*3 - i*DOT_SIZE;
            y[i] = DOT_SIZE*3;
        }
        timer = new Timer(200, this);
        timer.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }
    public void loadImages(){
        ImageIcon iia = new ImageIcon("src/main/resources/beer.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("src/main/resources/matvey.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple, appleX, appleY, this);
            for(int i = 0; i < dots; i++){
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        else{
            String str = "Game Over";
            Font f = new Font("Elephant", Font.BOLD, 28);
            g.setColor(Color.red);
            g.setFont(f);
            g.drawString(str, SIZE/2 - SIZE/8, SIZE/2);
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        }
        if(down){
            y[0] += DOT_SIZE;
        }
        if(up){
            y[0] -= DOT_SIZE;
        }
    }

    public void checkApple(){
        if(x[0]==appleX && y[0]==appleY){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        for(int i = dots; i > 0; i--){
            if(dots > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();

    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                up = true;
                right = false;
                left = false;
            }

            if(key == KeyEvent.VK_DOWN && !up){
                down = true;
                left = false;
                right = false;
            }
        }
    }


}
