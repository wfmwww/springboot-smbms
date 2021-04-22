<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
    <div class="right">
        <img class="wColck" src="${pageContext.request.contextPath }/statics/images/clock.jpg" alt=""/>
        <div class="wFont">
            <h2>${userSession.userName }</h2>
            <p>欢迎来到超市订单管理系统!</p>
        </div>
    </div>
<button onclick="xia()" style="background-color: red">下载</button>
<script type="text/javascript">
function xia() {
    window.location.href = "${pageContext.request.contextPath }/sys/file/download.html";
}
</script>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
