package org.m2chausson;

import com.sun.net.httpserver.HttpServer;
import org.m2chausson.dao.ClientDao;
import org.m2chausson.dao.ProduitDao;
import org.m2chausson.webhooks.WebhookHandler;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceM2Chausson");
        ClientDao clientDao = new ClientDao();
        ProduitDao produitDao = new ProduitDao(entityManagerFactory);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/webhooks", new WebhookHandler(clientDao, produitDao));
        server.setExecutor(null);
        server.start();
    }
}
