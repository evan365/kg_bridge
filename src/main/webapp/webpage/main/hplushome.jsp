<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!--360浏览器优先以webkit内核解析-->
    <title>Bridge</title>

    <link rel="shortcut icon" href="images/favicon.ico">
    <link href="plug-in/hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="plug-in/hplus/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="plug-in/hplus/css/animate.css" rel="stylesheet">
    <link href="plug-in/hplus/css/style.css?v=4.1.0" rel="stylesheet">
</head>


<body class="gray-bg">
	<div class="row  border-bottom white-bg dashboard-header">
		<div class="col-sm-12">
			<div class="panel-group" id="version">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5 class="panel-title">
							<a data-toggle="collapse" data-parent="#version" href="#v54">转入接口：EAS提供</a>
						</h5>
					</div>
					<div id="v54" class="panel-collapse collapse in">
						<div class="panel-body">
							<ol>
								<li>承保数据传入EAS</li>
							</ol>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5 class="panel-title">
							<a data-toggle="collapse" data-parent="#version" href="#v55">转出接口：前海保理提供</a>
						</h5>
					</div>
					<div id="v55" class="panel-collapse collapse">
						<div class="panel-body">
							<ol>
								<li>发送保理单</li>
								<li>作废保理单</li>
							</ol>
						</div>
					</div>
				</div>
			</div>
			<hr>
		</div>

	</div>
	<!-- 全局js -->
	<script src="plug-in/hplus/js/jquery.min.js?v=2.1.4"></script>
	<script src="plug-in/hplus/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="plug-in/hplus/js/plugins/layer/layer.min.js"></script>

	<!-- 自定义js -->
	<script src="plug-in/hplus/js/content.js"></script>
</body>
</html>
