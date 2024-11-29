package persistence;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;

import main.Actividad;
import main.Usuario;
import main.Usuario.Sexo;

public class GestorBD {

	//Se elimina el final de los atributos para poder actualizar los valores.
	protected static String DRIVER_NAME;
	protected static String DATABASE_FILE;
	protected static String CONNECTION_STRING;
	protected static Connection connection;

    // Constructor: Establece la conexión con la base de datos
    public GestorBD() {
    	try {
			//Se crea el Properties y se actualizan los 3 parámetros
			Properties connectionProperties = new Properties();
			connectionProperties.load(new FileReader("resources/parametros.properties"));
			
			DRIVER_NAME = connectionProperties.getProperty("DRIVER_NAME");
			DATABASE_FILE = connectionProperties.getProperty("DATABASE_FILE");
			CONNECTION_STRING = connectionProperties.getProperty("CONNECTION_STRING") + DATABASE_FILE;
			
			//Cargar el diver SQLite
			Class.forName(DRIVER_NAME);
		} catch (Exception ex) {
			System.err.format("\n* Error al cargar el driver de BBDD: %s", ex.getMessage());
			ex.printStackTrace();
		}
	}
    
    
    // Crear tabla Usuarios si no existe
    private void crearTablaUsuarios() {
    	//Se abre la conexión y se obtiene el Statement
    	//Al abrir la conexión, si no existía el fichero, se crea la base de datos
    	try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {	
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
	    	 PreparedStatement pstmt = con.prepareStatement(sql);

	    	 if (!pstmt.execute()) {
		        	System.out.println("\n- Se ha creado la tabla Usuario");
		        }
		        
		        //Es necesario cerrar el PreparedStatement
		        pstmt.close();		
			} catch (Exception ex) {
				System.err.format("\n* Error al crear la BBDD: %s", ex.getMessage());
				ex.printStackTrace();			
			}
    }

    // Método para insertar un usuario en la base de datos
    public void insertarUsuarios(Usuario... usuarios) {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
          //Se define la plantilla de la sentencia SQL
            String sql = "INSERT INTO usuarios (nombre, apellido, dni, telefono, edad, sexo, contraseña) VALUES (?, ?, ?, ?, ?, ?, ?)";			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			System.out.println("- Insertando usuarios...");
			
			//Se recorren los clientes y se insertan uno a uno
			for (Usuario u : usuarios) {				
				pstmt.setString(1, u.getNombre());
				pstmt.setString(2, u.getApellido());
				pstmt.setString(3, u.getDni());
				pstmt.setInt(4, u.getTelefono());
				pstmt.setInt(5, u.getEdad());
				pstmt.setString(6, u.getSexo().toString());
				pstmt.setString(7, u.getContraseña());
				
				if (1 == pstmt.executeUpdate()) {					
					System.out.format("\n - Cliente insertado: %s", u.toString());
				} else {
					System.out.format("\n - No se ha insertado el cliente: %s", u.toString());
				}
			}			
		} catch (Exception ex) {
			System.err.format("\n* Error al insertar datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
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
                pstmt.setBytes(5, actividad.getLogoBytes()); // Convertir ImageIcon a byte[]
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
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
        GestorBD gestorBD = new GestorBD("jdbc:sqlite:usuarios.csv");

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

