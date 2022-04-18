var editor, account, link;

$(function () {
    updateInfo();

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
            // console.log(content);
            var data = content['data'];
            $('#article-title').text(data['title']);
            $('#article-watch').text(data['visCount']);
            $('#article-like').text(data['likeCount']);
            $('#article-dislike').text(data['dislikeCount']);
            $('#article-date').text(data['postDate']);
            info_flag = true;
            if (data['state'] === 'like') {
                setArticleState(1, 0);
            } else if (data['state'] === 'dislike') {
                setArticleState(0, 1);
            }
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

    $('#btn-like').click(function () {
        requestArticleState($('#article-like'), $('#article-dislike'), 'like');
    });

    $('#btn-dislike').click(function () {
        requestArticleState($('#article-dislike'), $('#article-like'), 'dislike');
    });

})

function setArticleState(state1, state2) {
    // var elems = [$('#btn-like'), $('#btn-dislike')];
    // console.log(elems);
    let elem1 = $('#btn-like')[0].getElementsByTagName('i')
    let elem2 = $('#btn-dislike')[0].getElementsByTagName('i')
    if (state1 === 1) {
        $(elem1).removeClass("bi-hand-thumbs-up");
        $(elem1).addClass("bi-hand-thumbs-up-fill");
    } else if (state1 === 0) {
        $(elem1).removeClass("bi-hand-thumbs-up-fill");
        $(elem1).addClass("bi-hand-thumbs-up");
    }
    if (state2 === 1) {
        $(elem2).removeClass("bi-hand-thumbs-down");
        $(elem2).addClass("bi-hand-thumbs-down-fill");
    } else if (state2 === 0) {
        $(elem2).removeClass("bi-hand-thumbs-down-fill");
        $(elem2).addClass("bi-hand-thumbs-down");
    }
}

function requestArticleState(state1, state2, opt) {
    updateInfo();
    $.ajax({
        url: '/api/article/operation/state',
        data: {
            "account": account,
            "link": link,
            "state": opt
        },
        type: "post",
        dataType: "json",
        success: function (content) {
            // console.log(content);
            if (content['code'] === -1) {
                alert_info("Sign In, Please.");
            }
            if (content['code'] !== 0) return;
            var flag = content['data']['like'];
            // console.log(flag);
            if (flag === 0) return;
            updateLikeState(state1, state2, flag);
            if (opt === 'like') {
                setArticleState(1, 0);
            } else if (opt === 'dislike') {
                setArticleState(0, 1);
            }
        }
    });
}

function updateLikeState(state1, state2, flag) {
    var count = parseInt(state1[0].textContent) + 1;
    state1.text(count);
    if (flag === 2) {
        count = parseInt(state2[0].textContent) - 1;
        state2.text(count);
    }
}

function updateInfo() {
    var seg_url = window.location.href.split('/');
    var len = seg_url.length;
    account = seg_url[len - 2];
    link = seg_url[len - 1];
}