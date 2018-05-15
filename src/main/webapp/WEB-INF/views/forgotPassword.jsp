<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Test Jsp</title>
    <%

        String path=application.getRealPath(request.getRequestURI());

        String dir=new File(path).getParent();

        System.out.println("当前JSP文件所在目录的物理路径"+dir+"</br>");

        String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);

        System.out.println("web URL 路径:"+realPath1);

    %>
    <%--百度在线jquery引入--%>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js" type="text/javascript"></script>
    <%--<script src="<%=realPath1%>/static/jquery-2.1.1.js" type="text/javascript"></script>--%>
</head>

<body>
<h2>路径测试</h2>
<p>当前WEB应用的物理路径：<%=application.getRealPath("/")%></p><BR
    <p>当前你求请的JSP文件的物理路径：<%=application.getRealPath(request.getRequestURI())%></p><BR>
<div>
    <div>
        <label>手机号</label>
        <div>
            <input type="text"  placeholder="必须是已经维护了手机号的导购员" name="mobile" id="mobile"/>
        </div>
        <label>图形验证码</label>
        <div>
            <input type="text" name="captcha" id="captcha" placeholder="请输入图形验证码" >
        </div>
        <div>
            <img id="vcodec" src="http://localhost:8888/CheckCode.svl"/>
        </div>
        <div>
            <button type="button" id="submit_button">验证</button>
        </div>
    </div>
</div>
<script type="text/javascript">
    $().ready(function () {
        $("#vcodec").on("click",function(){
            $(this).attr("src","http://localhost:8888/CheckCode.svl?v="+Math.random());
        });
        $("#submit_button").on('click',function(){
            var vcode=$("input[name='captcha']").val();
            var mobile=$("#mobile").val();
            var url="http://localhost:8888/restful/forgotPW/sendValid?mobile="+mobile+"&captcha="+vcode;
            $.post(url,function (text, status) {
                alert(text.toString()+":");
            });
        })
    })
</script>

</body>
</html>