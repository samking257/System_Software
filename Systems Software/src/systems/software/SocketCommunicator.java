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
