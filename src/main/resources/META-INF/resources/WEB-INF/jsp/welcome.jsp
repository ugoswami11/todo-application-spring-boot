<%request.setAttribute("pageTitle", "Welcome");%>
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	<h1>Welcome ${name}</h1>
	<a href="list-todos">Manage Todos</a>
</div>
<%@ include file="common/footer.jspf"%>