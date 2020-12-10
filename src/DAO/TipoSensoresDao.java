/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Entidad.TipoSensores;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Dell
 */
public class TipoSensoresDao {
    private static EntityManagerFactory
            emf=Persistence.createEntityManagerFactory("BonoPU");

 public static void insertarTipo(TipoSensores object){
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
 
   public static boolean eliminarTipo(TipoSensores object) {
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
    
    
    public static TipoSensores leerTipo(TipoSensores object) {
        EntityManager em = emf.createEntityManager();
        TipoSensores res = null;
        Query q = em
                .createQuery("SELECT t FROM TipoSensores t " + "WHERE  t.id_Tipo LIKE :id_Tipo"
                        + " AND t.tipo LIKE :tipo")
                .setParameter("id_Tipo", object.getId_Tipo()).setParameter("tipo", object.getTipo());

        try {
            res = (TipoSensores) q.getSingleResult();
        } catch (NonUniqueResultException e) {
            res = (TipoSensores) q.getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            return res;
        }
    }
    
    
    public static TipoSensores buscarTipo(int id_i) {
        EntityManager em = emf.createEntityManager();
        TipoSensores res = null;
        Query q = em.createQuery("SELECT t FROM TipoSensores t " + "WHERE t.id_Tipo = :id").setParameter("id",
                id_i);

        try {
            res = (TipoSensores) q.getSingleResult();
        } catch (NonUniqueResultException e) {
            res = (TipoSensores) q.getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            return res;
        }
    }
    
    
    
    
    public static boolean actualizarTipo(TipoSensores object, TipoSensores new_object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        boolean ret = false;

        try {
            object = leerTipo(object);
            object.setTipo(new_object.getTipo());
            object.setMinPermitido(new_object.getMinPermitido());
            object.setMaxPermitido(new_object.getMaxPermitido());
            object.setPromedio(new_object.isPromedio());
            object.setNumHoras(new_object.getNumHoras());

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