/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserver;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import systems.software.Message;

/**
 *
 * @author Sam King
 */
public class ClientServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    void SendMessage(){
        while(true){
            try{
                //create new server socket
                ServerSocket SS = new ServerSocket(1000);
                //Keep checking for message
                Socket Chat = SS.accept();
                
                //Input Object
                ObjectInputStream OIS = new ObjectInputStream(Chat.getInputStream()); 
                //Read Object as Message
                Message MessageSending = (Message)OIS.readObject();
                
                //Split Object
                
                //Find Sender
                
                //Send String to Correct User
                
            }catch(Exception E){
                System.out.println("Network Error:" + E.getMessage());
            }
            
        }
    
    }
    
    
    
    
}
