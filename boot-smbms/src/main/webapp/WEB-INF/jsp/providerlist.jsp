<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>供应商管理页面</span>
        </div>
        <div class="search">
        	<form method="post" action="${pageContext.request.contextPath }/sys/pro/proList.html">
				<input name="method" value="query" type="hidden">
				<span>供应商编码：</span>
				<input name="queryProCode" type="text" value="${queryProCode }">
				
				<span>供应商名称：</span>
				<input name="queryProName" type="text" value="${queryProName }">
				<input type="hidden" name="pageIndex" value="1"/>
				<input value="查 询" type="submit" id="searchbutton">
				<c:if test="${userSession.userRole!=3}">
				<a href="${pageContext.request.contextPath }/sys/pro/addPro.html">添加供应商</a>
				</c:if>
			</form>
        </div>
        <!--供应商操作表格-->
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
                <th width="10%">供应商编码</th>
                <th width="20%">供应商名称</th>
                <th width="10%">联系人</th>
                <th width="10%">联系电话</th>
                <th width="10%">传真</th>
                <th width="10%">创建时间</th>
                <th width="30%">操作</th>
            </tr>
            <c:forEach var="provider" items="${providerList }" varStatus="status">
				<tr>
					<td>
					<span>${provider.proCode }</span>
					</td>
					<td>
					<span>${provider.proName }</span>
					</td>
					<td>
					<span>${provider.proContact}</span>
					</td>
					<td>
					<span>${provider.proPhone}</span>
					</td>
					<td>
					<span>${provider.proFax}</span>
					</td>
					<td>
					<span>
					<fmt:formatDate value="${provider.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
					</td>
					<td>
					<span><a class="viewProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/statics/images/read.png" alt="查看" title="查看"/></a></span>
						<c:if test="${userSession.userRole!=3}">
					<span><a class="modifyProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/statics/images/xiugai.png" alt="修改" title="修改"/></a></span>
					<span><a class="deleteProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/statics/images/schu.png" alt="删除" title="删除"/></a></span>
						</c:if>
					</td>
				</tr>
			</c:forEach>
        </table>
	<c:if test="${providerList.size()==0}"><h1 align="center" style="color: red">未查询到</h1></c:if>

	<input type="hidden" id="totalPageCount" value="${totalPageCount}"/>
		<c:import url="rollpage.jsp">
			<c:param name="totalCount" value="${totalCount}"/>
			<c:param name="currentPageNo" value="${currentPageNo}"/>
			<c:param name="totalPageCount" value="${totalPageCount}"/>
		</c:import>
	<div class="providerAdd" id="proView" style="display: none">
		<div>
			<label>供应商编码：</label>
			<input type="text" id="v_proCode" value="" readonly="readonly">
		</div>
		<div>
			<label>供应商名称：</label>
			<input type="text" id="v_proName" value="" readonly="readonly">
		</div>
		<div>
			<label>供应商联系人：</label>
			<input type="text" id="v_proContact" value="" readonly="readonly">
		</div>
		<div>
			<label>联系电话：</label>
			<input type="text" id="v_proPhone" value="" readonly="readonly">
		</div>
		<div>
			<label>地址：</label>
			<input type="text" id="v_proAddress" value="" readonly="readonly">
		</div>
		<div>
			<label>传真：</label>
			<input type="text" id="v_roFax" value="" readonly="readonly">
		</div>
		<div>
			<label>供应商详细描述：</label>
			<%--<input id="v_proDesc" value="" readonly="readonly"/>--%>
			<textarea id="v_proDesc" cols="40" rows="3"  readonly></textarea>
		</div>
	</div>
    </div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeProv">
   <div class="removerChid">
       <h2>提示</h2>
       <div class="removeMain" >
           <p>你确定要删除该供应商吗？</p>
           <a href="#" id="yes">确定</a>
           <a href="#" id="no">取消</a>
       </div>
   </div>
</div>

<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/providerlist.js"></script>
