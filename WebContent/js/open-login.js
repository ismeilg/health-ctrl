$(document).ready(function () {

    var frameSrc = "/login.html";

    $('#openBtn').click(function () {
        $('#myModal').on('show', function () {

            $('iframe').attr("src", frameSrc);

        });
        $('#myModal').modal({
            show: true
        })
    });
});