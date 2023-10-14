
package chat.app;

import static chat.app.Server.ss;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;


public class Client extends JFrame implements ActionListener{
    JPanel p1;
    JLabel l1;
    Font f1,f2;
    JTextField t1;
    static JTextArea a1;
    JButton b1;
    Cursor cr;
    Container a;
    JScrollPane sp;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    Client(){
        a=this.getContentPane();
        a.setBackground(new Color(0,0,139));
        f1=new Font("arial",Font.BOLD,25);
        f2=new Font("arial",Font.PLAIN,23);
        cr=new Cursor(Cursor.HAND_CURSOR);
        p1=new JPanel();
        p1.setBounds(0,0,500,70);
        p1.setBackground(new Color(25,25,112));
        p1.setLayout(null);
        add(p1);
        
        l1=new JLabel("Client");
        l1.setBounds(100,15,200,40);
        l1.setFont(f1);
        l1.setForeground(Color.WHITE);
        p1.add(l1);
        
        t1=new JTextField();
        t1.setBounds(2,600,360,50);
        t1.setFont(f2);
        t1.setForeground(Color.BLACK);
        t1.setBackground(new Color(216,191,216));
        add(t1);
        
        b1=new JButton("send");
        b1.setBounds(364,600,115,50);
        b1.setFont(f1);
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(65,105,225));
        b1.setCursor(cr);
        b1.addActionListener(this);
        add(b1);
        
        a1=new JTextArea();
        a1.setBounds(2,73,478,524);
        a1.setBackground(new Color(216,191,216));
        a1.setFont(f2);
        a1.setForeground(Color.BLACK);
        a1.setEditable(false);
        a1.setLineWrap(true);
        a1.setWrapStyleWord(true);
        add(a1);
        
         
        sp=new JScrollPane(a1);
        sp.setBounds(2,73,478,524);
        sp.setBorder(BorderFactory.createEmptyBorder());
        
        ScrollBarUI ui=new BasicScrollBarUI(){
            @Override
            protected JButton createDecreaseButton(int orientation){
                JButton button=super.createDecreaseButton(orientation);
               button.setBackground(new Color(25,25,112));
                button.setForeground(Color.WHITE);
                this.thumbColor=new Color(65,105,225);
                return button;
            }
            @Override
                protected JButton createIncreaseButton(int orientation){
                JButton button=super.createDecreaseButton(orientation);
                button.setBackground(new Color(25,25,112));
                button.setForeground(Color.WHITE);
                return button;
                
            }
        };
        
        sp.getVerticalScrollBar().setUI(ui);
        add(sp);
        
       
        
        setLayout(null);
        setBounds(1100,200,500,700);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setVisible(true);
        
    }
    
    public static void main(String[] args) {
        new Client().setVisible(true);
        String msgIn="";
        try{
        s= new Socket("127.0.0.1",5600);
        din= new DataInputStream(s.getInputStream());//data rcv that is sent by server
        dout=new DataOutputStream(s.getOutputStream());//data which will be sent by me
        while(true){
        //need to read the data comes from server
        msgIn=din.readUTF();//Reading data came from server
        a1.setText(a1.getText()+"Server : "+msgIn+"\n\n");
        }
        }catch(Exception e){}
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
         try{
       String msgOut=t1.getText();
       a1.setText(a1.getText()+"Me : "+msgOut+"\n\n");
       
       dout.writeUTF(msgOut);//send msg to server
       
       t1.setText(""); 
         }catch(Exception e){}
         }
       
    }
