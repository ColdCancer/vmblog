$(function () {
    $.ajax({
        url: '/web/api/information',
        type: 'get',
        dataType: 'json',
        success: function (content) {
            if (content['code'] !== 0) return;
            var data = content['data'];
            console.log(data);
            let photo = '/api/resources/' + data['account'] + '/profile-photo';
            $('#blogger-photo').attr('src', photo);
            $('#info-name').text(data['name']);
            $('#info-account').text(data['account']);
            $('#info-register').text(data['register']);
            $('#info-current').text(data['current']);
            var article_elem = $('#info-article');
            article_elem.html('');
            for (let index in data['article']) {
                let item = data['article'][index];
                let segment = item['segment'];
                if (segment == null) segment = " - - - ";
                article_elem.append(new InfoArticleItem(item['title'], segment,
                    item['updateDate'], item['type'], item['topRank']).convert());
            }
            var comment_elem = $('#info-comment');
            comment_elem.html('');
            for (let index in data['comment']) {
                let item = data['comment'][index];
                comment_elem.append(new InfoCommnetItem(item['from'], item['to'],
                    item['article'], item['comment'], item['postDate']).convert());
            }
        }
    })
})