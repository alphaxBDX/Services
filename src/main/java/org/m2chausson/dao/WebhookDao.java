package org.m2chausson.dao;

import org.m2chausson.entities.Client;
import org.m2chausson.entities.Produit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class WebhookDao {

    private final EntityManagerFactory entityManagerFactory;

    public WebhookDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    // Méthodes pour les entités Produit
    public void creerProduit(Produit produit) {
        executeTransaction(em -> em.merge(produit));
    }

    public void majProduit(Produit produit) {
        executeTransaction(em -> em.merge(produit));
    }

    public void supprimerProduit(Produit produit) {
        executeTransaction(em -> {
            Produit managedProduit = em.find(Produit.class, produit.getId());
            if (managedProduit != null) {
                em.remove(managedProduit);
            }
        });
    }

    // Méthodes pour les entités Client
    public void creerClient(Client client) {
        executeTransaction(em -> em.merge(client));
    }

    public void majClient(Client client) {
        executeTransaction(em -> em.merge(client));
    }

    public void supprimerClient(Client client) {
        executeTransaction(em -> {
            Client managedClient = em.find(Client.class, client.getId());
            if (managedClient != null) {
                em.remove(managedClient);
            }
        });
    }

    private void executeTransaction(TransactionalAction action) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            action.execute(em);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de l'exécution de la transaction : " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @FunctionalInterface
    private interface TransactionalAction {
        void execute(EntityManager em);
    }
}