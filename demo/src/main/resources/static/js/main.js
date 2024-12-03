/** First Slider */
$(".slider-one")
.not(".slick-intialized")
.slick({
    autoplay:true,
    autoplaySpeed:3000,
    dots:true,
    prevArrow: ".site-slider .slider-btn .prev",
    nextArrow: ".site-slider .slider-btn .next",
});
// Khi cửa sổ được load sẽ tự gọi hàm alert ra
// $(window).on('load', function () {  
//     var con= confirm("Đăng kí nhận bản tin Hoàng Sang\nĐừng bỏ lỡ hàng ngàn sản phẩm và chương trình siêu hấp dẫn");
//     if(con==true){
//         location.href = "#footer"
//     }
//     console.log("Hello");
// });

$(function () {
    $('[data-toggle="tooltip"]').tooltip()
});
    $('#myCollapsible').collapse({
        toggle: false
});
    $('.carousel').carousel();
//Get the button:
mybutton = document.getElementById("myBtn");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    mybutton.style.display = "block";
  } else {
    mybutton.style.display = "none";
  }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}