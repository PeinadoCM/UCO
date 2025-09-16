<jsp:useBean id="customerBean" scope="session" class="display.CustomerBean"></jsp:useBean>
<%
    if(customerBean != null && customerBean.getCorreo() != null && customerBean.getCorreo() != ""){

        if(customerBean.getAdmin() == true){
            response.sendRedirect("/pract3/mvc/control/admin/pagina_principalAdminController.jsp");
        }
        else{
            response.sendRedirect("/pract3/mvc/control/user/pagina_principalUserController.jsp");
        }
        return;
    }
%>