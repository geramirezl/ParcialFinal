/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidad.Historicos;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Dell
 */
public class HistoricosDao {
    private static EntityManagerFactory
            emf=Persistence.createEntityManagerFactory("BonoPU");
    
   

    public static void insertarHistoricos(Historicos object){
           EntityManager em= emf.createEntityManager();
           em.getTransaction().begin();
           try{
               em.persist(object);
               em.getTransaction().commit();
           }catch(Exception e){
               e.printStackTrace();
               em.getTransaction().rollback();
           }finally{
               em.close();
           }

       }
    
    public static boolean eliminarHistoricos(Historicos object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        boolean ret = false;
        try {
            em.remove(object);
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            return ret;
        }
    }
    
    
    public static Historicos leerHistoricos(Historicos object) {
        EntityManager em = emf.createEntityManager();
        Historicos res = null;
        Query q = em
                .createQuery("SELECT h FROM Historicos h " + "WHERE  h.id_historico LIKE :id_historico"
                        + " AND h.idSensor LIKE :idSensor")
                .setParameter("id_historico", object.getId_historico()).setParameter("idSensor", object.getIdSensor());

        try {
            res = (Historicos) q.getSingleResult();
        } catch (NonUniqueResultException e) {
            res = (Historicos) q.getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            return res;
        }
    }
    
    
    public static ArrayList<Historicos> leerVariosHistoricos(int id_sensor)  {
        EntityManager em = emf.createEntityManager();
        List<Historicos> res = null;
        Query q = em
                .createQuery("SELECT h FROM Historicos h " + "WHERE h.idSensor =:id " + "ORDER BY h.id_historico DESC")
                .setParameter("id", id_sensor);

        String fecha="";
            SimpleDateFormat dateFormat = new 
                SimpleDateFormat ("yyyy-MM-DD HH:mm:ss");
            
            Calendar calendar = GregorianCalendar.getInstance();
            int dia;
        
        try {
            res = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            ArrayList<Historicos> l = new ArrayList<>(res.size());
            if (res.size() > 5) {
                for (int i = 0; i < 5; i++) {
                      
                        l.add(res.get(i));
                    
                    
                }
            } else {
                  
                        l.addAll(res);
                    
                    
                }
            
                return l;
            }

            
    }

    
    
    public static ArrayList<Historicos> leerVariosHistoricosT(int id_sensor) {
        EntityManager em = emf.createEntityManager();
        List<Historicos> res = null;
        Query q = em
                .createQuery("SELECT h FROM Historicos h " + "WHERE h.idSensor =:id " + "ORDER BY h.id_historico DESC")
                .setParameter("id", id_sensor);

        try {
            res = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            ArrayList<Historicos> l = new ArrayList<>(res.size());
            
            l.addAll(res);
          
            return l;
        }

    }
    
    public static Historicos leerUltimoHistoricos(int id_sensor) {
        EntityManager em = emf.createEntityManager();
        Historicos res = null;
        Query q = em
                .createQuery("SELECT h FROM Historicos h " + "WHERE h.idSensor =:id " + "ORDER BY h.id_historico DESC")
                .setParameter("id", id_sensor);

        try {
            res = (Historicos) q.getSingleResult();
        } catch (NonUniqueResultException e) {
            res = (Historicos) q.getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            return res;
        }
    }
    
    public static boolean actualizarHistoricos(Historicos object, Historicos new_object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        boolean ret = false;

        try {
            object = leerHistoricos(object);
            object.setIdSensor(new_object.getIdSensor());
            object.setValorTomado(new_object.getValorTomado());
            object.setFechaHora(new_object.getFechaHora());
            object.setProcesamiento(new_object.getProcesamiento());
            em.getTransaction().commit();
            ret= true;

        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            return ret;
        }
    }
}
