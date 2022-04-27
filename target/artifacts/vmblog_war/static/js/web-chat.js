$(function () {
    requestChatList();

    $('#chat-send').click(function () {
        var chat_main = $('#chat-main');
        var text = chat_main.val();
        if (text === '') {
            alert_info("Input Chat Content, Please.");
            return;
        }
        $.ajax({
            url: '/web/api/chat/addChat',
            data: {'topic': text},
            type: 'post',
            dataType: 'json',
            success: function (content) {
                if (content['code'] !== 0) {
                    alert_info("chat failure.");
                } else {
                    alert_info("chat success.");
                    chat_main.val('');
                    requestChatList();
                }
            }
        })
    });

    $('#chat-cancel').click(function () {
        $('#chat-main').val('');
    })

    let timer = setInterval(function () {
        // console.log(login_flag);
        if (login_flag !== true) return;
        clearInterval(timer);
        $('#chat-info').addClass('d-none');
        // $('#reply-info').addClass('d-none');
        $('#chat-edit').removeClass('d-none');
        // $('#reply-edit').removeClass('d-none');
    }, 1000);
});

function requestChatList() {
    $.ajax({
        url: '/api/chat/currentChat',
        type: 'get',
        dataType: 'json',
        success: function(content) {
            if (content['code'] !== 0) return;
            var data = content['data'];
            // console.log(data);
            var chat_elem = $('#chat-list');
            chat_elem.html('');
            let len = Object.keys(data).length;
            for (let index in data) {
                let item = data[index];
                var item_elem;
                if (item['self']) {
                    item_elem = new ChatItemRight(item['name'], item['photo'],
                        item['date'], item['topic']).convert();
                } else {
                    item_elem = new ChatItemLeft(item['name'], item['photo'],
                        item['date'], item['topic']).convert();
                }
                chat_elem.append(item_elem);
                $('#chat-scroll').scrollTop(item_elem.offset().top - 20);
            }
        }
    })
}