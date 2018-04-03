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
        <li><g:link controller="lookupPerson" action="search">Search</g:link></li>
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

<footer>
    <span class="build-number">wrwerw<g:meta name="info.app.build.date"/></span>
</footer>

<div id="footer">
    <div style="text-align:center;height:27px;padding-top:5px;">
        <span style="float:left;color: white;text-align:left; font-size: 1.2em">Build: <g:meta name="build.time"/></span>
        <font color="white" size="2" > Copyright <g:formatDate format="yyyy" date="${new Date()}"/> Australian Phenomics Facility | Australian National University | Centre for Personalised Immunology | Canberra Clinical Genomics</font>
        <span style="float:right;color: white;text-align:left;"></span>
    </div>
</div>

<table>
    <thead>
    <tr><th>Name</th><th>Value</th></tr>
    </thead>
    <tbody>
    <tr><td>Build time</td><td><g:meta name="build.time"/></td></tr>
    <tr><td>Build java version</td><td><g:meta name="build.java.version"/></td></tr>
    <tr><td>Build host</td><td><g:meta name="build.host"/></td></tr>
    </tbody>
</table>

</body>
</html>
