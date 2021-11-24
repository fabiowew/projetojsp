package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelTelefone;

public class DAOTelefoneRepository {

	private Connection connection;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public DAOTelefoneRepository() {
			connection = SingleConnectionBanco.getConnection();
	}
	
	public void gravaTelefone(ModelTelefone modelTelefone) throws Exception{
		
		String sql = "INSERT INTO public.telefone (numero, usuario_pai_id, usuario_cad_id) values ( ?, ?, ?)";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setString(1, modelTelefone.getNumero());
		preparedSql.setLong(2, modelTelefone.getUsuario_pai_id().getId());
		preparedSql.setLong(3, modelTelefone.getUsuario_cad_id().getId());
		preparedSql.execute();
		connection.commit();
		
	}
	
	public void deleteFone(long id) throws SQLException {
		
		String sql = "delete from telefone where id = ?";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setLong(1, id);
		preparedSql.executeUpdate();
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
			modelTelefone.setUsuario_cad_id(daoUsuarioRepository.consultaUsuarioID(rs.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultaUsuarioID(rs.getLong("usuario_pai_id")));
			
			retorno.add(modelTelefone);
		}
		return retorno;
	}
	public boolean existeFone(String fone, Long idUser) throws SQLException {
		String sql = "select count(1) > 0 as existe from telefone where usuario_pai_id = ? and numero =?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, idUser);
		preparedStatement.setString(2, fone);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		resultSet.next();
		return resultSet.getBoolean("existe");
		
	}
	
}