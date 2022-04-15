var currentPage = 1;

$(function () {
    getArticleItemsByPageNum(currentPage);
    $('#article-page-previous').click(function () {
        getArticleItemsByPageNum(currentPage - 1);
        currentPage -= 1;
    });
    $('#article-page-next').click(function () {
        getArticleItemsByPageNum(currentPage + 1);
        currentPage += 1;
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
                    article.postDate, article.updateDate).convert();
                $article_list_elem.append(article_elem);
            }
            window.scrollTo(0, 0);
        }
    });
}
