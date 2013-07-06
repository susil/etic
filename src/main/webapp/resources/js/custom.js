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
		"sAjaxSource": '/myapp/systems/systemtable',
		"bDestroy": true,
		"bAutoWidth" : false, 
		 "aoColumns":[
						{"sWidth": '5%', "mDataProp": "id", bSearchable: false, bSortable: false},
						{"sWidth": '20%', "mDataProp": "title"},
						{"sWidth": '30%', "mDataProp": "description"},
						{"sWidth": '10%', "mDataProp": "techcontact"},
						{"sWidth": '10%', "mDataProp": "funccontact"},
						{"sWidth": '10%', "mDataProp": "launchdate" , "sType": "date-us"
							,    
							"mRender": function ( data, type, full ) {
				              var javascriptDate = new Date(data);
							  var monthInt = javascriptDate.getMonth() + 1;
							  javascriptDate = monthInt+"/"+javascriptDate.getDate()+"/"+javascriptDate.getFullYear();
				              return "<div class= date>"+javascriptDate+"<div>";
				              } 							
						},
						{"sWidth": '5%', "mDataProp": "currentrelease"},
						{"sWidth": '10%', "mDataProp": "currentreleaseddate", "sType": "date-us"
							,    
							"mRender": function ( data, type, full ) {
				              var javascriptDate = new Date(data);
							  var monthInt = javascriptDate.getMonth() + 1;
							  javascriptDate = monthInt+"/"+javascriptDate.getDate()+"/"+javascriptDate.getFullYear();
				              return "<div class= date>"+javascriptDate+"<div>";
				              } 							
						}
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
	
	
//	jQuery.fn.dataTableExt.oSort['date-us-asc']  = function(a,b) {  
//	    //use text()
//	    var ukDatea = $(a).text().split('/');
//	    var ukDateb = $(b).text().split('/');
//
//	    var x = (ukDatea[2] + ukDatea[1] + ukDatea[0]) * 1;
//	    var y = (ukDateb[2] + ukDateb[1] + ukDateb[0]) * 1;
//	    return ((x < y) ? -1 : ((x > y) ?  1 : 0));
//	};
//
//	jQuery.fn.dataTableExt.oSort['date-us-desc'] = function(a,b) {
//	    //use text()
//	    var ukDatea = $(a).text().split('/');
//	    var ukDateb = $(b).text().split('/');
//
//	    var x = (ukDatea[2] + ukDatea[1] + ukDatea[0]) * 1;
//	    var y = (ukDateb[2] + ukDateb[1] + ukDateb[0]) * 1;
//
//	    return ((x < y) ? 1 : ((x > y) ?  -1 : 0));
//	} 
	
   	jQuery.extend( jQuery.fn.dataTableExt.oSort, {
		   "date-us-pre": function ( a ) {
		       var b = a.match(/(\d{1,2})\/(\d{1,2})\/(\d{2,4})/),
		           month = b[1],
		           day = b[2],
		           year = b[3];
		 
		 
		       if(year.length == 2){
		           if(parseInt(year, 10)<70) year = '20'+year;
		           else year = '19'+year;
		       }
		       if(month.length == 1) month = '0'+month;
		       if(day.length == 1) day = '0'+day;
		 
		       var tt = year+month+day;
		       return  tt;
		   },
		   "date-us-asc": function ( a, b ) {
		       return a - b;
		   },
		 
		   "date-us-desc": function ( a, b ) {
		       return b - a;
		   }
		});
		 
		jQuery.fn.dataTableExt.aTypes.unshift(
		   function ( sData )
		   {
		       if (sData !== null && sData.match(/\d{1,2}\/\d{1,2}\/\d{2,4}/))
		       {
		 
		           return 'date-us';
		       }
		       return null;
		   }
		);  	
	
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


function loadProducerTable() {
	////#//alert('loading consumers...');
	var selected = $('input:radio[name=index]:checked').val();
	//$('#sourceId').val( $('#tableSystems').data('model')[selected].id );
	//#//alert('selected='+selected);
	$.get(urlHolder.producers, 
			
			{
			//#//sourceId: $('#tableSystems').data('model')[selected].id
			//$('#id').val()
				sourceId:selected
			},
			function(response) {
		
		
 		$('#tableProducers').find('tbody').remove();
 		
 		for (var i=0; i<response.produces.length; i++) {
			var row = '<tr>';
			//row += '<td><input type="radio" name="index" id="index" value="'+i+'"></td>';
			row += '<td></td>';
			row += '<td>' + response.produces[i].producer.title + '</td>';
			row += '<td>' + response.produces[i].consumer.title + '</td>';
			row += '<td>' + response.produces[i].datasetname + '</td>';
			row += '</tr>';
	 		$('#tableProducers').append(row);
 		}
 		
 		$('#tableProducers').data('model', response.produces);
		//toggleForms('hide');
		
 	});
}

function submitNewRecord() {
	
	////#//alert('Step 2:'+ urlHolder.add+':'+$('#id').val()+':'+$('#title').val()+':'+$('#description').val());
	
	$.post(urlHolder.add, {
			id: $('#newId').val(),
			title: $('#newTitle').val(),
			description: $('#newDescription').val(),
			techcontact:$('#newTechcontact').val(),
			funccontact:$('#newFunccontact').val(),
			launchdate:$('#newLaunchdate').val(),
			currentrelease:$('#newCurrentrelease').val(),
			currentreleaseddate:$('#newCurrentreleaseddate').val()
			
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
			description: $('#editDescription').val(),
			techcontact:$('#editTechcontact').val(),
			funccontact:$('#editFunccontact').val(),
			launchdate:$('#editLaunchdate').val(),
			currentrelease:$('#editCurrentrelease').val(),
			currentreleaseddate:$('#editCurrentreleaseddate').val()
			
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
		alert('Select a record first!');
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

		$('#editTechcontact').val( aRecordResponse.techcontact );
		$('#editFunccontact').val( aRecordResponse.funccontact );
		$('#editLaunchdate').val( 
				function (c ) {
					var javascriptDate = new Date(aRecordResponse.launchdate);
					var monthInt = javascriptDate.getMonth() + 1;
					  javascriptDate = monthInt+"/"+javascriptDate.getDate()+"/"+javascriptDate.getFullYear();
					return javascriptDate;
				}
				 );
		$('#editCurrentrelease').val( aRecordResponse.currentrelease );
		$('#editCurrentreleaseddate').val( 
				function (c ) {
					var javascriptDate = new Date(aRecordResponse.currentreleaseddate);
					var monthInt = javascriptDate.getMonth() + 1;
					  javascriptDate = monthInt+"/"+javascriptDate.getDate()+"/"+javascriptDate.getFullYear();
					return javascriptDate;
				}
		
		);

		
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

	$('#newTechcontact').val('');
	$('#newFunccontact').val('');
	$('#newLaunchdate').val('');
	$('#newCurrentrelease').val('');
	$('#newCurrentreleaseddate').val('');


}

function resetEditForm() {
	$('#editId').val('');
	$('#editTitle').val('');
	$('#editDescription').val('');

	$('#editTechcontact').val('');
	$('#editFunccontact').val('');
	$('#editLaunchdate').val('');
	$('#editCurrentrelease').val('');
	$('#editCurrentreleaseddate').val('');

}

function toggleForms(id) {
	if (id == 'hide') {
		$('#newForm').hide();
		$('#editForm').hide();
		$('#connectForm').hide();
		$("#divConsumers").hide();
		$("#divProducers").hide();
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
		$("#divProducers").hide();
 		$("#divConsumers").show();
	}
	else if (id == 'producer') {
 		resetEditForm();
 		$('#newForm').hide();
 		$('#editForm').hide();
 		$('#connectForm').hide();
		$("#divConsumers").hide();
 		$("#divProducers").show();
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
