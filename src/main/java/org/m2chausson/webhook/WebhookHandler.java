package org.m2chausson.webhook;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.m2chausson.dao.ClientDao;
import org.m2chausson.dao.ProduitDao;
import org.m2chausson.entities.Client;
import org.m2chausson.entities.Produit;
import org.m2chausson.webhook.WebhookService.Notification;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WebhookHandler implements HttpHandler {
    private final ClientDao clientDao;
    private final ProduitDao produitDao;
    private final WebhookService webhookService;

    public WebhookHandler(WebhookService webhookService, ClientDao clientDao, ProduitDao produitDao) {
        this.clientDao = clientDao;
        this.produitDao = produitDao;
        this.webhookService = webhookService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream requestBody = exchange.getRequestBody();
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                WebhookRequest request = objectMapper.readValue(requestBody, WebhookRequest.class);
                String response;

                // Gestion des actions pour les clients
                if ("client_create".equalsIgnoreCase(request.getType())) {
                    Client client = new Client(request.getName());
                    clientDao.createClient(client);
                    response = webhookService.sendMessage(Notification.CLIENT_CREATE, client);

                } else if ("client_update".equalsIgnoreCase(request.getType())) {
                    Client client = clientDao.getClientById(request.getId());
                    if (client != null) {
                        client.setNom(request.getName());
                        clientDao.updateClient(client);
                        response = webhookService.sendMessage(Notification.CLIENT_UPDATE, client);
                    } else {
                        response = "Client avec l'ID " + request.getId() + " non trouvé.\n";
                    }

                } else if ("client_delete".equalsIgnoreCase(request.getType())) {
                    Client client = clientDao.getClientById(request.getId());
                    if (client != null) {
                        clientDao.deleteClient(client);
                        response = webhookService.sendMessage(Notification.CLIENT_DELETE, client);
                    } else {
                        response = "Client avec l'ID " + request.getId() + " non trouvé.\n";
                    }

                    // Gestion des actions pour les produits
                } else if ("produit_create".equalsIgnoreCase(request.getType())) {
                    Produit produit = new Produit(request.getName(), request.getPrix(), request.getTypeProduit());
                    produitDao.createProduit(produit);
                    response = webhookService.sendMessage(Notification.PRODUIT_CREATE, produit);

                    // Mettre à jour un produit
                } else if ("produit_update".equalsIgnoreCase(request.getType())) {
                    Produit produit = produitDao.getProduitById(request.getId());
                    if (produit != null) {
                        produit.setNom(request.getName());
                        produit.setPrix(request.getPrix());
                        produit.setType(request.getTypeProduit());
                        produitDao.updateProduit(produit);
                        response = webhookService.sendMessage(Notification.PRODUIT_UPDATE, produit);
                    } else {
                        response = "Produit avec l'ID " + request.getId() + " non trouvé.\n";
                    }

                    // Supprimer un produit
                } else if ("produit_delete".equalsIgnoreCase(request.getType())) {
                    Produit produit = produitDao.getProduitById(request.getId());
                    if (produit != null) {
                        produitDao.deleteProduit(produit);
                        response = webhookService.sendMessage(Notification.PRODUIT_DELETE, produit);
                    } else {
                        response = "Produit avec l'ID " + request.getId() + " non trouvé.\n";
                    }

                } else {
                    response = "Type de webhook non supporté\n";
                }

                sendResponse(exchange, response);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "Erreur dans le traitement du webhook\n");
            }
        } else {
            sendResponse(exchange, "Méthode non supportée");
        }
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}

class WebhookRequest {
    private String type;
    private String name;
    private double prix;
    private String typeProduit;
    private int id;

    // Getters and setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public String getTypeProduit() { return typeProduit; }
    public void setTypeProduit(String typeProduit) { this.typeProduit = typeProduit; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}