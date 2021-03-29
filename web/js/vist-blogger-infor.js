window.onload = function () {
    var blogger = window.location.pathname.split('/')[1];
    alert(window.location.pathname);
    // $('#blogger-name').html(blogger);
    // $('#article-a').attr('href', blogger + "/article.html");
    // $('#link-a').attr('href', blogger + "/link.html");
    // $('#about-a').attr('href', blogger + "/about.html");

    // $.ajax({
    //     url: '/admin/checkBloggerByForm',
    //     data: {'account': $account, 'password': $password},
    //     type: 'post',
    //     dataType: 'text',
    //     success:function (data) {
    //         if (data === 'true') {
    //             location.href = 'manage.html'
    //         } else {
    //             alert('账号、密码错误！');
    //             // $('#login-info').html('账号、密码错误！').css('color', 'red');
    //         }
    //     }
    // })
}