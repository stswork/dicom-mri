$(document).ready(function() {
    $("form").bind("submit", function(e) {
            $(e.currentTarget).find("div.error").hide();
            e.preventDefault();
            var serialized = $(e.currentTarget).serializeArray();
            var s = '';
            var _data = {};
            for(s in serialized){
                _data[serialized[s]['name']] = serialized[s]['value']
            }
            $.ajax({
                 type: e.currentTarget.method,
                 url: e.currentTarget.action,
                 data: JSON.stringify(_data),
                 success: function(data) {
                    if(data && data.status) {
                        if(data.status == 200) {
                            window.location = $(e.currentTarget).data().uri;
                        } else {
                            $(e.currentTarget).find("div.error p").html(data.message);
                            $(e.currentTarget).find("div.error").show();
                        }
                    }
                 },
                 error: function(xhr, textStatus, errorThrown) {
                        var em = jQuery.parseJSON(xhr.responseText)
                        $(e.currentTarget).find("div.error p").html(em.message);
                        $(e.currentTarget).find("div.error").show();
                 },
                 dataType: "json",
                 contentType: "application/json"
            });
    });
});