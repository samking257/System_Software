/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.software;

import java.io.ObjectOutputStream;
import java.net.Socket;

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
    public void songSend(){
    }
    public void songReceive(){
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
    
    public void writeToConsole(){
        try{
            Socket s = new Socket("localhost",5555);
            ObjectOutputStream stream = new ObjectOutputStream(s.getOutputStream());
            
            stream.writeObject(new String("hey"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
              
}
