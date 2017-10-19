
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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
 
class decomPanel extends  JPanel {
     
    decomPanel(JTextArea area) {
        this.area=area;
        setSize(550,50);
        setLocation(0,50);
        add(myLabel);
        add(FileLocation);
        add(Browse);
        add(Go);
        add(decompress);
        Browse.setBackground(java.awt.Color.red);
        Go.setBackground(java.awt.Color.red);
        decompress.setBackground(java.awt.Color.red);
        
        
         
        Browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 
                int rVal =c.showOpenDialog(null);
                if(rVal == JFileChooser.APPROVE_OPTION) {
                    filename=c.getSelectedFile().getAbsolutePath();
                    Location=c.getSelectedFile().getParent();
                    FileLocation.setText( filename);
                    try {
                         
                         
                        file=new File(filename);
                        file_input = new FileInputStream(file);
                        data_in    = new DataInputStream(file_input );
                         
                         
                    } catch (IOException ex) {
                        JOptionPane.showConfirmDialog(null,"Cannot Read The File ?"
                                ,"Error",JOptionPane.ERROR_MESSAGE);
                         
                    }
                     
                }
                if(rVal == JFileChooser.CANCEL_OPTION) {
                    FileLocation.setText("");
                    filename=null;
                     
                }
                 
                 
            }
             
        }
        );
        Go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(filename!=null) {
                     
                    FillArea();
                }
            }
        }
        );
         
        decompress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DecompressFunction();
            }
        });
    }
    public void DecompressFunction() {
        if(filename!=null) {
            String seq = null,nextseq = null,todic=null;
            create_Diectionary();
            short Code;
            short Old_Code;
            short result;
            try {
                 
                 
                 
                WriteH=new BufferedWriter(new FileWriter(Location+"/Decom.txt"));
                 
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            area.setText("Reading Data From The File and Decompressing it ...!!!"+'\n');
             
            try {
                 
                 
                 
                Code= data_in.readShort();
                seq=Character.toString((char)Code);
                 
                 
                while(true) {
                    Code=data_in.readShort();
                    nextseq=getTheString(Code);
                    result=search_in_Diectionary(seq+nextseq);
                     
                    if(result!=-1) {
                        seq+=nextseq;
                        todic=null;
                    } else {
                        if(!nextseq.equals("?")) {
                            todic=seq+nextseq.substring(0,1);
                             
                            addToDiectionary(todic);
                            WriteH.write(seq);
                             
                            seq=nextseq;
                        } else {
                             
                            todic=seq+seq.substring(0,1);
                            addToDiectionary(todic);
                            WriteH.write(seq);
                             
                            seq=nextseq=todic;
                        }
                         
                    }
                     
                }
                 
                 
                 
                 
            } catch (IOException ex) {
                try {
                     
                    if(seq!=null) {
                        WriteH.write(seq);
                        area.append('\n'+"The File Decompressed... "+'\n');
                        area.append("Location : "+Location+"/Decom.txt"+'\n');
                    }
                } catch (IOException ex1) {
                    ex.printStackTrace();
                }
                 
                 
                 
            }
            try {
                 
                 
                 
                WriteH.flush();
                WriteH.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
             
        }
         
    }
    public String getTheString(short code) {
         
        if(code<256)
            return  Character.toString((char)code);
        else if(code<diectionary.size()) {
            return (String)(diectionary.get(code));
        } else return "?";
         
         
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
        System.out.println(seq);
        diectionary.add(seq);
    }
    public void FillArea() {
        if(filename!=null) {
            try {
                area.setText(" ");
                if(data_in==null)
                    System.out.println("NULL");
                short ch=data_in.readShort();
                while(ch!=-1) {
                    area.append(Short.toString(ch)+" ");
                    ch=data_in.readShort();
                }
                 
                 
            } catch (IOException ex) {
                 
            }
        }
    }
    private JFileChooser c = new JFileChooser();
    private JLabel myLabel=new JLabel("File Location: ");
    private JTextField FileLocation=new JTextField(20);
    private JButton Browse=new JButton("/..");
    private JButton Go=new JButton("Read");
    private JButton decompress=new JButton("Decompress");
    private String filename;
    private JTextArea area;
    private ArrayList diectionary ;
    private BufferedWriter WriteH;
    private String Location;
    private File file ;
    private FileInputStream file_input;
    private  DataInputStream data_in ;
     
}

