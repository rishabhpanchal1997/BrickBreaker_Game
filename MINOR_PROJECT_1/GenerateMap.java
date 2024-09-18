package MINOR_PROJECT_1;

import java.awt.*;

public class GenerateMap {

    //CLASS FOR CREATING BRICKS

    int[][] map; //map for all bricks --> 2D array defined
    int brickwidth; //width of a brick
    int brickheight; //height of a brick


    //When this function is called --> 2D array created
    //Size of the 2D array will be based on the values of rows and columns
    GenerateMap(int rows, int cols){

        //map has number of bricks = rows*cols
        map = new int[rows][cols]; //2D array initialised
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                map[i][j]=1; //when ball is not hit --> value = 1 -> initially all bricks are not broken
            }
        }

        brickheight = 150/rows;
        brickwidth = 540/cols;
    }

    //When this method is called --> bricks will be created as per 2D array [MAP]
    //This uses Graphics2D
    public void makeBricks(Graphics2D g){

        //to generate the bricks
        for(int i=0; i<map.length; i++){
            for(int j=0; j<map[0].length; j++){

                //if brick has been hit --> do not generate that brick
                if(map[i][j]>0) {

                    g.setColor(Color.red);//color of the bricks generated

                    //filled rectangle of mentioned dimension generated
                    //We have set top left brick at location at x=80 and y=80 since at starting i=j=0
                    //note that position of x-axis and y-axis increase and decrease (first two parameters)
                    // and not the brick size (last two parameters )
                    g.fillRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);

                    //Stroking – is a process of drawing a shape’s outline applying
                    //stroke width, line style, and color attribute
                    g.setStroke(new BasicStroke(3.0f));//border of the brick

                    g.setColor(Color.BLACK);//color of the border

                    //The drawRect method draws a rectangle outline for the given position and size.
                    g.drawRect(j*brickwidth+80,i*brickheight+50,brickwidth,brickheight);
                }
            }
        }
    }
    public void setValue(int i,int j, int val){
        map[i][j]=val;
    }
}
