package model;

import java.time.LocalDateTime;

public class Usuario {
    private String id;
    private String nombre;
    private String cedula;
    private String username;
    private String passwordHash;
    private String rol; // ADMIN, ANALISTA
    private boolean activo;
    private String createdBy;
    private LocalDateTime createdAt;

    public Usuario() {}

    // Getters y setters
    public String getId() {
        return id; }
    public void setId(String id) {
        this.id = id; }

    public String getNombre() {
        return nombre; }
    public void setNombre(String nombre) {
        this.nombre = nombre; }

    public String getCedula() {
        return cedula; }
    public void setCedula(String cedula) {
        this.cedula = cedula; }

    public String getUsername() {
        return username; }
    public void setUsername(String username) {
        this.username = username; }

    public String getPasswordHash() {
        return passwordHash; }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash; }

    public String getRol() {
        return rol; }
    public void setRol(String rol) {
        this.rol = rol; }

    public boolean isActivo() {
        return activo; }
    public void setActivo(boolean activo) {
        this.activo = activo; }

    public String getCreatedBy() {
        return createdBy; }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() {
        return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt; }
}
