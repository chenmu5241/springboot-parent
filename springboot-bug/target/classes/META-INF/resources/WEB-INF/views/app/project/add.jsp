<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="../../util/tlds.jsp"%>
<div class="row">
	<div class="col-xs-12" id="content">
		<!-- PAGE CONTENT BEGINS -->
		<form class="form-horizontal" role="form"  id="form" method="post" action="">
			<input type="hidden" name="id" value="">
			<!-- #section:elements.form -->
			<div class="form-group col-xs-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1">项目名称<span style="color: red">*</span></label>
				<div class="col-sm-10">
					<input type="text"  placeholder="项目名称" name="name"  value=""  data-rule="required;remote[${ctx}/project/check]"  class="col-xs-8" />
				</div>
			</div>
			<div class="form-group col-xs-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1">英文名称</label>
				<div class="col-sm-10">
					<input type="text"  placeholder="英文名称" name="enName" value=""    class="col-xs-8" />
				</div>
			</div>
			<div class="form-group col-xs-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1">项目描述</label>
				<div class="col-sm-10">
					<input type="text" name="description"  placeholder="项目描述"  class="col-xs-8" />
				</div>
			</div>
			<div class="form-group col-xs-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1">项目url</label>
				<div class="col-sm-10">
					<input type="text" name="url"  placeholder="项目url"  class="col-xs-8" />
				</div>
			</div>
			<div class="form-group col-xs-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1">项目开始</label>
				<div class="col-sm-10">
					<input type="text" name="startTime"  id="startTime" readonly="readonly"  placeholder="项目开始"  class="col-xs-8 datepicker" />
				</div>
			</div>
			<div class="col-xs-12">
			<div class="space-20"></div>	
				<a id="btn_submit" onclick="submit()" class="btn btn-app btn-info no-border" type="button"> <i class="ace-icon fa fa-check"></i>确认</a> 
				<a href="javascript:history.go(-1)" class="btn btn-app btn-danger no-border" data-dismiss="modal" style="margin-left: 0px;"> 
				<i class="ace-icon fa fa-times"></i> 取消
				</a>
			</div>
		</form>
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->
<script>
appendBreadcrumb("#project/toList","新增");

function submit(){
	if(!$("form").isValid()){
		return;
	}
	bootbox.confirm("确定要增加吗?",function(f){
		if(f){
			$.ajax({
				type : "post",
				cache : false,
				async : true,
				dataType : "json",
				url : '${ctx}/project/insertOrUpdate',
				data : $("form").serialize(),
				success : function(result) {
					if (result.success) {
						bootbox.alert("添加成功",function(){
							location.href = "#project/toList";
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
        $('#startTime').datepicker({
            autoclose: true,
            language: 'zh-CN',
            format: 'yyyy-mm-dd'
        });
        $('#startTime').datepicker('setDate', new Date());
	});
</script>
