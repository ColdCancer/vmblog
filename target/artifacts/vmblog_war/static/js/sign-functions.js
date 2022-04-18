function requestLoginUp(account, password) {
    var content = null;
    $.ajax({
        url: '/web/passport/signup',
        data: {
            'account': account,
            'password': $.md5(password)},
        type: 'post',
        async: false,
        dataType: 'json',
        success: function (content_) {
            content = content_;
        }
    });
    return content;
}

function requestLoginOut() {
    var content = '';
    $.ajax({
        url: '/web/passport/signout',
        type: 'post',
        async: false,
        dataType: 'json',
        success: function (content_) {
            content = content_;
        }
    });
    return content;
}

function requestLoginIn(account, password, remember) {
    var content = null;
    $.ajax({
        url: '/web/passport/signin',
        data: {
            'account': account,
            'password': $.md5(password),
            'remember': remember},
        type: 'post',
        async: false,
        dataType: 'json',
        success: function (content_) {
            // console.log(typeof(content['code']));
            // console.log(content['code']);
            // alert_info(content["message"]);
            // if (content['code'] === 0) {
            //     $('#SignInBox').addClass("d-none");
            //     $('#SignUpBox').addClass("d-none");
            //     $('#AccountBox').removeClass("d-none");
            //     var photo_url = '/api/resources/' + $account + '/profile-photo';
            //     $('#profile-photo').attr('src', photo_url);
            // }
            content = content_;
        }
    });
    return content;
}

function requestStateOfLogin() {
    var content = null;
    $.ajax({
        url: '/api/passport/status',
        type: 'get',
        async: false,
        dataType: 'json',
        success: function (content_) {
            content = content_;
        }
    });
    return content;
}