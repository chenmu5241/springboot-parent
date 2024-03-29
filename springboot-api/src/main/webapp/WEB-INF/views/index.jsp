<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./util/tlds.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title id="title">swagger-ui-layer</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="webjars/layui/css/layui.css">
<link rel="stylesheet" type="text/css" href="webjars/css/global.css">
<link rel="stylesheet" type="text/css" href="webjars/css/jquery.json-viewer.css">
</head>
<body>
	<script id="template" type="text/template">
    <div class="layui-layout layui-layout-admin"
        style="border-bottom: solid 3px #1aa094;">
        <div class="layui-header header "> 
            <div class="layui-main">
                <div class="admin-login-box logo">
                        <span>{{:info.title}}<small class="version">{{:info.version}}</small></span>
                </div>
				<a href="${ctx}/logout" style="float:right;margin-top:20px;font-size:18px;color:white">退出</a>
            </div>
        </div>
        <div class="layui-side layui-bg-black" id="admin-side">
            <div class="layui-side-scroll" id="admin-navbar-side"
                lay-filter="side">
                <ul class="layui-nav layui-nav-tree beg-navbar">
                    {{for tags itemVar="~tag"}}
                        {{if name != "basic-error-controller"}}
                        <li class="layui-nav-item"><a href="javascript:;"><i class="fa fa-cogs" aria-hidden="true" data-icon="fa-cogs"></i>
                            <cite>{{:name}}</cite><span class="layui-nav-more"></span></a>
                            <dl class="layui-nav-child">
                                {{!--获取tags下面对应的方法--}}
                                {{props ~root.paths itemVar="~path"}}
                                    {{!--具体方法--}}
                                    {{props prop}}
                                        {{if prop.tags[0] == ~tag.name}}
                                            <dd title="{{:key}} {{:prop.description}}">
                                                <a href="javascript:;" style="line-height:56px" name="a_path" path="{{:~path.key}}" method="{{:key}}" operationId="{{:prop.operationId}}"><i class="fa fa-navicon" data-icon="fa-navicon"></i><cite class=""><cite class="{{:key}}_font pl10">{{:prop.summary}}</cite></a>
                                            </dd>
                                        {{/if}}
                                    {{/props}}
                                {{/props}}
                            </dl>
                        </li>
                        {{/if}}
                    {{/for}}
				</ul>
            </div>
        </div>
        <div class="layui-body site-content" id="path-body"
            style="border-left: solid 2px #1AA094;">
            {{!-- body 内容  $ref = temp_body --}}
        </div>
        
        {{if info.license}}
        <div class="layui-footer footer">
            <div class="layui-main">
                <a href="{{:info.license.url}}" target="blank">{{:info.license.name}}</a></p>
            </div>
        </div>
        {{/if}}
    </div>
</script>
	<script id="temp_body" type="text/template">
    <div class="layui-tab layui-tab-brief">
        <ul class="layui-tab-title">
            <li class="layui-this"><span class="method {{:method}}" m_operationId ="{{:operationId}}" path="{{:path}}" method = "{{:method}}">{{:method}}</span>{{:path}}</li>
            <li>在线测试</li>
        </ul>
        <div class="layui-tab-content" style="min-height: 150px; padding: 5px 0px 0px; height: 803px;">
            <div class="layui-tab-item layui-show">
              <table class="layui-table">
              <colgroup>
                <col width="150">
                <col width="150">
                <col width="150">
                <col>
              </colgroup>
              <tbody>
                <tr>
                  <th>Path</th>
                  <td colspan="3">{{:path}}</td>
                </tr>
                <tr>
                  <th>Summary</th>
                  <td colspan="3">{{:summary}}</td>
                </tr>
                <tr>
                  <th>Description</th>
                  <td colspan="3">{{:description}}</td>
                </tr>
                <tr>
                  <th>Consumes</th>
                  <td>{{:consumes}}</td>
                  <th>Produces</th>
                  <td>{{:produces}}</td>
                </tr>
              </tbody>
            </table>
            <fieldset class="layui-elem-field layui-field-title" >
                <legend>请求参数</legend>
            </fieldset>
            <table class="layui-table">
              <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>type</th>
                    <th>Required</th>
                </tr> 
              </thead>
              <tbody>
                {{for parameters}}
                    <tr>
                        <td>{{:name}}</td>
                        <td>{{:description}}</td>
                        <td>{{:type}}</td>
                        {{if required}}
                            <td><i class="layui-icon">&#xe618;</i></td>
                        {{else}}
                            <td></td>
                        {{/if}}                   
                    </tr>
                {{/for}}
              </tbody>
            </table>
            <fieldset class="layui-elem-field layui-field-title" >
                <legend>返回参数</legend>
            </fieldset>
                <table class="layui-table">
                    <thead>
                    <tr>
                        <th>Properties</th>
                        <th>Type</th>
                        <th>Description</th>
                    </tr>
                    </thead>
                    <tbody id="path-body-response-model">
                    </tbody>

                </table>
                <fieldset class="layui-elem-field layui-field-title" >
                    <legend>【data】数据体</legend>
                </fieldset>
                <table class="layui-table" style="margin-left: 30px;width: 50%">
                    <thead>
                    <tr>
                        <th>Properties</th>
                        <th>Type</th>
                        <th>Description</th>
                    </tr>
                    </thead>
                    <tbody id="path-child-body-response-model">
                    </tbody>

                </table>
            </div>
            <div class="layui-tab-item">
                <fieldset class="layui-elem-field layui-field-title" >
                    <legend>Parameters</legend>
                </fieldset>
                <div class="method-type">
                    <lable>Parameter Type : </lable>
                    <input type="hidden" id="content_type_{{:operationId}}" value="form">
                    <button id="pt_form_{{:operationId}}" type="form" operationId = "{{:operationId}}" onclick="changeParameterType(this)" class="layui-btn layui-btn-small layui-btn-normal layui-btn-radius">Form</button>
                    <button id="pt_json_{{:operationId}}" type="json" operationId = "{{:operationId}}" onclick="changeParameterType(this)" class="layui-btn layui-btn-small layui-btn-primary layui-btn-radius">Json</button>
                </div>
                <textarea class="parameter-text hide" rows="10" id="text_tp_{{:operationId}}"></textarea>
                <table class="layui-table"  id="table_tp_{{:operationId}}">
                  <colgroup>
                    <col width="150">
                    <col>
                    <col>
                    <col width="150">
                    <col width="150">
                   <col>
                  </colgroup>
                  <thead>
                    <tr>
                        <th>Name</th>
                        <th>Value</th>
                        <th>Description</th>
                        <th>Parameter Type</th>
                        <th>Data Type</th>
                        <th>Required</th>
                    </tr> 
                  </thead>
                  <tbody>
                    {{for parameters}}
                        <tr>
                            <td>{{:name}}</td>
                            <td>
                            {{if required}}
                                <input type="text" p_operationId="{{:~root.operationId}}" name="{{:name}}" in="{{:in}}" required="required" value="{{:default}}" placeholder="required" autocomplete="off" class="layui-input">
                            {{else}}
                                <input type="text" p_operationId="{{:~root.operationId}}" name="{{:name}}" in="{{:in}}" value="{{:default}}" autocomplete="off" class="layui-input">
                            {{/if}}  
                            </td>
                            <td>{{:description}}</td>
                            <td>{{:in}}</td>
                            <td>{{:type}}</td>     
                            {{if required}}
                                <td><i class="layui-icon">&#xe618;</i></td>
                            {{else}}
                                <td></td>
                            {{/if}}                   
                        </tr>
                    {{/for}}
                  </tbody>
                </table>
                <div>
                    <button class="layui-btn" name="btn_submit" onclick="getData('{{:operationId}}')"> Submit </button>
                </div>
                <fieldset class="layui-elem-field layui-field-title" >
                    <legend>Responses</legend>
                </fieldset>
                <div class="responseJson"><pre id="json-response"></pre>
            </div>
        </div>
        </div>
    </div>
</script>
	<script id="temp_body_response_model" type="text/template">
   {{props properties}}
    <tr>
        <td>{{:key}}</td>
        <td>{{:prop.type}}</td>
        <td>{{:prop.description}}</td>
    </tr>
   {{/props}} 
</script>
</body>
<script src="webjars/layui/layui.js"></script>
<script src="webjars/js/jquery.js"></script>
<script src="webjars/js/jsrender.min.js"></script>
<script src="webjars/js/jquery.json-viewer.js"></script>
<script src="webjars/js/docs.js"></script>
</html>
