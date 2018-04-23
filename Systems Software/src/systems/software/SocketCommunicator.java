/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.software;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Sam King
 */
public class SocketCommunicator 
{
//Music Network Server
    public void signUp(Member member) {
        
        try{
            Socket serverConnection = new Socket("localhost",2222);
            
            ObjectOutputStream OutputStream = new ObjectOutputStream(serverConnection.getOutputStream());
            OutputStream.writeObject(member);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    
    public Member getUser(String username){
        Member returnMember = null;
    try{
            Socket serverConnection = new Socket("localhost",4444);
            
            ObjectOutputStream OutputStream = new ObjectOutputStream(serverConnection.getOutputStream());
            OutputStream.writeObject(username);
            
            ObjectInputStream in = new ObjectInputStream(serverConnection.getInputStream());
            returnMember = (Member)in.readObject();
            return returnMember;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return returnMember;
        }
    }
            //send song thing that takes path and name
   // make new file with path 
     //make byte array from that file Files.readAllBytes(myfile.toPath());
     //writeObject(byteArray)
     //
    
    void sendSong(String fileLocal,String fileName, String clientName){
        
        try{
             Socket serverConnection = new Socket("localhost",6969);
             
             File song = new File(fileLocal);// file got
             byte[] byteArray = new byte [(int) song.length()]; // array size = file size
             BufferedInputStream bis = new BufferedInputStream(new FileInputStream(song)); // in the buffer
             bis.read(byteArray,0,byteArray.length); //put the buffer in byte array
                    
                OutputStream os = serverConnection.getOutputStream();
        
                    DataOutputStream d = new DataOutputStream(os);
                    d.writeUTF(fileName + "," + clientName); //send name and username to UTF
                    os.write(byteArray,0,byteArray.length); //create the actual byte array
                    
                    os.flush(); //empty os
                    
            }catch(Exception e){
                 System.out.println(e.getMessage());
           
            };
    }
    
     public ArrayList<Member> onlinePepes(){
         ArrayList<Member> returnArray = new ArrayList<>();
         try{
            Socket serverConnection = new Socket("localhost",6666);
            
            ObjectOutputStream OutputStream = new ObjectOutputStream(serverConnection.getOutputStream());
            OutputStream.writeObject("");
            
            ObjectInputStream In = new ObjectInputStream(serverConnection.getInputStream());
            returnArray = (ArrayList<Member>)In.readObject();
            
            return returnArray;
         }
         catch
             (Exception e){
            System.out.println(e.getMessage());
             return returnArray;
         }
     }

     
    public void singOut (Member member){
        try{
            Socket serverConnection = new Socket("localhost",3333);
            
            ObjectOutputStream OutputStream = new ObjectOutputStream(serverConnection.getOutputStream());
            OutputStream.writeObject(member);
            
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void logIn(){  
    }
    public void postSend(){
    }
    public void postReceive(){
    }

    
        public void writeToConsole(){
        try{
            Socket s = new Socket("localhost",5555);
            ObjectOutputStream stream = new ObjectOutputStream(s.getOutputStream());
            
            stream.writeObject(new String("hey"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
//Chat Server    
    public void acceptChat()
    {
        
    }
            
    public void sendingMessage(String message, String Sender, String Receiver)
    {
        try{
            
            Socket S = new Socket("localhost", 1000);
            ObjectOutputStream OOS = new ObjectOutputStream(S.getOutputStream());
            Message MessageToSend = new Message();
            MessageToSend.Contents = message;
            MessageToSend.Receiver = Receiver;
            MessageToSend.Sender = Sender;
            
            OOS.writeObject(MessageToSend);
        
        }catch(Exception E){
            System.out.println("Network Error:" + E.getMessage());
        }
        
    }

    
   
              
}
