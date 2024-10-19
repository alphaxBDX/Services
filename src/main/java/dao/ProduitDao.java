package dao;

import entity.Produit;

import javax.persistence.*;
import java.util.List;

public class ProduitDao {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceM2Chausson");

    public ProduitDao(EntityManagerFactory entityManagerFactory) {}

    // Récupérer tous les Produits
    public List<Produit> getAllProduits() throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            return entityManager.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupérations des produits", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    // Récupérer un produit par ID
    public Produit getProduitById(int id) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            return entityManager.find(Produit.class, id);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération d'un produit par ID", e);
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
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(produit);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new Exception("Erreur lors de la création d'un produit", e);
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
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(produit);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
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
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            produit = entityManager.merge(produit);
            entityManager.remove(produit);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
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