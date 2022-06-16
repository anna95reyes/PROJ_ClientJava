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
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.model.Entrada;
import org.milaifontanals.model.Estat;
import org.milaifontanals.model.Projecte;
import org.milaifontanals.model.Tasca;
import org.milaifontanals.model.Usuari;

/**
 *
 * @author anna9
 */
public class Client {
    
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket socket;
    private Integer port = 5056;
    
    public void start() throws Exception {
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            socket = new Socket(ip, port);
            
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            
            String login = casLogin();
            casLlistaProjectes(login);
            casLlistaTasquesAssignades(login, 5, 1);
            casLlistaEstats(login);
            casGetTasca(1);
            casLlistaEntrades(login, 1);
            casLlistaUsuaris(login);
            casGetEntrada(1, 1);
            casGetUsuari(login);
            casAfegirEntrada(1, new Entrada(casNovaEnumeracio(1), new Date(), "Entrada", casGetUsuari(login), null, null));
            casModificarEntrada(1, new Entrada(1, new Date(), "Entrada modificada", casGetUsuari(login), null, null));
            
            oos.writeObject(-1);
            
            System.out.println("Tancant aquesta connexió : " + socket);
            socket.close();
            System.out.println("Connexió tancada");
            
        } catch(Exception e){
            
            e.printStackTrace();
        }
        
        try {
            this.ois.close();
            this.oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    public String casLogin() throws Exception {
        oos.writeObject(1);
        oos.writeObject("annareyes");
        oos.writeObject("5172B75E9E6CF611AA6E79DA990F2E04");

        String login = (String)ois.readObject();
        if (!login.equals("")) {
            System.out.println(login);
        } else {
            System.out.println("Usuari o contrasenya incorrectes");
        }
        return login;
    }
    
    public List<Projecte> casLlistaProjectes(String login) throws Exception {
        oos.writeObject(2);
        oos.writeObject(login);
        List<Projecte> projectes = (List<Projecte>)ois.readObject();
        System.out.println(projectes);
        return projectes;
    }
    
    public List<Tasca> casLlistaTasquesAssignades(String login, Integer estatId, Integer projecteId) throws Exception {
        oos.writeObject(3);
        oos.writeObject(login);
        oos.writeObject(estatId);
        oos.writeObject(projecteId);
        List<Tasca> tasquesAssignades = (List<Tasca>)ois.readObject();
        System.out.println(tasquesAssignades);
        return tasquesAssignades;
    }
    
    public List<Estat> casLlistaEstats (String login) throws Exception {
        oos.writeObject(4);
        oos.writeObject(login);
        List<Estat> estats = (List<Estat>)ois.readObject();
        System.out.println(estats);
        return estats;
    }
    
    public Tasca casGetTasca (Integer tascaId) throws Exception {
        oos.writeObject(5);
        oos.writeObject(tascaId);
        Tasca tasca = (Tasca)ois.readObject();
        System.out.println(tasca);
        return tasca;
    }
    
    public List<Entrada> casLlistaEntrades (String login, Integer tascaId) throws Exception {
        oos.writeObject(6);
        oos.writeObject(login);
        oos.writeObject(tascaId);
        List<Entrada> entrades = (List<Entrada>)ois.readObject();
        System.out.println(entrades);
        return entrades;
    }
    
    public List<Usuari> casLlistaUsuaris(String login) throws Exception {
        oos.writeObject(7);
        oos.writeObject(login);
        List<Usuari> usuaris = (List<Usuari>)ois.readObject();
        System.out.println(usuaris);
        return usuaris;
    }
    
    public Entrada casGetEntrada(Integer idTasca, Integer idEntrada) throws Exception {
        oos.writeObject(8);
        oos.writeObject(idTasca);
        oos.writeObject(idEntrada);
        Entrada entrada = (Entrada)ois.readObject();
        System.out.println(entrada);
        return entrada;
    }
    
    public Usuari casGetUsuari(String login) throws Exception {
        oos.writeObject(9);
        oos.writeObject(login);
        Usuari usuari = (Usuari)ois.readObject();
        System.out.println(usuari);
        return usuari;
    }
    
    public Integer casNovaEnumeracio (Integer idTasca)throws Exception {
        oos.writeObject(10);
        oos.writeObject(idTasca);
        Integer novaEntrada = (Integer)ois.readObject();
        System.out.println(novaEntrada);
        return novaEntrada;
    }
    
    public void casAfegirEntrada (Integer idTasca, Entrada entrada) throws Exception {
        oos.writeObject(11);
        oos.writeObject(idTasca);
        oos.writeObject(entrada);
    }
    
    public void casModificarEntrada (Integer idTasca, Entrada entrada) throws Exception {
        oos.writeObject(12);
        oos.writeObject(idTasca);
        oos.writeObject(entrada);
    }
}
