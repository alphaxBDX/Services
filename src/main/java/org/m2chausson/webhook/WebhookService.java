package org.m2chausson.webhook;

import org.m2chausson.dao.WebhookDao;
import org.m2chausson.entities.Client;
import org.m2chausson.entities.Produit;

public class WebhookService {

    private final WebhookDao webhookDao;

    public WebhookService(WebhookDao webhookDao) {
        this.webhookDao = webhookDao;
    }

    public enum Notification {
        // Appel pour les méthodes Produits
        PRODUIT_UPDATE {
            @Override
            public String executeAction(WebhookDao webhookDao, Object message) {
                if (message instanceof Produit) {
                    Produit produit = (Produit) message;
                    webhookDao.majProduit(produit);
                    return "Produit mis à jour avec succès : " + produit.getNom() + " (ID: " + produit.getId() + ")\n";
                } else {
                    throw new IllegalArgumentException("Le message doit être un Produit pour PRODUIT_UPDATE");
                }
            }
        },
        PRODUIT_CREATE {
            @Override
            public String executeAction(WebhookDao webhookDao, Object message) {
                if (message instanceof Produit) {
                    Produit produit = (Produit) message;
                    webhookDao.creerProduit(produit);
                    return "Produit créé avec succès : " + produit.getNom() + " (ID: " + produit.getId() + ")\n";
                } else {
                    throw new IllegalArgumentException("Le message doit être un Produit pour PRODUIT_CREATE");
                }
            }
        },
        PRODUIT_DELETE {
            @Override
            public String executeAction(WebhookDao webhookDao, Object message) {
                if (message instanceof Produit) {
                    Produit produit = (Produit) message;
                    webhookDao.supprimerProduit(produit);
                    return "Produit supprimé avec succès : " + produit.getNom() + " (ID: " + produit.getId() + ")\n";
                } else {
                    throw new IllegalArgumentException("Le message doit être un Produit pour PRODUIT_DELETE");
                }
            }
        },

        // Appel pour les méthodes Clients
        CLIENT_UPDATE {
            @Override
            public String executeAction(WebhookDao webhookDao, Object message) {
                if (message instanceof Client) {
                    Client client = (Client) message;
                    webhookDao.majClient(client);
                    return "Client mis à jour avec succès : " + client.getNom() + " (ID: " + client.getId() + ")\n";
                } else {
                    throw new IllegalArgumentException("Le message doit être un Client pour CLIENT_UPDATE");
                }
            }
        },
        CLIENT_CREATE {
            @Override
            public String executeAction(WebhookDao webhookDao, Object message) {
                if (message instanceof Client) {
                    Client client = (Client) message;
                    webhookDao.creerClient(client);
                    return "Client créé avec succès : " + client.getNom() + " (ID: " + client.getId() + ")\n";
                } else {
                    throw new IllegalArgumentException("Le message doit être un Client pour CLIENT_CREATE");
                }
            }
        },
        CLIENT_DELETE {
            @Override
            public String executeAction(WebhookDao webhookDao, Object message) {
                if (message instanceof Client) {
                    Client client = (Client) message;
                    webhookDao.supprimerClient(client);
                    return "Client supprimé avec succès : " + client.getNom() + " (ID: " + client.getId() + ")\n";
                } else {
                    throw new IllegalArgumentException("Le message doit être un Client pour CLIENT_DELETE");
                }
            }
        };
        public abstract String executeAction(WebhookDao webhookDao, Object message);
    }

    // Méthode pour envoyer un message avec la notification
    public String sendMessage(Notification notificationType, Object message) {
        try {
            String responseMessage = notificationType.executeAction(webhookDao, message);
            return responseMessage;
        } catch (Exception e) {
            return "Erreur lors de l'exécution de l'action pour " + notificationType.name() + " : " + e.getMessage() + "\n";
        }
    }
}