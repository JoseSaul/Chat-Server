package Conection;

import java.io.Serializable;
import java.util.ArrayList;

public class paquete implements Serializable {

    private String usuario, ip, texto;
    private ArrayList<String> conectados;

    public paquete(){

    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public void setTexto(String texto){
        this.texto = texto;
    }

    public ArrayList<String> getConectados() {
        return conectados;
    }

    public String getUsuario(){
        return usuario;
    }

    public String getIp() {
        return ip;
    }

    public String getTexto(){
        return texto;
    }

    public void setConectados(ArrayList<String> conectados) {
        this.conectados = conectados;
    }
}
