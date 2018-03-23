<%--
  Created by IntelliJ IDEA.
  User: soujitsuken
  Date: 18/3/19
  Time: 01:23
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>
<h2>Audit Read</h2>
<table>
    <tr>
        <th>DateRead</th>
        <td>Actor</td>
        <td>Query</td>

    </tr>
    <g:each in="${auditReadList}" var="audit" >
        <tr>
            <td><g:formatDate format="dd/MM/yyyy" date="${audit.dateRead}" /></td>
            <td>${audit.actor}</td>
            <td>${audit.query}</td>
        </tr>
    </g:each>
</table>


<div class="pagination">
    <g:paginate total="${lookupPersonCount ?: 0}" />
</div>
</body>
</html>