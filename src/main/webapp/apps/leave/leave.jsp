<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String path = request.getContextPath();%>
<html>
<head>
    <title>请假系统|待处理任务</title>
    <link href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="<%=path%>/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" >
    <link href="<%=path%>/plugins/scojs/css/scojs.css" type="text/css" rel="stylesheet">
    <link href="<%=path%>/plugins/scojs/css/sco.message.css" type="text/css" rel="stylesheet">
    <script src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
    <script src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=path%>/plugins/scojs/js/sco.message.js"></script>
    <script src="<%=path%>/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
    <script src="<%=path%>/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript">
    </script>
    <script type="text/javascript">
        var ctx = '<%=request.getContextPath() %>';
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
            <a class="navbar-brand" href="<%=path%>/experiment/list/task">请假系统</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">请假管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li class="active"><a href="<%=path%>/experiment/list/task">当前流程</a></li>
                        <li><a href="<%=path%>/experiment/list/running">在运行流程</a></li>
                        <li><a href="<%=path%>/experiment/list/finished">已结束流程</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">工作区 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<%=path%>/workflow/process-list">流程定义与部署管理 </a></li>
                        <li><a href="<%=path%>/workflow/processinstance/process-list">所有流程 </a></li>
                        <li><a href="<%=path%>/workflow/processinstance/running">在运行流程</a></li>
                        <li><a href="<%=path%>/workflow/model/list">模型工作区</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${user.getFirstName()}  ${user.getLastName()} <span class="caret"></span></a>
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
    <div style="float: right;margin-bottom: 20px">
        <button class="btn btn-primary" onclick="javascript:startLeave();">请假</button>
    </div>

    <div>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>#</th>
                <th>执行者</th>
                <th>申请时间</th>
                <th>结束时间</th>
                <th>当前节点</th>
                <th>任务创建时间</th>
                <th>流程状态</th>
                <th>使用工具</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.result }" var="experiment" varStatus="status">
                <c:set var="task" value="${experiment.task }" />
                <c:set var="pi" value="${experiment.processInstance }" />
                <tr id="${experiment.id }" tid="${task.id }">
                    <td>${status.index + 1}</td>
                    <td>${experiment.userid }</td>
                    <td>${experiment.starttime }</td>
                    <td>${experiment.endtime }</td>
                    <td>
                        <button class="btn btn-primary btn-sm show" id="${pi.id}" onclick="showPage('${pi.id}');">${task.name }</button>
                    </td>
                        <%--<td><a target="_blank" href='${ctx }/workflow/resource/process-instance?pid=${pi.id }&type=xml'>${task.name }</a></td> --%>
                    <td>${task.createTime }</td>
                    <td>${pi.suspended ? "已挂起" : "正常" }；<b title='流程版本号'>V: ${experiment.processDefinition.version }</b></td>
                    <td><span class="label label-info">${task.description}</span></td>
                    <td>
                        <c:if test="${empty task.assignee }">
                            <a href="<%=path%>/experiment/task/claim/${task.id}" class="btn btn-info btn-sm">签收</a>
                        </c:if>
                        <c:if test="${not empty task.assignee }">
                            <a href="<%=path%>/experiment/complete1/${task.id}/false" class="btn btn-danger btn-sm">回退</a>
                            <a href="<%=path%>/experiment/complete1/${task.id}/true" class="btn btn-success btn-sm">继续</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- 请假模态框 -->
<div class="modal fade" id="leave-model" tabindex="-1" role="dialog" aria-labelledby="model1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="model1"> <span class="glyphicon glyphicon-edit"> 请假表单</span></h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="type">选择假种</label>
                        <select class="form-control" id="type">
                            <option selected> </option>
                            <option>事假</option>
                            <option>病假</option>
                            <option>公休</option>
                            <option>婚假</option>
                            <option>其他</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="starttime">开始时间</label>
                        <div class="input-group date form_date start" style="height:40px" data-link-field="dtp_input2" >
                            <input id="starttime" class="form-control" type="text" name="starttime" style="height:40px" value="" readonly>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-remove"></span>
                            </span>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="endtime">结束时间</label>
                        <div class="input-group date form_date end" style="height:40px" data-link-field="dtp_input2" >
                            <input id="endtime" class="form-control" type="text" name="endtime" style="height:40px" value="" readonly>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-remove"></span>
                            </span>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="reason">文本框</label>
                        <textarea class="form-control" rows="3" id="reason"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

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
    $(function(){
        $('.start').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd hh:ii',
            weekStart: 1,
            startDate : new Date,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 0,
            forceParse: 0
        });
        $('.end').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd hh:ii',
            weekStart: 1,
            startDate : new Date,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 0,
            forceParse: 0
        }).on("click", function (ev) {
            $(".end").datetimepicker("setStartDate", $("#starttime").val());
        });
    });

    <c:if test="${not empty error}">
    $.scojs_message("${error}", $.scojs_message.TYPE_ERROR);
    </c:if>
    <c:if test="${not empty message}">
    $.scojs_message("${message}", $.scojs_message.TYPE_OK);
    </c:if>
    function showPage(id){
        $("#img").attr("src",'<%=path%>/workflow/process/trace/auto/'+id).css("width",500).css("height",400);
        $("#show-model").modal();
    }
    function startLeave(){
        $("#leave-model").modal({
            backdrop : "static",
            keyboard : false
        });
    }
</script>
</body>
</html>
