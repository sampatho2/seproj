/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package binpacking;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

/**
 *
 * @author sam
 */
public class BestInter2 implements MouseListener{
    
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        BestInter2 b2 = new BestInter2();
    }
    PMmodifier pmr;
    JFrame mainWind;
    JPanel PMPanel;  
    JPanel PMidPanel;
    JPanel butPanel;
    //JScrollPane scrPane;
    JPanel[] PMArr;
    JLabel[] PMidLabels;
    int PMCount;
    JButton[] but;
    
    Dimension butDim = new Dimension(150,200);
    Dimension idDim = new Dimension(150,20);

    public BestInter2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        
        mainWind = new JFrame("Bin packing and VM consolidation");
        PMPanel = new JPanel();
        PMidPanel = new JPanel(new FlowLayout());
        butPanel = new JPanel(new FlowLayout());
//        scrPane= new JScrollPane();
        mainWind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWind.getContentPane().setLayout(new BoxLayout(mainWind.getContentPane(), BoxLayout.Y_AXIS));
        pmr= new PMmodifier();
        buildGUI();
        
        
        //mainWind.pack();
        
    }
    
    
    
    
    void setSizes(int noOfPMs){
        
        int framWidth = (noOfPMs*(butDim.height))+10;
        PMPanel.setSize(framWidth,300 );
        PMidPanel.setSize(framWidth,20);
//        scrPane.setSize(framWidth,400);
        butPanel.setSize(framWidth,100);
        mainWind.setSize(framWidth, 520);
    }
    
    void buildGUI(){
        PMCount = pmr.getPMCountA();
        createPMs(PMCount);        
        but= new JButton[4];
        but[0] = new JButton("add VM");
        butPanel.add(but[0]);
        but[1] = new JButton("delete VM");
        butPanel.add(but[1]);
        but[2] = new JButton("consolidate");
        butPanel.add(but[2]);
        but[3] = new JButton("Exit");
        butPanel.add(but[3]);
        addButtonListeners(but);
        
//        scrPane= new JScrollPane();
  //      scrPane.setLayout(new BoxLayout(scrPane, BoxLayout.Y_AXIS));
        setSizes(PMCount);
    //    scrPane.add(PMPanel);
        
        //mainWind.add(scrPane);
        mainWind.add(PMPanel);
        mainWind.add(PMidPanel);
        mainWind.add(butPanel);
        mainWind.setResizable(false);
        mainWind.setVisible(true);
        
        
    }
    
    void createPMs(int noOfPMs){
        //int noOfPMs=pmr.getPMCount();
        
        PMArr = new JPanel[noOfPMs];
        PMidLabels = new JLabel[noOfPMs];
        
        for(int i=0; i < noOfPMs; i++){
            PMArr[i] = new JPanel();
            PMArr[i].addMouseListener(this);
            PMidLabels[i] = new JLabel(pmr.getPMNoA(i));
            PMArr[i].setLayout(new BoxLayout(PMArr[i], BoxLayout.Y_AXIS));
            PMArr[i].setPreferredSize(butDim);
            PMArr[i].setBorder(BorderFactory.createLineBorder(Color.green,4));
            if(!pmr.getOnStatusA(i)){
                PMArr[i].setBorder(BorderFactory.createLineBorder(Color.red,4));
                
            }
            PMidLabels[i].setPreferredSize(idDim);
            PMPanel.add(PMArr[i]);
            PMidPanel.add(PMidLabels[i]);
            
            
        }
        for(int i=0;i<pmr.getPMCountA();i++){
            if(pmr.getOnStatusA(i)==true){
                
                for(int j=0;j<pmr.getVMCount(i);j++){
                    String VM_ID=pmr.getVMID(i,j);
                    //System.out.println("vm id returned "+VM_ID);
                    int VMCap=pmr.getVMCap(i,j);
                    JLabel lbl=new JLabel(VM_ID+" "+VMCap);
                    Dimension dim = new Dimension(butDim.width,2*VMCap);
                    lbl.setBorder(BorderFactory.createLineBorder(Color.green,3));
                    lbl.setMaximumSize(dim);
                    PMArr[i].add(lbl);
                    
                }
                int resCap=pmr.getResCapA(i);
                JLabel remCap=new JLabel("Rem Cap "+resCap);
                remCap.setMaximumSize(new Dimension(butDim.width,2*resCap));
                remCap.setBorder(BorderFactory.createLineBorder(Color.green,3));
                PMArr[i].add(remCap);
            }
        }
        
    }
    
    void addButtonListeners(JButton[] buts){
        buts[0].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                MyDialogs m=new MyDialogs(mainWind, "Enter VM ID and capacity");
                if(m.areAccepted()){
                    
                    String[] vs ;
                    vs = m.getValues();
                    if((vs[0]==null || vs[1]==null)){
                        JOptionPane.showMessageDialog(mainWind, "Please "
                                + "enter both the values");
                    }
                    
                    
                    else if(Float.parseFloat(vs[1])<20||Float.parseFloat(vs[1])>100){
                    
                        JOptionPane.showMessageDialog(mainWind, "The VM"
                                + " capacity should not be less than 20 and "
                                + "greater than 100");
                    }
                        
                    else {
                    int err=pmr.addVMA(vs[0], (int)Math.ceil(Float.parseFloat(vs[1])));
                    if(err==1){
                        JOptionPane.showMessageDialog(mainWind, "There exists "
                                + "a VM with same ID choose another");
                    }
                    else if(err==2){
                        JOptionPane.showMessageDialog(mainWind, "Sorry no enogh "
                                + "capacity to add a VM");
                    }
                    remove();
                    buildGUI();
                    }
                }
                
                
            }
        });
        buts[1].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                DelDialog d =new DelDialog(mainWind, "Delete VM");
                if(d.areAccepted()){
                    int[] vs;
                    vs= d.getValues();
                    pmr.deleteVM(vs[0],vs[1]);
                    remove();
                    buildGUI();
                }
            }
        });
        
        buts[2].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(PMPanel, 
                            "Sure about consolidating>",
                            "consolidation",JOptionPane.YES_NO_OPTION)){
                if(pmr.consolidate()){
                    //System.out.println("entered");
                    
                    remove();
                    buildGUI();
                }
                }
            }
        });
        
        buts[3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(2);
            }
        });
    }
    
    void remove(){
        for(int i=0;i<PMCount;i++){
            PMPanel.remove(PMArr[i]);
            PMidPanel.remove(PMidLabels[i]);
        }
        for(int j=0;j<4;j++){
            butPanel.remove(but[j]);
        }
        
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        for(int i=0;i<pmr.getPMCountA();i++){
            if(me.getSource()==PMArr[i]){
                if(!pmr.getOnStatusA(i)){
                pmr.changeState(i);
                }
                else{
                    if(JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(PMPanel, 
                            "Sure about switching off?",
                            "Switch Off",JOptionPane.YES_NO_OPTION)){
                    pmr.switchOFFPM(i);
                    }
                }
                remove();
                buildGUI();
                continue;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
