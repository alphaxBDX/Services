package dao;

import entity.Client;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static dao.ProduitDao.entityManagerFactory;

public class ClientDao {

    public static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");

    public ClientDao() {
    }

    // Récupérer tous les clients
    public List<Client> getAllClients() throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            return entityManager.createQuery("SELECT c FROM Client c").getResultList();
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de tous les clients", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    // Récupérer un client par ID
    public Client getClientById(long id) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            return entityManager.find(Client.class, id);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la recherche d'un client par son identifiant", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    // Créer un nouveau client
    public void createClient(Client client) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new Exception("Erreur lors de l'ajout d'un nouveau client", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    // Mettre à jour un client existant
    public void updateClient(Client client) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new Exception("Erreur lors de la mise à jour du client", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    // Supprimer un client
    public void deleteClient(Client client) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            client = entityManager.merge(client);
            entityManager.remove(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new Exception("Erreur lors de la suppression du client", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
