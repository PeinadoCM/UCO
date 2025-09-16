<jsp:useBean id="customerBean" scope="session" class="display.CustomerBean"></jsp:useBean>
<%
    if(customerBean == null || customerBean.getCorreo() == null || customerBean.getCorreo() == "" || customerBean.getAdmin() == true){
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <script src="/pract3/js/volver_atras.js"></script>
    </head>
</html>
<%
        return;
    }
%>