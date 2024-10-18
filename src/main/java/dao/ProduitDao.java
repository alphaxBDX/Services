package dao;

import data.entity.Client;
import data.entity.Produit;

import java.util.List;
import javax.persistence.*;

public class ProduitDao {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");

    // Récupérer tous les enregistrements de Produit
    public List<Produit> getAllProduits() throws Exception {
        EntityManager entityManager = null;
        try {
            return entityManager.createQuery("SELECT p FROM Produit p").getResultList();
        } catch (Exception e) {
            throw new Exception("Error while retrieving all Produits", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    // Récupérer un enregistrement de Produit par ID
    public Produit getProduitById(long id) throws Exception {
        EntityManager entityManager = null;
        try {
            return entityManager.find(Produit.class, id);
        } catch (Exception e) {
            throw new Exception("Error while retrieving Produit by ID", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    // Créer un nouveau Produit
    public void createProduit(Produit produit) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(produit);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new Exception("Error while adding a Produit", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    // Mettre à jour un enregistrement de Produit existant
    public void updateProduit(Produit produit) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(produit);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new Exception("Error while updating Produit", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    // Supprimer un enregistrement de Produit
    public void deleteProduit(Produit produit) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager.getTransaction().begin();
            produit = entityManager.merge(produit); // Assurer que le produit est en mode managé
            entityManager.remove(produit);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new Exception("Error while deleting Produit", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
