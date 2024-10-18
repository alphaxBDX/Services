import dao.ClientDao;
import dao.ProduitDao;
import entity.Client;
import entity.Produit;

public class App {

    public static void main(String[] args) {
        ProduitDao produitDao = new ProduitDao();
        ClientDao clientDao = new ClientDao();

        // Test des opérations CRUD sur Client
        try {
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
        }
    }
}
