/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicnetworkserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import systems.software.Member;
import systems.software.Posts;
import systems.software.Songs;


/**
 *
 * @author LiamF
 */
public class MusicNetworkgui extends javax.swing.JFrame {

    //globals
    private ArrayList<Member> member = new ArrayList<>();
    final private ArrayList<Posts> posts = new ArrayList<>();
    
    
    /**
     * Creates new form MusicNetworkgui
     */
    public MusicNetworkgui() {
        initComponents();
        Begin();
    }
    void Begin(){
        new Thread (this::signUp).start();
        
        new Thread (this::signOut).start();
        
        new Thread (this::postsSend).start();
        
        new Thread(this::readUser).start();
        
        new Thread (this::postReceive).start();
                        
        new Thread (this::songInfoSend).start();
        
        new Thread (this::songReceive).start();
        
        new Thread (this::onlinePepes).start();
        
        new Thread (this::sendSong).start();
                
    }
   private void sendSong(){
       try{
            ServerSocket socket = new ServerSocket(9999);
            Socket client = socket.accept();
            
            //recieve request for song name
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            String requestedSongName = (String)ois.readObject();
            
            
            //load all songs from txt file into array
            ArrayList<Songs> allSongs = new ArrayList<>();
            ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream("UserDatabase/songs.txt"));
            allSongs= (ArrayList<Songs>)fileReader.readObject();
            
            String songPath = null;
            
            for(Songs s: allSongs){
                if(s.songName.equals(requestedSongName)){
                    songPath = "music/" + s.songName;
                }
            }
            
            if(songPath != null){
                 File song = new File(songPath);
                               
                 byte[] byteArray = new byte [(int) song.length()];
                 
                 //get file
                 BufferedInputStream bis = new BufferedInputStream(new FileInputStream(song));
                 bis.read(byteArray,0,byteArray.length);
                 
                 OutputStream sendingSong = client.getOutputStream();
                 
                 sendingSong.write(byteArray,0,byteArray.length);
                 sendingSong.flush();
            }else{
                OutputStream sendingSong = client.getOutputStream();
                sendingSong.write(null);
            }
           
            
            socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
   }

    private void signUp() {
        while(true){
        try{
            ServerSocket socket = new ServerSocket(2222);
            Socket client = socket.accept();
            //receive and read member
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            Member receiveMember = (Member)inputStream.readObject();
            //read database array from file
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("UserDatabase/members.txt"));
            ArrayList<Member> database = (ArrayList<Member>)is.readObject();
                            
            //add to list of online members
            boolean added = false;
            //loop through member list
            for(int x = 0; x < member.size(); x++)
            {
                //if member is already in the list then update info by
                //replacing the whole member with new member
                if(receiveMember.userName.equals(member.get(x).userName)){
                    member.set(x, receiveMember);
                    added = true;
                    break;
                }
            }
            //if member isnt already in the list then add them in
            if(!added){
                member.add(receiveMember);
            }
            
            //add account to database
            added = false;
            for(int x = 0; x < database.size(); x++)
            {
                //set member array to database array
                if(receiveMember.userName.equals(database.get(x).userName)){
                    database.set(x, receiveMember);
                    added = true;
                    break;
                }
            }
            if(!added){
                database.add(receiveMember);
            }
            //save database to text file
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("UserDatabase/members.txt"));
            out.writeObject(database);
            socket.close();  
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
                
        }
    }
    
    private void signOut() {
        while(true){
        try{
            ServerSocket socket = new ServerSocket(3333);
            Socket client = socket.accept();
            
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            Member receiveMember = (Member)inputStream.readObject();

            ArrayList<Member> allMembers = new ArrayList<>();
            for(Member m: member){
                if(!(m.userName.equals(receiveMember.userName))){
                    allMembers.add(m);
                }
            }
            member = allMembers;
            
            socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
                
        }
    }
    
    public void readUser(){
        while (true){
            try{
            ServerSocket socket = new ServerSocket(4444);
            Socket client = socket.accept();
            
            ObjectInputStream getUsername = new ObjectInputStream(client.getInputStream());
            String Username = (String)getUsername.readObject();
            
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("UserDatabase/members.txt"));
            ArrayList<Member> Database = new ArrayList<>();
            Database = (ArrayList<Member>)is.readObject();
            
            for(Member person: Database){
                if(person.userName.equals(Username)){
                    ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
                    os.writeObject(person);
                }
            }
                       
            socket.close();
            }catch (Exception e){
            System.out.println(e.getMessage());
            }
        }
        
    }
    
    private void onlinePepes(){
        while(true){
        try{
            ServerSocket socket = new ServerSocket(6666);
            Socket client = socket.accept();
            
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            String receiveReq = (String)inputStream.readObject();
            
            ObjectOutputStream outputStream = new ObjectOutputStream (client.getOutputStream());
            outputStream.writeObject(member);
            socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
                
        }
    }
    
    public Member sendUser(){
        while(true){
        try{
            ServerSocket socket = new ServerSocket(5555);
            Socket client = socket.accept();
            
            socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
                
        }
    }
    
      private void songReceive(){
        while(true){
        try{
            ServerSocket socket = new ServerSocket(6969);
            Socket client = socket.accept();

            byte[] thebyte = new byte[8000000];//make massive []
            InputStream is = client.getInputStream();
            DataInputStream d = new DataInputStream(is); //data input transfers
            
            String[] infomationArray = d.readUTF().split(","); //seperate
            String fileName = infomationArray[0];//assign
            String Uname = infomationArray[1];//assign
            
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("music/"+ fileName)); //puts file in buffer again
         int count;
         while((count = client.getInputStream().read(thebyte))>1000){
             bos.write(thebyte,0,count);            
         }
         
         //load all songs from txt file into array
         
         ArrayList<Songs> allSongs = new ArrayList<>();
         ObjectInputStream ois = new ObjectInputStream(new FileInputStream("UserDatabase/songs.txt"));
        allSongs = (ArrayList<Songs>)ois.readObject();
                 
         //add new song to array
         Songs s = new Songs();
         s.user = Uname;
         s.songName = fileName;
         allSongs.add(s);
         
         //save array to txt file
         ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("UserDatabase/songs.txt"));
         oos.writeObject(allSongs);
           
            socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
                
        }
    }
    
    private void postsSend(){
        while(true){
            try{
            ServerSocket socket = new ServerSocket(1234);
            Socket client = socket.accept();
            
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream()); 
            oos.writeObject(posts);
            
            socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    }
    private void postReceive(){
        while(true){
            try{
            ServerSocket socket = new ServerSocket(0001);
            Socket client = socket.accept();
            
            ObjectInputStream oos = new ObjectInputStream(client.getInputStream()); 
            Posts newPost = (Posts)oos.readObject();
            
            posts.add(newPost);
            
            socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
                
        }
    }

    private void songInfoSend(){
        while(true){
            try{
            ServerSocket socket = new ServerSocket(8888);
            Socket client = socket.accept();
            
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream()); 
            
            //load all songs from txt file into array
            ArrayList<Songs> allSongs = new ArrayList<>();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("UserDatabase/songs.txt"));
            allSongs = (ArrayList<Songs>)ois.readObject();
            
            oos.writeObject(allSongs);
            
            socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
                
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Refresh = new javax.swing.JButton();
        onlinePeeps = new java.awt.List();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Refresh.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Refresh.setText("Refresh");
        Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshActionPerformed(evt);
            }
        });

        onlinePeeps.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Online Members:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(onlinePeeps, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(onlinePeeps, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
        onlinePeeps.removeAll();
        for(Member m: member){
            onlinePeeps.add(m.userName);
        }      
    }//GEN-LAST:event_RefreshActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MusicNetworkgui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MusicNetworkgui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MusicNetworkgui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MusicNetworkgui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MusicNetworkgui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Refresh;
    private javax.swing.JLabel jLabel1;
    private java.awt.List onlinePeeps;
    // End of variables declaration//GEN-END:variables
}
