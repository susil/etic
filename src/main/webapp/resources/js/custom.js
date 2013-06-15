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
var oTable = null;

function initTable ()
{
  return $('#tableSystems').dataTable( {
		//"sAjaxSource": 'http://localhost:8080/myapp/systems/systemtable',
		"bRetrieve ": true
	} ); 
  
}

function reloadtable()
{
	oTable = $('#tableSystems').dataTable( {
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
	
//	  $('#tableSystems').dataTable( {
//		    "bFilter": false,
//		    "bDestroy": true
//		  } );
	  
	  //initTable();
}

function loadTable() {
	////#//alert('loading...');
	reloadtable();
	//initTable();
	$.get(urlHolder.records, function(response) {
		
// 		$('#tableSystems').find('tbody').remove();
// 		
// 		for (var i=0; i<response.systems.length; i++) {
//			var row = '<tr>';
//			row += '<td><input type="radio" name="index" id="index" value="'+i+'"></td>';
//			row += '<td>' + response.systems[i].id + '</td>';
//			row += '<td>' + response.systems[i].title + '</td>';
//			row += '<td>' + response.systems[i].description + '</td>';
//			row += '</tr>';
//	 		$('#tableSystems').append(row);
// 		}
 		
 		$('#tableSystems').data('model', response.systems);
		toggleForms('hide');
 	});
}

function loadConsumerTable() {
	////#//alert('loading consumers...');
	var selected = $('input:radio[name=index]:checked').val();
	//$('#sourceId').val( $('#tableSystems').data('model')[selected].id );
	//#//alert('selected='+selected);
	$.get(urlHolder.consumers, 
			
			{
			//#//sourceId: $('#tableSystems').data('model')[selected].id
			//$('#id').val()
				sourceId:selected
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
	
	////#//alert('Step 2:'+ urlHolder.add+':'+$('#id').val()+':'+$('#title').val()+':'+$('#description').val());
	
	$.post(urlHolder.add, {
			id: $('#newId').val(),
			title: $('#newTitle').val(),
			description: $('#newDescription').val()
		}, 
		function(response) {
			if (response != null) {
				loadTable();
				toggleForms('hide'); ;
				toggleCrudButtons('show');
				//#//alert('Success! Record has been added.');
			} else {
				//#//alert('Failure! An error has occurred!');
			}
		}
	);	
}

function submitDeleteRecord() {
	var selected = $('input:radio[name=index]:checked').val();
	
	$.post(urlHolder.del, {
			//id: $('#tableSystems').data('model')[selected].id
			id:selected
		}, 
		function(response) {
			if (response == true) {
				loadTable(urlHolder.records);
				//#//alert('Success! Record has been deleted.');
			} else {
				//#//alert('Failure! An error has occurred!');
			}
		}
	);
}

function submitUpdateRecord() {
	$.post(urlHolder.edit, {
			id: $('#editId').val(),
			title: $('#editTitle').val(),
			description: $('#editDescription').val()
		}, 
		function(response) {
			////#//alert(response.id );
			//if (response == true) {
			if (response.id == $('#editId').val() ) {
				loadTable();
				toggleForms('hide'); ;
				toggleCrudButtons('show');
				//#//alert('Success! Record has been edited.');
			} else {
				//#//alert('Failure! An error has occurred!');
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
				//#//alert('Connect Success! Record has been edited.');
			} else {
				//#//alert('Connect Failure! An error has occurred!');
			}
		}
	);
}

function hasSelected() {
	var selected = $('input:radio[name=index]:checked').val();
	if (selected == undefined) { 
		//#//alert('Select a record first!');
		return false;
	}
	
	return true;
}

function fillEditForm() {
	var selected = $('input:radio[name=index]:checked').val();
	//#//alert(selected+":");
	
	$.get(urlHolder.aRecord+selected, 
			
//			{
//				sourceId:selected
//			},
			function(aRecordResponse) {
		
		$('#editId').val( aRecordResponse.id );
		$('#editTitle').val( aRecordResponse.title );
		$('#editDescription').val( aRecordResponse.description );
		
 	});
	
	
    

    

}

function fillConnectForm() {
	var selected = $('input:radio[name=index]:checked').val();
	//$('#sourceId').val( $('#tableSystems').data('model')[selected].id );
	$('#sourceId').val( selected );
	
    var jsonList;
    var listItems= "";
    $.get(urlHolder.records, function(response) { 

    for (var i = 0; i < response.systems.length; i++){
        listItems+= "<option value='" + response.systems[i].id + "'>" + response.systems[i].title + "</option>";
      }
      $("#destinationId").html(listItems);

      
    });
    
    

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
		$("#divConsumers").hide();
		////#//alert('hide all forms...');
		
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
 		$("#divConsumers").show();
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
