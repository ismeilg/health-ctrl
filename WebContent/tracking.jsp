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

    <title>Home</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/tracking_page.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Rubik|Suez+One" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Nunito" rel="stylesheet">

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

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
                <h1 class="page-header">Previous Tests</h1>
            </div>
        </div>
        <div class="row">

            <table class="table table-striped col-sm-10">
                <thead>
                <tr>
                		<td>Date</td>
                    	<c:forEach var="lab" items="${comp_labels}">
                  		  <td>${lab}</td>
               			 </c:forEach>
                </thead>
                <tbody>
                        
                     	<c:forEach var="tempResults" items="${allResults}" varStatus="status">
                     	 <tr class="results">
            			 <c:forEach var = "temp" items="${tempResults}">
            			 <c:choose>
  							<c:when test="${temp.name eq 'date'}">
							  <td id="date">${temp.date}</td>
							  </c:when>
							  <c:otherwise>
							  <td>${temp.value}</td>
							  </c:otherwise>		  
 						 </c:choose> 		
                          </c:forEach>
                           <td></td>
                            
                         </tr> 
            
                             <tr class="delta">
                             <td></td>
            			 <c:forEach var = "tempDe" items="${deltaResults[status.index]}">
                        	<td>${tempDe}</td>
                          </c:forEach>
                          </tr>
                   		 </c:forEach>
           
                </tbody>
            </table>
        </div>

        <hr>

        <div class="row">
            <div class="bmi">
                <p class="your-bmi">Your Last BMI:</p>
                <aside class="bmi-res">${bmiWeight[0]}</aside>

                <p class="your-bmi">Your Last Weight:</p>
                <aside class="bmi-res">${bmiWeight[1]}</aside>
            </div>
        </div>
        <div class="row">
        	 <form id="tracking" name="tracking" action="TrackingServlet" method="post">
    			<input type="hidden" name="command" value="recommendation">
				<input type="submit" value="View Last Recommendation" class="btn btn-success new-test">
			</form>
            <a class="btn btn-success new-test" href="blood_test_form.jsp">Take New Test</a>
        </div>

        <div class="container col-sm-12">

            <hr>

            <!-- Footer -->
            <footer>
                <div class="row">
                    <div class="col-lg-12">
                        <p style="text-align: center;">Copyright &copy; Health Ctrl</p>
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