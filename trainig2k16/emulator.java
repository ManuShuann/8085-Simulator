import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
public class emulator extends JFrame{
 JFrame f =new JFrame("8085 Emulataor By MANPREET");
 JPanel p_textarea,p_register,p_flag,p_memory,p_remark,p_convertor,p_buttons;
 JLabel Acc,Reg_B,Reg_C,Reg_D,Reg_E,Reg_L,Reg_H,Reg_Stack,Pro_Coun,L_hex,L_dec,cF,zF,sF,pF,mem_adrs,mem_content;
 JButton clear,compile,Line_by,convert,mem_insert;
 JTextField hex,dec,Tex_Acc,Tex_Reg_B,Tex_Reg_C,Tex_Reg_D,Tex_Reg_E,Tex_Reg_H,Tex_Reg_L,Tex_Reg_Stack,Tex_Pro_Coun,Tex_cF,Tex_zF,Tex_sF,Tex_pF,Tex_mem_adrs,Tex_mem_content;
 JTextArea txt=new JTextArea();
 JTextArea txt_remark=new JTextArea();
 JScrollPane scrolltxt = new JScrollPane(txt);
 JFileChooser fc = new JFileChooser();
 static String A[]=new String[65536];
 char[] keyword = {'A','B','C','D','E','H','L','M'};
 static String register[]=new String[7];
 String store_stack,store_pc;
 
 static int carry=0;
 static int sign=0;
 static int paritory=0;
 static int zero=0;
 String[] instlines;
 String instr[]=new String[3];
 String flipbtn="Edit Registers";
 public void flag(){
  cF=new JLabel("Carry Flag");zF=new JLabel("Zero Flag");sF=new JLabel("Sign Flag");pF=new JLabel("Paritory");
  Tex_cF=new JTextField();Tex_zF=new JTextField();Tex_sF=new JTextField();Tex_pF=new JTextField();
  cF.setPreferredSize(new Dimension(60, 30));
  Tex_cF.setPreferredSize(new Dimension(40, 30));
  Tex_cF.setEditable(false);
  p_flag.add(cF);
  p_flag.add(Tex_cF);
  zF.setPreferredSize(new Dimension(60, 30));
  Tex_zF.setPreferredSize(new Dimension(40, 30));
  Tex_zF.setEditable(false);
  p_flag.add(zF);
  p_flag.add(Tex_zF);
  sF.setPreferredSize(new Dimension(60, 30));
  Tex_sF.setPreferredSize(new Dimension(40, 30));
  Tex_sF.setEditable(false);
  p_flag.add(sF);
  p_flag.add(Tex_sF);
  pF.setPreferredSize(new Dimension(60, 30));
  Tex_pF.setPreferredSize(new Dimension(40, 30));
  Tex_pF.setEditable(false);
  p_flag.add(pF);
  p_flag.add(Tex_pF);
  p_flag.revalidate();
 
 }
 public void memory(){
  mem_adrs=new JLabel("Adress 4bit Hex=");mem_content=new JLabel("Value=");
  Tex_mem_adrs=new JTextField();Tex_mem_content=new JTextField();
  mem_insert=new JButton("Insert");
  mem_adrs.setPreferredSize(new Dimension(100, 30));
  Tex_mem_adrs.setPreferredSize(new Dimension(70, 30));
  p_memory.add(mem_adrs);
  p_memory.add(Tex_mem_adrs);
  mem_content.setPreferredSize(new Dimension(40, 30));
  Tex_mem_content.setPreferredSize(new Dimension(50, 30));
  p_memory.add(mem_content);
  p_memory.add(Tex_mem_content);
  mem_insert.setPreferredSize(new Dimension(200, 60));
  p_memory.add(mem_insert);
  p_memory.revalidate();
  mem_insert.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){
    String x = Tex_mem_adrs.getText();
    String y = Tex_mem_content.getText();
    if(x.length()<=4 &&y.length()<=2){
     try{txt_remark.append("Adress="+x);
               Integer decimal_x = Integer.parseInt(x, 16);

               A[decimal_x]=y;
               
               txt_remark.append("\n\nValue="+A[decimal_x]);
               Tex_mem_adrs.setText("");
               Tex_mem_content.setText("");
           }

           catch(NumberFormatException ne){
            String error="Enter valid 4 Bit Hexadicmal Adress ";
            txt_remark.setText(error);

           }
   try{
             Integer decimal_y = Integer.parseInt(y, 16);
         }

         catch(NumberFormatException ne){
          String error="Enter valid 2 Bit Hexadicmal Value";
          txt_remark.setText(error);
         }
    }else{
     txt_remark.setText("Please provide valid 4 Bit  Hexdecimal adress and 2 bit Value" );
    }
    
   }
  }); 
 }
 //save as function
 public void SaveAs() {

        final JFileChooser SaveAs = new JFileChooser();
        SaveAs.setApproveButtonText("Save");
        int actionDialog = SaveAs.showOpenDialog(this);

        File fileName = new File(SaveAs.getSelectedFile() + ".txt");
        try {
            if (fileName == null) {
                return;
            }
            BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
            String ln = System.getProperty("line.separator");
            String text = txt.getText() ;
            String as = text.replaceAll("\n", ln);
            outFile.write(as.toString(),0, as.length());
          
           // getting entry line by line
            outFile.close();
        } catch (IOException ex) {
         txt_remark.setText("");
         txt_remark.setText("Input output error pls Try again");
        }

    }
 //
 //constructor
 public emulator(){
  //all zero initialization of memory and register
  all_zero_initialization();
  
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
  int x = (int) ((dimension.getWidth() - f.getWidth()) / 2);
  int y = (int) ((dimension.getHeight() - f.getHeight()) / 2);
  f.setLocation(x-400, y-300);
  f.setSize(910,615);
  f.setVisible(true);
  f.setResizable(false);
  f.setLayout(null);
  
  p_flag=new JPanel();
  p_memory=new JPanel();
  p_remark=new JPanel();
  p_convertor=new JPanel();
  //menu
  JMenuBar fileMenu = new JMenuBar();
  f.setJMenuBar(fileMenu);
  JMenu menu =new JMenu("File");
  fileMenu.add(menu);
  
  
   JMenuItem save = new JMenuItem("Save");
  
   JMenuItem ne = new JMenuItem("New");
   menu.add(ne);
   menu.add(save);
   save.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
     SaveAs();
    }
   });
  //menu ends
  //text area
  Font font = new Font("SansSerif", Font.BOLD, 20);
  txt.setFont(font);
  scrolltxt.setBounds(3, 3, 500, 400);  
  f.add(scrolltxt);
  validate();
  //ends
  
  //register display
  p_register=new JPanel();
  p_register.setBounds(506, 3, 230, 250);
  Border border = BorderFactory.createLineBorder(Color.BLACK);
  p_register.setBorder(BorderFactory.createTitledBorder(border, "Register"));
  Acc=new JLabel("Accumalotar");
  Reg_B=new JLabel("B");Reg_C=new JLabel("C");Reg_D=new JLabel("D");Reg_E=new JLabel("E");Reg_H=new JLabel("H");Reg_L=new JLabel("L");Reg_Stack=new JLabel("Stack Pointer");Pro_Coun=new JLabel("PC");
  Tex_Acc=new JTextField();Tex_Reg_B=new JTextField();Tex_Reg_C=new JTextField();Tex_Reg_D=new JTextField();Tex_Reg_E=new JTextField();Tex_Reg_H=new JTextField();Tex_Reg_L=new JTextField();Tex_Reg_Stack=new JTextField();Tex_Pro_Coun=new JTextField();
  Acc.setPreferredSize(new Dimension(90, 30));
  Tex_Acc.setPreferredSize(new Dimension(100, 30));
  Tex_Acc.setEditable(false);
  p_register.add(Acc);
  p_register.add(Tex_Acc);
  Reg_B.setPreferredSize(new Dimension(20, 30));
  Tex_Reg_B.setPreferredSize(new Dimension(70, 30));
  Tex_Reg_B.setEditable(false);
  p_register.add(Reg_B);
  p_register.add(Tex_Reg_B);
  Reg_C.setPreferredSize(new Dimension(20, 30));
  Tex_Reg_C.setPreferredSize(new Dimension(70, 30));
  Tex_Reg_C.setEditable(false);
  p_register.add(Reg_C);
  p_register.add(Tex_Reg_C);
  Reg_D.setPreferredSize(new Dimension(20, 30));
  Tex_Reg_D.setPreferredSize(new Dimension(70, 30));
  Tex_Reg_D.setEditable(false);
  p_register.add(Reg_D);
  p_register.add(Tex_Reg_D);
  Reg_E.setPreferredSize(new Dimension(20, 30));
  Tex_Reg_E.setPreferredSize(new Dimension(70, 30));
  Tex_Reg_E.setEditable(false);
  p_register.add(Reg_E);
  p_register.add(Tex_Reg_E);
  
  Reg_H.setPreferredSize(new Dimension(20, 30));
  Tex_Reg_H.setPreferredSize(new Dimension(70, 30));
  Tex_Reg_H.setEditable(false);
  p_register.add(Reg_H);
  p_register.add(Tex_Reg_H);
  Reg_L.setPreferredSize(new Dimension(20, 30));
  Tex_Reg_L.setPreferredSize(new Dimension(70, 30));
  Tex_Reg_L.setEditable(false);
  p_register.add(Reg_L);
  p_register.add(Tex_Reg_L);
  
  Reg_Stack.setPreferredSize(new Dimension(90, 30));
  Tex_Reg_Stack.setPreferredSize(new Dimension(100, 30));
  Tex_Reg_Stack.setEditable(false);
  p_register.add(Reg_Stack);
  p_register.add(Tex_Reg_Stack);
  
  Pro_Coun.setPreferredSize(new Dimension(90, 30));
  Tex_Pro_Coun.setPreferredSize(new Dimension(100, 30));
  Tex_Pro_Coun.setEditable(false);
  p_register.add(Pro_Coun);
  p_register.add(Tex_Pro_Coun);
  
  
  
  f.add(p_register);
  p_register.revalidate();
  //ends
  //flag display
  p_flag=new JPanel();
  p_flag.setBounds(742, 3, 150, 250);
  
  p_flag.setBorder(BorderFactory.createTitledBorder(border, "Flag"));
  flag();
  f.add(p_flag);
  p_flag.revalidate();
  //ends
  //memory initilization
  p_memory=new JPanel();
  p_memory.setBounds(506, 256, 386, 144);
  p_memory.setBorder(BorderFactory.createTitledBorder(border, "Memory Initialization"));
  memory();
  f.add(p_memory);
  p_memory.revalidate();
  
  //ends
  
  //remark
  p_remark=new JPanel();
  p_remark.setBounds(390, 403, 500, 160);
  txt_remark.setPreferredSize(new Dimension(485, 130));
  txt_remark.setEditable(false);
  p_remark.add(txt_remark);
  p_remark.setBorder(BorderFactory.createTitledBorder(border, "Remark"));
  f.add(p_remark);
  p_remark.revalidate();
  //ends
  
  
  //buttons
  p_buttons=new JPanel();
  p_buttons.setBounds(3, 403, 150, 160);
  p_buttons.setBorder(BorderFactory.createTitledBorder(border, ""));
  clear=new JButton("Clear");
  clear.setPreferredSize(new Dimension(140, 46));
  p_buttons.add(clear);
  clear.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){
    txt.setText("");
   }
  });
  compile=new JButton("Compile");
  compile.setPreferredSize(new Dimension(140, 46));
  p_buttons.add(compile);
  compile.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){
    txt_remark.setText("");
    compile();
   }
  });
  
  Line_by=new JButton(flipbtn);
  Line_by.setPreferredSize(new Dimension(140, 46));
  p_buttons.add(Line_by);
  Line_by.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){
    if(flipbtn.equals("Edit Registers")){
     txt_remark.setText("");
     Tex_Acc.setEditable(true);
     Tex_Reg_B.setEditable(true);
     Tex_Reg_C.setEditable(true);
     Tex_Reg_D.setEditable(true);
     Tex_Reg_E.setEditable(true);
     Tex_Reg_H.setEditable(true);
     Tex_Reg_L.setEditable(true);
     Tex_Reg_Stack.setEditable(true);
     Tex_Pro_Coun.setEditable(true);
     flipbtn="Store value to Reg";
    }else{
     register[0]=Tex_Acc.getText();
     if(register[0].length()>2){
      txt_remark.setText("enter valid 2 bit hexa number");
      register[0]=Integer.toHexString(0);
      Tex_Acc.setText("");
     }
     
     register[1]=Tex_Reg_B.getText();
     if(register[1].length()>2){
      txt_remark.setText("enter valid 2 bit hexa number");
      register[1]=Integer.toHexString(0);
      Tex_Reg_B.setText("");
     }
     register[2]=Tex_Reg_C.getText();
     if(register[2].length()>2){
      txt_remark.setText("enter valid 2 bit hexa number");
      register[2]=Integer.toHexString(0);
      Tex_Reg_C.setText("");
     }
     register[3]=Tex_Reg_D.getText();
     if(register[3].length()>2){
      txt_remark.setText("enter valid 2 bit hexa number");
      register[3]=Integer.toHexString(0);
      Tex_Reg_D.setText("");
     }
     register[4]=Tex_Reg_E.getText();
     if(register[4].length()>2){
      txt_remark.setText("enter valid 2 bit hexa number");
      register[4]=Integer.toHexString(0);
      Tex_Reg_E.setText("");
     }
     register[5]=Tex_Reg_H.getText();
     if(register[5].length()>2){
      txt_remark.setText("enter valid 2 bit hexa number");
      register[5]=Integer.toHexString(0);
      Tex_Reg_H.setText("");
     }
     register[6]=Tex_Reg_L.getText();
     if(register[6].length()>2){
      txt_remark.setText("enter valid 2 bit hexa number");
      register[6]=Integer.toHexString(0);
      Tex_Reg_L.setText("");
     }
     store_stack=Tex_Reg_Stack.getText();
     store_pc=Tex_Pro_Coun.getText();
     Tex_Acc.setEditable(false);
     Tex_Reg_B.setEditable(false);
     Tex_Reg_C.setEditable(false);
     Tex_Reg_D.setEditable(false);
     Tex_Reg_E.setEditable(false);
     Tex_Reg_H.setEditable(false);
     Tex_Reg_L.setEditable(false);
     Tex_Reg_Stack.setEditable(false);
     Tex_Pro_Coun.setEditable(false);
     flipbtn="Edit Registers";
     
    }
   }
  });
  f.add(p_buttons);
  p_buttons.revalidate();

  
  //ends
  //convertor
  p_convertor=new JPanel();
  p_convertor.setBounds(156, 403, 230, 161);
  p_convertor.setBorder(BorderFactory.createTitledBorder(border, "Hex to Dec Converter "));
  hex=new JTextField("");
  hex.setEditable(false);
  L_hex=new JLabel("Hexadecimal");
  dec=new JTextField("");
  L_dec=new JLabel("Decimal");
  convert=new JButton("Convert");
  hex.setPreferredSize(new Dimension(90, 30));
  L_hex.setPreferredSize(new Dimension(80, 30));
  dec.setPreferredSize(new Dimension(90, 30));
  L_dec.setPreferredSize(new Dimension(80, 30));
  convert.setPreferredSize(new Dimension(170, 46));
  p_convertor.add(L_dec);
  p_convertor.add(dec);
  p_convertor.add(convert);
  p_convertor.add(L_hex);
  p_convertor.add(hex);
  f.add(p_convertor);
  convert.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){
    String x = dec.getText();
    int intx = Integer.parseInt ( x);
    
    //String strI = String.valueOf(intx);
    String hexval = Integer.toHexString(intx);
    hexval.toUpperCase();
    //txt_remark.setText("");
    //txt_remark.setText(strI);
    hex.setText(hexval);
    
   }
  });
  p_convertor.revalidate();
  validate();
  
  //ends
   
 }
 
 public void compile(){
  txt_remark.setText("");
  String[] instlines = txt.getText().split("\\n");
 
   //txt_remark.append(instlines[4]);
  char ch;
  for(int i=0;i<instlines.length;i++){
   int len=instlines[i].length();
   int st=0,end=0,counter=0;
   instr[0]="";instr[1]="";instr[2]="";
   for(int j=0;j<len;j++){
    ch=instlines[i].charAt(j);
    if(ch==' '||ch==','){
     end=j;
     instr[counter]=instlines[i].substring(st,end);
     counter++;
     st=j+1;
    }
    if(j==len-1){
     instr[counter]=instlines[i].substring(st,len);
     if(instr[counter].length()>1&&counter>0){
      instr[counter]=instlines[i].substring(st,len-1);
     }
    }
   }
   txt_remark.append(instr[0]);
   
   //SEND VALUE
   instruction(instr[0],instr[1],instr[2]);
   
  }
  
  //txt_remark.append(instr[]);
  
 }
 //INSTRUCTION SET
 public void instruction(String s1,String s2,String s3){
  
  // MOV
  if(s1.equalsIgnoreCase("MOV")){
   int errorcase1=search(keyword,s2.charAt(0));
   int errorcase2=search(keyword,s3.charAt(0));
   int source=whichregister(keyword,s3.charAt(0));
   int destination=whichregister(keyword,s2.charAt(0));
   if(errorcase1==0&&errorcase1==0){
    txt_remark.append("\n Sysntax error");
   }
   if(errorcase1==1&&errorcase1==1){
    if(s2.charAt(0)!='M'&&s3.charAt(0)!='M'){
     register[destination]=register[source];
     display();
     
    }else{
     String memadrs=register[5]+register[6];
     //txt_remark.append(memadrs);
     Integer decimal_mem = Integer.parseInt(memadrs, 16);
     String Data=A[decimal_mem];
     if(s3.charAt(0)=='M'){
      //txt_remark.append("\nTHIS OCCUR");
      register[destination]=Data;
      display();
     }
     //txt_remark.append(Data);
     
     if(s2.charAt(0)=='M'){
      A[decimal_mem]=register[source];
      txt_remark.append("\nAdress="+memadrs);
      txt_remark.append("VALUE="+A[decimal_mem]);
      display();
     }
     
    }
   }
   
  }
  //inst-2 MVI
  if(s1.equalsIgnoreCase("MVI")){
   if(s2.charAt(0)=='M'){
    String memadrs=register[5]+register[6];
    Integer decimal_mem = Integer.parseInt(memadrs, 16);
    A[decimal_mem]=s3;
    display();
    txt_remark.append("\nAdress="+memadrs);
    txt_remark.append("VALUE="+s3);
   }else{
    int errorcase1=search(keyword,s2.charAt(0));
    int destination=whichregister(keyword,s2.charAt(0));
    if(errorcase1==0){
     txt_remark.append("\nSyntax error");
    }else{
     register[destination]=s3;
     display();
    }
    
   }
  }
  //INST-3 LDA
  if(s1.equalsIgnoreCase("LDA")){
   if(s2.length()>4){
    txt_remark.append("\nInvalid Address");
   }else{
    try{
     Integer decimal_mem = Integer.parseInt(s2, 16);
     register[0]=A[decimal_mem];
     display();
    }
    catch(NumberFormatException ne){
     txt_remark.append("\nAdress was not hexdecimal");
    }
   }
   
   //Integer decimal_mem = Integer.parseInt(s2, 16);
  }
  //INST-4 LDAX
  if(s1.equalsIgnoreCase("LDAX")){
   int errorcase1=search(keyword,s2.charAt(0));
   int source=whichregister(keyword,s2.charAt(0));
   if(s2.length()>1||errorcase1==0){
    txt_remark.append("\nSyntax error");
   }
   if(source>=1&&source<=6){
    if(source==1||source==2){
     String memadrs=register[1]+register[2];
     Integer decimal_mem = Integer.parseInt(memadrs, 16);
     register[0]=A[decimal_mem];
     display();
    }
    if(source==3||source==4){
     String memadrs=register[3]+register[4];
     Integer decimal_mem = Integer.parseInt(memadrs, 16);
     register[0]=A[decimal_mem];
     display();
    }
    if(source==5||source==6){
     String memadrs=register[5]+register[6];
     Integer decimal_mem = Integer.parseInt(memadrs, 16);
     register[0]=A[decimal_mem];
     display();
    }
   }
   if(source==0){
    txt_remark.append("\nSyntax error");
   }
   
  }
  //INST-5 STA content of acc are copied into mem
  if(s1.equalsIgnoreCase("STA")){
   if(s2.length()>4){
    txt_remark.append("\nINVALID ADDRESS");
   }else{
    try{
     Integer decimal_mem = Integer.parseInt(s2, 16);
     A[decimal_mem]=register[0];
     display();
     txt_remark.append("\nAdress="+s2);
     txt_remark.append("VALUE="+register[0]);
    }
    catch(NumberFormatException ne){
     txt_remark.append("\nAdress was not hexdecimal");
    }
   }
   
  }
  //inst-6 STAX
  if(s1.equalsIgnoreCase("STAX")){
   int errorcase1=search(keyword,s2.charAt(0));
   int source=whichregister(keyword,s2.charAt(0));
   if(s2.length()>1||errorcase1==0){
    txt_remark.append("\nSyntax error");
   }
   if(source>=1&&source<=6){
    if(source==1||source==2){
     String memadrs=register[1]+register[2];
     Integer decimal_mem = Integer.parseInt(memadrs, 16);
     A[decimal_mem]=register[0];
     display();
     txt_remark.append("\nAdress="+memadrs);
     txt_remark.append("VALUE="+A[decimal_mem]);
    }
    if(source==3||source==4){
     String memadrs=register[3]+register[4];
     Integer decimal_mem = Integer.parseInt(memadrs, 16);
     A[decimal_mem]=register[0];
     display();
     txt_remark.append("\nAdress="+memadrs);
     txt_remark.append("VALUE="+A[decimal_mem]);
    }
    if(source==5||source==6){
     String memadrs=register[5]+register[6];
     Integer decimal_mem = Integer.parseInt(memadrs, 16);
     register[0]=A[decimal_mem];
     txt_remark.append("\nAdress="+memadrs);
     txt_remark.append("VALUE="+A[decimal_mem]);
     display();
    }
   }
   if(source==0||source==7){
    txt_remark.append("\nSyntax error");
   }
   
  }
  //INST-7 SHLD STORE HL PAIR IN MEMORY
  if(s1.equalsIgnoreCase("SHLD")){
   if(s2.length()>4){
    txt_remark.append("\nInvalid address ");
   }else{
    try{
     Integer decimal_mem = Integer.parseInt(s2, 16);
     A[decimal_mem]=register[6];
     A[decimal_mem+1]=register[5];
     display();
     txt_remark.append("\nAdress="+s2);
     txt_remark.append("VALUE="+register[6]);
     txt_remark.append("\nAdress="+Integer.toHexString(decimal_mem+1));
     txt_remark.append("VALUE="+register[5]);
    }
    catch(NumberFormatException ne){
     txt_remark.append("\nAdress was not hexdecimal");
    }
   }
   
  }
  //inst-8 XCHG H<-->D L<-->E
  if(s1.equalsIgnoreCase("XCHG")){
   
    String temp;
    temp=register[3];
    register[3]=register[5];
    register[5]=temp;
    temp=register[4];
    register[4]=register[6];
    register[6]=temp;
    display();
   
  }
  //SPHL
  if(s1.equalsIgnoreCase("SPHL")){
   
   String memadrs=register[5]+register[6];
   store_stack=memadrs;
   display();
   
  }
  if(s1.equalsIgnoreCase("XTHL")){
   String memadrs=store_stack;
   Integer decimal_mem = Integer.parseInt(memadrs, 16);
   register[5]=A[decimal_mem];
   register[6]=A[decimal_mem+1];
   display();
   
  }
  //PCHL
  
         if(s1.equalsIgnoreCase("PCHL")){
   
   String memadrs=register[5]+register[6];
   store_pc=memadrs;
   display();
   
  }
         // ADD R OR M
         if(s1.equalsIgnoreCase("ADD")){
          int source=whichregister(keyword,s2.charAt(0));
          if(s2.charAt(0)=='M'){
           String memadrs=register[5]+register[6];
           Integer decimal_mem = Integer.parseInt(memadrs, 16);
           Integer temp_a = Integer.parseInt(register[0], 16);
           Integer temp_m = Integer.parseInt(A[decimal_mem], 16);
           int result=temp_a+temp_m;
           register[0]=Integer.toHexString(result);
           if(register[0].length()>2){
            carry=1;
           }
           display();
          }if(source<8){
          
           Integer temp_a = Integer.parseInt(register[0], 16);
           Integer temp_m = Integer.parseInt(register[source], 16);
           int result=temp_a+temp_m;
           register[0]=Integer.toHexString(result);
           if(register[0].length()>2){
            carry=1;
           }
           display();
          
          }
         }
         //ADC R AND M
         if(s1.equalsIgnoreCase("ADC")){
          int source=whichregister(keyword,s2.charAt(0));
          if(s2.charAt(0)=='M'){
           String memadrs=register[5]+register[6];
           Integer decimal_mem = Integer.parseInt(memadrs, 16);
           Integer temp_a = Integer.parseInt(register[0], 16);
           Integer temp_m = Integer.parseInt(A[decimal_mem], 16);
           int result=temp_a+temp_m+carry;
           register[0]=Integer.toHexString(result);
           if(carry==0){
            carry=1;
           }else{
            carry=0;
           }
           display();
          }if(source<8){
          
           Integer temp_a = Integer.parseInt(register[0], 16);
           Integer temp_m = Integer.parseInt(register[source], 16);
           int result=temp_a+temp_m+carry;
           register[0]=Integer.toHexString(result);
           if(carry==0){
            carry=1;
           }else{
            carry=0;
           }
           display();
          
          }
         
         }
         // ADI INS
         if(s1.equalsIgnoreCase("ADI")){
          if(s2.length()>2){
           txt_remark.setText("invalid address");
          }else{
           txt_remark.setText(s2);
          // register[0]="0";
           Integer decimal_mem = Integer.parseInt(s2, 16);
           Integer decimal_a = Integer.parseInt(register[0], 16);
           decimal_a=decimal_a+decimal_mem;
           register[0]=Integer.toHexString(decimal_a);
           txt_remark.append(Integer.toHexString(decimal_a));
           if(register[0].length()>2){
            carry=1;
           }
           display();
          }
         }
         // ACI INS
         if(s1.equalsIgnoreCase("ACI")){
          if(s2.length()>2){
           txt_remark.setText("invalid address");
          }else{
           txt_remark.setText(s2);
           //register[0]="0";
           Integer decimal_mem = Integer.parseInt(s2, 16);
           Integer decimal_a = Integer.parseInt(register[0], 16);
           decimal_a=decimal_a+decimal_mem+carry;
           register[0]=Integer.toHexString(decimal_a);
           txt_remark.append(Integer.toHexString(decimal_a));
           if(carry==0){
            carry=1;
           }else{
            carry=0;
           }
           if(register[0].length()>2){
            carry=1;
           }
           display();
          }
         }
         //DAD
         //
         //SUB
         if(s1.equalsIgnoreCase("SUB")){
          int source=whichregister(keyword,s2.charAt(0));
          if(s2.charAt(0)=='M'){
           String memadrs=register[5]+register[6];
           Integer decimal_mem = Integer.parseInt(memadrs, 16);
           Integer temp_a = Integer.parseInt(register[0], 16);
           Integer temp_m = Integer.parseInt(A[decimal_mem], 16);
           int result=temp_a-temp_m;
           register[0]=Integer.toHexString(result);
           if(result<0){
            carry=1;
           }
           display();
          }if(source<8){
          
           Integer temp_a = Integer.parseInt(register[0], 16);
           Integer temp_m = Integer.parseInt(register[source], 16);
           int result=temp_a-temp_m;
           register[0]=Integer.toHexString(result);
           if(result<0){
            carry=1;
            sign=1;
           }
           display();
          
          }
         }
         //SBB
         if(s1.equalsIgnoreCase("SBB")){
          int source=whichregister(keyword,s2.charAt(0));
          if(s2.charAt(0)=='M'){
           String memadrs=register[5]+register[6];
           Integer decimal_mem = Integer.parseInt(memadrs, 16);
           Integer temp_a = Integer.parseInt(register[0], 16);
           Integer temp_m = Integer.parseInt(A[decimal_mem], 16);
           int result=temp_a-temp_m-carry;
           register[0]=Integer.toHexString(result);
           if(carry==0){
            carry=1;
           }else{
            carry=0;
           }
           display();
          }if(source<8){
          
           Integer temp_a = Integer.parseInt(register[0], 16);
           Integer temp_m = Integer.parseInt(register[source], 16);
           int result=temp_a-temp_m-carry;
           register[0]=Integer.toHexString(result);
           if(carry==0){
            carry=1;
           
           }else{
            carry=0;
           }
           display();
          
          }
         
         }
         //SUI
         if(s1.equalsIgnoreCase("SUI")){
          if(s2.length()>2){
           txt_remark.setText("invalid address");
          }else{
           txt_remark.setText(s2);
          // register[0]="0";
           Integer decimal_mem = Integer.parseInt(s2, 16);
           Integer decimal_a = Integer.parseInt(register[0], 16);
           decimal_a=decimal_a-decimal_mem;
           register[0]=Integer.toHexString(decimal_a);
           txt_remark.append(Integer.toHexString(decimal_a));
           if(decimal_a<0){
            sign=1;
            carry=1;
           }
           if(decimal_a==0){
            zero=1;
           }
           display();
          }
         }
         //SBI
         if(s1.equalsIgnoreCase("SBI")){
          if(s2.length()>2){
           txt_remark.setText("invalid address");
          }else{
           txt_remark.setText(s2);
           //register[0]="0";
           Integer decimal_mem = Integer.parseInt(s2, 16);
           Integer decimal_a = Integer.parseInt(register[0], 16);
           decimal_a=decimal_a-decimal_mem-carry;
           register[0]=Integer.toHexString(decimal_a);
           txt_remark.append(Integer.toHexString(decimal_a));
           if(carry==0){
            carry=1;
           }else{
            carry=0;
           }
           if(decimal_a<0){
            carry=1;
            sign=1;
           }
           if(decimal_a==0){
            zero=0;
           }
          
           display();
          }
         }
         //INR increment function
         if(s1.equalsIgnoreCase("INR")){
          int errorcase1=search(keyword,s2.charAt(0));
          if(errorcase1==0){
           txt_remark.setText("Sysntax error \n pls provide valid register or memory");
          }
          if(s2.charAt(0)=='M'){
           String memadrs=register[5]+register[6];
           Integer decimal_mem = Integer.parseInt(memadrs, 16);
           Integer temp_m = Integer.parseInt(A[decimal_mem], 16);
           temp_m=temp_m+1;
           A[decimal_mem]=Integer.toHexString(temp_m);
           display();
           txt_remark.append("\nAddress="+memadrs);
           txt_remark.append("  value="+A[decimal_mem]);
          }
          int source=whichregister(keyword,s2.charAt(0));
          if(source>=0&&source<=6){
           Integer temp_m = Integer.parseInt(register[source], 16);
           temp_m=temp_m+1;
           register[source]=Integer.toHexString(temp_m);
           display();
          }
         
         
         
         }
         //INX INCREASE REGISTER PAIR
         if(s1.equalsIgnoreCase("INX")){
          int errorcase1=search(keyword,s2.charAt(0));
          if(s2.charAt(0)=='M'||errorcase1==0){
           txt_remark.setText("Syntax error");
          }
          int source=whichregister(keyword,s2.charAt(0));
          if(source==1||source==2){
           String memadrs=register[1]+register[2];
           Integer temp_m = Integer.parseInt(memadrs, 16);
           temp_m=temp_m+1;
           register[1]=Integer.toHexString(temp_m).substring(0,2);
           register[2]=Integer.toHexString(temp_m).substring(2,4);
           display();
          
          }
          if(source==3||source==4){
           String memadrs=register[3]+register[4];
           Integer temp_m = Integer.parseInt(memadrs, 16);
           temp_m=temp_m+1;
           register[3]=Integer.toHexString(temp_m).substring(0,2);
           register[4]=Integer.toHexString(temp_m).substring(2,4);
           display();
          
          }
          if(source==5||source==6){
           String memadrs=register[5]+register[6];
           Integer temp_m = Integer.parseInt(memadrs, 16);
           temp_m=temp_m+1;
           register[5]=Integer.toHexString(temp_m).substring(0,2);
           register[6]=Integer.toHexString(temp_m).substring(2,4);
           display();
          
          }
         }
         //DECREMENT
         if(s1.equalsIgnoreCase("DCR")){
          int errorcase1=search(keyword,s2.charAt(0));
          if(errorcase1==0){
           txt_remark.setText("Sysntax error \n pls provide valid register or memory");
          }
          if(s2.charAt(0)=='M'){
           String memadrs=register[5]+register[6];
           Integer decimal_mem = Integer.parseInt(memadrs, 16);
           Integer temp_m = Integer.parseInt(A[decimal_mem], 16);
           temp_m=temp_m-1;
           A[decimal_mem]=Integer.toHexString(temp_m);
           display();
           txt_remark.append("\nAddress="+memadrs);
           txt_remark.append("  value="+A[decimal_mem]);
          }
          int source=whichregister(keyword,s2.charAt(0));
          if(source>=0&&source<=6){
           Integer temp_m = Integer.parseInt(register[source], 16);
           temp_m=temp_m-1;
           register[source]=Integer.toHexString(temp_m);
           display();
          }
         
         
         
         }
         //INX INCREASE REGISTER PAIR
         if(s1.equalsIgnoreCase("DCX")){
          int errorcase1=search(keyword,s2.charAt(0));
          if(s2.charAt(0)=='M'||errorcase1==0){
           txt_remark.setText("Syntax error");
          }
          int source=whichregister(keyword,s2.charAt(0));
          if(source==1||source==2){
           String memadrs=register[1]+register[2];
           Integer temp_m = Integer.parseInt(memadrs, 16);
           temp_m=temp_m-1;
           register[1]=Integer.toHexString(temp_m).substring(0,2);
           register[2]=Integer.toHexString(temp_m).substring(2,4);
           display();
          
          }
          if(source==3||source==4){
           String memadrs=register[3]+register[4];
           Integer temp_m = Integer.parseInt(memadrs, 16);
           temp_m=temp_m-1;
           register[3]=Integer.toHexString(temp_m).substring(0,2);
           register[4]=Integer.toHexString(temp_m).substring(2,4);
           display();
          
          }
          if(source==5||source==6){
           String memadrs=register[5]+register[6];
           Integer temp_m = Integer.parseInt(memadrs, 16);
           temp_m=temp_m-1;
           register[5]=Integer.toHexString(temp_m).substring(0,2);
           register[6]=Integer.toHexString(temp_m).substring(2,4);
           display();
          
          }
         }
         //SET CARRY FLAG TO 1
         if(s1.equalsIgnoreCase("STC")){
          carry=1;
          display();
         }
         //complemeny carry flag
         if(s1.equalsIgnoreCase("CMC")){
          if(carry==1){
           carry=0;
           display();
          }else
          {
           carry=1;
           display();
          }
         
         
         }
         if(s1.equalsIgnoreCase("CMP")){
          int errorcase1=search(keyword,s2.charAt(0));
          int source=whichregister(keyword,s2.charAt(0));
          if(errorcase1==0){
           txt_remark.setText("Syntax error");
          }
          if(s2.charAt(0)=='M'){
           String memadrs=register[5]+register[6];
           Integer decimal_mem = Integer.parseInt(memadrs, 16);
           Integer temp_m = Integer.parseInt(A[decimal_mem], 16);
           Integer temp_a = Integer.parseInt(register[0], 16);
           if(temp_a==temp_m){
            carry=0;zero=1;
            display();
           }
           if(temp_a<temp_m){
            carry=1;zero=0;
            display();
           }
           if(temp_a>temp_m){
            carry=0;zero=0;
            display();
           }
          
          }
          if(source>=0&&source<=6){
           Integer temp_r = Integer.parseInt(register[source], 16);
           Integer temp_a = Integer.parseInt(register[0], 16);
           if(temp_a==temp_r){
            carry=0;zero=1;
            display();
           }
           if(temp_a<temp_r){
            carry=1;zero=0;
            display();
           }
           if(temp_a>temp_r){
            carry=0;zero=0;
            display();
           }
          }
         
         }
         if(s1.equalsIgnoreCase("HLT")){
          display();
         }
         if(s1.length()>4){
          txt_remark.setText("Syntax error \n this instruction is not included pls contact shaharyar shauat");
         }
        
        
  
 }
 //ends
 //SEARCH FUNCTION
 public int search(char schar[], char sch){
  int found=0;
  for(int si=0; si<schar.length;si++){
   if(sch==schar[si]){
    found=1;
   } 
  }
  return found;
 }
 //SEARCH ENDS HERE
 //which register
 public int whichregister(char schar[], char sch){
  int whchreg=8;
  for(int si=0;si<7;si++){
   if(sch==schar[si]){
    whchreg=si;
   }
  }
  return whchreg;
 }
 //ends here
 // display function
 public void display(){
  txt_remark.setText("");
  txt_remark.append("No error");
  txt_remark.append("\nA="+register[0]);
  txt_remark.append("\nB="+register[1]);txt_remark.append("  C="+register[2]);
  txt_remark.append("\nD="+register[3]);txt_remark.append("  E="+register[4]);
  txt_remark.append("\nH="+register[5]);txt_remark.append("  L="+register[6]);
  Tex_Acc.setText(register[0]);
  Tex_Reg_B.setText(register[1]);
  Tex_Reg_C.setText(register[2]);
  Tex_Reg_D.setText(register[3]);
  Tex_Reg_E.setText(register[4]);
  Tex_Reg_H.setText(register[5]);
  Tex_Reg_L.setText(register[6]);
  Tex_cF.setText(String.valueOf(carry));
  Tex_sF.setText(String.valueOf(sign));
  Tex_zF.setText(String.valueOf(zero));
  Tex_pF.setText(String.valueOf(paritory));
  Tex_Reg_Stack.setText(store_stack);
  Tex_Pro_Coun.setText(store_pc);
 }
 //ends here
 //all zero initialization of memory and register
 public static void all_zero_initialization(){
  for(int i=0;i<65536;i++){
   A[i]=String.valueOf(0);
  }
  for(int i=0;i<7;i++){
   register[i]=String.valueOf(0);
  }
  carry=0;sign=0;zero=0;
  paritory=0;
 }
 //
 
 public static void main (String args[]){
  new emulator();
 }

}

