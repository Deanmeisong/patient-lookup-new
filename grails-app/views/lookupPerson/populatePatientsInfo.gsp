<html>
<head>
    <meta name="layout" content="main">
    <title>Populate Lookup Persons</title>

    <script>
        $(function() {
            $(".lined").linedtextarea(
                <g:if test="${rowError != null}">
                {selectedLine: ${rowError+1}}
                </g:if>
            );

            $('.lined').on('paste', function () {
                var element = this;
                setTimeout(function () {
                    $(element).attr('readonly', 'true');
                }, 10);

                $(element).css('background-color', '#EBECE4');
            });

            $('#reset').click( function() {
                $("#textarea").removeAttr('readonly');
                $("#textarea").css('background-color', 'white');
            });

        });

    </script>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'lookupPerson.label', default: 'LookupPerson')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>

</head>
<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>
<h1>Import Patient</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
<g:form action="doPopulatePatientsInfo">

    <table>
        <tr>
            <td style="width: 1020px;">
                <g:textArea id="textarea" name="data" value="${data}" rows="50" cols="130" style="width:1000px; height:500px;" class="lined" />
            </td>
            <td valign="top" align="left">
                <span style="white-space: pre;">
                    ${report }
                </span>
            </td>
        </tr>
    </table>
    <br/>
    <g:submitButton name="submit" value="Submit" />
    <input type="reset" name="reset" value="Reset" id="reset" />
</g:form>


</body>

</html>