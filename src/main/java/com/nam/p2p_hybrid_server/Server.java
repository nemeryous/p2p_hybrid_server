/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nam.p2p_hybrid_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAM
 */
public class Server {
    ServerSocket serverSocket;
    Set<Client> clients;

    public Server() {
        clients = new HashSet<>();
        try {
            this.serverSocket = new ServerSocket(9999);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                Client newClient = new Client(clientSocket);
                clients.add(newClient);
                Thread thread = new Thread(newClient);
                thread.start();
                System.out.println(clients.size());

//                 Setup Peer's server port
                setupClientServerPort(newClient);

//                Send all peer's server port for new peer
                sendAllPeerPortForClient(newClient);
                
                
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public void setupClientServerPort(Client client) {
        client.setClientServerPort(clients.size());
       
    }
    
    public void sendAllPeerPortForClient(Client directClient) {
        List<Integer> peerServerPorts = new ArrayList<>();
        
        for (Client client : clients) {
                    if (!client.equals(directClient)) {
                        peerServerPorts.add(client.getClientServerPort());
                    }
                }
        directClient.sendAllServerPortForPeer(peerServerPorts);
    }
    
    
}
