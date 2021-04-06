$(function () {
    /*print blogger's name in html head*/
    var blogger = window.location.pathname.split('/')[1];
    $('#blogger-name').html(blogger);

    /*using ajax come to get article.html content message*/
    $.ajax({
        /*get json message by url*/
        url: window.location.href + '/getMessageInArticle',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            alert("debug");
        }
    });
});