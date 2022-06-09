/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anna9
 */
public class Client {
    
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;
    private Integer port = 5056;
    
    public void start() throws IOException {
        try {
            
            InetAddress ip = InetAddress.getByName("localhost");
            
            socket = new Socket(ip, port);
            
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            
            dos.writeInt(1);
            dos.writeUTF("annareyes");
            dos.writeUTF("5172B75E9E6CF611AA6E79DA990F2E04");


            String login = dis.readUTF();
            if (!login.equals("")) {
                System.out.println(login);
            } else {
                System.out.println("Usuari o contrasenya incorrectes");
            }
            
            dos.writeInt(-1);
            
            System.out.println("Tancant aquesta connexió : " + socket);
            socket.close();
            System.out.println("Connexió tancada");
            
        } catch(Exception e){
            
            e.printStackTrace();
        }
        
        try {
            this.dis.close();
            this.dos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
}
