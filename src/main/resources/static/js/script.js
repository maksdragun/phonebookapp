$(document).ready(function () {

    var loginPage = $("#content-login");
    var regPage = $("#content-registration");

    // registration page
    $("#showregistration").on("click", function (e) {
        e.preventDefault();
        var newheight = regPage.height();
        $(regPage).css("display", "block");

        $(loginPage).stop().animate({
            "left": "-880px"
        }, 800, function () { /* callback */
        });

        $(regPage).stop().animate({
            "left": "0px"
        }, 800, function () {
            $(loginPage).css("display", "none");
        });

        $("#page").stop().animate({
            "height": newheight + "px"
        }, 550, function () { /* callback */
        });
    });

    // login page
    $("#showlogin").on("click", function (e) {
        e.preventDefault();
        var newheight = loginPage.height();
        $(loginPage).css("display", "block");
        $(loginPage).stop().animate({
            "left": "0px"
        }, 800, function () { /* callback */
        });
        $(regPage).stop().animate({
            "left": "880px"
        }, 800, function () {
            $(regPage).css("display", "none");
        });

        $("#page").stop().animate({
            "height": newheight + "px"
        }, 550, function () { /* callback */
        });
    });

    $(".registration").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();
        var fullName = $("#fullName").val();
        var answer = $(".form-answer");

        if (username !== null || password !== null || fullName !== null) {
            $("#form").submit();
        } else {
            answer.text("You did not fill out the required fields!");
        }
    });

    $(".addNewOrEditContact").click(function () {
        var surname = $("#surname").val();
        var firstName = $("#firstName").val();
        var patronymic = $("#patronymic").val();
        var mobilePhone = $("#mobilePhone").val();

        var answer = $(".form-answer");

        if (surname !== null || firstName !== null || patronymic !== null || mobilePhone !== null) {
            $("#form").submit();
        } else {
            answer.text("Fields marked with an * are required");
        }
    });
});

function removeContact(id) {
    var contactId = id;
    $("#" + contactId + "").closest("tr").remove();
    $.post(
        "/phoneBookApp/delete",
        {
            contactId: contactId
        },
        function success(data) {
            console.log("contact with id " + data + " - removed");
        }
    );
}

function search() {
    var input, filter, table, tr, td, td1, td2, td3, td4, td5, td6, td7, i,
        cellContent, cellContent1, cellContent2, cellContent3, cellContent4,
        cellContent5, cellContent6, cellContent7;
    input = document.getElementById("finder");
    filter = input.value.toLowerCase();
    table = document.getElementById("contactTable");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        td1 = tr[i].getElementsByTagName("td")[1];
        td2 = tr[i].getElementsByTagName("td")[2];
        td3 = tr[i].getElementsByTagName("td")[3];
        td4 = tr[i].getElementsByTagName("td")[4];
        td5 = tr[i].getElementsByTagName("td")[5];
        td6 = tr[i].getElementsByTagName("td")[6];
        td7 = tr[i].getElementsByTagName("td")[7];
        if (td) {
            cellContent = td.innerHTML.toLowerCase().replace(/-/g, "");
            cellContent1 = td1.innerHTML.toLowerCase().replace(/-/g, "");
            cellContent2 = td2.innerHTML.toLowerCase().replace(/-/g, "");
            cellContent3 = td3.innerHTML.toLowerCase().replace(/-/g, "");
            cellContent4 = td4.innerHTML.toLowerCase().replace(/-/g, "");
            cellContent5 = td5.innerHTML.toLowerCase().replace(/-/g, "");
            cellContent6 = td6.innerHTML.toLowerCase().replace(/-/g, "");
            cellContent7 = td7.innerHTML.toLowerCase().replace(/-/g, "");

            if (cellContent.indexOf(filter) > -1
                || cellContent1.indexOf(filter) > -1
                || cellContent2.indexOf(filter) > -1
                || cellContent3.indexOf(filter) > -1
                || cellContent4.indexOf(filter) > -1
                || cellContent5.indexOf(filter) > -1
                || cellContent6.indexOf(filter) > -1
                || cellContent7.indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }

}
