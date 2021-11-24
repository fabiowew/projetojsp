package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import beandto.BeanDtoGraficoSalarioUser;
import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

@SuppressWarnings("unused")
public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();

	}
	
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado, String dataInicial, String dataFinal) throws Exception {

		String sql = "select avg(rendamensal) as media_salarial, perfil from model_login where usuario_id = ? and datanascimento >= ? and datanascimento <= ? group by perfil";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setLong(1, userLogado);
		preparedSql.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		preparedSql.setDate(3, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
		
		ResultSet resultSet = preparedSql.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while(resultSet.next()) {
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");		
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;	
	}
	
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado) throws Exception{
		
		String sql = "select avg(rendamensal) as media_salarial, perfil from model_login where usuario_id = ? group by perfil";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setLong(1, userLogado);
		
		ResultSet resultSet = preparedSql.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while(resultSet.next()) {
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");		
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;		
	}
	

	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {

		if (objeto.isNovo()) {
			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, dataNascimento, rendamensal) VALUES (?,?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			preparedSql.setLong(5, userLogado);
			preparedSql.setString(6, objeto.getPerfil());
			preparedSql.setString(7, objeto.getSexo());
			preparedSql.setString(8, objeto.getCep());
			preparedSql.setString(9, objeto.getLogradouro());
			preparedSql.setString(10, objeto.getBairro());
			preparedSql.setString(11, objeto.getLocalidade());
			preparedSql.setString(12, objeto.getUf());
			preparedSql.setString(13, objeto.getNumero());
			preparedSql.setDate(14, objeto.getDataNascimento());
			preparedSql.setDouble(15, objeto.getRendaMensal());
			

			preparedSql.execute();
			connection.commit();
			
			if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser =?, extensaofotouser=? where login =?";
				
				preparedSql = connection.prepareStatement(sql);
				
				preparedSql.setString(1, objeto.getFotouser());
				preparedSql.setString(2, objeto.getExtensaofotouser());
				preparedSql.setString(3, objeto.getLogin());
				
				preparedSql.execute();
				connection.commit();
			}
			

		} else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?,numero=?, datanascimento=?, rendamensal=? WHERE id = " + objeto.getId() + ";";
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			preparedSql.setString(5, objeto.getPerfil());
			preparedSql.setString(6, objeto.getSexo());
			preparedSql.setString(7, objeto.getCep());
			preparedSql.setString(8, objeto.getLogradouro());
			preparedSql.setString(9, objeto.getBairro());
			preparedSql.setString(10, objeto.getLocalidade());
			preparedSql.setString(11, objeto.getUf());
			preparedSql.setString(12, objeto.getNumero());
			preparedSql.setDate(13, objeto.getDataNascimento());
			preparedSql.setDouble(14, objeto.getRendaMensal());


			preparedSql.executeUpdate();
			connection.commit();

			if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser =?, extensaofotouser=? where id =?";
				
				preparedSql = connection.prepareStatement(sql);
				
				preparedSql.setString(1, objeto.getFotouser());
				preparedSql.setString(2, objeto.getExtensaofotouser());
				preparedSql.setLong(3, objeto.getId());
				
				preparedSql.execute();
				connection.commit();
			}
			
		}
		return this.consultaUsuario(objeto.getLogin(), userLogado);
	}
	

	
	//Consulta todos os Usuários Paginada
    public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws SQLException{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		
		String sql = "SELECT * FROM model_login where useradmin is false and usuario_id =" + userLogado + " order by nome offset "+offset+" limit 5";
		

		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		
		while(resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setEmail(resultado.getString("email"));
			//modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

		
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
	public int totalPagina(long userLogado) throws SQLException {
		
		String sql = "select count(1) as total from model_login where usuario_id =" + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
	
		ResultSet resultado = statement.executeQuery();	
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		if(resto > 0) {
			pagina++;
		}
		
		return pagina.intValue();
	}
    
	//Consulta todos os Usuários
    public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws Exception{
		
  		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
  		
  		String sql = "SELECT * FROM model_login where useradmin is false and usuario_id =" + userLogado;

  		PreparedStatement statement = connection.prepareStatement(sql);
  		
  		ResultSet resultado = statement.executeQuery();
  		
  		
  		while(resultado.next()) {
  			ModelLogin modelLogin = new ModelLogin();
  			modelLogin.setId(resultado.getLong("id"));
  			modelLogin.setNome(resultado.getString("nome"));
  			modelLogin.setEmail(resultado.getString("email"));
  			modelLogin.setLogin(resultado.getString("login"));
  			modelLogin.setSenha(resultado.getString("senha"));
  			modelLogin.setUseradmin(resultado.getBoolean("userAdmin"));
  			modelLogin.setPerfil(resultado.getString("perfil"));
  			modelLogin.setSexo(resultado.getString("sexo"));
  			modelLogin.setFotouser(resultado.getString("fotouser"));
  			modelLogin.setCep(resultado.getString("cep"));
  			modelLogin.setLogradouro(resultado.getString("logradouro"));
  			modelLogin.setBairro(resultado.getString("bairro"));
  			modelLogin.setNumero(resultado.getString("numero"));
  			modelLogin.setLocalidade(resultado.getString("localidade"));
  			modelLogin.setUf(resultado.getString("uf"));
  			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
  			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));
  			
  			modelLogin.setTelefones(this.listaFone(modelLogin.getId()));
  		
  			retorno.add(modelLogin);
  		}
  		return retorno;
  	}
    
	//Consulta todos os Usuários
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login where useradmin is false and usuario_id =" + userLogado + " and datanascimento >= ? and datanascimento <= ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));

		ResultSet resultado = statement.executeQuery();
		
		
		while(resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setUseradmin(resultado.getBoolean("userAdmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));
			
			modelLogin.setTelefones(this.listaFone(modelLogin.getId()));
		
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
    public List<ModelLogin> consultaUsuarioList(Long userLogado) throws SQLException{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		
		String sql = "SELECT * FROM model_login where useradmin is false and usuario_id =" + userLogado + " limit 5";
		

		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		
		while(resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setUseradmin(resultado.getBoolean("userAdmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));

		
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
    
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws SQLException{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%"+nome+"%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setUseradmin(resultado.getBoolean("userAdmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));

		
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, int offset) throws SQLException{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%"+nome+"%");
		statement.setLong(2, userLogado);

		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setUseradmin(resultado.getBoolean("userAdmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));

		
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
	public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws SQLException{
		
		String sql = "select count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%"+nome+"%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();	
		resultado.next();
		Double cadastros = resultado.getDouble("total");
		Double porpagina = 5.0;
		Double pagina = cadastros / porpagina;
		Double resto = pagina % 2;
		
		if(resto > 0) {
			pagina++;
		}
		return pagina.intValue();
	}

	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login + "')  and useradmin is false and usuario_id =" + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setUseradmin(resultado.getBoolean("userAdmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));


		}

		return modelLogin;
	}
	
	public ModelLogin consultaUsuarioLogado(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login + "') ";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setUseradmin(resultado.getBoolean("userAdmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));

		}

		return modelLogin;
	}
	
	public ModelLogin consultaUsuario(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login + "')  and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setUseradmin(resultado.getBoolean("userAdmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));

		}

		return modelLogin;
	}
	
	public ModelLogin consultaUsuarioID(Long id) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id = ?  and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));

		}

		return modelLogin;
	}
	
	
	public ModelLogin consultaUsuarioID(String id, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id = ?  and useradmin is false and usuario_id =?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));

		}

		return modelLogin;
	}

	public boolean validarLogin(String login) throws Exception {

		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('" + login + "')";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		resultado.next();// Para ele entrar nos resultados do sql
		return resultado.getBoolean("existe");

	}
	
	public void DeletarUser(String idUser) throws SQLException {
		String sql = "DELETE FROM public.model_login	WHERE id=?  and useradmin is false";
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, Long.parseLong(idUser));
		prepareSql.executeUpdate();
		connection.commit();
	}
	
public List<ModelTelefone> listaFone(Long idUserPai) throws Exception{
		
		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
		
		String sql = "select * from telefone where usuario_pai_id = ?";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setLong(1, idUserPai);
		
		ResultSet rs = preparedSql.executeQuery();
		
		while(rs.next()) {
			
			ModelTelefone modelTelefone = new ModelTelefone();
			
			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuario_cad_id(this.consultaUsuarioID(rs.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(this.consultaUsuarioID(rs.getLong("usuario_pai_id")));
			
			retorno.add(modelTelefone);
		}
		return retorno;
	}

	
}