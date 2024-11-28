package persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.Usuario;
import main.Usuario.Sexo;

public class GestorBD {
    private Connection connection;

    // Constructor: Establece la conexión con la base de datos
    public GestorBD(String url) {
        try {
            connection = DriverManager.getConnection(url);
            crearTablaUsuarios(); // Crear tabla si no existe
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Crear tabla Usuarios si no existe
    private void crearTablaUsuarios() {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                apellido TEXT NOT NULL,
                dni TEXT NOT NULL UNIQUE,
                telefono INTEGER NOT NULL,
                edad INTEGER NOT NULL,
                sexo TEXT NOT NULL,
                contraseña TEXT NOT NULL
            )
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al crear tabla usuarios: " + e.getMessage());
        }
    }

    // Método para insertar un usuario en la base de datos
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, dni, telefono, edad, sexo, contraseña) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getDni());
            stmt.setInt(4, usuario.getTelefono());
            stmt.setInt(5, usuario.getEdad());
            stmt.setString(6, usuario.getSexo().toString());
            stmt.setString(7, usuario.getContraseña());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar un usuario en la base de datos
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, telefono = ?, edad = ?, sexo = ?, contraseña = ? WHERE dni = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setInt(3, usuario.getTelefono());
            stmt.setInt(4, usuario.getEdad());
            stmt.setString(5, usuario.getSexo().toString());
            stmt.setString(6, usuario.getContraseña());
            stmt.setString(7, usuario.getDni());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un usuario por su DNI
    public boolean eliminarUsuario(String dni) {
        String sql = "DELETE FROM usuarios WHERE dni = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getInt("telefono"),
                    rs.getInt("edad"),
                    Sexo.valueOf(rs.getString("sexo")),
                    rs.getString("contraseña")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    // Método para obtener un usuario por su DNI
    public Usuario obtenerUsuarioPorDni(String dni) {
        String sql = "SELECT * FROM usuarios WHERE dni = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getInt("telefono"),
                    rs.getInt("edad"),
                    Sexo.valueOf(rs.getString("sexo")),
                    rs.getString("contraseña")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario por DNI: " + e.getMessage());
        }
        return null;
    }

    // Cerrar la conexión con la base de datos
    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        GestorBD gestorBD = new GestorBD("jdbc:sqlite:usuarios.db");

        // Insertar un usuario
        Usuario usuario = new Usuario("Juan", "Pérez", "12345678A", 123456789, 30, Sexo.HOMBRE, "password123");
        gestorBD.insertarUsuario(usuario);

        // Consultar todos los usuarios
        List<Usuario> usuarios = gestorBD.obtenerTodosLosUsuarios();
        usuarios.forEach(u -> System.out.println(u.getNombre() + " " + u.getApellido()));

        // Cerrar la conexión
        gestorBD.cerrarConexion();
    }

}

