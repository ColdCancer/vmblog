window.onload = function () {
    // 获取博主名字并输出至页面
    var blogger = window.location.pathname.split('/')[1];
    $('#blogger-name').html(blogger);


}