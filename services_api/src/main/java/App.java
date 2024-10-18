import dao.ClientDao;
import dao.ProduitDao;
import entity.Client;
import entity.Produit;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

    public static void main(String[] args) {
        // Création de l'EntityManagerFactory avec l'unité de persistance
        EntityManagerFactory entityManagerFactory = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");

            // Instanciation des DAO avec l'EntityManagerFactory
            ClientDao clientDao = new ClientDao(entityManagerFactory);
            ProduitDao produitDao = new ProduitDao(entityManagerFactory);

            // Test des opérations CRUD sur Client
            System.out.println("=== Test Client DAO ===");

            // Création d'un client
            Client newClient = new Client("John Doe");
            clientDao.createClient(newClient);
            System.out.println("Client ajouté : " + newClient);

            // Récupération du client par ID
            Client retrievedClient = clientDao.getClientById(newClient.getId());
            System.out.println("Client récupéré : " + retrievedClient);

            // Mise à jour du client
            retrievedClient.setNom("John Doe Updated");
            clientDao.updateClient(retrievedClient);
            System.out.println("Client mis à jour : " + retrievedClient);

            // Suppression du client
            clientDao.deleteClient(retrievedClient);
            System.out.println("Client supprimé : " + retrievedClient);

            // Test des opérations CRUD sur Produit
            System.out.println("\n=== Test Produit DAO ===");

            // Création d'un produit
            Produit newProduit = new Produit("Produit A", 25.50, "Type 1");
            produitDao.createProduit(newProduit);
            System.out.println("Produit ajouté : " + newProduit);

            // Récupération du produit par ID
            Produit retrievedProduit = produitDao.getProduitById(newProduit.getId());
            System.out.println("Produit récupéré : " + retrievedProduit);

            // Mise à jour du produit
            retrievedProduit.setPrix(30.00);
            produitDao.updateProduit(retrievedProduit);
            System.out.println("Produit mis à jour : " + retrievedProduit);

            // Suppression du produit
            produitDao.deleteProduit(retrievedProduit);
            System.out.println("Produit supprimé : " + retrievedProduit);

        } catch (Exception e) {
            System.err.println("Une erreur s'est produite : " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (entityManagerFactory != null) {
                entityManagerFactory.close(); // Assurez-vous de fermer l'EntityManagerFactory
            }
        }
    }
}
