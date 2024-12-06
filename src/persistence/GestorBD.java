package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;



import main.Actividad;
import main.Usuario;
import main.Usuario.Sexo;



public class GestorBD {

    private static final String PROPERTIES_FILE = "config/parametros.properties";
    private static final String CONNECTION_STRING = "jdbc:sqlite:resources/data/database.db";
    private static final String LOG_FOLDER = "log";

    private Properties properties;
    private String driverName;
    private String databaseFile;

    private static final Logger logger = Logger.getLogger(GestorBD.class.getName());

    // Constructor: Inicializa el gestor de la base de datos

	public GestorBD() {
		try (FileInputStream fis = new FileInputStream("config/logger.properties")) {
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

			// Crear tablas
			//crearTablaUsuarios();
			//crearTablaActividades();
			//crearTablaParticipaciones();
		} catch (Exception ex) {
			logger.warning(String.format("Error al cargar el driver de la base de datos: %s", ex.getMessage()));
		}
	}

	
	
	
	
	
	
	/** 
	 * 
	 * 
	 * 
	 * GESTION DE LA TABLA USUARIOS:
	 * 
	 * 
	 * 
	 * **/
	
	
	
	
	
	
	
    // Crear tabla Usuarios
    public void crearTablaUsuarios() {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS usuario (
                    NOMBRE_USUARIO VARCHAR(20) NOT NULL,
            		APELLIDO_USUARIO VARCHAR(30) NOT NULL, 
            		DNI_USUARIO CHAR(9) NOT NULL,
            		TELEFONO_USUARIO VARCHAR(15) NOT NULL,
            		EDAD_USUARIO INT NOT NULL,
            		SEXO_USUARIO CHAR(1) NOT NULL,
            		CONTRASENA_USUARIO VARCHAR(50) NOT NULL,
            		PRIMARY KEY(DNI_USUARIO),
            		CHECK(EDAD_USUARIO > 0));
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
        String sql = "INSERT INTO usuario (nombre_usuario, apellido_usuario, dni_usuario, telefono_usuario, edad_usuario, sexo_usuario, contraseña_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
        String sql = "DELETE FROM usuario WHERE dni_usuario = ?";
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
                        rs.getString("nombre_usuario"),
                        rs.getString("apellido_usuario"),
                        rs.getString("dni_usuario"),
                        rs.getInt("telefono_usuario"),
                        rs.getInt("edad_usuario"),
                        Sexo.valueOf(rs.getString("sexo_usuario")),
                        rs.getString("contraseña_usuario")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
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

    
    /** 
	 * 
	 * 
	 * 
	 * GESTION DE LA TABLA ACTIVIDADES:
	 * 
	 * 
	 * 
	 * **/
    
    
 // Crear tabla Actividades si no existe
    public void crearTablaActividades() {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = """
                CREATE TABLE IF NOT EXISTS actividad (
                    NOMBRE_ACTIVIDAD VARCHAR(30) NOT NULL,
            		DURACION_ACTIVIDAD INT NOT NULL,
            		INTENSIDAD_ACTIVDAD VARCHAR(10) NOT NULL,
            		CALORIAS_ACTIVIDAD INT NOT NULL,
            		DESCRIPCION VARCHAR(1000) NOT NULL,  
            		PRIMARY KEY(NOMBRE_ACTIVIDAD),
            		CHECK(DURACION_ACTIVIDAD > 0));
                )
            """;

            PreparedStatement pstmt = con.prepareStatement(sql);

            if (!pstmt.execute()) {
                System.out.println("\n- Se ha creado la tabla actividad");
            }
            
            pstmt.close();
        } catch (Exception ex) {
            System.err.format("\n* Error al crear la tabla de actividad: %s", ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void eliminarTablaActividad() {
        String sql = "DROP TABLE IF EXISTS actividad"; 

        try (Statement stmt = DriverManager.getConnection(CONNECTION_STRING).createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabla 'actividad' eliminada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar la tabla 'actividad': " + e.getMessage());
        }
    }    
    
    // Método para insertar actividades en la base de datos
    
    public void insertarActividades(String nombre, int duracion, String intensidad, int calorias, String descripcion) {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = """
                INSERT INTO actividades (nombre_actividad, duracion_actividad, intensidad_actividad, calorias_actividad, descripcion) 
                VALUES (?, ?, ?, ?, ?)
            """;

            PreparedStatement pstmt = con.prepareStatement(sql);
            
            System.out.println("- Insertando actividades...");

           
            pstmt.setString(1, nombre);
            pstmt.setInt(2, duracion);
            pstmt.setString(3, intensidad);
            pstmt.setInt(4, calorias);
            pstmt.setString(5, descripcion);

            if (1 == pstmt.executeUpdate()) {
                System.out.format("\n - Actividad insertada: %s", nombre);
            } else {
                System.out.format("\n - No se ha insertado la actividad: %s", nombre);
            }
            
            pstmt.close();
        } catch (Exception ex) {
            System.err.format("\n* Error al insertar actividades: %s", ex.getCause());
            ex.printStackTrace();
        }
    }

    
    /** 
   	 * 
   	 * 
   	 * 
   	 * GESTION DE LA TABLA SESIONES:
   	 * 
   	 * 
   	 * 
   	 * **/
    
    
    public void crearTablaSesion() {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS sesion (
                    CAPACIDAD_SESION INT NOT NULL,
            		FECHA_SESION CHAR(16) NOT NULL,
            		ID_SESION INT NOT NULL AUTO_INCREMENT,
            		NOMBRE_ACTIVIDAD VARCHAR(30) NOT NULL, 
            		PRIMARY KEY(ID_SESION),
            		FOREIGN KEY(NOMBRE_ACTIVIDAD) REFERENCES ACTIVIDAD (NOMBRE_ACTIVIDAD) ON DELETE CASCADE,
            		CHECK(CAPACIDAD_SESION > 0));
                )
            """;
            stmt.execute(sql);
            System.out.println("Tabla 'sesion' creada o ya existía.");
        } catch (SQLException ex) {
            System.err.format("Error al crear la tabla 'sesion': %s", ex.getMessage());
        }
    }

    public void insertarSesiones(Actividad... actividades) {
        String sql = "INSERT INTO sesion (capacidad_sesion, fecha_sesion, nombre_actividad) VALUES (?, ?, ?)";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (Actividad a : actividades) {
                pstmt.setInt(1, a.getCapacidad());
                pstmt.setString(2, a.getFecha().toString());
                pstmt.setString(3, a.getNombre());
                pstmt.executeUpdate();
            }
            System.out.println("Usuarios insertados correctamente.");
        } catch (SQLException ex) {
            System.err.format("Error al insertar usuarios: %s", ex.getMessage());
        }
    }

    public List<Actividad> obtenerTodosLasSesiones() {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT * FROM usuario u LEFT JOIN actividad a on u.nombre_actividad = a.nombre_actividad";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Actividad actividad = new Actividad(
                		rs.getString("nombre_actividad"),
                		rs.getInt("duracion_actividad"),
                		rs.getString("intensidad_actividad"),
                		rs.getInt("calorias_actividad"),
                		rs.getString("descripcion"),
                        rs.getInt("capacidad_sesion"),
                        rs.getString("fecha_sesion"),
                        rs.getInt("id_sesion"),
                        new ArrayList<Usuario>()
                );
                actividades.add(actividad);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return actividades;
    }
 
    
    /** 
	 * 
	 * 
	 * 
	 * GESTION DE LA TABLA PARTICIPACIONES:
	 * 
	 * 
	 * 
	 * **/
    
    
    // Crear tabla Participaciones si no existe
    private void crearTablaParticipaciones() {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = """
                CREATE TABLE IF NOT EXISTS participa (
                    DNI_USUARIO CHAR(9) NOT NULL,
            		ID_SESION INT NOT NULL,
            		PRIMARY KEY(DNI_USUARIO, ID_SESION),
            		FOREIGN KEY(DNI_USUARIO) REFERENCES USUARIO (DNI_USUARIO) ON DELETE CASCADE,
            		FOREIGN KEY(ID_SESION) REFERENCES SESION (ID_SESION) ON DELETE CASCADE);
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
    
    public void insertarParticipacion(int idUsuario, int idActividad) {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = """
                INSERT INTO participa (dni_usuario, id_sesion) 
                VALUES (?, ?)
            """;

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idActividad);

            if (1 == pstmt.executeUpdate()) {
                System.out.format("\n - Usuario %d inscrito a la actividad %d", idUsuario, idActividad);
            } else {
                System.out.println("\n - No se pudo inscribir al usuario.");
            }

            pstmt.close();
        } catch (Exception ex) {
            System.err.format("\n* Error al insertar participación: %s", ex.getMessage());
            ex.printStackTrace();
        }
    }
   
    // Método para obtener todas las participaciones
    public List<String> obtenerTodasLasParticipaciones() {
        List<String> participaciones = new ArrayList<>();
        String sql = """
            SELECT * FROM participa
        """;

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String participacion = String.format(
                    "%s, %s",
                    rs.getString("dni_usuario"),
                    rs.getString("id_sesion")
                );
                participaciones.add(participacion);
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al obtener participaciones: %s", ex.getMessage());
            ex.printStackTrace();
        }

        return participaciones;
    }
    
   
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

    
    public void limpiarTablas() {
        String sqlUsuario = "DELETE FROM usuario";
        String sqlActividades = "DELETE FROM actividades";
        String sqlParticipa = "DELETE FROM participo";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement()) {
        	
            stmt.executeUpdate(sqlUsuario);
            System.out.println("Tabla 'usuario' limpiada correctamente.");

            stmt.executeUpdate(sqlActividades);
            System.out.println("Tabla 'actividades' limpiada correctamente.");
            
            stmt.executeUpdate(sqlParticipa);
            System.out.println("Tabla 'participa' limpiada correctamente.");

        } catch (SQLException ex) {
            System.err.format("Error al limpiar tablas: %s", ex.getMessage());
            ex.printStackTrace();
        }
    }
}

