/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PixieScreenShare;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Dylan Adams
 */
public class ScreenShare extends Thread{
    
    boolean sharing;
    
    public ScreenShare(boolean sharing) {
        this.sharing = sharing;
    }
    
    private static double[][] comparePixelMap(double[][] old){
        
        
        
        return new double[1][1];
    }
    
    private static void updatePixelMap(BufferedImage bi, Rectangle monitor) {
        
        double[][] pixelMap = new double[monitor.width][monitor.height];
        for(int w = 0; w < pixelMap.length; w++){
            for(int h = 0; h < pixelMap[0].length; h++) {
                pixelMap[w][h] = bi.getRGB(w, h);
            }
        }
        
        comparePixelMap(pixelMap);
        
    }
    
    public double[][] getPixelMap(int width, int height) {
        
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        double[][] pixelMap = new double[width][height];
        
        
        
        return pixelMap;
        
    }
    
    public static GraphicsDevice getMonitor(int monitor) {
        
        Rectangle resolution = new Rectangle();
        BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        /* Docs Saample Code */
        Rectangle virtualBounds = new Rectangle();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        
        return gs[monitor];
        
    }
    
    public static void main(String[] args) {
        try {
            GraphicsDevice main = getMonitor(0);
            GraphicsConfiguration gc = main.getDefaultConfiguration();
            Rectangle resolution = gc.getBounds();

            BufferedImage bi = gc.createCompatibleImage(resolution.width, resolution.height);
            Robot rb = new Robot(main);
            bi = rb.createScreenCapture(resolution);
            //ImageIO.write(bi, "jpg", new File("test.jpg"));
            updatePixelMap(bi, resolution);
        
//        for (int j = 0; j < gs.length; j++) {
//            GraphicsDevice gd = gs[j];
//            GraphicsConfiguration[] gc = gd.getConfigurations();
//            monitor = gc[0].getBounds();
//            bi = gc[0].createCompatibleImage(monitor.width,monitor.height);
//            for (int i = 0; i < gc.length; i++) {
//                virtualBounds = virtualBounds.union(gc[i].getBounds());
//            }
//        }
            
            //updatePixelMap(bi, monitor);
            
        } 
//        catch (IOException ex) {
//            System.out.println("Image write error: " + ex.getMessage());
//        } 
        catch (AWTException ex) {
            System.out.println("Robot error: " + ex.getMessage());
        }
        
    }
    
}
