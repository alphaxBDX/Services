package org.m2chausson;

import com.sun.net.httpserver.HttpServer;
import org.m2chausson.dao.ClientDao;
import org.m2chausson.dao.ProduitDao;
import org.m2chausson.dao.WebhookDao;
import org.m2chausson.webhook.WebhookHandler;
import org.m2chausson.webhook.WebhookService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceM2Chausson");

        ClientDao clientDao = new ClientDao();
        ProduitDao produitDao = new ProduitDao(entityManagerFactory);
        WebhookDao webhookDao = new WebhookDao(entityManagerFactory);

        WebhookService webhookService = new WebhookService(webhookDao);

        // Création et configuration du serveur HTTP
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/webhook", new WebhookHandler(webhookService, clientDao, produitDao));
        server.setExecutor(null);
        server.start();
    }
}