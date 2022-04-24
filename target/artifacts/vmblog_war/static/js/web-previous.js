var editor, account, link, currentId, nowData, parentId, toBlogger;

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

    let timer = setInterval(function () {
        // console.log(login_flag);
        if (login_flag !== true) return;
        clearInterval(timer);
        $('#comment-info').addClass('d-none');
        $('#reply-info').addClass('d-none');
        $('#comment-edit').removeClass('d-none');
        $('#reply-edit').removeClass('d-none');
    }, 1000);

    requestCommentList();

    $('#btn-like').click(function () {
        requestArticleState($('#article-like'), $('#article-dislike'), 'like');
    });

    $('#btn-dislike').click(function () {
        requestArticleState($('#article-dislike'), $('#article-like'), 'dislike');
    });

    $('#comment-send').click(function () {
        var comment_elem = $('#comment-main');
        var text = comment_elem.val();
        // console.log(text);
        $.ajax({
            url: '/web/api/comment/addComment',
            type: 'post',
            data: {
                'account': account,
                'link': link,
                'comment': text
            },
            dataType: 'json',
            success: function (content) {
                // console.log(content);
                if (content['code'] !== 0) {
                    alert_info("comment failure.");
                } else {
                    alert_info("comment success.");
                    requestCommentList();
                    comment_elem.val('');
                }
            }
        })
    });

    $('#modal-reply').on('shown.bs.modal', function () {
        // $('#myInput').trigger('focus')
        // console.log(currentId);
        // console.log(nowData[currentId]);
        let data = nowData[currentId];
        $('#reply-photo').attr('src', '/api/resources/' + data['from'] + '/profile-photo')
        $('#reply-from').text(data['from']);
        $('#reply-date').text(data['date']);
        $('#reply-to').text('@' + data['to'] + " :");
        $('#reply-content').text(data['topic']);
    })

    $('#reply-send').click(function () {
        // console.log(this);
        var text = $('#reply-main').val();
        if (text === '') {
            alert_info("Input Content, Please.");
            return;
        }
        $.ajax({
            url: '/web/api/comment/attachComment',
            type: 'post',
            data: {
                'parent-id': currentId,
                'article-account': account,
                'article-link': link,
                'to-blogger': toBlogger,
                'topic': text
            },
            dataType: 'json',
            success: function (content) {
                console.log(content);
                if (content['code'] !== 0) {
                    alert_info("send failure.");
                } else {
                    alert_info("send success.");
                    requestCommentList();
                    $("#modal-reply").modal("hide");
                }
            }
        });

    })
})

function requestCommentList() {
    $.ajax({
        url: '/api/comment/list',
        type: 'get',
        data: {
            'account': account,
            'link': link
        },
        dataType: 'json',
        success: function (content) {
            // console.log(content);
            if (content['code'] !== 0) return;
            var data = content['data'];
            nowData = data;
            var keys = Object.keys(data);
            // console.log(keys);
            var len = keys.length;
            if (len === 0) return;
            var direct = {}, flag = [];
            for (let i = 0; i < len; i++) {
                direct[keys[i]] = null;
                flag[keys[i]] = 1;
            }
            // console.log(direct);
            for (let i = 0; i < len; i++) {
                var item = data[keys[i]];
                let parentId = item['parent'];
                if (parentId == null) continue;
                direct[parentId] = parseInt(keys[i]);
                direct[keys[i]] = -1;
                flag[keys[i]] = 0;
            }
            // console.log(direct);
            // console.log(flag);
            var commentList = $('#comment-list');
            commentList.html('');
            // for (let id in data) {
            for (let index = len - 1; 0 <= index; index--) {
                if (flag[keys[index]] === 0) continue;
                let item = data[keys[index]];
                console.log(item);
                commentList.append(new ParentComment(item['fromName'], item['date'],
                    item['toName'], keys[index] + ':' + item['parent'] + ':' + item['from'], item['topic']).convert());
                let son_index = direct[keys[index]];
                // console.log(son_index);
                while (son_index !== null && son_index !== -1) {
                    item = data[son_index];
                    commentList.append(new SonComment(item['from'], item['date'],
                        item['to'], son_index + ':' + item['parent'], item['topic']).convert());
                    son_index = direct[son_index];
                }
            }

            $('.comment-reply').click(function () {
                // console.log($(this).attr('value').split(':'));
                var value_item = $(this).attr('value').split(':');
                currentId = value_item[0];
                parentId = value_item[1];
                toBlogger = value_item[2];
            });
        }

    });

}

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