$(document).ready(function()
{
    var submit = $("button[type='submit']");
    // bắt sự kiện click vào nút Login
    submit.click(function()
    {
        var username = $("input[name='username']").val();
        var password = $("input[name='password']").val();
        // Kiểm tra đã nhập tên tài khoản chưa
        if (username == '') {
            alert('Vui lòng nhập tài khoản');
            return false;
        }
        // Kiểm tra đã nhập mật khẩu chưa
        if (password == '') {
            alert('Vui lòng nhập mật khẩu');
            return false;
        }
        // Lấy tất cả dữ liệu trong form login
        var data = $('form#form-login').serialize();
        // Sử dụng $.ajax()
        $.ajax({
        type : 'POST', //kiểu post
        url  : 'submit.php', //gửi dữ liệu sang trang submit.php
        data : data,
        success :  function(data){
                if (data == 'false'){
                    alert('Sai tên đăng nhập hoặc mật khẩu');
                } else {
                    $('#content').html(data);
                }
        }
        });
        return false;
    });
});

var account = [
    {
        username = "admin",
        password = "123"
    },
    {
        username = "sict",
        password = "12345"
    }
]
function getInfo(){
    var username = document.getElementById("username").value
    var password = document.getElementById("password").value
    for(i=0;i<account.length;i++){
        if(username == account[i].username && password == account[i].password){
            document.getElementById("acount").innerHTML = account[i].username;
        }
    }
}