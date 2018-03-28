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
            <th>History</th>
        </tr>
        <g:each in="${lookupPersonList}" var="p" >
            <tr>
                <td><f:display bean="${p}" property="firstName" />
                <g:link controller="lookupPerson" action="editLookupPerson" params='[lookupPersonId:"${p.id}", patientSelectionCriteria:"${patientSelectionCriteria}"]'><asset:image src="edit-icon.png" width="18" height="18"/></g:link>
                <g:link controller="lookupPerson" action="delete" params='[lookupPersonId:"${p.id}", patientSelectionCriteria:"${patientSelectionCriteria}"]' onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><g:img dir="images" file="delete-ico.png" width="18" height="18" title="delete file" /></g:link>
                </td>
                <td><f:display bean="${p}" property="lastName" /></td>
                <td><g:formatDate format="dd/MM/yyyy" date="${p.dateOfBirth}" /></td>
                <td><f:display bean="${p}" property="patientDbId" /></td>
                <td><g:link controller ="audit" action="getAuditLogging" params='[ className: p.getClass().getSimpleName(),objectId: p.id , previousController:"LookupPerson"]'><g:img dir="images" src="change_history.png" title="view history"/></g:link>
                </td>
            </tr>
        </g:each>
    </table>


    <div class="pagination">
        %{--<g:paginate total="${lookupPersonCount ?: 0}" params="${params}" />--}%
        <g:paginate next="Forward" prev="Back"
                    maxsteps="0" controller="lookupPerson"
                    action="showSearch" total="${lookupPersonCount ?: 0}" params="${params}" />
    </div>
</div>
</body>
</html>