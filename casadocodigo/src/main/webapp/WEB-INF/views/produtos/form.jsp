<!-- Diretiva JSP -->
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!-- Import da taglib JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Casa do Código</title>
</head>
<body>
	<!-- modelAttribute serve para dar o nome da entidade que o form está utilizando,
		 tirando a necessidade de colocar o nome dela em casa um de seus campos. -->
	<!-- mvcUrl serve para resolver a URL do controller que receberá a requisição,
	     e para isso precisa do nome do controller ou suas iniciais, além do método
	     que receberá a requisição. Para que isso funcione, os request mappings devem
	     ter / no começo do nome, do contrário ele concatenará todos os paths
	     sem as devidas / -->
    <form:form action="${s:mvcUrl('PC#gravar').build()}" method="POST"
    	modelAttribute="produto">
        <div>
            <label>Título</label>
            <form:input path="titulo"/>
            <form:errors path="titulo"/>
        </div>
        <div>
            <label>Descrição</label>
            <form:textarea rows="10" cols="20" path="descricao"/>
            <form:errors path="descricao"/>
        </div>
            <label>Páginas</label>
            <form:input path="paginas"/>
            <form:errors path="paginas"/>
        <div>
        <div>
        	<label>Data de lançamento</label>
        	<form:input path="dataLancamento"/>
        	<form:errors path="dataLancamento"/>
        </div>

        <!-- Usando JSTL aqui, para facilitar o uso do JSP -->
        <c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
            <div>
                <label>${tipoPreco}</label>
                <form:input path="precos[${status.index}].valor"/>
                <form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}"/>
            </div>
        </c:forEach>

        <!--
        <div>
            <label>E-book</label>
            <input type="text" name="ebook">
        </div>
        <div>
            <label>Impresso</label>
            <input type="text" name="impresso">
        </div>
        <div>
            <label>Combo</label>
            <input type="text" name="combo">
        </div>
        -->
        <button type="submit">Cadastrar</button>

        </div>
    </form:form>
</body>
</html>