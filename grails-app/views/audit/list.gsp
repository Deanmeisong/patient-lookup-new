<%--
  Created by IntelliJ IDEA.
  User: soujitsuken
  Date: 18/3/18
  Time: 22:50
--%>

<!DOCTYPE html>
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

        <g:if test="${auditHistory?.size() > 0 }">
            <div class="list">
                <table>
                    <thead>
                    <tr>
                        <th>ShowId</th>
                        <th>Actor Name</th>
                        <th>Class Name</th>
                        <th>Event Name</th>
                        <th>Property Name</th>
                        <th>Old Value</th>
                        <th>New Value</th>
                        <th>Created Date</th>
                        <th>Last Updated Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${auditHistory}" status="i" var="auditHistoryInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${showId}</td>
                            <td>${ auditHistoryInstance.actor}</td>
                            <td>${ auditHistoryInstance.className}</td>
                            <td>${ auditHistoryInstance.eventName}</td>
                            <td>${ auditHistoryInstance.propertyName}</td>
                            <td>${ auditHistoryInstance.oldValue}</td>
                            <td>${ auditHistoryInstance.newValue}</td>
                            <td>${ auditHistoryInstance.dateCreated}</td>
                            <td>${ auditHistoryInstance.lastUpdated}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </g:if>
        <g:else>
            <div style="padding:30px;">
                None available
            </div>
        </g:else>
    <div class="pagination">
        <g:paginate total="${lookupPersonCount ?: 0}" />
    </div>
</div>
</body>
</html>