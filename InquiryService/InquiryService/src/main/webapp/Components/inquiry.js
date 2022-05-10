//initializing the form

$(document).ready(function()
{
if ($("#alertSuccess").text().trim() == "")
 {
 $("#alertSuccess").hide();
 }
 $("#alertError").hide();
});


// event handler for the click event of the save button. 

$(document).on("click", "#btnSave", function(event)
{
// Clear alerts---------------------
 $("#alertSuccess").text("");
 $("#alertSuccess").hide();
 $("#alertError").text("");
 $("#alertError").hide();
 
// use the client-model for Form Validation
var status = validateInquiryForm();
if (status != true)
 {
 $("#alertError").text(status);
 $("#alertError").show();
 return;
 }
 
 // If validation sucess submit the form

 var type = ($("hidInquiryIDSave").val() == "") ? "POST" : "PUT";
 $.ajax(
{
 url : "InquriesAPI",
 type : type,
 data : $("#formInquiry").serialize(),
 dataType : "text",
 complete : function(response, status)
 {
 onInquirySaveComplete(response.responseText, status);
 }
});
});

// event handler for the click event of the Update button.
 
$(document).on("click", ".btnUpdate", function(event)
{
$("#hidInquiryIDSave").val($(this).data("inquiryid"));
 $("#InquiryTitle").val($(this).closest("tr").find('td:eq(0)').text());
 $("#InquiryDesc").val($(this).closest("tr").find('td:eq(1)').text());
 $("#ContactNum").val($(this).closest("tr").find('td:eq(2)').text());
 
});

// event handler for the click event of the Delete button.

$(document).on("click", ".btnRemove", function(event)
{
 $.ajax(
 {
 url : "InquriesAPI",
 type : "DELETE",
 data : "inquiryID=" + $(this).data("inquiryid"),
 dataType : "text",
 complete : function(response, status)
 {
 onInquiryDeleteComplete(response.responseText, status);
 }
 });
});

// CLIENT-MODEL================================================================
function validateInquiryForm()
{
// Title
if ($("#InquiryTitle").val().trim() == "")
 {
 return "Insert Inquiry Title.";
 }
// Description
if ($("#InquiryDesc").val().trim() == "")
 {
 return "Insert Inquiry Description.";
 } 
 // Number
if ($("#ContactNum").val().trim() == "")
 {
 return "Insert Contact Number.";
 }
// is numerical value
var tmpNum = $("#ContactNum").val().trim();
if (!$.isNumeric(tmpNum))
 {
 return "Insert a numerical value for Number.";
 }
return true;

}

function onInquirySaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divInquriesGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while saving.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while saving..");
 $("#alertError").show();
 } 
 $("#hidInquiryIDSave").val("");
 $("#formInquiry")[0].reset();
}


function onInquiryDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divInquriesGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while deleting.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while deleting..");
 $("#alertError").show();
 }
}


