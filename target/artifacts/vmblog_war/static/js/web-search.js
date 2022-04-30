var current_page = 1, update_flag = 0;

$(function () {
    var seg_url = window.location.href.split('/');
    var len = 0;
    while (seg_url[len] !== 'so') len++;
    len++;
    if (seg_url[len] === 'category') {
        var account  = seg_url[len + 1];
        var typename = seg_url[len + 2];
        $('#search-title').text("Search: Category");
        requestArticleByCategory(account, typename);
    } else {
        console.log("search");
    }
})

function requestArticleByCategory(account, typename) {
    update_flag = 0;
    $.ajax({
        url: '/api/article/byCategory',
        data: {
            'account': account,
            'typename': typename,
            'pageNum': current_page
        },
        type: 'get',
        dataType: 'json',
        success: function (content) {
            if (content['code'] !== 0) return;
            // console.log(content);
            var data = content['data'];
            $article_list_elem = $('#article-list');
            $article_list_elem.html('');
            for (var index in data) {
                var article = data[index];
                var article_elem = new ArticleCart(article.title,
                    article.segmental, article.cover, article.post,
                    article.blogger, article.views, article.like,
                    article.dislike, article.link, article.category,
                    article.account).convert();
                if (article.category === null) {
                    var cate_elem = $(article_elem).find('#category-elem');
                    // console.log(cate_elem);
                    cate_elem.remove();
                    // article_elem = article_elem.removeChild(cate_elem);
                }
                if (article.cover === '#') {
                    $(article_elem).find("img")[0].remove();
                }
                $article_list_elem.append(article_elem);
            }
            window.scrollTo(0, 0);
            $('#page-num').text('page: ' + current_page);
            update_flag = 1;
        }
    });

}