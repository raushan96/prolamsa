function xsdDurationFormatter(cellvalue, options, rowObject) {
	var newCellValue = '';
	var charValue = '';

	for (var i = 0; i < cellvalue.length; i++) {
		var token = cellvalue.charAt(i);

		if (i == 0) {
			if (token == 'P') {
				continue;
			} else {
				break;
			}
		} else {
			if (isNaN(token)) // letter
			{
				if (token == 'T') {
					continue;
				}

				var isPlural = false;

				if (charValue != '') {
					var numValue = parseInt(charValue);
					if (!isNaN(numValue)) {
						if (numValue == 0 || numValue > 1) {
							isPlural = true;
						}
					}
				}

				var units = null;

				if (token == 'D') {
					units = (isPlural) ? "days " : "day ";
				} else if (token == "H") {
					units = (isPlural) ? "hrs " : "hr ";
				} else if (token == "M") {
					units = (isPlural) ? "mins " : "min ";
				} else if (token == "S") {
					units = (isPlural) ? "secs " : "sec ";
				}

				if (units != null) {
					newCellValue += charValue + units;
					charValue = '';
				}
			} else // number
			{
				charValue += token;
			}
		}
	}

	return newCellValue;
}