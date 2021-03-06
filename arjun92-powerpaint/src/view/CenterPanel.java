/*
 * TCSS 305 - Power Paint
 */

package view;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import tools.LineTool;
import tools.Tools;



/**
 * This is a panel center of the frame to draw.
 * @author Arjun Prajapati
 * @version November 11, 2017
 */
public class CenterPanel extends JPanel {
   
    /** default thickness for JSlider. */
    public static final int DEFAULT_THICKNESS = 10;
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** The preferred size of this panel. */
    private static final Dimension SIZE = new Dimension(500, 300);
    

    /** primary color. */
    private Color myPrimaryColor;
    
    /** secondary color. */
    private Color mySecondaryColor;
//    
    /** current selected color. */
    private Color myCurrentColor;
   
    
    /** Selected tools. */
    private Tools mySelectedTool;
    
    /** List to store the shapes of the panel. */
    private final List<PaintShape> myPaintShape; 
    
    /** the current thickness of the line. */
    private int myThickness;

    /** Shape to draw. */
    private Shape myShape;
    
    /** the GUI object. */
    private final  PowerPaintGui myPowerPaint;
    
    /** boolean to check to paint current Shape. */
    private boolean myInitialDraw;

    /**
     * Constructor of the CenterPanel to setup the panel.
     * 
     * @param thePowerPaint , the window class containing the frame.
     */
    public CenterPanel(final PowerPaintGui thePowerPaint) {
        super();
        
        myPowerPaint = thePowerPaint;
        ititalizePanel();
        myPaintShape = new ArrayList<PaintShape>();
        myThickness = DEFAULT_THICKNESS;
        
        // to select the default tool.
        mySelectedTool = new LineTool();
        


    }
    
    /**
     * Sets up the panel.
     */
    private void ititalizePanel() {
        setBackground(Color.WHITE);
        setPreferredSize(SIZE);
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        
        // to add the action lister to the panel.
        final MouseInputAdapter mia = new MyMouseInputAdapter();
        addMouseListener(mia);
        addMouseMotionListener(mia);
        
    }
    
    /**
     * This method is to set the thickness to draw.
     * @param theThickness , integer value 
     */
    public void setThickness(final int theThickness) {
        myThickness = theThickness;
    }
    
    /**
     * THis method to set the primary color.
     * @param myColor
     */

    /**
     *  reset the primary color when a new color is chosen.
     *  
     * @param thePrimaryColor the primary color
     */
    public void setPrimaryColor(final Color thePrimaryColor) {
        myPrimaryColor = thePrimaryColor;
    }
    
    /**
     *  reset the secondary color when a new color is chosen.
     *  
     * @param theSecondaryColor the secondary color
     */
    public void setSecondaryColor(final Color theSecondaryColor) {
        mySecondaryColor = theSecondaryColor;
    }
    
    /**
     *  remove all the shapes on the drawing panel.
     */
    public void clear() {
        myPaintShape.clear();
        myInitialDraw = false;
        repaint();
    }
    
    
    /**
     * the method switch tool when certain tool is selected.
     * 
     * @param theTool the name of the tool.
     */
    public void setTool(final Tools theTool) {
        mySelectedTool = theTool;
        
    }

    /**
     * Method to draw the shape.
     * 
     * @param theGraphics , component to draw.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        
        final Graphics2D g2d  = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        // to draw the shapes in list.
        for (final PaintShape paintShape : myPaintShape) {
            myPowerPaint.setEnable(true);
            
            g2d.setColor(paintShape.getColor());
            
            g2d.setStroke(new BasicStroke(paintShape.getThickness()));
            g2d.draw(paintShape.getShape());
            
        }
        
        
        //  Statement to check if the thickness is more than zero to draw shape.
        if (myInitialDraw) {
            g2d.setStroke(new BasicStroke(myThickness));
            g2d.setColor(myCurrentColor);
            if (myThickness != 0) {
                g2d.draw(myShape);
            }
        }
        
        

    }


    /**
     * method to draw with given selected shape, color and theThickness.
     * 
     * @param theShape the drawing shape
     * @param theColor the color to draw
     * @param theThickness the thickness to draw.
     */
    public void addShape(final Shape theShape
                         , final Color theColor, final int theThickness) {
        final PaintShape shape = new PaintShape(theShape, theThickness, theColor);
        myPaintShape.add(shape);
        repaint();
        
    }

    /**
     *  mouse action listener.
     *  
     * @author Arjun Prajapati
     * @version November 11, 2017
     */
    class MyMouseInputAdapter extends MouseInputAdapter {
        /**get the start point.*/
        private Point myInitialPoint;
        
        /**
         * method fires when mouse button is clicked.
         */
        @Override
        public void mousePressed(final MouseEvent theEvent) {
            /** Condition to check, if the tool is eraser.*/
            if (mySelectedTool.getDescription().equals("eraserTool")) {
                myCurrentColor = Color.WHITE;
            } else if (theEvent.getButton() == MouseEvent.BUTTON1) {
                myCurrentColor = myPrimaryColor;
            } else {
                myCurrentColor = mySecondaryColor;
            }
        
            myInitialPoint = theEvent.getPoint();
            myShape = mySelectedTool.setNewShape(theEvent);
        }
        
        /**
         * method fires when the button of mouse is released.
         */
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            if (myThickness != 0) {
                mySelectedTool.draw(myShape, myInitialPoint, theEvent);
                addShape(myShape, myCurrentColor, myThickness);

            }
        }
        
        /**
         * method fires when mouse detect when mouse is dragged.
         */
        @Override
        public void mouseDragged(final MouseEvent theEvent) {
            
            mySelectedTool.draw(myShape, myInitialPoint, theEvent);
            myInitialDraw = true;
            repaint();
                

        }
    }
}

