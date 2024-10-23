package org.m2chausson.webhook;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.m2chausson.dao.ClientDao;
import org.m2chausson.dao.ProduitDao;
import org.m2chausson.entities.Client;
import org.m2chausson.entities.Produit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WebhookHandler implements HttpHandler {
    private final ClientDao clientDao;
    private final ProduitDao produitDao;

    public WebhookHandler(ClientDao clientDao, ProduitDao produitDao) {
        this.clientDao = clientDao;
        this.produitDao = produitDao;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream requestBody = exchange.getRequestBody();
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                WebhookRequest request = objectMapper.readValue(requestBody, WebhookRequest.class);

                if (request.getType().equals("client")) {
                    Client client = new Client(request.getName());
                    clientDao.createClient(client);
                    String response = "Client ajouté avec succès : " + client.getNom() + "\n";
                    sendResponse(exchange, response);
                } else if (request.getType().equals("produit")) {
                    Produit produit = new Produit(request.getName(), request.getPrix(), request.getTypeProduit());
                    produitDao.createProduit(produit);
                    String response = "Produit ajouté avec succès : " + produit.getNom() + "\n";
                    sendResponse(exchange, response);
                } else {
                    sendResponse(exchange, "Type de webhook non supporté" + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "Erreur dans le traitement du webhook" + "\n");
            }
        } else {
            sendResponse(exchange, "Méthode non supportée");
        }
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream output = exchange.getResponseBody();
        output.write(response.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}

class WebhookRequest {
    private String type;
    private String name; // je précise le type (client ou produit) dans ma requête curl avec l'option "-d"
    private double prix;
    private String typeProduit;

    // Getters and setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
    }
}