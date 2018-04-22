;

$(function() {
    // $.ajax({
    //     method: 'POST',
    //     url: '',
    //     contentType: 'application/json;charset=UTF-8',
    //     success: function (info) {
   
//    var info = {
//        "order": "73211091092918391",
//        "date": "2017年8月31日",
//        "username": "邱隘镇",
//        "password": "107372",
//        "phone": "12345678901",
//        "contactPhone": "12121212121"
//    }

    var info = JSON.parse(ZJDX.getPdfInfo());

    $('.orderid').html(info.order);
    $('.acceptdate').html(info.date);
    $('.username').html(info.username);
    $('.telephone').html(info.contactPhone);//联系人号码
    $('.newtelephone').html(info.phone);//办理的新号码
    $('.password').html(info.password);

    // }
    // })
})