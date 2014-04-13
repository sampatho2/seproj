/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package binpacking;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author mc13mt49
 */
public class BestInter {
    
    JFrame start;
    JButton lod;
    
    BestInter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        start = new JFrame("Choose File");
        start.getContentPane().setLayout(null);
        start.setSize(new Dimension(500,400));
        lod = new JButton("Load");
        lod.setSize(new Dimension(80,50));
        start.getContentPane().add(lod);        
        lod.setLocation(((start.getWidth()/2)-(lod.getWidth()/2)),
                ((start.getHeight()/2)-(lod.getHeight()/2)));
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttonListen();
        start.setResizable(false);
        start.setVisible(true);        
    }
    
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        
        BestInter b = new BestInter();
        b.buttonListen();
        
    }
    void buttonListen(){
        lod.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                final JFileChooser fc = new JFileChooser();
                int isFileChosen = fc.showOpenDialog(start);
                if(isFileChosen == JFileChooser.APPROVE_OPTION){
                    File fil = fc.getSelectedFile();
                    try {
                        new Parse(fil);
                        start.dispose();
                        new BestInter2();
                    } catch (IOException ex) {
                        Logger.getLogger(BestInter.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(BestInter.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(BestInter.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(BestInter.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(BestInter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            }
        });
        
    }
    
}
