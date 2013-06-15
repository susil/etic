<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<c:url value="/systems/records" var="recordsUrl"/>
<c:url value="/systems/create" var="addUrl"/>
<c:url value="/systems/update" var="editUrl"/>
<c:url value="/systems/delete" var="deleteUrl"/>
<c:url value="/systems/connect" var="connectUrl"/>
<c:url value="/systems/consumers" var="consumersUrl"/>
<c:url value="/systems/record/" var="aRecordUrl"/>

<html>
<head>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/demo_page.css"/>'/>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/demo_table.css"/>'/>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-2.0.2.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery.dataTables.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>
	<script type='text/javascript'>
	$(function() {
		// init
		urlHolder.records = '${recordsUrl}';
		urlHolder.add = '${addUrl}';
		urlHolder.edit = '${editUrl}';
		urlHolder.del = '${deleteUrl}';
		urlHolder.connect = '${connectUrl}';
		urlHolder.consumers ='${consumersUrl}';
		urlHolder.aRecord ='${aRecordUrl}';
		
		loadTable();
		
		$('#newBtn').click(function() { 
			toggleForms('new');
			toggleCrudButtons('hide');
		});
		
		$('#editBtn').click(function() { 
			if (hasSelected()) {
				toggleForms('edit');
				toggleCrudButtons('hide');
				fillEditForm();
			}
		});
		
		$('#reloadBtn').click(function() { 
			loadTable();
		});

		$('#deleteBtn').click(function() {
			if (hasSelected()) { 
				submitDeleteRecord();
			}
		});
		
		$('#newForm').submit(function() {
			event.preventDefault();
			//alert('1');
			submitNewRecord();
		});
		
		$('#editForm').submit(function() {
			event.preventDefault();
			submitUpdateRecord();
		});

		$('#closeNewForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});
		
		$('#closeEditForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});

		
		$('#connectBtn').click(function() { 
			if (hasSelected()) {
				toggleForms('connect');
				toggleCrudButtons('hide');
				fillConnectForm();
			}
		});
		
		$('#connectForm').submit(function() {
			event.preventDefault();
			submitConnectRecord();
		});
		
		$('#closeConnectForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});
	
		$('#consumerBtn').click(function() { 
			//alert('consumerBtn...')
			if (hasSelected()) {
				//alert('loadConsumerTable...')
				
				toggleForms('consumer');
				//toggleCrudButtons('hide');
				loadConsumerTable();
			}
		});	
		
	});
/* 	
    var jsonList = {"Table" : [{"id" : "2","title" : "MPM21"},
                               {"id" : "3","title" : "MPM22"},
                               {"id" : "4","title" : "MPM23"},
                                {"id" : "5","title" : "MPM42"},
                                {"id" : "6","title" : "MPM52"}]}; */
    

	</script>

	<title>User Records</title>
	
		<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				//alert('lets call ajax to get data...');
				//var 
				var  oTable = $('#tableSystems').dataTable( {
					"bProcessing": true,
					"sAjaxSource": 'http://localhost:8080/myapp/systems/systemtable',
					"bDestroy": true,
					"bAutoWidth" : false, 
					 "aoColumns":[
						{"sWidth": '10%', "mDataProp": "id", bSearchable: false, bSortable: false},
						{"sWidth": '25%', "mDataProp": "title"},
						{"sWidth": '40%', "mDataProp": "description"}
						],
						"aoColumnDefs": [ {
						      "aTargets": [ 0 ],
						      //"mData": "download_link",
						      "mRender": function ( data, type, full ) {
						        //return '<a href="'+data+'">Download</a>';
					        return '<input type="radio" name="index" id="index" value="'+data+'"/>' +
					        '<input type="hidden" name="id" id="id" value="' + data + '"/>';
						      }
						    } ]	
				} );
				//alert(oTable);
				
				
			} );
		</script>
</head>

<body>
<div id="container">
	<h3 id='banner'>Enterprise Systems Integration Tracker (ESIT)</h3>
	<hr/>
	<div id="dynamic" style="width:100%">
	<div id="dt_tableSystems" class="ex_highlight_row">
	<table id='tableSystems' class="display" >
		<caption></caption>
		<thead>
			<tr>
			<th width="10%">Select</th>
			<th width="25%">Title</th>
			<th width="40%">Description</th>
			</tr>
		</thead>
	<tbody>
		
	</tbody>
	<tfoot>
<!-- 		<tr> -->
<!-- 			<th>Id</th> -->
<!-- 			<th>Title</th> -->
<!-- 			<th>Description</th> -->
<!-- 		</tr> -->
	</tfoot>
	
	</table>
	</div>
	</div>
	
</div>	


	<div id="divConsumers">
		<table id='tableConsumers'>
			<caption></caption>
			<thead>
				<tr>
					<th></th>
					<th>Producer</th>
					<th>Consumer</th>
					<th>Dataset</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id='controlBar1'>
	</div>

	<div id='controlBar'>
	<br><br>
		<input type='button' value='New' id='newBtn' />
		<input type='button' value='Delete' id='deleteBtn' />
		<input type='button' value='Edit' id='editBtn' />
		<input type='button' value='Reload' id='reloadBtn' />
		<input type='button' value='Connect' id='connectBtn' />
		<input type='button' value='Consumer' id='consumerBtn' />
	</div>
	
	<div id='newForm'>
		<form>
  			<fieldset>
				<legend>New</legend>
				<label for='newId'>Id</label><input type='text' id='newId'/><br/>
				<label for='newTitle'>Title</label><input type='text' id='newTitle'/><br/>
				<label for='newDescription'>Description</label><input type='text' id='newDescription'/><br/>
  			</fieldset>
			<input type='button' value='Close' id='closeNewForm' />
			<input type='submit' value='Submit'/>
		</form>
	</div>
	
	<div id='editForm'>
		<form> 
  			<fieldset>
				<legend>Edit Record</legend>
				<input type='hidden' id='editUsername'/>
				<label for='editId'>id</label><input type='text' id='editId'/><br/>
				<label for='editTitle'>Title</label><input type='text' id='editTitle'/><br/>
				<label for='editDescription'>Descrition</label><input type='text' id='editDescription'/><br/>
			</fieldset>	
			<input type='button' value='Close' id='closeEditForm' />
			<input type='submit' value='Submit'/>
		</form>
	</div>

	<div id='connectForm'>
		<form> 
  			<fieldset>
				<legend>Connect System</legend>
				<input type='hidden' id='editUsername'/>
				<label for='sourceId'>Source</label><input type='text' id='sourceId'/><br/>
				<label for='destinationId'>Destination</label>
				<select id="destinationId"></select><br/>
				<label for='datasetname'>DataSet</label><input type='text' id='datasetname'/><br/>
			</fieldset>	
			<input type='button' value='Close' id='closeConnectForm' />
			<input type='submit' value='Submit'/>
		</form>
	</div>
	

</body>
</html>