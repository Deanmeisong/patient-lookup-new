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
            </ul>
        </div>
        <div id="create-lookupPerson" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
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
            <g:form resource="${this.lookupPerson}" method="POST">
                <fieldset class="form">
                    <div>
                        First name: <g:textField name="firstName" />
                        <g:hiddenField name="encryptedFirstName" value="none" />
                    </div>

                    <div>
                        Last name: <g:textField name="lastName" />
                        <g:hiddenField name="encryptedLastName" value="none" />
                    </div>

                    <div>
                        Date of Birth: <g:datePicker name="dateOfBirth" />
                    </div>

                    <div>
                        Patient Database ID: <g:textField name="patientDbId" />
                        <g:hiddenField name="encryptedPatientDbId" value="0" />
                    </div>

                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>