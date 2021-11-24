<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="model.ModelLogin"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>
<jsp:include page="theme-loader.jsp"></jsp:include>
<body>

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="navbarmymenu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start -->

						<jsp:include page="pageheader.jsp"></jsp:include>

						<div class="pcoded-inner-content">
							<div class="main-body">
								<div class="page-wrapper">
									<div class="page-body"></div>

									<h4 class="sub-title">Cadastro de Telefones</h4>
									<!-- Qualquer ação executara vai enviar os dados para a servlet "ServletUsuarioController" e com a acao do Botão -->
									<form class="form-material" action="<%=request.getContextPath()%>/ServletTelefone" method="post" id="formFone">
										<label class="float-label"> </label> 

										<div class="form-group form-default">
											
											<label>ID User:</label> 
											<input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${modelLogin.id}">
											<span class="form-bar"></span>
										</div>
										
										<div class="form-group form-default">
											<label>Nome: </label>
											<input readonly="readonly" type="text" name="nome" id="nome" autocomplete="off" class="form-control" value="${modelLogin.nome}">
											<span class="form-bar"></span> 
											
										</div>
										
										<div class="form-group form-default">
											<label>Numero: </label>
											<input required="required" type="text" name="numero" id="numero" autocomplete="off" class="form-control" value="${modelLogin.numero}">
											<span class="form-bar"></span> 
											
										</div>
										<button class="btn btn-success waves-effect waves-light">Salvar</button>
										
									</form>
								</div>
								<span id="msg"> ${msg} </span>
								<div id="styleSelector"></div>
								
								<div style="height: 300px; overflow: scroll;" >	
					<table class="table" id="tabelaresultadosview">
					<thead>
					    <tr>
					      <th scope="col">ID</th>
					      <th scope="col">Numero</th>
					      <th scope="col">Excluir</th>
					    </tr>
					  </thead>
					  <tbody>
					  
					    <c:forEach var="f" items="${modelTelefones}"> 
					    	<tr>
					    	<td><c:out value="${f.id}"></c:out></td>
					    	<td><c:out value="${f.numero}"></c:out></td>
					    	<td><a class="btn btn-success" href="<%= request.getContextPath() %>/ServletTelefone?acao=excluir&idFone=${f.id}&userpai=${modelLogin.id}">Excluir</a></td>
					    	</tr>
					    </c:forEach>
					  </tbody>
					</table>
					
					</div>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="javascriptfile.jsp"></jsp:include>

</body>

</html>
