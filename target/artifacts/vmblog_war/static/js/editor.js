// var editor;

$(function () {
    getDefaultTime($('#article-post'));

    var editor = editormd("article-editormd", {
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
        // var article = $('#')
        if (title === '') {
            alert_info("Input Article Title, Please");
        } else {
            var article = editor.getHTML();
            $.ajax({
                url: '/web/article/add',
                data: {
                    'title': title,
                    'article': article,
                    'postDate': $('#article-post').val()
                },
                type: 'post',
                dataType: 'json',
                success: function (content) {
                    alert_info(content['message']);
                    getDefaultTime($('#article-post'));
                }
            });
        }
    });
});

function getDefaultTime(elem) {
    var myDate = new Date((new Date).getTime() + 8 * 60 * 60 * 1000);
    var time = myDate.toJSON().substr(0,19);
    //.split('T').join(' ').substr(0,19);
    // alert_info(time);
    $(elem).val(time);
}

function alert_info(message) {
    $('#alert-content').html(message);
    $('#alert-toast').toast('show');
}