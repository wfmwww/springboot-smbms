var billObj;

//订单管理页面上点击删除按钮弹出删除框(billlist.jsp)
function deleteBill(obj){
	$.ajax({
		type:"GET",
		url:path+"/sys/bill/delBill.html",
		data:{billid:obj.attr("billid")},
		dataType:"json",
		success:function(data){
			alert(data);
			alert(data == "true");
			if(data == "true"){//删除成功：移除删除行
				//cancleBtn();
				//obj.parents("tr").remove();
				window.location = path + "/sys/bill/billList.html";
			}else if(data == "false"){//删除失败
				//alert("对不起，删除订单【"+obj.attr("billcc")+"】失败");
				changeDLGContent("对不起，删除订单【"+obj.attr("billcc")+"】失败");
			}else if(data == "notexist"){
				//alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
				changeDLGContent("对不起，订单【"+obj.attr("billcc")+"】不存在");
			}
		},
		error:function(data){
			//cancleBtn();
			alert("对不起，删除失败");
			//changeDLGContent("对不起，删除失败");

		}
	});
}

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeBi').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeBi').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

$(function(){
	$(".viewBill").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href=path+"/sys/bill/billView.html?billid="+ obj.attr("billid");
	});
	
	$(".modifyBill").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/sys/bill/upBill.html?billid="+ obj.attr("billid");
	});
	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		cancleBtn();
		deleteBill(billObj);
	});

	/*$(".deleteBill").on("click",function(){
		billObj = $(this);
		changeDLGContent("你确定要删除订单【"+billObj.attr("billcc")+"】吗？");
		openYesOrNoDLG();
	});*/
	
	$(".deleteBill").on("click",function(){
		var obj = $(this);
		if(confirm("你确定要删除订单【"+obj.attr("billcc")+"】吗？")){
			$.ajax({
				type:"GET",
				url:path+"/sys/bill/delBill.html",
				data:{billid:obj.attr("billid")},
				dataType:"json",
				success:function(data){
					if(data == "true"){//删除成功：移除删除行
						//alert("删除成功");
						//obj.parents("tr").remove();
						window.location = path + "/sys/bill/billList.html";
					}else if(data == "false"){//删除失败
						alert("对不起，未支付订单【"+obj.attr("billcc")+"】不可删除");
					}else if(data == "notexist"){
						alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
					}
				},
				error:function(data){
					alert("对不起，删除失败");
				}
			});
		}
	});
});