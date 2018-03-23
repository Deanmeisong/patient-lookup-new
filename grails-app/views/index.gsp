<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Sequence Request Database</title>
    <style>
    .container {
        position: relative;
        font-family: Arial;
    }

    .text-block {
        position: absolute;
        top: 20%;
        left: 5%;
        color: black;
        padding-left: 20px;
        padding-right: 20px;
    }
    h1{
        color: #999999;
        font-family: arial, sans-serif;
        font-size: 90px;
        font-weight: bold;
        margin-top: 0px;
        margin-bottom: 0px;
    }
    </style>
</head>
<body>

    <content tag="nav">
        <li><g:link controller="lookupPerson" action="Search">Search</g:link></li>
        <li><g:link controller="lookupPerson" action="populatePatientsInfo">Populate</g:link></li>
        <li><g:link controller="lookupPerson" action="create">Create</g:link></li>
    </content>

    <div class="svg" role="presentation">
        <div class="container"">
            <asset:image src="header.jpg" class="grails-logo"/>
            <div class="text-block">
                <h1>LookUp DataBase</h1>
            </div>
        </div>

    </div>


</body>
</html>
