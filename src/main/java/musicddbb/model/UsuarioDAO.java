package musicddbb.model;

public class UsuarioDAO extends Usuario {

	private boolean persist;

	public UsuarioDAO() {
		super();
		persist = false;
	}

	public UsuarioDAO(int id, String correo, String nombre, String foto, List<Lista> listas_creadas,
			List<Lista> listas_suscrito) {
		super(id, correo, nombre, foto, listas_creadas, listas_suscrito);
		persist = false;
	}

	public UsuarioDAO(String nombre, String nacionalidad, String foto, List<Lista> listas_creadas,
			List<Lista> listas_suscrito) {
		super(-1, nombre, nacionalidad, foto, listas_creadas, listas_suscrito);
		persist = false;
	}

	public UsuarioDAO(String nombre, String nacionalidad, String foto) {
		super(-1, nombre, nacionalidad, foto, null, null);
		persist = false;
	}

	public UsuarioDAO(Usuario u) {
		this.id = u.id;
		this.correo = u.correo;
		this.nombre = u.nombre;
		this.foto = u.foto;
		this.listas_creadas = u.listas_creadas;
		this.listas_suscrito = u.listas_suscrito;
	}

	public void persist() {
		persist = true;
	}

	public void detatch() {
		persist = false;
	}

	@Override
	public void setId(int id) {
		super.setId(id);
		if (persist) {
			save();
		}
	}

	@Override
	public void setNombre(String nombre) {
		super.setNombre(nombre);
		if (persist) {
			save();
		}
	}

	@Override
	public void setCorreo(String correo) {
		super.setCorreo(correo);
		if (persist) {
			save();
		}
	}

	@Override
	public void setFoto(String foto) {
		super.setFoto(foto);
		if (persist) {
			save();
		}
	}

	@Override
	public void setListas_creadas(List<Lista> listas_creadas) {
		super.setListas_creadas(listas_creadas);
		if (persist) {
			save();
		}
	}

	@Override
	public void setListas_suscrito(List<Lista> listas_suscrito) {
		super.setListas_suscrito(listas_suscrito);
		if (persist) {
			save();
		}
	}

	/**
	 * Metodo que guarda un usuario en la base de datos
	 *
	 * @return -1 en caso de que no haga nada o el id del usuario que hayamos
	 *         agregado o editado
	 */
	public int save() {
		int result = -1;

		try {
			java.sql.Connection csql = ConnectionUtil.getConnection();

			if (this.id > 0) {
				// UPDATE
				String q = "UPDATE usuario SET correo = ?, nombre = ?, foto = ? WHERE id = ?";
				PreparedStatement ps = csql.prepareStatement(q);
				ps.setString(1, correo);
				ps.setString(2, nombre);
				ps.setString(3, foto);
				ps.setInt(4, id);
				result = ps.executeUpdate();
			} else {
				// INSERT
				String q = "INSERT INTO usuario (id,correo,nombre,foto) VALUES(NULL,?,?,?)";
				PreparedStatement ps = csql.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, correo);
				ps.setString(2, nombre);
				ps.setString(3, foto);
				result = ps.executeUpdate();
				try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						result = generatedKeys.getInt(1); // devuelve el ultimo id insertado
					}
				}
				this.id = result;
			}

		} catch (SQLException ex) {
			Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

		return result;
	}

	/**
	 * Metodo que Lista todos los usuarios de la base de datos
	 *
	 * @return Todas loss usuarios
	 */
	public static List<Usuario> selectAll() {
		return selectAll("");
	}

	/**
	 * Funcion que selecciona por nombre todos los usuarios de la base de datos que
	 * sea por el pattern
	 *
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve una lista de usuarios
	 */
	public static List<Usuario> selectAll(String pattern) {
		List<Usuario> result = new ArrayList<>();

		try {
			java.sql.Connection csql = ConnectionUtil.getConnection();
			String q = "SELECT * FROM usuario";

			if (pattern.length() > 0) {
				q += " WHERE nombre LIKE ?";
			}

			PreparedStatement ps = csql.prepareStatement(q);

			if (pattern.length() > 0) {
				ps.setString(1, pattern + "%");
			}

			ResultSet rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Usuario u = new Usuario();
					u.setId(rs.getInt("id"));
					u.setCorreo(rs.getString("correo"));
					u.setNombre(rs.getString("nombre"));
					u.setFoto(rs.getString("foto"));
					u.setListas_creadas(null);
					u.setListas_suscrito(null);
					result.add(u);
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

		return result;
	}

	/**
	 * Funcion que selecciona por id todos los usuarios de la base de datos
	 *
	 * @param id id por lo que se filtra el select
	 * @return devuelve una lista de usuarios
	 */
	public static Usuario selectAllForID(int id) {
		Usuario result = new Usuario();

		try {
			java.sql.Connection csql = ConnectionUtil.getConnection();
			String q = "SELECT * FROM usuario WHERE id = ?";

			PreparedStatement ps = csql.prepareStatement(q);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs != null) {
				rs.next();
				result.id = rs.getInt("id");
				result.correo = rs.getString("correo");
				result.nombre = rs.getString("nombre");
				result.foto = rs.getString("foto");
				result.listas_creadas = null;
				result.listas_suscrito = null;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

		return result;
	}

	/**
	 * Funcion que selecciona por nombre todos los usuarios de la base de datos que
	 * sea por el pattern
	 * 
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve una lista de usuarios
	 */
	public static Usuario selectAllForNombre(String nombre) {
		Usuario result = new Usuario();

		try {
			java.sql.Connection csql = ConnectionUtil.getConnection();
			String q = "SELECT * FROM usuario WHERE nombre = ?";

			PreparedStatement ps = csql.prepareStatement(q);

			ps.setString(1, nombre);

			ResultSet rs = ps.executeQuery();

			if (rs != null) {
				rs.next();
				result.id = rs.getInt("id");
				result.correo = rs.getString("correo");
				result.nombre = rs.getString("nombre");
				result.foto = rs.getString("foto");
				result.listas_creadas = null;
				result.listas_suscrito = null;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

		return result;
	}

	/**
	 * Borra de la base de datos el usuario
	 *
	 * @return -1 si no se ha borrado o el id del usuario borrado
	 */
	public int remove() {
		int result = -1;

		if (this.id > 0) {

			try {
				java.sql.Connection csql = ConnectionUtil.getConnection();
				String q = "DELETE FROM usuario WHERE id = " + this.id;
				PreparedStatement ps = csql.prepareStatement(q);
				result = ps.executeUpdate();
				if (result > 0) {
					this.id = -1;
				}
			} catch (SQLException ex) {
				Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		return result;
	}
}
