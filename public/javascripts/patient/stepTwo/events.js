$(document).ready(function () {
    $("#patientForm").live("submit", function(e) {
        $("#loader-a").attr("style","display:inline;");
        e.preventDefault();
        var _data = new FormData($(this)[0]);
        var _formData = $(this).serialize();
        $.ajax({
            type: e.currentTarget.method,
            url: e.currentTarget.action,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            dataType: "json",
            data: _data,
            success: function(data) {
                $(e.currentTarget).find("div.message p").html(data.message);
                $("#loader-a").attr("style","display:none;");
                $(e.currentTarget).find("div.message").show();
                $(e.currentTarget).get(0).reset();
                setTimeout(function () {
                    $(e.currentTarget).find("div.message").hide();
                    window.location = $(e.currentTarget).data().uri;
                }, 3000);
            },
            error: function(xhr, textStatus, errorThrown) {
                var em = jQuery.parseJSON(xhr.responseText)
                $(e.currentTarget).find("div.message p").html(em.message);
                $("#loader-a").attr("style","display:none;");
                $(e.currentTarget).find("div.message").show();
                $(e.currentTarget).get(0).reset();
                setTimeout(function () {
                    $(e.currentTarget).find("div.message").hide();
                }, 5000);
            }
        });
        return false;
    });
});