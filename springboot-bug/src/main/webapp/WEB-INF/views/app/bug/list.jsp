<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="../../util/tlds.jsp"%>
<style>
img {
	height: 40px;
	width: 40px;
}
</style>
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<form class="form-horizontal" role="form" id="searchForm" onsubmit="return false;">
			<!-- #section:elements.form -->
			<div class="form-group col-sm-4">
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1">项目名称</label>
				<div class="col-sm-9">
					<input type="text" id="form-field-1" name="projectName" placeholder="项目名称" class="col-xs-10 ">
				</div>
			</div>
			<div class="form-group col-sm-4">
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1">所属项目</label>
				<div class="col-sm-9">
					<select name="projectId" id="projectId" class="col-xs-10 ">
						<option value="">请选择</option>
						<c:forEach items="${projects }" var="project">
							<option value="${project.id }">${project.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-sm-4">
				<button class="btn  btn-primary btn-app" onclick="bt1.search('#searchForm')" type="submit">
					<i class="ui-icon ace-icon fa fa-search center bigger-110 "></i>查询
				</button>

			</div>
			<div class="clearfix"></div>
		</form>
		<div clas="col-xs-12">
			<a class="btn  btn-success btn-app" type="button" href="#bug/toAdd"> <i class="ui-icon ace-icon fa fa-plus center bigger-110 "></i>增加
			</a>
			<button class="btn btn-app btn-danger" type="button" onclick="deleteCheckedData()">
				<i class="ui-icon ace-icon fa fa-close center bigger-110 "></i>删除
			</button>
		</div>
	</div>
	<!-- /.col -->
</div>
<div class="row">
	<div class="col-xs-12">
		<table id="table">
			<thead>
				<tr>
					<th data-checkbox="true"></th>
					<th data-field='state' data-align="center" data-width="20" data-formatter="createIndex">序号</th>
					<th data-field="projectName" data-align="center" data-sortable="true">项目名称</th>
					<th data-field="description" data-align="center" data-sortable="true">bug描述</th>
					<th data-field="state" data-align="center" data-sortable="true" data-formatter='formatState'>状态</th>
					<th data-field="startTime" data-align="center" data-sortable="true" data-formatter='bootStrapTable.formatDate'>记录日期</th>
					<th data-field="updateTime" data-align="center" data-sortable="true" data-formatter='bootStrapTable.formatDate'>创建日期</th>
					<th data-field="id" data-align="center" data-width="80" data-formatter="operateFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script>
	function operateFormatter(value, row, index) {
		var html = "";
		html += '<a class="green tooltip-info" name="update_op" href="#bug/toEdit?id=' + value
				+ '" data-rel="tooltip" data-placement="bottom" title="编辑"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'
		html += '<a class="red" href="javascript:void(0)" onclick="bt1.remove(\'${ctx}/bug/delete\',' + value + ')" title="删除"><i class="ace-icon fa fa-trash-o bigger-130"></i>' + '</a>';
		return [ html ];
	}

	function formatState(state) {
		if (state == 0) {
			return "未处理"
		} else if (state == 1) {
			return "已处理"
		} else {
			return "已完成"
		}
	}

	function deleteCheckedData() {
		var accounts = $("#table").bootstrapTable("getSelections");
		var ids = [];
		for (var i = 0; i < accounts.length; i++) {
			if (accounts[i].id == 1) {
				continue;
			}
			ids.push(accounts[i].id);
		}
		if (ids.length == 0) {
			bootbox.alert("请先选择一条要删除的数据！");
			return;
		}
		bt1.remove('${ctx}/bug/delete', ids);
	}

	var otherOption = {
		sortName : "id",
		sortOrder : "asc",
		onLoadSuccess : function() {
			$('[data-uniqueid="1"]').children(":eq(0)").children().remove();
		}
	}

	var bt1 = bootStrapTable.initTable("#table", '${ctx}/bug/getList', otherOption);
	var scripts = [ null, null ]
	$('.page-content-area').ace_ajax('loadScripts', scripts);
</script>
