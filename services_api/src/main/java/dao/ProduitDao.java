package dao;

import entity.Produit;

import javax.persistence.*;
import java.util.List;

public class ProduitDao {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");

    // Suppression du constructeur inutile
    public ProduitDao(EntityManagerFactory entityManagerFactory) {}

    // Récupérer tous les enregistrements de Produit
    public List<Produit> getAllProduits() throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();  // Initialisation
            // Utilisation d'une query typée pour retourner une liste de Produit
            return entityManager.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Error while retrieving all Produits", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();  // Toujours fermer l'EntityManager
            }
        }
    }

    // Récupérer un enregistrement de Produit par ID
    public Produit getProduitById(int id) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();  // Initialisation
            return entityManager.find(Produit.class, id);
        } catch (Exception e) {
            throw new Exception("Error while retrieving Produit by ID", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();  // Toujours fermer l'EntityManager
            }
        }
    }

    // Créer un nouveau Produit
    public void createProduit(Produit produit) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();  // Initialisation
            entityManager.getTransaction().begin();  // Démarrer la transaction
            entityManager.persist(produit);  // Ajouter le produit
            entityManager.getTransaction().commit();  // Valider la transaction
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();  // Annuler la transaction si une erreur survient
            }
            throw new Exception("Error while adding a Produit", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();  // Toujours fermer l'EntityManager
            }
        }
    }

    // Mettre à jour un enregistrement de Produit existant
    public void updateProduit(Produit produit) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();  // Initialisation
            entityManager.getTransaction().begin();  // Démarrer la transaction
            entityManager.merge(produit);  // Mettre à jour le produit
            entityManager.getTransaction().commit();  // Valider la transaction
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();  // Annuler la transaction en cas d'erreur
            }
            throw new Exception("Error while updating Produit", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();  // Toujours fermer l'EntityManager
            }
        }
    }

    // Supprimer un enregistrement de Produit
    public void deleteProduit(Produit produit) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();  // Initialisation
            entityManager.getTransaction().begin();  // Démarrer la transaction
            produit = entityManager.merge(produit);  // S'assurer que le produit est dans un état managé
            entityManager.remove(produit);  // Supprimer le produit
            entityManager.getTransaction().commit();  // Valider la transaction
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();  // Annuler la transaction en cas d'erreur
            }
            throw new Exception("Error while deleting Produit", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();  // Toujours fermer l'EntityManager
            }
        }
    }
}
