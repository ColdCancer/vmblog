class ArticleCart {
    constructor(title, segmental, cover, post, blogger,
                views, like, dislike, link) {
        var url = "/article/" + blogger + "/" + link;
        this.pattern = "      <div class=\"card\">\n" +
            "        <img src=\"{2}\" class=\"card-img-top img-thumbnail\" alt=\"...\">\n" +
            "        <div class=\"card-body\">\n" +
            "          <h5 class=\"card-title text-center\"><a href=\"{8}\">{0}</a></h5>\n" +
            "          <p class=\"card-text\">{1}</p>\n" +
            "        </div>\n" +
            "        <div class=\"card-footer\">\n" +
            "          <span class=\"d-block\">\n" +
            "            <small class=\"font-italic\">post:</small>\n" +
            "            <small class=\"text-muted\">{3}</small>\n" +
            "            <small class=\"font-italic\">, by:</small>\n" +
            "            <small class=\"text-muted font-weight-bold\">\n" +
            "              <a class=\"text-dark\" href=\"#\">{4}</a>\n" +
            "            </small>\n" +
            "          </span>\n" +
            "          <span class=\"d-block text-right\">\n" +
            "            <small class=\"font-italic\">views:</small>\n" +
            "            <span class=\"badge badge-info\">{5}</span>\n" +
            "            <small class=\"font-italic\">, like:</small>\n" +
            "            <span class=\"badge badge-success\">{6}</span>\n" +
            "            <small class=\"font-italic\">, dislike:</small>\n" +
            "            <span class=\"badge badge-warning\">{7}</span>\n" +
            "          </span>\n" +
            "        </div>\n" +
            "      </div>\n";
        this.pattern = String.format(this.pattern, title, segmental, cover,
            post, blogger, views, like, dislike, url);
    };

    convert() {
        return $(this.pattern);
    }
}

class ArticleItem {
    constructor(id, title, post_data, update_date, opt) {
        this.pattern = "<tr class=\"article-manager-item\">\n" +
            "             <th class=\"option-item d-none\">\n" +
            "               <label><input type=\"radio\"></label>\n" +
            "             </th>\n" +
            "             <th id=\"article-id\" scope=\"row\">{0}</th>\n" +
            "             <td id=\"article-title\">{1}</td>\n" +
            "             <td id=\"article-post-date\">{2}</td>\n" +
            "             <td id=\"article-update-date\">{3}</td>\n" +
            "           </tr>"
        this.pattern = String.format(this.pattern, id, title, post_data, update_date, opt)
    }

    convert() {
        return $(this.pattern);
    }
}