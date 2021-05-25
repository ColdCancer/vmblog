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
    /*using ajax come to get tag.html content message*/
    $.ajax({
        /*get json message by url*/
        url: window.location.href + '/getTagList',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            $('#tag-count').html(data.total);
            $('#tag-link-count').html(data.linkCount);
            for (var i = 0; i < data.tags.length; i++) {
                var name = data.tags[i].name;
                var count = data.tags[i].count;
                var html = "<tr>\n<th scope=\"row\">{0}</th>\n<td>{1}</td>\n<td>{2}</td>\n" +
                    "<td><a class=\"btn btn-warning\" href=\"{1}/updata\">更改</a></td>\n" +
                    "<td><a class=\"btn btn-danger\" href=\"{1}/delete\">删除</a></td>\n</tr>";
                html = String.format(html, i + 1, name, count);
                $('#tag-list').append(html);
            }
        }
    });

    $('#add-tag').click(function () {
        // alert($('#tag-name').val());
        $.ajax({
            /*get json message by url*/
            url: window.location.href + '/addTag',
            data: {'tag-name': $('#tag-name').val()},
            type: 'post',
            dataType: 'json',
            success: function (data) {
            }
        });
    });

});

