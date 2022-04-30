var currentPage = 1, update_flag = 0;

$(function () {
    getArticleByPageNum();
    $('#article-page-previous').click(function () {
        currentPage -= 1;
        getArticleByPageNum();
        if (update_flag === 0) currentPage += 1;
    });
    $('#article-page-next').click(function () {
        currentPage += 1;
        getArticleByPageNum();
        if (update_flag === 0) currentPage -= 1;
    });

    requestArticleRank();
    requestCommentCurrent();
});

function requestCommentCurrent() {
    $.ajax({
        url: '/api/comment/current',
        type: 'get',
        dataType: 'json',
        success: function (content) {
            if (content['code'] !== 0) return;
            var data = content['data'];
            var len = Object.keys(data).length;
            var commentCurrent = $('#comment-current');
            commentCurrent.html('');
            for (let index = 0; index < len; index++) {
                let item = data[index];
                commentCurrent.append(new CommentCurrentItem(
                    item['url'], item['title'], item['from'],
                    item['to'], item['comment']).convert());
            }
        }
    });
}

function requestArticleRank() {
    $.ajax({
        url: '/api/article/rank',
        type: 'get',
        dataType: 'json',
        success: function (content) {
            if (content['code'] !== 0) return;
            var data = content['data'];
            var len = Object.keys(data).length;
            var articleRank = $('#article-rank');
            articleRank.html('');
            for (let index = 0; index < len; index++) {
                let item = data[index];
                articleRank.append(new ArticleRankItem(item['count'],
                    item['url'], item['article']).convert());
            }
        }
    });
}

function getArticleByPageNum() {
    update_flag = 0;
    $.ajax({
        url: '/api/article/page/' + currentPage,
        type: 'get',
        async: false,
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
                    article.dislike, article.link, article.category,
                    article.account).convert();
                // console.log(article.category);
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
            $('#page-num').text('page: ' + currentPage);
            update_flag = 1;
        }
    });
}