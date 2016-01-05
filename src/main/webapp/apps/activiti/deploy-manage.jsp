<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String path = request.getContextPath();%>
<html>
<head>
    <title>实验管理|运行中的流程</title>
    <link href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="<%=path%>/plugins/scojs/css/scojs.css" type="text/css" rel="stylesheet">
    <link href="<%=path%>/plugins/scojs/css/sco.message.css" type="text/css" rel="stylesheet">
    <script src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
    <script src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=path%>/plugins/scojs/js/sco.message.js"></script>
    <script type="text/javascript">
    </script>
    <script type="text/javascript">
        $(function(){
            $('#deploy').click(function() {
                $('#deployForm').slideToggle('fast');
                return false;
            });
        });
    </script>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<%=path%>/leave/list/task">请假系统</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">请假管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<%=path%>/leave/list/task">当前流程</a></li>
                        <li><a href="<%=path%>/leave/list/running">在运行流程</a></li>
                        <li><a href="<%=path%>/leave/list/finished">已结束流程</a></li>
                    </ul>
                </li>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">工作区 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li class="active"><a href="<%=path%>/workflow/process-list">流程定义与部署管理 </a></li>
                        <li><a href="<%=path%>/workflow/processinstance/process-list">所有流程 </a></li>
                        <li><a href="<%=path%>/workflow/processinstance/running">在运行流程</a></li>
                        <li><a href="<%=path%>/workflow/model/list">模型工作区</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${userid} <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">个人信息</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="<%=path%>/user/logout">注销</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div class="container-fluid">
    <h2>工作区/<small>流程定义及部署管理</small></h2>
    <button class="btn btn-primary" id="deploy" style="float:right">部署流程</button>
    <div id="deployForm" style="display: none">
        <h3>部署新流程/<small>支持的格式有:zip、bar、bpmn、bpmn20.xml</small></h3>
        <form action="<%=path%>/workflow/deploy" method="post" enctype="multipart/form-data">
            <input type="file" name="file" /><br>
            <input type="submit" value="提交" class="btn btn-success"/>
        </form>
    </div>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ProcessDefinitionId</th>
            <th>DeploymentId</th>
            <th>名称</th>
            <th>KEY</th>
            <th>版本号</th>
            <th>XML</th>
            <th>图片</th>
            <th>部署时间</th>
            <th width="10%">是否挂起</th>
            <th width="15%">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.result }" var="object">
            <c:set var="process" value="${object[0] }" />
            <c:set var="deployment" value="${object[1] }" />
            <tr>
                <td>${process.id }</td>
                <td>${process.deploymentId }</td>
                <td>${process.name }</td>
                <td>${process.key }</td>
                <td>${process.version }</td>
                <td><a target="_blank" href='<%=path%>/workflow/resource/read?processDefinitionId=${process.id}&resourceType=xml'>${process.resourceName }</a></td>
                <td><a target="_blank" href='<%=path%>/workflow/resource/read?processDefinitionId=${process.id}&resourceType=image'>${process.diagramResourceName }</a></td>
                <td>${deployment.deploymentTime }</td>
                <td>
                    <c:if test="${process.suspended }">
                        <span class="label label-danger">已挂起</span>  <a href="processdefinition/update/active/${process.id}" class="btn btn-sm btn-success">激活</a>
                    </c:if>
                    <c:if test="${!process.suspended }">
                        <span class="label label-success">已激活</span>  <a href="processdefinition/update/suspend/${process.id}" class="btn btn-sm btn-danger">挂起</a>
                    </c:if>
                </td>
                <td>
                    <a href='<%=path%>/workflow/process/delete?deploymentId=${process.deploymentId}' class="btn btn-sm btn-danger">删除</a>
                    <a href='<%=path%>/workflow/process/convert-to-model/${process.id}' class="btn btn-sm btn-success">转换为Model</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- 删除模态框 -->
<div class="modal fade" id="show-model" tabindex="-1" role="dialog" aria-labelledby="model2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="model2">
                    <span class="glyphicon glyphicon-search"></span> 流程图
                </h4>
            </div>
            <div class="modal-body">
                <img src="" id="img">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> 关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
    <c:if test="${not empty error}">
    $.scojs_message("${error}", $.scojs_message.TYPE_ERROR);
    </c:if>
    <c:if test="${not empty message}">
    $.scojs_message("${message}", $.scojs_message.TYPE_OK);
    </c:if>
</script>
</body>
</html>
