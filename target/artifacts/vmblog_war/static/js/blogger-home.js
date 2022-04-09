$(function () {
    /*print blogger's name in html head*/
    var blogger = window.location.pathname.split('/')[1];
    $('#blogger-name').html(blogger);

    /*using ajax come to get home.html content message*/
    $.ajax({
        /*get json message by url*/
        url: window.location.href + '/getMessageInHome',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            // this is Self Information
            $('#home-name').html(data.name);
            $('#home-birthplace').html(data.birthplace);
            $('#home-email').html(data.email);
            $('#home-sex').html(data.sex);
            $('#home-education').html(data.education);
            $('#home-position').html(data.position);

            /*this is What About Talk*/
            $('#home-talk').html(data.talk);

            /*this is About Self Web*/

            // alert(window.location.href);
        }
    });
});