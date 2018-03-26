<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'lookupPerson.label', default: 'LookupPerson')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-lookupPerson" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>
<div id="show-lookupPerson" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <script type="text/javascript">

        function checkForm(form)
        {
            if(!form.terms.checked) {
            alert("Please indicate that you accept the Terms and Conditions");
            form.terms.focus();
            return false;
        }
            return true;
        }

    </script>

    <form name="myForm" controller="lookupPerson" action="populatePatientsInfo" onsubmit="return checkForm(this);">
        <p><input type="checkbox" name="terms"> I accept the <u>Terms and Conditions</u></p>
        <p><input type="submit"></p>
    </form>


</div>
</body>
</html>