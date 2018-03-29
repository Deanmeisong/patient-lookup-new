<html>
<head>
    <meta name="layout" content="main"/>
    <title>Populate Lookup Persons</title>
    <style>
    h1 {
        font-size: 40px;
        font-family: "Times New Roman", Times, serif;
        color: deepskyblue;
    }
    </style>
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
</head>
<body>
%{--<div class="nav" role="navigation">--}%
    %{--<ul>--}%
        %{--<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>--}%
    %{--</ul>--}%
%{--</div>--}%
%{--<h1>Import Patient</h1>--}%

<g:form action="doPopulatePatientsInfo">
    <table>
        <tr>
            <h1>Import Patient</h1><p style="color: red; font-size: 20px"><asset:image src="warningRed.png" width="30" height="30"/> Please read the pupulation Instruction Before Doing Import</p>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
        </tr>
        <tr>
            <td width="40%" style="width: 1020px;">
                <g:textArea id="textarea" name="data" value="${data}" rows="20" cols="130" style="width:800px; height:500px;" class="lined" />
            </td>
            <td width="60%">
                <h1><asset:image src="attention.png" width="60" height="60"/>Population Instruction</h1>
                <p style="color: black; font-size: 20px">Each row represents a person including all the person's information(First Name, Second Name, Date of Birth, Patient Database Id).
                    Each row must have four columns representing First Name, Second Name, Date of Birth and Patient Database Id sequentially. Columns must be
                    seperated by "tab".
                </p>
            </td>
        </tr>
    </table>
    %{--<table>--}%
        %{--<tr>--}%
            %{--<th>Import Patient</th>--}%
        %{--</tr>--}%
        %{--<tr>--}%
            %{--<td width="20%" style="width: 1020px;">--}%
                %{--<g:textArea id="textarea" name="data" value="${data}" rows="20" cols="130" style="width:800px; height:500px;" class="lined" />--}%
            %{--</td>--}%
            %{--<td width="60%">--}%
                %{--<h1>Population Instruction</h1>--}%
                %{--<p></p>--}%
            %{--</td>>--}%
        %{--</tr>--}%
    %{--</table>--}%
    <g:submitButton name="submit" value="Submit" />
    <input type="reset" name="reset" value="Reset" id="reset" />
</g:form>


</body>

</html>