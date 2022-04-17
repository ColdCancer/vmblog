var editor, account, link;

$(function () {

    var seg_url = window.location.href.split('/');
    var len = seg_url.length;
    account = seg_url[len - 2];
    link = seg_url[len - 1];

    var info_flag = false;
    $.ajax({
        url: '/api/article/info',
        type: 'get',
        async: false,
        data: {
            'account': account,
            'link': link
        },
        dataType: 'json',
        success: function (content) {
            if (content['code'] !== 0) return;
            console.log(content);
            var data = content['data'];
            $('#article-title').text(data['title']);
            $('#article-watch').text(data['visCount']);
            $('#article-like').text(data['likeCount']);
            $('#article-dislike').text(data['dislikeCount']);
            $('#article-date').text(data['postDate']);
            info_flag = true;
        }
    });

    if (info_flag) {
        $.ajax({
            url: '/api/article/md/content',
            type: 'get',
            data: {
                'account': account,
                'link': link
            },
            dataType: 'text',
            success: function (content) {
                // if (content['code'] !== 0) return;
                // console.log(content);

                editor = editormd.markdownToHTML("article-previous", {
                    htmlDecode: "style,script,iframe",
                    markdown: content,
                    delay: 50,
                    emoji: true,
                    // previewTheme: "dark",
                    taskList: true,
                    tex: true,
                    flowChart: true,
                    sequenceDiagram: true
                })
                // $('.editormd-markdown-textarea').text(content);
            }
        });
    }
})