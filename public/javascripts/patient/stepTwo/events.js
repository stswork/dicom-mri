$(document).ready(function () {
    $( "#progressbar" ).progressbar({
          value: 85
        });
    /*$('#dicomToJpegFC').change( function(){
        var _progVal = $(this).val();
        $(this).progressbar({
              value: _progVal
        });
    });*/

    //Adding progress event listener to ajax settings
    (function addXhrProgressEvent($) {
        var originalXhr = $.ajaxSettings.xhr;
        //oReq.addEventListener("progress", updateProgress, false);
        $.ajaxSetup({
            progress: function() { console.log("standard progress callback"); },
            progressUpload: function() { console.log("standard upload progress callback"); },
            xhr: function() {
                var req = originalXhr(), that = this;
                if (req.upload) {
                    if (typeof req.upload.addEventListener == "function") {
                        req.addEventListener("progress", function(evt) {
                            that.progressUpload(evt);
                        },false);
                    }
                }
                return req;
            }
        });
    })(jQuery);

    $("#patientForm").live("submit", function(e) {
        $("#loader-a").attr("style","display:inline;");
        e.preventDefault();
        var _data = new FormData($(this)[0]);
        var _formData = $(this).serialize();

        /*function updateProgress (oEvent) {
          if (oEvent.lengthComputable) {
            var percentComplete = oEvent.loaded / oEvent.total;
            // ...
          } else {
            // Unable to compute progress information since the total size is unknown
          }
        }*/
        $(e.currentTarget).find("div.message p").html("Uploading...");
        $(e.currentTarget).find("div.message").show();
        $.ajax({
            progress: function(evt) {
                console.log("INSIDE PROGRESS");
                if (evt.lengthComputable) {
                    console.log("Loaded " + parseInt( (evt.loaded / evt.total * 100), 10) + "%");
                }
                else {
                    console.log("Length not computable.");
                }
            },
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