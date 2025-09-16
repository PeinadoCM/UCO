<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/iniciosesion.jsp" %>

<jsp:setProperty property="correo" value="" name="customerBean"/>
<jsp:setProperty property="contraseña" value="" name="customerBean"/>
<jsp:setProperty property="nombre" value="" name="customerBean"/>
<jsp:setProperty property="fecha_nacimiento" value="" name="customerBean"/>
<jsp:setProperty property="fecha_inscripcion" value="" name="customerBean"/>
<jsp:setProperty property="admin" value="" name="customerBean"/>

<%

    response.sendRedirect("/pract3");
    return;
%>