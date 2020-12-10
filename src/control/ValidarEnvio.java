/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.HistoricosDao;
import DAO.SensoresDao;
import DAO.TipoSensoresDao;
import Entidad.Historicos;
import Entidad.Sensores;
import Entidad.Sistema;
import Entidad.TipoSensores;
import Frontera.FramePrincipal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Dell
 */
public class ValidarEnvio {

    
    public ValidarEnvio(){
        
    }
    
    public static void enviarDatos(int id_Sensor){
        Sensores s = SensoresDao.buscarSensores(id_Sensor);

        TipoSensores t = TipoSensoresDao.buscarTipo(s.getId_Sensor());

        int valor_tomado = (int) (Math.random()*t.getMaxPermitido()+20) ;
        

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        String fecha = LocalDateTime.now().format(formatter);
        
        

        Historicos h = new Historicos();
        
        h.setProcesamiento("Normal");
        h.setIdSensor(id_Sensor);
        h.setFechaHora(fecha);
        h.setValorTomado(valor_tomado);

        HistoricosDao.insertarHistoricos(h);
    }
    
    
    public static ArrayList<Object[]> mostrarHistorico(int id) {
        ArrayList<Historicos> l = HistoricosDao.leerVariosHistoricos(id);
        ArrayList<Object[]> rows = new ArrayList<>();
        Sensores ins = SensoresDao.buscarSensores(id);
        TipoSensores s = TipoSensoresDao.buscarTipo(ins.getId_Sensor());

        Object[] row = new Object[3];
        for (int i = 0; i < l.size(); i++) {
            row = new Object[6];
            row[0] = l.get(i).getIdSensor();
            row[1] = l.get(i).getValorTomado();
            row[2] = l.get(i).getFechaHora();

            rows.add(row);
        }

        return rows;
    }
    
    public static String procesamientoDe(int id) throws ParseException{
        Sensores ins = SensoresDao.buscarSensores(id);
        TipoSensores s = TipoSensoresDao.buscarTipo(ins.getId_Sensor());
        String res="";
        
        
        if(s.isPromedio()){
            ArrayList<Historicos> l = HistoricosDao.leerVariosHistoricosT(id);
            int value=0;
            int row = 0;
            int count=0;
            String fecha="";
            SimpleDateFormat dateFormat = new 
                SimpleDateFormat ("yyyy-MM-DD HH:mm:ss");
            
            Calendar calendar = GregorianCalendar.getInstance();
            Date now=new Date();
            calendar.setTime(now);
            int nowHour=calendar.get(Calendar.HOUR_OF_DAY);
            int regHour;
               
            for (int i = 0; i < l.size(); i++) {
            fecha= l.get(i).getFechaHora();
            Date date1 = dateFormat.parse(fecha);
            calendar.setTime(date1);
            regHour=calendar.get(Calendar.HOUR_OF_DAY);

                if(nowHour-regHour<s.getNumHoras()){
                    row=l.get(i).getValorTomado();
                    count++;
                } 
                else{
                    row=0;
                }
       
                value=value+row;
            }
            
            if(count!=0){value=value/count;}
            
            
            if(value<=s.getMinPermitido()){
                res="1,con el valor promediado: "+value+" de las ultimas "+s.getNumHoras() +" Hora(s)";
            }
            else if(value>=s.getMaxPermitido()){
                res="3, con el valor promediado: "+value+" de las ultimas "+s.getNumHoras() +" Hora(s)";
            }
            else{
                res="2, con el valor promediado: "+value+" de las ultimas "+ s.getNumHoras()+" Hora(s)";
            }
            
        }
        else{
            
            Historicos h=HistoricosDao.leerUltimoHistoricos(id);
            if(h.getValorTomado()<=s.getMinPermitido()){
                res="1,con el valor "+h.getValorTomado();
            }
            else if(h.getValorTomado()>=s.getMaxPermitido()){
                res="3, con el valor "+h.getValorTomado();
            }
            else{
                res="2, con el valor "+h.getValorTomado();
            }
        }
        
        return res;
    }
}
