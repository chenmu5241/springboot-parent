<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="../../util/tlds.jsp"%>
<div class="row">
	<div class="col-xs-12" id="content">
		<!-- PAGE CONTENT BEGINS -->
		<form class="form-horizontal" role="form"  id="form" method="post" action="">
			<input type="hidden" name="id" value="">
			<!-- #section:elements.form -->
			<div class="form-group col-xs-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1">申请人</label>
				<div class="col-sm-10">
					<input type="text"  placeholder="名称" name="user" value="" data-rule="required;remote[${ctx}/account/checkAccount]"  class="col-xs-8" />
				</div>
			</div>
			<div class="form-group col-xs-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1">申请原因</label>
				<div class="col-sm-10">
					<input type="text"  placeholder="名称" name="apply" value="" data-rule="required;remote[${ctx}/account/checkAccount]"  class="col-xs-8" />
				</div>
			</div>
			<div class="col-xs-12">
			<div class="space-20"></div>	
				<a id="btn_submit" onclick="submit()" class="btn btn-sm btn-info no-border" type="button"> <i class="ace-icon fa fa-check"></i>确认</a> 
				<a href="javascript:history.go(-1)" class="btn btn-sm btn-danger no-border" data-dismiss="modal" style="margin-left: 0px;"> 
				<i class="ace-icon fa fa-times"></i> 取消
				</a>
			</div>
		</form>
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->
<script>
appendBreadcrumb("#account/index","新增");

function submit(){
	if(!$("form").isValid()){
		return;
	}
	bootbox.confirm("增加后用户名无法修改,确定要增加吗?",function(f){
		if(f){
			$.ajax({
				type : "post",
				cache : false,
				async : true,
				dataType : "json",
				url : '${ctx}/account/insert',
				data : $("form").serialize(),
				success : function(result) {
					if (result.success) {
						bootbox.alert("添加成功",function(){
							location.href = "#account/toAccountList";
						});
					} else {
						bootbox.alert(result.message);
					}
				}
			})
		}
	})
}

	var scripts = [ null, null ]
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		
		
	});
</script>
