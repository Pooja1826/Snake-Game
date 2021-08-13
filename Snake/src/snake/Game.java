package snake;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game extends JPanel implements ActionListener {
    
    private Image apple;
    private Image dot;
    private Image head;
    
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 1600;
    private final int RANDOM_POSITION = 39;
    
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    private int dots;
    private int apple_x;
    private int apple_y;
    private boolean inGame = true;
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean downDirection = false;
    private boolean upDirection = false;
    
    private Timer timer;
    
    Game(){
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(400,400));
        loadImages();
        initGame();
    }
    
    private void loadImages(){
        ImageIcon i1 =new ImageIcon(getClass().getResource("apple.png"));
        apple = i1.getImage();
        
        ImageIcon i2 =new ImageIcon(getClass().getResource("dot.png"));
        dot= i2.getImage();
        
        ImageIcon i3 =new ImageIcon(getClass().getResource("head.png"));
        head = i3.getImage();
    }
    
    private void initGame(){
        dots=3;
        for(int z=0;z<dots;z++){
            x[z]= 50 - z*DOT_SIZE; // 1 : 50 , 2 : 50-10=40, 3 : 50-20=30
            y[z]=50;
        }
        locateApple();
        timer = new Timer(140,this);
        timer.start();
    }
    
    public void locateApple(){
        
        int r = (int)(Math.random()*RANDOM_POSITION); //0 and 1 : 0.1,0.2,...,0.9 => 0.6*20=12
        apple_x = (r*DOT_SIZE); //12*10=120
        //1*39=39*10=390+10=400;
        r = (int)(Math.random()*RANDOM_POSITION);
        apple_y = (r*DOT_SIZE);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    
    public void checkApple(){
        if((x[0]==apple_x) && (y[0]==apple_y)){
            dots++;
            locateApple();
        }
        
    }
    
    public void checkCollision(){
        
        for(int i=dots;i>0;i--){
            if((i>4) && ((x[0]==x[i]) && (y[0]==y[i]))){
                inGame=false;
            }
        }
        
        if(x[0]>=400){
            inGame=false;
        }
        if(y[0]>=400){
            inGame=false;
        }
        if(x[0]<0){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(!inGame){
            timer.stop();
        }
    }
    
    public void move(){
        
        for(int z=dots;z>0;z--){
            x[z]=x[z-1];
            y[z]=y[z-1];
        }
        if(leftDirection){
            x[0] -= DOT_SIZE;
        }
        if(rightDirection){
            x[0] += DOT_SIZE;
        }
        if(upDirection){
            y[0] -= DOT_SIZE;
        }
        if(downDirection){
            y[0] += DOT_SIZE;
        }
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for(int z=0;z<dots;z++){
                if(z==0){
                    g.drawImage(head,x[z],y[z],this);
                }
                else{
                    g.drawImage(dot, x[z], y[z], this);
                }
                Toolkit.getDefaultToolkit().sync();
            }
        }
        else{
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g){
        String msg = "Game Over";
        Font font = new Font("SAN_SERIF",Font.BOLD,14);
        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (400-metrices.stringWidth(msg))/2, 400/2);
    }
    
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            
            if(key==KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
              if(key==KeyEvent.VK_UP && (!downDirection)){
                leftDirection=false;
                upDirection=true;
                rightDirection=false;
            }
             if(key==KeyEvent.VK_DOWN && (!upDirection)){
                leftDirection=false;
                rightDirection=false;
                downDirection=true;
            }
        }
    }
}
