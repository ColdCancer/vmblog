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
                var article_elem = new ArticleCart(article.title,
                    article.segmental, article.cover, article.post,
                    article.blogger, article.views, article.like,
                    article.dislike, article.link).convert();
                if (article.cover === '#') {
                    $(article_elem).find("img")[0].remove();
                }
                $article_list_elem.append(article_elem);
            }
            window.scrollTo(0, 0);
        }
    });
}