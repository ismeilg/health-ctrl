<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" type="image/png" href="/images/favicon.png"/>
    
    <title>Health Ctrl</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/login-page.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Rubik|Suez+One" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Nunito" rel="stylesheet">

    <!--validation scripts-->
    <script src="js/signup_validation.js"></script>
    
     <script>
        function submitForm() {
           var $signupform=$('#formId').val();
          $('#'+$signupform).submit();
        	//document.getElementById("signupform").submit();
            }
</script>
</head>

<body>

   <a href="index.html">
    <img class="logo-img" src="images/ver1%20cropped.png"></a>
    
    <div class="container">
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <div class="panel-title">Sign In</div>
                </div>

                <div style="padding-top:30px" class="panel-body">

                    <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                     <form action="UserRegistrationServlet" method="post" id="loginform" class="form-horizontal" role="form">
						<input type="hidden" name="command" value="Login">

                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input id="login-username" type="text" class="form-control" name="email" placeholder="Email" required>
                        </div>

                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input id="login-password" type="password" class="form-control" name="password" placeholder="Password" required>
                        </div>

                        <div style="margin-top:10px" class="form-group">
                            <!-- Button -->

                            <div class="col-sm-12 controls">
                                <input type="submit" id="btn-login" href="#" class="btn btn-success" value="Login" >
                            </div>
                        </div>


                        <div class="form-group">
                            <div class="col-md-12 control">
                                <div style="border-top: 1px solid#abacad; padding-top:15px; font-size:90%">
                                    Don't have an account!
                                    <a href="#" onClick="$('#loginbox').hide(); $('#signupbox').show()">
                                            Sign Up Here
                                        </a>
                                </div>
                            </div>
                        </div>
                    </form>

                    <!--SIGN-UP-->

                </div>
            </div>
        </div>
        <div id="signupbox" style="display:none; margin-top:50px" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <div class="panel-title">Sign Up</div>
                    <div><a id="signinlink" href="#" onclick="$('#signupbox').hide(); $('#loginbox').show()">Sign In</a></div>
                </div>
                <div class="panel-body">
                    <form id="signupform" name="signupform" action="UserRegistrationServlet" method="post" class="form-horizontal" onsubmit="return validateFom();">
                    <input type="hidden" name="command" value="Register">
                        <div id="errorBox"></div>

                        <div class="form-group">
                            <label for="email" class="col-md-3 control-label">Email</label>
                            <div class="col-md-9">
                                <input type="text" id="email" class="form-control" name="email" placeholder="Email Address">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="firstname" class="col-md-3 control-label">First Name</label>
                            <div class="col-md-9">
                                <input type="text" id="fname" class="form-control" name="first_name" placeholder="First Name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-md-3 control-label">Last Name</label>
                            <div class="col-md-9">
                                <input type="text" id="lname" class="form-control" name="last_name" placeholder="Last Name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-md-3 control-label">Password</label>
                            <div class="col-md-9">
                                <input type="password" id="passwd" class="form-control" name="password" placeholder="Password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="age" class="col-md-3 control-label">Birthday</label>
                            <div class="col-md-9">
                                <div>
                                 <select id="day" name="birthday_day">                                   
                                        <script type="text/javascript">
                                            var e = document.getElementById('day');
                                            var j = -1;
                                            while (j++ < 31) {
                                                var s = document.createElement('option');
                                                var e = document.getElementById('day');
                                                if (j == 0) {
                                                    s.text = "Day";
                                                    s.value = "na";
                                                    try {
                                                        e.add(s, null);
                                                    } catch (ex) {
                                                        e.add(s);
                                                    }
                                                } else {
                                                    s.text = j;
                                                    s.value = j;
                                                    try {
                                                        e.add(s, null);
                                                    } catch (ex) {
                                                        e.add(s);
                                                    }
                                                }
                                            }
                                        </script>
                                    </select> &nbsp;&nbsp;
                                    <select id ="month" name="birthday_month">
                                      <option value="" selected >Month</option>
                                      <option value="1">Jan</option>
                                      <option value="2">Feb</option>
                                      <option value="3">Mar</option>
                                      <option value="4">Apr</option>
                                      <option value="5">May</option>
                                      <option value="6">Jun</option>
                                      <option value="7">Jul</option>
                                      <option value="8">Aug</option>
                                      <option value="9">Sep</option>
                                      <option value="10">Oct</option>
                                      <option value="11">Nov</option>
                                      <option value="12">Dec</option>
                                    </select> &nbsp;&nbsp;
                                    <select id="year" name="birthday_year">
                                        <script type="text/javascript">
                                            var e = document.getElementById('year');
                                            var j = 1945;
                                            while (j++ < 2010) {
                                                var s = document.createElement('option');
                                                var e = document.getElementById('year');
                                                if (j == 1946) {
                                                    s.text = "Year";
                                                    s.value = "na";
                                                    try {
                                                        e.add(s, null);
                                                    } catch (ex) {
                                                        e.add(s);
                                                    }
                                                } else {
                                                    s.text = j;
                                                    s.value = j;
                                                    try {
                                                        e.add(s, null);
                                                    } catch (ex) {
                                                        e.add(s);
                                                    }
                                                }
                                            }
                                        </script>
          
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="gender" class="col-md-3 control-label">Gender</label>
                            <div class="col-md-9">
                                <div class="radio-inline" id="radio_button">
                                    <label><input id="male" type="radio" name="gender" value="male">Male</label>
                                    <div class="radio-inline" style="margin-left: 40px;">
                                        <label><input id="female" type="radio" name="gender" value="female">Female</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                       <button type="submit" id="btn-signup" class="btn btn-success"  value="Sign Up" onclick="submitForm();">SUBMIT</button>         
                    </form>
                </div>
            </div>
        </div>
    </div>  
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

</body>

</html>