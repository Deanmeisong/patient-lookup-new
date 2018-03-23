<%--
  Created by IntelliJ IDEA.
  User: localuseradmin
  Date: 27/02/2018
  Time: 12:54 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'lookupPerson.label', default: 'LookupPerson')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
<a href="#create-lookupPerson" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="audit" controller="audit" action="list">Audit</g:link></li>
        <li><g:link class="audit" controller="audit" action="listRead">AuditRead</g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="create-lookupPerson" class="content scaffold-create" role="main">
    <h1><g:message code="default.search.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${this.lookupPerson}">
        <ul class="errors" role="alert">
            <g:eachError bean="${this.lookupPerson}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>

    <script>
        function validateForm(){
            var firstName = document.forms["myForm"]["firstName"].value;
            var lastName = document.forms["myForm"]["lastName"].value;
            var patientDbId = document.forms["myForm"]["patientDbId"].value;
            if (!(firstName||lastName||patientDbId)){
                alert("Please input at least one parameter to search");
                return false;
            }
            var reg = new RegExp("^[0-9]*$");
            if(!reg.test(patientDbId)){
                alert("patient database id must be numbers！");
                return false;
            }
        }
    </script>

    <form name="myForm" controller="lookupPerson" action="showSearch" onsubmit="return validateForm()">
        <label>First Name: </label>
        <g:textField name="firstName"/><br/>
        <label>Last Name: </label>
        <g:textField name="lastName"/><br/>
        <label>Patient DataBase ID: </label>
        <g:textField name="patientDbId"/><br/>
        <input type="submit" value="search">
    </form>

</div>
</body>
</html>
