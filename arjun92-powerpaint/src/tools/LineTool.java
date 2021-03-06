/*
 * TCSS 305 - Power Paint
 */
package tools;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;


//import view.CenterPanel;

/**
 * Tools to draw line.
 * @author Arjun Prajapati
 * @version November 11, 2017
 */
public class LineTool extends AbstractTools {
    /** declaration of CenterPanel.*/
//    private CenterPanel panel;
    /**
     * this method set up the drawing property for the line shape.
     */
    @Override
    public void draw(final Shape theShape
                     , final Point theStartPoint
                     , final MouseEvent theEvent) {
//        ((Line2D) theShape).setLine(panel.getWidth() 
//        - (theEvent.getX() - theStartPoint.x) / 2,
//                                    panel.getHeight() - 
//        (theEvent.getX() - theStartPoint.x) / 2,
//                                    panel.getWidth() * 2 , panel.getHeight() * 2 );  
        ((Line2D) theShape).setLine(theStartPoint.x,
                                    theStartPoint.y, theEvent.getX(), theEvent.getY());
//        System.out.println(x);
    }
    
    /**
     * return a new line shape.
     * 
     * @return return a new line 2D shape.
     */
    @Override
    public Shape setNewShape(final  MouseEvent theEvent) {
        return new Line2D.Double();
    }

    @Override
    public String getDescription() {
        return "";
    }


    





}
