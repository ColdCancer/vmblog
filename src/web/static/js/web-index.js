class ArticleCart {
    constructor(title, segmental, image, post, blogger,
                views, like, dislike, link) {
        var url = "/article/" + blogger + "/" + link;
        this.pattern = "<div class=\"card shadow mb-3\" >\n" +
            "  <div class=\"row no-gutters\">\n" +
            "    <div class=\"col-md-4\">\n" +
            "      <img class=\"img-thumbnail\" src=\"{2}\" alt=\"...\">\n" +
            "    </div>\n" +
            "    <div class=\"col-md-8\">\n" +
            "      <div class=\"card-body\">\n" +
            "        <h5 class=\"card-title\"><a href=\"{8}\">{0}</a></h5>\n" +
            "        <p class=\"card-text\">{1}</p>\n" +
            "        <p class=\"card-text\">\n" +
            "        <small class=\"font-italic\">post:</small>\n" +
            "        <small class=\"text-muted\">{3}</small>\n" +
            "        <small class=\"font-italic\">, by:</small>\n" +
            "        <small class=\"text-muted font-weight-bold\">\n" +
            "          <a class=\"text-dark\" href=\"#\">{4}</a>\n" +
            "        </small>\n" +
            "        <small class=\"font-italic\">, views:</small>\n" +
            "        <span class=\"badge badge-info\">{5}</span>\n" +
            "        <small class=\"font-italic\">, like:</small>\n" +
            "        <span class=\"badge badge-success\">{6}</span>\n" +
            "        <small class=\"font-italic\">, dislike:</small>\n" +
            "        <span class=\"badge badge-warning\">{7}</span>\n" +
            "        </p>\n" +
            "      </div>\n" +
            "    </div>\n" +
            "  </div>\n" +
            "</div>";
        this.pattern = String.format(this.pattern, title, segmental, image,
            post, blogger, views, like, dislike, url);
    };

    convert() {
        return $(this.pattern);
    }
}

var currentPage = 1;

$(function () {
    // setWebArticleIndex(currentPage);
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
                    article.segmental, article.image, article.post,
                    article.blogger, article.views, article.like,
                    article.dislike, article.link).convert());
            }
            window.scrollTo(0, 0);
        }
    });
}