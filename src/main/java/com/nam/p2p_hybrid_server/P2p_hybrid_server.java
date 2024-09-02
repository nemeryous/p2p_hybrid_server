/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.nam.p2p_hybrid_server;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author NAM
 */
public class P2p_hybrid_server {

    public static Set<Client> clients = new HashSet<>();

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
