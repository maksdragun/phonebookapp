$( document ).ready(function() {
	
	$(".signUp").click(function() {
		var username = $("#username").val();
		var password = $("#password").val();
		var fullName = $("#fullName").val();
		var answer = $(".form-answer");

		if(username !== null || password !== null || fullName !== null) {
            $("#form").submit();
		} else {
            answer.text("You did not fill out the required fields!");
		}
	});
	
	$(".addNewOrEditContact").click(function() {
		var surname = $("#surname").val();
		var firstName = $("#firstName").val();
		var patronymic = $("#patronymic").val();
		var mobilePhone = $("#mobilePhone").val();
		
		var answer = $(".form-answer");
		
		if(surname !== null || firstName !== null || patronymic !== null || mobilePhone !== null) {
            $("#form").submit();
		} else {
            answer.text("Fields marked with an * are required");
		}
	});

    (function ($) {
        $('.filter').keyup(function () {
            var rex = new RegExp($(this).val(), 'i');
            $('.for-search').closest('tr').hide();
            $('.for-search').filter(function () {
            	rex.test($(this).text()).css("background-color", "yellow");
                return rex.test($(this).text());
            }).closest('tr').show();
        })
    }(jQuery));

});

function removeContact(id) {
    var contactId = id;
	$("#"+ contactId + "").closest("tr").remove();
    $.post(
    	"/phoneBookApp/delete",
    	{
            contactId: contactId
    	},
    	function success(data) {
    	}
    );
}

