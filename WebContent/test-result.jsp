<%@ page import="java.util.*,com.healthctrl.*" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Blood test result</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/test_results.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Rubik|Suez+One" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Nunito" rel="stylesheet">

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
	
	  <link type="jsp" rel="stylesheet" href="WebContent.test_results.jsp"> 
</head>

<body>
    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Start Bootstrap</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="#">About</a>
                    </li>
                    <li>
                        <a href="#">Services</a>
                    </li>
                    <li>
                        <a href="#">Contact</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container">
        <div class="row">

            <div class="col-lg-12">
                <h1 class="page-header">Test Results:</h1>
            </div>

            <div class="row">

                <!--for loop-->
               <c:forEach var="tempResults" items="${results}">
                <div class="prog_div col-md-4 col-sm-6 col-xs-12">
                    <!-- progress-bar-->
                    <div class="test_result">
                        <span class="test_name">${tempResults.type}</span>
                        <!--test name-->
                        <div class="progress_bar">
                            <div class="line min">
                                <span class="line_value">${tempResults.min}</span>
                                <!--min value-->
                            </div>
                            <div class="line max">
                                <span class="line_value">${tempResults.max}</span>
                                <!--max value-->
                            </div>
                            <!--  (50 * value / (max - min))px  -->
                            <div class="line val danger" style="transform: translateX(${tempResults.avalue}px);">
                                <!--result value-->
                                <span class="line_value">${tempResults.value}</span>
                            </div>
                        </div>
                    </div>
                </div>
                </c:forEach>
                </div>
                <!--end-for-loop-->
           <hr>

            <!--            BMI-->
            <div class="row">
                <div class="bmi">

                    <p class="your-bmi">Your BMI is:</p>
                    <aside class="bmi-res">${bmi}</aside>
                    <!--  from the DB/CODE  -->
                    <div class="block">
                        <div class="col-sm-8 col-md-8">
                            <img class="bmi-img" src="${bmi_img}">
                            <!--  from the DB  -->
                        </div>
                        <div class="col-sm-3 col-md-3">
                            <img class="bmi-img" src="${image_bmi}">
                            <!--  from the DB  -->
                        </div>
                    </div>
                </div>
				<form action="BloodTestServlet" method="post">
					 <input type="hidden" name="command" value="recommendation">
					 <input type="submit" value="Continue to Your Recommendation"  class="sub-btn btn">
                </form>
            </div>
            <hr>
            <!-- Footer -->
            <footer>
                <div class="row">
                    <div class="col-lg-12">
                        <p>Copyright &copy; Health Ctrl</p>
                    </div>
                </div>
            </footer>

        </div>
    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

</body>

</html>