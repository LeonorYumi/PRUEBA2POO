package model;

public class Usuario {

    private int id;
    private String nombre;
    private String cedula;
    private String username;
    private String passwordHash;
    private int rolId;
    private String rolNombre;
    private boolean activo;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor completo
    public Usuario(int id, String nombre, String cedula, String username,
                   String passwordHash, int rolId, String rolNombre, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.username = username;
        this.passwordHash = passwordHash;
        this.rolId = rolId;
        this.rolNombre = rolNombre;
        this.activo = activo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Métodos útiles
    public boolean esAdmin() {
        return "ADMIN".equalsIgnoreCase(rolNombre);
    }

    public boolean esAnalista() {
        return "ANALISTA".equalsIgnoreCase(rolNombre);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", username='" + username + '\'' +
                ", rol='" + rolNombre + '\'' +
                ", activo=" + activo +
                '}';
    }
}