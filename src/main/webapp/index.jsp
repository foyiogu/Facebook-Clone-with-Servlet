<%@include file="includes/header.jsp"%>

<div class="container mt-5">

    <div class="row welcome">

        <div class="col-lg-7">

            <img src="" alt="">
            <h1 class="logo pb-2">JAVA Book</h1>
            <h2 class="JavaFacebook mb-5">JAVA Book helps you connect and share <br> with Java Programmers.</h2>
        </div>

        <div class="col-lg-5">

            <div class="shadow p-3 mb-5 bg-body rounded">

                <form action="<%= request.getContextPath() %>/front" method="post">
                    <input type="hidden" name="page" value="login">

                    <div class="mb-3">
                        <input type="email" name="email" value="<%=request.getAttribute("email1") != null ? request.getAttribute("email1").toString() : ""  %>"  class="form-control" placeholder="Email Address" aria-describedby="emailHelp">
                        <%
                            if(request.getAttribute("email") != null) {
                        %>
                        <div id="emailHelp" class="form-text text-danger"><%= request.getAttribute("email").toString() %></div>
                        <%
                            }
                        %>
                    </div>

                    <div class="mb-3">
                        <input type="password" name="password" class="form-control" placeholder="Password" aria-describedby="passwordHelp">
                    </div>

                    <div class="d-grid">
                        <button class="btn btn-primary btn-lg" type="submit">Log In</button>
                    </div>
                </form>

                <div class="d-grid gap-2 mt-3">
                    <p class="text-center mt-2"><a href="#">Forgotten password?</a></p>
                    <div class="divider"></div>
                    <a href ="<%= request.getContextPath() %>/front?page=register" class="btn btn-green btn-lg mt-3 mb-3">
                        Create New Account
                    </a>

                </div>
            </div>

        </div>

    </div>

</div>


<%@include file="/includes/footer.jsp" %>

