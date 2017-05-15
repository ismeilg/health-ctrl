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
    <link href="css/home_page.css" rel="stylesheet">
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
                <h1 class="page-header">Previous Tests:</h1>
            </div>
        </div>
        <div class="row">

            <table class="table table-hover col-sm-10">
                <thead>
                    <tr class="row">
                        <td>Date</td>
                        <td>Weight</td>
                        <td>Test Results</td>
                        <td>Recommendation</td>
                </thead>
                <tbody>
                    <!-- foreach -->
<!--                    in the for loop, should lesharsher the 1.data-id, 2.testEcho, 3.recEcho, 4.recommendation, 5.testResult  from the id column in the DB -->
                    <tr class="row">
                        <td class="col-sm-3">03.04.2017</td>
                        <td class="col-sm-3"> 23</td>
                        <td class="col-sm-3">
                            <button class="btn view-test" data-id="1" data-view="test">View Test</button>
                        </td>
                        <td class="col-sm-3">
                            <button class="btn view-recommendation" data-id="1" data-view="recc">View Recommendation</button>
                        </td>
                    </tr>
                    <tr class='hideTr testResult1'>
                        <td colspan="5" class="text-center testREcho">asf</td>
                    </tr>
                    <tr class="hideTr recommendation1">
                        <td colspan="5" class="text-center recEcho">asfasf</td>
                    </tr>
                    <!-- endforeach -->

                    <tr class="row">
                        <td class="col-sm-3">07.04.2013</td>
                        <td class="col-sm-3">33</td>
                        <td class="col-sm-3">
                            <button class="btn view-test" data-id="2" data-view="test">View Test</button>
                        </td>
                        <td class="col-sm-3">
                            <button class="btn view-recommendation" data-id="2" data-view="recc">View Recommendation</button>
                        </td>
                    </tr>
                    <tr class="hideTr testResult2">
                        <td colspan="5" class="text-center testREcho2"></td>
                    </tr>
                    <tr class="hideTr recommendation2">
                        <td colspan="5" class="text-center recEcho2">asfasf</td>
                    </tr>


                </tbody>
            </table>

            <hr>
            <div class="wrapper">
                <button class="new-test btn btn-success col-sm-3 col-md-3"><a class="new-test" href="blood_test_form.html">New Test</a></button>
            </div>
            <!-- Footer -->
            <footer>
                <div class="row">
                    <div class="col-lg-12">
                        <p style="text-align: center">Copyright &copy; Health Ctrl inc.</p>
                    </div>
                </div>
                <!-- /.row -->
            </footer>

        </div>
    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="js/slider.js"></script>
    <script src="js/main.js"></script>
    <script src="js/script.js"></script>


</body>

</html>