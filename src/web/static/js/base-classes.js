class ArticleCart {
    constructor(title, segmental, cover, post, blogger,
                views, like, dislike, link) {
        var url = "/article/" + blogger + "/" + link;
        // segmental = "<span style='white-space: pre;'>" + segmental + "</span>";
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
            "          </span>\n" +
            "          <span class='d-block text-center'>" +
            "            <small class=\"font-italic\">by:</small>\n" +
            "            <small class=\"text-muted font-weight-bold\">\n" +
            "              <a class=\"text-dark\" href=\"/blogger/{4}\">{4}</a>\n" +
            "            </small>\n" +
            "          </span>" +
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
        // console.log(segmental);
        this.pattern = String.format(this.pattern, title, segmental, cover,
            post, blogger, views, like, dislike, url);
    };

    convert() {
        return $(this.pattern);
    }
}

class ArticleItem {
    constructor(id, title, post_data, update_date, link) {
        var url = "/web/editor/article/edit/" + link;
        this.pattern = "<tr class=article-manager-item\">\n" +
            "             <td class=\"option-item text-center d-none\">\n" +
            "               <label class=\"mb-1\">" +
            "                   <input class='opt-items' type=\"checkbox\" name='opts' value='{5}'>" +
            "               </label>\n" +
            "             </td>\n" +
            "             <td class=\"text-center\" id=\"article-id\" scope=\"row\">{0}</td>\n" +
            "             <td class=\"text-center\" id=\"article-title\">{1}</td>\n" +
            "             <td class=\"text-center\" id=\"article-post-date\">{2}</td>\n" +
            "             <td class=\"text-center\" id=\"article-update-date\">{3}</td>\n" +
            "             <td class=\"text-center edit-item\" id=\"article-edit\">\n" +
            "               <a class=\"btn btn-sm btn-dark py-0\" href=\"{4}\">Edit</a>\n" +
            "             </td>\n" +
            "           </tr>";
        this.pattern = String.format(this.pattern, id, title, post_data, update_date, url, link);
    }

    convert() {
        return $(this.pattern);
    }
}

class SelectCategory {
    constructor(value, text) {
        this.pattern = "<option class=\"select-category\" value=\"{0}\">{1}</option>\n";
        this.pattern = String.format(this.pattern, value, text);
    }

    convert() {
        return $(this.pattern);
    }
}

class CategoryItem {
    constructor(id, name, count, son) {
        this.pattern = "<tr class=\"category-manager-item\">\n" +
            "             <td class=\"text-center\" id=\"cagegory-id\">{0}</td>\n" +
            "             <td class=\"text-center\" id=\"cagegory-name\">" +
            (son === 1 ? "<i class='bi bi-arrow-bar-right'></i>" : "<i class='bi bi-back'></i>") + " {1}" +
            "             </td>\n" +
            "             <td class=\"text-center\" id=\"cagegory-count\">{2}</td>\n" +
            "             <td class=\"text-center\" id=\"cagegory-opt\">\n" +
            "               <button type=\"button\" class=\"btn-category btn btn-s btn-dark py-0\" value=\"{3}\">Delete</button>\n" +
            "             </td>\n" +
            "             <input type='hidden' value='{1}'> " +
            "           </tr>";

        this.pattern = String.format(this.pattern, id, name, count, name);
    }

    convert() {
        return $(this.pattern);
    }
}

class ParentComment {
    constructor(from_blogger, date, to_blogger, id_to, comment) {
        var img = '/api/resources/' + from_blogger + '/profile-photo';
        id_to = id_to + ":" + from_blogger;
        this.pattern = "<li id='comment-item' class='list-group-item'>\n" +
            "    <div class='row'>\n" +
            "        <div class='col-2 border-right'>\n" +
            "            <div class='text-center my-auto'>\n" +
            "                <img id='comment-photo' class='image-responsive rounded-circle d-block mx-auto' src='{0}' height='40' width='40' alt='...'>\n" +
            "                <div id='comment-from' class='text-center'>{1}</div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div class='col'>\n" +
            "            <div class='row'>\n" +
            "                <div class='col-8'>\n" +
            "                    <span id='comment-date' class='text-secondary'>{2}</span>\n" +
            "                    <span id='comment-to' class='text-dark'>@{3} :</span>\n" +
            "                </div>\n" +
            "                <div class='col-4 text-right'>\n" +
            "                    <button type='button' class='comment-reply btn btn-sm btn-outline-primary' " +
            "                        data-toggle='modal' data-target='#modal-reply' value='{4}'>回复</button>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div class='row'>\n" +
            "                <div class='col-12'>\n" +
            "                    <span id='comment-content' class='text-primary pl-3'>{5}</span>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</li>";
        this.pattern = String.format(this.pattern, img, from_blogger, date,
            to_blogger, id_to, comment);
    }

    convert() {
        return $(this.pattern);
    }
}

class SonComment {
    constructor(from_blogger, date, to_blogger, id_to, comment) {
        var img = '/api/resources/' + from_blogger + '/profile-photo';
        id_to = id_to + ":" + from_blogger;
        this.pattern = "<li id='son_comment-item' class='list-group-item'>\n" +
            "    <div class='row'>\n" +
            "        <div class='col-1 border-right'></div>\n" +
            "        <div class='col-2 border-right'>\n" +
            "            <div class='text-center my-auto'>\n" +
            "                <img id='son-comment-photo' class='image-responsive rounded-circle d-block mx-auto' src='{0}' height='40' width='40' alt='...'>\n" +
            "                <div id='son-comment-from' class='text-center'>{1}</div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div class='col'>\n" +
            "            <div class='row'>\n" +
            "                <div class='col-9'>\n" +
            "                    <span id='son-comment-date' class='text-secondary'>{2}</span>\n" +
            "                    <span id='son-comment-to' class='text-dark'>@{3} :</span>\n" +
            "                </div>\n" +
            "                <div class='col-3 text-right'>\n" +
            "                    <button type='button' class='comment-reply btn btn-sm btn-outline-primary' " +
            "                        data-toggle='modal' data-target='#modal-reply' value='{4}'>回复</button>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div class='row'>\n" +
            "                <div class='col-12'>\n" +
            "                    <span id='son-comment-content' class='text-primary pl-3'>{5}</span>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</li>";
        this.pattern = String.format(this.pattern, img, from_blogger, date,
            to_blogger, id_to, comment);
    }

    convert() {
        return $(this.pattern);
    }
}