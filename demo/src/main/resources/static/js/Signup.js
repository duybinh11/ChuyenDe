function Signup(){
    var checkUsername = 0;
    var id=idup;
    var level=0;
    var username = document.getElementById('usernameSignup').value;
    var email = document.getElementById('emailSignup').value;
    var password = document.getElementById('passwordSignup').value; 
    
    if (username != '' && password !='' && email !='' && password !='') {
    for(let i = 0; i < account.length; i++) {
        if (account[i].username== username) {
            checkUsername = 1;
            break;
        }
    } 
        if (checkUsername == 0) {
    accountSignup = {id,username,password,level};
    account.push(accountSignup);

    localStorage.setItem("account", JSON.stringify(account));

    checkLogin = id;
    localStorage.setItem("checkLogin", JSON.stringify(checkLogin));

    var id=idup++;
    localStorage.setItem("idup", JSON.stringify(idup));
    console.log(account); 
    document.getElementById("statusSignup").innerHTML="Đăng ký thành công";
    document.getElementById("menu_account_login").innerHTML = username;
        document.getElementById("menu_account_login").style.display = 'block';
        document.getElementById("menu_login_button").style.display = 'none';
        document.getElementById("hello_user").innerHTML ="Xin chào "+username+" !";
        document.getElementById("menu_logout").style.display = 'block'; 
        location.reload();
}else { 
document.getElementById("statusSignup").innerHTML="Tên tài khoản đã tồn tại";
}
}else {
document.getElementById("statusSignup").innerHTML="Vui lòng nhập đầy đủ thông tin";
}
}