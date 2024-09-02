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

    Integer currentPort;

    public Server() {
        currentPort = 1;
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
                P2p_hybrid_server.clients.add(newClient);
                Thread thread = new Thread(newClient);
                thread.start();

//              Generate port for new client
//              Setup Peer's server port
                setupClientServerPort(currentPort, newClient);
                currentPort++;

//              Send all peer's server port for new peer
                sendAllPeerPortForClient(newClient);


            } catch (Exception ex) {
                System.out.println("Error in run method of Server Class Thread \n" + ex);
            }
        }
    }

    public void setupClientServerPort(Integer port, Client client) {
        client.setClientServerPort(port);

    }

    public void sendAllPeerPortForClient(Client directClient) {
        List<Integer> peerServerPorts = new ArrayList<>();

        for (Client client : P2p_hybrid_server.clients) {
            if (!client.equals(directClient)) {
                peerServerPorts.add(client.getClientServerPort());
            }
        }
        directClient.sendAllServerPortForPeer(peerServerPorts);
    }

    

}
