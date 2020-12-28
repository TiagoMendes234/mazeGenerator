import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.awt.Color.*;

public class Algorithm {
    private final ArrayList<Point> points = new ArrayList<>();
    private final ArrayList<Point> visited = new ArrayList<>();
    private final ArrayList<Point> paint = new ArrayList<>();
    private Point current;
    private final int yMax;
    private final int xMax;
    private final BufferedImage image;
    private final Graphics2D graphics2D;

    public Algorithm(int yMax,int xMax){
        this.current=new Point(0,0);
        this.yMax=yMax;
        this.xMax=xMax;
        this.image=new BufferedImage ( 98, 98, BufferedImage.TYPE_INT_ARGB );
        this.graphics2D=image.createGraphics ();
        graphics2D.setPaint ( BLACK );
        graphics2D.fillRect ( 0, 0, image.getWidth(), image.getHeight() );
        graphics2D.setPaint ( BLUE );
        for(int x=0;x<=image.getWidth();x=x+4) {
            for (int y = 0; y <= image.getHeight(); y = y + 4) {
                graphics2D.fillRect(x, y, 2, 2);
            }
        }
    }

    public void generate() throws IOException {
        points.add(current);
        while (visited.size() != xMax*yMax ) {
            visited.add(current);
            current = checkStatus(current, xMax, yMax);
            if(current.getY()==-100 || current.getX()==-100 ) {
                if(points.size()!=0) {
                    points.remove(points.size() - 1);
                }
                if(points.size()!=0) {
                    current = points.get(points.size() - 1);
                }
            }else{
                paintDirection(current, points.get(points.size() - 1));
                points.add(current);
            }
        }
        graphics2D.dispose ();
        File outputfile = new File("image.png");
        ImageIO.write(image, "png", outputfile);
    }

    private Point checkStatus(Point p, Integer xMax1, Integer yMax1){
        ArrayList<Point> aux = new ArrayList<>();
        for(int i=-4;i<5;i=i+8) {
            if ((int) (p.getX() + i) >= 0 && (int) (p.getX() + i) <= xMax1) {
                if (!visited.contains(new Point((int) p.getX() + i, (int) p.getY())))
                    aux.add(new Point((int) p.getX() + i, (int) p.getY()));
            }
            if ((int) (p.getY() + i) >= 0 && (int) (p.getY() + i) <= yMax1) {
                if (!visited.contains(new Point((int) p.getX(), (int) p.getY() + i)) )
                    aux.add(new Point((int) p.getX(), (int) p.getY() + i));
            }
        }

        if(aux.size()>0){
            int rand=new Random().nextInt(aux.size());
            return aux.get(rand);
        }else{
            return new Point(-100,-100);
        }

    }

    public void paintDirection(Point a,Point b) {
        graphics2D.setPaint ( WHITE );
        if (a.getX() != b.getX()) {
            if (a.getX() > b.getX()) {
                graphics2D.fillRect((int) (((a.getX()+b.getX())/2)), (int) (a.getY()), 2, 2);
                graphics2D.fillRect((int) (((a.getX()+b.getX())/2)+2), (int) (a.getY()), 2, 2);
                graphics2D.fillRect((int) (b.getX()), (int) (b.getY()), 2, 2);
            } else {
                graphics2D.fillRect((int) (((b.getX() + a.getX()) / 2)), (int) (b.getY()), 2, 2);
                graphics2D.fillRect((int) (((b.getX() + a.getX()) / 2)-2), (int) (b.getY()), 2, 2);
                graphics2D.fillRect((int) (b.getX()), (int) (b.getY()), 2, 2);
            }
        } else {
            if (a.getY() > b.getY()) {
                graphics2D.fillRect((int) (a.getX()), (int) (((a.getY()+b.getY())/2)), 2, 2);
                graphics2D.fillRect((int) (a.getX()), (int) (((a.getY()+b.getY())/2)+2), 2, 2);
                graphics2D.fillRect((int) (b.getX()), (int) (b.getY()), 2, 2);
            } else {
                graphics2D.fillRect((int) (b.getX()), (int) (((b.getY()+a.getY())/2)), 2, 2);
                graphics2D.fillRect((int) (b.getX()), (int) (((b.getY()+a.getY())/2)-2), 2, 2);
                graphics2D.fillRect((int) (b.getX()), (int) (b.getY()), 2, 2);

            }
        }
    }

    public ArrayList<Point> getPaint(){
        return paint;
    }

}

