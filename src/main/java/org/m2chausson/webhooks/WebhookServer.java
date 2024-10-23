package org.m2chausson.webhooks;

import org.m2chausson.dao.ClientDao;
import org.m2chausson.dao.ProduitDao;
import org.m2chausson.entities.Client;
import com.sun.net.httpserver.HttpServer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WebhookServer {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceM2Chausson");

        ClientDao clientDao = new ClientDao();
        ProduitDao produitDao = new ProduitDao(entityManagerFactory);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/webhooks", new WebhookHandler(clientDao, produitDao));
        server.setExecutor(null);
        server.start();

        System.out.println("Le serveur Java démarre sur le port 8080");
    }
}
