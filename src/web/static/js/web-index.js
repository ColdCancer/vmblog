var currentPage = 1;

$(function () {
    getArticleByPageNum(currentPage);
    $('#article-page-previous').click(function () {
        getArticleByPageNum(currentPage - 1);
        currentPage -= 1;
    });
    $('#article-page-next').click(function () {
        getArticleByPageNum(currentPage + 1);
        currentPage += 1;
    });
});


function getArticleByPageNum(pageNum) {
    $.ajax({
        url: '/api/article/page/' + pageNum,
        type: 'get',
        dataType: 'json',
        success: function (content) {
            if (content['code'] !== 0) return;
            var article_json = content['data'];
            $article_list_elem = $('#article-list');
            $article_list_elem.html('');
            for (var article_index in article_json) {
                var article = article_json[article_index];
                $article_list_elem.append(new ArticleCart(article.title,
                    article.segmental, article.cover, article.post,
                    article.blogger, article.views, article.like,
                    article.dislike, article.link).convert());
            }
            window.scrollTo(0, 0);
        }
    });
}