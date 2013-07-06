<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
 	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/tdata_page.css"/>'/>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/tdata_table.css"/>'/>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>

 	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/jquery-ui-1.10.3.custom.css"/>'/>

	<script type='text/javascript' src='<c:url value="/resources/js/jquery-2.0.2.min.js"/>'></script>
 
 	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/jquery-ui-1.10.3.custom.css"/>'/>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui-1.10.3.custom.js"/>'></script>

 <script>
	
 $(function(){
	
 		$( "#tabs" ).tabs();
	
/*   	    $(".tabs").click(function(e) {
 	    	e.preventDefault();
 	    	
 	    	var aVal= $('tabs.href').val()
 	    	alert(aVal);
 	    }); */ 
	
	
	});

 	

</script>

	<title>Home</title>
</head>
<body>
	<h3 id='banner'>Enterprise Systems Integration Tracker (ESIT)</h3>

<div id="tabs">
<ul>
<li><a href="#tabs-1">Home</a></li>
<li><a href="#tabs-2" >System Graph</a></li>
<li><a href="#tabs-3">Document</a></li>
</ul>

<div id="tabs-1">

<script type="text/javascript" charset="utf-8">
  $(document).ready(function(){

      var outputdiv = "#tabs-1c";
	  $.ajax({
		type: "get",
		cache: false,
		url: "/myapp/systems",
		dataType: "html",
		success: function(html){
		  $(outputdiv).html(html);
	    }
	  });      
    });	
</script>

<div id="tabs-1c"></div>
</div>


<div id="tabs-2">
<p>
	<a href="/myapp/graph#tabs-2" target="_blank" >Click Here to see Systems Network...</a>
	</br>
	</br>
</p>
<div id="tabs-2c"></div>
</div>

<div id="tabs-3">
<p>Docs:
</br>
</p>

<p>
</br>
<a href="https://wiki.library.ucsf.edu/display/~234877@ucsf.edu/ESB+Data+Integrator" >Ref Note</a>
</br></br>
<a href="https://wiki.library.ucsf.edu/display/~234877@ucsf.edu/2013/06/12/NoSQL+Database+(+Neo4J+)+with+Spring+framework" >Tech Note</a>
</br></br>
<a href="https://github.com/susil/etic">Source Code</a>

</p>




<div id="tabs-3c"></div>
</div>

</div>
<div>
	<P> ETIC: 2013 </P>
</div>
</body>
</html>
