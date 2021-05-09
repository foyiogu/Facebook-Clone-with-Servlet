<%@ page import="com.francis.facebook.model.Post" %>
<%@ page import="com.francis.facebook.data_access_object.LikePostDAO" %>
<%@ page import="com.francis.facebook.model.Comment" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.francis.facebook.data_access_object.UserDAO" %>
<%@include file="includes/header.jsp"%>
<%@include file="includes/nav.jsp"%>

<div class="container">

    <div class="row">
        <div class="col-lg-3">
            <div class="shadow p-3 mb-5 bg-body rounded text-center">
                <img class="profile-pic-icon-three mt-4" src="assets/images/avatar.png" alt="">
                <h5 class="mt-3">Post Author: <%
                    User authUser = (User) request.getSession().getAttribute("user_session");

                    User postAuthor = (User) request.getAttribute("user");
                    Post post = (Post) request.getAttribute("post");
                    out.print(postAuthor.getName());

                %>
                </h5>
                <h5>
                    <span class="badge bg-dark">
                        No of Likes <br>
                        <%= request.getAttribute("totalLikes") %>
                    </span>
                </h5>
            </div>
        </div>
        <div class="col-lg-9">

            <div class="shadow p-3 mb-5 bg-body rounded">
                <div class="row">
                        <%
                            if(request.getParameter("post_success") != null) {
                        %>
                        <div class="alert alert-success" role="alert">
                            <button type="button" class="btn-close" aria-label="Close"></button>
                            Post Updated Successfully!!!
                        </div>
                        <%
                            }
                            if(request.getParameter("invalid_field") != null) {
                        %>
                        <div class="alert alert-danger" role="alert">
                            Enter a field, don't dare confuse my database. (y)
                        </div>
                        <%
                            }
                        %>
                    <h4 class="text-center">View Posts</h4>
                    <h6 class="card-subtitle mb-2 text-muted">
                        <%= post.getBody() %>
                    </h6>
                    <p class="text-muted"><%=post.getCreatedAt()%></p>
                    <div class="d-flex justify-content-between">
                        <form action="<%=request.getContextPath()%>/like_post" method="post">
                            <input type="hidden" name="post_id" value="<%=post.getId()%>">
                            <%
                                LikePostDAO likePostDAO = new LikePostDAO();

                                if (!likePostDAO.isPostLikedByUser(post.getId(), authUser.getId())) {
                            %>
                            <button type="submit"  class="btn btn btn-outline-primary">Like</button>
                            <%
                                }
                                else {
                            %>
                            <button type="submit" class="btn btn btn-outline-warning">Dislike</button>
                            <%
                                }
                            %>
                        </form>
                        <%
                            if(authUser.getId() == post.getUser_id()) {
                        %>
                        <form action="<%=request.getContextPath()%>/post" method="post">
                            <input type="hidden" name="page" value="delete_post">
                            <input type="hidden" name="post_user_id" value="<%= post.getUser_id()%>">
                            <input type="hidden" name="post_id" value="<%=post.getId()%>">
                            <input type="submit" class="btn btn-danger" value="Delete Post">
                        </form>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>

            <%
                //Uncomment this line of code during demostration to show how this feature works in back end.
                if (post.getUser_id() == authUser.getId()) {
            %>
            <div class="shadow p-3 mb-5 bg-body rounded mt-3">
                <div class="row">
                    <div class="col-lg-1">
                        <img class="profile-pic-icon-two" src="/assets/images/avatar.png" alt="">
                    </div>
                    <form action="<%=request.getContextPath()%>/post" method="post">
                        <input type="hidden" name="page" value="update_post">
                        <input type="hidden" name="post_id" value="<%= post.getId() %>">
                        <input type="hidden" name="post_author_id" value="<%= post.getUser_id() %>">
                        <div class="col-lg-11 text-right">
                            <textarea class="form-control drag" name="body" placeholder="write a post"><%= post.getBody() %></textarea>
                            <button class="btn btn-success btn-md mt-3 btn-block" type="submit">Update Post</button>
                        </div>
                    </form>
                </div>
            </div>

            <%
                }
            %>

            <div class="shadow p-3 mb-5 bg-body rounded">

                <div class="row">
                    <%
                        if(request.getParameter("comment_success") != null) {
                    %>
                    <div class="alert alert-success" role="alert">
                        Comment Added Successully.
                    </div>
                    <%
                        }

                    %>
                    <form action="<%=request.getContextPath()%>/comment" method="post">
                        <input type="hidden" name="post_id" value="<%= post.getId() %>">
                        <div class="row">
                            <div class="col-lg-10">
                                <input type="text" name="body" class="form-control comment-form">
                            </div>
                            <div class="col-lg-2">
                                <button type="submit" class="btn btn-primary">Add Comment</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <%
                ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
                if(comments.size() == 0){
            %>
                <h4 style="text-align: center; color: #ffffff;">No Comment Added for this Post!!</h4>
            <%
            } else {
                    out.println("<h4> Comments </h4>");
                    for(Comment comment : comments)
                    {
        %>
            <div id="section-one" class="shadow p-3 mb-5 bg-body rounded">
                <div class="row">
                    <div class="col-lg-1">
                        <img class="profile-pic-icon-two" src="/assets/images/avatar.png" />
                    </div>
                    <div class="col-lg-11">
                        <h5 class="postName">
                            <%
                                User commentAuthor = (new UserDAO().getUserById(comment.getUser_id()));
                                out.print(commentAuthor.getName());
                            %>
                        </h5>
                        <b>

                        </b>
                        <h6 class="card-subtitle mb-2 text-muted">
                            <%= comment.getBody() %>
                        </h6>
                        <p class="post-date">Likes and Dis Likes Here</p>


                    </div>

                </div>
            </div>
            <%
                }
                }
            %>

        </div>

    </div>
</div>
<div class="container">
    <div class="row">
        <nav aria-label="...">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
                </li>
                <li class="page-item active"><a class="page-link" href="#">1</a></li>
                <li class="page-item" aria-current="page">
                    <a class="page-link" href="#">2</a>
                </li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item">
                    <a class="page-link" href="#">Next</a>
                </li>
            </ul>
        </nav>
    </div>
</div>
<footer class="text-center mt-5 mb-5">Java Book - 2021</footer>
<script type="text/javascript">
    function removesection_one(){
        document.getElementById('section-one').remove();
    }
    function removesection_two(){
        document.getElementById('section-two').remove();
    }
    function removesection_three(){
        document.getElementById('section-three').remove();
    }
</script>


<%@include file="/includes/footer.jsp" %>
