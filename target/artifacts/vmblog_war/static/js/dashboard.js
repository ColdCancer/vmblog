$active_link_elem = null;
$link_box_pairs = {
    'information-link': $('#information-box'),
    'article-link': $('#article-box'),
    'category-link': $('#category-box'),
    'comment-link': $('#comment-box'),
}

function selectItem(elem) {
    // if ($active_link_elem) {
    //     $active_box_elem = $link_box_pairs[$active_link_elem.attr('id')];
    //     $active_box_elem.addClass('d-none');
    //     $active_box_elem.removeClass('d-flex');
    //     $active_link_elem.removeClass('active');
    // }
    // $active_link_elem = $(elem);
    // $active_box_elem = $link_box_pairs[$active_link_elem.attr('id')];
    // $active_box_elem.removeClass('d-none');
    // $active_link_elem.addClass('active');
    // $active_box_elem.addClass('d-flex');
}

function requestInformation() {
    $.ajax({
        url: '/web/dashboard/information',
        type: 'get',
        dataType: 'json',
        success: function (content) {
            console.log(content)
        }
    });
}

function requestArticle() {
    $.ajax({
        url: '/web/dashboard/article',
        type: 'get',
        dataType: 'json',
        success: function (content) {
            console.log(content)
        }
    });
}

function requestCategory() {
    $.ajax({
        url: '/web/dashboard/category',
        type: 'get',
        dataType: 'json',
        success: function (content) {
            console.log(content)
        }
    });
}

function requestComment() {
    $.ajax({
        url: '/web/dashboard/comment',
        type: 'get',
        dataType: 'json',
        success: function (content) {
            console.log(content)
        }
    });
}

$(function () {
    // selectItem($active_link_elem = $('#information-link'));
    // requestInformation();
});