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

            <table>
                <tr>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Date of Birth</th>
                    <th>Database ID</th>
                </tr>
                <g:each in="${lookupPersonList}" var="p" >
                    <tr>
                        <td><f:display bean="${p}" property="firstName" /></td>
                        <td><f:display bean="${p}" property="lastName" /></td>
                        <td><g:formatDate format="dd/MM/yyyy" date="${p.dateOfBirth}" /></td>
                        <td><f:display bean="${p}" property="patientDbId" /></td>
                    </tr>
                </g:each>
            </table>

            <f:table collection="${lookupPersonList}" domainClass="patient.lookup.LookupPerson" />

            <div class="pagination">
                <g:paginate total="${lookupPersonCount ?: 0}" />
            </div>
        </div>
    </body>
</html>