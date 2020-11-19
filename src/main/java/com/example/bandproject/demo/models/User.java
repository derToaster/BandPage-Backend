package com.example.bandproject.demo.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true, name = "username")
    private String username;
    @Column(length = 100, name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(nullable = false, name = "enabled")
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role;
    @Column(nullable = false, name = "isApproved")
    private boolean isApproved;
    private String securityQuestion;
    private String securityAnswer;

    @OneToMany(mappedBy = "users")
    private Set<Skills> userSkills = new HashSet<>();


    @OneToMany(mappedBy = "owner")
    private Set<Band> ownership = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    private Set<Notifications> sent = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<Notifications> received = new HashSet<>();

    @OneToMany(mappedBy = "members")
    private Set<BandMembership> memberships = new HashSet<>();


    public User() {
    }

    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.enabled = user.enabled;
        this.role = user.role;
        this.userSkills = user.userSkills;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public Set<Skills> getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(Set<Skills> userSkills) {
        this.userSkills = userSkills;
    }

    public Set<Band> getOwnership() {
        return ownership;
    }

    public void setOwnership(Set<Band> ownership) {
        this.ownership = ownership;
    }


    public Set<Notifications> getSent() {
        return sent;
    }

    public void setSent(Set<Notifications> sent) {
        this.sent = sent;
    }

    public Set<Notifications> getReceived() {
        return received;
    }

    public void setReceived(Set<Notifications> received) {
        this.received = received;
    }

    public Set<BandMembership> getMemberships() {
        return memberships;
    }

    public void setMemberships(Set<BandMembership> memberships) {
        this.memberships = memberships;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
}
