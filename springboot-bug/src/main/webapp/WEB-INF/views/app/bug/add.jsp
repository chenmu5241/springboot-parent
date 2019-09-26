<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="../../util/tlds.jsp"%>
<div class="row">
	<div class="col-xs-12" id="content">
		<!-- PAGE CONTENT BEGINS -->
		<form class="form-horizontal" role="form" id="form" method="post" action="">
			<input type="hidden" name="id" value="">
			<input type="hidden" name="projectName" id="projectName" value="">
			<!-- #section:elements.form -->
			<div class="form-group col-xs-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1">所属项目<span style="color: red">*</span></label>
				<div class="col-sm-10">
					<select name="projectId" id="projectId" class="col-xs-8" style="padding: 0" data-rule="项目:required">
						<option value="">请选择</option>
						<c:forEach items="${projects }" var="project">
							<option value="${project.id }">${project.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group col-xs-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1">记录日期</label>
				<div class="col-sm-10">
					<input type="text" name="startTime" id="startTime" readonly="readonly" placeholder="项目开始" class="col-xs-8 datepicker" />
				</div>
			</div>
			<div class="form-group col-xs-12">
				<label class="col-sm-1 control-label no-padding-right" for="form-field-1">bug描述</label>
				<div class="col-sm-11">
					<script id="editor" name="description" style="height: 200px; width: 600px"></script>
				</div>
			</div>
			<div class="col-xs-12">
				<div class="space-20"></div>
				<a id="btn_submit" onclick="submit()" class="btn btn-app btn-info no-border" type="button"> <i class="ace-icon fa fa-check"></i>确认
				</a> <a href="javascript:history.go(-1)" class="btn btn-app btn-danger no-border" data-dismiss="modal" style="margin-left: 0px;"> <i class="ace-icon fa fa-times"></i> 取消
				</a>
			</div>
		</form>
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->
<script>
	appendBreadcrumb("#bug/toList", "新增");

	function submit() {
		if (!$("form").isValid()) {
			return;
		}
		$("#projectName").val($("#projectId"). find("option:selected").text() );
		
		bootbox.confirm("确定要增加吗?", function(f) {
			if (f) {
				$.ajax({
					type : "post",
					cache : false,
					async : true,
					dataType : "json",
					url : '${ctx}/bug/insertOrUpdate',
					data : $("form").serialize(),
					success : function(result) {
						if (result.success) {
							bootbox.alert("添加成功", function() {
								location.href = "#bug/toList";
							});
						} else {
							bootbox.alert(result.message);
						}
					}
				})
			}
		})
	}

	var ue;
	var scripts = [ null, null ]
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		if (ue) {
			ue.destroy()
		}
		ue = UE.getEditor('editor', {
			elementPathEnabled : false,
			toolbars : [ [ 'undo', //撤销
			'redo', //重做
			'bold', //加粗
			'italic', //斜体
			'underline', //下划线
			'strikethrough', //删除线
			'subscript', //下标
			'fontborder', //字符边框
			'formatmatch', //格式刷
			'removeformat', //清除格式
			'cleardoc', //清空文档
			'fontfamily', //字体
			'fontsize', //字号
			'paragraph', //段落格式
			'simpleupload', //单图上传
			'snapscreen', //截图
			] ]

		});

		$('#startTime').datepicker({
			autoclose : true,
			language : 'zh-CN',
			format : 'yyyy-mm-dd'
		});
		$('#startTime').datepicker('setDate', new Date());
	});
</script>
