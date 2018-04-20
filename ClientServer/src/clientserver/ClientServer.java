/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserver;

import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import systems.software.Message;

/**
 *
 * @author Sam King
 */
public class ClientServer {
    
    ArrayList<String> list = new ArrayList();
    ArrayList<Message> Messagelist = new ArrayList();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    void Register()
    {
        //List of all members online
        //User logs in and sends username to the server
        while(true)
        {
            try{
                //create new server socket
                ServerSocket SS = new ServerSocket(1010);
                //Keep checking for message
                Socket reg = SS.accept();
                DataInputStream DIS = new DataInputStream(reg.getInputStream());
                String UserToReg = DIS.readUTF();
                
                //Adding username to the register list
                list.add(UserToReg);
                                         
            }catch(Exception E){
            System.out.println("Network Error:" + E.getMessage());
            }
        }
    }
    
    void ReceiveMessage()
    {   
        //Message is sent from user to server 
        //Message is receiced, read and put into a list
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
                
                //Add to message array list
                Messagelist.add(MessageSending);
                                                           
            }catch(Exception E){
                System.out.println("Network Error:" + E.getMessage());
            }            
        }    
    }
    
    void sendMessage()
    {
        //User will send username to the server
        //Checks if the user has a message the sends message
        while(true){
            try{
                //Create new Server Socket
                ServerSocket ss = new ServerSocket(1020);
                Socket s = ss.accept();                             
                DataInputStream DIS = new DataInputStream(s.getInputStream());
                String username = DIS.readUTF();
                
                //Loop through messagelist array if username=Receiver send message
                for(Message M:Messagelist)
                {
                    if(username.equals(M.Receiver)){
                        ObjectOutputStream OOS = new ObjectOutputStream(s.getOutputStream());
                        Message MessageToSend = new Message();

                        OOS.writeObject(MessageToSend);
                    } 
                }

            }catch(Exception E){
                System.out.println("Network Error:" + E.getMessage());
            }
         }
    }
    
    
    
    
}
