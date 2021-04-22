<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面 >> 用户修改页面</span>
        </div>
        <div class="providerAdd">
        <form id="userForm" enctype="multipart/form-data" name="userForm" method="post" action="${pageContext.request.contextPath }/sys/user/upUserSave.html">
			<input type="hidden" name="id" value="${user.id }"/>
			 <div>
                    <label for="userName">用户名称：</label>
                    <input type="text" name="userName" id="userName" value="${user.userName }"> 
					<font color="red"></font>
             </div>
			 <div>
                    <label >用户性别：</label>
                    <select name="gender" id="gender">
						<c:choose>
							<c:when test="${user.gender == 1 }">
								<option value="1" selected="selected">女</option>
					    		<option value="2">男</option>
							</c:when>
							<c:otherwise>
								<option value="1">女</option>
					    		<option value="2" selected="selected">男</option>
							</c:otherwise>
						</c:choose>
					 </select>
             </div>
			 <div>
                    <label for="birthday">出生日期：</label>
                    <input type="text" Class="Wdate" id="birthday" name="birthday" value="<fmt:formatDate value="${user.birthday }" pattern="yyyy-MM-dd"/>"
					 onclick="WdatePicker();">
                    <font color="red"></font>
              </div>
			
		       <div>
                    <label for="phone">用户电话：</label>
                    <input type="text" name="phone" id="phone" value="${user.phone }"> 
                    <font color="red"></font>
               </div>
                <div>
                    <label for="address">用户地址：</label>
                    <input type="text" name="address" id="address" value="${user.address }">
                </div>
				<div>
                    <label >用户角色：</label>
                    <!-- 列出所有的角色分类 -->
					<input type="hidden" value="${user.userRole }" id="rid" />
					<select name="userRole" id="userRole"></select>
                    <%--<select name="userRole" id="userRole">
                        <option value="1" <c:if test="${user.userRole==1}">selected</c:if>>系统管理员</option>
                        <option value="2" <c:if test="${user.userRole==2}">selected</c:if>>经理</option>
                        <option value="3" <c:if test="${user.userRole==3}">selected</c:if>>普通用户</option>
                    </select>--%>
        			<font color="red"></font>
                </div>
            <div>
                <input type="hidden" id="errorInfo" value="${uploadFileError}">
                <label for="a_idPicPath">证件照：</label>
                <input type="file" name="attachs" id="a_idPicPath">
                <font color="red"></font>
            </div>
            <div>
                <input type="hidden" id="errorInfo_wp" value="${uploadFileError_wp}">
                <label for="a_workPicPath">工作证照片：</label>
                <input type="file" name="attachs" id="a_workPicPath">
                <font color="red"></font>
            </div>
			 <div class="providerAddBtn">
                    <input type="button" name="save" id="save" value="保存" />
                    <input type="button" id="back" name="back" value="返回"/>
                </div>
            </form>
        </div>
    </div>
</section>
<script type="text/javascript">
    var a_idPicPath = null;
    var errorInfo =null;
    var a_workPicPath = null;
    var errorInfo_wp =null;
    a_idPicPath = $("#a_idPicPath");
    errorInfo = $("#errorInfo");
    a_workPicPath = $("#a_workPicPath");
    errorInfo_wp = $("#errorInfo_wp");
    if(errorInfo.val()==null || errorInfo.val()==""){
        a_idPicPath.next().html("* 上传文件不能超过500k * 格式只能为 jpg、jpeg、png、pneg");
    }else{
        a_idPicPath.next().html(errorInfo.val());
    }
    if(errorInfo_wp.val()==null || errorInfo_wp.val()==""){
        a_workPicPath.next().html("* 上传文件不能超过500k * 格式只能为 jpg、jpeg、png、pneg");
    }else{
        a_workPicPath.next().html(errorInfo_wp.val());
    }
</script>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/usermodify.js"></script>
