package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnectionBanco;
import dao.DAOVersionadorBanco;


@WebFilter(urlPatterns = { "/principal/*" }) // Interceptar todas as requisicoes que vierem do projeto ou mapeamento
public class FilterAutenticacao implements Filter {

	private static Connection connection;

	public FilterAutenticacao() {

	}

	// Encerra os processos quando o servidor e parado
	// Mataria os processos de conexao com o banco
	public void destroy() {
	}

	// Intercepta as requisicoes e da as respostas no sistema
	// Tudo que fizer no sistema vai fazer por aqui EX: Valore autenticacao, dar
	// Commit e Rollback de transacoes do banco
	// validar e fazer redirecionamento de paginas
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			String usuarioLogado = (String) session.getAttribute("usuario");
			String urlParaAutenticar = req.getServletPath();// Uel que está sendo acessada

			// Validar se esta logado senao redireciona para a tela de login
			if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {// Nao Esta logado

				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return;// para aa execucao e redireciona para o login

			} else {
				chain.doFilter(request, response);
			}
			connection.commit();//se deu tudo certo faz o commit

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			try {
				
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	// Inicia os processos ou recursos quando o servidor sobe o projeto
	
	
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
		
		DAOVersionadorBanco daoVersionadorBanco = new DAOVersionadorBanco();
		String caminhoPastaSQL = fConfig.getServletContext().getRealPath("versionadobancosql") + File.separator;
		
		File[] filesSql = new File(caminhoPastaSQL).listFiles();
		
		try {
			for (File file : filesSql) {
				boolean arquivoJaRodado = daoVersionadorBanco.arquivoSqlRodado(file.getName());
				
				if(!arquivoJaRodado) {
					
					FileInputStream entradaArquivo = new FileInputStream(file);
					Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");
					
					StringBuilder sql = new StringBuilder();
					
					while(lerArquivo.hasNext()) {
					
						sql.append(lerArquivo.nextLine());
						sql.append("\n");
						
					}
					connection.prepareStatement(sql.toString()).execute();
					daoVersionadorBanco.gravaArquivoSqlRodado(file.getName());
					connection.commit();
					lerArquivo.close();
				}
				
			}
		}catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}