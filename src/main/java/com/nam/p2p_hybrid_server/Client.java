/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nam.p2p_hybrid_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAM
 */
public final class Client implements Runnable{
    Socket clientSocket;
    DataInputStream dis;
    DataOutputStream dos;
    String name;
    Integer clientServerPort;

    public Client(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.dis = new DataInputStream(clientSocket.getInputStream());
        this.dos = new DataOutputStream(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        
        while (!clientSocket.isClosed()) {
            
            try {
                String messageFromClient;
                messageFromClient = dis.readUTF();
                System.out.println(messageFromClient);
                
                switch (messageFromClient) {
                    case "new_user" -> {
                        String  clientName = dis.readUTF();
                        this.name = clientName;
                        sendMessage("your_name");
                        sendMessage(name);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    clientSocket.close();
                    dis.close();
                    dos.close();
                } catch (IOException ex1) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            
            
        }
        
    }
    public void getOwnServerPort(Integer serverPort) throws IOException {
        sendMessage("own_server_port");
        sendMessage(serverPort.toString());
    }
    
    public void sendMessage(String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
    }

    public Integer getClientServerPort() {
        return clientServerPort;
    }

    public void setClientServerPort(Integer clientServerPort) {
        try {
            this.clientServerPort = clientServerPort;
            sendMessage("own_server_port");
            sendMessage(clientServerPort.toString());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendAllServerPortForPeer(List<Integer> peerServerPort) {
        
        try {
            sendMessage("all_server_ports");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(peerServerPort);
            objectOutputStream.flush();
//            objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
}
