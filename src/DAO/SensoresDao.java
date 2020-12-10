/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidad.Sensores;
import java.util.ArrayList;
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
public class SensoresDao {
    private static EntityManagerFactory
            emf=Persistence.createEntityManagerFactory("BonoPU");

 public static void insertarSensores(Sensores object){
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
  public static boolean eliminarSensores(Sensores object) {
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
    
    
    public static Sensores leerSensores(Sensores object) {
        EntityManager em = emf.createEntityManager();
        Sensores res = null;
        Query q = em
                .createQuery("SELECT s FROM Sensores s " + "WHERE  s.id_Sensor LIKE :id_Sensor"
                        + " AND s.id_tipo LIKE :id_tipo")
                .setParameter("id_Sensor", object.getId_Sensor())
                .setParameter("id_tipo", object.getId_tipo());

        try {
            res = (Sensores) q.getSingleResult();
        } catch (NonUniqueResultException e) {
            res = (Sensores) q.getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            return res;
        }
    }
    
    
    public static Sensores buscarSensores(int id_i) {
        EntityManager em = emf.createEntityManager();
        Sensores res = null;
        Query q = em
                .createQuery("SELECT s FROM Sensores s " + "WHERE s.id_Sensor =:id")
                .setParameter("id",id_i);

        try {
            res = (Sensores) q.getSingleResult();
        } catch (NonUniqueResultException e) {
            res = (Sensores) q.getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            return res;
        }
    }
    
    
    
    
    public static boolean actualizarSensores(Sensores object, Sensores new_object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        boolean ret = false;

        try {
            object = leerSensores(object);
            object.setId_tipo(new_object.getId_tipo());
            object.setUbicacion(new_object.getUbicacion());
            object.setTipo(new_object.getTipo());
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
