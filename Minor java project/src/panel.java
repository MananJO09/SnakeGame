import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {
    static int width =1200;
    static int height =600;
    static int unit=50;
    // to specify time in which new frame is displayed
    Timer timer;
    //To generate food item in randomly
    Random random;

    int foodx, foody;
    int score;
    int length=3;
    char direction='R';
    boolean flag= false;// for specifying if the game is ended or not
    //when flag is false we would be on the game over location, and if flag==true , snake will do the working
    static int delay=160;

    int xsnake[] = new int[288];
    int ysnake[] = new int[288];

    panel(){
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);

        //this command allows keyboard input to be processed
        this.setFocusable(true);
        random = new Random();

        //creating abstract class for input processing
        this.addKeyListener(new myKey());

        gamestart();
    }

    public void gamestart(){
        spawnfood();
        flag=true; // Game is started
        timer= new Timer(delay,this);
        timer.start();

    }

    public void spawnfood(){
        foodx= random.nextInt(width/unit)* unit;
        foody= random.nextInt(height/unit)*unit;
    }

    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void draw(Graphics graphic ){

        if(flag== true){
            graphic.setColor(Color.RED);
            graphic.fillOval(foodx, foody, unit, unit);

            for(int i=0; i<length; i++){
                if(i==0){
                    graphic.setColor(Color.orange);
                }
                else {
                    graphic.setColor(Color.GREEN);
                }
                graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
            }

            graphic.setColor(Color.cyan);
            graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
            FontMetrics f= getFontMetrics(graphic.getFont());
            graphic.drawString("Score:"+score, (width-f.stringWidth("Score:"+score))/2, graphic.getFont().getSize());

        }
        else{
            gameover(graphic);
        }
    }

    public void gameover(Graphics graphic){
        //to display the score
        graphic.setColor(Color.cyan);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics f= getFontMetrics(graphic.getFont());
        graphic.drawString("Score:"+score, (width-f.stringWidth("Score:"+score))/2, graphic.getFont().getSize());

        //to display the game-over text
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
        FontMetrics f2= getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER!", (width-f2.stringWidth("GAME OVER!"))/2, height/2);

        //to display the replay prompt
        graphic.setColor(Color.green);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        graphic.drawString("Press R to replay", (width-f.stringWidth("Press R to replay"))/2, height/2+150);

    }

    public void checkhit(){
        if(xsnake[0]<0){
            flag =false;
        }
        else if (xsnake[0]>1200) {
            flag =false;
        }
        else if(ysnake[0]<0){
            flag = false;
        }
        else if (ysnake[0]>600) {
            flag = false;
        }

        for(int i=length; i>0; i--){
            if((xsnake[0]==xsnake[i]) && (ysnake[0]==ysnake[i])){
                flag = false;
            }
        }

        if(!flag){
            timer.stop();
        }

    }

    public void eat(){
        if((xsnake[0]==foodx) && (ysnake[0]==foody)){
            length++;
            score++;
            spawnfood();
        }
    }

    public void move(){
        for(int i=length; i>0; i--){
            xsnake[i]= xsnake[i-1];
            ysnake[i]= ysnake[i-1];
        }

        switch (direction) {
            case 'R':
                xsnake[0] = xsnake[0] + unit;
                break;
            case 'L':
                xsnake[0] = xsnake[0] - unit;
                break;
            case 'D':
                ysnake[0] = ysnake[0]+unit;
                break;
            case 'U':
                ysnake[0] =ysnake[0]-unit;
                break;
        }
    }

    public class myKey extends KeyAdapter {
        public void keyPressed(KeyEvent evt){
            switch (evt.getKeyCode()){
                case KeyEvent.VK_UP:
                    if(direction !='D'){
                    direction ='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction !='U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(direction !='R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction !='L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!flag){
                        score=0;
                        length=3;
                        direction='R';
                        Arrays.fill(xsnake, 0);
                        Arrays.fill(ysnake, 0);

                        gamestart();
                    }
                    break;
            }
        }
    }

    public void actionPerformed(ActionEvent e){

        if(flag){
            move();
            eat();
            checkhit();
        }
        repaint();
    }
}
