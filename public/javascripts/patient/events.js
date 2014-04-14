$(document).ready(function () {
    $("#addProductForm").submit(function (event) {

        //disable the default form submission
        event.preventDefault();
        //grab all form data
        var formData = $(this).serialize();

        $.ajax({
            url: 'addProduct.php',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function () {
                alert('Form Submitted!');
            },
            error: function(){
                alert("error in ajax form submission");
            }
        });

        return false;
    });
});