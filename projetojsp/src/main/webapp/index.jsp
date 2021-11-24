<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Sistema</title>

<style type="text/css">
form{
	position:absolute;
	top: 40%;
	left: 33%;
	right: 33%;
	
}

h2{
	position: absolute;
	top: 35%;
	left: 33%;
	right: 33%
}
h3{
	position: absolute;
	top: 25%;
	left: 33%;
	right: 33%
}
h4{
	color: red;
}
h7{
	position: absolute;
	top: 30%;
	left: 33%;
	right: 33%;
	color: black;
}
</style>

</head>
<body>
	<h3>Bem  Vindo ao Sistema<h3></h3>
	<!-- <h7>Servlets + Jsp + Jdbc + Sql+ Java + Bootstrap + Ajax + Json + Junit + Maven + Git + Html + Javascript + Jstl + Dshaboard</h7> -->
	<form action="<%= request.getContextPath() %>/ServletLogin" method="post" form class="row g-3 needs-validation" novalidate>
		<input type="hidden" value="<%=request.getParameter("url")%> name="url">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<!-- Bootstrap CSS -->
		<link
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
			rel="stylesheet"
			integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU"
			crossorigin="anonymous">

		<div class="mb-3 form-check">
			<label class="form-label">Login:</label> 
			<input  class="form-control" name="login" type="text" required>
			<div class="invalid-feedback">
				Obrigatorio!
			</div>
			<div class="valid-feedback">
				ok
			</div>
		</div>

		<div class="mb-3 form-check">
			<label  class="form-label">Senha:</label>
			<input class="form-control" name="senha" type="password" required>
			<div class="invalid-feedback">
				Obrigatorio!
			</div>
			<div class="valid-feedback">
				ok
			</div>
		</div>
		
		<div class="mb-3 form-check">
			<label  class="form-label"><h4>Login e Senha -> admin</h4></label>
		</div>

			<input type="submit" value="Enviar" class="btn btn-primary">
		<h5>${msg}</h5>

	</form>

	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
		crossorigin="anonymous"></script>
<script type="text/javascript">

//Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()

</script>
</body>
</html>