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
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">请假管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li class="active"><a href="<%=path%>/leave/list/task">当前流程</a></li>
                        <li><a href="<%=path%>/leave/list/running">在运行流程</a></li>
                        <li><a href="<%=path%>/leave/list/finished">已结束流程</a></li>
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
    <div style="float: right;margin-bottom: 20px">
        <button class="btn btn-primary" onclick="javascript:startLeave();">请假</button>
    </div>

    <div>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>#</th>
                <th>请假人</th>
                <th>假种</th>
                <th>申请开始时间</th>
                <th>申请结束时间</th>
                <th>申请时间</th>
                <th>当前节点</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.result }" var="leave" varStatus="status">
                <c:set var="task" value="${leave.task }" />
                <c:set var="pi" value="${leave.processInstance }" />
                <tr id="${leave.id }" tid="${task.id }">
                    <td>${status.index + 1}</td>
                    <td>${leave.userid }</td>
                    <td>${leave.leaveType }</td>
                    <td>${leave.starttime }</td>
                    <td>${leave.endtime }</td>
                    <td>${leave.applytime }</td>
                    <td>
                        <button class="btn btn-primary btn-sm show" id="${pi.id}" onclick="showPage('${pi.id}');">${task.name }</button>
                    </td>
                    <td>
                        <c:if test="${empty task.assignee }">
                            <a href="<%=path%>/leave/task/claim/${task.id}" class="btn btn-info btn-sm">签收</a>
                        </c:if>
                        <c:if test="${not empty task.assignee }">
                            <a href="javascript:void(0)" class="btn btn-success btn-sm" id="complete" onclick="complete('${task.taskDefinitionKey}','${leave.id }','${task.id }')" >办理</a>
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
            <form action="<%=path%>/workflow/processinstance/start/leave" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="type">选择假种</label>
                        <select class="form-control" id="type" name="leaveType">
                            <option selected> </option>
                            <option value="事假">事假</option>
                            <option value="病假">病假</option>
                            <option value="公休">公休</option>
                            <option value="婚假">婚假</option>
                            <option value="其他">其他</option>
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
                        <label for="reason">请假原因</label>
                        <textarea class="form-control" rows="3" id="reason" name="reason" placeholder="简单描述一下您请假的缘由..."></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">提交</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 领导批准模态框 -->
<div class="modal fade" id="leader-model" tabindex="-1" role="dialog" aria-labelledby="model2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="model2"> <span class="glyphicon glyphicon-edit"> 部门领导批复</span></h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="leader-id">
                <input type="hidden" id="leader-taskId">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">申请人</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="leader-model-userid"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">开始时间</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="leader-model-starttime"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">结束时间</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="leader-model-endtime"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">原因</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="leader-model-reason"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="leader-opinion" class="col-sm-2 control-label">批复意见</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" rows="3" id="leader-opinion" placeholder="简单批复同意或者驳回原因..."></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" onclick="javascript:leaderHandle('agree');" class="btn btn-success">同意</button>
                <button type="button" onclick="javascript:leaderHandle('disagree');" class="btn btn-danger">驳回</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 人事批准模态框 -->
<div class="modal fade" id="personnel-model" tabindex="-1" role="dialog" aria-labelledby="model3" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="model3"> <span class="glyphicon glyphicon-edit"> 人事主管批复</span></h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="personnel-id">
                <input type="hidden" id="personnel-taskId">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">申请人</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="personnel-model-userid"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">开始时间</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="personnel-model-starttime"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">结束时间</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="personnel-model-endtime"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">原因</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="personnel-model-reason"></p>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">部门领导意见</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="personnel-model-leader-message"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="personnel-opinion" class="col-sm-2 control-label">批复意见</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" rows="3" id="personnel-opinion" placeholder="简单批复同意或者驳回原因..."></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" onclick="javascript:personnelHandle('agree');" class="btn btn-success">同意</button>
                <button type="button" onclick="javascript:personnelHandle('disagree');" class="btn btn-danger">驳回</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 销假模态框 -->
<div class="modal fade" id="back-model" tabindex="-1" role="dialog" aria-labelledby="model4" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="model4"> <span class="glyphicon glyphicon-edit"> 销假</span></h4>
            </div>
            <form class="form-horizontal">
                <div class="form-group" id="back-leader">
                    <label class="col-sm-2 control-label">领导意见</label>
                    <div class="col-sm-10">
                        <p class="form-control-static" id="back-leader-message"></p>
                    </div>
                </div>
                <div class="form-group" id="back-personnel">
                    <label class="col-sm-2 control-label">人事批复</label>
                    <div class="col-sm-10">
                        <p class="form-control-static" id="back-personnel-message"></p>
                    </div>
                </div>
            </form>
            <hr>
            <form action="<%=path%>/leave/back" method="post">
                <input type="hidden" id="back-id" name="id">
                <input type="hidden" id="back-taskId" name="taskId">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="realiystarttime">实际开始时间</label>
                        <div class="input-group date form_date start" style="height:40px" data-link-field="dtp_input2" >
                            <input id="realiystarttime" class="form-control" type="text" name="realiystarttime" style="height:40px" value="" readonly>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-remove"></span>
                            </span>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="realiyendtime">实际结束时间</label>
                        <div class="input-group date form_date end" style="height:40px" data-link-field="dtp_input2" >
                            <input id="realiyendtime" class="form-control" type="text" name="realiyendtime" style="height:40px" value="" readonly>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-remove"></span>
                            </span>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">提交</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 调整申请模态框 -->
<div class="modal fade" id="re-model" tabindex="-1" role="dialog" aria-labelledby="model6" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="model6"> <span class="glyphicon glyphicon-edit"> 调整申请</span></h4>
            </div>
            <form class="form-horizontal">
                <div class="form-group" id="re-leader">
                    <label class="col-sm-2 control-label">领导意见</label>
                    <div class="col-sm-10">
                        <p class="form-control-static" id="re-leader-message"></p>
                    </div>
                </div>
                <div class="form-group" id="re-personnel">
                    <label class="col-sm-2 control-label">人事批复</label>
                    <div class="col-sm-10">
                        <p class="form-control-static" id="re-personnel-message"></p>
                    </div>
                </div>
            </form>
            <hr>
            <form action="<%=path%>/leave/re" method="post">
                <input type="hidden" name="taskId" id="re-taskId">
                <input type="hidden" name="id" id="re-id">
                <input type="hidden" name="b" id="re-b">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="type">选择假种</label>
                        <select class="form-control" id="re-type" name="leaveType">
                            <option selected> </option>
                            <option value="事假">事假</option>
                            <option value="病假">病假</option>
                            <option value="公休">公休</option>
                            <option value="婚假">婚假</option>
                            <option value="其他">其他</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="re-starttime">开始时间</label>
                        <div class="input-group date form_date start" style="height:40px" data-link-field="dtp_input2" >
                            <input id="re-starttime" class="form-control" type="text" name="starttime" style="height:40px" value="" readonly>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-remove"></span>
                            </span>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="re-endtime">结束时间</label>
                        <div class="input-group date form_date end" style="height:40px" data-link-field="dtp_input2" >
                            <input id="re-endtime" class="form-control" type="text" name="endtime" style="height:40px" value="" readonly>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-remove"></span>
                            </span>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="re-reason">请假原因</label>
                        <textarea class="form-control" rows="3" id="re-reason" name="reason" placeholder="简单描述一下您请假的缘由..."></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">提交</button>
                    <a class="btn btn-danger" id="destoryApply">放弃申请[销毁]</a>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 图形模态框 -->
<div class="modal fade" id="show-model" tabindex="-1" role="dialog" aria-labelledby="model5" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="model5">
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
        $("#img").attr("src",'<%=path%>/workflow/process/trace/auto/'+id);
        $("#show-model").modal();
    }
    function startLeave(){
        $("#leave-model").modal({
            backdrop : "static",
            keyboard : false
        });
    }
    function complete(name, id, taskId){
        if(name == "LeaderApproval"){
            $("#leader-id").val(id);
            $("#leader-taskId").val(taskId);
            $.getJSON('<%=path%>/leave/detail-with-vars/'+id+'/'+taskId+'', {}, function(data){
                var obj = eval(data);
                $("#leader-model-userid").text(obj.userid);
                $("#leader-model-starttime").text(obj.starttime);
                $("#leader-model-endtime").text(obj.endtime);
                $("#leader-model-reason").text(obj.reason);
            });
            $("#leader-model").modal();
        }
        if(name == "PersonnelApproval"){
            $("#personnel-id").val(id);
            $("#personnel-taskId").val(taskId);
            $.getJSON('<%=path%>/leave/detail-with-vars/'+id+'/'+taskId+'', {}, function(data){
                var obj = eval(data);
                var variables = obj.variables;
                $("#personnel-model-userid").text(obj.userid);
                $("#personnel-model-starttime").text(obj.starttime);
                $("#personnel-model-endtime").text(obj.endtime);
                $("#personnel-model-reason").text(obj.reason);
                $("#personnel-model-leader-message").text(variables.LeaderMessage);
            });
            $("#personnel-model").modal();
        }
        if(name == "ChangeApply"){
            $.getJSON('<%=path%>/leave/detail-with-vars/'+id+'/'+taskId+'', {}, function(data){
                var obj = eval(data);
                var variables = obj.variables;
                $("#re-type").val(obj.leaveType);
                $("#re-starttime").val(obj.starttime);
                $("#re-endtime").val(obj.endtime);
                $("#re-reason").val(obj.reason);
                $("#destoryApply").attr("href","<%=path%>/leave/re?b=false&taskId="+taskId);
                if(variables.LeaderMessage != null){
                    $("#re-leader-message").text(variables.LeaderMessage);
                }else{
                    $("#re-leader").css('display','none')
                }
                if(variables.PersonnelMessage != null){
                    $("#re-personnel-message").text(variables.PersonnelMessage);
                }else{
                    $("#re-personnel").css('display','none')
                }
            });
            $("#re-id").val(id);
            $("#re-taskId").val(taskId);
            $("#re-b").val(true);
            $("#re-model").modal();
        }
        if(name == "BackFromLeave"){
            $("#back-id").val(id);
            $("#back-taskId").val(taskId);
            $.getJSON('<%=path%>/leave/detail-with-vars/'+id+'/'+taskId+'', {}, function(data){
                var obj = eval(data);
                var variables = obj.variables;
                if(variables.LeaderMessage != null){
                    $("#back-leader-message").text(variables.LeaderMessage);
                }else{
                    $("#back-leader").css('display','none')
                }
                if(variables.PersonnelMessage != null){
                    $("#back-personnel-message").text(variables.PersonnelMessage);
                }else{
                    $("#back-personnel").css('display','none')
                }
            });
            $("#back-model").modal();
        }
    }
    function leaderHandle(x){
        var id = $("#leader-id").val();
        var taskId = $("#leader-taskId").val();
        var b = x == "agree" ? true: false;
        $.getJSON('<%=path%>/leave/complete/'+taskId+'/Leaderpass', {s : $("#leader-opinion").val(), b : b}, function(data){
            var obj = eval("("+data+")");
            if(obj == 1){
                $.scojs_message("处理成功!", $.scojs_message.TYPE_OK);
            }else{
                $.scojs_message("处理失败!", $.scojs_message.TYPE_ERROR);
            }
            window.location.reload();
        });
    }

    function personnelHandle(x){
        var id = $("#personnel-id").val();
        var taskId = $("#personnel-taskId").val();
        var b = x == "agree" ? true: false;
        $.getJSON('<%=path%>/leave/complete/'+taskId+'/Personnelpass', {s : $("#personnel-opinion").val(), b : b}, function(data){
            var obj = eval("("+data+")");
            if(obj == 1){
                $.scojs_message("处理成功!", $.scojs_message.TYPE_OK);
            }else{
                $.scojs_message("处理失败!", $.scojs_message.TYPE_ERROR);
            }
            window.location.reload();
        });
    }
</script>
</body>
</html>
