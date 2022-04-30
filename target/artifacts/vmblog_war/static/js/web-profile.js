$(function () {
    var seg_url = window.location.href.split('/');
    var len = seg_url.length;
    var account = seg_url[len - 2];

    $.ajax({
        url: '/api/blogger/information',
        type: 'get',
        data: {'account': account},
        dataType: 'json',
        success: function (content) {
            if (content['code'] !== 0) return;
            // name, sex, motto, article-count, comment-count, current, days
            var data = content['data'];
            $('#home-name').text(data['name']);
            $('#home-sex').text(data['sex']);
            $('#home-talk').text(data['motto']);
            $('#home-birthplace').text(data['birthplace']);
            $('#home-education').text(data['education']);
            $('#home-email').text(data['email']);
            $('#home-company').text(data['company']);
            $('#blog-total').text(data['articleTotal']);
            $('#blog-time').text(data['currentPost']);
            $('#blog-comment').text(data['commentTotal']);
            $('#blog-alive').text(data['liveTime']);
        }
    });

    $.ajax({
        url: '/api/comment/blogger/current',
        type: 'get',
        data: {'account': account},
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
});