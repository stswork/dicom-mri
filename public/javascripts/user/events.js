$(document).ready(function () {
    $("#userForm").live("submit", function(e) {
        $("#loader-a").attr("style","display:inline;");
        e.preventDefault();
        var _data = $(e.currentTarget).serialize();
        alert(_data);
        console.log(_data);
        $.ajax({
            type: e.currentTarget.method,
            url: e.currentTarget.action,
            dataType: "json",
            data: _data,
            success: function(data) {
                $(e.currentTarget).find("div.message p").html(data.message);
                $("#loader-a").attr("style","display:none;");
                $(e.currentTarget).find("div.message").show();
                setTimeout(function () {
                    $(e.currentTarget).find("div.message").hide();
                }, 5000);
            },
            error: function(xhr, textStatus, errorThrown) {
                var em = jQuery.parseJSON(xhr.responseText)
                $(e.currentTarget).find("div.message p").html(em.message);
                $("#loader-a").attr("style","display:none;");
                $(e.currentTarget).find("div.message").show();
                setTimeout(function () {
                    $(e.currentTarget).find("div.message").hide();
                }, 5000);
            }
        });
        return false;
    });
});