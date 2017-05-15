function validateFom() {
    var emailRegex = /^[A-Za-z0-9._]*\@[A-Za-z]*\.[A-Za-z]{2,5}$/;
    var fname = document.getElementById("fname").value;
        lname = document.getElementById("lname").value;
        femail = document.getElementById("email").value;
        fpassword = document.getElementById("passwd").value;
        month = document.getElementById("month").value;
        day = document.getElementById("day").value;
        year = document.getElementById("year").value;


    if (femail == "") {
    	document.getElementById("email").focus();
        document.getElementById("errorBox").innerHTML = "enter the email";
        console.log("Please Select Connection Type.");
        return false;
    } else if (!emailRegex.test(femail)) {
    	document.getElementById("email").focus();
        document.getElementById("errorBox").innerHTML = "enter the valid email";
        return false;
    }

    if (fname == "") {
    	document.getElementById("fname").focus();
        document.getElementById("errorBox").innerHTML = "enter the first name";
        return false;
    }
    if (lname == "") {
    	document.getElementById("lname").focus();
        document.getElementById("errorBox").innerHTML = "enter the last name";
        return false;
    }

    if (fpassword == "") {
    	document.getElementById("passwd").focus();
        document.getElementById("errorBox").innerHTML = "enter the password";
        return false;
    }
    if (day == "na") {
    	document.getElementById("day").focus();
        document.getElementById("errorBox").innerHTML = "enter birthday day";
        return false;
    }
    if (month == "") {
    	document.getElementById("month").focus();
        document.getElementById("errorBox").innerHTML = "enter birthday month";
        return false;
    }
    if (year == "na") {
    	document.getElementById("year").focus();
        document.getElementById("errorBox").innerHTML = "enter birthday year";
        return false;
    }
    if (document.getElementById("male").checked == false && document.getElementById("female").checked == false) {
        document.getElementById("errorBox").innerHTML = "select your gender";
        return false;
    }
    if (fname != '' && lname != '' && femail != '' && fpassword != '' && fmonth != '' && fday != '' && fyear != '') {
//        var toServer = myJSONObject.toJSONString();
//        var request = new XMLHttpRequest();
//        var stringParameter = "command";
//        request.open("POST", "http://localhost:8080/sadna-healthCtrl/src/com/healthctrl/servlets?stringParameter=" + stringParameter, true);
//    	document.getElementById("signupform").submit();
//    	if (xhr.send(null)) {
//            window.location.href= "/blood_test_form.html";
//        } else {
//            document.getElementById("errorBox").innerHTML = "sorry.. bah bye";
//        }
      document.getElementById("signupform").submit();
    	return true;

    }
}