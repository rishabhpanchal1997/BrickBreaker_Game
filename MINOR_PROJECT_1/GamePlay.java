package MINOR_PROJECT_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play=false; //to acknowledge game status : over or not
    private int score =0; //score of the game
    private int totalbricks =21 ;
    private final Timer timer; //timer is functionality of the swing
    private int playerX =290; //default slider location
    private int ballPosX = 120; //original position of the ball in x direction
    private int ballPosY = 350; //original position of ball in y direction
    private int ballXdir = -1; //original direction of ball in negative X axis side
    private int ballYdir = -4 ; //original direction of ball in negative Y axis side
    private GenerateMap map;

    GamePlay(){
        map = new GenerateMap(3,7); //bricks map with 3 rows and 7 columns

        //keep track on keyboard
        addKeyListener(this);

        setFocusable(true);

        //setFocusTraversalKeysEnabled() decides whether or not focus traversal keys (TAB key, SHIFT+TAB, etc.)
        //are allowed to be used when the current Component has focus
        //It will not allow to change focus(pointing) if we use traversal keys
        setFocusTraversalKeysEnabled(false);


        //A Swing timer (an instance of javax.swing.Timer) fires one or more action events after a specified delay.
        //Do not confuse Swing timers with the general-purpose timer facility in the java.util package.
        //Swing timers are recommended rather than general-purpose timers for GUI-related tasks
        //If you want the timer to go off only once, you can invoke setRepeats(false) on the timer.
        //To start the timer, call its start method. To suspend it, call stop.
        //
        int delay = 8;
        timer = new Timer(delay,this);
        timer.start();
    }

    //initialize the ball, the platform, the bricks

    //IMPORTANT NOTE : When JPanel is added into JFrame, JPanel will automatically search for paint(graphics g) method
    //hence we do not need to call paint method. JPanel will automatically call it
    //if name of the paint method is changed --> it will not be implemented
    //hence careful with name of the paint method
    public void paint(Graphics g){

        //NOTE that g is not 2D object
        //BACKGROUND of the frame
        g.setColor(Color.BLACK);
        g.fillRect(1,1,700,600);

        //BRICKS
        map.makeBricks((Graphics2D) g); //typecast from graphics to graphics2D because it accepts 2D object

        //BORDER
        g.setColor(Color.ORANGE);
        g.fillRect(1,1,700,4); //top border (north)
        g.fillRect(1,1,4,600); //left border (west)
        g.fillRect(681,1,4,600); //right border(east)
        g.setColor(Color.RED);
        g.fillRect(1,560,700,4); //bottom border (south)

        //SCORE
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD,25));
        g.drawString(""+score,590,30);

        //SLIDER TO PLAY BALL
        g.setColor(Color.ORANGE);
        g.fillRect(playerX,550,100,8);

        //BALL
        g.setColor(Color.GREEN);
        g.fillOval(ballPosX,ballPosY,20,20);

        //WHEN BALL TOUCHES THE FLOOR --> GAME OVER
        if(ballPosY>560){
            play=false;
            ballXdir= -1;
            ballYdir= -4;
            g.setColor(Color.WHITE);
            g.setFont(new Font("SANS-SERIF",Font.PLAIN,25));
            g.drawString("GAME OVER !",260,300);
            g.drawString("YOUR SCORE IS : "+score,220,330);
        }

        //WHEN ALL THE BRICKS ARE HIT --> PLAYER WON AND GAME OVER
        if(totalbricks==0){
            play=false;
            ballXdir= -1;
            ballYdir= -4;
            g.setColor(Color.WHITE);
            g.setFont(new Font("SANS-SERIF",Font.PLAIN,25));
            g.drawString("YOU WON !",270,300);
            g.drawString("YOUR SCORE IS : "+score,220,330);
        }



    }

    //FOR ANY INTERACTION OF KEYS OR ACTION --> THESE METHODS WILL BE EXECUTED
    //INTERSECTION OR BALL HITS BRICK ETC.
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        //Only if we are playing then check for collision
        if(play){

            //We can't check oval collision with rectangle
            //We will create rectangle around the ball so that we can check collision
            //rectangle1(ball_data).intersect(rectangle2)

            //if ball collides with platform --> jump back towards bricks --> y direction should reverse
            if(new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir=-ballYdir;
            }

            //if ball collide with bricks
            //first 'map' denotes map variable of this class
            //second 'map' denotes map variable of GenerateMap class

            //loops : this is label given to these nested loops
            DoubleLoops:
            for(int i=0; i<map.map.length; i++){
                for(int j=0; j<map.map[0].length; j++){

                    //if brick has not been hit --> map[i][j]>0
                    //only then we will check interaction of ball with brick
                    if(map.map[i][j]>0){

                        //below are the dimension and position of the brick got hit
                        int brickX = j*map.brickwidth+80;
                        int brickY = i*map.brickheight+50;
                        int brickwidth = map.brickwidth;
                        int brickheight = map.brickheight;

                        //now create rectangle of dimension and size of brick got hit
                        Rectangle brickRect = new Rectangle(brickX,brickY,brickwidth,brickheight);

                        //border rectangle around the ball
                        Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);

                        //now check the intersection of these two rectangle : brick and ballRectangle
                        if(ballRect.intersects(brickRect)){
                            map.setValue(i,j,0); //set that respective 2d array position as 0
                            totalbricks--; //reduce number of total bricks each time ball hits brick
                            score +=5; //increase score value by 5 each time

                            //if ball hits brick horizontally from left or right
                            //rectangle_name.x --> The X coordinate of the upper-left corner of the Rectangle.
                            //rectangle_name.y --> The Y coordinate of the upper-left corner of the Rectangle.
                            if(ballPosX+19<=brickRect.y||ballPosX+1>=brickRect.x+brickwidth){
                                ballXdir=-ballXdir;
                            }

                            else{
                                ballYdir=-ballYdir;
                            }
                            //this will break both the loops simultaneously
                            break DoubleLoops;
                        }
                    }
                }
            }
            //ball position should update in the direction of movement
            ballPosX+=ballXdir;
            ballPosY+=ballYdir;

            //if ball reaches left wall
            if(ballPosX<0){
                ballXdir=-ballXdir;
            }

            if(ballPosX>660){
                ballXdir=-ballXdir;
            }

            if(ballPosY<0){
                ballYdir=-ballYdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        //if user press right key
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            //if slider position is at border(x=5) or less
            //then set slider left corner at x=5
            //it has to be recalculated as slider movement per key press changes
            if(playerX>=555){playerX=580;}
            else{moveRight();}
        }

        //if user press left key
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            //if slider position is at border(x=5) or less
            //then set slider left corner at x=5
            //it has to be recalculated as slider movement per key press changes
            if(playerX<=5){playerX=5;}
            else{moveLeft();}
        }

        //if user press enter key
        if(e.getKeyCode()==KeyEvent.VK_ENTER){

            //ONLY DO ACTION IF GAME IS NOT ON --> reset all the data and position
            if(!play){
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -1;
                ballYdir = -4;
                score = 0;
                playerX = 310;
                totalbricks = 21;

                //we need to recreate all the bricks because either game is over or won by player
                map = new GenerateMap(3,7);
                repaint(); //inbuilt function that calls paint function

            }
        }
    }

    private void moveLeft() {
        play=true;
        playerX-=10; //move the slider left side by 20
    }

    private void moveRight() {
        play=true;
        playerX+=10; //move the slider right side by 20
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
