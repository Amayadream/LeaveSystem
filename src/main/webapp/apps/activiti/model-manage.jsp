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
        function add(){
            $("#add-model").modal();
        }
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
                        <li><a href="<%=path%>/workflow/process-list">流程定义与部署管理 </a></li>
                        <li><a href="<%=path%>/workflow/processinstance/process-list">所有流程 </a></li>
                        <li><a href="<%=path%>/workflow/processinstance/running">在运行流程</a></li>
                        <li class="active"><a href="<%=path%>/workflow/model/list">模型工作区</a></li>
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
    <h1>工作区/<small>模型工作区</small></h1>
    <button class="btn btn-success" style="float:right;" onclick="javascript:add()">创建模型</button>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>KEY</th>
            <th>Name</th>
            <th>Version</th>
            <th>创建时间</th>
            <th>最后更新时间</th>
            <th>元数据</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list }" var="model">
            <tr>
                <td>${model.id }</td>
                <td>${model.key }</td>
                <td>${model.name}</td>
                <td>${model.version}</td>
                <td>${model.createTime}</td>
                <td>${model.lastUpdateTime}</td>
                <td>${model.metaInfo}</td>
                <td>
                    <a href="<%=path%>/modeler.html?modelId=${model.id}" target="_blank">编辑</a>
                    <a href="<%=path%>/workflow/model/getXml/${model.id}" target="_blank">角色工具</a>
                    <a href="<%=path%>/workflow/model/deploy/${model.id}">部署</a>
                    导出(<a href="<%=path%>/workflow/model/export/${model.id}/bpmn" target="_blank">BPMN</a>
                    |&nbsp;<a href="<%=path%>/workflow/model/export/${model.id}/json" target="_blank">JSON</a>)
                    <a href="<%=path%>/workflow/model/delete/${model.id}">删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- 添加模态框 -->
<div class="modal fade" id="add-model" tabindex="-1" role="dialog" aria-labelledby="model2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="model2">
                    <span class="glyphicon glyphicon-edit"></span> 添加模型
                </h4>
            </div>
            <form action="<%=path%>/workflow/model/create" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="name">模型名称</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="这里输入模型的名称...">
                    </div>
                    <div class="form-group">
                        <label for="key">关键字</label>
                        <input type="text" class="form-control" id="key" name="key" placeholder="这里输入模型的关键字">
                    </div>
                    <div class="form-group">
                        <label for="description">描述</label>
                        <textarea id="description" class="form-control" rows="2" id="description" name="description" placeholder="这里输入模型的描述..."></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">提交</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> 关闭</button>
                </div>
            </form>
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
