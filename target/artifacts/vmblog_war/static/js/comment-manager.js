var currentPage = 1;

$(function () {
    getCommentItemsPageNum(currentPage);

    $('#comment-page-previous').click(function () {
        getCommentItemsPageNum(currentPage - 1);
        currentPage -= 1;
    });
    $('#comment-page-next').click(function () {
        getCommentItemsPageNum(currentPage + 1);
        currentPage += 1;
    });
})

function getCommentItemsPageNum(page) {
    $.ajax({
        url: '/web/api/comment/items/page/' + page,
        type: 'get',
        dataType: 'json',
        success: function (content) {
            if (content['code'] !== 0) return;
            var comment_json = content['data'];
            var comment_list_elem = $('#comment-manager-body');
            comment_list_elem.html('');
            for (var comment_index in comment_json) {
                var comment = comment_json[comment_index];
                var comment_elem = new CommentItem(comment_index, comment.article,
                    comment.to, comment.main, comment.post, comment.id).convert();
                comment_list_elem.append(comment_elem);
            }
            window.scrollTo(0, 0);

            $('.comment-delete').click(function () {
                // console.log(this);
                var id = $(this).attr('value');
                $.ajax({
                    url: '/web/api/comment/deleteComment/' + id,
                    type: 'post',
                    dataType: 'json',
                    success: function (content) {
                        if (content['code'] !== 0) {
                            alert_info("delete failure.");
                        } else {
                            alert_info("delete success.");
                            getCommentItemsPageNum(currentPage);
                        }
                    }
                });
            });
        }
    });
}

