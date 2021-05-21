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
            // alert("debug");
            for (var index in data) {
                var html = "<div class=\"card mb-4\"><div class=\"card-body\">";
                html += "<h2 class=\"card-title\">" + data[index]['article-title'] + "</h2>";
                html += "<p class=\"card-text\">" + data[index]['article-content'] + "</p>";
                html += "<a href=\"#\" class=\"btn btn-primary float-right\">Read More &rarr;</a>";
                html += "</div><div class=\"card-footer text-muted\">";
                html += "Post On " + data[index]['article-release'] + " by ";
                html += "<a href=\"#\">" + blogger + "</a></div></div>";
                $('#post-article').append(html);
            }
        }
    });
});