<%--
  Created by IntelliJ IDEA.
  User: soujitsuken
  Date: 18/3/18
  Time: 20:09
--%>


<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'lookupPerson.label', default: 'LookupPerson')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-lookupPerson" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="list-lookupPerson" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    Count: ${lookupPersonCount}

    <p>this is for test</p>
    <p>lookupPerson: ${lookupPerson}</p>

    <g:form name="myForm" action="edit" id="${lookupPerson.id}">
        <g:hiddenField name="patientSelectionCriteria" value="${patientSelectionCriteria}" />
        <fieldset class="form">
            <div>
                First name: <g:textField name="firstName" value="${lookupPerson.firstName}"/>
                <g:hiddenField name="encryptedFirstName" value="${lookupPerson.encryptedFirstName}" />
            </div>

            <div>
                Last name: <g:textField name="lastName" value="${lookupPerson.lastName}"/>
                <g:hiddenField name="encryptedLastName" value="${lookupPerson.encryptedLastName}" />
            </div>

            <div>
                Date of Birth: <g:datePicker name="dateOfBirth" value="${lookupPerson.dateOfBirth}"/>
            </div>

            <div>
                Patient Database ID: <g:textField name="patientDbId" value="${lookupPerson.patientDbId}"/>
                <g:hiddenField name="encryptedPatientDbId" value="${lookupPerson.encryptedPatientDbId}" />
            </div>

        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="create" class="create" value="${message(code: 'default.button.update.label', default: 'Update')}" />
        </fieldset>
    </g:form>


    <div class="pagination">
        <g:paginate total="${lookupPersonCount ?: 0}" />
    </div>
</div>
</body>
</html>
