package org.unibl.etf.ip.erent.dto;

public class ClientDTO {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean blocked;
    private boolean active;

    public ClientDTO() {}

    public ClientDTO(int id, String username, String firstName, String lastName, String email, boolean blocked, boolean active) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.blocked = blocked;
        this.active = active;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isBlocked() { return blocked; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}