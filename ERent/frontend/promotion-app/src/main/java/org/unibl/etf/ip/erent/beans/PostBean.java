package org.unibl.etf.ip.erent.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostBean implements Serializable {
    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public PostBean() {}

    public PostBean(int id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}