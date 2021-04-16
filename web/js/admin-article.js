$(function () {
    // alert(window.location.href + '/getArticleList');
    String.format = function() {
        var s = arguments[0];
        for (var i = 0; i < arguments.length - 1; i++) {
            var reg = new RegExp("\\{" + i + "\\}", "gm");
            s = s.replace(reg, arguments[i + 1]);
        }
        return s;
    }
    /*using ajax come to get article.html content message*/
    $.ajax({
        /*get json message by url*/
        url: window.location.href + '/getArticleList',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            $('#article-count').html(data.total);
            $('#article-last').html(data.reccent);
            for (var i = 0; i < data.article.length; i++) {
                var title = data.article[i].title;
                var time = data.article[i].time;
                var src = data.article[i].src;
                var html = "<tr>\n<th scope=\"row\">{0}</th>\n<td>{1}</td>\n<td>{2}</td>\n" +
                    "<td><a class=\"btn btn-warning\" href=\"{3}/updata\">更改</a></td>\n" +
                    "<td><a class=\"btn btn-danger\" href=\"{3}/delete\">删除</a></td>\n</tr>";
                html = String.format(html, i + 1, title, time, src);
                $('#article-list').append(html);
            }
        }
    });
});

