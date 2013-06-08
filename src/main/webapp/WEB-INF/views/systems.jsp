<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<c:url value="/systems/records" var="recordsUrl"/>
<c:url value="/systems/create" var="addUrl"/>
<c:url value="/systems/update" var="editUrl"/>
<c:url value="/systems/delete" var="deleteUrl"/>
<c:url value="/systems/connect" var="connectUrl"/>
<c:url value="/systems/consumers" var="consumersUrl"/>


<html>
<head>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>

	<title>User Records</title>
	
	<script type='text/javascript'>
	$(function() {
		// init
		urlHolder.records = '${recordsUrl}';
		urlHolder.add = '${addUrl}';
		urlHolder.edit = '${editUrl}';
		urlHolder.del = '${deleteUrl}';
		urlHolder.connect = '${connectUrl}';
		urlHolder.consumers ='${consumersUrl}';
		
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
	</script>
</head>

<body>
	<h1 id='banner'>Adv Systems</h1>
	<hr/>
	
	<table id='tableSystems'>
		<caption></caption>
		<thead>
			<tr>
				<th></th>
				<th>Id</th>
				<th>Title</th>
				<th>Description</th>
			</tr>
		</thead>
	</table>
	
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
	
	<div id='controlBar'>
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
				<label for='newId'>Id</label><input type='text' id='id'/><br/>
				<label for='newTitle'>Title</label><input type='text' id='title'/><br/>
				<label for='newDescription'>Description</label><input type='text' id='description'/><br/>
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
				<label for='destinationId'>Destination</label><input type='text' id='destinationId'/><br/>
				<label for='datasetname'>DataSet</label><input type='text' id='datasetname'/><br/>
			</fieldset>	
			<input type='button' value='Close' id='closeConnectForm' />
			<input type='submit' value='Submit'/>
		</form>
	</div>
	
</body>
</html>