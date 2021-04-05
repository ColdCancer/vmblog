$(function () {
    $.ajax({
        url: '/user/home/getMessageInHome',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            $('#home-name').html(data.name);
            $('#home-sex').html(data.sex);
            $('#home-email').html(data.email);
            // alert(data.email);
        }
    });
});