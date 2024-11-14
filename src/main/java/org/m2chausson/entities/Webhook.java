package org.m2chausson.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "webhook")
public class Webhook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @Column(name = "webhook_notif_type", nullable = false, length = 100)
    private String webhookNotifType;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    public Webhook() {
    }

    public Webhook(Client client, String webhookNotifType, String url) {
        this.client = client;
        this.webhookNotifType = webhookNotifType;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getWebhookNotifType() {
        return webhookNotifType;
    }

    public void setWebhookNotifType(String webhookNotifType) {
        this.webhookNotifType = webhookNotifType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}