ACC.paginationsort = {

	bindAll: function()
	{
		this.bindPaginaSort();
	},
	bindPaginaSort: function()
	{
		with(ACC.paginationsort) {
			bindSortForm($('#sort_form1'));
			bindSortForm($('#sort_form2'));
		}
	},

	bindSortForm: function(sortForm)
	{
		sortForm.change(function() {
			this.submit();
		});
	}
};

$(document).ready(function() {
	ACC.paginationsort.bindAll();
});
