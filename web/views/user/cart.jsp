<%-- 
    Document   : cart
    Created on : May 24, 2023, 9:40:04 AM
    Author     : Huynh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,model.Category,model.Product,java.util.Map,jakarta.servlet.http.Cookie" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết giỏ hàng</title>
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <script src="https://kit.fontawesome.com/ddee20c3c8.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&display=swap" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel = "stylesheet" href = "${pageContext.request.contextPath}/assets/user/common/header.css">
        <link rel = "stylesheet" href = "${pageContext.request.contextPath}/assets/user/common/footer.css">
        <link rel = "stylesheet" href = "${pageContext.request.contextPath}/assets/user/cart/css/cart.css">
    </head>
    <body>
        <%
                //for header
            
                List<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
                Map<Integer, List<String>> branchesMap = (Map<Integer, List<String>>) request.getAttribute("branchesmap");
        
                //for main
                Product product = (Product) request.getAttribute("product"); 
        %>
        <header class="header">
            <div class="heading">
                <div class="logo">
                    <a href="home"><img src="./assets/user/home/images/header/logo.PNG" alt="" class="logo-img"></a>
                </div>
                <form action="allproduct" class = "search">
                    <input type="text" name ="searchname" class="search-bar" placeholder="Tìm kiếm...">
                    <button class="fa-solid fa-magnifying-glass icon-search"  onclick="form.submit()"></button>
                </form>
                <div class="contact">
                    <a href="#" class="hotline">Hotline: 190010001</a>
                </div>
            </div>
            <div class="header__nav">

                <ul class = "header__nav-list">
                    <li><a class = "header__nav-item" href = "home">Trang Chủ</a></li>
                    <li><a class = "header__nav-item" href = "allproduct?catid=0">Tất cả</a></li>
                        <%
                            for(Category category: categories){
                        %>
                    <li class = "header__brand-appear"><a class = "header__nav-item" href = "allproduct?catid=<%=category.getId()%>"><%=category.getName()%></a>
                        <div class="header__brand">
                            <ul class="wine-list">
                                <%
                                    for(String branch:  branchesMap.get(category.getId())){
                                %>
                                <li><a href="allproduct?catid=<%=category.getId()%>&branchoption=<%=branch%>" class="wine-item"><%=branch%></a></li>
                                    <%
                                        }
                                    %>

                            </ul>
                        </div>
                    </li>
                    <%
                        }
                    %>
                </ul>
                <ul class = "header__nav-btn">
                    <li class="header__nav-item user-btn -display-inline"><a href="user_profile"><i class="fa-solid fa-user"></i><span>${sessionScope.user.email}</span></a></li>
                                <% Cookie[] cart = request.getCookies();
                                    int num = 0;
                                    for(Cookie cookie : cart){
                                        if(cookie.getName().equals("cart")){
                                            num = cookie.getValue().split("_").length;
                                        }
                                    }
                                %>
                    <li class = "header__nav-item -display-inline"><a href = "cart"><i class="fa-solid fa-cart-shopping"></i><div class="cart-count"><%= num%></div></a></li>

                </ul>
            </div>
        </header>
        <div class="main">
            <% ArrayList<Map.Entry<Product, Integer> > cartItems = (ArrayList<Map.Entry<Product, Integer> >)request.getAttribute("cart"); %>
            <% 
               if(cartItems == null){
            %>
            <div id = "empty-cart-note">
                <p class ="empty-cart-note">Không có sản phẩm nào trong giỏ hàng</p>
                
            </div>
            <% 
                }else{
                       
            %>
            <table class="product-list-cart">
                <tr class="product-list-subject">
                    <th class="title-subject product-subject">Sản phẩm</th>
                    <th class = "origin-price-subject product-subject">Giá bán</th>
                    <th class="price-subject product-subject">Giá khuyến mại</th>
                    <th class="quantity-subject product-subject">Số lượng</th>
                    <th class="temporary-price-subject product-subject">Tạm tính</th>
                </tr>
                <% 
                int tong = 0;
                for(Map.Entry<Product, Integer> item : cartItems){
                %>
                <tr class = "product-item">
                    <td class = "product-title">
                        <a href = "updatecart?delete=<%= item.getKey().getId() %>" id = "delete-btn"><i class="fa-sharp fa-solid fa-trash delete-btn"></i></a>
                        <a class = "product-link" href="productdetail?pid=<%= item.getKey().getId() %>">
                            <img class = "product-image" src="./assets/admin/images/thumbnail/<%= item.getKey().getThumbnail() %>" alt="">
                            <p class = "product-name"><%= item.getKey().getName() %></p>
                        </a>
                        <p hidden class = "max-quantity"><%= item.getKey().getQuantity() %></p>
                    </td>
                    <%
                        String op = String.valueOf(item.getKey().getPrice());
                        int k = 0;
                        String price = "";
                        for(int j = op.length() - 1; j >= 0; j--){
                            price = op.charAt(j) + price;
                            k++;
                            if(k == 3 && j != 0){
                                price = "." + price;
                                k = 0;
                            }
                        }
                    
                    %>
                    <td class = "origin-price"><%= price %> VNĐ</td>
                    <%
                        String tmp = String.valueOf(item.getKey().getPrice_sale());
                        k = 0;
                        String salePrice = "";
                        for(int j = tmp.length() - 1; j >= 0; j--){
                            salePrice = tmp.charAt(j) + salePrice;
                            k++;
                            if(k == 3 && j != 0){
                                salePrice = "." + salePrice;
                                k = 0;
                            }
                        }
                    
                        String p = String.valueOf(item.getValue() * item.getKey().getPrice_sale());
                        String totalPricePerProduct = "";
                        k =0;
                        for(int j = p.length() - 1; j >= 0; j--){
                            totalPricePerProduct = p.charAt(j) + totalPricePerProduct;
                            k++;
                            if(k == 3 && j != 0){
                                totalPricePerProduct = "." + totalPricePerProduct;
                                k = 0;
                            }
                        }
                    %>
                    <td class = "price-product"><%= salePrice %> VNĐ</td>
                    <td class = "quantity-product">
                        <a class="minus" href = "updatecart?update=<%= item.getKey().getId() %>&quantity=<%= item.getValue() %>">-</a>
                        <input class="input-qty" max="<%= item.getKey().getQuantity() %>" min="1" readonly name="" type="number" value="<%= item.getValue() %>" >
                        <a class="plus" href = "updatecart?update=<%= item.getKey().getId() %>&quantity=<%= item.getValue() %>">+</a>
                    </td>
                    <td class = "temporary-price-product"><%= totalPricePerProduct %> VNĐ</td>
                </tr>
                <%
                    tong += item.getValue() * item.getKey().getPrice_sale();
                }

                String p = String.valueOf(tong);
                String totalPrice = "";
                int k =0;
                for(int j = p.length() - 1; j >= 0; j--){
                    totalPrice = p.charAt(j) + totalPrice;
                    k++;
                    if(k == 3 && j != 0){
                        totalPrice = "." + totalPrice;
                        k = 0;
                    }
                }
                %>



            </table>
            <form action="checkout" class = "payment-form" method = "GET">
                <strong class = "price-total">Tổng: <span><%= totalPrice %></span> VNĐ</strong>
                <input id = "payment-btn" type="submit" value = "THANH TOÁN">
            </form>
                <%} %>
        </div>
        <footer>
            <div class="footer__advantage">
                <div class="advantage-item advantage__genuine">
                    <i class="fa-solid fa-shield-halved advantage-icon"></i>
                    <h3>Cửa hàng rượu chính hãng</h3>
                    <i>Phân phối và bán lẻ rượu chính hãng</i>
                </div>
                <div class="advantage-item advantage__freeship">
                    <i class="fa-solid fa-truck advantage-icon"></i>
                    <h3>Giao hàng toàn quốc</h3>
                    <i>Freeship với hóa đơn từ 5 triệu</i>
                </div>
                <div class="advantage-item advantage__quality">
                    <i class="fa-solid fa-thumbs-up advantage-icon"></i>
                    <h3>Cam kết chất lượng</h3>
                    <i>Cửa hàng uy tín trên 1 năm</i>
                </div>
            </div>
            <div class="footer__content">
                <ul class="footer-content-list">
                    <li class = "footer-content-item content__about"><h3>Giới thiệu</h3>
                        <ul>
                            <li><a href="home">Về chúng tôi</a></li>
                            <li><a href="#">Bài viết - Blog</a></li>
                        </ul>
                    </li>
                    <li class = "footer-content-item content__product-list"><h3>Danh mục sản phẩm</h3>
                        <ul>
                            <%
                                for(int i = 0; i < Math.min(4, categories.size()); i++){
                            %>
                            <li><a href="allproduct?catid=<%=categories.get(i).getId()%>"><%=categories.get(i).getName()%></a></li>
                                <%
                                    }
                                %>
                        </ul>
                    </li>
                    <li class ="footer-content-item content_shop-system"><h3>Hệ thống cửa hàng</h3>
                        <ul>
                            <li>
                                <i class="fa-solid fa-location-dot"></i>
                                <a href = "https://goo.gl/maps/bbo9MHvQguqnbwnMA">Km10 - Nguyễn Trãi - Hà Đông - HN</a>
                            </li>
                            <li>
                                <i class="fa-solid fa-location-dot"></i>
                                <a href = "https://goo.gl/maps/ZewHMCuUf2GR7r8u7">122 Hoàng Quốc Việt - Cầu Giấy - HN</a>
                            </li>
                            <li>
                                <i class="fa-solid fa-phone"></i>
                                <a href = "">19001018</a>
                            </li>
                            <li>
                                <h3>LIÊN HỆ VỚI CHÚNG TÔI</h3>
                                <a><i class="fa-brands fa-facebook icon-contact"></i></a>
                                <a><i class="fa-brands fa-facebook-messenger icon-contact"></i></a>
                                <a><i class="fa-brands fa-instagram icon-contact"></i></a>

                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="copyright">
                <h3 class = "copyright-item">UỐNG RƯỢU CÓ TRÁCH NHIỆM</h3>
                <p class = "copyright-item">Các sản phẩm rượu không dành cho người dưới 18 tuổi và phụ nữ đang mang thai.</p>
                <p class = "copyright-item">©Copyright Nhóm 8 LTWeb PTIT 2023</p>
            </div>
        </footer>
        <script src = "./assets/user/cart/js/cart.js"></script>
    </body>
</html>
