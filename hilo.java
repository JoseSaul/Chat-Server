import Conection.paquete;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class hilo extends Thread{

    private void renviarConectados(ArrayList<String> conectados, paquete paquete) throws IOException {
        for (String c: conectados){
            Socket destino = new Socket(c,9999);
            ObjectOutputStream reenviar = new ObjectOutputStream(destino.getOutputStream());
            reenviar.writeObject(paquete);
            reenviar.close();
        }
    }

    @Override
    public void run() {

            try {
                ServerSocket server = new ServerSocket(9090);
                Socket destino;
                String usuario, ip, mensaje, ipOrigen;
                ArrayList <String> conectados = new ArrayList <String>();
                paquete paquete;

                while (true){
                    Socket socketServer = server.accept(); //Crea un socket y acepta la conexion.

                    //Proceso de mensaje:
                    ObjectInputStream entrada = new ObjectInputStream(socketServer.getInputStream());
                    paquete = (paquete) entrada.readObject();
                    usuario = paquete.getUsuario();
                    ip = paquete.getIp();
                    mensaje = paquete.getTexto();

                    entrada.close();

                    if (!mensaje.equals(" %&OnLiNe&")){
                        if (ip.equals("Multicast")){
                            System.out.println("Usuario: " + usuario + " - Multicast - " + mensaje);
                            renviarConectados(conectados, paquete);
                        }else{
                            System.out.println("Usuario: " + usuario + " - Direccion: " + ip + " - " + mensaje);

                            //Renviar a otro cliente
                            destino = new Socket(ip,9999);
                            ObjectOutputStream reenviar = new ObjectOutputStream(destino.getOutputStream());
                            reenviar.writeObject(paquete);

                            reenviar.close();
                        }
                    } else{
                        //Detectar usuarios:
                        InetAddress location = socketServer.getInetAddress();
                        String ipCliente = location.getHostAddress();
                        System.out.println("Online: " + ipCliente);
                        conectados.add(ipCliente);
                        paquete.setConectados(conectados);  //Paquete con ArrayList
                        renviarConectados(conectados,paquete);
                    }
                }

            } catch (IOException e) {
                System.out.print("Error al conectar.");


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

}
