<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><t:mutiLang langKey="jeect.platform"/></title>
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="plug-in/themes/adminlte/css/home/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="plug-in/themes/adminlte/css/home/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="plug-in/themes/adminlte/css/home/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="plug-in/themes/adminlte/css/home/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="plug-in/themes/adminlte/css/home/_all-skins.min.css">
  <!-- Morris chart -->
  <link rel="stylesheet" href="plug-in/themes/adminlte/css/home/morris.css">
  <!-- jvectormap -->
  <link rel="stylesheet" href="plug-in/themes/adminlte/css/home/jquery-jvectormap.css">
  <!-- Date Picker -->
  <link rel="stylesheet" href="plug-in/themes/adminlte/css/home/bootstrap-datepicker.min.css">
  <!-- Daterange picker -->
  <link rel="stylesheet" href="plug-in/themes/adminlte/css/home/daterangepicker.css">
  <!-- bootstrap wysihtml5 - text editor -->
  <link rel="stylesheet" href="plug-in/themes/adminlte/css/home/bootstrap3-wysihtml5.min.css">

  <!-- Google Font -->
  <link rel="stylesheet" href="plug-in/themes/adminlte/css/home/family.css">
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
								<li>EAS登录认证</li>
								<li>引入承保单</li>
								<li>引入放款单</li>
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
