<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<HTML>
<HEAD><TITLE>Nodes</TITLE>

	<link type='text/css' rel='stylesheet' 	href='<c:url value="/resources/css/base.css"/>'/>
	<link type='text/css' rel='stylesheet' 	href='<c:url value="/resources/css/ForceDirected.css"/>'/>
	<script type='text/javascript' 			src='<c:url value="/resources/js/jit.js"/>'></script>
	<script type='text/javascript' 			src='<c:url value="/resources/js/systemsgraphview.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-2.0.2.min.js"/>'></script>	
	
</HEAD>
<body onload="init();">
<div id="container">

<div id="left-container">



        <div class="text">
        <h4>
				Enterprise System Integration tracker    
        </h4> 
            Let's track Enterprise System Integration...
        </div>

        <div id="id-list"></div>


</div>

<div id="center-container">
    <div id="infovis"></div>    
</div>

<div id="right-container">

<div id="inner-details"></div>

</div>

<div id="log"></div>
</div>
</body>
