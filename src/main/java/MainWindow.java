import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320*3, 320*3+25);
        setLocation(400, 0);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args){
        MainWindow mw = new MainWindow();
    }

}
