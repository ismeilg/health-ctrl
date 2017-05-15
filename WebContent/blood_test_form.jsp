<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Blood Test Form</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/blood_test_form.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Rubik|Suez+One" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Nunito" rel="stylesheet">
    <link type="jsp" rel="stylesheet" href="WebContent.blood_test_form.jsp"> 

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
                <a class="navbar-brand" href="index.html">Start Bootstrap</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="about.html">About</a>
                    </li>
                    <li>
                        <a href="services.html">Services</a>
                    </li>
                    <li class="active">
                        <a href="contact.html">Contact</a>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Portfolio <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="portfolio-1-col.html">1 Column Portfolio</a>
                            </li>
                            <li>
                                <a href="portfolio-2-col.html">2 Column Portfolio</a>
                            </li>
                            <li>
                                <a href="portfolio-3-col.html">3 Column Portfolio</a>
                            </li>
                            <li>
                                <a href="portfolio-4-col.html">4 Column Portfolio</a>
                            </li>
                            <li>
                                <a href="portfolio-item.html">Single Portfolio Item</a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Blog <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="blog-home-1.html">Blog Home 1</a>
                            </li>
                            <li>
                                <a href="blog-home-2.html">Blog Home 2</a>
                            </li>
                            <li>
                                <a href="blog-post.html">Blog Post</a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Other Pages <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="full-width.html">Full Width Page</a>
                            </li>
                            <li>
                                <a href="sidebar.html">Sidebar Page</a>
                            </li>
                            <li>
                                <a href="faq.html">FAQ</a>
                            </li>
                            <li>
                                <a href="404.html">404</a>
                            </li>
                            <li>
                                <a href="pricing.html">Pricing Table</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container">

        <!-- Page Heading/Breadcrumbs -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Blood Test &amp; Prefrences</h1>
                <ol class="breadcrumb">
                    <li><a href="index.html">Home</a>
                    </li>
                    <li class="active">analysis</li>
                </ol>
            </div>
        </div>
        <!-- /.row -->



        <form class="form-horizontal" action="BloodTestServlet" method="post">
        <input type="hidden" name="command" value="ADD"/>
            <div class="container col-sm-6">
                <h2>Fill In Your Blood Test Results</h2>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="albumin">Albumin:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="albumin" name="albumin">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="glucose">Glucose:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="glucose" name="glucose">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="potassium">Potassium:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="potassium" name="potassium">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="calcium">Calcium:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="calcium" name="calcium">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="Triglycerides">Triglycerides:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="Triglycerides" name="triglycerides">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="hdl">HDL:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="hdl" name="hdl">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="ldl">LDL:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="ldl" name="ldl">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="cholestrol">Cholestrol:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="cholestrol" name="cholestrol">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="sodium">Sodium:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="sodium" name="sodium">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="b12">B12:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="b12" name="b12">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="protein_total">Protein_total:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="protein_total" name="protein_total">
                    </div>
                </div>
            </div>
            <div class="container col-sm-6">
                <div>
                    <h2>Fill In Your Physical Data </h2>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="weight">Weight:</label>
                        <div class="col-sm-7">
                            <input type="number" class="form-control" name="weight" max="300" placeholder="weight in kg">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="height">Height:</label>
                        <div class="col-sm-7">
                            <input type="number" class="form-control" name="height" max="210" placeholder="height in cm">
                        </div>
                    </div>
                    <div style="margin-top: 55px;">
                        <h2>Fill In Your Prefernces</h2>
                        <div class="form-group">
                            <label class="control-label push-right" for="sensitive">Are You Sensitive To:</label>
                            <div class="col-sm-10">
                                <div class="checkbox">
                                    <label class="check-box"><input type="checkbox" name="sensitive" value="sugar">Sugar</label>
                                    <label class="check-box"><input type="checkbox" name="sensitive" value="lactose">Lactose</label>
                                    <label class="check-box"><input type="checkbox" name="sensitive" value="gluten">Gluten</label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label push-right" for="veg">Are You A:</label>
                            <div class="col-sm-12">
                                <div class="checkbox">
                                    <label class="check-box"><input type="checkbox" name="prefer" value="vegiterian">Vegiterian</label>
                                    <label class="check-box"><input type="checkbox"  name="prefer" value="vegan">Vegan</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <img src="images/144.jpg" class="btm-img">
            </div>
            <div class="form-group">
                <div class="col-sm-12">
                    <button type="submit" class="btn btn-default sub-btn">Submit</button>
                </div>
            </div>

        </form>
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
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Contact Form JavaScript -->
    <!-- Do not edit these files! In order to set the email address and subject line for the contact form go to the bin/contact_me.php file. -->
    <script src="js/jqBootstrapValidation.js"></script>

</body>

</html>