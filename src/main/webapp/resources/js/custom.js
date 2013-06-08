/**
 * Contains custom JavaScript code
 * 
 * http://philogb.github.io/jit/static/v20/Jit/Examples/ForceDirected/example2.code.html
 * http://mbostock.github.io/protovis/ex/
 * http://infovistoolkit.blogspot.com/2012/04/java-infovis-toolkit-detailed-tutorial.html
 * 
 * 
 * 
 */
var urlHolder = new Object();

function loadTable() {
	//alert('loading...');
	$.get(urlHolder.records, function(response) {
		
		
 		$('#tableSystems').find('tbody').remove();
 		
 		for (var i=0; i<response.systems.length; i++) {
			var row = '<tr>';
			row += '<td><input type="radio" name="index" id="index" value="'+i+'"></td>';
			row += '<td>' + response.systems[i].id + '</td>';
			row += '<td>' + response.systems[i].title + '</td>';
			row += '<td>' + response.systems[i].description + '</td>';
			row += '</tr>';
	 		$('#tableSystems').append(row);
 		}
 		
 		$('#tableSystems').data('model', response.systems);
		toggleForms('hide');
 	});
}

function loadConsumerTable() {
	//alert('loading consumers...');
	var selected = $('input:radio[name=index]:checked').val();
	//$('#sourceId').val( $('#tableSystems').data('model')[selected].id );
	
	$.get(urlHolder.consumers, 
			
			{
			sourceId: $('#tableSystems').data('model')[selected].id
			},
			function(response) {
		
		
 		$('#tableConsumers').find('tbody').remove();
 		
 		for (var i=0; i<response.produces.length; i++) {
			var row = '<tr>';
			//row += '<td><input type="radio" name="index" id="index" value="'+i+'"></td>';
			row += '<td></td>';
			row += '<td>' + response.produces[i].producer.title + '</td>';
			row += '<td>' + response.produces[i].consumer.title + '</td>';
			row += '<td>' + response.produces[i].datasetname + '</td>';
			row += '</tr>';
	 		$('#tableConsumers').append(row);
 		}
 		
 		$('#tableConsumers').data('model', response.produces);
		//toggleForms('hide');
		
 	});
}

function submitNewRecord() {
	
	//alert('Step 2:'+ urlHolder.add+':'+$('#id').val()+':'+$('#title').val()+':'+$('#description').val());
	
	$.post(urlHolder.add, {
			id: $('#id').val(),
			title: $('#title').val(),
			description: $('#description').val()
		}, 
		function(response) {
			if (response != null) {
				loadTable();
				toggleForms('hide'); ;
				toggleCrudButtons('show');
				alert('Success! Record has been added.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);	
}

function submitDeleteRecord() {
	var selected = $('input:radio[name=index]:checked').val();
	
	$.post(urlHolder.del, {
			id: $('#tableSystems').data('model')[selected].id
		}, 
		function(response) {
			if (response == true) {
				loadTable(urlHolder.records);
				alert('Success! Record has been deleted.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);
}

function submitUpdateRecord() {
	$.post(urlHolder.edit, {
			id: $('#editId').val(),
			title: $('#editTitle').val(),
			description: $('#editDescription').val(),
			role: $('#editRole').val()
		}, 
		function(response) {
			if (response == true) {
				loadTable();
				toggleForms('hide'); ;
				toggleCrudButtons('show');
				alert('Success! Record has been edited.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);
}

function submitConnectRecord() {
	$.post(urlHolder.connect, {
			sourceId: $('#sourceId').val(),
			destinationId: $('#destinationId').val(),
			datasetname: $('#datasetname').val()
		}, 
		function(response) {
			if (response != null) {
				loadTable();
				toggleForms('hide'); ;
				toggleCrudButtons('show');
				alert('Connect Success! Record has been edited.');
			} else {
				alert('Connect Failure! An error has occurred!');
			}
		}
	);
}

function hasSelected() {
	var selected = $('input:radio[name=index]:checked').val();
	if (selected == undefined) { 
		alert('Select a record first!');
		return false;
	}
	
	return true;
}

function fillEditForm() {
	var selected = $('input:radio[name=index]:checked').val();
	$('#editId').val( $('#tableSystems').data('model')[selected].id );
	$('#editTitle').val( $('#tableSystems').data('model')[selected].title );
	$('#editDescription').val( $('#tableSystems').data('model')[selected].description );
}

function fillConnectForm() {
	var selected = $('input:radio[name=index]:checked').val();
	$('#sourceId').val( $('#tableSystems').data('model')[selected].id );
}

function resetNewForm() {
	$('#newId').val('');
	$('#newTitle').val('');
	$('#newDescription').val('');
}

function resetEditForm() {
	$('#editId').val('');
	$('#editTitle').val('');
	$('#editDescription').val('');
}

function toggleForms(id) {
	if (id == 'hide') {
		$('#newForm').hide();
		$('#editForm').hide();
		$('#connectForm').hide();
		//alert('hide all forms...');
		
	} else if (id == 'new') {
 		resetNewForm();
 		$('#newForm').show();
 		$('#editForm').hide();
 		
	} else if (id == 'edit') {
 		resetEditForm();
 		$('#newForm').hide();
 		$('#editForm').show();
	}
	else if (id == 'connect') {
 		resetEditForm();
 		$('#newForm').hide();
 		$('#editForm').hide();
 		$('#connectForm').show();
	}
	else if (id == 'consumer') {
 		resetEditForm();
 		$('#newForm').hide();
 		$('#editForm').hide();
 		$('#connectForm').hide();
 		$("divConsumers").show();
	}
}

function toggleCrudButtons(id) {
	if (id == 'show') {
		$('#newBtn').removeAttr('disabled');
		$('#editBtn').removeAttr('disabled');
		$('#deleteBtn').removeAttr('disabled');
		$('#reloadBtn').removeAttr('disabled');
		
	} else if (id == 'hide'){
		$('#newBtn').attr('disabled', 'disabled');
		$('#editBtn').attr('disabled', 'disabled');
		$('#deleteBtn').attr('disabled', 'disabled');
		$('#reloadBtn').attr('disabled', 'disabled');
	}
}
