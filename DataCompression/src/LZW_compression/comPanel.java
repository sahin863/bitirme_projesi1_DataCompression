
package LZW_compression;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
 
class comPanel extends  JPanel {
     
    comPanel(JTextArea area) {
        this.area=area;
        setSize(550,50);
        setLocation(0,0);
        add(myLabel);
        add(FileLocation);
        add(Browse);
        add(Go);
        add(Compress);
        Browse.setBackground(java.awt.Color.red);
        Go.setBackground(java.awt.Color.red);
        Compress.setBackground(java.awt.Color.red);
        
         
        Browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rVal =c.showOpenDialog(null);
                if(rVal == JFileChooser.APPROVE_OPTION) {
                    filename=c.getSelectedFile().getAbsolutePath();
                    Location=c.getSelectedFile().getParent();
                    FileLocation.setText(filename);
                    try {
                        ReadH=new BufferedReader(new FileReader(filename));
                         
                         
                    } catch (IOException ex) {
                        JOptionPane.showConfirmDialog(null,"Can not Read The File ?"
                                ,"Error",JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
                if(rVal == JFileChooser.CANCEL_OPTION) {
                    FileLocation.setText("");
                    filename=null;
                }
                 
            }
        });
         
        Go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FillArea();
            }
        });
        Compress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                create_Diectionary();
                CompressFunction();
            }
        });
    }
    public void CompressFunction() {
        if(filename!=null) {
             
            String seq=null;
            short Code;
            short Old_Code;
            short result;
            try {
                file = new File(Location+"/Com.txt");
                file_output = new FileOutputStream(file);
                data_out = new DataOutputStream(file_output);
                 
                 
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            area.setText("Reading Data From The File and Compressing it ...!!!"+'\n');
             
            try {
                ReadH=new BufferedReader(new FileReader(filename));
                 
                 
                Old_Code=Code=(short)ReadH.read();
                seq=Character.toString((char)Code);
                 
                 
                while(seq!=null) {
                     
                    if(seq.length()>1)//sadece bir karakter
                    {
                        result=search_in_Diectionary(seq);
                        if(result==-1) {
                             
                            addToDiectionary(seq);
                             
                            seq=Character.toString(seq.charAt(seq.length()-1));
                             
                             
                             
                             
                            area.append(Short.toString(Old_Code)+" ");//
                            data_out.writeShort(Old_Code);
                            Old_Code=(short)((char)seq.charAt(0));
                             
                             
                        } else {
                            Old_Code=result;
                        }
                         
                    }
                     
                    Code=(short)ReadH.read();
                    seq+=Character.toString((char)Code);
                     
                    if(Code==-1)//end of file
                    {
                        area.append(Short.toString(Old_Code)+" ");//
                        data_out.writeShort(Old_Code);
                        area.append('\n'+"File Compressed ..."+'\n'+"Location: "+Location+"/Com.txt");//
                        area.append('\n'+"Thank you for using this program");//
                        break;
                    }
                }
                 
                 
                ReadH.close();
                data_out.flush();
                data_out.close();
                 
            } catch (IOException ex) {
                ex.printStackTrace();
            }
             
        }
         
    }
    public void FillArea() {
        try {
             
            area.setText(" ");
            String line=ReadH.readLine();
            while(line!=null) {
                area.append(line+'\n');
                line=ReadH.readLine();
            }
             
             
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void create_Diectionary() {
        diectionary=new ArrayList();
        for(short i=0;i<256;i++) {
            diectionary.add((char)i);
        }
    }
    public short search_in_Diectionary(String seq) {
         
         
        for(short i=256;i<diectionary.size();i++) {
             
            if(seq.equals((String)(diectionary.get(i)))) {
                return i;
            }
        }
        return -1;
         
    }
    public void addToDiectionary(String seq) {
        diectionary.add(seq);
    }
    private JFileChooser c = new JFileChooser();
    private JLabel myLabel=new JLabel("File Location: ");
    private JTextField FileLocation=new JTextField(20);
    private JButton Browse=new JButton("/..");
    private JButton Go=new JButton("Read");
    private JButton Compress=new JButton("Compress");
    private String filename;
    private String Location;
    private ArrayList diectionary ;
    private JTextArea area;
    private BufferedReader ReadH;
     
    private FileOutputStream file_output ;
    private File file ;
    private DataOutputStream data_out;
         
}
