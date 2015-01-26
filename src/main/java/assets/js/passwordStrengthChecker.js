"use strict";

function passwordStrengthChecker () {
    var hideBars = function() {
        $("#passWeak").hide();
        $("#passMedium").hide();
        $("#passStrong").hide();
    }

    hideBars();
    $("#passwordInput").keyup(function() {
        var points = 0;
        var pass = $("#passwordInput").val();

        if (pass.length > 0) {
            points = Math.ceil(pass.length / 8);
        }
        if (/[0-9]/.test(pass)) {
            points++;
        }
        if (/[A-Z]/.test(pass)) {
            points++;
        }
        if (/[^0-9a-zA-Z]/.test(pass)) {
            points++;
        }

        hideBars();
        switch (points) {
            case 0: return;
            case 1:
            case 2:
            case 3:
            case 4: $("#passWeak").show(); return;
            case 5:
            case 6: $("#passMedium").show(); return;
            default: $("#passStrong").show(); return;
        }
    });
}
passwordStrengthChecker();
