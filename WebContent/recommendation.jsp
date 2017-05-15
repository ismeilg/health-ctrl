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

    <title>Recommendation</title>

    <!-- Bootstrap Core CSS -->
    <!--    <link href="css/bootstrap.min.css" rel="stylesheet">-->

    <!-- Custom CSS -->
    <link href="css/recommendation.css" rel="stylesheet">

    <link href="https://fonts.googleapis.com/css?family=Rubik|Suez+One" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Nunito" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

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

        <!-- Title -->
        <h1>Your Recommendation</h1>


        <hr>

        <!-- Date/Time -->
        <p><span class="glyphicon glyphicon-time"></span> Posted on August 24, 2013 at 9:00 PM</p>

        <hr>
        <div class="col-md-6 border">
            <!-- Preview Image -->
            <img class="img-responsive" src="images/pexels-photo-113758_Fotor.jpg" alt="">
            <h1>Nutritional Recommendation</h1>
            <hr>
            <div class="row">
                <p class="lead">Based on your last blood test results:</p>
                <div class="col-sm-6">
                    <!-- Post Content -->
                    <p class="lead">You Should Eat:</p>
                    <ul class="eat">
                       <c:forEach var="recom" items="${recommendation}">
                        <li><i class="glyphicon glyphicon-thumbs-up"></i>${recom}</li>
               			</c:forEach>
                    </ul>
                </div>
                <div class="col-sm-6">
                    <!-- Post Content -->
                    <p class="lead">You Should Avoid:</p>
                    <ul class="avoid">
                       <c:forEach var="unrecom" items="${unrecommended}">
                        <li><i class="glyphicon glyphicon-thumbs-down"></i>${unrecom}</li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <hr>
            <div class="row">
                <!-- Post Content -->
                <p class="lead">Nutritional Guidelines:</p>
                <ul class="guidelines">
                    <li><i class="glyphicon glyphicon-adjust"></i>Make half your plate fruits and vegetables.</li>
                    <ul class="plate">
                        <li><i class="glyphicon glyphicon-apple"></i>Focus on whole fruits.</li>
                        <li><i class="glyphicon glyphicon-leaf"></i>Vary your veggies.</li>
                    </ul>
                    <li><i class="glyphicon glyphicon-grain"></i>Make half your grains whole grains.</li>
                    <li><i class="glyphicon glyphicon-piggy-bank"></i>Move to low-fat and fat-free milk or yogurt.</li>
                    <li><i class="glyphicon glyphicon-cutlery"></i>Vary your protein routine.</li>
                    <li><i class="glyphicon glyphicon-ice-lolly"></i>Drink and eat less sodium, saturated fat, and added sugars.</li>
                    <li><i class="glyphicon glyphicon-tint"></i>Drink atleast 10 glasses of water each day.</li>
                    <li><i class="glyphicon glyphicon-scale"></i>Maintain an active routine.</li>
                    <li><i class="glyphicon glyphicon-time"></i>Meals timing:</li>
                    <ul class="plate">
                        <li><i class="glyphicon glyphicon-search"></i>Eat breakfast within 1 hour of rising.</li>
                        <li><i class="glyphicon glyphicon-zoom-in"></i>Eat every 3 hours after that.</li>
                        <li><i class="glyphicon glyphicon-zoom-out"></i>Stop eating 3 hours before bedtime.</li>
                    </ul>
                </ul>
            </div>



        </div>

        <!-- Blog Sidebar Widgets Column -->
        <div class="col-md-6">
            <!-- Preview Image -->
            <img class="img-responsive" src="images/pexels-photo-305239_Fotor.jpg" alt="">
            <h1>Fitness Recommendation</h1>
            <hr>

            <div class="row" style="padding-left: 15px; box-sizing: border-box;">
                <p class="lead">Based on your eating preferences, we recommend you to eat before and after exercising as following:</p>
                <table class="table col-sm-12">
                    <thead>
                        <tr>
                            <td></td>
                            <td>Eat</td>
                            <td>Avoid</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="should-eat">
                            <td class="head">Before Exercise</td>
                            <td id="before">
                                <ul class="eat">
                                      <c:forEach var="sportBefore" items="${sportBefore}">
                       				 <li><i class="glyphicon glyphicon-thumbs-up"></i>${sportBefore}</li>
               						</c:forEach>
                                </ul>
                            </td>
                            <td id="before">
                                <ul class="avoid">
                            		  <c:forEach var="unrecom" items="${notBeforeSport}">
                       					<li><i class="glyphicon glyphicon-thumbs-down"></i>${unrecom}</li>
                       				 </c:forEach>
                                </ul>
                            </td>
                        </tr>
                        <tr class="should-avoid">
                            <td class="head">After Exercise</td>
                            <td id="after">
                                <ul class="eat">
                                    <c:forEach var="recom" items="${foodAfterSportrecommendation}">
                       					<li><i class="glyphicon glyphicon-ok"></i>${recom}</li>
                       				 </c:forEach>
                                </ul>
                            </td>
                            <td id="after">
                                <ul class="avoid">
                                     <c:forEach var="unrecom" items="${unrecommendedAfterSport}">
                       					<li><i class="glyphicon glyphicon-remove"></i>${unrecom}</li>
                       				 </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="row"  style="padding-left: 15px; box-sizing: border-box;">

                <hr>
                <p class="lead">According to the bmi and age your target heart rate  is: ${heartRrate}</p>
              <p>In this range of heart rate you will ${sportRecommendation} </p>
                <hr>
            </div>
            <div  style="padding-left: 15px; box-sizing: border-box;">
                <h2>Fitness Videos</h2>
                <p class="lead">Enjoy working out in the most effective way with these demos. Just choose the workout and start moving:</p>

                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#cardio">Cardio</a></li>
                    <li><a data-toggle="tab" href="#back">Back</a></li>
                    <li><a data-toggle="tab" href="#abs">Abs</a></li>
                    <li><a data-toggle="tab" href="#hands">Hands</a></li>
                    <li><a data-toggle="tab" href="#legs">Legs</a></li>
                    <li><a data-toggle="tab" href="#full">Full Body</a></li>
                </ul>

                <div class="tab-content">
                    <div id="cardio" class="tab-pane fade in active">
                        <div id="tabs-6" class="embed-responsive embed-responsive-16by9">
                            <iframe class="embed-responsive-item" width="100%" height="345" allowfullscreen="" src="https://www.youtube.com/embed/7f9XpgN9f6k">
</iframe>
                        </div>
                    </div>
                    <div id="back" class="tab-pane fade">
                        <div id="tabs-5" class="embed-responsive embed-responsive-16by9">
                            <iframe class="embed-responsive-item" width="100%" height="345" allowfullscreen="" src="https://www.youtube.com/embed/dxUWgnWrOno">
</iframe>
                        </div>
                    </div>
                    <div id="abs" class="tab-pane fade">
                        <div id="tabs-6" class="embed-responsive embed-responsive-16by9">
                            <iframe class="embed-responsive-item" width="100%" height="345" allowfullscreen="" src="https://www.youtube.com/embed/LebPal5gKrc">
</iframe>
                        </div>
                    </div>
                    <div id="hands" class="tab-pane fade">
                        <div id="tabs-6" class="embed-responsive embed-responsive-16by9">
                            <iframe class="embed-responsive-item" width="100%" height="345" allowfullscreen="" src="https://www.youtube.com/embed/dzWc0JTsPhg">
</iframe>
                        </div>
                    </div>
                    <div id="legs" class="tab-pane fade">
                        <div id="tabs-6" class="embed-responsive embed-responsive-16by9">
                            <iframe class="embed-responsive-item" width="100%" height="345" allowfullscreen="" src="https://www.youtube.com/embed/Womx4TM6p3A">
</iframe>
                        </div>
                    </div>
                    <div id="full" class="tab-pane fade">
                        <div id="tabs-6" class="embed-responsive embed-responsive-16by9">
                            <iframe class="embed-responsive-item" width="100%" height="345" allowfullscreen="" src="https://www.youtube.com/embed/7f9XpgN9f6k">
</iframe>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <hr>
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

</body>

</html>