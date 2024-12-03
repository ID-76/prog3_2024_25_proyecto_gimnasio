package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.ImageIcon;


import main.Actividad;
import main.Actividad.Tipo;
import main.Usuario;
import main.Usuario.Sexo;



public class GestorBD {

    private static final String PROPERTIES_FILE = "resources/config/parametros.properties";
    private static final String CONNECTION_STRING = "jdbc:sqlite:resources/data/database.db";
    private static final String LOG_FOLDER = "resources/log";

    private Properties properties;
    private String driverName;
    private String databaseFile;

    private static final Logger logger = Logger.getLogger(GestorBD.class.getName());

    // Constructor: Inicializa el gestor de la base de datos
    public GestorBD() {
        try (FileInputStream fis = new FileInputStream("resources/config/logger.properties")) {
            // Inicialización del Logger
            LogManager.getLogManager().readConfiguration(fis);

            // Lectura del fichero de propiedades
            properties = new Properties();
            properties.load(new FileReader(PROPERTIES_FILE));

            driverName = properties.getProperty("driver");
            databaseFile = properties.getProperty("file");

            // Crear carpetas de log si no existen
            File dir = new File(LOG_FOLDER);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Crear carpeta para la base de datos si no existe
            dir = new File(databaseFile.substring(0, databaseFile.lastIndexOf("/")));
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Cargar el driver SQLite
            Class.forName(driverName);
        } catch (Exception ex) {
            logger.warning(String.format("Error al cargar el driver de la base de datos: %s", ex.getMessage()));
        }
    }

    // Crear tabla Usuarios
    public void crearTablaUsuarios() {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS usuario (
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
            stmt.execute(sql);
            System.out.println("Tabla 'usuario' creada o ya existía.");
        } catch (SQLException ex) {
            System.err.format("Error al crear la tabla 'usuario': %s", ex.getMessage());
        }
    }

    // Insertar usuarios
    public void insertarUsuarios(Usuario... usuarios) {
        String sql = "INSERT INTO usuario (nombre, apellido, dni, telefono, edad, sexo, contraseña) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (Usuario u : usuarios) {
                pstmt.setString(1, u.getNombre());
                pstmt.setString(2, u.getApellido());
                pstmt.setString(3, u.getDni());
                pstmt.setInt(4, u.getTelefono());
                pstmt.setInt(5, u.getEdad());
                pstmt.setString(6, u.getSexo().toString());
                pstmt.setString(7, u.getContraseña());
                pstmt.executeUpdate();
            }
            System.out.println("Usuarios insertados correctamente.");
        } catch (SQLException ex) {
            System.err.format("Error al insertar usuarios: %s", ex.getMessage());
        }
    }

    // Actualizar un usuario
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, apellido = ?, telefono = ?, edad = ?, sexo = ?, contraseña = ? WHERE dni = ?";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setInt(3, usuario.getTelefono());
            stmt.setInt(4, usuario.getEdad());
            stmt.setString(5, usuario.getSexo().toString());
            stmt.setString(6, usuario.getContraseña());
            stmt.setString(7, usuario.getDni());
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // Eliminar un usuario por DNI
    public boolean eliminarUsuario(String dni) {
        String sql = "DELETE FROM usuario WHERE dni = ?";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, dni);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement();
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

    // Obtener usuarios por rango de edad
    public List<Usuario> obtenerUsuariosPorRangoDeEdad(int edadMinima, int edadMaxima) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE edad BETWEEN ? AND ?";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, edadMinima);
            stmt.setInt(2, edadMaxima);
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios por rango de edad: " + e.getMessage());
        }
        return usuarios;
    }

    // Contar usuarios por sexo
    public int contarUsuariosPorSexo(Sexo sexo) {
        String sql = "SELECT COUNT(*) AS total FROM usuario WHERE sexo = ?";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, sexo.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al contar usuarios por sexo: " + e.getMessage());
        }
        return 0;
    }
    
    
 // Método para obtener un desglose de usuarios por sexo
    public Map<Sexo, Integer> desgloseUsuariosPorSexo() {
        String sql = "SELECT sexo, COUNT(*) AS total FROM usuario GROUP BY sexo";
        Map<Sexo, Integer> conteoPorSexo = new HashMap<>();

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                try {
                    Sexo sexo = Sexo.valueOf(rs.getString("sexo"));
                    int total = rs.getInt("total");
                    conteoPorSexo.put(sexo, total);
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.err.println("Dato inválido encontrado en la columna 'sexo': " + rs.getString("sexo"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener desglose de usuarios por sexo: " + e.getMessage());
        }

        return conteoPorSexo;
    }


    // Actualizar contraseña de un usuario
    public boolean actualizarContraseña(String dni, String nuevaContraseña) {
        String sql = "UPDATE usuario SET contraseña = ? WHERE dni = ?";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nuevaContraseña);
            stmt.setString(2, dni);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la contraseña: " + e.getMessage());
            return false;
        }
    }





 // Método para obtener un usuario por su DNI
    public Usuario obtenerUsuarioPorDni(String dni) {
        String sql = "SELECT * FROM usuario WHERE dni = ?";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario por DNI: " + e.getMessage());
        }
        return null; // Retorna null si no se encuentra el usuario
    }


 // Cerrar la conexión con la base de datos
    private Connection connection;

    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    
    
    
    
    
    
    
 // Crear tabla Actividades si no existe
    private void crearTablaActividades() {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = """
                CREATE TABLE IF NOT EXISTS actividades (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    capacidad INTEGER NOT NULL,
                    fecha DATETIME NOT NULL,
                    ocupacion INTEGER DEFAULT 0,
                    logo BLOB,
                    calorias INTEGER NOT NULL,
                    intensidad TEXT NOT NULL,
                    descripcion TEXT,
                    duracion INTEGER NOT NULL,
                    tipo TEXT NOT NULL CHECK (tipo IN ('Aeróbico', 'Fuerza', 'Flexibilidad', 'Mixto'))
                )
            """;

            PreparedStatement pstmt = con.prepareStatement(sql);

            if (!pstmt.execute()) {
                System.out.println("\n- Se ha creado la tabla Actividades");
            }
            
            pstmt.close();
        } catch (Exception ex) {
            System.err.format("\n* Error al crear la tabla de actividades: %s", ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    
    // Método para insertar actividades en la base de datos
    
    public void insertarActividades(Actividad... actividades) {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = """
                INSERT INTO actividades (nombre, capacidad, fecha, ocupacion, logo, calorias, intensidad, descripcion, duracion, tipo) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            PreparedStatement pstmt = con.prepareStatement(sql);
            
            System.out.println("- Insertando actividades...");

            for (Actividad actividad : actividades) {
                pstmt.setString(1, actividad.getNombre());
                pstmt.setInt(2, actividad.getCapacidad());
                pstmt.setObject(3, actividad.getFecha());
                pstmt.setInt(4, actividad.getOcupacion());
                //pstmt.setByte(5, actividad.getLogo()); // Convertir ImageIcon a byte[]
                pstmt.setInt(6, actividad.getCalorias());
                pstmt.setString(7, actividad.getIntensidad());
                pstmt.setString(8, actividad.getDescripcion());
                pstmt.setInt(9, actividad.getDuracion());
                pstmt.setString(10, actividad.getTipo().toString());

                if (1 == pstmt.executeUpdate()) {
                    System.out.format("\n - Actividad insertada: %s", actividad.getNombre());
                } else {
                    System.out.format("\n - No se ha insertado la actividad: %s", actividad.getNombre());
                }
            }
            
            pstmt.close();
        } catch (Exception ex) {
            System.err.format("\n* Error al insertar actividades: %s", ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    
    // Método para obtener todas las actividades
    public List<Actividad> obtenerTodasLasActividades() {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT * FROM actividades";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getString("nombre"),
                    rs.getInt("capacidad"),
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    rs.getInt("ocupacion"),
                    new ImageIcon(rs.getBytes("logo")),
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    rs.getInt("calorias"),
                    rs.getString("intensidad"),
                    rs.getString("descripcion"),
                    rs.getInt("duracion"),
                    Tipo.valueOf(rs.getString("tipo"))
                );
                actividades.add(actividad);
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al obtener actividades: %s", ex.getMessage());
            ex.printStackTrace();
        }

        return actividades;
    }
    
    
    // Método para obtener una actividad por su nombre
 // Método para obtener una actividad por su nombre
    public Actividad obtenerActividadPorNombre(String nombre) {
        String sql = "SELECT * FROM actividades WHERE nombre = ?";
        Actividad actividad = null;

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                actividad = new Actividad(
                    rs.getString("nombre"),
                    rs.getInt("capacidad"),
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    rs.getInt("ocupacion"),                    
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    new ImageIcon(rs.getBytes("logo")),
                    rs.getInt("calorias"),
                    rs.getString("intensidad"),
                    rs.getString("descripcion"),
                    rs.getInt("duracion"),
                    Tipo.valueOf(rs.getString("tipo"))
                );
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al obtener actividad por nombre: %s", ex.getMessage());
            ex.printStackTrace();
        }

        return actividad;
    }

    
    
    // Crear tabla Participaciones si no existe
    private void crearTablaParticipaciones() {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = """
                CREATE TABLE IF NOT EXISTS participaciones (
                    id_participacion INTEGER PRIMARY KEY AUTOINCREMENT,
                    id_usuario INTEGER NOT NULL,
                    id_actividad INTEGER NOT NULL,
                    fecha_inscripcion DATETIME DEFAULT CURRENT_TIMESTAMP,
                    estado TEXT DEFAULT 'Confirmada',
                    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
                    FOREIGN KEY (id_actividad) REFERENCES actividades(id)
                )
            """;

            PreparedStatement pstmt = con.prepareStatement(sql);

            if (!pstmt.execute()) {
                System.out.println("\n- Se ha creado la tabla Participaciones");
            }

            pstmt.close();
        } catch (Exception ex) {
            System.err.format("\n* Error al crear la tabla de participaciones: %s", ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    // Método para insertar participaciones en la base de datos
    
    public void insertarParticipacion(int idUsuario, int idActividad, String estado) {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = """
                INSERT INTO participaciones (id_usuario, id_actividad, estado) 
                VALUES (?, ?, ?)
            """;

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idActividad);
            pstmt.setString(3, estado);

            if (1 == pstmt.executeUpdate()) {
                System.out.format("\n - Usuario %d inscrito a la actividad %d con estado %s", idUsuario, idActividad, estado);
            } else {
                System.out.println("\n - No se pudo inscribir al usuario.");
            }

            pstmt.close();
        } catch (Exception ex) {
            System.err.format("\n* Error al insertar participación: %s", ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    // Consultar qué usuarios están inscritos en una actividad específica.
    public List<Usuario> obtenerUsuariosPorActividad(int idActividad) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = """
            SELECT u.* FROM usuarios u
            INNER JOIN participaciones p ON u.id = p.id_usuario
            WHERE p.id_actividad = ?
        """;

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, idActividad);
            ResultSet rs = pstmt.executeQuery();

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
        } catch (Exception ex) {
            System.err.format("\n* Error al obtener usuarios por actividad: %s", ex.getMessage());
            ex.printStackTrace();
        }

        return usuarios;
    }
    
    // Consultar qué actividades está inscrito un usuario específico.
    
    public List<Actividad> obtenerActividadesPorUsuario(int idUsuario) {
        List<Actividad> actividades = new ArrayList<>();
        String sql = """
            SELECT a.* FROM actividades a
            INNER JOIN participaciones p ON a.id = p.id_actividad
            WHERE p.id_usuario = ?
        """;

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getString("nombre"),
                    rs.getInt("capacidad"),
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    rs.getInt("ocupacion"),
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    // NO HAY QUE CARGAR EL LOGO NI LA INTENSIDAD NI LAS CALORIAS YA QUE ESO YA LO SACA EL CONSTRUCTOR
                    new ImageIcon(rs.getBytes("logo")),
                    rs.getInt("calorias"),
                    rs.getString("intensidad"),
                    rs.getString("descripcion"),
                    rs.getInt("duracion"),
                    Tipo.valueOf(rs.getString("tipo"))
                );
                actividades.add(actividad);
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al obtener actividades por usuario: %s", ex.getMessage());
            ex.printStackTrace();
        }

        return actividades;
    }

    
    
    // Método para obtener todas las participaciones
    public List<String> obtenerTodasLasParticipaciones() {
        List<String> participaciones = new ArrayList<>();
        String sql = """
            SELECT u.nombre AS nombre_usuario, u.apellido, a.nombre AS nombre_actividad, 
                   p.fecha_inscripcion, p.estado
            FROM participaciones p
            INNER JOIN usuarios u ON p.id_usuario = u.id
            INNER JOIN actividades a ON p.id_actividad = a.id
        """;

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String participacion = String.format(
                    "Usuario: %s %s, Actividad: %s, Fecha: %s, Estado: %s",
                    rs.getString("nombre_usuario"),
                    rs.getString("apellido"),
                    rs.getString("nombre_actividad"),
                    rs.getString("fecha_inscripcion"),
                    rs.getString("estado")
                );
                participaciones.add(participacion);
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al obtener participaciones: %s", ex.getMessage());
            ex.printStackTrace();
        }

        return participaciones;
    }

    
	/*public List<Participacion> obtenerTodasLasParticipaciones() {
		List<Participacion> participaciones = new ArrayList<>();
		String sql = "SELECT * FROM participaciones";

		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Participacion participacion = new Participacion(rs.getInt("id_participacion"), rs.getInt("id_usuario"),
						rs.getInt("id_actividad"), rs.getTimestamp("fecha_inscripcion").toLocalDateTime(),
						rs.getString("estado"));
				participaciones.add(participacion);
			}
		} catch (Exception ex) {
			System.err.format("\n* Error al obtener participaciones: %s", ex.getMessage());
			ex.printStackTrace();
		}

		return participaciones;
	}*/
    
    
    
    // Método para obtener una participación por su ID
    public String obtenerParticipacionPorId(int id) {
        String participacion = null;
        String sql = """
            SELECT u.nombre AS nombre_usuario, u.apellido, a.nombre AS nombre_actividad, 
                   p.fecha_inscripcion, p.estado
            FROM participaciones p
            INNER JOIN usuarios u ON p.id_usuario = u.id
            INNER JOIN actividades a ON p.id_actividad = a.id
            WHERE p.id = ?
        """;

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                participacion = String.format(
                    "ID: %d, Usuario: %s %s, Actividad: %s, Fecha: %s, Estado: %s",
                    id,
                    rs.getString("nombre_usuario"),
                    rs.getString("apellido"),
                    rs.getString("nombre_actividad"),
                    rs.getString("fecha_inscripcion"),
                    rs.getString("estado")
                );
            } else {
                System.out.println("No se encontró ninguna participación con ese ID.");
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al obtener participación por ID: %s", ex.getMessage());
            ex.printStackTrace();
        }

        return participacion;
    }

    
    
    
    
    
    
    
    
   
}

