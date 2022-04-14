var editor;

$(function () {
    // getDefaultTime($('#article-data'));

    editor = editormd("article-editormd", {
        height              : 580,
        watch               : true,                // 关闭实时预览
        syncScrolling       : "single",
        // fontsize            : 28,
        // htmlDecode          : true,
        // emoji               : true,
        // flowChart           : true,
        // tex                 : true,         // 开启科学公式TeX语言支持，默认关闭
        saveHTMLToTextarea  : true,
        // tocm                : true,         // Using [TOCM]
        // sequenceDiagram     : true,
        // theme               : "dark",
        // previewTheme        : "dark",
        // editorTheme         : "pastel-on-dark",
        // dialogLockScreen    : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
        //dialogShowMask      : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
        //dialogDraggable     : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
        //dialogMaskOpacity   : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
        //dialogMaskBgColor   : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
        // imageUpload         : true,
        // imageFormats        : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        // imageUploadURL      : "./php/upload.php",
        path                : "/editor/lib/"
    });

    $('#btn-post').click(function () {
        var title = $('#article-title').val();
        // console.log($('#article-image')[0].files[0]);
        if (title === '') {
            alert_info("Input Article Title, Please");
        } else {
            var article = editor.getHTML();
            var blob_ojb = new Blob([article], {"type" : "text/html;charset=utf-8"});
            // console.log(article1);
            // console.log(typeof article1);
            // var textCon =
            var formData = new FormData();
            var nowData = getDefaultTime();
            // nowData = nowData.replace('T', ' ');
            formData.append("article", blob_ojb);
            formData.append("title", title);
            formData.append("postDate", nowData);
            $.ajax({
                url: '/api/article/addArticle',
                data: formData,
                type: 'post',
                processData: false,
                contentType: false,
                dataType: 'json',
                success: function (content) {
                    alert_info(content['message']);
                    // getDefaultTime($('#article-post'));
                    if (content['code'] !== 0) return;
                    var data = content['data'];
                    checkAndSendCover(data['account'], data['article-link']);
                    $('#last-post-date').html("Last Update: " + nowData);
                    changeNotEdit();
                }
            });
        }
    });

    $('#btn-edit').click(function () {
        $('#btn-save').attr('disabled', false);
        $('#btn-edit').addClass('d-none');
        $('#btn-post').removeClass('d-none');
        $('#article-title').attr('disabled', false);
        $('#article-cover').attr('disabled', false);
        $('#article-category').attr('disabled', false);
        // editor.config({ readOnly : false });
        $('.editormd-preview-close-btn').click();
        editor.watch();
        // $('#article-content').attr('disabled', false);
    });

    $('#btn-exit').click(function () {
        location.href = '/web/dashboard/article';
    });
});

function checkAndSendCover(account, link) {
    var file = $('#article-cover')[0].files[0];
    // console.log(file);
    if (file) {
        var formData = new FormData();
        formData.append("cover", file);
        formData.append('account', account);
        formData.append('link', link);

        $.ajax({
            url: '/api/article/addCover',
            type: 'post',
            processData: false,
            contentType: false,
            data: formData,
            dataType: 'json',
            success: function (content) {
                // alert_info(content['message']);
            }
        });
    }
}

function getDefaultTime() {
    var myDate = new Date((new Date).getTime() + 8 * 60 * 60 * 1000);
    return myDate.toJSON().substr(0, 19).replace('T', ' ');
    //.split('T').join(' ').substr(0,19);
    // alert_info(time);
}

function changeNotEdit() {
    $('#btn-save').attr('disabled', true);
    $('#btn-edit').removeClass('d-none');
    $('#btn-post').addClass('d-none');
    $('#article-title').attr('disabled', true);
    $('#article-cover').attr('disabled', true);
    $('#article-category').attr('disabled', true);
    editor.unwatch();
    editor.previewing();
    $('.editormd-preview-close-btn').addClass('d-none');
    // editor.config({ readOnly : true });
    // $('#article-content').attr('disabled', false);
}

function alert_info(message) {
    $('#alert-content').html(message);
    $('#alert-toast').toast('show');
}