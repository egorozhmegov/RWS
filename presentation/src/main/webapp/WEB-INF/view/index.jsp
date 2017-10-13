<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>RWS</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css"/>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="css/index.css"/>
</head>
<body>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">
        <div class="bootstrap-iso">
            <div class="row">
                <form method="post" action="/client/search">
                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <input class="form-control" type="text" id="station1" name="station1" placeholder="From" required/>
                    </div>

                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <input class="form-control" type="text" id="station2" name="station2" placeholder="To" required/>
                    </div>


                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <input class="form-control" id="date" name="date" placeholder="MM/DD/YYY" type="text" required/>
                    </div>

                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <button type="submit" class="btn btn-primary btn-md">Search</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="col-md-3"></div>


<script>
    $(document).ready(function () {
        var date_input = $('input[name="date"]');
        var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
        var options = {
            format: 'mm/dd/yyyy',
            container: container,
            todayHighlight: true,
            autoclose: true
        };
        date_input.datepicker(options);
    })
</script>

</body>
</html>
