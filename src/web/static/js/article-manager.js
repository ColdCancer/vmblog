var currentPage = 1;
var optState = 0;

$(function () {

    $('#article-operation').click(function () {
        if (optState === 0) {
            $('.option-item').removeClass("d-none");
            $('.edit-item').addClass("d-none");
            // $(this).find("i")[0].text('Cancel');
            // console.log($(this).html())
            $(this).html($(this).html().replace("Operation", "Cancel"));
            $('#article-delete').removeClass("disabled");
            $('#article-classify').removeClass("disabled");
        } else {
            $('.option-item').addClass("d-none");
            $('.edit-item').removeClass("d-none");
            $('#article-delete').addClass("disabled");
            $('#article-classify').addClass("disabled");
            $(this).html($(this).html().replace("Cancel", "Operation"));
        }
        optState = 1 - optState;
    });

    getArticleItemsByPageNum(currentPage);
    $('#article-page-previous').click(function () {
        getArticleItemsByPageNum(currentPage - 1);
        currentPage -= 1;
    });
    $('#article-page-next').click(function () {
        getArticleItemsByPageNum(currentPage + 1);
        currentPage += 1;
    });

    $('#article-delete').click(function () {
        var opt_list = $('.opt-items');
        // console.log(opt_list);
        var opt_links = [];
        for (var i = 0; i < opt_list.length; i++) {
            var opt = opt_list[i];
            if (opt.checked === false) continue;
            opt_links.push(opt.getAttribute('value'));
        }
        // console.log(opt_links);
        if (opt_list.length === 0) return;
        $.ajax({
            url: '/web/api/article/delete/list',
            type: 'post',
            data: { 'deleteList': opt_links },
            traditional: true,
            dataType: 'json',
            success: function (content) {
                // console.log(content);
                var data = content['data'];
                for (var item_index in data) {
                    var item = data[item_index];
                    if (item['delete'] !== 1) continue;
                    console.log(item['link']);
                    $(String.format("input[value='{0}']", item['link']))[0].
                        parentElement.parentElement.parentElement.remove();
                }
            }
        });
    });
});


function getArticleItemsByPageNum(pageNum) {
    $.ajax({
        url: '/web/api/article/items/page/' + pageNum,
        type: 'get',
        dataType: 'json',
        success: function (content) {
            if (content['code'] !== 0) return;
            var article_json = content['data'];
            $article_list_elem = $('#article-manager-body');
            $article_list_elem.html('');
            for (var article_index in article_json) {
                var article = article_json[article_index];
                var article_elem = new ArticleItem(article.id, article.title,
                    article.postDate, article.updateDate, article.link).convert();
                $article_list_elem.append(article_elem);
            }
            window.scrollTo(0, 0);
        }
    });
}
