document.addEventListener("DOMContentLoaded", function () {
    const buyButtons = document.querySelectorAll(".buy-btn");
    const increaseButtons = document.querySelectorAll(".quantity-btn.increase");
    const decreaseButtons = document.querySelectorAll(".quantity-btn.decrease");

    increaseButtons.forEach(button => {
        button.addEventListener("click", function () {
            const quantityElement = this.closest(".cart-item").querySelector(".quantity-value");
            let currentQuantity = parseInt(quantityElement.getAttribute("data-quantity"));
            currentQuantity++; // Tăng số lượng lên 1
            quantityElement.setAttribute("data-quantity", currentQuantity);
            quantityElement.textContent = currentQuantity; // Cập nhật hiển thị
        });
    });

    decreaseButtons.forEach(button => {
        button.addEventListener("click", function () {
            const quantityElement = this.closest(".cart-item").querySelector(".quantity-value");
            let currentQuantity = parseInt(quantityElement.getAttribute("data-quantity"));
            if (currentQuantity > 1) {
                currentQuantity--; // Giảm số lượng xuống 1 nếu lớn hơn 1
                quantityElement.setAttribute("data-quantity", currentQuantity);
                quantityElement.textContent = currentQuantity; // Cập nhật hiển thị
            }
        });
    });

    buyButtons.forEach(button => {
        button.addEventListener("click", function () {
            const cartItem = this.closest(".cart-item");
            const cartId = cartItem.getAttribute("cart-id");
            const quantityElement = cartItem.querySelector(".quantity-value");
            const quantity = quantityElement.getAttribute("data-quantity");
    
    
            console.log("Cart ID:", cartId);
            console.log("Quantity:", quantity);
    
            fetch("/     ", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ id: cartId, count: quantity, isFromCart: 1 }),  // Kiểm tra lại việc gửi giá trị đúng
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("Mua thành công!");
                } else {
                    alert("Có lỗi xảy ra khi mua.");
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Có lỗi xảy ra khi gửi yêu cầu.");
            });
        });
    });
    
});
