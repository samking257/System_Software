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
            
            //Registering to Chat Server
            Register(member.userName);
            
            serverConnection.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void UpdateDatabase(Member member)
    {
        //same as signUp function accept it doesnt register them again
        try{
            Socket serverConnection = new Socket("localhost",2222);
            
            ObjectOutputStream OutputStream = new ObjectOutputStream(serverConnection.getOutputStream());
            OutputStream.writeObject(member);
                        
            serverConnection.close();
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
            serverConnection.close();
            return returnMember;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return returnMember;
        }
    }

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
            
            serverConnection.close();
            return returnArray;
         }
         catch
             (Exception e){
            System.out.println(e.getMessage());
             return returnArray;
         }
     }
    

    public void signOut (Member member){
        try{
            Socket serverConnection = new Socket("localhost",3333);
            
            ObjectOutputStream OutputStream = new ObjectOutputStream(serverConnection.getOutputStream());
            OutputStream.writeObject(member);
            
            //Unregistering Member from Chat Server
            unRegister(member.userName);
            
            serverConnection.close();
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void writeToConsole(){
        try{
            Socket s = new Socket("localhost",5555);
            ObjectOutputStream stream = new ObjectOutputStream(s.getOutputStream());
            
            stream.writeObject(new String("hey"));
            
            s.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void logIn(){  
    }
    public void postSend(){
    }
    public void postReceive(){
    }
    public void songSend(){
    }
    public void songReceive(){
    }
    


//Chat Server                
    public void sendingMessage(String message, String Sender, String Receiver)
    {
        //Send message object to the server
        try{
            
            Socket S = new Socket("localhost", 1000);
            ObjectOutputStream OOS = new ObjectOutputStream(S.getOutputStream());
            Message MessageToSend = new Message();
            MessageToSend.Contents = message;
            MessageToSend.Receiver = Receiver;
            MessageToSend.Sender = Sender;
            
            OOS.writeObject(MessageToSend);
            
            S.close();
        
        }catch(Exception E){
            System.out.println("Network Error:" + E.getMessage());
        }
        
    }
    
    public void Register(String Username)
    {
        //Send username to the server for registration
        try{
            Socket S = new Socket("localhost", 1010);
            DataOutputStream DOS = new DataOutputStream(S.getOutputStream());
            
            DOS.writeUTF(Username);
            
            S.close();
        }catch(Exception E){
            System.out.println("Network Error:" + E.getMessage());
        }
        
    }
    
    public void unRegister(String Username)
    {
        //Send username to the server for unregistration
        try{
            Socket S = new Socket("localhost", 1030);
            DataOutputStream DOS = new DataOutputStream(S.getOutputStream());
            
            DOS.writeUTF(Username);
            
            S.close();
        }catch(Exception E){
            System.out.println("Network Error:" + E.getMessage());
        }
    }
    
    public Message receiveMessage(String Username)
    {
        //User receives message that show its contents and sender
        //Send username to server to check if theres any messages for them
        try{
            //Send username to server
            Socket S = new Socket("localhost", 1020);
            DataOutputStream DOS = new DataOutputStream(S.getOutputStream());            
            DOS.writeUTF(Username);
            
            //receiver message
            ObjectInputStream OIS = new ObjectInputStream(S.getInputStream());
            Message M = (Message)OIS.readObject();
            
            S.close();
            return M;
            
        }catch(Exception E){
            System.out.println("Network Error:" + E.getMessage());
        }
        return null;
    }
        
    
}
