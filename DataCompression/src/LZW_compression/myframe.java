
package LZW_compression;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
  
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
  
 
public class myframe extends JFrame{
     
  
    public myframe()
    {
        setBackground(Color.YELLOW);
        setSize(560,400);
        setLocation(200,200);
        setTitle("LZW-Compersion");
        JSTextArea.setLocation(10,120);
        JSTextArea.setSize(520,190);
        FileData.setLocation(10,100);
        FileData.setSize(100,20);
        quit.setLocation(450, 320);
        quit.setSize(80,30);
        quit.setBackground(Color.red);
    
        Container cp=getContentPane();
        cp.setForeground(Color.RED);
        cp.setLayout(null);
        cp.add(comP);
        cp.add(decomP);
        cp.add(FileData);
        cp.add(JSTextArea);
        cp.add(quit);
        cp.setBackground(Color.YELLOW);
        // cp.add(credit_text);
         
         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        quit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
      
    }
   
      private JTextArea text=new JTextArea(50,20);
      private JLabel FileData=new JLabel("File Data :");
      private JScrollPane JSTextArea=new JScrollPane(text);
      private comPanel comP=new comPanel(text);
      private decomPanel decomP=new decomPanel(text);
      private JButton quit=new JButton("Exit");
    
       
}

