<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./util/tlds.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css" href="${ctx }/static/webuploader.css">
<script type="text/javascript" src="${ctx }/static/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/static/webuploader.js"></script>
</head>
<body>
	<!--dom结构部分-->
	<div id="uploader-demo">
		<!--用来存放item-->
		<div id="fileList" class="uploader-list"></div>
		<div id="filePicker">选择图片</div>
		<button onclick="uploadNow()">开始上传</button>
	</div>
	<script type="text/javascript">
		// 初始化Web Uploader
		var uploader = WebUploader.create({
			// 选完文件后，是否自动上传。
			auto : false,
			method : "post",
			fileVal : "file",
			formData : {
				filePath : "myImg"
			},
			// swf文件路径
			swf : '${ctx}/js/Uploader.swf',
			duplicate : true,//是否可重复选文件
			// 文件接收服务端。
			server : '${ctx }/webupload/upload',
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : {
				id : '#filePicker',
				multiple : true
			},
			// 只允许选择图片文件。
			accept : {
				title : 'Images',
				extensions : 'gif,jpg,jpeg,bmp,png',
				mimeTypes : 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
			}
		});

		// 当有文件添加进来的时候
		uploader.on('filesQueued', function(files) {
			var $list = $("#fileList");
			for (var i = 0; i < files.length; i++) {
				var file = files[i];
				var $div = $('<div id="' + file.id + '" class="item">' + '<img width="100" height="100"  onclick="deleteIt(this)">' + '<h4 class="info">' + file.name + '</h4>'
						+ '<p class="state">等待上传...</p>' + '</div>');
				var $img = $div.find("img");
				$list.append($div);
				// 创建缩略图
				// 如果为非图片文件，可以不用调用此方法。
				// thumbnailWidth x thumbnailHeight 为 100 x 100
				uploader.makeThumb(file, function(error, src) {
					if (error) {
						$img.replaceWith('<span>不能预览</span>');
						return;
					}
					$img.attr('src', src);
				}, 100, 100);
			}
		});

		uploader.on('uploadSuccess', function(file, result) {
			var $list = $("#fileList");
			uploader.makeThumb(file, function(error, src) {
				if (error) {
					$img.replaceWith('<span>不能预览</span>');
					return;
				}
				$("#" + file.id).children("img").attr("src", "${ctx }/" + result.data)
				$("#" + file.id).children("img").attr("relsrc", result.data)
			}, 100, 100);
		})

		// 文件上传过程中创建进度条实时显示。
		uploader.on('uploadProgress', function(file, percentage) {
			console.log(100 * percentage)
			var $li = $('#' + file.id);
			$li.children(".state").text(parseInt(percentage * 100) + '%');
		});

		function uploadNow() {
			uploader.upload();
		}

		function deleteIt(img) {
			var flag = confirm("确定要删除吗");
			if (flag) {
				//ajax请求将表单域直接序列化
				$.ajax({
					type : "POST",
					url : "${ctx }/webupload/deleteFile",
					data : {
						fileName : $(img).attr("relsrc")
					},
					success : function(result) {
						$(img).parent().remove();
					}
				});
			}

		}
	</script>
</body>
</html>