/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import DAO.SensoresDao;
import DAO.TipoSensoresDao;



/**
 *
 * @author Dell
 */
public class Sistema {
    public static void main(String[] args) {
        
        
        TipoSensores t= new TipoSensores();
        t.setTipo("SonProm");
        t.setNombre("Modulo Sonido Promediado");
        t.setMinPermitido(0);
        t.setMaxPermitido(1023);
        t.setNumHoras(1);
        t.setPromedio(true);
        
        TipoSensoresDao.insertarTipo(t);
     
        
        Sensores s = new Sensores();
    
        
        s.setId_tipo(2);
        s.setTipo("Son");
        s.setUbicacion("Central");
        
        SensoresDao.insertarSensores(s);


    }
}
