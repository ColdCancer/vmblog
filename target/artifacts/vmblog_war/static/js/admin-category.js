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
        url: window.location.href + '/getCategoryList',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            $('#category-count').html(data.total);
            $('#category-link-count').html(data.linkCount);
            for (var i = 0; i < data.categories.length; i++) {
                var name = data.categories[i].name;
                var count = data.categories[i].count;
                var html = "<tr>\n<th scope=\"row\">{0}</th>\n<td>{1}</td>\n<td>{2}</td>\n" +
                    "<td><a class=\"btn btn-warning\" href=\"{1}/updata\">更改</a></td>\n" +
                    "<td><a class=\"btn btn-danger\" href=\"{1}/delete\">删除</a></td>\n</tr>";
                html = String.format(html, i + 1, name, count);
                $('#category-list').append(html);
            }
        }
    });

    $('#add-category').click(function () {
        // alert($('#tag-name').val());
        $.ajax({
            /*get json message by url*/
            url: window.location.href + '/addCategory',
            data: {'categoryName': $('#category-name').val()},
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.result) {
                    alert("添加类别成功！");
                    window.location.reload();
                } else {
                    alert("添加类别失败，检查是否已存在！");
                }
            }
        });
    });

});

